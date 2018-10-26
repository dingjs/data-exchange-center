package data.exchange.center.service.businessdata.util;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

/**
 * 列出FTP服务器上指定目录下面的所有文件
 */
public class FTPListAllFiles {
    private static Logger logger = Logger.getLogger(FTPListAllFiles.class);
    public FTPClient ftp;
    public ArrayList<Map<String, String>> arFiles;

    /**
     * 重载构造函数
     * 
     * @param isPrintCommmand
     *            是否打印与FTPServer的交互命令
     */
    public FTPListAllFiles(boolean isPrintCommmand) {
        ftp = new FTPClient();
        arFiles = new ArrayList<Map<String, String>>();
        if (isPrintCommmand) {
            ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        }
    }

    /**
     * 登陆FTP服务器
     * 
     * @param host
     *            FTPServer IP地址
     * @param port
     *            FTPServer 端口
     * @param username
     *            FTPServer 登陆用户名
     * @param password
     *            FTPServer 登陆密码
     * @return 是否登录成功
     * @throws IOException
     */
    public boolean login(String host, int port, String username, String password) throws IOException {
        this.ftp.connect(host, port);
        if (FTPReply.isPositiveCompletion(this.ftp.getReplyCode())) {
            if (this.ftp.login(username, password)) {
                this.ftp.setControlEncoding("GBK");
                return true;
            }
        }
        if (this.ftp.isConnected()) {
            this.ftp.disconnect();
        }
        return false;
    }

    /**
     * 关闭数据链接
     * 
     * @throws IOException
     */
    public void disConnection() throws IOException {
        if (this.ftp.isConnected()) {
            this.ftp.disconnect();
        }
    }

    /**
     * 递归遍历出目录下面所有文件
     * 
     * @param pathName
     *            需要遍历的目录，必须以"/"开始和结束
     * @throws IOException
     */
    public void List(String pathName) throws IOException {
        if (pathName.startsWith("/") && pathName.endsWith("/")) {
            String directory = pathName;
            // 更换目录到当前目录
            this.ftp.changeWorkingDirectory(directory);
            FTPFile[] files = this.ftp.listFiles();
            for (FTPFile file : files) {
                if (file.isFile()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("path", directory);
                    map.put("fileName", file.getName());
                    arFiles.add(map);
                } else if (file.isDirectory()) {
                    List(directory + file.getName() + "/");
                }
            }
        }
    }

    /**
     * 递归遍历目录下面指定的文件名
     * 
     * @param pathName
     *            需要遍历的目录，必须以"/"开始和结束
     * @param ext
     *            文件的扩展名
     * @throws IOException
     */
    public void List(String pathName, String ext) throws IOException {
        if (pathName.startsWith("/") && pathName.endsWith("/")) {
            String directory = pathName;
            // 更换目录到当前目录
            this.ftp.changeWorkingDirectory(directory);
            FTPFile[] files = this.ftp.listFiles();
            for (FTPFile file : files) {
                if (file.isFile()) {
                    if (file.getName().endsWith(ext)) {
                        Map<String, String> map = new HashMap<>();
                        map.put("path", directory);
                        map.put("fileName", file.getName());
                        arFiles.add(map);
                    }
                } else if (file.isDirectory()) {
                    List(directory + file.getName() + "/", ext);
                }
            }
        }
    }

    public static Iterator<Map<String, String>> getZipPath(String ip,int port,String user,String password,String ftpPath) throws IOException {
        FTPListAllFiles f = new FTPListAllFiles(true);
        if (f.login(ip, port, user, password)) {
            f.List(ftpPath, "zip");
        }
        f.disConnection();
        Iterator<Map<String, String>> it = f.arFiles.iterator();
        return it;
    }

    public static void main(String[] args) throws IOException {
        FTPListAllFiles f = new FTPListAllFiles(true);
        if (f.login("150.0.2.163", 21, "fayi", "fayi")) {
            f.List("/2018-06-04/", "zip");
        }
        f.disConnection();
        Iterator<Map<String, String>> it = f.arFiles.iterator();
        while (it.hasNext()) {
            Map<String, String> map = it.next();
            
            logger.info(map.get("path"));
            logger.info(map.get("fileName"));
        }

    }
}
