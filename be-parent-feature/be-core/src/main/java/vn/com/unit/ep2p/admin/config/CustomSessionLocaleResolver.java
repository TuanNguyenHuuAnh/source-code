/*******************************************************************************
 * Class        ：CustomSessionLocaleResolver
 * Created date ：2019/06/06
 * Lasted date  ：2019/06/06
 * Author       ：HungHT
 * Change log   ：2019/06/06：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.config;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.utils.CookieUtils;

/**
 * CustomSessionLocaleResolver
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class CustomSessionLocaleResolver extends SessionLocaleResolver {

    @Autowired
    SystemConfig systemConfig;
    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.i18n.SessionLocaleResolver#resolveLocale(javax.servlet.http.HttpServletRequest)
     */
    public Locale resolveLocale(HttpServletRequest request) {
        String lang = CookieUtils.getCookie(null, CookieUtils.COOKIE_LANGUAGE, request);
        Locale locale = null;
        if (StringUtils.isNotBlank(lang)) {
            locale = new Locale(lang);
        } else {
            // Get lang from company
//            LanguageDto langDto = UserProfileUtils.getLanguageDefault();
//            if (null != langDto) {
//                locale = new Locale(langDto.getCode());
//            } else {
                locale = (Locale) WebUtils.getSessionAttribute(request, LOCALE_SESSION_ATTRIBUTE_NAME);
                if (locale == null) {
                    // Get lang from system default
                    lang = systemConfig.getConfig(SystemConfig.LANGUAGE_DEFAULT);
                    if (StringUtils.isNotBlank(lang)) {
                        locale = new Locale(lang);
                    } else {
                        locale = determineDefaultLocale(request);
                    }
                }
//            }
        }
        WebUtils.setSessionAttribute(request, LOCALE_SESSION_ATTRIBUTE_NAME, locale);
        return locale;
    }
}
