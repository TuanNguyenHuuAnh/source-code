/*******************************************************************************
 * Class        ：FileStorageUtils
 * Created date ：2020/11/10
 * Lasted date  ：2020/11/10
 * Author       ：tantm
 * Change log   ：2020/11/10：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.utils;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.com.unit.common.utils.CommonFileUtil;
import vn.com.unit.common.utils.CommonStringUtil;

/**
 * FileStorageUtils
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public class FileStorageUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageUtils.class);

    /**
     * Create directory if path not exists
     *
     * @param path
     *            type String
     * @author hand
     * @throws IOException
     */
    public static void createDirectoryNotExists(String path) throws IOException {
        // file path
        File file = new File(CommonFileUtil.separatorsToSystem(path));
        // not exists directory
        if (!file.exists()) {
            // create directory
            setPermission(file);
            file.mkdirs();
        }
    }

    /**
     * Set permission
     * 
     * @param file
     *            type File
     * @throws IOException
     *             type IOException
     * @author tantm
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
     * Get contentType file
     * 
     * @param fileNameFull
     *            type String
     * @return ContentType
     * @author tantm
     */
    public static String getContentType(String fileNameFull) {
        String contentType = "application/octet-stream";

        String extensionFile = CommonFileUtil.getExtension(fileNameFull);

        if (CommonStringUtil.isNotEmpty(extensionFile)) {
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
