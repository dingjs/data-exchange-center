package data.exchange.center.ommp.util.http.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import data.exchange.center.common.code.CodeUtil;
import data.exchange.center.ommp.util.http.HttpRequest;
import data.exchange.center.ommp.util.rabbitmq.RabbitmqInfo;

public class HttpGetRequest implements HttpRequest {

	@Override
	public Map<String, Object> request(String subStr) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		BufferedReader breader = null;
		HttpResponse response = null;
		try {
			StringBuffer url = new StringBuffer();
			url.append(RabbitmqInfo.HTTP)
				.append(RabbitmqInfo.IP_ADDR_1)
				.append(":")
				.append(RabbitmqInfo.PORT)
				.append(subStr);
			HttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url.toString());
			String up = RabbitmqInfo.USERNAME + ":" + RabbitmqInfo.PASSWORD;
			String credentials = Base64.encodeBase64String(up.getBytes("UTF-8"));
			httpGet.setHeader("Authorization", "Basic " + credentials);
			response = httpClient.execute(httpGet);
			breader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder responseString = new StringBuilder();
			String line = null;
			while ((line = breader.readLine()) != null) {
				responseString.append(line);
			}
			retMap.put(CodeUtil.RETURN_CODE, response.getStatusLine().getStatusCode());
			retMap.put(CodeUtil.RETURN_MSG, responseString.toString());
			return retMap;
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put(CodeUtil.RETURN_CODE, response.getStatusLine().getStatusCode());
			retMap.put(CodeUtil.RETURN_MSG, e.getMessage());
			return retMap;
		}finally {
			try {
				breader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
