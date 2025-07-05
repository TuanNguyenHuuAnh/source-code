/*******************************************************************************
 * Class        Utils
 * Created date 2017/02/16
 * Lasted date  2017/02/16
 * Author       hand
 * Change log   2017/02/1601-00 hand create a new
 ******************************************************************************/
package vn.com.unit.imp.excel.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Utils
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class Utils {
    private static final String COOKIE_EXPORT_VALUE = "OK";
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
}
