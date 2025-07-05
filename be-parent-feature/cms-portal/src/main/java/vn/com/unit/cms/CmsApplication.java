package vn.com.unit.cms;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import vn.com.unit.common.config.JcanaryProperties;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableConfigurationProperties(JcanaryProperties.class)
@ComponentScan(basePackages = { "vn.com.unit" }, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "vn.com.unit.spring.service.auto.impl.*")})
public class CmsApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CmsApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }

}
