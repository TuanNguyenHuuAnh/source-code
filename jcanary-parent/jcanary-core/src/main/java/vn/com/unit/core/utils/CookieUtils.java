/*******************************************************************************
 * Class        ：CookieUtils
 * Created date ：2019/06/06
 * Lasted date  ：2019/06/06
 * Author       ：HungHT
 * Change log   ：2019/06/06：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.core.utils;

import java.net.URL;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * CookieUtils
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class CookieUtils {
    
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(CookieUtils.class);
    
    public static final String COOKIE_LOGIN = "JSESSIONID00";
    public static final String COOKIE_LANGUAGE = "JSESSIONID01";
    public static final String COOKIE_STYLE = "JSESSIONID02";
    public static final String DOMAIN_LOGIN = "DOMAIN_LOGIN";
    public static final String COOKIE_FIREBASE = "JSESSIONID03";

    /**
     * createCookie
     * 
     * @param accountId
     * @param cookieName
     * @param cookieValue
     * @param maxAgeDays
     * @param httpServletRequest
     * @param httpServletResponse
     * @author HungHT
     */
    public static boolean createCookie(Long accountId, String cookieName, String cookieValue, Integer maxAgeDays,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        boolean result = false;
        httpServletRequest.getSession(true);
        int expiry = 30 * 24 * 60 * 60;
        if (null != maxAgeDays) {
            expiry = maxAgeDays * 24 * 60 * 60;
        }
        String valueEncrypt = null;
        try {
            String contextPath = httpServletRequest.getContextPath();
            String name = cookieName.toUpperCase();
            if (null != accountId) {
                name = name.concat(String.valueOf(accountId));
            }
            valueEncrypt = PasswordUtil.encryptString(cookieValue);
            URL url = new URL(URLUtil.getURLBase(httpServletRequest));
            Cookie cookie = new Cookie(name, valueEncrypt);
            // KhoaNA - 20191118 - Always set secure for cookie
            if (url.getProtocol().equalsIgnoreCase("https")) {
                cookie.setSecure(true);
            }
            cookie.setSecure(true);
            cookie.setMaxAge(expiry);
            if (StringUtils.isBlank(contextPath)) {
                contextPath = "/";
            }
            cookie.setPath(";Path=" + contextPath + ";HttpOnly;SameSite=Strict;");
            httpServletResponse.addCookie(cookie);
            result = true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }
    
    /**
     * getCookie
     * 
     * @param accountId
     * @param cookieName
     * @param httpServletRequest
     * @return
     * @author HungHT
     */
    public static String getCookie(Long accountId, String cookieName, HttpServletRequest httpServletRequest) {
        String value = null;
        try {
            String name = cookieName.toUpperCase();
            if (null != accountId) {
                name = name.concat(String.valueOf(accountId));
            }
            Cookie[] cookies = httpServletRequest.getCookies();
            if (null != cookies && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equalsIgnoreCase(name)) {
                        value = PasswordUtil.decryptString(cookie.getValue());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return value;
    }
}
