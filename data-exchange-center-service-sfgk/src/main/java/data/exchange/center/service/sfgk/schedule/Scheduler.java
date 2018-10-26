package data.exchange.center.service.sfgk.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import data.exchange.center.service.sfgk.service.TaskService;
import data.exchange.center.service.sfgk.service.impl.KtggTaskServiceImpl;

@Component
public class Scheduler {

	private static Logger logger = LoggerFactory.getLogger(KtggTaskServiceImpl.class);
	
	@Autowired
	@Qualifier("ktggTaskService")
	private TaskService ktggTaskService;
	@Autowired
	@Qualifier("ajckTaskService")
	private TaskService ajckTaskService;
	@Autowired
	@Qualifier("ajgkTaskService")
	private TaskService ajgkTaskService;
	@Autowired
	@Qualifier("dxTaskService")
	private TaskService dxTaskService;
	@Autowired
	@Qualifier("fygkfsTaskService")
	private TaskService fygkfsTaskService;
	@Autowired
	@Qualifier("wzfwlTaskService")
	private TaskService wzfwlTaskService;
	@Autowired
	@Qualifier("yhdlTaskService")
	private TaskService yhdlTaskService;
	
	/**
	 * 
	 * @function 总共七位，分别表示秒（0-59），分（0-59），时（0-23），日期天/日（1-31），月份）
	 * （1-12），星期（1-7,1表示星晴天，7表示星期六），年（可以缺省。取值范围是1970-2099）。
	 * @author Tony
	 * @creaetime 2018年4月25日 下午4:28:03
	 */
	@Scheduled(cron="0 30 0 * * ?")//每晚十二点半执行一次
	public void startTask() {
		logger.info("抽取开庭公告信息任务开始");
		try {
			ktggTaskService.startTask();
		} catch (Exception e) {
			logger.error("ktggTaskService"+e.getMessage());
			e.printStackTrace();
		}
		try {
			ajckTaskService.startTask();
		} catch (Exception e) {
			logger.error("ajckTaskService"+e.getMessage());
			e.printStackTrace();
		}
		try {
			ajgkTaskService.startTask();
		} catch (Exception e) {
			logger.error("ajgkTaskService"+e.getMessage());
			e.printStackTrace();
		}
		try {
			dxTaskService.startTask();
		} catch (Exception e) {
			logger.error("dxTaskService"+e.getMessage());
			e.printStackTrace();
		}
		try {
			fygkfsTaskService.startTask();
		} catch (Exception e) {
			logger.error("fygkfsTaskService"+e.getMessage());
			e.printStackTrace();
		}
		try {
			wzfwlTaskService.startTask();
		} catch (Exception e) {
			logger.error("wzfwlTaskService"+e.getMessage());
			e.printStackTrace();
		}
		try {
			yhdlTaskService.startTask();
		} catch (Exception e) {
			logger.error("yhdlTaskService"+e.getMessage());
			e.printStackTrace();
		}
	}
}
