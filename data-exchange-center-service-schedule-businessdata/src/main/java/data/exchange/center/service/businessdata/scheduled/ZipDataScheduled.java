package data.exchange.center.service.businessdata.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import data.exchange.center.service.businessdata.service.HyZipDowloadServer;
@Component
public class ZipDataScheduled  {

    private static Logger logger = LoggerFactory.getLogger(BusinessDataScheduled.class);


    @Autowired
    private HyZipDowloadServer hyZipDowloadServer;

    
    /**
     * 
     * @function 总共七位，分别表示秒（0-59），分（0-59），时（0-23），日期天/日（1-31），月份）
     *           （1-12），星期（1-7,1表示星晴天，7表示星期六），年（可以缺省。取值范围是1970-2099）。
     * @author Tony
     * @creaetime 2018年4月25日 下午4:28:03
     */
    @Scheduled(cron = "0 55 15 * * ?") // 每晚十二点半执行一次
    public void startTask() {
        logger.info("上报非结构化数据任务开始");
        try {
            hyZipDowloadServer.zipAnalysis();
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
    }
//    @Override
//    public void run(String... args) throws Exception {
//       
//    
//        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    hyZipDowloadServer.zipAnalysis();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 0, TimeUtils.interval, TimeUnit.MILLISECONDS);
//    }
}

