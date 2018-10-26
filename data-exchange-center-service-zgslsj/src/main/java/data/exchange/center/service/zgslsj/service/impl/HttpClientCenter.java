package data.exchange.center.service.zgslsj.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.io.FileUtils;

public class HttpClientCenter {
    static HttpClient httpClient = null;

    // 构造HttpClient的实例
    private static HttpClientCenter instance;

    private HttpClientCenter() {
        httpClient = new HttpClient();
    }

    public static HttpClientCenter getInstance() {
        if (instance == null) {
            return new HttpClientCenter();
        }
        return instance;
    }

    public byte[] postXmlMethod(String url, String content) throws UnsupportedEncodingException, IOException {
        PostMethod postMethod = new PostMethod(url);

        // 可使用XML格式传输post参数
        postMethod.setRequestEntity(new StringRequestEntity(content, "text/xml", "UTF-8"));
        try {
            HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
            // 设置连接超时时间(单位毫秒)
            managerParams.setConnectionTimeout(30000);
            // 设置读数据超时时间(单位毫秒)
            managerParams.setSoTimeout(120000);
            // 执行postMethod，并返回状态码
            int statusCode = httpClient.executeMethod(postMethod);
            System.out.println("statusCode = " + statusCode);
            // HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
            if (statusCode == HttpStatus.SC_OK) {
                System.out.println("接口调用成功");
                if (postMethod.getResponseHeader("Content-Disposition") != null) {
                    System.out.println("成功返回文 件");
                    byte[] bytes = postMethod.getResponseBodyAsString().getBytes("utf-8");
                    return bytes;
//                    File file = new File("d://test.xml");
//                    FileUtils.writeByteArrayToFile(file, bytes);
                } else {
                    return postMethod.getResponseBodyAsString().getBytes("utf-8");
//                    System.out.println(postMethod.getResponseBodyAsString());
                }
            } else {
                System.out.println("接口返回失败!");
                return "接口返回失败!".getBytes();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage().getBytes();
        }
        
    }
    
    public static void main(String args[]) {
        HttpClientCenter hcc = HttpClientCenter.getInstance();
        try {
            String url = "http://192.1.36.74:8080/drsp/services/resource/api/4028b281562f551e01562f562aac0002.gxml?systemMark=true&ticket=bee2b96b7ef04fe805761ad6e4d2f4e9";
            File contentFile = new File("d:\\Ls.xml");
            String fileContent = FileUtils.readFileToString(contentFile, "UTF-8");
            hcc.postXmlMethod(url, fileContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
