package data.exchange.center.service.sfgk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 工具
 * HttpClientUtil
 * @author wenmj
 * @version 1.0
 *
 */
public class HttpClientUtil {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 获得
     * @param url url
     * @param ob ob
     */
    public static void get(String url, Object ob) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //使用httpget的方式调用
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            // 获取响应实体    
            //            HttpEntity entity = response.getEntity();
            // 打印响应状态    
            //            if (entity != null) {
            //
            //            }
        } catch (ClientProtocolException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpclient != null)
                    httpclient.close();
            } catch (IOException e) {
                logger.error("异常！");
            }
        }
    }

    /**
     * @param url URL
     * @param ob ob
     * @return
     * @throws Exception 
     */
    public static String post(String url, Object ob) throws Exception {
        String result = StringUtils.EMPTY;
        //本身是连接池的方式  连接池数目
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            //post方式传递过去的就是json的param
            HttpPost httpPost = new HttpPost(url);

            //在head上设置systemid和authcode信息
            httpPost.addHeader("SYSTEMID","com.thunisoft.fy.zg.spclgk.gzfw");
            httpPost.addHeader("AUTHCODE","version-test");

            //设置超时时间
            //setConnectTimeout：设置连接超时时间，单位毫秒。
            //setConnectionRequestTimeout：设置从connect Manager获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
            //setSocketTimeout：请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
            RequestConfig requestConfig = RequestConfig
                    .custom()
                    .setConnectTimeout(60000000)
                    .setConnectionRequestTimeout(60000000)
                    .setSocketTimeout(60000000).build();
            httpPost.setConfig(requestConfig);
            UrlEncodedFormEntity uefEntity = null;
            // 创建参数队列    
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            
            String jsonParam = "WGmzlMagPH8nlXi6xy9zMCLZATceJ1dhlMZhm7fnjUphbgwrK0iLQUb1DxEQO5Xr2pLTDtX/uengq5Ngtaq/ChMgTMAUcdD6FeT2Hm559D4JhWFYXkTGmzDtbwf56oAl5SRR8dkLFIxo6YPasiqIQTLnOe8KDhIk3gHeZk0Gnd4=";
            logger.info(new Date() + ": 采集发送的加密的参数:" + jsonParam);
            formparams.add(new BasicNameValuePair("PARAMS", jsonParam));
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");

            httpPost.setEntity(uefEntity);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            result = EntityUtils.toString(entity, "UTF-8");
            
        } catch (Exception e) {
            logger.error("客户端调用出现错误", e);
            throw e;
        } finally {
            // 关闭连接,释放资源    
            try {
                if (httpclient != null) {
                    httpclient.close();
                }
            } catch (Exception e) {
                logger.error("客户端关闭出现错误", e);
            }
        }
        return result;
    }

    /**
     * @param bytes
     * @return
     */
    public static byte[] decode(final byte[] bytes) {
        return Base64.decodeBase64(bytes);
    }

    /**
     * 二进制数据编码为BASE64字符串
     * 
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(final byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }

}
