/*******************************************************************************
 * Class        ：CommonPasswordUtil
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：tantm
 * Change log   ：2020/12/22：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.common.utils;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

import vn.com.unit.common.crypto.AesUtil;

/**
 * 
 * CommonPasswordUtil
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */

@Component
public class CommonPasswordUtil {

    /** The Constant isEncryptWithSystemUUID. */
    // @Value("${configuration.cryptor.isEncryptWithSystemUUID}")
    private static final Boolean isEncryptWithSystemUUID = BooleanUtils.toBooleanDefaultIfNull(
            BooleanUtils.toBooleanObject(CommonUtility.getConfigureProperty("configuration.cryptor.isEncryptWithSystemUUID")),
            Boolean.FALSE);

    /** The Constant secretKey. */
    // @Value("${configuration.cryptor.secretKey}")
    private static final String secretKey = CommonUtility.getConfigureProperty("configuration.cryptor.secretKey");

    /**
     * Decrypt string.
     *
     * @param encryptedString
     *            the encrypted string type String
     * @param isEncryptWithSystemUUID
     *            the is encrypt with system UUID type Boolean
     * @param secretKey
     *            the secret key type String
     * @return the string
     * @author tantm
     */
    public static String decryptString(String encryptedString, Boolean isEncryptWithSystemUUID, String secretKey) {
        if (BooleanUtils.isTrue(isEncryptWithSystemUUID)) {
            return AesUtil.decryptWithSystemUUID(encryptedString);
        } else {
            return AesUtil.decryptWithSecretKey(encryptedString, secretKey);
        }
    }

    /**
     * Decrypt string.
     *
     * @param encryptedString
     *            the encrypted string type String
     * @return the string
     * @author tantm
     */
    public static String decryptString(String encryptedString) {
        return decryptString(encryptedString, isEncryptWithSystemUUID, secretKey);
    }

    /**
     * Encrypt string.
     *
     * @param rawString
     *            the raw string type String
     * @param isEncryptWithSystemUUID
     *            the is encrypt with system UUID type Boolean
     * @param secretKey
     *            the secret key type String
     * @return the string
     * @throws Exception
     *             the exception
     * @author tantm
     */
    public static String encryptString(String rawString, Boolean isEncryptWithSystemUUID, String secretKey) throws Exception {
        if (BooleanUtils.isTrue(isEncryptWithSystemUUID)) {
            return AesUtil.encryptedWithSystemUUID(rawString);
        } else {
            return AesUtil.encryptedSecretKey(rawString, secretKey);
        }
    }

    /**
     * Encrypt string.
     *
     * @param encryptedString
     *            the encrypted string type String
     * @return the string
     * @throws Exception
     *             the exception
     * @author tantm
     */
    public static String encryptString(String encryptedString) throws Exception {
        return encryptString(encryptedString, isEncryptWithSystemUUID, secretKey);
    }

}
