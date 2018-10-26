package data.exchange.center.service.filewatcher.rabbitmq;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import com.rabbitmq.client.Channel;

import data.exchange.center.common.code.CodeUtil;
import data.exchange.center.common.rabbitmq.RabbitmqConf;
import data.exchange.center.service.filewatcher.service.LogService;
import data.exchange.center.service.filewatcher.service.ParseFtpZipService;

/**
 * 
 * Description: 对注解@RabbitListener中的队列进行监听
 * <p>Company: xinya </p>
 * <p>Date:2017年9月6日 下午4:13:48</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Configuration
@RabbitListener(queues = RabbitmqConf.XTBAPT_QUEUE)
public class XTBAPTListener {
	
	private static final Logger logger = LoggerFactory.getLogger(XTBAPTListener.class);  
	
    @Autowired
    private ParseFtpZipService parseFtpZipService;
    @Autowired
    private LogService      logService;
    
    @RabbitHandler
    public void process(
    		@Payload Map<String, Object> rabbitmqMessage,
    		@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, 
    		Channel channel) throws IOException, TimeoutException {
    	System.out.println(rabbitmqMessage.toString());
    	try {
    		/**
			 * 调用协同办案平台服务处理     正常发送给通达海则由通达海进行反馈，否则出错我们直接反馈
			 */
			Map<String, Object> map = (Map<String, Object>) parseFtpZipService.unzipAndParse(rabbitmqMessage.get("taskId").toString());
    		if(map.get(CodeUtil.RETURN_CODE).toString().equals(CodeUtil.RETURN_SUCCESS)) {
    			logger.info(rabbitmqMessage.get("taskId").toString()+"已解析并发送至通达海，"+map.toString());
    			logService.addLog(rabbitmqMessage.get("taskId").toString(),"已解析并发送至通达海，"+map.toString(), "info");
    		}else {
    			logger.info(rabbitmqMessage.get("taskId").toString()+"解析错误，"+map.get(CodeUtil.RETURN_MSG).toString());
    			logService.addLog(rabbitmqMessage.get("taskId").toString(),"解析错误，"+map.get(CodeUtil.RETURN_MSG).toString(), "error");
    			//错误先保存到反馈消息表然后再返回
    			parseFtpZipService.saveErrMsg(rabbitmqMessage.get("taskId").toString());
    			parseFtpZipService.handleCallBack(rabbitmqMessage.get("taskId").toString());
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("解析错误："+e.getMessage());
			logService.addLog(rabbitmqMessage.get("taskId").toString(),"解析错误："+e.getMessage(), "error");
		}finally {
			channel.basicAck(deliveryTag, true);
//			channel.close();
		}
    }  
} 