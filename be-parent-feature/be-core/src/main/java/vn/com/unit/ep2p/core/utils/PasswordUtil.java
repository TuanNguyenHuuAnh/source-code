/*******************************************************************************
 * Class        JCanaryPasswordUtil
 * Created date 2018/01/10
 * Lasted date  2018/01/10
 * Author       CongDT
 * Change log   2018/01/1001-00 CongDT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.utils;

import org.apache.commons.lang.BooleanUtils;

import vn.com.unit.common.crypto.AesUtil;

/**
 * JCanaryPasswordUtil
 * 
 * @version 01-00
 * @since 01-00
 * @author CongDT
 */
public class PasswordUtil {
    // @Value("${configuration.cryptor.isEncryptWithSystemUUID}")
    private static final Boolean isEncryptWithSystemUUID = BooleanUtils.toBooleanDefaultIfNull(
            BooleanUtils.toBooleanObject(Utility.getConfigureProperty("configuration.cryptor.isEncryptWithSystemUUID")),
            Boolean.FALSE);

    // @Value("${configuration.cryptor.secretKey}")
    private static final String secretKey = Utility.getConfigureProperty("configuration.cryptor.secretKey");

    public static String decryptString(String encryptedString, Boolean isEncryptWithSystemUUID, String secretKey) {
        if (BooleanUtils.isTrue(isEncryptWithSystemUUID)) {
            return AesUtil.decryptWithSystemUUID(encryptedString);
        } else {
            return AesUtil.decryptWithSecretKey(encryptedString, secretKey);
        }
    }

    public static String decryptString(String encryptedString) {
        return decryptString(encryptedString, isEncryptWithSystemUUID, secretKey);
    }

    public static String encryptString(String rawString, Boolean isEncryptWithSystemUUID, String secretKey)
            throws Exception {
        if (BooleanUtils.isTrue(isEncryptWithSystemUUID)) {
            return AesUtil.encryptedWithSystemUUID(rawString);
        } else {
            return AesUtil.encryptedSecretKey(rawString, secretKey);
        }
    }

    public static String encryptString(String encryptedString) throws Exception {
        return encryptString(encryptedString, isEncryptWithSystemUUID, secretKey);
    }
}
