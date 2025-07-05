/*******************************************************************************
 * Class        ：TokenProvider
 * Created date ：2020/12/02
 * Lasted date  ：2020/12/02
 * Author       ：KhoaNA
 * Change log   ：2020/12/02：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.core.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import vn.com.unit.common.config.JcanaryProperties;
import vn.com.unit.core.constant.AuthConstant;
import vn.com.unit.core.dto.JwtTokenInfo;
import vn.com.unit.core.dto.JwtTokenValidate;
import vn.com.unit.core.dto.UserAuthorityDto;
import vn.com.unit.core.enumdef.TokenEnum;
import vn.com.unit.core.security.UserPrincipal;
import vn.com.unit.core.service.AuthorityService;

/**
 * TokenProvider
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 * @Last updated: 22/03/2024	nt.tinh SR16136 - Fix lỗi phát hiện trong quá trình Pentest - 2023
 */
@Component
public class TokenProvider {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private Key key;

    private long tokenValidityInMilliseconds;

    private long tokenValidityInMillisecondsForRememberMe;

    private final JcanaryProperties jCanaryProperties;

    private final AuthorityService authorityService;

    private Date expired;

    private static final Set<String> blacklistedTokens = new HashSet<>();
    
    public TokenProvider(JcanaryProperties jCanaryProperties, AuthorityService authorityService) {
        this.jCanaryProperties = jCanaryProperties;
        this.authorityService = authorityService;
    }

    @SuppressWarnings("deprecation")
    @PostConstruct
    public void init() {
        byte[] keyBytes;
        String secret = jCanaryProperties.getSecurity().getAuthentication().getJwt().getSecret();
        if (!StringUtils.isEmpty(secret)) {
            log.warn("Warning: the JWT key used is not Base64-encoded. "
                    + "We recommend using the `jcanary.security.authentication.jwt.base64-secret` key for optimum security.");
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        } else {
            log.debug("Using a Base64-encoded JWT secret key");
            String base64Secret = jCanaryProperties.getSecurity().getAuthentication().getJwt().getBase64Secret();
            Assert.notNull(base64Secret, "We must config the `jcanary.security.authentication.jwt.base64-secret`.");
            keyBytes = Decoders.BASE64
                    .decode(jCanaryProperties.getSecurity().getAuthentication().getJwt().getBase64Secret());
        }
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.tokenValidityInMilliseconds = 1000
                * jCanaryProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
        this.tokenValidityInMillisecondsForRememberMe = 1000
                * jCanaryProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSecondsForRememberMe();
    }

    public String createToken(Authentication authentication, boolean rememberMe) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//        long now = (new Date()).getTime();

        long now = System.currentTimeMillis();

        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }

//        String authorities = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
//
//        return Jwts.builder().setSubject(String.valueOf(userPrincipal.getAccountId())).claim(AUTHORITIES_KEY, authorities)
//                .signWith(key, SignatureAlgorithm.HS512).setExpiration(validity).compact();

        this.expired = validity;

        return Jwts.builder().setSubject(String.valueOf(userPrincipal.getAccountId()))
                .signWith(key, SignatureAlgorithm.HS512).setExpiration(validity).compact();
    }

    public JwtTokenInfo createRefreshToken(Authentication authentication, boolean requireTracking) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put(AuthConstant.CLAIMS_TYPE, TokenEnum.REFRESH_TOKEN.getType());
        claims.put(AuthConstant.CLAIMS_USER_ID, userPrincipal.getAccountId());
