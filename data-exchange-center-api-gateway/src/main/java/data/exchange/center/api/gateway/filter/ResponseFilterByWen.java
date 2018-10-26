package data.exchange.center.api.gateway.filter;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.apache.RibbonApacheHttpResponse;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import data.exchange.center.api.gateway.domain.Statistics;
import data.exchange.center.api.gateway.service.StatisticsService;
import data.exchange.center.api.gateway.util.NetUtils;
import data.exchange.center.api.gateway.util.StrUtil;

public class ResponseFilterByWen extends ZuulFilter {

	private static Logger logger = LoggerFactory.getLogger(ResponseFilterByWen.class);
	
	@Autowired
	private StatisticsService statisticsService;
	
	private BlockingQueue<Statistics> queue;
	
	private Thread writeThread;
	
	private volatile boolean running = true;
	
	// 统计信息收集定时器
    private final ScheduledFuture<?> sendFuture;
    
    // 定时任务执行器
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);

    private long monitorInterval = 60000;
    
    private final ConcurrentMap<Statistics, AtomicReference<long[]>> statisticsMap = new ConcurrentHashMap<Statistics, AtomicReference<long[]>>();

    private static final int LENGTH = 10;
    
    //并发数
    private final ConcurrentMap<String, AtomicInteger> concurrents = new ConcurrentHashMap<String, AtomicInteger>();
    
    public ResponseFilterByWen() {

        // 启动统计信息收集定时器
        sendFuture = scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                // 收集统计信息
                try {
                    send();
                } catch (Throwable t) { // 防御性容错
                    logger.error("Unexpected error occur at send statistic, cause: " + t.getMessage(), t);
                }
            }
        }, monitorInterval, monitorInterval, TimeUnit.MILLISECONDS);
	}
    
	@PostConstruct
    private void init() {
        queue = new LinkedBlockingQueue<Statistics>(100000);
        writeThread = new Thread(new Runnable() {
            public void run() {
                while (running) {
                    try {
                        writeToDataBase(); // 记录统计日志
                    } catch (Throwable t) { // 防御性容错
                        logger.error("Unexpected error occur at write stat log, cause: " + t.getMessage(), t);
                        try {
                            Thread.sleep(5000); // 失败延迟
                        } catch (Throwable t2) {
                        }
                    }
                }
            }
        });
        writeThread.setDaemon(true);
        writeThread.setName("DubboMonitorAsyncWriteLogThread");
        writeThread.start();
    }
	
    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

	@Override
	public Object run() {
		try {
			RequestContext context = RequestContext.getCurrentContext();
			HttpServletRequest request = context.getRequest();
			RibbonApacheHttpResponse ribbonResponse = (RibbonApacheHttpResponse) context.get("ribbonResponse");
			/**
             * 请求url
             */
            String remoteUrl = request.getRequestURI().toLowerCase();
            URI url = null;
            try {
            	url = ribbonResponse.getRequestedURI();
            }catch(Exception e) {
            	
            }
            if(url == null) {
            	return null;
            }
            String invokeDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String service = remoteUrl.substring(0, remoteUrl.lastIndexOf("/")).replace("/", "");
            String method = remoteUrl.substring(remoteUrl.lastIndexOf("/"), remoteUrl.length()).replace("/", "");
            String consumerIp = NetUtils.getIpAddress(request);
            String provider = url.getAuthority().substring(0, url.getAuthority().indexOf(":"));
            String type = "provider";
            long invokeTime = System.currentTimeMillis();
            
            int maxElapsed = 0;
            int maxConcurrent = 1;
			
			
			// 读写统计变量
			int success = 1;
            int failure = 0;
	        int input = 0;
	        int output = 0;
	        long elapsed = System.currentTimeMillis() - (long)request.getAttribute(StrUtil.INVOKE_TIME);//耗时
	        System.err.println("耗时:"+elapsed+"ms");
	        int concurrent = getConcurrent(remoteUrl).get();
	        // 初始化原子引用
	        Statistics statistics = new Statistics();
	        statistics.setConcurrent(concurrent);
	        statistics.setConsumer(consumerIp);
	        statistics.setElapsed(elapsed);
	        statistics.setFailure(failure);
	        statistics.setInvokeDate(invokeDate);
	        statistics.setInvokeTime(invokeTime);
	        statistics.setMaxConcurrent(maxConcurrent);
	        statistics.setMaxElapsed(maxElapsed);
	        statistics.setMethod(method);
	        statistics.setProvider(provider);
	        statistics.setService(service);
	        statistics.setSuccess(success);
	        statistics.setType(type);
	        
	        
	        AtomicReference<long[]> reference = statisticsMap.get(statistics);
	        if (reference == null) {
	            statisticsMap.putIfAbsent(statistics, new AtomicReference<long[]>());
	            reference = statisticsMap.get(statistics);
	        }
	        // CompareAndSet并发加入统计数据
	        long[] current;
	        long[] update = new long[LENGTH];
	        do {
	            current = reference.get();
	            if (current == null) {
	                update[0] = success;
	                update[1] = failure;
	                update[2] = input;
	                update[3] = output;
	                update[4] = elapsed;
	                update[5] = concurrent;
	                update[6] = input;
	                update[7] = output;
	                update[8] = elapsed;
	                update[9] = concurrent;
	            } else {
	                update[0] = current[0] + success;
	                update[1] = current[1] + failure;
	                update[2] = current[2] + input;
	                update[3] = current[3] + output;
	                update[4] = current[4] + elapsed;
	                update[5] = (current[5] + concurrent) / 2;
	                update[6] = current[6] > input ? current[6] : input;
	                update[7] = current[7] > output ? current[7] : output;
	                update[8] = current[8] > elapsed ? current[8] : elapsed;
	                update[9] = current[9] > concurrent ? current[9] : concurrent;
	            }
	        } while (! reference.compareAndSet(current, update));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}
	
	private void writeToDataBase() throws Exception {
        try {
        	Statistics statistics = queue.take();
        	if(statistics.getFailure() == 0
        			&&statistics.getMaxConcurrent() == 0
        			&&statistics.getConcurrent() == 0
        			&&statistics.getSuccess() ==0) {
        		System.err.println("并发成功失败全为零");
        		return;
        	}
        	statisticsService.addStatistics(statistics);
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
        }
    }
	
	public void send() {
        if (logger.isInfoEnabled()) {
            logger.info("Send statistics to monitor ");
        }
        for (Map.Entry<Statistics, AtomicReference<long[]>> entry : statisticsMap.entrySet()) {
            // 获取已统计数据
            Statistics statistics = entry.getKey();
            AtomicReference<long[]> reference = entry.getValue();
            long[] numbers = reference.get();
            long success = numbers[0];
            long failure = numbers[1];
            long input = numbers[2];
            long output = numbers[3];
            long elapsed = numbers[4];
            long concurrent = numbers[5];
            long maxInput = numbers[6];
            long maxOutput = numbers[7];
            long maxElapsed = numbers[8];
            long maxConcurrent = numbers[9];
             
            // 发送汇总信息
            collect(statistics);
            
            // 减掉已统计数据
            long[] current;
            long[] update = new long[LENGTH];
            do {
                current = reference.get();
                if (current == null) {
                    update[0] = 0;
                    update[1] = 0;
                    update[2] = 0;
                    update[3] = 0;
                    update[4] = 0;
                    update[5] = 0;
                } else {
                    update[0] = current[0] - success;
                    update[1] = current[1] - failure;
                    update[2] = current[2] - input;
                    update[3] = current[3] - output;
                    update[4] = current[4] - elapsed;
                    update[5] = current[5] - concurrent;
                }
            } while (! reference.compareAndSet(current, update));
        }
    }
	
	public void collect(Statistics statistics) {
        queue.offer(statistics);
        if (logger.isInfoEnabled()) {
            logger.info("collect statistics: " + statistics);
        }
	}
	
	/**
	 * 
	 * @function 获取并发计数器
	 * @author wenyuguang
	 * @creaetime 2017年12月21日 下午3:55:19
	 * @param invoker
	 * @param invocation
	 * @return
	 */
    private AtomicInteger getConcurrent(String key) {
        AtomicInteger concurrent = concurrents.get(key);
        if (concurrent == null) {
            concurrents.putIfAbsent(key, new AtomicInteger());
            concurrent = concurrents.get(key);
        }
        return concurrent;
    }
    
    public void destroy() {
        try {
            sendFuture.cancel(true);
        } catch (Throwable t) {
            logger.error("Unexpected error occur at cancel sender timer, cause: " + t.getMessage(), t);
        }
    }
}
