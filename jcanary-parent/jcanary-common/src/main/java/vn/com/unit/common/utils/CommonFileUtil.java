/*******************************************************************************
 * Class        FileUtils
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.com.unit.common.exception.SystemException;

/**
 * FileUtils
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class CommonFileUtil extends FilenameUtils {

    private static final Logger logger = LoggerFactory.getLogger(CommonFileUtil.class);

    /**
     * 
     * readFileToByteArray
     * 
     * @param file
     *            type File
     * @return byte[]
     * @author taitt
     */
    public static byte[] readFileToByteArray(File file) throws IOException {
        return FileUtils.readFileToByteArray(file);
    }

    /**
     * 
     * moveFile
     * 
     * @param fileName
     *            type String
     * @param folderSource
     *            type String
     * @param newFileName
     *            type String
     * @param folderTarget
     *            type String
     * @author taitt
     */
    public static void moveFile(String fileName, String folderSource, String newFileName, String folderTarget) throws IOException {
        File source = new File(folderSource + fileName);
        File target = new File(folderTarget + newFileName);
        CommonFileUtil.setPermission(target);
        try {
            FileUtils.copyFile(source, target);
            if (!source.delete()) {
                logger.error("source not delete");
            }
        } catch (IOException e) {
            StringBuilder msgError = new StringBuilder("IOException:");
            msgError.append("Can not move file ");
            msgError.append(fileName);
            msgError.append(" from folderSource ");
            msgError.append(folderSource);
            msgError.append(" to folderTarget ");
            msgError.append(folderTarget);
            msgError.append(":");
            throw new SystemException(msgError.toString() + e.toString());
        }
    }

    /**
     * 
     * setPermission
     * 
     * @param file
     *            type File
     * @author taitt
     */
    public static void setPermission(File file) throws IOException {
        if (!file.setReadable(true, false)) {
            logger.error("file not set Readable");
        }
        if (!file.setWritable(true, false)) {
            logger.error("file not set Writable");
        }
        if (!file.setExecutable(true, false)) {
            logger.error("file not set Executable");
        }
    }

    /**
     * Gets the contents of an <code>InputStream</code> as a <code>byte[]</code>.
     * <p>
     * This method buffers the input internally, so there is no need to use a <code>BufferedInputStream</code>.
     *
     * @param input
     *            the <code>InputStream</code> to read from
     * @return the requested byte array
     * @throws NullPointerException
     *             if the input is null
     * @throws IOException
     *             if an I/O error occurs
     */
    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        return IOUtils.toByteArray(inputStream);
    }
    
    /**
     * Get contentType file
     * 
     * @param fileNameFull
     *          type String
     * @return String
     * @author KhoaNA
     */
    public static String getContentType(String fileNameFull) {
        String contentType = "application/octet-stream";
        
        String extensionFile = getExtension(fileNameFull);
        
        if( CommonStringUtil.isNotEmpty(extensionFile) ) {
            if (extensionFile.equals("pdf"))
                contentType = "application/pdf";
            else if (extensionFile.equals("txt"))
                contentType = "text/plain";
            else if (extensionFile.equals("exe"))
                contentType = "application/octet-stream";
            else if (extensionFile.equals("zip"))
                contentType = "application/zip";
            else if (extensionFile.equals("doc"))
                contentType = "application/msword";
            else if (extensionFile.equals("xls"))
                contentType = "application/vnd.ms-excel";
            else if (extensionFile.equals("xlsx"))
                contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            else if (extensionFile.equals("ppt"))
                contentType = "application/vnd.ms-powerpoint";
            else if (extensionFile.equals("gif"))
                contentType = "image/gif";
            else if (extensionFile.equals("png"))
                contentType = "image/png";
            else if (extensionFile.equals("jpeg"))
                contentType = "image/jpeg";
            else if (extensionFile.equals("jpg"))
                contentType = "image/jpeg";
            else if (extensionFile.equals("mp3"))
                contentType = "audio/mpeg";
            else if (extensionFile.equals("wav"))
                contentType = "audio/x-wav";
            else if (extensionFile.equals("mpeg"))
                contentType = "video/mpeg";
            else if (extensionFile.equals("mpg"))
                contentType = "video/mpeg";
            else if (extensionFile.equals("mpe"))
                contentType = "video/mpeg";
            else if (extensionFile.equals("mov"))
                contentType = "video/quicktime";
            else if (extensionFile.equals("avi"))
                contentType = "video/x-msvideo";
            else if (extensionFile.equals("flv"))
                contentType = "video/flv";
        }

        return contentType;
    }
}
