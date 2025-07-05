/*******************************************************************************
 * Class        ：JwtFilter
 * Created date ：2020/12/02
 * Lasted date  ：2020/12/02
 * Author       ：KhoaNA
 * Change log   ：2020/12/02：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.core.security.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.core.service.AuthorityService;

/**
 * JwtFilter
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class JwtFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;

    @SuppressWarnings("unused")
    private final AuthorityService authorityService;

    public JwtFilter(TokenProvider tokenProvider, AuthorityService authorityService) {
        this.tokenProvider = tokenProvider;
        this.authorityService = authorityService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);
        Locale locale = resolveLocale(httpServletRequest);
        int countToken = 0;	//authorityService.countByToken(jwt);
        if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt).isValid() && countToken == 0) {
            Authentication authentication = this.tokenProvider.getAuthentication(jwt, locale);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(CommonConstant.JWT_AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(CommonConstant.JWT_BEARER)) {
            return bearerToken.substring(CommonConstant.JWT_BEARER.length());
        }
        return null;
    }

    private Locale resolveLocale(HttpServletRequest request) {
        String acceptLanguage = request.getHeader(CommonConstant.JWT_CONTENT_LANGUAGE);
        List<Locale.LanguageRange> ranges = (StringUtils.hasText(acceptLanguage)
                ? Locale.LanguageRange.parse(acceptLanguage)
                : null);
        if (null != ranges && !ranges.isEmpty()) {
            return Locale.forLanguageTag(ranges.get(0).getRange());
        } else {
            return new Locale(CommonConstant.JWT_LANGUAGE_DEFAULT);
        }
    }
}
