package data.exchange.center.service.unstructured.node.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.UUID;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipInputStream;

import oracle.sql.BLOB;

/**
 * Description:
 * <p>Company: pelox </p>
 * <p>Date:2017年6月1日 下午5:18:16</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
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
}
