/*******************************************************************************
 * Class        ：DtsRequestUtil
 * Created date ：2020/11/10
 * Lasted date  ：2020/11/10
 * Author       ：KhoaNA
 * Change log   ：2020/11/10：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.dts.utils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import vn.com.unit.dts.constant.DtsConstant;

/**
 * RequestUtil
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class DtsRequestUtil {
	
	/**
	 * <p>Get full request url.</p>
	 * @param request
	 * 			type HttpServletRequest
	 * @return String
	 * @author KhoaNA
	 */
	public static String getFullRequestUrl(HttpServletRequest request) {
        String requestURL = String.valueOf(request.getRequestURL());
        String queryString = request.getQueryString();
        if (DtsStringUtil.isNotBlank(queryString)) {
            return String.join(DtsConstant.QUESTION_MARK, requestURL, queryString);
        }
        return requestURL;
    }
	
	/**
	 * <p>Get info mandatory header.</p>
	 * @param request
	 * 			type HttpServletRequest
	 * @return String
	 * @author KhoaNA
	 */
	public static String getInfoMandatoryHeader(HttpServletRequest request) {
        Map<String, String> map = Maps.newHashMap();
        String jwt = DtsJwtUtil.getJwtFromRequest(request);
        if (DtsStringUtil.isNotBlank(jwt) && jwt.length() > DtsConstant.JWT_LENGTH) {
            String subStringLast = DtsStringUtil.substring(jwt, jwt.length() - DtsConstant.JWT_LENGTH);
            jwt = DtsStringUtil.repeat(DtsConstant.ASTERISK, 4) + DtsStringUtil.repeat(DtsConstant.DOT, 3) + subStringLast;
        }
        String deviceId = request.getHeader(DtsConstant.JWT_DEVICE_ID);
        map.put(DtsConstant.JWT_TOKEN, jwt);
        map.put(DtsConstant.JWT_DEVICE_ID, deviceId);
        return map.toString();
    }
	
	public static String getBody(HttpServletRequest request, JoinPoint joinPoint, ObjectMapper jsonMapper) {
        String body = DtsConstant.EMPTY;
        String reqMethod = request.getMethod();
        if (DtsConstant.REQUEST_POST.equals(reqMethod) || DtsConstant.REQUEST_PUT.equals(reqMethod) || DtsConstant.REQUEST_DELETE.equals(reqMethod)) {
            Object obj = getBodyObject(joinPoint);
            if (obj == null) return body;
            try {
                body += jsonMapper.writeValueAsString(obj);
            } catch (JsonProcessingException ignore) {
            }
        }
        return body;
    }
	
	private static Object getBodyObject(JoinPoint joinPoint) {
        Object obj = Stream.of(joinPoint.getArgs())
                .filter(Objects::nonNull)
                .filter(o -> {
                    Class<?> aClass = o.getClass();
                    String name = aClass.getName();
                    if (o instanceof List) {
                        List<?> list = (List<?>) o;
                        if (!DtsCollectionUtil.isEmpty(list)) {
                            name = list.get(0).getClass().getName();
                        }
                    }
                    return name.contains("vn.com.unit") || name.contains("java.util");
                })
                .findAny().orElse(null);
        return obj;
    }
}
