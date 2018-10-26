package data.exchange.center.service.parse.ftpzip.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

/**
 * 
 * <p>Title: ObjectTranscoder.java</p>
 * <p>Description: </p>
 * <p>Copyright:Copyright(c) 2017</p>
 * <p>Company: pelox </p>
 * <p>CreateTime:2017年3月12日 上午1:24:48</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class SerializationUtil {
    
    protected static Logger logger = Logger.getLogger(SerializationUtil.class);
    
    /**
     * 
     * @function 序列化
     * @author wenyuguang
     * @creaetime 2017年3月12日 上午1:36:25
     * @param value
     * @return
     */
    public static byte[] serialize(Object value) {
        if ( value == null ) {
            logger.error("Can't serialize null");
            throw new NullPointerException("Can't serialize null");
        }
        byte[] rv = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            os.close();
            bos.close();
            rv = bos.toByteArray();
        }
        catch (IOException e) {
            logger.error("Non-serializable object",e);
            throw new IllegalArgumentException("Non-serializable object", e);
        }
        finally {
            try {
                if ( os != null )
                    os.close();
                if ( bos != null )
                    bos.close();
            }
            catch (Exception e2) {
                logger.error(e2.getMessage());
                e2.printStackTrace();
            }
        }
        return rv;
    }

    /**
     * 
     * @function 反序列化
     * @author wenyuguang
     * @creaetime 2017年3月12日 上午1:36:36
     * @param in
     * @return
     */
    public static Object deserialize(byte[] in) {
        Object rv = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if ( in != null ) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                rv = is.readObject();
                is.close();
                bis.close();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            try {
                if ( is != null )
                    is.close();
                if ( bis != null )
                    bis.close();
            }
            catch (Exception e2) {
                logger.error(e2.getMessage());
                e2.printStackTrace();
            }
        }
        return rv;
    }
}
