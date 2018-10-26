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

public class Garkhttptest {
    static HttpClient httpClient = null;
    // 构造HttpClient的实例
    private static Garkhttptest instance;

    private Garkhttptest() {
        httpClient = new HttpClient();
    }

    public static Garkhttptest getInstance() {
        if (instance == null) {
            return new Garkhttptest();
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
            // 设置读数据超时时间(单位毫秒) 等待结果时间
            managerParams.setSoTimeout(1200000);
            // 执行postMethod，并返回状态码
            int statusCode = httpClient.executeMethod(postMethod);
            System.out.println("statusCode = " + statusCode);
            // HttpClient对于要求接受后继服务的请求，象POST和 PUT等不能自动处理转发
            if (statusCode == HttpStatus.SC_OK) {
                System.out.println("接口调用成功 ");
                if (postMethod.getResponseHeader("Content-Disposition") != null) {
                    System.out.println("成功返回 文件");
                    byte[] bytes = postMethod.getResponseBodyAsString().getBytes("utf-8");
                    // File file = new File("d://xxx.txt");
                    // FileUtils.writeByteArrayToFile(file, bytes);
                    return bytes;
                } else {
                    
                    byte[] bytes = postMethod.getResponseBodyAsString().getBytes("utf-8");
                    // File file = new File("d://xxx.txt");
                    // FileUtils.writeByteArrayToFile(file, bytes);
                    return bytes;
                }
            } else {
                System.out.println("接口返回失 败!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String args[]) {
//        Garkhttptest hcc = Garkhttptest.getInstance();
//        try {
//            StringBuilder urlBuilder1 = new StringBuilder();
//            urlBuilder1.append(
//                    "http://192.1.36.74:8080/drsp/services/resource/api/2c90405a51195d270151196707e50002.gxml?systemMark=true&ticket=d57641466ed05964a79464270fdabcee");
//            urlBuilder1.append("&xtmc=");
//            urlBuilder1.append(URLEncoder.encode("四川数据中心系统 ", "UTF-8"));
//            urlBuilder1.append("&ip=");
//            urlBuilder1.append(URLEncoder.encode("150.0.32.19", "UTF-8"));
//            urlBuilder1.append("&user=");
//            urlBuilder1.append(URLEncoder.encode("白茂军", "UTF-8"));
//            urlBuilder1.append("&fydm=");
//            urlBuilder1.append(URLEncoder.encode("510000", "UTF-8"));
//            hcc.postXmlMethod(urlBuilder1.toString(), buildRKKWebServiceXml2());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
    public static String buildRKKWebServiceXml2() throws IOException {
        // post参数内容放在了ga.xml文件中，可根据需要修改
        File contentFile = new File("d:\\当事人.xml");
        String fileContent = FileUtils.readFileToString(contentFile, "UTF-8");
        System.out.println(fileContent);
        return fileContent;
    }
}
