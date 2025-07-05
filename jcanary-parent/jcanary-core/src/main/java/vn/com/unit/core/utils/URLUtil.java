/*******************************************************************************
 * Class        Utils
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.core.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import vn.com.unit.core.constant.ConstantCore;

/**
 * Common method
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class URLUtil {

	/**
	 * Validate ajax request
	 * 
	 * @param request type HttpServletRequest
	 * @return boolean
	 * @author KhoaNA
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String requestedWithHeader = request.getHeader("X-Requested-With");
		return "XMLHttpRequest".equals(requestedWithHeader);
	}

	/**
	 * Get base URL from request
	 * 
	 * @param request type HttpServletRequest
	 * @return String
	 * @author KhoaNA
	 */
	public static String getURLBase(HttpServletRequest request) {
		String scheme = request.getScheme() + "://";
		String serverName = request.getServerName();
		String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
		String contextPath = request.getContextPath();
		return scheme + serverName + serverPort + contextPath;
	}

	/**
	 * [?/&] + param + [=] + value
	 * 
	 * @param isFirstParam type boolean
	 * @param paramName    type String
	 * @param paramValue   type Object
	 * @return String
	 * @author KhoaNA
	 */
	public static String buildParamWithPrefix(boolean isFirstParam, String paramName, Object paramValue) {
		String result = paramName.concat(ConstantCore.EQUAL).concat(paramValue.toString());

		if (isFirstParam) {
			result = ConstantCore.QUESTION_MARK.concat(result);
		} else {
			result = ConstantCore.AMPERSAND.concat(result);
		}

		return result;
	}

	public static String cleanPath(String fileName) {
		return StringUtils.cleanPath(fileName).replace("../", "");
	}
}
