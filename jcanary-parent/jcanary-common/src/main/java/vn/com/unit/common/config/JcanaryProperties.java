/*******************************************************************************
 * Class        ：JcanaryProperties
 * Created date ：2020/12/02
 * Lasted date  ：2020/12/02
 * Author       ：KhoaNA
 * Change log   ：2020/12/02：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

import lombok.Getter;
import lombok.Setter;


/**
 * JcanaryProperties
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Getter
@ConfigurationProperties(prefix = "jcanary")
public class JcanaryProperties {
    
    private final Security security = new Security();
    
    private final CorsConfiguration cors = new CorsConfiguration();
    
    @Getter
    public static class Security {
        
        private final Authentication authentication = new Authentication();
        
        @Getter
        public static class Authentication {
            
            private final Jwt jwt = new Jwt();
            
            @Getter
            @Setter
            public static class Jwt {
                
                private String secret = JcanaryDefaults.Security.Authentication.Jwt.secret;

                private String base64Secret = JcanaryDefaults.Security.Authentication.Jwt.base64Secret;

                private long tokenValidityInSeconds = JcanaryDefaults.Security.Authentication.Jwt
                    .tokenValidityInSeconds;

                private long tokenValidityInSecondsForRememberMe = JcanaryDefaults.Security.Authentication.Jwt
                    .tokenValidityInSecondsForRememberMe;
            }
        }
    }
}
