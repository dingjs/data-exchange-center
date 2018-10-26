package data.exchange.center.service.download;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FileDownload {

    public static final String LOCAL_PATH = "E:/";

    public static void main(String[] args) {
        // 待下载文件地址
        String fileUrl = "http://150.0.2.164/api/service-download/download?key=300000000000910_109&accessToken=8af1fc8ea58d11e798596c92bf0cbd5c";
        InputStream in = null;
        OutputStream out = null;
        HttpURLConnection conn = null;
        String fileName = "test.jpg";
        try {
            // 初始化连接
            URL url = new URL(fileUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // 读取数据
            if ( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                byte[] buffer = new byte[2048];
                in = conn.getInputStream();
                out = new FileOutputStream(new File(LOCAL_PATH, fileName));
                int count = 0;
                while ((count = in.read(buffer)) != -1) {
                    if ( count != 0 ) {
                        out.write(buffer, 0, count);
                    }
                    else {
                        break;
                    }
                }
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                out.close();
                in.close();
                conn.disconnect();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
