package data.exchange.center.service.sefon.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

public class Test {
	public static void main(String[] args) {

	String	leaf_text="AQACAAUA";

		System.out.println(decodeLowHexInt(leaf_text));
	}
	public static  List<Integer> decodeLowHexInt(String base64Str){ 
		List<Integer> arr = new ArrayList<Integer>();  
		if(CommUtils.isEmptyStr(base64Str)) return arr; 
		byte[] data =  null; 
		try { 
		data = Base64.decodeBase64(CommUtils.trim(base64Str).getBytes("UTF-8")); 
		} catch (UnsupportedEncodingException e) { 
		e.printStackTrace(); 
		} 
		StringBuilder hex = new StringBuilder(); 
		int i = 0 ; 
		for(byte d :data){ 
		hex.append(CommUtils.charToHex((char)d)); 
		i++; 
		 
		if(i % 2 == 0){ 
		arr.add(CommUtils.ntohl(Integer.parseInt(hex.toString(),16))); 
		hex.delete(0, hex.length()); 
		} 
		} 
		return arr; 
		} 
}
