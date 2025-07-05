/*******************************************************************************
 * Class        LoginSuccessHandler
 * Created date 2016/08/02
 * Lasted date  2016/08/02
 * Author       KhoaNA
 * Change log   2016/08/0201-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.UrlConst;
import vn.com.unit.ep2p.admin.service.MenuService;
import vn.com.unit.ep2p.admin.utils.CookieUtils;
import vn.com.unit.ep2p.admin.utils.URLUtil;

/**
 * LoginSuccessHandler
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /** menuService */
    @Autowired
    MenuService menuService;

    private RequestCache requestCache = new HttpSessionRequestCache();

    private static final String URL_PRIOR_LOGIN = "URL_PRIOR_LOGIN";

    private static final String URL_WEB_SOCKET = "/notifications";

    @Autowired
    SystemConfig systemConfig;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
//        systemLogsService.writeSystemLogs("LO0#S00_Login", "Login success", LogTypeEnum.INFO.toInt(), "Login success", "", request);

        // setSessionAttribute
        WebUtils.setSessionAttribute(request, ConstantCore.FORMAT_DATE,
                systemConfig.getConfig(SystemConfig.DATE_PATTERN, UserProfileUtils.getCompanyId()));
        WebUtils.setSessionAttribute(request, ConstantCore.PATTERN_CURRENCY, null);

        // Create cookies for lang
        if (CommonStringUtil.isEmpty(CookieUtils.getCookie(null, CookieUtils.COOKIE_LANGUAGE, request))) {
            String lang = systemConfig.getConfig(SystemConfig.LANGUAGE_DEFAULT, UserProfileUtils.getCompanyId());
            CookieUtils.createCookie(null, CookieUtils.COOKIE_LANGUAGE, lang, null, request, response);
        }

        clearAuthenticationAttributes(request);

        String targetUrl = null;
        // 1.Redirect change Password on User's initial login
//        if (UserProfileUtils.isForceChangePass()) {
//            // clear session: SAVE_REQUEST
//            requestCache.removeRequest(request, response);
//
//            targetUrl = UrlConst.ROOT.concat(UrlConst.REDIRECT_CHANGE_PASSWORD);
//            getRedirectStrategy().sendRedirect(request, response, targetUrl);
//            return;
//        }

        // 2.Always use default target url
        String targetUrlParameter = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl()
                || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
            // clear session: SAVE_REQUEST
            requestCache.removeRequest(request, response);

            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest == null) {
            try {
                if (request.getSession().getAttribute(URL_PRIOR_LOGIN) != null) {
                    targetUrl = (String) request.getSession().getAttribute(URL_PRIOR_LOGIN);
                    // Clean attribute from session
                    request.getSession().removeAttribute(URL_PRIOR_LOGIN);

                    if (targetUrl != null) {
                        // Redirect to url priority login
                        targetUrl = targetUrl.replace(URLUtil.getURLBase(request).trim(), "");

                        // Check exist menu
                        boolean existsUrl = menuService.checkExistUrl(targetUrl);
                        if (existsUrl && !targetUrl.isEmpty() && !UrlConst.ROOT.equals(targetUrl)) {

                            // clear session: SAVE_REQUEST
                            requestCache.removeRequest(request, response);

                            getRedirectStrategy().sendRedirect(request, response, targetUrl);
                            return;
                        }
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        // 3.Use the DefaultSavedRequest URL
        if (savedRequest != null) {
            targetUrl = savedRequest.getRedirectUrl();
            if (!CommonStringUtil.isEmpty(targetUrl)
                    && !targetUrl.contains(UrlConst.ROOT.concat(UrlConst.REDIRECT_CHANGE_PASSWORD))
                    && !targetUrl.contains(UrlConst.COMMON_ERROR_PAGE) && !targetUrl.contains("/ajax/download")
                    && !targetUrl.contains(URL_WEB_SOCKET)) {
                // clear session: SAVE_REQUEST
                requestCache.removeRequest(request, response);

                // logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
                getRedirectStrategy().sendRedirect(request, response, targetUrl);
                return;
            }
        }

        // clear session: SAVE_REQUEST
        requestCache.removeRequest(request, response);
        super.onAuthenticationSuccess(request, response, authentication);
    }

    @Override
    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}
