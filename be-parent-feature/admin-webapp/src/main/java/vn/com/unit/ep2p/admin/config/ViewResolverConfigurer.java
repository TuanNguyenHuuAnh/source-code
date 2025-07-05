package vn.com.unit.ep2p.admin.config;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
public class ViewResolverConfigurer {

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(springTemplateEngine);
        viewResolver.setRedirectHttp10Compatible(false);
        viewResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        viewResolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return viewResolver;
    }
}