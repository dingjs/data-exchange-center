/**
 * data-governance-hcdatain-dequeue-oracleAQ
 * created by yuguang at 2017年4月5日
 */
package data.exchange.center.queue.sync.aq.rabbitmq.oraceaq;

/**
 * Description:
 * <p>Company: pelox </p>
 * <p>Date:2017年4月5日 下午9:43:16</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
public class Config {

    public String userName  = "dcadm";
    public String password  = "dcadm";
    public String jdbcUrl   = "jdbc:oracle:thin:@150.0.2.15:1521/oraods";
    public String queueName = "q_test";
}
