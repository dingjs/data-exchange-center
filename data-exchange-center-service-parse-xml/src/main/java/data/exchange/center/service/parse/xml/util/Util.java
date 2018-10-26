package data.exchange.center.service.parse.xml.util;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * 数据同步工具类
 * <p>Title: Util.java</p>
 * <p>Description: </p>
 * <p>Copyright:Copyright(c)2016</p>
 * <p>Company: 成都四方</p>
 * <p>CreateTime:2016年10月18日 上午10:45:03</p>
 * @author wenyuguang
 * @version 1.0
 *
 */
public class Util {
	
    /**
     * 
     * @function blob转string GBK
     * @author wenyuguang
     * @creaetime 2017年3月28日 下午4:40:39
     * @param blob
     * @return String
     * @throws SQLException
     * @throws UnsupportedEncodingException 
     */
    public static String BLOB2GBKString(Blob blob) throws SQLException, UnsupportedEncodingException{  
        byte[] inbyte=null;  
        if (blob != null) {  
            inbyte = blob.getBytes(1, (int) blob.length());  
        }  
        return new String (inbyte,"GBK");
    } 
    
    /**
     * 
     * @function blob转string 默认utf8
     * @author wenyuguang
     * @creaetime 2017年3月28日 下午4:40:39
     * @param blob
     * @return String
     * @throws SQLException
     * @throws UnsupportedEncodingException 
     */
    public static String BLOB2UTF8String(Blob blob) throws SQLException, UnsupportedEncodingException{  
        byte[] inbyte=null;  
        if (blob != null) {  
            inbyte = blob.getBytes(1, (int) blob.length());  
        }  
        return new String(inbyte,"UTF-8");
    }
}
