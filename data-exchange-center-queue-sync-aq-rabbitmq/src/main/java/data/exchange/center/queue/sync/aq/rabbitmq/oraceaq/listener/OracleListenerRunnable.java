/**
 * data-governance-hcdatain-dequeue-oracleAQ
 * created by yuguang at 2017年4月15日
 */
package data.exchange.center.queue.sync.aq.rabbitmq.oraceaq.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;

import data.exchange.center.queue.sync.aq.rabbitmq.conf.RabbitConfigMq;
import data.exchange.center.queue.sync.aq.rabbitmq.oraceaq.Config;
import data.exchange.center.queue.sync.aq.rabbitmq.util.SpringUtil;
import oracle.jms.AQjmsDestination;
import oracle.jms.AQjmsFactory;
import oracle.jms.AQjmsSession;

/**
 * 
 * Description: oracle队列消息监听，出队消息向rabbitmq发送
 * <p>Company: xinya </p>
 * <p>Date:2017年9月6日 下午3:17:15</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class OracleListenerRunnable implements Runnable, ConfirmCallback {
	
	private static Logger logger = LoggerFactory.getLogger(OracleListenerRunnable.class);
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			RabbitTemplate rabbitTemplate = (RabbitTemplate)SpringUtil.getApplicationContext().getBean("rabbitTemplate");
			Config config = new Config();
			QueueConnectionFactory queueConnectionFactory = AQjmsFactory.getQueueConnectionFactory(config.jdbcUrl,
					new Properties());
			QueueConnection conn = queueConnectionFactory.createQueueConnection(config.userName, config.password);
			AQjmsSession session = (AQjmsSession) conn.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
			conn.start();
			logger.info("threadName:"+Thread.currentThread().getName() + "-> oracle aq listener has been started.......................");
			Queue queue = (AQjmsDestination) session.getQueue(config.userName, config.queueName);
			MessageConsumer consumer = session.createConsumer(queue);
			consumer.setMessageListener(new MessageListener() {
				public void onMessage(Message message) {
					/**
					 * 参数
					 */
					Map<String, Object> msgMap = new HashMap<String, Object>();
					try {
						CorrelationData correlationData = new CorrelationData(message.getStringProperty("c_uuid"));

						msgMap.put("c_uuid", message.getStringProperty("c_uuid"));
						msgMap.put("n_exchgid", message.getStringProperty("n_exchgid"));
						msgMap.put("c_pkcolval", message.getStringProperty("c_pkcolval"));
						msgMap.put("c_pktypeval", message.getStringProperty("c_pktypeval"));
						logger.info(msgMap.toString());
						
						
						/**
						 * 根据消息类型进行转发
						 */
						rabbitTemplate.convertAndSend(
								RabbitConfigMq.exchange, 
								RabbitConfigMq.routingKey, 
								msgMap,
								correlationData);
						message.acknowledge();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {

		}
	}

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		if(ack) {
			logger.info(correlationData.getId()+"->oracle aq sync to rabbitmq success");  
		}else {
			logger.error(correlationData.getId()+"->oracle aq sync to rabbitmq fail, casue:"+cause);
		}
	}
}
