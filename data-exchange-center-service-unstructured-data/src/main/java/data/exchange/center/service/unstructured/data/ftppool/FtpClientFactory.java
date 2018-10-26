package data.exchange.center.service.unstructured.data.ftppool;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool.PoolableObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import data.exchange.center.service.unstructured.data.domain.FTPClientInfoConfigure;

/**
 * Description:FTPClient工厂类，通过FTPClient工厂提供FTPClient实例的创建和销毁
 * <p>Company: pelox </p>
 * <p>Date:2017年5月5日 下午6:09:55</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
public class FtpClientFactory implements PoolableObjectFactory<FTPClient> {
    
    private static Logger logger = LoggerFactory.getLogger(FtpClientFactory.class);
    
    private FTPClientInfoConfigure config;

    public final int poolSize;
    /**
     * 给工厂传入一个参数对象，方便配置FTPClient的相关参数
     * @param config
     */
    public FtpClientFactory(FTPClientInfoConfigure config) {
        this.config = config;
        this.poolSize = Integer.parseInt(config.getPOOLSIZE());
    }

    /**
     * (non-Javadoc)
     * @see org.apache.commons.pool.PoolableObjectFactory#makeObject()
     */
    public FTPClient makeObject() throws Exception {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(Integer.parseInt(config.getCLIENTTIMEOUT()));
        try {
            ftpClient.connect(config.getHOST(), Integer.parseInt(config.getPORT()));
            int reply = ftpClient.getReplyCode();
            if ( !FTPReply.isPositiveCompletion(reply) ) {
                ftpClient.disconnect();
                throw new Exception("FTPServer refused connection: host:" + config.getHOST() + ",port:" + config.getPORT());
            }
            boolean result = ftpClient.login(config.getUSERNAME(), config.getPASSWORD());
            if(!result){
                logger.error("ftpClient登陆失败! userName:" + config.getUSERNAME() + " ; password:" + config.getPASSWORD());
                throw new Exception("ftpClient登陆失败! userName:" + config.getUSERNAME() + " ; password:" + config.getPASSWORD());
            }
            ftpClient.setFileType(Integer.parseInt(config.getTRANSFERFILETYPE()));
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding(config.getENCODING());
            if(config.getLOCALPASSIVEMODE().equals("1")){
                ftpClient.enterLocalPassiveMode();
            }
        }
        catch(IOException e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new Exception(e.getMessage());
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
