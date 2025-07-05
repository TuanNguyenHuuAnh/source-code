/*******************************************************************************
 * Class        ：KeyUtils
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：tantm
 * Change log   ：2020/12/22：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.common.crypto;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collections;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * KeyUtils.
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public class KeyUtils {

    /**
     * Key from system UUID.
     *
     * @return the byte[]
     * @throws NoSuchAlgorithmException
     *             the no such algorithm exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @author tantm
     */
    public static byte[] keyFromSystemUUID() throws NoSuchAlgorithmException, IOException {
        return keyFromString(SystemUUID.getSystemUUID());
    }

    /**
     * Key from string.
     *
     * @param input
     *            the input type String
     * @return the byte[]
     * @throws NoSuchAlgorithmException
     *             the no such algorithm exception
     * @author tantm
     */
    public static byte[] keyFromString(String input) throws NoSuchAlgorithmException {
        String keyStr = input + String.join("", Collections.nCopies(36, "0"));
        return ChecksumUtils.md5(keyStr.substring(0, 36));
    }

    /**
     * Create private key from bytes. Encoded spec for private key is PKCS#8
     *
     * @param keyBytes
     *            byte array representing key
     * @param algorithm
     *            encoded algorithm (RSA, DSA...)
     * @return PrivateKey instance
     * @throws NoSuchAlgorithmException
     *             the no such algorithm exception
     * @throws InvalidKeySpecException
     *             the invalid key spec exception
     * @author tantm
     */
    public static PrivateKey privateKeyFromByte(byte[] keyBytes, String algorithm)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(algorithm);
        return kf.generatePrivate(spec);
    }

    /**
     * Create public key from bytes. Encoded spec for private key is X.509
     *
     * @param keyBytes
     *            byte array representing key
     * @param algorithm
     *            encoded algorithm (RSA, DSA...)
     * @return PublicKey instance
     * @throws NoSuchAlgorithmException
     *             the no such algorithm exception
     * @throws InvalidKeySpecException
     *             the invalid key spec exception
     * @author tantm
     */
    public static PublicKey publicKeyFromByte(byte[] keyBytes, String algorithm) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(algorithm);
        return kf.generatePublic(spec);
    }

    /**
     * Generate aes key.
     *
     * @param length
     *            the length type int
     * @return the string
     * @throws NoSuchAlgorithmException
     *             the no such algorithm exception
     * @author tantm
     */
    public static String generateAesKey(int length) throws NoSuchAlgorithmException {
        KeyGenerator gen = KeyGenerator.getInstance(AesUtil.AES_ALGORITHM);
        gen.init(length);
        SecretKey secret = gen.generateKey();
        byte[] binary = secret.getEncoded();
        return Base64.getEncoder().encodeToString(binary);
    }
}
