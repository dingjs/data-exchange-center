package data.exchange.center.queue.sync.aq.rabbitmq.conf;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * Description: 初始化交换器，路由器，队列
 * <p>Company: xinya </p>
 * <p>Date:2017年9月4日 下午5:41:32</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Configuration
public class RabbitConfigMq {
	
	public static String queueName       = "queueTest";
	public static String queueName1       = "queueTest";
	public static String queueName2       = "queueTest";
	public static String queueName3       = "queueTest";
	public static String queueName4       = "queueTest";
	public static String queueName5       = "queueTest";
	public static String queueName6       = "queueTest";
	public static String queueName7       = "queueTest";
	public static String queueName8       = "queueTest";
	public static String queueName9       = "queueTest";
	public static String queueName10       = "queueTest";
	public static String exchange        = "ex_test";
	public static String exchange1        = "ex_test";
	public static String exchange2        = "ex_test";
	public static String exchange3        = "ex_test";
	public static String exchange4        = "ex_test";
	public static String exchange5        = "ex_test";
	public static String exchange6        = "ex_test";
	public static String exchange7        = "ex_test";
	public static String exchange8        = "ex_test";
	public static String exchange9        = "ex_test";
	public static String exchange10        = "ex_test";
	public static String routingKey      = "rt_test";
	public static String routingKey1      = "rt_test";
	public static String routingKey2      = "rt_test";
	public static String routingKey3      = "rt_test";
	public static String routingKey4      = "rt_test";
	public static String routingKey5      = "rt_test";
	public static String routingKey6      = "rt_test";
	public static String routingKey7      = "rt_test";
	public static String routingKey8      = "rt_test";
	public static String routingKey9      = "rt_test";
	public static String routingKey10      = "rt_test";
	
	/**
	 * 
	 * @function 初始化队列
	 * @author wenyuguang
	 * @creaetime 2017年9月5日 上午9:53:26
	 * @return
	 */
	@Bean
    public Queue testQueue() {
        return new Queue(queueName, true);
    }
	
	@Bean
    public Queue testQueue1() {
        return new Queue(queueName, true);
    }
	
	@Bean
    public Queue testQueue2() {
        return new Queue(queueName, true);
    }
	
	@Bean
    public Queue testQueue3() {
        return new Queue(queueName, true);
    }
	
	@Bean
    public Queue testQueue4() {
        return new Queue(queueName, true);
    }
	
	@Bean
    public Queue testQueue5() {
        return new Queue(queueName, true);
    }
	
	@Bean
    public Queue testQueue6() {
        return new Queue(queueName, true);
    }
	
	@Bean
    public Queue testQueue7() {
        return new Queue(queueName, true);
    }
	
	@Bean
    public Queue testQueue8() {
        return new Queue(queueName, true);
    }
	
	@Bean
    public Queue testQueue9() {
        return new Queue(queueName, true);
    }
	
	@Bean
    public Queue testQueue10() {
        return new Queue(queueName, true);
    }
    
    /**
     *  设置交换机类型
     */  
	@Bean  
    public DirectExchange defaultExchange() {  
        /** 
         * DirectExchange:按照routingkey分发到指定队列 
         * TopicExchange:多关键字匹配 
         * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念 
         * HeadersExchange ：通过添加属性key-value匹配 
         */  
        return new DirectExchange(exchange);  
    }
	
	@Bean  
    public DirectExchange defaultExchange1() {  
        /** 
         * DirectExchange:按照routingkey分发到指定队列 
         * TopicExchange:多关键字匹配 
         * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念 
         * HeadersExchange ：通过添加属性key-value匹配 
         */  
        return new DirectExchange(exchange);  
    }
	
	@Bean  
    public DirectExchange defaultExchange2() {  
        /** 
         * DirectExchange:按照routingkey分发到指定队列 
         * TopicExchange:多关键字匹配 
         * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念 
         * HeadersExchange ：通过添加属性key-value匹配 
         */  
        return new DirectExchange(exchange);  
    }
	
	@Bean  
    public DirectExchange defaultExchange3() {  
        /** 
         * DirectExchange:按照routingkey分发到指定队列 
         * TopicExchange:多关键字匹配 
         * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念 
         * HeadersExchange ：通过添加属性key-value匹配 
         */  
        return new DirectExchange(exchange);  
    }
	
