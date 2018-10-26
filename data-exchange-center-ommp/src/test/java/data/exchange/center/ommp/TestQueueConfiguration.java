package data.exchange.center.ommp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.alibaba.fastjson.JSON;

public class TestQueueConfiguration {
    private static final String USERNAME="sgyrabbitmq001";
    private static final String PASSWORD="sgyrabbitmq001";
    private static final String QUEUE="undo.sync.ftp.queue";
    //%2F代表"/"(默认的vhost)
    private static final String URL="http://150.0.2.46:15672/api/queues/node/"+QUEUE;
    public static void main(String[] args) throws UnsupportedEncodingException {
        HttpResponse response = null;
        int statusCode = 0;
        String auto_delete = null, repsonseStr=null,durable = null;
        try {
            DefaultHttpClient Client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(URL);
            String up = USERNAME+":"+PASSWORD;
            //设置凭证
            String credentials = Base64.encodeBase64String(up.getBytes("UTF-8"));
            httpGet.setHeader("Authorization","Basic "+credentials);
            response = Client.execute(httpGet);
            //读取响应内容
            BufferedReader breader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder responseString = new StringBuilder();
            String line = null;
            while ((line = breader.readLine()) !=null) {
                responseString.append(line);
            }
            breader.close();
            repsonseStr = responseString.toString();
            auto_delete = JSON.parseObject(repsonseStr).getString("auto_delete");
            durable = JSON.parseObject(repsonseStr).getString("durable");
            statusCode = response.getStatusLine().getStatusCode();
            System.out.println("statusCode="+statusCode+" repsonseStr =" + repsonseStr);
        } catch (Exception e) {
            System.out.println("Could not connect to "+URL);
            System.exit(RabbitMQUtils.EXIT_CRITICAL);
            e.printStackTrace();
        }
        //队列不存在的情况
        if(statusCode==404){
            System.out.println("Critical:Queue "+QUEUE+" does not exist");
            System.exit(RabbitMQUtils.EXIT_CRITICAL);
        }
        if(statusCode>299){
            System.out.println("Unexpected API error :"+repsonseStr);
            System.exit(RabbitMQUtils.EXIT_UNKNOWN);
        }
        //检测auto_delete或者durable属性不对应则警告
        if(auto_delete!="false"){
            System.out.println("WARN: Queue "+QUEUE+"-auto_delete flag is not false");
            System.exit(RabbitMQUtils.EXIT_WARNING);
        }if(durable!="true"){
            System.out.println("WARN: Queue "+QUEUE+"-durable flag is not true");
            System.exit(RabbitMQUtils.EXIT_WARNING);
        }
        System.out.println("OK! Connect to "+URL+" successful ");
        System.exit(RabbitMQUtils.EXIT_OK);
    }
}