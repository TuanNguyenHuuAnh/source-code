/*******************************************************************************
 * Class        ：I18nConfiguration
 * Created date ：2020/11/16
 * Lasted date  ：2020/11/16
 * Author       ：taitt
 * Change log   ：2020/11/16：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.config;


import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import vn.com.unit.ep2p.core.constant.AppApiConstant;

/**
 * I18nConfiguration
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Configuration
public class I18nConfiguration {

	@Bean(name = "messageSource")
    public ResourceBundleMessageSource getMessageResource()  {
    	ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasenames("i18n/messages", 
        		"i18n/error_messages_app_api", 
                "i18n/error_messages_app_core",
        		"i18n/error_messages_dts",
        		"i18n/error_messages_storage",
        		"i18n/error_messages_common",
        		"i18n/error_messages_sla",
        		"i18n/error_messages_workflow",
        		"i18n/error_messages_imp_excel",
        		"i18n/error_messages_workflow_activiti");
        rs.setDefaultEncoding(AppApiConstant.UTF_8);
        rs.setUseCodeAsDefaultMessage(true);
        return rs;
    }
    
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.ENGLISH);
        return slr;
    }
}
