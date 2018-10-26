package data.exchange.center.service.huayu.xfbg.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;

import data.exchange.center.common.constant.Constant;
import data.exchange.center.common.log.LoggerService;
import data.exchange.center.common.rabbitmq.MessageLevel;
import data.exchange.center.common.rabbitmq.RabbitmqConf;
import data.exchange.center.common.time.TimeUtils;
import data.exchange.center.service.huayu.xfbg.domain.Ajxx;
import data.exchange.center.service.huayu.xfbg.domain.Daj;
import data.exchange.center.service.huayu.xfbg.domain.Gcj;
import data.exchange.center.service.huayu.xfbg.mapper.DaJzMapper;
import data.exchange.center.service.huayu.xfbg.service.PushDaJzService;
import data.exchange.center.service.huayu.xfbg.util.ReflectUtils;
import net.sf.json.JSONArray;

/**
 * @ClassName: XmlGetDataServiceImpl
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: BaiMaojun
 * @Date: 2018年2月27日 上午11:27:28
 */
@Service
public class PushDaJzServiceImpl implements PushDaJzService {
    @Autowired
    private DaJzMapper daJzMapper;

    @Value("${spring.application.name}")
    private String serverName;
    @Resource(name = "sgyRedisTemplate")
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private LoggerService logService;

    Connection connection = null;
    Channel channel = null;

    private static Logger logger = LoggerFactory.getLogger(PushDaJzServiceImpl.class);

