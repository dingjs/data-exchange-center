package data.exchange.center.service.unstructured.data;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class TestFtpEncode {

	public static void main(String[] args) {
		final FTPClient higherPeoplesCourtFtpClient = new FTPClient();
        int reply;
        try {
            /**
             * ftp服务器采用的UTF-8编码格式
             */
//        	higherPeoplesCourtFtpClient.setControlEncoding("UTF-8");
        	higherPeoplesCourtFtpClient.setControlEncoding("GBK");
            higherPeoplesCourtFtpClient.connect("150.31.101.7", 21);
            higherPeoplesCourtFtpClient.login("ABBYYFTP", "123") ;
            /**
             * 被动模式
             */
            higherPeoplesCourtFtpClient.enterLocalPassiveMode();
            /**
             * 230 表示登录成功
             */
            reply = higherPeoplesCourtFtpClient.getReplyCode() ;
            if (!FTPReply.isPositiveCompletion(reply)) {
                higherPeoplesCourtFtpClient.disconnect();
            }
            higherPeoplesCourtFtpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            higherPeoplesCourtFtpClient.changeWorkingDirectory("/2018/21/303600000023427/");
            FTPFile[] files = higherPeoplesCourtFtpClient.listFiles();
            System.out.println(files);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if (higherPeoplesCourtFtpClient.isConnected()) {
                try {
                    higherPeoplesCourtFtpClient.logout();
                    higherPeoplesCourtFtpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
	}
}
