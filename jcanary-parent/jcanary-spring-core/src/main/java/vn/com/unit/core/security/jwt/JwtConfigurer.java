/*******************************************************************************
 * Class        ：JwtConfigurer
 * Created date ：2020/12/03
 * Lasted date  ：2020/12/03
 * Author       ：KhoaNA
 * Change log   ：2020/12/03：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.core.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import vn.com.unit.core.service.AuthorityService;

/**
 * JwtConfigurer
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;

    private final AuthorityService authorityService;

    public JwtConfigurer(TokenProvider tokenProvider, AuthorityService authorityService) {
        this.tokenProvider = tokenProvider;
        this.authorityService = authorityService;
    }

    @Override
    public void configure(HttpSecurity http) {
        JwtFilter customFilter = new JwtFilter(tokenProvider, authorityService);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
