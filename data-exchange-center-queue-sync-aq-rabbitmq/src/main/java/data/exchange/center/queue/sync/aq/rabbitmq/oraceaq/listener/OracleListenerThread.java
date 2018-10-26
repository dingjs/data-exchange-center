/**
 * data-governance-hcdatain-dequeue-oracleAQ
 * created by yuguang at 2017年4月15日
 */
package data.exchange.center.queue.sync.aq.rabbitmq.oraceaq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * <p>Company: pelox </p>
 * <p>Date:2017年4月15日 下午11:11:40</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
public class OracleListenerThread {
	
	private static Logger logger = LoggerFactory.getLogger(OracleListenerRunnable.class);
	
    public void main(String[] args) {
        try {
            OracleListenerRunnable oracleListenerRunnable = new OracleListenerRunnable();
            Thread thread = new Thread(oracleListenerRunnable);
            thread.setName("oracle aq listener thread");
            thread.start();
            logger.info("************************ oracle aq listener has been started ************************");
            System.in.read();
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
