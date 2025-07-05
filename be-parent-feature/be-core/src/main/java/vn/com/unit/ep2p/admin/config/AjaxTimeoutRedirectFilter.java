/*******************************************************************************
 * Class        AjaxTimeoutRedirectFilter
 * Created date 2017/04/05
 * Lasted date  2017/04/05
 * Author       KhoaNA
 * Change log   2017/04/0501-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.security.web.util.ThrowableCauseExtractor;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.WebUtils;

import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.utils.CookieUtils;
import vn.com.unit.ep2p.admin.utils.URLUtil;
import vn.com.unit.ep2p.constant.UrlConst;

public class AjaxTimeoutRedirectFilter extends GenericFilterBean {

    // private static final Logger logger = LoggerFactory.getLogger(AjaxTimeoutRedirectFilter.class);

    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();
    private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

    private int customSessionExpiredErrorCode = 901;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
            // logger.debug("Chain processed normally");
        } catch (IOException ex) {
            throw ex;
        } catch (Exception ex) {
            String user = UserProfileUtils.getUserNameLogin();
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            if (user == null && httpRequest.getSession(true) != null) {
                String url = httpRequest.getRequestURL().toString();
                String subUrl = url.replace(URLUtil.getURLBase(httpRequest).trim(), "");
                if (!"/404".equalsIgnoreCase(subUrl) && !"/system-logs/writeSystemLogs".equalsIgnoreCase(subUrl)
                        && !UrlConst.COMMON_ERROR_PAGE.equalsIgnoreCase(subUrl) && !"/ajax/download".equalsIgnoreCase(subUrl)) {
                    if (StringUtils.isNotBlank(httpRequest.getQueryString())) {
                        url += "?" + httpRequest.getQueryString();
                    }
                    try {
                        if (httpRequest.getSession().getAttribute("URL_PRIOR_LOGIN") == null) {
                            httpRequest.getSession().setAttribute("URL_PRIOR_LOGIN", url);
                        }
                    } catch (Exception e) {

                    }

                }
            }
            Throwable[] causeChain = throwableAnalyzer.determineCauseChain(ex);
            RuntimeException ase = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class,
                    causeChain);

            if (ase == null) {
                ase = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
            }

            if (ase != null) {
                if (ase instanceof AuthenticationException) {
                    throw ase;
                } else if (ase instanceof AccessDeniedException) {

                    if (authenticationTrustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
                        // logger.info("User session expired or not logged in yet");
                        String ajaxHeader = ((HttpServletRequest) request).getHeader("X-Requested-With");
                        HttpServletResponse resp = (HttpServletResponse) response;
                        if ("XMLHttpRequest".equals(ajaxHeader)) {
                            // logger.info("Ajax call detected, send {} error code", this.customSessionExpiredErrorCode);
                            resp.sendError(this.customSessionExpiredErrorCode);
                            // resp.sendRedirect(((HttpServletRequest) request).getContextPath() + "/login?timeout=true");
                        } else {
                            // logger.info("Redirect to login page");
                            String url = (String) WebUtils.getSessionAttribute(((HttpServletRequest) request), CookieUtils.COOKIE_LOGIN);
                            if (StringUtils.isBlank(url)) {
                                url = CookieUtils.getCookie(null, CookieUtils.COOKIE_LOGIN, ((HttpServletRequest) request));
                            }
                            if (StringUtils.isBlank(url)) {
                                url = UrlConst.LOGIN;
                            }
                            resp.sendRedirect(((HttpServletRequest) request).getContextPath() + url);
                            throw ase;
                        }
                    } else {
                        throw ase;
                    }
                }
            }
        }
    }

    private static final class DefaultThrowableAnalyzer extends ThrowableAnalyzer {

        /**
         * @see org.springframework.security.web.util.ThrowableAnalyzer#initExtractorMap()
         */
        protected void initExtractorMap() {
            super.initExtractorMap();

            registerExtractor(ServletException.class, new ThrowableCauseExtractor() {

                public Throwable extractCause(Throwable throwable) {
                    ThrowableAnalyzer.verifyThrowableHierarchy(throwable, ServletException.class);
                    return ((ServletException) throwable).getRootCause();
                }
            });
        }

    }

    public void setCustomSessionExpiredErrorCode(int customSessionExpiredErrorCode) {
        this.customSessionExpiredErrorCode = customSessionExpiredErrorCode;
    }
}