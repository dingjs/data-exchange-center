package service.sjsb.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import service.sjsb.service.TaskService;

@Component
public class Scheduler{

    private static Logger logger = LoggerFactory.getLogger(Scheduler.class);

    @Autowired
    private TaskService taskService;
    /**
     * 
     * @function 总共七位，分别表示秒（0-59），分（0-59），时（0-23），日期天/日（1-31），月份）
     *           （1-12），星期（1-7,1表示星晴天，7表示星期六），年（可以缺省。取值范围是1970-2099）。
     * @author Tony
     * @creaetime 2018年4月25日 下午4:28:03
     */
	@Scheduled(cron = "0 30 0 * * ?") // 每晚十二点半执行一次
	public void startTask() {
		logger.info("上报非结构化数据任务开始");
		try {
			taskService.startTask();
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
	}
//    /*
//     * 定时任务执行器
//     */
//    private final static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
//
//    @Override
//    public void run(String... arg0) throws Exception {
//        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
//            public void run() {
//                taskService.startTask();
//            }
//        }, 0, 60000, TimeUnit.MILLISECONDS);
//    }

}
