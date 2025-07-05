/*******************************************************************************
 * Class        Utils
 * Created date 2017/02/16
 * Lasted date  2017/02/16
 * Author       hand
 * Change log   2017/02/1601-00 hand create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.constant;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.ep2p.admin.exception.BusinessException;

/**
 * Utils
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class Utils {

    public static final String formatNumber = "a";

    public static String PATTERN_MONEY;
    
    private static final String COOKIE_EXPORT_VALUE = "OK";
    
    /** logger */
    final static Logger logger = LoggerFactory.getLogger(Utils.class);

    public static void writeLog(String msg) {
        logger.debug(msg);
    }
    
    /**
     * convertNewFileName remove dau, ky tu dac biet
     *
     * @param input
     *            String
     * @return String
     * @author hand
     */
    public static String convertNewFileName(String input) {
        String output = StringUtils.EMPTY;
        if (StringUtils.isNotEmpty(input)) {
            output = Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("đ", "d")
                    .replaceAll("[^\\p{ASCII}]", "").replaceAll("[^\\p{Alpha}\\p{Digit}]+", "");
        }
        return output;
    }
    
    public static String webPathFormatInnerFileUrl(String webInfoRealPath, String content, String replacementRegex, String webSubdirectoryPath) throws UnsupportedEncodingException{
        String reFormatContent = new String(content);
        Pattern patt = Pattern.compile(replacementRegex);
        Matcher matcher = patt.matcher(content);
        matcher.matches();
        while(matcher.find()){
            String downloadUrlPrefix = matcher.group(2);
            String fileUrl = matcher.group(3);
            String preFormatFileUrl = downloadUrlPrefix + fileUrl;
            String decodeFileUrl = URLDecoder.decode(fileUrl, "UTF-8");
            String formatFileUrl = Paths.get("/" + ConstantCore.VCCB_WEBSITE_SUB_DOMAIN + "/"+ UrlConst.WEB_APP_DIRECTORY, webSubdirectoryPath, decodeFileUrl).toString();
            String filePath = Paths.get(webInfoRealPath, webSubdirectoryPath, decodeFileUrl).toString();
            File webFile = new File(FilenameUtils.separatorsToSystem(filePath));
            if(!webFile.exists()){
                String placeHolderUrl = "./static/place-holder-image/default-image.png";
                reFormatContent = reFormatContent.replace(preFormatFileUrl, placeHolderUrl);
            }else{
                reFormatContent = reFormatContent.replace(preFormatFileUrl, formatFileUrl);
            }
        }
        return reFormatContent;
    }
    
    /**
     * writeValueAsString
     *
     * @param object
     * @return
     * @author CongDT
     */
    public static String writeValueAsString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            return String.valueOf(e.getMessage());
        }
    }

    /**
     * checkEmailAddress
     *
     * @param emailString
     * @param isMandantory
     * @return
     * @throws Exception
     * @author CongDT
     */
    public static String[] checkEmailAddress(String emailString, boolean isMandantory) throws Exception {

        if (StringUtils.isBlank(emailString)) {

            if (isMandantory) {
                throw new BusinessException("Please input recipient ");
            } else {
                return null;
            }

        }

        Set<String> emailToSet = new HashSet<>();
        for (String oneEmail : emailString.split(";")) {
            if (StringUtils.isEmpty(oneEmail)) {
                throw new BusinessException("One email address is empty, " + emailString);
            }
            InternetAddress emailAddr = new InternetAddress(oneEmail);
            emailAddr.validate();
            emailToSet.add(oneEmail);
        }

        return emailToSet.toArray(new String[emailToSet.size()]);

    }
    
    /**
	 * set setCookie
	 * 
	 * @param tokenId
	 * @param request
	 * @param response
	 */
	public static void addCookieForExport(String name, HttpServletRequest request, HttpServletResponse response) {
		Cookie myCookie = new Cookie(name, COOKIE_EXPORT_VALUE);
		if (request.isSecure()) {
			myCookie.setSecure(true);
		}

		myCookie.setPath("/");
		response.addCookie(myCookie);
	}
	
    /**
	 * Create Select2Dto common
	 * 
	 * @param id
	 * 			type String
	 * @param name
	 * 			type String
	 * @param text
	 * 			type String[]
	 */
	public static Select2Dto createSelect2Dto(String id, String name, String[] textArray) {
		
		String text = ConstantCore.EMPTY;
		for (String elementText : textArray) {
			text = text.concat(elementText).concat(ConstantCore.COLON);
		}
		text = text.substring(0, text.length() - 1);
		
		Select2Dto result = new Select2Dto(id, name, text);
		return result;
	}
	
	/**
     * convert InputStream to BpmnModel
     * 
     * @param inputStream
     * 			type InputStream
     * @return BpmnModel
     * @author KhoaNA
     */
	public static BpmnModel convertInputStreamToBpmnModel(InputStream inputStream) {
		BpmnModel bpmnModel = null;
		try {
			XMLInputFactory xif = XMLInputFactory.newInstance();
			InputStreamReader inr = new InputStreamReader(inputStream, ConstantCore.UTF_8);
			XMLStreamReader xtr = xif.createXMLStreamReader(inr);
			BpmnXMLConverter converter = new BpmnXMLConverter();
			bpmnModel = converter.convertToBpmnModel(xtr);
		} catch (Exception e) {
			throw new BusinessException("updateProcessInfo: wrong format bpmn file.");
		}
		return bpmnModel;
	}
	
	/**
     * build ActUserId By CompanyId And UserName
     * 
     * @param companyId
     * 			type Long
     * @param username
     * 			type String
     * @return String
     * @author KhoaNA
     */
	/*public static String buildActUserIdByCompanyIdAndUserName(Long companyId, String username) {
		String result = String.valueOf(companyId)
							.concat(ConstantCore.UNDERSCORE)
							.concat(username);
		return result;
	}*/
}
