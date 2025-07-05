/*******************************************************************************
 * Class        ：EnterpriseApplication
 * Created date ：2020/11/08
 * Lasted date  ：2020/11/08
 * Author       ：KhoaNA
 * Change log   ：2020/11/08：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p;

import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableAsync;

import vn.com.unit.common.config.JcanaryProperties;

/**
 * EnterpriseApplication
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */

@EnableCaching
@EnableAsync
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) // , LiquibaseConfiguration.class
@EnableConfigurationProperties(JcanaryProperties.class)
@ComponentScan(basePackages = "vn.com.unit", excludeFilters = {
		@ComponentScan.Filter(type = FilterType.REGEX, pattern = "vn\\.com\\.unit\\.ep2p\\.admin..*"),
		@ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "vn.com.unit.ep2p.admin.*"),
		@ComponentScan.Filter(type = FilterType.REGEX, pattern = "vn.com.unit.ep2p.configurer.ProcessEngineConfig"),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
				vn.com.unit.ep2p.configurer.ProcessEngineConfig.class,
				}),
})
public class EnterpriseApplication {

	private static final String UTC_TIME_ZONE = "UTC";

	private static final String HCM_TIME_ZONE = "Asia/Saigon";

//	public static final String TIME_ZONE = UTC_TIME_ZONE;

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone(HCM_TIME_ZONE));
		System.out.println("Spring boot application running in " + HCM_TIME_ZONE + " timezone: " + new Date());
	}
	
	public static void main(String[] args) {
		SpringApplication.run(EnterpriseApplication.class, args);
	}

}
