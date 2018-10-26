package data.exchange.center.service.zgslsj.service.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class JgHttpClientCenter {
    static HttpClient jgHttpClientCenter = null;

    // 构造HttpClient的实例
    private static JgHttpClientCenter instance;

    private JgHttpClientCenter() {
        jgHttpClientCenter = new HttpClient();
    }

    public static JgHttpClientCenter getInstance() {
        if (instance == null) {
            return new JgHttpClientCenter();
        }
        return instance;
    }
    public static void main(String args[]) {
        try {
            // post方式请求组织机构数据
//            StringBuilder urlBuilder = new StringBuilder();
//            urlBuilder.append("服务地址");
//            urlBuilder.append("?systemMark=true&ticket=9b5bccf4930f963d8a201fa958f78d76&startPage=1");
//            Map<String, String> map = new HashMap<String, String>();
//            map.put("jgmc", "四川省高级人民法院");
//            postXmlMethod(urlBuilder.toString(), map);
            // get方式请求组织机构数据
            StringBuilder urlBuilder1 = new StringBuilder();
            //urlBuilder1.append("服务地址");
            urlBuilder1.append("http://192.1.36.74:8080/drsp/services/resource/api/2c9040125457e10401545816181b00e6.gxml?systemMark=true&ticket=831e4b4362f50172993c274f061ff722&startPage=1");
            urlBuilder1.append("&jgmc=");
            urlBuilder1.append(URLEncoder.encode("四川省高级人民法院", "UTF-8"));
            getMethod(urlBuilder1.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  static byte[] getMethod(String url) {
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        try {
            HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
            managerParams.setConnectionTimeout(300000);
            managerParams.setSoTimeout(1200000);
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode == HttpStatus.SC_OK) {
                return getMethod.getResponseBodyAsString().getBytes("utf-8");
//                System.out.println(getMethod.getResponseBodyAsString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            getMethod.releaseConnection();
        }
        return null;
    }

    public static void postXmlMethod(String url, Map<String, String> map) {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        String param = buildResourceShareXmlParam(map, "1");
        try {
            postMethod.setRequestEntity(new StringRequestEntity(param, "text/xml", "UTF-8"));
            HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
            managerParams.setConnectionTimeout(30000);
            managerParams.setSoTimeout(1200000);
            int statusCode = httpClient.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
                System.out.println(postMethod.getResponseBodyAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }
    }

    private static String buildResourceShareXmlParam(Map<String, String> map, String type) {
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding("UTF-8");
        Element rootElememt = document.addElement("Condition");
        rootElememt.addAttribute("type", type);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            rootElememt.addElement(entry.getKey()).addText(entry.getValue());
        }
        return document.asXML();
    }

}
