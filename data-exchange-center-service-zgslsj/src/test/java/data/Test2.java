package data;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.io.FileUtils;

public class Test2 {

    public static void main(String[] args) throws Exception {
        // 待下载文件地址
        File contentFile = new File("d:\\Ls.xml");
        String fileContent = FileUtils.readFileToString(contentFile, "UTF-8");
        String fileUrl = "http://150.0.2.164/api/service-slsj/getLsxx?xml=" + URLEncoder.encode(fileContent)
                + "&accessToken=";

        System.out.println(fileUrl);
        InputStream in = null;
        HttpURLConnection conn = null;
        try {
            // 初始化连接
            URL url = new URL(fileUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);

            // 读取数据
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                byte[] buff = new byte[2048];
                ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
                in = conn.getInputStream();
                int rc = 0;
                while ((rc = in.read(buff, 0, 100)) > 0) {
                    swapStream.write(buff, 0, rc);
                }
                byte[] in2b = swapStream.toByteArray();
                String s = new String(in2b);
                System.out.println(s);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