//        claims.put(AuthConstant.CLAIMS_DEVICE_ID, userPrincipal.getDeviceId());
//        claims.put(AuthConstant.CLAIMS_REQUIRE_TRACKING, requireTracking);
//        claims.put(AuthConstant.CLAIMS_USER_TYPE, userPrincipal.getAccountType());
        long tokenValidity = this.tokenValidityInMillisecondsForRememberMe;
        Date sssuedAt = userPrincipal.getAuthDate();
        if (null == sssuedAt) {
            sssuedAt = new Date();
        }
        Date expiration = new Date(sssuedAt.getTime() + tokenValidity);
        String token = generateToken(claims, userPrincipal.getUsername(), sssuedAt, expiration);
        return new JwtTokenInfo(token, sssuedAt, expiration, tokenValidity);
    }

    private String generateToken(Map<String, Object> claims, String subject, Date sssuedAt, Date expiration) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(sssuedAt).setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS512).compact();
    }

    public Authentication getAuthentication(String token, Locale locale) {
//        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
//
//        List<GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(CommonConstant.COMMA))
//                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        String userId = getUserIdFromJWT(token);
        UserAuthorityDto jcaAccount = new UserAuthorityDto();
        try {
            jcaAccount = authorityService.getAccountById(Long.valueOf(userId));
        } catch (Exception e) {
            log.error("#####getAuthentication#####", e);
        }
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        UserAuthorityDto authorityDto = new UserAuthorityDto();
        authorityDto.setId(jcaAccount.getId());
        authorities = authorityService.findAuthorityDetail(authorityDto);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserPrincipal userPrincipal = modelMapper.map(jcaAccount, UserPrincipal.class);
        userPrincipal.setAuthorities(authorities);
        userPrincipal.setAccountId(jcaAccount.getId());
        userPrincipal.setEnabled(jcaAccount.isEnabled());
        userPrincipal.setGoogleFlag(jcaAccount.getGoogleLogin());
        userPrincipal.setFacebookFlag(jcaAccount.getFacebookLogin());
        userPrincipal.setAppleFlag(jcaAccount.getAppleLogin());

        userPrincipal.setLocale(locale);
        return new UsernamePasswordAuthenticationToken(userPrincipal, token, authorities);
    }
    
    public Authentication getAuthenticationByRefreshToken(String refreshToken, Locale locale) {
        String userId = getUserIdFromJWT(refreshToken);
        UserAuthorityDto jcaAccount = new UserAuthorityDto();
        try {
            jcaAccount = authorityService.getAccountById(Long.valueOf(userId));
        } catch (Exception e) {
            log.error("#####getAuthenticationByRefreshToken#####", e);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        UserAuthorityDto authorityDto = new UserAuthorityDto();
        authorityDto.setId(jcaAccount.getId());
        authorities = authorityService.findAuthorityDetail(authorityDto);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserPrincipal userPrincipal = modelMapper.map(jcaAccount, UserPrincipal.class);
        userPrincipal.setAuthorities(authorities);
        userPrincipal.setAccountId(jcaAccount.getId());
        userPrincipal.setEnabled(jcaAccount.isEnabled());

        userPrincipal.setLocale(locale);
        return new UsernamePasswordAuthenticationToken(userPrincipal, refreshToken, authorities);
    }

    public JwtTokenValidate validateToken(String token) {
        JwtTokenValidate tokenValidate = new JwtTokenValidate();
        tokenValidate.setValid(false);
        try {
        	if (blacklistedTokens.contains(token)) {
        		return tokenValidate;
        	}
            tokenValidate.setClaims(Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody());
            tokenValidate.setValid(true);
            return tokenValidate;
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.", e);
            tokenValidate.setExpired(true);
            tokenValidate.setClaims(e.getClaims());
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Invalid JWT token.", e);
        }

        return tokenValidate;
    }

    public Key getKey() {
        return this.key;
    }

    public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token).getBody();
        return claims.get("uid") != null ? claims.get("uid").toString() : claims.getSubject();
    }

    public Date getExpired() {
        return this.expired;
    }
    
    public void destroyToken(String token) {
    	HashSet<String> tokenExpired = new HashSet<String>();
    	for (String item : blacklistedTokens) {
    		Claims claims = Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(item).getBody();
            Date expiration = claims.getExpiration();
            if (expiration.before(new Date())) {
            	tokenExpired.add(item);
            }
		}
    	blacklistedTokens.removeAll(tokenExpired);
    	blacklistedTokens.add(token);
    }
}
