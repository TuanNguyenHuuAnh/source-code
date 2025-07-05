package vn.com.unit.ep2p.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import vn.com.unit.ep2p.admin.config.AccessDeniedHandler;
import vn.com.unit.ep2p.admin.config.CustomLogoutSuccessHandler;
import vn.com.unit.ep2p.admin.config.LoginFailureHandler;
import vn.com.unit.ep2p.admin.config.LoginSuccessHandler;
import vn.com.unit.ep2p.constant.SpringSecurityConstant;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebSpringSecurityConfigurer extends WebSecurityConfigurerAdapter {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandler();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new LoginUrlAuthenticationEntryPoint(SpringSecurityConstant.URL_LOGIN);
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new LoginFailureHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/static/**").permitAll()
            .antMatchers("/login**").permitAll()
            .antMatchers("/dashboard").permitAll()
            .anyRequest().authenticated();

        http.antMatcher("/**").csrf()
            .and()
                .formLogin().loginPage(SpringSecurityConstant.URL_LOGIN)
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
            .and()
                .logout()
                .logoutUrl(SpringSecurityConstant.URL_LOGOUT).deleteCookies(SpringSecurityConstant.JSESSIONID)
                .logoutSuccessHandler(logoutSuccessHandler())
            .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint());
        
        http.sessionManagement()
                .invalidSessionUrl("/login?invalid-session=true")
                .sessionFixation().migrateSession()
                .maximumSessions(10)
                .expiredUrl("/login?message=max_session");
    }
}
