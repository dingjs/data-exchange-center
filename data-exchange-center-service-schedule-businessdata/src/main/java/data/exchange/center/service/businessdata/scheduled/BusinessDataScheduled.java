package data.exchange.center.service.businessdata.scheduled;

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
import data.exchange.center.service.businessdata.service.HyZipDowloadServer;
import data.exchange.center.service.businessdata.service.XmlGetDataService;

/**
 * Description:定时任务
 * <p>
 * Company: xinya
 * </p>
 * <p>
 * Date:2018年2月7日 上午10:41:36
 * </p>
 * 
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Component
public class BusinessDataScheduled implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(BusinessDataScheduled.class);



    @Autowired
    private XmlGetDataService xmlGetDataService;

    /**
     * 定时任务执行器
     */
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    /**
     * 
     * @function
     * @author wenyuguang
     * @creaetime 2017年10月17日 上午11:09:43
     */
    @Override
    public void run(String... args) throws Exception {
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    // xmlGetDataService.getDcMonXmlOutXml();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, 0, TimeUtils.interval, TimeUnit.MILLISECONDS);
    }
}
