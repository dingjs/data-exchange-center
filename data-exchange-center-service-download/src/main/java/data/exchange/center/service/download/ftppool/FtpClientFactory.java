package data.exchange.center.service.download.ftppool;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool.PoolableObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月12日 下午5:06:24</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class FtpClientFactory implements PoolableObjectFactory<FTPClient> {
    
    private static Logger logger = LoggerFactory.getLogger(FtpClientFactory.class);
    
    public final int poolSize = 1;

    /**
     * (non-Javadoc)
     * @see org.apache.commons.pool.PoolableObjectFactory#makeObject()
     */
    public FTPClient makeObject() throws Exception {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(10000);
        try {
            ftpClient.connect("150.0.2.70", 21);
            int reply = ftpClient.getReplyCode();
            if ( !FTPReply.isPositiveCompletion(reply) ) {
                ftpClient.disconnect();
                throw new Exception("FTPServer refused connection: host:150.0.2.70,port:21");
            }
            boolean result = ftpClient.login("dcbase", "dcbase");
            if(!result){
                logger.error("ftpClient登陆失败! userName:" + "dcbase" );
            }
            ftpClient.setFileType(2);
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("GBK");
          	ftpClient.enterLocalPassiveMode();
        }
        catch(IOException e){
            logger.error(e.getMessage());
        }
        catch(Exception e){
            logger.error(e.getMessage());
        }
        return ftpClient;
    }

    /**
     * (non-Javadoc)
     * @see org.apache.commons.pool.PoolableObjectFactory#destroyObject(java.lang.Object)
     */
    public void destroyObject(FTPClient ftpClient) throws Exception {
        try {
            if(ftpClient != null && ftpClient.isConnected()) {
                ftpClient.logout();
            }
        }
        catch (IOException io) {
            io.printStackTrace();
            logger.error(io.getMessage());
        }
        finally {
            // 注意,一定要在finally代码中断开连接，否则会导致占用ftp连接情况
            try {
                ftpClient.disconnect();
            }
            catch (IOException io) {
                io.printStackTrace();
                logger.error(io.getMessage());
            }
        }
    }

    /**
     * (non-Javadoc)
     * @see org.apache.commons.pool.PoolableObjectFactory#validateObject(java.lang.Object)
     */
    public boolean validateObject(FTPClient ftpClient) {
        try {
            return ftpClient.sendNoOp();
        }
        catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Failed to validate client: " + e, e);
        }
    }

    public void activateObject(FTPClient ftpClient) throws Exception {
    }

    public void passivateObject(FTPClient ftpClient) throws Exception {

    }

}
