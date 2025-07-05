package vn.com.unit.cms.admin.all.config;

//import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

//import freemarker.template.TemplateException;

@Configuration
public class CmsMailConfig {
	
	@Bean 
	public FreeMarkerViewResolver freemarkerViewResolver() { 
	    FreeMarkerViewResolver resolver = new FreeMarkerViewResolver(); 
	    resolver.setCache(true); 
	    resolver.setPrefix(""); 
	    resolver.setSuffix(".ftl"); 
	    return resolver; 
	}
	
	@Bean 
	public FreeMarkerConfigurer freemarkerConfig() { 
	    FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer(); 
	    freeMarkerConfigurer.setTemplateLoaderPath("classpath:/freemarker");
	    return freeMarkerConfigurer; 
	}

//	@Bean
//	public FreeMarkerConfigurer freemarkerConfig() {
////		FreeMarkerConfigurer bean = new FreeMarkerConfigurer();
////		bean.setTemplateLoaderPath("/freemarker/");
////		return bean;
//		
////        FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
////        factory.setTemplateLoaderPath("classpath:freemarker/"); // "classpath:templates"
////        factory.setDefaultEncoding("UTF-8");
////        FreeMarkerConfigurer result = new FreeMarkerConfigurer();
////        try {
////			result.setConfiguration(factory.createConfiguration());
////		} catch (IOException e) {
////			logger.error("Exception ", e);
////		} catch (TemplateException e) {
////			logger.error("Exception ", e);
////		}
////        return result;
//        
//        
//		FreeMarkerConfigurer bean = new FreeMarkerConfigurer();
//		bean.setTemplateLoaderPath("classpath:freemarker/");
//		return bean;
//	}

}
