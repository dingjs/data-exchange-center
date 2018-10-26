package data.exchange.center.service.businessdata;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.RSAUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import sun.misc.BASE64Decoder;

/**
 * RSA 工具类。提供加密，解密，生成密钥对等方法。
 * 
 */
public class RSAUtils {
    private static final Log logger = LogFactory.getLog(RSAUtil.class);

    /**
     * 此实例只可创建一个，否则会有内存泄露
     */
    public static final BouncyCastleProvider bouncyCastleProvider = new BouncyCastleProvider();

    static {
        Security.addProvider(RSAUtils.bouncyCastleProvider);
    }

    //TODO dingjsh commented in 20160822 将密钥成对写在这其实是一种不太好的实现，这个类应该只提供公用方法，系统中用到什么公钥私钥应该写到自己系统的常量中
    /******web 登录*******/
    public static final String LOGIN_WEB_PUBLIC = "30819f300d06092*************d9c5453490830cf70203010001";

    public static final String LOGIN_WEB_PRIVATE = "30820277020100300d06*********0472a86e6e9c71954d45736bab94e293cdd86c7747eaa41c6";


    public static final String AJXX_EWM_PRIVATE = "30820276020100300d0609***************1cc0cb833dc88d5649f9df064358727d405b899ed8141f14ff7a97aa38b09e9f754bd45db2";

    /******web 案件查询*******/
    public static final String AJXX_WEB_PUBLIC = "30819f300d06092a86***********6fbc60c3cc547cb294ee0c847d8a277e0aff4abfa1f3ca902f42b9453b89e50203010001";

    public static final String AJXX_WEB_PRIVATE = "30820276020100300***********9f133a5a5fed5c541173dbff371025b6e430d529b473544";

    /** app 二维码 **/
    public static final String EWM_PUBLIC = "30819f300d06092a864886f70d010101050003818d0030818902818100a088a1671e23f3777f419465952268f10fd206e550d4c63d0472649b6f3b6bc1a3e604e694ca6a997ee7b5b713bf17b1e20f528ffe810ef675929626efc49d841ba00b89ecb7dc48e75683f73ef6dc02413dc58f727f23067a80a469e0c4e7fb6dec7247c97342965d3a13a9072b9c8a36176683ec6c60f16061e4f450c0922b0203010001";

    public static final String EWM_PRIVATE = "30820277020100300d0*************045dc6e847a96f73535a5512730381768bbf588599a3c5836b48c5";

    /** 律协系统加密公钥 **/
    public static final String LOGIN_LXXT_PUBLIC = "30819f300d06092a864886f70d********bde8591bb46a5da3f8833865d3fb7f8a46b7d8a333dee9f198103745b9170be45d789dbed46f29809a764b5c05250203010001";

    /** 律协系统解密私钥**/
    public static final String LOGIN_LXXT_PRIVATE = "3082027602010030*************e4ac18b03c12253f726bc0a8e89025";

    /**庭审系统云**/
    public static final String TSXTY_PRIVATE = "308202760201003**********6464eec72afa86c5bdd7f06c884713b394346b04119";

    /**全国审判流程公开接口**/
    public static final String QGSPLCGK_PRIVATE = "308202780201**********329668f8ebe79ca";

    public static final String QGSPLCGK_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCflZaYF13v52SGYV2qyXeVfEAqy0RzH/8d3MTPuSqw4nAD1DSpqwU5g6Tq7Krf276exTmt7+ZpAYz0rDuxLN6z79XOlwXC/hNwcUXFbxNJJM6igxWSevt9NrQstKy0TmZnOvjpXmTcQzXpOwhGxtvBAWDcEylB+KviH2IsVDPtsQIDAQAB";

