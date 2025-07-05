/*******************************************************************************
 * Class        ：CustomLogoutSuccessHandler
 * Created date ：2019/06/06
 * Lasted date  ：2019/06/06
 * Author       ：HungHT
 * Change log   ：2019/06/06：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.util.WebUtils;

//import vn.com.unit.core.security.UserPrincipal;
//import vn.com.unit.ep2p.admin.service.MenuService;
import vn.com.unit.ep2p.admin.utils.CookieUtils;
import vn.com.unit.ep2p.constant.UrlConst;

/**
 * CustomLogoutSuccessHandler
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);

//    @Autowired
//    private MenuService menuService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.web.authentication.logout.LogoutSuccessHandler#
     * onLogoutSuccess(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.Authentication)
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            Authentication authentication) throws IOException, ServletException {
        String url = null;
        if (authentication != null && authentication.getPrincipal() != null) {
            try {
//                UserPrincipal userProfile = (UserPrincipal) authentication.getPrincipal();
                url = (String) WebUtils.getSessionAttribute(httpServletRequest, CookieUtils.COOKIE_LOGIN);
                if (StringUtils.isBlank(url)) {
                    url = CookieUtils.getCookie(null, CookieUtils.COOKIE_LOGIN, httpServletRequest);
                }

                // delete firebase token
//                String deviceToken = CookieUtils.getCookie(null, CookieUtils.COOKIE_FIREBASE, httpServletRequest);
//                DeviceTokenDto deviceTokenObj = new DeviceTokenDto();
//                deviceTokenObj .setDeviceToken(deviceToken);
//                deviceTokenService.deleteDeviceTokenForWeb(deviceTokenObj);

                httpServletRequest.getSession().invalidate();
                // you can add more codes here when the user successfully logs out,
                // such as updating the database for last active.
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        // redirect to login
        if (StringUtils.isBlank(url)) {
            url = UrlConst.LOGIN;
        }
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + url);
    }

}