package data.exchange.center.ommp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class TestRabbitmq {
	private static final String USERNAME = "sgyrabbitmq001";
	private static final String PASSWORD = "sgyrabbitmq001";
	// %2F代表"/"(默认的vhost)
	private static final String URL = "http://150.0.2.46:15672/api/connections/name";

	public static void main(String[] args) throws UnsupportedEncodingException {
		HttpResponse response = null;
		int statusCode = 0;

		InputStream in = null;
		try {

			HttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(URL);
			String up = USERNAME + ":" + PASSWORD;
			// 设置凭证
			String credentials = Base64.encodeBase64String(up.getBytes("UTF-8"));
			httpGet.setHeader("Authorization", "Basic " + credentials);
			response = httpClient.execute(httpGet);
			// 读取响应内容
			BufferedReader breader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder responseString = new StringBuilder();
			String line = null;
			while ((line = breader.readLine()) != null) {
				responseString.append(line);
			}
			breader.close();
			String repsonseStr = responseString.toString();
			statusCode = response.getStatusLine().getStatusCode();
			String json = JSON.toJSONString(repsonseStr);
			Map<String, Object> map = JSON.parseObject(repsonseStr,
					new TypeReference<Map<String, Object>>() {
					});
			System.out.println("statusCode=" + statusCode + " repsonseStr =" + repsonseStr);
		} catch (Exception e) {
			System.out.println("Could not connect to " + URL);
			System.exit(RabbitMQUtils.EXIT_CRITICAL);
			e.printStackTrace();
		}
	}
}