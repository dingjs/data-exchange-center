package data.exchange.center.queue.sync.aq.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import data.exchange.center.queue.sync.aq.rabbitmq.oraceaq.listener.OracleListenerThread;

@ComponentScan(basePackages = {"data.exchange.center.queue.sync.aq.rabbitmq"})
@SpringBootApplication
public class DataExchangeCenterQueueSyncAqRabbitmqApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataExchangeCenterQueueSyncAqRabbitmqApplication.class, args);
//		OracleListenerThread multithread = new OracleListenerThread();
//		multithread.main(args);
	}
}