    @Override
    public void pushDaJz() {
        try {
            List<Ajxx> ajxxList = daJzMapper.getAllAjxx();
            for (int i = 0; i < ajxxList.size(); i++) {
                Ajxx ajxx = ajxxList.get(i);
                Map<String, Object> messageMap = new HashMap<String, Object>();
                String key = Constant.LOCK_NO.concat(ajxx.getAHDM());
                if (!redisTemplate.hasKey(key)) {
                    String ajbs = ajxx.getAHDM();
                    ajbs = ajbs.substring(0, 6) + "0" + ajbs.substring(7, ajbs.length());
                    int dasize = pushDa(ajbs,ajxx.getAHDM());
                    int jzsize = pushjz(ajbs,ajxx.getAHDM());
                    if (dasize > 0 || jzsize > 0) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("key", key);
                        map.put("level", MessageLevel.LEVEL_0);// 如果要改下面还有两个
                        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
                        redisTemplate.setKeySerializer(stringSerializer);
                        redisTemplate.setHashKeySerializer(stringSerializer);
                        ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
                        operations.set(key, map, TimeUtils.THIRTY_DAY, TimeUnit.SECONDS);

                        messageMap.put(Constant.AJBS, ajxx.getAHDM());
                        messageMap.put(Constant.FYDM, ajxx.getFYDM());
                        messageMap.put(Constant.AJLX, ajxx.getAJLX());
                        messageMap.put(Constant.LEVEL, MessageLevel.LEVEL_0);// 如果要改上面有一个下面还有一个
                        setRabbitMq(messageMap);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    } 
    class HandleThread extends Thread {  
        private List<Ajxx> data;  
        private int start;  
        private int end;  
   
        public HandleThread(List<Ajxx> data, int start, int end) {  
            this.data = data;  
            this.start = start;  
            this.end = end;  
        }  
   
        public void run() {  
            //这里处理数据  
            List<Ajxx> subList = data.subList(start, end);  
            for(int a=0; a<subList.size(); a++){  
                try {
                    Ajxx ajxx = subList.get(a);
                    Map<String, Object> messageMap = new HashMap<String, Object>();
                    String key = Constant.LOCK_NO.concat(ajxx.getAHDM());
                    if (!redisTemplate.hasKey(key)) {
                        String ajbs = ajxx.getAHDM();
                        ajbs = ajbs.substring(0, 6) + "0" + ajbs.substring(7, ajbs.length());
                        int dasize = pushDa(ajbs,ajxx.getAHDM());
                        int jzsize = pushjz(ajbs,ajxx.getAHDM());
                        if (dasize > 0 || jzsize > 0) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("key", key);
                            map.put("level", MessageLevel.LEVEL_0);// 如果要改下面还有两个
                            RedisSerializer<?> stringSerializer = new StringRedisSerializer();
                            redisTemplate.setKeySerializer(stringSerializer);
                            redisTemplate.setHashKeySerializer(stringSerializer);
                            ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
                            operations.set(key, map, TimeUtils.THIRTY_DAY, TimeUnit.SECONDS);

                            messageMap.put(Constant.AJBS, ajxx.getAHDM());
                            messageMap.put(Constant.FYDM, ajxx.getFYDM());
                            messageMap.put(Constant.AJLX, ajxx.getAJLX());
                            messageMap.put(Constant.LEVEL, MessageLevel.LEVEL_0);// 如果要改上面有一个下面还有一个
                            setRabbitMq(messageMap);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }  
        }  
   
    }
    public synchronized void handleList(List<Ajxx> data, int threadNum) {  
        int length = data.size();  
        int tl = length % threadNum == 0 ? length / threadNum : (length  
                / threadNum + 1);  
   
        for (int i = 0; i < threadNum; i++) {  
            int end = (i + 1) * tl;  
            HandleThread thread = new HandleThread( data, i * tl, end > length ? length : end);  
            thread.start();  
        }  
    }  
    public int pushDa(String ajbs,String ahdm) {
        JSONArray jsonObject = httpRequest("http://150.0.2.155:9090/JxjsInterface/JxjsDajServlet?ajbs=" + ajbs, "GET");
        System.out.println("档案" + ajbs + "数据量" + jsonObject.size());
        toSgy(jsonObject, "da",ahdm);
        return jsonObject.size();
    }

    public int pushjz(String ajbs,String ahdm) {
        // 获取过程卷
        JSONArray jsonObject = httpRequest("http://150.0.2.155:9090/JxjsInterface/JxjsGcjServlet?ajbs=" + ajbs, "GET");
        System.out.println("过程卷" + ajbs + "数据量" + jsonObject.size());
        toSgy(jsonObject, "gc",ahdm);
        return jsonObject.size();
    }

    @SuppressWarnings("unchecked")
    public void toSgy(JSONArray jsonObject, String tab,String ahdm) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (tab.equals("da")) {
                for (int i = 0; i < jsonObject.size(); i++) {
                    map = (Map<String, Object>) jsonObject.get(i);
                    String str = (String) map.get("C_FilePath");
                    String path = str.substring(str.indexOf("_path") + 6, str.length() - 1);
                    path = getURLDecoderString(path);
                    String name = str.substring(str.indexOf("_file_name") + 11, str.indexOf("_file_size") - 1);
                    String wjdx = str.substring(str.indexOf("_file_size") + 11, str.indexOf("_path") - 1);
                    Daj daj = ReflectUtils.getBean(map, Daj.class);
                    daj.setC_FILENAME(name);
                    daj.setC_WJDX(Integer.valueOf(wjdx));
                    daj.setC_PATH(path);
                    daj.setN_AJBS(ahdm);
                    daJzMapper.pushDajToSGY(daj);
                }
            }
            if (tab.equals("gc")) {
                for (int i = 0; i < jsonObject.size(); i++) {
                    map = (Map<String, Object>) jsonObject.get(i);
                    Gcj gcj = ReflectUtils.getBean(map, Gcj.class);
                    String str = (String) map.get("C_DOC_PROTOCOL");
                    String path = str.substring(str.indexOf("_path") + 6, str.length() - 1);
                    path = getURLDecoderString(path);
                    String name = str.substring(str.indexOf("_file_name") + 11, str.indexOf("_file_size") - 1);
                    String wjdx = str.substring(str.indexOf("_file_size") + 11, str.indexOf("_path") - 1);
                    gcj.setC_FILENAME(name);
                    gcj.setC_WJDX(Integer.valueOf(wjdx));
                    gcj.setC_PATH(path);
                    gcj.setN_AJBS(ahdm);
                    daJzMapper.pushGcjToSGY(gcj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getURLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "GBK");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setRabbitMq(Map<String, Object> messageMap) {
        rabbitTemplate.convertAndSend(RabbitmqConf.UNDO_SYNC_DATA_EXCHANGE, RabbitmqConf.UNDO_SYNC_DATA_ROUTEKEY,
                messageMap, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        message.getMessageProperties().setPriority(MessageLevel.LEVEL_0);// 如果要改上面还有两个
                        return message;
                    }
                }, new CorrelationData(UUID.randomUUID().toString()));
    }

    public static JSONArray httpRequest(String requestUrl, String requestMethod) {
        JSONArray jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {

            URL url = new URL(requestUrl);
            // http协议传输
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();
            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            Object str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONArray.fromObject(buffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
