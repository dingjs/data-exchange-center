package data.exchange.center.queue.sync.aq.rabbitmq.oraceaq.sender;

import java.util.Map;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import data.exchange.center.queue.sync.aq.rabbitmq.oraceaq.Config;
import oracle.jms.AQjmsFactory;
import oracle.jms.AQjmsSession;

/**
 * 
 * Description:rabbitmq传输消息到oraceaq
 * <p>Company: xinya </p>
 * <p>Date:2017年8月31日 下午5:12:06</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class SendMsgToOracleAQ {
    
    private static Logger logger = LoggerFactory.getLogger(SendMsgToOracleAQ.class);
    /**
     * 入队
     * @param userName  用户名
     * @param password  密码
     * @param jdbcUrl   url
     * @param queueName 队列名
     * @param txtmsg    TextMessage消息
     */
    public static boolean enQueue(Map<String, Object> map){
        boolean result = false;
        QueueSession queueSession = null;
        QueueConnectionFactory qcfact;
        QueueConnection qconn = null;
        Config config = new Config();
        try {
            qcfact=AQjmsFactory.getQueueConnectionFactory(config.jdbcUrl, new Properties());
            qconn = qcfact.createQueueConnection(config.userName, config.password);
            queueSession = qconn.createQueueSession(true, Session.CLIENT_ACKNOWLEDGE);

            qconn.start();
            boolean enqResult = enqueueMessage(queueSession, config.userName, "q_test", map);
            if(enqResult){
                result = true;
            }else{
                result = false;
            }
        }
        catch (Exception ex) {
            System.out.println("Exception-1: " + ex);
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }finally{
            try {
            	if(queueSession != null) {
            		queueSession.close();
            		queueSession=null;
            	}
            	if(qconn != null) {
            		qconn.close();
            		qconn=null;
            	}
            }
            catch (JMSException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * 
     * @function 入队方法
     * @author wenyuguang
     * @creaetime 2017年4月3日 下午6:15:17
     * @param queueSession
     * @param userName
     * @param queueName
     * @return 
     * @throws Exception
     */
    private static boolean enqueueMessage(QueueSession queueSession, String userName, String queueName, Map<String, Object> map) throws Exception {
        boolean result = false;
        javax.jms.Queue queue;
        QueueSender qsender;
        TextMessage txtmsg = queueSession.createTextMessage();
        try {
            queue = ((AQjmsSession) queueSession).getQueue(userName, queueName);
            /**
             * 2017-4-10 21:58:11
             * 设置消息级别 统一为3
             */
            txtmsg.setJMSPriority(3);
            txtmsg.setStringProperty("c_uuid",      map.get("c_uuid").toString());
            txtmsg.setStringProperty("n_exchgid",   map.get("n_exchgid").toString());
            txtmsg.setStringProperty("c_pkcolval",  map.get("c_pkcolval").toString());
            txtmsg.setStringProperty("c_pktypeval", map.get("c_pktypeval").toString());
            
            qsender = queueSession.createSender(queue);
            qsender.send(txtmsg);
            queueSession.commit();
            result = true;
            return result;
        }
        catch (JMSException e) {
            System.out.println("Error in Sending Messages : " + e.getMessage());
            logger.error(e.getMessage());
            result = false;
            return result;
        }
    }
}
