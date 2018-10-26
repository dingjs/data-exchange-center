package data.exchange.center.service.swh.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import org.dom4j.Attribute;

public class Constant {
    
    public static String  srcToBase64(String text){
        final Base64.Encoder encoder = Base64.getEncoder();
        try {
            byte[] textByte = text.getBytes("UTF-8");
            text = encoder.encodeToString(textByte);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return text;
        
    }
    public static String  base64Tosrc(String text){
        if(text == null){
            return null;  
        }
        final Base64.Decoder decoder = Base64.getDecoder();
        try {
            text = new String(decoder.decode(text), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return text;
    }
    
    public static String  elBase64Tosrc(Attribute attribute){
        if(attribute == null){
            return null;  
        }
        final Base64.Decoder decoder = Base64.getDecoder();
        String text = null;
        try {
            text = new String(decoder.decode(attribute.getText()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return text;
    }
	 
    /**
     * 获取服务器时间（“yyyy-MM-dd HH:mm:ss,SSS”格式的字符串）
     * @return now time yyyy-MM-dd HH:mm:ss,SSS
     */
    public static String getServerTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String servTime = sdf.format(new Date());
        sdf =null;
        return servTime;
    }
    public static String getUUID(){
        return  UUID.randomUUID().toString().replaceAll("-", "");
       }
}