	@Bean  
    public DirectExchange defaultExchange4() {  
        /** 
         * DirectExchange:按照routingkey分发到指定队列 
         * TopicExchange:多关键字匹配 
         * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念 
         * HeadersExchange ：通过添加属性key-value匹配 
         */  
        return new DirectExchange(exchange);  
    }
	
	@Bean  
    public DirectExchange defaultExchange5() {  
        /** 
         * DirectExchange:按照routingkey分发到指定队列 
         * TopicExchange:多关键字匹配 
         * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念 
         * HeadersExchange ：通过添加属性key-value匹配 
         */  
        return new DirectExchange(exchange);  
    }
	
	@Bean  
    public DirectExchange defaultExchange6() {  
        /** 
         * DirectExchange:按照routingkey分发到指定队列 
         * TopicExchange:多关键字匹配 
         * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念 
         * HeadersExchange ：通过添加属性key-value匹配 
         */  
        return new DirectExchange(exchange);  
    }
	
	@Bean  
    public DirectExchange defaultExchange7() {  
        /** 
         * DirectExchange:按照routingkey分发到指定队列 
         * TopicExchange:多关键字匹配 
         * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念 
         * HeadersExchange ：通过添加属性key-value匹配 
         */  
        return new DirectExchange(exchange);  
    }
	
	@Bean  
    public DirectExchange defaultExchange8() {  
        /** 
         * DirectExchange:按照routingkey分发到指定队列 
         * TopicExchange:多关键字匹配 
         * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念 
         * HeadersExchange ：通过添加属性key-value匹配 
         */  
        return new DirectExchange(exchange);  
    }
	
	@Bean  
    public DirectExchange defaultExchange9() {  
        /** 
         * DirectExchange:按照routingkey分发到指定队列 
         * TopicExchange:多关键字匹配 
         * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念 
         * HeadersExchange ：通过添加属性key-value匹配 
         */  
        return new DirectExchange(exchange);  
    }
	
	@Bean  
    public DirectExchange defaultExchange10() {  
        /** 
         * DirectExchange:按照routingkey分发到指定队列 
         * TopicExchange:多关键字匹配 
         * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念 
         * HeadersExchange ：通过添加属性key-value匹配 
         */  
        return new DirectExchange(exchange);  
    }
  
	@Bean  
    public Binding binding() {  
        /** 将队列绑定到交换机 */  
        return BindingBuilder.bind(testQueue()).to(defaultExchange()).with(routingKey);  
    }
	
	@Bean  
    public Binding binding1() {  
        /** 将队列绑定到交换机 */  
        return BindingBuilder.bind(testQueue()).to(defaultExchange()).with(routingKey);  
    }
	
	@Bean  
    public Binding binding2() {  
        /** 将队列绑定到交换机 */  
        return BindingBuilder.bind(testQueue()).to(defaultExchange()).with(routingKey);  
    }
	
	@Bean  
    public Binding binding3() {  
        /** 将队列绑定到交换机 */  
        return BindingBuilder.bind(testQueue()).to(defaultExchange()).with(routingKey);  
    }
	
	@Bean  
    public Binding binding4() {  
        /** 将队列绑定到交换机 */  
        return BindingBuilder.bind(testQueue()).to(defaultExchange()).with(routingKey);  
    }
	
	@Bean  
    public Binding binding5() {  
        /** 将队列绑定到交换机 */  
        return BindingBuilder.bind(testQueue()).to(defaultExchange()).with(routingKey);  
    }
	
	@Bean  
    public Binding binding6() {  
        /** 将队列绑定到交换机 */  
        return BindingBuilder.bind(testQueue()).to(defaultExchange()).with(routingKey);  
    }
	
	@Bean  
    public Binding binding7() {  
        /** 将队列绑定到交换机 */  
        return BindingBuilder.bind(testQueue()).to(defaultExchange()).with(routingKey);  
    }
	
	@Bean  
    public Binding binding8() {  
        /** 将队列绑定到交换机 */  
        return BindingBuilder.bind(testQueue()).to(defaultExchange()).with(routingKey);  
    }
	
	@Bean  
    public Binding binding9() {  
        /** 将队列绑定到交换机 */  
        return BindingBuilder.bind(testQueue()).to(defaultExchange()).with(routingKey);  
    }
	
	@Bean  
    public Binding binding10() {  
        /** 将队列绑定到交换机 */  
        return BindingBuilder.bind(testQueue()).to(defaultExchange()).with(routingKey);  
    }
}