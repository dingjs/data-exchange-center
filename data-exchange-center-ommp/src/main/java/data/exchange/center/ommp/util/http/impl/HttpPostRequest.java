package data.exchange.center.ommp.util.http.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import data.exchange.center.ommp.util.http.HttpRequest;
import data.exchange.center.ommp.util.rabbitmq.RabbitmqInfo;

public class HttpPostRequest implements HttpRequest {

	// %2F代表"/"(默认的vhost)
	private static final String URL = "http://150.0.2.46:15672/api/overview";

	@Override
	public Map<String, Object> request(String subStr) {
		try {
			StringBuffer str = new StringBuffer();
			str.append(RabbitmqInfo.HTTP).append(RabbitmqInfo.IP_ADDR_1).append(":").append(RabbitmqInfo.PORT);
			HttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(URL);
			String up = RabbitmqInfo.USERNAME + ":" + RabbitmqInfo.PASSWORD;
			String credentials = Base64.encodeBase64String(up.getBytes("UTF-8"));
			httpGet.setHeader("Authorization", "Basic " + credentials);
			HttpResponse response = httpClient.execute(httpGet);
			BufferedReader breader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder responseString = new StringBuilder();
			String line = null;
			while ((line = breader.readLine()) != null) {
				responseString.append(line);
			}
			breader.close();
			String repsonseStr = responseString.toString();
			int statusCode = response.getStatusLine().getStatusCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
