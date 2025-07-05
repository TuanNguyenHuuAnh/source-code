package vn.com.unit.cms.admin.all.util;

import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;

@Component
public class CmsEmailUtils {

	@Resource(name = "freemarkerConfig")
	private FreeMarkerConfigurer freemarkerConfig;
	
	private static final Logger logger = LoggerFactory.getLogger(CmsEmailUtils.class);

	// 2021-08-03 LocLT clone from
	// vn.com.unit.ep2p.asset.service.impl.EmailServiceImpl.getContentFromTemplate
	public String getContentFromTemplate(Map<String, Object> model, String templateFile) {
		String content = new String();

		try {
			Locale locale = new Locale("en");
			// , locale
			Template template = freemarkerConfig.getConfiguration().getTemplate(templateFile, locale, "UTF-8");
			content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		} catch (Exception e) {
			logger.error("vn.com.unit.cms.admin.all.util.CmsEmailUtils", e);
		}
		return content.toString();
	}
	
}
