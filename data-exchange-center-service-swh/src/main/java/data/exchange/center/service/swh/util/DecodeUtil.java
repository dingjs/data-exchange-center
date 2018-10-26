package data.exchange.center.service.swh.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang.StringUtils;

import oracle.sql.BLOB;

/**
 * @ClassName:  DecodeUtil
 * @Description:转换公共方法类
 * @author: BaiMaojun
 * @Date:   2018年4月24日 下午2:55:07
 */
public class DecodeUtil {

    /**
     * 通达海方提供的解压缩blob方法
     * 解压文件流
     * @author wenyuguang
     * @creaetime 2016年10月18日 上午10:44:56
     * @param in 输入流
     * @return 返回一个字节输出流
     */
    public static byte[] deCompress(InputStream in) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipInputStream zipIn = null;
        try {
            CheckedInputStream csumi = new CheckedInputStream(in, new CRC32());
            zipIn = new ZipInputStream(new BufferedInputStream(csumi));
            byte[] bytes = new byte[1024];
            while ((zipIn.getNextEntry()) != null) {
                int x;
                while ((x = zipIn.read(bytes)) != -1) {
                    baos.write(bytes, 0, x);
                }
            }
            csumi.close();
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        finally {
            try {
                if ( zipIn != null )
                    zipIn.close();
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return baos.toByteArray();
    }
    
    /** 
     * 将blob转化为byte[],可以转化二进制流的 
     *  
     * @param blob 
     * @return  byte[]
     */  
    public static byte[] blobToBytes(BLOB blob) {  
        InputStream is = null;  
        byte[] b = null;  
        try {  
            is = blob.getBinaryStream();  
            b = new byte[(int) blob.length()];  
            is.read(b);  
            return b;  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                is.close();  
                is = null;  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return b;  
    }  
    
    /**  
     * 数组转对象  
     * @param bytes  
     * @return  
     */  
    public static Object toObject(byte[] bytes){      
        Object obj = null;      
        try {        
            ByteArrayInputStream bis = new ByteArrayInputStream (bytes);        
            ObjectInputStream ois = new ObjectInputStream (bis);        
            obj = ois.readObject();      
            ois.close();   
            bis.close();   
        } catch (IOException ex) {        
            ex.printStackTrace();   
        } catch (ClassNotFoundException ex) {        
            ex.printStackTrace();   
        }      
        return obj;    
    }
   public static String getUUID(){
    return  UUID.randomUUID().toString().replaceAll("-", "");
   }
   
   
   public static Integer srcToInt(String src){
       if(StringUtils.isBlank(src)){
           return null; 
       }
       return Integer.valueOf(src);
   }
   public static BigDecimal srcToBgd(String src){
       if(StringUtils.isBlank(src)){
           return null; 
       }
       BigDecimal bd=new BigDecimal(src);
       return bd;
   }

   /**
    * 将短时间格式字符串转换为时间 yyyy-MM-dd
    * 
    * @param strDate
    * @return
    */
   public static Date strToDate(String strDate) {
       if(StringUtils.isBlank(strDate)){
           return null;
       }
       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
       ParsePosition pos = new ParsePosition(0);
       Date strtodate = formatter.parse(strDate, pos);
       return strtodate;
   }

   /**
    * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
    * 
    * @param strDate
    * @return
    */
   public static Date strToDateLong(String strDate) {
       if(StringUtils.isBlank(strDate)){
           return null;
       }
       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       ParsePosition pos = new ParsePosition(0);
       Date strtodate = formatter.parse(strDate, pos);
       return strtodate;
   }
   
   public static byte[] strToByte(String strDate) {
       if (StringUtils.isBlank(strDate)){
           return null; 
       }
       return strDate.getBytes();
   }
}
