package data.exchange.center.service.businessdata;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

public class Test {
    static String  subData = "RNF2YYDhW9F8vYocLKcSopw/D2aa4HN/OC0OVtHNY6rRS39rBjsDw7fDVFLw9zoBD15K2zMJwiXQt0uf652Wh6DMJ8XWOtPJ4k7SU9xc5EAGy2DRfs0CKoanG53dKwepG4U6zSctv/Kap5gO1FvZ24GAJ3MQeVrgeGjyKEOpNeo=";
    public static void main(String[] args) throws UnsupportedEncodingException, Exception {
        byte[] bytes = RSAUtils.decrypt(RSAUtils.getPublicKeyByStr(RSAUtils.QGSPLCGK_PUBLIC),decode(subData.getBytes("UTF-8")));
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
