/*******************************************************************************
  * Class        ConstantExcelWithPassword
 * Created date 2017/10/28
 * Lasted date  2017/10/28
 * Author       phatvt
 * Change log   2017/10/2801-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.export.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.security.GeneralSecurityException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.crypt.CipherAlgorithm;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.crypt.HashAlgorithm;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
/**
 * ConstantExcelWithPassword
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
public class ExcelWithPasswordUtil {

    /**
     * createAndWriteEncryptedWorkbook
     *
     * @param requestOutputStream
     * @param workbook
     * @param password
     * @throws IOException
     * @author phatvt
     */
    public static void createAndWriteEncryptedWorkbook(OutputStream requestOutputStream, XSSFWorkbook workbook, String password) throws IOException {
        try {
            POIFSFileSystem fileSystem = new POIFSFileSystem();
            OutputStream outputStremEncryped = getEncryptingOutputStream(fileSystem, password);
            workbook.write(outputStremEncryped);
            fileSystem.writeFilesystem(requestOutputStream);
            outputStremEncryped.flush();
        } finally {
            workbook.close();
           
        }
    }
    /**
     * checkPasswordEncrypt
     *
     * @param workbook
     * @param multipartFile
     * @param password
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     * @author phatvt
     */
    public static Workbook checkPasswordEncrypt(Workbook workbook, MultipartFile multipartFile, String password)
            throws IOException, InvalidFormatException{
        boolean isWorkbookLock = false;
        String contentType = multipartFile.getContentType();
        
            if (contentType.equals("application/vnd.ms-excel")) {
                //.xls
                try {
                    org.apache.poi.hssf.record.crypto.Biff8EncryptionKey.setCurrentUserPassword(password);
                    workbook = new HSSFWorkbook(multipartFile.getInputStream());
                } catch (EncryptedDocumentException e) {
                    //wrong password
                    isWorkbookLock = false;
                }
            } else {
                // Checking of .xlsx file with password protected.
                InputStream inputStream = null;
                inputStream = multipartFile.getInputStream();
                if (!inputStream.markSupported()) {
                    inputStream = new PushbackInputStream(inputStream, 8);
                }
                POIFSFileSystem pOIFSFileSystem = new POIFSFileSystem(inputStream);
                EncryptionInfo encryptionInfo = new EncryptionInfo(pOIFSFileSystem);
                Decryptor decryptor = Decryptor.getInstance(encryptionInfo);
    
                try {
                    isWorkbookLock = decryptor.verifyPassword(password);
                    if (isWorkbookLock) {
                        inputStream = decryptor.getDataStream(pOIFSFileSystem);
                        workbook = new XSSFWorkbook(OPCPackage.open(inputStream));
                    } else {
                        // wrong password
                        isWorkbookLock = false;
                    }
                } catch (GeneralSecurityException e) {
                }
            }
            return workbook;
    }
    /**
     * getEncryptingOutputStream
     *
     * @param poifsFileSystem
     * @param password
     * @return
     * @throws IOException
     * @author phatvt
     */
    private static OutputStream getEncryptingOutputStream(POIFSFileSystem poifsFileSystem, String password) throws IOException {
        //encrypt data
        EncryptionInfo encryptionInfo = new EncryptionInfo(EncryptionMode.agile, CipherAlgorithm.aes256, HashAlgorithm.sha512, -1, -1, null);
        Encryptor encryptor = encryptionInfo.getEncryptor();
        encryptor.confirmPassword(password);
        try {
            return encryptor.getDataStream(poifsFileSystem);
        } catch (GeneralSecurityException e) {
            // TODO handle this better
            throw new RuntimeException(e);
        }
    }
    /**
     * stream2file
     *
     * @param in
     * @param contentType
     * @return
     * @throws IOException
     * @author phatvt
     */
    public static File stream2file(InputStream in, String contentType) throws IOException {

        File tempFile = null;
        String property = "java.io.tmpdir";
        String tempDir = System.getProperty(property);
        File tempFolder = new File(tempDir, "HSSA");
        FileUtils.forceMkdir(tempFolder);

        if (contentType.equals("application/vnd.ms-excel")) {
            tempFile = File.createTempFile("temp", ".xls", tempFolder);
        } else {
            tempFile = File.createTempFile("temp", ".xlsx", tempFolder);
        }
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        in.close();
        return tempFile;

    }
    /**
     * setTokenToCookie
     *
     * @param token
     * @param request
     * @param response
     * @author phatvt
     */
    public static void setTokenToCookie(String token, HttpServletRequest request, HttpServletResponse response){
        Cookie myCookie = new Cookie(token, "OK");
        if(request.isSecure()){
            myCookie.setSecure(true);
        }
        myCookie.setPath("/");
        response.addCookie(myCookie);
    }
}
