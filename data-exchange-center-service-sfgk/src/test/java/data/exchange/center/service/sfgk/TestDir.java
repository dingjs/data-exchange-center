package data.exchange.center.service.sfgk;

import java.io.File;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

public class TestDir {

	public static void main(String[] args) throws IOException {
		File file = new File("E:\\test\\tt");
		File list[] = file.listFiles();
		for(File f:list) {
			
//			System.out.println(f.getCanonicalPath());
		}
//		System.out.println(file.getCanonicalPath());
		
		
		String str = new String(Base64.decodeBase64("RNF2YYDhW9F8vYocLKcSopw/D2aa4HN/OC0OVtHNY6rRS39rBjsDw7fDVFLw9zoBD15K2zMJwiXQt0uf652Wh6DMJ8XWOtPJ4k7SU9xc5EAGy2DRfs0CKoanG53dKwepG4U6zSctv/Kap5gO1FvZ24GAJ3MQeVrgeGjyKEOpNeo="));
		System.out.println(str);
	}
}
