package vn.com.unit.cms.core.utils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import vn.com.unit.cms.core.module.banner.dto.ImageDto;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.service.JRepositoryService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.core.utils.FileUtil;

@Component
public class CmsUtils {

    // COMMON UTILS
    public static final String PATTERN_MONEY_COMMA = "#,###.00";
    public static final String PATTERN_MONEY = "#,###";

    private static SystemConfig systemConfig;

    private static JRepositoryService jRepositoryService;

    @Autowired
    public CmsUtils(SystemConfig systemConfig, JRepositoryService jRepositoryService) {
        CmsUtils.systemConfig = systemConfig;
        CmsUtils.jRepositoryService = jRepositoryService;
    }

    final static Logger logger = LoggerFactory.getLogger(CmsUtils.class);

    public static String getPathTemp() {
        String path = systemConfig.getConfig(SystemConfig.PHYSICAL_PATH_TEMP);
        if (StringUtils.isBlank(path)) {
            path = jRepositoryService.getPathByRepository(SystemConfig.REPO_UPLOADED_TEMP);
        }
        return path;
    }

    public static String getPathMain() {
        String path = systemConfig.getConfig(SystemConfig.PHYSICAL_PATH_MAIN);
        if (StringUtils.isBlank(path)) {
            path = jRepositoryService.getPathByRepository(SystemConfig.REPO_UPLOADED_MAIN);
        }
        return path;
    }

    public static List<ImageDto> getFilePathListServer(String folderServer, String contextPath, String customerAlias)
            throws IOException {
        // resultList
        List<ImageDto> resultList = new ArrayList<ImageDto>();

        // imagedto
        ImageDto dto = null;

        // public static final String PHYSICAL_PATH_MAIN = "PHYSICAL_PATH_MAIN";
        String mainPath = systemConfig.getConfig(SystemConfig.PHYSICAL_PATH_MAIN);

        // pathEditor
        String pathEditor = folderServer + "/editor/download?url=";
        pathEditor = pathEditor.replace("//", "/");

        // folder editor
        File folder = new File(Paths.get(mainPath, folderServer, "editor").toString());

        if (folder.exists()) {
            File[] fileList = folder.listFiles();
            if (fileList != null && fileList.length > 0) {
                for (File file : fileList) {
                    // isDirectory
                    if (file.isDirectory()) {
                        // folderDirect
                        File folderDirect = new File(
                                Paths.get(mainPath, folderServer, "editor", file.getName()).toString());
                        // fileDirectList
                        File[] fileDirectList = folderDirect.listFiles();

                        for (File fileDirect : fileDirectList) {
                            // isFile
                            if (fileDirect.isFile()) {

                                if (ImageIO.read(fileDirect) != null) {
                                    String imagePath = "";
                                    if (StringUtils.isNotBlank(customerAlias)) {
                                        imagePath = contextPath + UrlConst.ROOT + customerAlias;
                                    } else {
                                        imagePath = contextPath;
                                    }

                                    // imagePath
                                    imagePath += UrlConst.ROOT + pathEditor
                                            + URLEncoder.encode(file.getName() + "/" + fileDirect.getName(), "UTF-8");

                                    dto = new ImageDto();
                                    dto.setImage(imagePath);
                                    dto.setThumb(imagePath);

                                    resultList.add(dto);
                                }
                            }
                        }
                    }
                }
            }
        }

        return resultList;
    }

    /**
     * string to uppercase
     *
     * @param value
     * @return String
     * @author hand
     */
    public static String toUppercase(String value) {
        return StringUtils.isEmpty(value) ? value : value.toUpperCase();
    }

    /**
     * trim string for search
     *
     * @param value
     * @return
     * @author hand
     */
    public static String trimForSearch(String value) {
        return StringUtils.isEmpty(value) ? value : value.trim();
    }

    public static String getBaseUrl(HttpServletRequest request) {

        String result = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();

        return result;
    }
    public static String getBaseUrlNonPort(HttpServletRequest request) {

        String result = request.getScheme() + "://" + request.getServerName() +  request.getContextPath();

        return result;
    }

    /**
     * @param sourceDirectory
     * @param targetDirectory
     * @return
     * @throws IOException
     */
    private static boolean moveDirectoryData(File sourceDirectory, File targetDirectory) throws IOException {
        boolean retVal = false;
        if (sourceDirectory.exists()) {
            if (!targetDirectory.exists()) {
                FileUtil.setPermission(targetDirectory);
                targetDirectory.mkdirs();
            }
            File[] fileList = sourceDirectory.listFiles();
            if (fileList != null && fileList.length > 0) {
                for (File file : fileList) {
                    File targetFile = new File(targetDirectory.getPath(), file.getName());
                    FileCopyUtils.copy(file, targetFile);
                    if (file.delete()) {
                        logger.error("source not delete");
                    }
                    retVal = true;
                }
            }
            if (sourceDirectory.delete()) {
                logger.error("source not delete");
            }
        }
        return retVal;
    }

