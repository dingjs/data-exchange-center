package data.exchange.center.service.businessdata;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

//    public static void main(String[] args) throws Exception {
//        // 待下载文件地址
//        String fileUrl = "http://150.0.8.103:81/7391effada7c4a0e9bc31936ab5d9b0a/Topics/33dfededd93d45fd9b73ba536fecbd75/案件讨论.doc";
//		HttpClient httpClient = HttpClients.createDefault();
//		HttpGet httpGet = new HttpGet(fileUrl);
//		HttpResponse response = httpClient.execute(httpGet);
//        InputStream in = response.getEntity().getContent();
//        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
//        int count = 0;
//        byte[] buffer = new byte[2048];
//        while ((count = in.read(buffer)) != -1) {
//            if ( count != 0 ) {
//                swapStream.write(buffer, 0, count);
//            }
//            else {
//                break;
//            }
//        }
//        byte[] in2b = swapStream.toByteArray();
//        System.out.println(in2b);
//        swapStream.close();
//        in.close();
//    }
    public static void main(String[] args) throws Exception {
      // 待下载文件地址
      String fileUrl = "http://150.101.64.35:8902/v1/LoadFile?Record=1test.doc";
      InputStream in = null;
      OutputStream out = null;
      HttpURLConnection conn = null;
      String fileName = "test.doc";
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
    //outputStream转inputStream
    public static ByteArrayInputStream parse(OutputStream out) throws Exception
    {
        ByteArrayOutputStream   baos=new   ByteArrayOutputStream();
        baos=(ByteArrayOutputStream) out;
        ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        return swapStream;
    }
    
//    public static void main(String[] args) {
//        // 待下载文件地址
//        String fileUrl = "http://150.0.32.85:8080/JxjsInterface/JxjsFtpServlet?path=3000/201708/1/168082035df495a2015e280e62ac0ada/archives/&name=168082035e367182015e3b870821068c.doc";
//        InputStream in = null;
//        OutputStream out = null;
//        HttpURLConnection conn = null;
//        String fileName = "test.doc";
//        try {
//            // 初始化连接
//            URL url = new URL(fileUrl);
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//            // 读取数据
//            if ( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
//                byte[] buffer = new byte[2048];
//                in = conn.getInputStream();
//                out = new FileOutputStream(new File(LOCAL_PATH, fileName));
//                int count = 0;
//                while ((count = in.read(buffer)) != -1) {
//                    if ( count != 0 ) {
//                        out.write(buffer, 0, count);
//                    }
//                    else {
//                        break;
//                    }
//                }
//            }
//        }
//        catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        finally {
//            try {
//                out.close();
//                in.close();
//                conn.disconnect();
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
