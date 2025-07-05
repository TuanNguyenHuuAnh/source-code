/*******************************************************************************
 * Class        ：CommonBase64Util
 * Created date ：2020/12/14
 * Lasted date  ：2020/12/14
 * Author       ：taitt
 * Change log   ：2020/12/14：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.common.utils;

import java.nio.charset.StandardCharsets;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * CommonBase64Util
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class CommonBase64Util {

    public static String encode(String message) {
        return new String(Base64.encodeBase64(CommonStringUtil.getBytes(message, StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    public static String decode(String encode) {
        byte[] decodeByte = Base64.decodeBase64(encode);
        return new String(decodeByte, StandardCharsets.UTF_8);
    }
    
    /**
     * decodeToByte
     * @param encode
     * @return byte[]
     * @author ngannh
     */
    public static byte[] decodeToByte(String encode) {
        return Base64.decodeBase64(encode);
    }
    /**
     * removeBase64Header
     * @param base64
     * @return String
     * @author ngannh
     */
    public static String removeBase64Header(String base64) {
        if(base64 == null) return  null;
        return base64.trim().replaceFirst("data[:]([a-zA-Z])+[/]([a-z])+;base64,", "");
    }
    /**
     * getImageType
     * @param base64
     * @return String
     * @author ngannh
     */
    public static String getImageType(String base64) {
        String[] header = base64.split("[;]");
        if(header == null || header.length == 0) return null;
        return header[0].split("[/]")[1];
    }

    
    public static String encodeWithSecretKey(String message, String secretKey) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            hash = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }
    
    public static String encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }
    
    
    public static void main(String[] args) {
        try {
            String password = "Pass1234";

            String hash = encodeWithSecretKey(password, "S3r3ct@k3y@X@cTh5c");
            System.out.println("PASSWORD SECRECT KEY: " + hash);
            
            String encode = encode(password);
            System.out.println("PASSWORD ENCODE: " + encode);
            
            String decode = decode(encode);
            System.out.println("PASSWORD DECODE: " + decode);
            
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}