    /**
     * moveTempToUploadFolder move file from temp to main folder
     *
     * @param fileName
     * @param folderPath
     * @author thuydtn
     */
    public static boolean moveTempSubFolderToUpload(String folderPath) {
        File sourceFolder = new File(getPathTemp(), folderPath);
        String targetFolder = Paths.get(getPathMain(), folderPath).toString();
        File target = new File(targetFolder);
        try {
            moveDirectoryData(sourceFolder, target);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return true;
    }

    public static void createDirectoryNotExists(String path) throws IOException {
        // file path
        File file = new File(FilenameUtils.separatorsToSystem(path));
        // not exists directory
        if (!file.exists()) {
            // create directory
            FileUtil.setPermission(file);
            file.mkdirs();
        }
    }

    /**
     * moveTempToUploadFolder move file from temp to main folder
     *
     * @param fileName
     * @param folderName
     * @author hand
     * @throws IOException
     */
    public static String moveTempToUploadFolder(String fileName, String folderName) throws IOException {
        if (fileName.contains(ConstantCore.AT_FILE)) {
            String newName = fileName.substring(fileName.lastIndexOf("@") + 1);
            // file source
            File source = new File(getPathTemp(), fileName);

            String pathTarget = Paths.get(getPathMain(), folderName).toString();

            // create directory taget if not exists
            createDirectoryNotExists(pathTarget);

            // file target
            File target = new File(pathTarget, newName);
            if (!target.exists()) {
                FileUtil.setPermission(target);
                // target.createNewFile();
            }

            try {
                FileCopyUtils.copy(source, target);
                if (!source.delete()) {
                    logger.error("source not delete");
                }

            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            String result = folderName + "/" + newName;
            return result.replace("\\", "/");

        } else {
            return fileName;
        }
    }

    public static boolean fileExistedInTemp(String fileUrl) {
        String tempPath = getPathTemp();
        String domain = StringUtils.EMPTY;
        String path = Paths.get(domain, tempPath).toString();
        File tempFile = new File(path, fileUrl);
        return tempFile.exists();
    }

    public static boolean fileExistedInMain(String fileUrl) {
        String tempPath = getPathMain();
        String domain = StringUtils.EMPTY;
        String path = Paths.get(domain, tempPath).toString();
        File tempFile = new File(path, fileUrl);
        return tempFile.exists();
    }

    public static List<Long> convertStringToListLong(String content, String regex) {
        if (StringUtils.isBlank(content)) {
            return null;
        }

        List<Long> lstResult = new ArrayList<>();
        String[] lstTmp = content.split(regex);
        for (String tmp : lstTmp) {
            if (StringUtils.isNotBlank(tmp.trim())) {
                lstResult.add(Long.valueOf(tmp.trim()));
            }
        }
        return lstResult;
    }

    /**
     * convertStringToBigDcimal
     *
     * @param value
     * @return
     * @author hand
     */
    public static BigDecimal convertStringToBigDcimal(String value, String pattern) {
        BigDecimal bigDecimal = null;
        try {
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            decimalFormat.setParseBigDecimal(true);
            bigDecimal = (BigDecimal) decimalFormat.parse(value);
        } catch (Exception e) {
            logger.error(e + ":" + e.getMessage());
        }

        return bigDecimal;
    }

    /**
     * convert byte to string UTF8
     *
     * @param bytes
     * @return String
     * @author hand
     */
    public static String converByteToStringUTF8(byte[] bytes) {
        Charset UTF8_CHARSET = Charset.forName(ConstantCore.UTF_8);
        return new String(bytes, UTF8_CHARSET);
    }

    public static String convertBigDcimalToString(BigDecimal value, String pattern) {
        String result = null;
        try {
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            decimalFormat.setParseBigDecimal(true);
            result = decimalFormat.format(value);
        } catch (Exception e) {
            logger.error(e + ":" + e.getMessage());
        }
        return result;
    }

    /**
     * convert string to byte UTF8
     *
     * @param string
     * @return byte[]
     * @author hand
     */
    public static byte[] convertStringToByteUTF8(String string) {
        Charset UTF8_CHARSET = Charset.forName(ConstantCore.UTF_8);
        return string.getBytes(UTF8_CHARSET);
    }

    /**
     * convert value Double ToString with type value format
     * 
     * @author ThuyNTB
     */
    public static String convertDoubleToString(Double value, String pattern) {
        String result = null;
        try {
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            result = decimalFormat.format(value);
        } catch (Exception e) {
            logger.error(e + ":" + e.getMessage());
        }
        return result;
    }

    /**
     * set setCookie
     * 
     * @param tokenId
     * @param request
     * @param response
     */
    public static void addCookieForExport(String name, HttpServletRequest request, HttpServletResponse response) {
        Cookie myCookie = new Cookie(name, "OK");
        if (request.isSecure()) {
            myCookie.setSecure(true);
        }

        myCookie.setPath("/");
        response.addCookie(myCookie);
    }
    
    public static MessageList setErrorForValidate(String error, MessageList messageList) {
        if (messageList == null) {
            messageList = new MessageList(Message.ERROR);
            messageList.add(error);
        } else {
            messageList.setStatus(Message.ERROR);
            messageList.add(error);
        }

        return messageList;
    }
    
    public static String genCaptcha(int num) {
		String charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		int n = charset.length();
		if (num > n) num = n;
		String value = "";
		for (int i = 0; i < num; ++i) {
			value += charset.charAt(((int) (Math.floor(Math.random() * n))));
		}
		return value;
	}
}
