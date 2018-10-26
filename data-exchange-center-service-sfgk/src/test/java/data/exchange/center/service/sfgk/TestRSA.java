package data.exchange.center.service.sfgk;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import data.exchange.center.service.sfgk.util.Code;

public class TestRSA {

	public static void main(String[] args) throws Exception {
		String data = "PARAMS:{\r\n" + 
				"    YWLX:TESTUSABILITY,\r\n" + 
				"    FYBH: 100,\r\n" + 
				"    VERSION: 1.3\r\n" + 
				"}";
//		byte[] encryptedBytes = Code.encrypt(data.toString().getBytes(), Code.getPublicKey(Code.publicKey));
//		System.out.println(Base64.encodeBase64String(encryptedBytes));
		
		
		
		String base = "RNF2YYDhW9F8vYocLKcSopw/D2aa4HN/OC0OVtHNY6rRS39rBjsDw7fDVFLw9zoBD15K2zMJwiXQt0uf652Wh6DMJ8XWOtPJ4k7SU9xc5EAGy2DRfs0CKoanG53dKwepG4U6zSctv/Kap5gO1FvZ24GAJ3MQeVrgeGjyKEOpNeo=";
		byte[] decodeBase = Base64.decodeBase64(base);
		String s = new String (Code.decrypt(decodeBase, Code.getPrivateKey(Code.privateKey)));
		System.out.println(s);
		
	}
}
