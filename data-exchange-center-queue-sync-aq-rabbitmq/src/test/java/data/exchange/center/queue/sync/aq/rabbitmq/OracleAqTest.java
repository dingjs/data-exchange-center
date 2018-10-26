package data.exchange.center.queue.sync.aq.rabbitmq;

import java.io.IOException;
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

import org.springframework.amqp.rabbit.support.CorrelationData;

import oracle.jms.AQjmsDestination;
import oracle.jms.AQjmsFactory;
import oracle.jms.AQjmsSession;

public class OracleAqTest {

	public static void main(String[] args) {
		try {
		    QueueConnectionFactory queueConnectionFactory = AQjmsFactory
		    		.getQueueConnectionFactory("jdbc:oracle:thin:@150.0.2.15:1521/oraods", new Properties());
		    QueueConnection conn = queueConnectionFactory.createQueueConnection("dcadm", "dcadm");
		    AQjmsSession session = (AQjmsSession) conn.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
		    Queue queue = (AQjmsDestination) session.getQueue("dcadm", "q_test");
		    MessageConsumer consumer = session.createConsumer(queue);
		    consumer.setMessageListener(new MessageListener() {
		        public void onMessage(Message message) {
		            /**
		             * 参数
		             */
		            Map<String, Object> msgMap = new HashMap<String, Object>();
		            try {
		            	CorrelationData correlationData = new CorrelationData(message.getStringProperty("c_uuid"));
		            	
		                msgMap.put("c_uuid",      message.getStringProperty("c_uuid")     );
		                msgMap.put("n_exchgid",   message.getStringProperty("n_exchgid")  );
		                msgMap.put("c_pkcolval",  message.getStringProperty("c_pkcolval") );
		                msgMap.put("c_pktypeval", message.getStringProperty("c_pktypeval"));
		                
		                /**
		                 * 根据消息类型进行转发
		                 */
//		                rabbitTemplate.convertAndSend(
//		                		RabbitConfigMq.exchange, 
//		                		RabbitConfigMq.routingKey, 
//		                		msgMap, 
//		                		correlationData);
		                
		            }
		            catch (Exception e) {
		                e.printStackTrace();
		            }
		        }
		    });
		}catch(Exception e) {
			e.getMessage();
		}
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
