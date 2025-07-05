package vn.com.unit.ep2p.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.constant.UrlConstant;
import vn.com.unit.ep2p.core.constant.AppApiConstant;

/**
 * @Last updated: 22/03/2024	nt.tinh SR16136 - Fix lỗi phát hiện trong quá trình Pentest - 2023
 */
@Component
public class RestUtil {

    private static SystemConfig systemConfig;
    
    public RestUtil(SystemConfig systemConfig){
        this.systemConfig = systemConfig;
    }

	private static final Logger logger = LoggerFactory.getLogger(RestUtil.class);

	
	public final static String LANGUAGE_CODE_VI = "VI";
	public final static String LANGUAGE_CODE_EN = "EN";
	
	public static String convertObjectToJsonString(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		String result = "";
		if (object != null) {
			try {
				result = mapper.writeValueAsString(object);
			} catch (JsonProcessingException e) {
				logger.error("_convertObjectToJsonString_", e);;
			}
		}
		return result;
	}
	
//	public static String getCustomerAlias(Long customerId){
//	    String customerAlias;
//        if (UrlConstant.PERSONAL_ID.equals(customerId)) {
//            customerAlias = UrlConstant.PERSONAL;
//        } else  if(UrlConstant.CORPORATE_ID.equals(customerId)){
//            customerAlias = UrlConstant.CORPORATE;
//        }else {
//        	customerAlias = UrlConstant.HD_BANK;
//        }
//        return customerAlias;
//	}
	
    public static String replaceImageUrl(String urlImg, HttpServletRequest request) {
    	
    	String result  = StringUtils.EMPTY;
    	
        if (!StringUtils.isBlank(urlImg)) {
            result = getBaseUrl(request) + AppApiConstant.API_V1 + AppApiConstant.API_APP + "/downloadFile"
                    + UrlConstant.FILE_NAME + urlImg.toString();
        }
        
        return result;
    }

    public static String replaceFileUrlDownload(String urlImg, HttpServletRequest request) {
    	
    	String result  = StringUtils.EMPTY;
    	
        if (!StringUtils.isBlank(urlImg)) {
            result = getBaseUrl(request) + AppApiConstant.API_V1 + AppApiConstant.API_APP + "/export-file"
                    + UrlConstant.FILE_NAME + urlImg.toString();
        }
        
        return result;
    }
    /**
     * replace all url download file ckeditor
     * 
     * @param content
     * @param replacementRegex
     * @return String
     * @throws UnsupportedEncodingException
     */
    public static String replaceUrlDownloadFile(String content, String replacementRegex, String replaceUrl) throws UnsupportedEncodingException {
        String reFormatContent = new String(content);
        Pattern patt = Pattern.compile(replacementRegex);
        Matcher matcher = patt.matcher(content);
        matcher.matches();
        while (matcher.find()) {
            String contextPathAdmin = matcher.group(2);
            reFormatContent = reFormatContent.replace(contextPathAdmin, replaceUrl);
        }
        return reFormatContent;
    }
    
    public static Long getCustomerIdByCustomerAlias(String customerAlias){
        Long customerId = -1L;
        if (UrlConstant.PERSONAL.equals(customerAlias)) {
            customerId = UrlConstant.PERSONAL_ID;
        } else {
            customerId = UrlConstant.CORPORATE_ID;
        }
        return customerId;
    };
    
    public static String replaceUrlImg(String customerAlias, String nameReplace, HttpServletRequest httpRequest,
            String urlImgBefore, String pathAdmin, String pathApi, ServletContext servletContext) throws Exception {

    	String regex = "(\")([a-zA-Z0-9%_\\.]*\\/"+ pathAdmin + "\\/" + nameReplace;
		if (!StringUtils.isBlank(customerAlias)) {
			regex = "(\")([a-zA-Z0-9%_\\.]*\\/"+ pathAdmin + "\\/" + customerAlias + "\\/" + nameReplace;
		}
        regex = regex + "\\/editor\\/download\\?url=)([a-zA-Z0-9%_\\.]*)(\")";
        
        String replaceUrl = getBaseUrl(httpRequest) + AppApiConstant.API_V1 + AppApiConstant.API_APP + "/downloadFile" + "?fileName=/data/editor/";
        return RestUtil.replaceUrlDownloadFile(urlImgBefore, regex, replaceUrl);
    }
    
    public static String getBaseUrl(HttpServletRequest request) {
        String baseUrl;
        //get baseUtl from config
        String BASE_URL = systemConfig.getConfig("BASE_URL");
        if (StringUtils.isNotBlank(BASE_URL)){
            baseUrl = BASE_URL;
        } else {
            baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        }
        return baseUrl;
    }
    
   // 16 characters for AES-128, 24 characters for AES-192, 32 characters for AES-256
    private static final String key = "mysecretkey12345"; // 128 bit key
    private static final String initVector = "encryptionIntVec";
    public static String encrypt(List<String> plainText) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);

        byte[] encrypted = cipher.doFinal(plainText.toString().getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }
}
