package data.exchange.center.service.sfgk.util.rsa;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

public class Test {
	static String subData = "RNF2YYDhW9F8vYocLKcSopw/D2aa4HN/OC0OVtHNY6rRS39rBjsDw7fDVFLw9zoBD15K2zMJwiXQt0uf652Wh6DMJ8XWOtPJ4k7SU9xc5EAGy2DRfs0CKoanG53dKwepG4U6zSctv/Kap5gO1FvZ24GAJ3MQeVrgeGjyKEOpNeo=";

	static String data = "LKgYhi847SuZ4kmp89MM9j9XLseU+dupJqGGBXpGdlNesbLc6JOYL6phSsme3hBZpMukQV3NJ2S5bXthmxx7w3x3+5xpSOzlqxFjqi8f177JeDoiTvSl6ZsIiz7VrUjgeZZ7xQZ0zQMaL0FCmyvlnolG1uqroROm5jsKMl8w51oFhd+HFrc6HPxYvJYIPJQjYv8LBYIMxQ/riMP2DLo27h/IFfIOEz/C66RVt1Nt6UZhp0svjVgKKnPT7CMh+3zfNPsO2s+BD7YMu9iBk6mT+qTvuxH+BR4QyuEfXaJntAslksD0v6MSstqI5SAM+QIyzv35yoNx/h6f+D5oI4UjjQ==";
	public static void main(String[] args) throws UnsupportedEncodingException, Exception {
		byte[] bytes = RSAUtils.decrypt(RSAUtils.getPublicKeyByStr(RSAUtils.QGSPLCGK_PUBLIC),
				decode(data.getBytes("UTF-8")));
		System.out.println(new String(bytes));
	}

	/**
	 * @param bytes
	 * @return
	 */
	public static byte[] decode(final byte[] bytes) {
		return Base64.decodeBase64(bytes);
	}
}