    public static PublicKey getPublicKeyByStr(String key) {
        KeyFactory keyFac = null;
        try {
            keyFac = KeyFactory.getInstance("RSA", bouncyCastleProvider);
            X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(Hex.decodeHex(key.toCharArray()));
            return keyFac.generatePublic(pubSpec);
        } catch (Exception ex) {
            byte[] keyBytes;
            PublicKey publicKey = null;
            try {
                keyBytes = (new BASE64Decoder()).decodeBuffer(key);
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                publicKey = keyFactory.generatePublic(keySpec);
            } catch (Exception e) {
                logger.error("公钥转换失败");
            }
            return publicKey;
        }

    }

    public static PublicKey getPublicKey(String hexKeyStr) {
        KeyFactory keyFac = null;
        try {
            keyFac = KeyFactory.getInstance("RSA", bouncyCastleProvider);
            X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(
                    Hex.decodeHex(hexKeyStr.toCharArray()));
            return keyFac.generatePublic(pubSpec);
        } catch (Exception ex) {
            logger.error("", ex);
        }
        return null;
    }

    /** 
     * 使用模和指数生成RSA公钥 
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA 
     * /None/NoPadding】 
     *  
     * @param modulus 
     *            模 
     * @param exponent 
     *            指数 
     * @return 
     */
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static PrivateKey getPrivateKey(String hexKeyStr) {
        KeyFactory keyFac = null;
        try {
            keyFac = KeyFactory.getInstance("RSA", bouncyCastleProvider);
            PKCS8EncodedKeySpec priSpec = new PKCS8EncodedKeySpec(
                    Hex.decodeHex(hexKeyStr.toCharArray()));
            return keyFac.generatePrivate(priSpec);
        } catch (Exception ex) {
            logger.error("", ex);
        }
        return null;
    }

    /** 
     * 使用模和指数生成RSA私钥 
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA 
     * /None/NoPadding】 
     *  
     * @param modulus 
     *            模 
     * @param exponent 
     *            指数 
     * @return 
     */
    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获得公钥模
     * @param pubKey
     * @return
     */
    public static String getPublicModulus(RSAPublicKey pubKey) {
        return pubKey.getModulus().toString(16);
    }

    /**
     * 获得公钥指数
     * @param pubKey
     * @return
     */
    public static String getPublicExponent(RSAPublicKey pubKey) {
        return pubKey.getPublicExponent().toString(16);
    }

