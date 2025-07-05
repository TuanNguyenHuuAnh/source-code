/*******************************************************************************
 * Class        AjaxAwareAuthenticationEntryPoint
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

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import vn.com.unit.ep2p.admin.utils.URLUtil;

/**
 * AjaxAwareAuthenticationEntryPoint.
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class AjaxAwareAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint     
{
    public AjaxAwareAuthenticationEntryPoint(String loginUrl) {
        super(loginUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (URLUtil.isAjaxRequest(request)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("Location", request.getContextPath() + "/dashboard");
            response.flushBuffer();
        } else {
            //super.commence(request, response, authException);
        }
    }
}