package data.exchange.center.service.test.aspect;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
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

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import data.exchange.center.common.log.Statistics;
import data.exchange.center.common.log.StatisticsService;
import data.exchange.center.service.test.util.NamedThreadFactory;

/**
 * 
 * Description:切面获取方法调用次数
 * <p>Company: xinya </p>
 * <p>Date:2017年9月27日 下午4:52:55</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
//@Aspect
//@Component
public class SystemStatisticsAspect {

	private Logger logger = LoggerFactory.getLogger(getClass());
	ThreadLocal<Long> startTime = new ThreadLocal<Long>();

	final String key = "execution(public * data.exchange.center.service.test.controller..*.*(..))";
	
	private final ConcurrentMap<String, AtomicInteger> concurrents = new ConcurrentHashMap<String, AtomicInteger>();
	
	private final ConcurrentMap<Object, AtomicReference<long[]>> statisticsMap = new ConcurrentHashMap<Object, AtomicReference<long[]>>();

	private ScheduledFuture<?> sendFuture;
	
	private volatile boolean running = true;
	
	private static final int LENGTH = 10;
	
	private Thread writeThread;

    private BlockingQueue<Statistics> queue;
    
    @Autowired
    private StatisticsService statisticsService;
	
	 // 定时任务执行器
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3, new NamedThreadFactory("StatisticsSendTimer", true));
	
    
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
    
	/**
	 * 
	 * 定义一个切入点.
	 * 解释下：
	 * ~ 第一个 * 代表任意修饰符及任意返回值.
	 * ~ 第二个 * 任意包名
	 * ~ 第三个 * 代表任意方法.
	 * ~ 第四个 * 定义在web包或者子包
	 * ~ 第五个 * 任意方法
	 * ~ .. 匹配任意数量的参数.   
	 */
	@Pointcut(value = key)
	public void SystemStatistics() {
		 sendFuture = scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
	            public void run() {
	                // 收集统计信息
	                try {
	                    //send();
	                } catch (Throwable t) { // 防御性容错
	                    logger.error("Unexpected error occur at send statistic, cause: " + t.getMessage(), t);
	                }
	            }
	        }, 60000, 60000, TimeUnit.MILLISECONDS);
	}
	@Before("SystemStatistics()")
	public void doBefore(JoinPoint joinPoint) {
//		try {
//			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//			HttpServletRequest request = attributes.getRequest();
//            // ---- 服务信息获取 ----
//            String invokeDate = new SimpleDateFormat("YYYY-MM-DD").format(new Date());
//			long elapsed      = System.currentTimeMillis() - startTime.get(); // 计算调用耗时
//            int concurrent    = getConcurrent(joinPoint).get(); // 当前并发数
//            String service    = joinPoint.getSignature().getDeclaringTypeName();// 获取服务名称
//            String method     = joinPoint.getSignature().getName(); // 获取方法名
//            String consumer   = getRequestIpAddress(request);
//            String provider   = InetAddress.getLocalHost().getHostAddress().toString();
//            int invokeTimes   = 1;
//            
//            
//            Statistics statistics = new Statistics(invokeDate, elapsed, concurrent, service, method, consumer,
//        			provider, invokeTime, invokeTimes);
//            AtomicReference<long[]> reference = statisticsMap.get(statistics);
//            if (reference == null) {
//                statisticsMap.putIfAbsent(statistics, new AtomicReference<long[]>());
//                reference = statisticsMap.get(statistics);
//            }
//            // CompareAndSet并发加入统计数据
//            long[] current;
//            long[] update = new long[LENGTH];
//            do {
//                current = reference.get();
//                if (current == null) {
//                    update[0] = elapsed;
//                    update[1] = concurrent;
//                    update[2] = input;
//                    update[3] = output;
//                    update[4] = ;
//                    update[5] = concurrent;
//                    update[6] = input;
//                    update[7] = output;
//                    update[8] = elapsed;
//                    update[9] = concurrent;
//                } else {
//                    update[0] = current[0] + success;
//                    update[1] = current[1] + failure;
//                    update[2] = current[2] + input;
//                    update[3] = current[3] + output;
//                    update[4] = current[4] + elapsed;
//                    update[5] = (current[5] + concurrent) / 2;
//                    update[6] = current[6] > input ? current[6] : input;
//                    update[7] = current[7] > output ? current[7] : output;
//                    update[8] = current[8] > elapsed ? current[8] : elapsed;
//                    update[9] = current[9] > concurrent ? current[9] : concurrent;
//                }
//            } while (! reference.compareAndSet(current, update));
//            
//            
//            
//        } catch (Throwable t) {
////            logger.error("Failed to monitor count service " + invoker.getUrl() + ", cause: " + t.getMessage(), t);
//        }
		
		
		
		
		
		
		
		startTime.set(System.currentTimeMillis());
		// 接收到请求，记录请求内容
		logger.info("SystemStatisticsAspect.doBefore()");
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		// 记录下请求内容
		logger.info("*******************************URL : " + request.getRequestURL().toString());
		logger.info("*******************************HTTP_METHOD : " + request.getMethod());
		logger.info("*******************************IP : " + request.getRemoteAddr());
		logger.info("*******************************CLASS_NAME : " + joinPoint.getSignature().getDeclaringTypeName());
		logger.info("*******************************CLASS_METHOD : " + joinPoint.getSignature().getName());		
		logger.info("*******************************ARGS : " + Arrays.toString(joinPoint.getArgs()));
		logger.info("*****************************"+joinPoint.getSignature().getDeclaringType());
		
		
		// 获取所有参数方法一：
		Enumeration<String> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			System.out.println(paraName + ": " + request.getParameter(paraName));
		}
		
		Statistics statistics = null;
		try {
			statistics = Statistics.class.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		queue.offer(statistics);
	}

	@AfterReturning("SystemStatistics()")
	public void doAfterReturning(JoinPoint joinPoint) {

		// 处理完请求，返回内容
		logger.info("SystemStatisticsAspect.doAfterReturning()");
		logger.info("耗时（毫秒） : " + (System.currentTimeMillis() - startTime.get()));
	}
	
	public void send() {
        if (logger.isInfoEnabled()) {
            //logger.info("Send statistics to monitor " + getUrl());
        }
        String timestamp = String.valueOf(System.currentTimeMillis());
        for (Map.Entry<Object, AtomicReference<long[]>> entry : statisticsMap.entrySet()) {
            // 获取已统计数据
            Object statistics = entry.getKey();
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
//            URL url = statistics.getUrl()
//                    .addParameters(MonitorService.TIMESTAMP, timestamp,
//                            MonitorService.SUCCESS, String.valueOf(success),
//                            MonitorService.FAILURE, String.valueOf(failure), 
//                            MonitorService.INPUT, String.valueOf(input), 
//                            MonitorService.OUTPUT, String.valueOf(output),
//                            MonitorService.ELAPSED, String.valueOf(elapsed),
//                            MonitorService.CONCURRENT, String.valueOf(concurrent),
//                            MonitorService.MAX_INPUT, String.valueOf(maxInput),
//                            MonitorService.MAX_OUTPUT, String.valueOf(maxOutput),
//                            MonitorService.MAX_ELAPSED, String.valueOf(maxElapsed),
//                            MonitorService.MAX_CONCURRENT, String.valueOf(maxConcurrent)
//                            );
            
            //monitorService.collect(url);
            //调用服务方法  统计信息入库
            
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
	
	private void collect() {
        try {
            // ---- 服务信息获取 ----
            long elapsed = System.currentTimeMillis() - startTime.get(); // 计算调用耗时
            int concurrent = getConcurrent(null).get(); // 当前并发数
            String application = "";//invoker.getUrl().getParameter(Constants.APPLICATION_KEY);
            String service = "";//invoker.getInterface().getName(); // 获取服务名称
            String method = "";//RpcUtils.getMethodName(invocation); // 获取方法名
//            URL url = invoker.getUrl().getUrlParameter(Constants.MONITOR_KEY);
            //Monitor monitor = monitorFactory.getMonitor(url);
            int localPort;
            String remoteKey;
            String remoteValue;
//            if (Constants.CONSUMER_SIDE.equals(invoker.getUrl().getParameter(Constants.SIDE_KEY))) {
//                // ---- 服务消费方监控 ----
//                context = RpcContext.getContext(); // 消费方必须在invoke()之后获取context信息
//                localPort = 0;
//                remoteKey = MonitorService.PROVIDER;
//                remoteValue = invoker.getUrl().getAddress();
//            } else {
//                // ---- 服务提供方监控 ----
//                localPort = invoker.getUrl().getPort();
//                remoteKey = MonitorService.CONSUMER;
//                remoteValue = context.getRemoteHost();
//            }
            String input = "", output = "";
//            if (invocation.getAttachment(Constants.INPUT_KEY) != null) {
//                input = invocation.getAttachment(Constants.INPUT_KEY);
//            }
//            if (result != null && result.getAttachment(Constants.OUTPUT_KEY) != null) {
//                output = result.getAttachment(Constants.OUTPUT_KEY);
//            }
//            monitor.collect(new URL(Constants.COUNT_PROTOCOL,
//                                NetUtils.getLocalHost(), localPort,
//                                service + "/" + method,
//                                MonitorService.APPLICATION, application,
//                                MonitorService.INTERFACE, service,
//                                MonitorService.METHOD, method,
//                                remoteKey, remoteValue,
//                                error ? MonitorService.FAILURE : MonitorService.SUCCESS, "1",
//                                MonitorService.ELAPSED, String.valueOf(elapsed),
//                                MonitorService.CONCURRENT, String.valueOf(concurrent),
//                                Constants.INPUT_KEY, input,
//                                Constants.OUTPUT_KEY, output));
        } catch (Throwable t) {
//            logger.error("Failed to monitor count service " + invoker.getUrl() + ", cause: " + t.getMessage(), t);
        }
    }
    
    // 获取并发计数器
    private AtomicInteger getConcurrent(JoinPoint joinPoint) {
        String key = joinPoint.getSignature().getDeclaringTypeName()+ "." + joinPoint.getSignature().getName();
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
    
    /**
     * 
     * @function 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
     * @author wenyuguang
     * @creaetime 2017年9月15日 上午11:39:03
     * @param request
     * @return
     * @throws IOException
     */
    private String getRequestIpAddress(HttpServletRequest request) throws IOException {  
        String ip = request.getHeader("X-Forwarded-For");  
        if (logger.isInfoEnabled()) {  
            logger.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);  
        }  
  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("Proxy-Client-IP");  
                if (logger.isInfoEnabled()) {  
                    logger.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);  
                }  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("WL-Proxy-Client-IP");  
                if (logger.isInfoEnabled()) {  
                    logger.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);  
                }  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_CLIENT_IP");  
                if (logger.isInfoEnabled()) {  
                    logger.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);  
                }  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
                if (logger.isInfoEnabled()) {  
                    logger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);  
                }  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getRemoteAddr();  
                if (logger.isInfoEnabled()) {  
                    logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);  
                }  
            }  
        } else if (ip.length() > 15) {  
            String[] ips = ip.split(",");  
            for (int index = 0; index < ips.length; index++) {  
                String strIp = (String) ips[index];  
                if (!("unknown".equalsIgnoreCase(strIp))) {  
                    ip = strIp;  
                    break;  
                }  
            }  
        }  
        return ip;  
    }
    
    
    /**
     * 调用写日志服务写日志到数据库
     *
     * @throws Exception
     */
    private void writeToDataBase() throws Exception {
    	Statistics statistics = queue.take();
        statisticsService.addStatistics(statistics);
    }
}