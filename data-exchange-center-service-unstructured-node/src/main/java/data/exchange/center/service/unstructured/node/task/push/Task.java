/**
 * data-governance-unstructured-agent-push
 * created by yuguang at 2017年5月24日
 */
package data.exchange.center.service.unstructured.node.task.push;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import data.exchange.center.service.unstructured.node.mapper.sgy.AgentPushDataMapper;
import data.exchange.center.service.unstructured.node.mapper.tdh.AgentGetDataMapper;
import data.exchange.center.service.unstructured.node.service.impl.LogService;
import data.exchange.center.service.unstructured.node.task.push.thread.queue.DleAjbsToRedis;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年10月11日 下午12:45:59</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Component
public class Task implements CommandLineRunner{
	@Value("${fydm}")
	public String fydm;
    
    @Value("${spring.application.name}")
    public String serverName;
    
    @Autowired
    private LogService logService;
    
    @Autowired
    private AgentGetDataMapper agentGetDataMapper;
    
    //锁定表redis
    @Resource(name = "sgyRedisTemplate")
	private RedisTemplate<Object, Object> redisTemplateLock;
    
    //对比数据redis
    @Resource(name = "redisTemplate")
	private RedisTemplate<Object, Object> redisTemplates;
	@Autowired
	private AgentPushDataMapper agentPushDataMapper;
    private final long interval = 300;
    
 	/**
 	 * 定时任务执行器
 	 */
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    
    /** 
     * 任务执行周期
     * 任务结束后休眠5分钟再次抓取数据
     * (non-Javadoc)
     * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
     */
	@Override
	public void run(String... args) throws Exception {
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            public void run() {
//            	List<ConcurrentMap<String, Object>> listRedis = Collections.synchronizedList(new ArrayList<>());
//            	ValueOperations<Object, Object> operations = redisTemplates.opsForValue();
//            	listRedis = (List<ConcurrentMap<String, Object>>) operations.get("300000000021255_EAJ_SSJCYX");
//            	System.out.println(listRedis);
            	new DleAjbsToRedis(agentGetDataMapper, redisTemplates, agentPushDataMapper, fydm,redisTemplateLock,serverName,logService);
               System.out.println("开始执行数据抽取定时任务");
            	//new PushAjbsToQueue(agentGetDataMapper, redisTemplate,agentPushDataMapper,fydm).run();
            }
        }, 0, interval, TimeUnit.MILLISECONDS);
	}
}
