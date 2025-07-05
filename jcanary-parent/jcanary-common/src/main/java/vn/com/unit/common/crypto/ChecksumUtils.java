/*******************************************************************************
 * Class        ：ChecksumUtils
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：tantm
 * Change log   ：2020/12/22：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.common.crypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ChecksumUtils.
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public class ChecksumUtils {

    /**
     * Sha 256.
     *
     * @param input
     *            the input type String
     * @return the byte[]
     * @throws NoSuchAlgorithmException
     *             the no such algorithm exception
     * @author tantm
     */
    public static byte[] sha256(String input) throws NoSuchAlgorithmException {
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        return digest.digest(bytes);
    }

    /**
     * Md 5.
     *
     * @param input
     *            the input type String
     * @return the byte[]
     * @throws NoSuchAlgorithmException
     *             the no such algorithm exception
     * @author tantm
     */
    public static byte[] md5(String input) throws NoSuchAlgorithmException {
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        MessageDigest digest = MessageDigest.getInstance("MD5");

        return digest.digest(bytes);
    }

    /**
     * Sha 1.
     *
     * @param input
     *            the input type String
     * @return the byte[]
     * @throws NoSuchAlgorithmException
     *             the no such algorithm exception
     * @author tantm
     */
    public static byte[] sha1(String input) throws NoSuchAlgorithmException {
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        MessageDigest digest = MessageDigest.getInstance("SHA-1");

        return digest.digest(bytes);
    }
}
