package data.exchange.center.service.sfgk.util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class Code {
	/**
	 * 准备中
	 */
	public static final int CODE_OK_100 = 100;
	/**
	 * 成功，准备完成
	 */
	public static final int CODE_OK_200 = 200;
	/**
	 * 错误请求 服务器不理解请求的语法，通常是参数不合法
	 */
	public static final int CODE_ERROR_400 = 400;
	/**
	 * 未授权 用户未登录或者没传接口授权码
	 */
	public static final int CODE_ERROR_401 = 401;
	/**
	 * 服务器拒绝请求 已经鉴权成功，但是无权调用此接口
	 */
	public static final int CODE_ERROR_403 = 403;
	/**
	 * 请求的资源不存在
	 */
	public static final int CODE_ERROR_404 = 404;
	/**
	 * 数据冲突
	 */
	public static final int CODE_ERROR_409 = 409;

	public static final String publicKey  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCflZaYF13v52SGYV2qyXeVfEAqy0RzH/8d3MTPuSqw4nAD1DSpqwU5g6Tq7Krf276exTmt7+ZpAYz0rDuxLN6z79XOlwXC/hNwcUXFbxNJJM6igxWSevt9NrQstKy0TmZnOvjpXmTcQzXpOwhGxtvBAWDcEylB+KviH2IsVDPtsQIDAQAB";

	public static final String privateKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCflZaYF13v52SGYV2qyXeVfEAqy0RzH/8d3MTPuSqw4nAD1DSpqwU5g6Tq7Krf276exTmt7+ZpAYz0rDuxLN6z79XOlwXC/hNwcUXFbxNJJM6igxWSevt9NrQstKy0TmZnOvjpXmTcQzXpOwhGxtvBAWDcEylB+KviH2IsVDPtsQIDAQAB";

	/**
	 * 
	 * @function 将base64编码后的公钥字符串转成PublicKey实例
	 * @author Tony
	 * @creaetime 2018年4月25日 下午2:52:15
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String publicKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(publicKey.getBytes());
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(keySpec);
	}

	/**
	 * 
	 * @function 将base64编码后的私钥字符串转成PrivateKey实例
	 * @author Tony
	 * @creaetime 2018年4月26日 下午7:20:52
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String privateKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(privateKey.getBytes());
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(keySpec);
	}

	/**
	 * 
	 * @function 公钥加密
	 * @author Tony
	 * @creaetime 2018年4月25日 下午2:52:55
	 * @param content
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] content, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");// java默认"RSA"="RSA/ECB/PKCS1Padding"
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(content);
	}

	/**
	 * 
	 * @function 解密
	 * @author Tony
	 * @creaetime 2018年4月26日 下午7:08:19
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] content, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(content);
	}
	
	public static final String V_CURSOR_1 = "v_cursor1";
	public static final String V_CURSOR_2 = "v_cursor2";
	public static final String V_CURSOR_3 = "v_cursor3";
	public static final String V_CURSOR_4 = "v_cursor4";
	public static final String V_CURSOR_5 = "v_cursor5";
	public static final String V_CURSOR_6 = "v_cursor6";
	public static final String V_CURSOR_7 = "v_cursor7";
	public static final String V_CURSOR_8 = "v_cursor8";
	public static final String V_CURSOR_9 = "v_cursor9";
	public static final String V_CURSOR_10 = "v_cursor10";
	public static final String V_CURSOR_11 = "v_cursor11";
	public static final String V_CURSOR_12 = "v_cursor12";
	public static final String V_CURSOR_13 = "v_cursor13";
	public static final String V_CURSOR_14 = "v_cursor14";
	public static final String V_CURSOR_15 = "v_cursor15";
	public static final String V_CURSOR_16 = "v_cursor16";
	public static final String V_CURSOR_17 = "v_cursor17";
	public static final String V_CURSOR_18 = "v_cursor18";
	public static final String V_CURSOR_19 = "v_cursor19";
	public static final String V_CURSOR_20 = "v_cursor20";
	
	public static final String XS_NUMBER   = "1";
	public static final String MS_NUMBER   = "2";
	public static final String XZ_NUMBER   = "3";
	public static final String PC_NUMBER   = "4";
}
