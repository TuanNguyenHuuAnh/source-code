/*******************************************************************************
 * Class        ：WebConfigurer
 * Created date ：2020/12/03
 * Lasted date  ：2020/12/03
 * Author       ：KhoaNA
 * Change log   ：2020/12/03：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import vn.com.unit.common.config.JcanaryProperties;

/**
 * WebConfigurer
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Configuration
public class WebConfigurer {

    @Autowired
    private JcanaryProperties jcanaryProperties;

    private final Logger logger = LoggerFactory.getLogger(WebConfigurer.class);

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = jcanaryProperties.getCors();
        if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
            logger.debug("Registering CORS filter");
            source.registerCorsConfiguration("/api/**", config);
            source.registerCorsConfiguration("/management/**", config);
            source.registerCorsConfiguration("/v2/api-docs", config);
            source.registerCorsConfiguration("/swagger-resources", config);
        }
        return new CorsFilter(source);
    }

}
