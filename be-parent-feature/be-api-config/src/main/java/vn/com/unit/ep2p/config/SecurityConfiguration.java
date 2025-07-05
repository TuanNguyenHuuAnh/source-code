/*******************************************************************************
 * Class        ：SecurityConfiguration
 * Created date ：2020/12/03
 * Lasted date  ：2020/12/03
 * Author       ：KhoaNA
 * Change log   ：2020/12/03：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.filter.CorsFilter;

import vn.com.unit.core.security.CustomAccessDeniedHandler;
import vn.com.unit.core.security.jwt.JwtAuthenticationEntryPoint;
import vn.com.unit.core.security.jwt.JwtConfigurer;
import vn.com.unit.core.security.jwt.TokenProvider;
import vn.com.unit.core.service.AuthorityService;
import vn.com.unit.ep2p.core.constant.AppApiConstant;

/**
 * SecurityConfiguration
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	String[] apiPermiAll = { "/api/v1/admin/**", "/api/v1/cms/news/**", "/api/v1/cms/banner/**",
			"/api/v1/app/select2/**", "/api/v1/cms/branch/**", "/api/v1/cms/contact**", "/api/v1/cms/contact/**",
			"/api/v1/authen/authentication/login**","/api/v1/authen/authentication/logout**", "/api/v1/admin/account/check-agent-exist**",
			"/api/v1/zipcode/**", "/api/v1/cms/db2/list-office-db2**", "/api/v1/cms/infoagent/check-agent-by-code**", "/api/v1/ds/**", "/api/v1/app/export-file**",
			"/api/v1/app/downloadFile**", "/api/v1/cms/notifys/add-notify-auto", "/api/v1/cms/certificate-letter/letter-agent-ter",
			"/api/v1/cms/certificate-letter/upload-file", "/api/v1/cms/certificate-letter/get-file-by-document",
			"/api/v1/cms/events/check-event", "/api/v1/cms/events/checkin-event",
			"/api/v1/authen/recaptcha/generate", "/api/v1/authen/recaptcha/check", "/api/v1/cms/email/**"};

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private CorsFilter corsFilter;

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**").antMatchers("/app/**/*.{js,html}").antMatchers("/i18n/**")
				.antMatchers("/content/**").antMatchers("/swagger-ui/index.html").antMatchers("/test/**")
				// .antMatchers("/management/**")
				.antMatchers(AppApiConstant.API_CONNECTION_MANAGEMENT + "/**");

		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
				"/configuration/security", "/swagger-ui.html", "/webjars/**");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
        http
            .csrf()
            .ignoringAntMatchers(apiPermiAll)
            .disable()
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
        .and()
            .headers()
            .contentSecurityPolicy("default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:")
        .and()
            .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
        .and()
            .frameOptions()
            .deny()
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
		    .antMatchers(HttpMethod.TRACE,"/**").denyAll()
            .antMatchers(apiPermiAll).permitAll()
            .antMatchers("/api/**").authenticated()
            //.antMatchers("/api/register").permitAll()
            //.antMatchers("/api/activate").permitAll()
            //.antMatchers("/api/account/reset-password/init").permitAll()
            //.antMatchers("/api/account/reset-password/finish").permitAll()
            //.antMatchers("/api/**").authenticated()
            //.antMatchers("/management/health").permitAll()
            //.antMatchers("/management/info").permitAll()
            //.antMatchers("/management/prometheus").permitAll()
            .antMatchers("/management/**").authenticated()
        .and()
            .httpBasic()
        .and()
            .apply(securityConfigurerAdapter());
        // @formatter:on
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	private JwtConfigurer securityConfigurerAdapter() {
		return new JwtConfigurer(tokenProvider, authorityService);
	}
}
