package rsa;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import data.exchange.center.service.sfgk.util.rsa.RSAUtils;
import sun.misc.BASE64Decoder;

public class RSACryptography {

	public static String data = "hello world";
	public static String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCflZaYF13v52SGYV2qyXeVfEAqy0RzH/8d3MTPuSqw4nAD1DSpqwU5g6Tq7Krf276exTmt7+ZpAYz0rDuxLN6z79XOlwXC/hNwcUXFbxNJJM6igxWSevt9NrQstKy0TmZnOvjpXmTcQzXpOwhGxtvBAWDcEylB+KviH2IsVDPtsQIDAQAB";
	public static String privateKeyString = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIhIs/3wz/nod7Ff/0UMzyK4gRCjPLqSfYkxxtlLn8GEn5Tg9kgKEl+CBiVad3w1afgFivaTHHI7xCC9zyulFkKQ3Q5IuouBkaY2+hKUPDzRRer3RmxUcNM4e5IUfDwG//8Hh69Q0kEHyD22lXGvo/kQnoUyhH+RjZ1UVAJAzj7lAgMBAAECgYAVh26vsggY0Yl/Asw/qztZn837w93HF3cvYiaokxLErl/LVBJz5OtsHQ09f2IaxBFedfmy5CB9R0W/aly851JxrI8WAkx2W2FNllzhha01fmlNlOSumoiRF++JcbsAjDcrcIiR8eSVNuB6ymBCrx/FqhdX3+t/VUbSAFXYT9tsgQJBALsHurnovZS1qjCTl6pkNS0V5qio88SzYP7lzgq0eYGlvfupdlLX8/MrSdi4DherMTcutUcaTzgQU20uAI0EMyECQQC6il1Kdkw8Peeb0JZMHbs+cMCsbGATiAt4pfo1b/i9/BO0QnRgDqYcjt3J9Ux22dPYbDpDtMjMRNrAKFb4BJdFAkBMrdWTZOVc88IL2mcC98SJcII5wdL3YSeyOZto7icmzUH/zLFzM5CTsLq8/HDiqVArNJ4jwZia/q6Fg6e8KO2hAkB0EK1VLF/ox7e5GkK533Hmuu8XGWN6I5bHnbYd06qYQyTbbtHMBrFSaY4UH91Qwd3u9gAWqoCZoGnfT/o03V5lAkBqq8jZd2lHifey+9cf1hsHD5WQbjJKPPIb57CK08hn7vUlX5ePJ02Q8AhdZKETaW+EsqJWpNgsu5wPqsy2UynO";
	public static final BouncyCastleProvider bouncyCastleProvider = new BouncyCastleProvider();

	public static void main(String[] args) throws Exception {

		Security.addProvider(bouncyCastleProvider);

		// 获取公钥
		PublicKey publicKey = getPublicKey(publicKeyString);

		// 获取私钥
		PrivateKey privateKey = getPrivateKey(publicKeyString);

		// 公钥加密
		// byte[] encryptedBytes=encrypt(data.getBytes(), publicKey);
		// System.out.println("加密后："+new String(encryptedBytes));

		byte[] b = Base64.getDecoder().decode(
				"RNF2YYDhW9F8vYocLKcSopw/D2aa4HN/OC0OVtHNY6rRS39rBjsDw7fDVFLw9zoBD15K2zMJwiXQt0uf652Wh6DMJ8XWOtPJ4k7SU9xc5EAGy2DRfs0CKoanG53dKwepG4U6zSctv/Kap5gO1FvZ24GAJ3MQeVrgeGjyKEOpNeo="
						.getBytes("UTF-8"));
		// 私钥解密
		byte[] decryptedBytes = decrypt(b, privateKey);
		System.out.println("解密后：" + new String(decryptedBytes));
	}

	// 将base64编码后的公钥字符串转成PublicKey实例
	public static PublicKey getPublicKey(String publicKey) throws Exception {

		try {
			byte[] keyBytes = Base64.getDecoder().decode(publicKey.getBytes());
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Hex.decodeHex(publicKey.toCharArray()));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA", bouncyCastleProvider);
			return keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			byte[] keyBytes;
			PublicKey publicKey1 = null;
			try {
				keyBytes = (new BASE64Decoder()).decodeBuffer(publicKey);
				X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");
				publicKey1 = keyFactory.generatePublic(keySpec);
				return publicKey1;
			} catch (Exception e1) {
				e.printStackTrace();
			}
		}
		return null;
	}

	// 将base64编码后的私钥字符串转成PrivateKey实例
	public static PrivateKey getPrivateKey(String privateKey) throws Exception {
		byte[] keyBytes = Base64.getDecoder().decode(privateKey.getBytes());
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Hex.decodeHex(privateKey.toCharArray()));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA", bouncyCastleProvider);
		return keyFactory.generatePrivate(keySpec);
	}

	// 公钥加密
	public static byte[] encrypt(byte[] content, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA", bouncyCastleProvider);// java默认"RSA"="RSA/ECB/PKCS1Padding"
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(content);
	}

	// 私钥解密
	public static byte[] decrypt(byte[] content, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA", bouncyCastleProvider);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(content);
	}

}