/*******************************************************************************
 * Class        AccessDeniedHandler
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.web.util.WebUtils;

import vn.com.unit.ep2p.admin.utils.CookieUtils;
import vn.com.unit.ep2p.admin.utils.URLUtil;
import vn.com.unit.ep2p.constant.UrlConst;

/**
 * AccessDeniedHandler.
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class AccessDeniedHandler extends AccessDeniedHandlerImpl {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
    AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if(accessDeniedException instanceof MissingCsrfTokenException) {
            if (URLUtil.isAjaxRequest(request)) {
            	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setHeader("Location", request.getContextPath() + "/dashboard");
                response.flushBuffer();
            } else {
                String url = (String) WebUtils.getSessionAttribute(((HttpServletRequest) request), CookieUtils.COOKIE_LOGIN);
                if (StringUtils.isBlank(url)) {
                    url = CookieUtils.getCookie(null, CookieUtils.COOKIE_LOGIN, ((HttpServletRequest) request));
                }
                if (StringUtils.isBlank(url)) {
                    url = UrlConst.LOGIN;
                }
                response.sendRedirect(request.getContextPath() + url + "?timeout=true");
            }
        }
        else if(accessDeniedException instanceof InvalidCsrfTokenException) {
            // when Invalid Csrf then redirect common error page
            response.sendRedirect(request.getContextPath() + UrlConst.COMMON_ERROR_PAGE);
        } 
        else {
            super.handle(request, response, accessDeniedException);
        }
    }
}