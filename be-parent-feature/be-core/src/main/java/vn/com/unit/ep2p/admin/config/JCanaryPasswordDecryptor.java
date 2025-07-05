/*******************************************************************************
 * Class        JCanaryPasswordDecryptor
 * Created date 2018/08/09
 * Lasted date  2018/08/09
 * Author       phatvt
 * Change log   2018/08/0901-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.config;

import org.springframework.stereotype.Component;

import vn.com.unit.ep2p.admin.utils.JCanaryPasswordUtil;

/**
 * JCanaryPasswordDecryptor
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
@Component
public class JCanaryPasswordDecryptor {

    /**
     * decryptString
     *
     * @param encryptedString
     * @return
     * @author phatvt
     */
    public static String decryptString(String encryptedString) {
        String decryptedString = JCanaryPasswordUtil
                .decryptString(encryptedString);
        return decryptedString;
    }

}
