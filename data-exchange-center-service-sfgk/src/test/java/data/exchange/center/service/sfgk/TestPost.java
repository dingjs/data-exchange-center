package data.exchange.center.service.sfgk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import data.exchange.center.service.sfgk.util.rsa.RSAUtils;
import net.sf.json.JSONObject;

public class TestPost {

	public static void main(String[] args) throws Exception {
		String url = "http://118.122.95.223:8888/api/service-sfgk/scsfgk";
//		String url = "http://127.0.0.1:9324/scsfgk";
		String param = "WGmzlMagPH8nlXi6xy9zMCLZATceJ1dhlMZhm7fnjUphbgwrK0iLQUb1DxEQO5Xr2pLTDtX/uengq5Ngtaq/ChMgTMAUcdD6FeT2Hm559D4JhWFYXkTGmzDtbwf56oAl5SRR8dkLFIxo6YPasiqIQTLnOe8KDhIk3gHeZk0Gnd4=";
//		OutputStreamWriter out = null;
//		BufferedReader in = null;
//		String result = "";
//		try {
//			URL realUrl = new URL(url);
//			URLConnection conn = realUrl.openConnection();
//			conn.setRequestProperty("accept", "*/*");
//			conn.setRequestProperty("connection", "Keep-Alive");
//			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//			conn.setDoOutput(true);
//			conn.setDoInput(true);
//			// out = new PrintWriter(conn.getOutputStream());
//			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
//			out.write(param);
//			out.flush();
//			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//			String line;
//			while ((line = in.readLine()) != null) {
//				result += line;
//			}
//		} catch (Exception e) {
//			System.out.println("发送 POST 请求出现异常！" + e);
//			e.printStackTrace();
//		} finally {
//			try {
//				if (out != null) {
//					out.close();
//				}
//				if (in != null) {
//					in.close();
//				}
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
//		}
//		System.out.println(result);
//
//		try {
//			HttpClient httpClient = HttpClients.createDefault();
//			HttpPost httpPost = new HttpPost(url);
//			httpPost.setEntity(new StringEntity(param, Charset.forName("UTF-8")));
//			HttpResponse response = httpClient.execute(httpPost);
//			BufferedReader breader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//			StringBuilder responseString = new StringBuilder();
//			String line = null;
//			while ((line = breader.readLine()) != null) {
//				responseString.append(line);
//			}
//			breader.close();
//			String repsonseStr = responseString.toString();
//			int statusCode = response.getStatusLine().getStatusCode();
//			System.out.println(repsonseStr);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		String end = HttpClientUtil.post(url, null);
		System.out.println(end);
		JSONObject jsObj = JSONObject.fromObject(end);
		byte s[] = RSAUtils.decrypt(RSAUtils.getPublicKeyByStr(RSAUtils.QGSPLCGK_PUBLIC),
				Base64.decodeBase64(jsObj.get("DATA").toString().getBytes("UTF-8")));
		System.out.println(new String(s));
	}
}
