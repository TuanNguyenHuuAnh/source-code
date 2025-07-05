/*******************************************************************************
 * Class        ：AesUtil
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：tantm
 * Change log   ：2020/12/22：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.common.crypto;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * AesUtil.
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public class AesUtil {

    /** The Constant AES_PREFIX. */
    public static final String AES_PREFIX = "{aes128}";

    /** The Constant AES_ALGORITHM. */
    public static final String AES_ALGORITHM = "AES";

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     * @throws Exception
     *             the exception
     * @author tantm
     */
    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            throw new RuntimeException("Please input raw password");
        }

        String rawPassword = args[0];
        String encryptedPassword = encryptedWithSystemUUID(rawPassword);
        System.out.println("ENCRYPTED PASSWORD : ");
        System.out.println(encryptedPassword);

    }

    /**
     * Encrypted with system UUID.
     *
     * @param inputText
     *            the input text type String
     * @return the string
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws NoSuchAlgorithmException
     *             the no such algorithm exception
     * @throws IllegalBlockSizeException
     *             the illegal block size exception
     * @throws InvalidKeyException
     *             the invalid key exception
     * @throws BadPaddingException
     *             the bad padding exception
     * @throws NoSuchPaddingException
     *             the no such padding exception
     * @author tantm
     */
    public static String encryptedWithSystemUUID(String inputText) throws IOException, NoSuchAlgorithmException, IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, NoSuchPaddingException {
        byte[] key = KeyUtils.keyFromSystemUUID();

        return AES_PREFIX + Base64.getEncoder()
                .encodeToString(encryptAes(inputText.getBytes(StandardCharsets.US_ASCII), Base64.getEncoder().encodeToString(key)));
    }

    /**
     * Decrypt with system UUID.
     *
     * @param encryptedString
     *            the encrypted string type String
     * @return the string
     * @author tantm
     */
    public static String decryptWithSystemUUID(String encryptedString) {
        if (encryptedString.startsWith(AES_PREFIX)) {
            encryptedString = encryptedString.trim().replace(AES_PREFIX, "").trim();
            try {
                String base64Key = Base64.getEncoder().encodeToString(KeyUtils.keyFromSystemUUID());
                byte[] bytes = Base64.getDecoder().decode(encryptedString);
                return new String(decryptAes(bytes, base64Key), StandardCharsets.US_ASCII);
            } catch (Exception e) {
                throw new RuntimeException("Cannot decrypt key", e);
            }
        }

        return encryptedString;
    }

    /**
     * Encrypted secret key.
     *
     * @param inputText
     *            the input text type String
     * @param secretKey
     *            the secret key type String
     * @return the string
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws NoSuchAlgorithmException
     *             the no such algorithm exception
     * @throws IllegalBlockSizeException
     *             the illegal block size exception
     * @throws InvalidKeyException
     *             the invalid key exception
     * @throws BadPaddingException
     *             the bad padding exception
     * @throws NoSuchPaddingException
     *             the no such padding exception
     * @author tantm
     */
    public static String encryptedSecretKey(String inputText, String secretKey) throws IOException, NoSuchAlgorithmException,
            IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {

        byte[] key = KeyUtils.keyFromString(secretKey);
        return AES_PREFIX + Base64.getEncoder()
                .encodeToString(encryptAes(inputText.getBytes(StandardCharsets.US_ASCII), Base64.getEncoder().encodeToString(key)));
    }

    /**
     * Decrypt with secret key.
     *
     * @param encryptedString
     *            the encrypted string type String
     * @param secretKey
     *            the secret key type String
     * @return the string
     * @author tantm
     */
    public static String decryptWithSecretKey(String encryptedString, String secretKey) {

        if (encryptedString.startsWith(AES_PREFIX)) {
            encryptedString = encryptedString.trim().replace(AES_PREFIX, "").trim();
            try {
                String base64Key = Base64.getEncoder().encodeToString(KeyUtils.keyFromString(secretKey));
                byte[] bytes = Base64.getDecoder().decode(encryptedString);
                return new String(decryptAes(bytes, base64Key), StandardCharsets.US_ASCII);
            } catch (Exception e) {
                throw new RuntimeException("Cannot decrypt key", e);
            }
        }

        return encryptedString;
    }

    /**
     * Encrypt data using aes algorithm.
     *
     * @param data
     *            data to encrypt
     * @param secretKey
     *            secret key in base64 encoding format
     * @return the byte[]
     * @throws NoSuchPaddingException
     *             the no such padding exception
     * @throws NoSuchAlgorithmException
     *             the no such algorithm exception
     * @throws BadPaddingException
     *             the bad padding exception
     * @throws IllegalBlockSizeException
     *             the illegal block size exception
     * @throws InvalidKeyException
     *             the invalid key exception
     * @author tantm
     */
    public static byte[] encryptAes(byte[] data, String secretKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKey), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(data);
    }

    /**
     * Decrypt data using aes algorithm.
     *
     * @param encodedData
     *            data to decrypt
     * @param secretKey
     *            secret key in base64 encoding format
     * @return the byte[]
     * @throws NoSuchPaddingException
     *             the no such padding exception
     * @throws NoSuchAlgorithmException
     *             the no such algorithm exception
     * @throws BadPaddingException
     *             the bad padding exception
     * @throws IllegalBlockSizeException
     *             the illegal block size exception
     * @throws InvalidKeyException
     *             the invalid key exception
     * @author tantm
     */
    public static byte[] decryptAes(byte[] encodedData, String secretKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKey), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return cipher.doFinal(encodedData);
    }
}
