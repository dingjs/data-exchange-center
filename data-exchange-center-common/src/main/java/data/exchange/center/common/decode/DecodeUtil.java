package data.exchange.center.common.decode;


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipInputStream;

import org.apache.commons.codec.binary.Base64;

import oracle.sql.BLOB;

/**
 * 
 * Description:
 * <p>
 * Company: xinya
 * </p>
 * <p>
 * Date:2018年4月26日 上午11:20:44
 * </p>
 * 
 * @author Tony
 * @version 1.0
 *
 */
public class DecodeUtil {

	/**
	 * 通达海方提供的解压缩blob方法 解压文件流
	 * 
	 * @author wenyuguang
	 * @creaetime 2016年10月18日 上午10:44:56
	 * @param in
	 *            输入流
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
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (zipIn != null)
					zipIn.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return baos.toByteArray();
	}

	/**
	 * 将blob转化为byte[],可以转化二进制流的
	 * 
	 * @param blob
	 * @return byte[]
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
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object toObject(byte[] bytes) {
		Object obj = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
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

	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static  List<Integer> decodeLowHexInt(String base64Str){ 
        List<Integer> arr = new ArrayList<Integer>();  
       // if(isEmptyStr(base64Str)) return arr; 
        byte[] data =  null; 
        try { 
        data = Base64.decodeBase64(base64Str.getBytes("UTF-8")); 
        } catch (UnsupportedEncodingException e) { 
        e.printStackTrace(); 
        } 
        StringBuilder hex = new StringBuilder(); 
        int i = 0 ; 
        for(byte d :data){ 
        hex.append(charToHex((char)d)); 
        i++; 
         
        if(i % 2 == 0){ 
        arr.add(ntohl(Integer.parseInt(hex.toString(),16))); 
        hex.delete(0, hex.length()); 
        } 
        } 
        return arr; 
        } 
    public static String charToHex(char c){
        String s = Integer.toHexString(c & 0xFF);
        if (s.length() == 1){
            return "0" + s;
        } else{
            return s;
        }
    }
    /**
     * 转小序
     * @param a
     * @return
     * @date 2013-2-21
     */
    public static int ntohl(int a){
        return ((a & 0xFF00) >> 8)|((a & 0x00FF) << 8);
    }
}
