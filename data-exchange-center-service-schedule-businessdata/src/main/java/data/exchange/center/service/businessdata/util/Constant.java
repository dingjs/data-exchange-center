package data.exchange.center.service.businessdata.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import oracle.sql.BLOB;

public class Constant {
	public final static int PAGE_NUM = 10000;
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
    public static String ajsbTo15(String ajbs){
		String toajbs = null;
		String ajlxCode;
		
		if(ajbs.length() == 19){
			ajlxCode = ajbs.substring(9, 12);
			if(ajlxCode.equals("189")){
				ajlxCode = "1";
			}else if (ajlxCode.equals("190")){
				ajlxCode = "2";
			}else if (ajlxCode.equals("191")){
				ajlxCode = "3";
			}else if (ajlxCode.equals("192")){
				ajlxCode = "4";
			}else if (ajlxCode.equals("194")){
				ajlxCode = "5";
			}else if (ajlxCode.equals("195")){
				ajlxCode = "6";
			}else if (ajlxCode.equals("196")){
				ajlxCode = "7";
			}else if (ajlxCode.equals("197")){
				ajlxCode = "8";
			}else{
				return "19";
			}
			//法院代码+“8”+年度+案件类型+案件类型+案件序号
			toajbs= ajbs.substring(1, 5)+"8"+ajbs.substring(7, 9)+ajlxCode+ajbs.substring(12, 19);
		}else if(ajbs.length() == 18) {
			ajlxCode = ajbs.substring(8, 12);
			if(ajlxCode.equals("1002")){
				ajlxCode = "1";
			}else if (ajlxCode.equals("1003")){
				ajlxCode = "2";
			}else if (ajlxCode.equals("1004")){
				ajlxCode = "3";
			}else if (ajlxCode.equals("1006")){
				ajlxCode = "4";
			}else if (ajlxCode.equals("1007")){
				ajlxCode = "5";
			}else if (ajlxCode.equals("1008")){
				ajlxCode = "6";
			}else if (ajlxCode.equals("1009")){
				ajlxCode = "7";
			}else if (ajlxCode.equals("1010")){
				ajlxCode = "8";
			}else{
				return "18";
			}
			toajbs = ajbs.substring(0,4)+"8"+ajbs.substring(6, 8)+ajlxCode+"8"+ajbs.substring(12, 18);	
		}
		return toajbs;
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