    /**
     * * 生成密钥对 *
     * 
     * @return KeyPair *
     * @throws EncryptException
     */
    public static KeyPair generateKeyPair() throws Exception {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA", bouncyCastleProvider);
            final int KEY_SIZE = 1024;// 没什么好说的了，这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低
            keyPairGen.initialize(KEY_SIZE, new SecureRandom());
            KeyPair keyPair = keyPairGen.generateKeyPair();
            return keyPair;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * * 加密 *
     * 
     * @param key
     *            加密的密钥 *
     * @param data
     *            待加密的明文数据 *
     * @return 加密后的数据 *
     * @throws Exception
     */
    public static byte[] encrypt(Key key, byte[] data) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA", bouncyCastleProvider);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
            // 加密块大小为127
            // byte,加密后为128个byte;因此共有2个加密块，第一个127
            // byte第二个为1个byte
            int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
            int leavedSize = data.length % blockSize;
            int blocksSize = leavedSize != 0 ? data.length / blockSize + 1
                    : data.length / blockSize;
            byte[] raw = new byte[outputSize * blocksSize];
            int i = 0;
            while (data.length - i * blockSize > 0) {
                if (data.length - i * blockSize > blockSize)
                    cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
                else
                    cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw,
                        i * outputSize);
                // 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到
                // ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了
                // OutputSize所以只好用dofinal方法。

                i++;
            }
            return raw;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e);
        }
    }

    /**  
     * 16进制 To byte[]  
     * @param hexString  
     * @return byte[]  
     * @throws DecoderException 
     */
    public static byte[] hexStringToBytes(String hexString) throws DecoderException {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        char[] hexChars = hexString.toCharArray();
        return Hex.decodeHex(hexChars);
    }

    /**
     * * 解密 *
     * 
     * @param key
     *            解密的密钥 *
     * @param raw
     *            已经加密的数据 *
     * @return 解密后的明文 *
     * @throws Exception
     */
    public static byte[] decrypt(Key key, byte[] raw) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA", bouncyCastleProvider);
            cipher.init(Cipher.DECRYPT_MODE, key);
            int blockSize = cipher.getBlockSize();
            ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
            int j = 0;

            while (raw.length - j * blockSize > 0) {
                bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
                j++;
            }
            return bout.toByteArray();
        } catch (Exception e) {
            logger.error("", e);
            throw e;
        }
    }

    public static String decryptFromBytes(Key key, byte[] raw) throws Exception {
        String result = "";
        byte[] de_result = RSAUtils.decrypt(key, raw);
        StringBuffer sb = new StringBuffer();
        sb.append(new String(de_result, "utf-8"));
        result = sb.toString();
        //result = URLDecoder.decode(result, "UTF-8");
        return result;
    }

    /**
     * DJSTODO dingjsh commented in 20160823 此方法不好，内部有隐藏逻辑，这个mi好像不是一个纯粹的加密串，它还做了16进制转换、倒序等操作
     * @param key
     * @param hexReversedString
     * @return
     * @author dingjsh
     * @time 2016-8-23下午05:15:51
     */
    @Deprecated
    public static String decrypt(Key key, String hexReversedString) {
        return decryptURLEncodedHexReversedString(key, hexReversedString);
    }

    /**
     * 因为前台js通过RSA.js传过来的是先把原字符串先URLEncode,再reverse，再做16进制转换，所以这个刚好倒过来，专门做这个操作
     * @param key
     * @param hexReversedString
     * @return
     * @author dingjsh
     * @time 2016-8-23下午05:59:59
     */
    public static String decryptURLEncodedHexReversedString(Key key, String hexReversedString) {
        String result = "";
        if (StringUtils.isBlank(hexReversedString)) {
            return hexReversedString;
        }
        byte[] en_result = null;
        ;
        try {
            en_result = hexStringToBytes(hexReversedString);
        } catch (DecoderException e1) {
            logger.error("mi不是有效的16进制字符串", e1);
            throw new IllegalArgumentException(e1);
        }
        try {
            byte[] de_result = RSAUtils.decrypt(key, en_result);
            StringBuffer sb = new StringBuffer();
            sb.append(new String(de_result));
            result = sb.reverse().toString();
            result = URLDecoder.decode(result, "UTF-8");
            return result;
        } catch (Exception e) {
            logger.error("解码出错！", e);
        }
        return "";

    }

    /**
     * 将密钥转成16进制字符串
     * @param key
     * @return
     * @author dingjsh
     * @time 2016-8-22下午03:21:31
     */
    public static String toHex(Key key) {
        return Hex.encodeHexString(key.getEncoded());
    }

    public static void main(String[] args) throws Exception {

        PublicKey keys = RSAUtils.getPublicKeyByStr(RSAUtils.QGSPLCGK_PUBLIC);

        byte[] bytes = decode(
            "IoalJUB+V/jnlwTRVVgxhc78c2t2xM++K9LjEID/YOA+XxIOUgb9eZAx64Vzvwf/v85kP3ZeyU4IjNxMYt4POrRWecAqX7WxAMDt96clrYUYLyrK1qcFg35DrQgy+HZSWxrw8tRT7N8gqTpDEWRbhRK37w/FHBxJWF1QPa3KQEU="
                    .getBytes("utf-8"));

        byte[] bd = RSAUtils.decrypt(keys, bytes);
        System.out.println(new String(bd));
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

    /**
     * @param bytes
     * @return
     */
    public static byte[] decode(final byte[] bytes) {
        return Base64.decodeBase64(bytes);
    }

}
