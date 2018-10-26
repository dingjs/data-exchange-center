package data.exchange.center.service.parse.ftpzip;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;

/**
 * 所需包
 * httpclient4.5.3
 * gson2.8.2
 *
 */
public class TongdahaiRequestTest {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		//rwh为主键不可重复
		//自行参数封装
		String url="http://150.0.2.164/api/service-parse-ftpzip/callBack"
				+ "?xtptbh=4"
				+ "&lcjdbh=1"
				+ "&lcslbh=1"
				+ "&rwh="+UUID.randomUUID().toString().toLowerCase()
				+ "&jsdwbm=1"
				+ "&jsdwlx=1"
				+ "&jsdwmc=1"
				+ "&fsdwlx=1"
				+ "&fsdwbm=1"
				+ "&fsdwmc=1"
				+ "&jgzt=1"
				+ "&ztms=1"
				+ "&fhsj=20171222170146"
				+ "&ywlcbm=1"
				+ "&jdbm=1"
				+ "&accessToken=9dfac51ee6f311e79b8b000ec6cba075";
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpGet = new HttpPost(url);
		HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();  
        System.out.println(response.getStatusLine()); 
        InputStream input = entity.getContent();
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = input.read(b)) != -1;) {
        	out.append(new String(b, 0, n));
        }
        System.out.println(out.toString());
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map = gson.fromJson(out.toString(), map.getClass());
        System.out.println(map);
	}
}
