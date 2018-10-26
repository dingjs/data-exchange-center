package data.exchange.center.queue.sync.aq.rabbitmq.oraceaq;

import org.springframework.boot.CommandLineRunner;

import data.exchange.center.queue.sync.aq.rabbitmq.oraceaq.listener.OracleListenerThread;

/**
 * 
 * Description: 监听启动类
 * <p>Company: xinya </p>
 * <p>Date:2017年9月7日 上午11:28:21</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
//@Component
public class OracleListener implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		OracleListenerThread multithread = new OracleListenerThread();
		multithread.main(args);
	}
}
