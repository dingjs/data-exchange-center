package data.exchange.center.service.parse.ftpzip;

import org.apache.commons.net.util.Base64;

public class TestBase64 {

	public static void main(String[] args) {
		byte[] base = Base64.decodeBase64("\r\n" + 
				"PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiID8+PHJlc3VsdD48Y29kZT4wPC9jb2RlPjxtZXNzYWdlPuivt+axguexu+Wei+mdnuazlTwvbWVzc2FnZT48L3Jlc3VsdD4=\r\n" + 
				"");
		System.out.println(new String(base));
	}
	
	public static byte[] decode(final byte[] bytes) {  
        return Base64.decodeBase64(bytes);  
    }
  
    /** 
     * 二进制数据编码为BASE64字符串 
     * 
     * @param bytes 
     * @return 
     * @throws Exception 
     */  
    public static String encode(final byte[] bytes) {  
        return new String(Base64.encodeBase64(bytes));  
    }  
}
