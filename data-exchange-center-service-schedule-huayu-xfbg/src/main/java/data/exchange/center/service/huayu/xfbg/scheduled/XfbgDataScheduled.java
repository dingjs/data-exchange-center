package data.exchange.center.service.huayu.xfbg.scheduled;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import data.exchange.center.common.time.TimeUtils;
import data.exchange.center.service.huayu.xfbg.service.PushDaJzService;
/**
 * Description:定时任务
 * <p>Company: xinya </p>
 * <p>Date:2018年2月7日 上午10:41:36</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Component
public class XfbgDataScheduled implements CommandLineRunner{
	
	private static Logger logger =   LoggerFactory.getLogger(XfbgDataScheduled.class);

	@Autowired
	private RabbitTemplate    rabbitTemplate;
	
	@Autowired
	private PushDaJzService pushDaJzService;
	
//	/**
// 	 * 定时任务执行器
// 	 */
//    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
//   
//	
    @Override
	public void run(String... args) throws Exception {
        pushDaJzService.pushDaJz();
//		scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
//		    @Override
//		    public void run() {
//		        try {
//		            pushDaJzService.pushDaJz();
//                } catch (Exception e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }, 0, TimeUtils.interval, TimeUnit.MILLISECONDS);
	}
}
