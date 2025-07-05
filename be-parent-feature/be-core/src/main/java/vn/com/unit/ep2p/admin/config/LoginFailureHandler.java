/*******************************************************************************
 * Class        LoginFailureHandler
 * Created date 2016/07/21
 * Lasted date  2016/07/21
 * Author       KhoaNA
 * Change log   2016/07/1901-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.util.WebUtils;

import vn.com.unit.ep2p.admin.enumdef.LogTypeEnum;
import vn.com.unit.ep2p.admin.exception.CustomAuthenticationException;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.admin.utils.CookieUtils;
import vn.com.unit.ep2p.constant.UrlConst;

/**
 * LoginFailureHandler
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private SystemLogsService systemLogsService;

    @Autowired
    MessageSource messageSource;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        String errorSystem = "";
        String systemCode = "";
        String errorReason = "";
        Long companyId = null;
        String username = "";
        boolean loginWithDomain = (Boolean) WebUtils.getSessionAttribute(request, CookieUtils.DOMAIN_LOGIN);
        if (exception instanceof CustomAuthenticationException) {
            username = ((CustomAuthenticationException) exception).getUsername();
            companyId = ((CustomAuthenticationException) exception).getCompanyId();
            systemCode = ((CustomAuthenticationException) exception).getSystemCode();
            
            // Get code error
            switch (((CustomAuthenticationException) exception).getCode()) {
            case "B001": // Username is required.
                errorReason = "invalid.user.null";
                break;
            case "B002": // Password is required.
                errorReason = "invalid.pass.null";
                break;
            case "B003": // Username does not exist.
                errorReason = "invalid.user.not.exist";
                break;
            case "B004": // The system is required. Please input valid data.
                errorReason = "invalid.system.null";
                errorSystem = "system";
                break;
            case "B005": // The system not exists. Please input valid data.
                errorReason = "invalid.system.not.exist";
                if (!loginWithDomain) {
                    errorSystem = "system";
                }
                break;
            case "B006": // The system expires. Please contact the administrator to support.
                errorReason = "invalid.system.expires";
                break;
            case "B007": // Account locked.
                errorReason = "login.user.lock";
                break;
            case "B008": // Account deactivated.
                errorReason = "invalid.user.deactivated";
                break;
            case "B009": // Login failed.
                errorReason = "invalid.user.password";
                break;
            default:
                errorReason = ((CustomAuthenticationException) exception).getCode();
                break;
            }
        } else {
            errorReason = exception.getMessage();
        }
        String message = messageSource.getMessage(errorReason, null, null);
        systemLogsService.writeSystemLogs("LO0#S00_Login", "Login fail", LogTypeEnum.ERROR.toInt(), message, username, companyId, request);
        String failUrl = UrlConst.LOGIN;
        if (StringUtils.isNotBlank(systemCode) && loginWithDomain) {
            failUrl = UrlConst.ROOT.concat(systemCode.toLowerCase()).concat(UrlConst.LOGIN);
        }
        setDefaultFailureUrl(failUrl + "?reason=" + errorReason + (StringUtils.isNotBlank(errorSystem) ? "&" + errorSystem + "=true" : ""));
        super.onAuthenticationFailure(request, response, exception);
    }
}
