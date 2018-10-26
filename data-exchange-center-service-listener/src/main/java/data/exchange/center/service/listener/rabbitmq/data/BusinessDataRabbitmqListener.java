package data.exchange.center.service.listener.rabbitmq.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import data.exchange.center.common.code.CodeUtil;
import data.exchange.center.common.constant.Constant;
import data.exchange.center.common.rabbitmq.RabbitmqConf;
import data.exchange.center.service.listener.service.DeleteBusinessDataService;
import data.exchange.center.service.listener.service.Logservice;
import data.exchange.center.service.listener.service.ParseXmlService;
/**
 * 
 * Description: 对缓存表中的数据进行处理
 * <p>Company: xinya </p>
 * <p>Date:2017年9月6日 下午4:13:48</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Component
@RabbitListener(
		bindings = @QueueBinding(
				value = @Queue(value = RabbitmqConf.BUSINESS_DATA_QUEUE, durable = "true"),
				exchange = @Exchange(value = RabbitmqConf.BUSINESS_DATA_EXCHANGE, ignoreDeclarationExceptions = "true"),
				key = RabbitmqConf.BUSINESS_DATA_ROUTEKEY)
		)
public class BusinessDataRabbitmqListener {
	
	private static final Logger logger = LoggerFactory.getLogger(BusinessDataRabbitmqListener.class);  
	
	@Autowired
	private ParseXmlService parseXmlService;
	@Autowired
	private Logservice loggerService;
	@Autowired
	private DeleteBusinessDataService deleteBusinessDataService;
	@Value("${spring.application.name}")
	private String applicationName;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
    @RabbitHandler
    public void process(
    		@Payload Map<String, Object> rabbitmqMessage,
    		@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, 
    		Channel channel) throws IOException, TimeoutException {
    	try {
    		channel.basicQos(0, 1, false);
    		String ajbs = rabbitmqMessage.get(Constant.AJBS).toString();
			String fydm = rabbitmqMessage.get(Constant.FYDM).toString();
			String ajlx = rabbitmqMessage.get(Constant.AJLX).toString();
			String srccode = rabbitmqMessage.get(Constant.AJSOURCE).toString();
			String sjlx = rabbitmqMessage.get(Constant.SJLX).toString();
			String uuid = String.valueOf(loggerService.getId());
			if(srccode.equals("002")) {//均来自通达海
				if(sjlx.equalsIgnoreCase(Constant.C_BUZ_SJLX_SPZT)) {//审判主体数据同步
					Map<String, Object> map = parseXmlService.parseStorageSPZT(ajbs);
					if(!map.get(CodeUtil.RETURN_CODE).equals(CodeUtil.RETURN_SUCCESS)) {
						loggerService.logger(
								applicationName, 
								11, 
								uuid, 
								ajbs, 
								"SPZT", 
								fydm,
								"002", 
								"同步审判组体数据异常，"+map.get(CodeUtil.RETURN_MSG).toString());
					}
					deleteBusinessDataService.delete_BUF_ZZJGRY2XML(ajbs);
					System.out.println("****************组织代码:"+rabbitmqMessage.get(Constant.AJBS).toString()+"组织机构人员同步完毕");
				}else if(sjlx.equalsIgnoreCase(Constant.C_BUZ_SJLX_INSERT)) {//业务数据同步
					Map<String, Object> map = parseXmlService.parseStorageTongdahai(ajbs);
					if(!map.get(CodeUtil.RETURN_CODE).equals(CodeUtil.RETURN_SUCCESS)) {
						loggerService.logger(
								applicationName, 
								11, 
								uuid, 
								ajbs, 
								"AJSYNC", 
								fydm, 
								"002", 
								"同步业务数据异常，"+map.get(CodeUtil.RETURN_MSG).toString());
					}
					deleteBusinessDataService.delete_BUF_EXTERNAL2XML(ajbs);
					System.out.println("****************案件标识:"+rabbitmqMessage.get(Constant.AJBS).toString()+"业务数据同步完毕");
				}else if(sjlx.equalsIgnoreCase(Constant.C_BUZ_SJLX_DELETE)) {//案件删除
					Map<String, Object> map = parseXmlService.parseStorageSPZT_AJSC(ajbs);
					if(!map.get(CodeUtil.RETURN_CODE).equals(CodeUtil.RETURN_SUCCESS)) {
						loggerService.logger(
								applicationName, 
								11, 
								uuid, 
								ajbs, 
								"AJDELETE", 
								fydm, 
								"002", 
								"案件删除异常，"+map.get(CodeUtil.RETURN_MSG).toString());
					}
					deleteBusinessDataService.delete_BUF_AJSC2XML(ajbs);
					/**
					 * 删除案件需要删除结构化数据
					 */
					Map<String,Object> params = new HashMap<>();
					params.put("ajbs", ajbs);
					params.put("ajlx", ajlx);
					deleteBusinessDataService.delete_FJGH(params);
					//增加删除案件对应的ftp删除队列操作，如若不加会导致大量操作表中删除不会操作  更新于2018年5月2日16:20:32
					Map<String, Object> messageMap = new HashMap<String, Object>();
					messageMap.put(Constant.AJBS,  rabbitmqMessage.get(Constant.AJBS).toString());
					messageMap.put(Constant.FYDM,  rabbitmqMessage.get(Constant.FYDM).toString());
					messageMap.put(Constant.AJLX,  rabbitmqMessage.get(Constant.AJLX).toString());
					messageMap.put(Constant.LEVEL, 2);
					rabbitTemplate.convertAndSend(
							RabbitmqConf.UNDO_SYNC_FTP_EXCHANGE, 
							RabbitmqConf.UNDO_SYNC_FTP_ROUTEKEY, 
							messageMap,
							new MessagePostProcessor() {
								@Override
								public Message postProcessMessage(Message message) throws AmqpException {
									message.getMessageProperties().setPriority(1);
									return message;
								}
							},
							new CorrelationData(UUID.randomUUID().toString())
					);
					System.out.println("****************案件标识:"+rabbitmqMessage.get(Constant.AJBS).toString()+"结构化和非结构化数据删除完毕");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}finally {
			channel.basicAck(deliveryTag, false);
		}
    }
} 