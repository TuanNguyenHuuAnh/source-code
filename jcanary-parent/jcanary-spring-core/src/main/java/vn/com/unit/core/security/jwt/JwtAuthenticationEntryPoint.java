/*******************************************************************************
 * Class        ：JwtAuthenticationEntryPoint
 * Created date ：2020/12/03
 * Lasted date  ：2020/12/03
 * Author       ：KhoaNA
 * Change log   ：2020/12/03：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.core.security.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import vn.com.unit.dts.utils.DtsJwtUtil;

/**
 * JwtAuthenticationEntryPoint
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Component
public class JwtAuthenticationEntryPoint  implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
    
    private static final String LOGGED_OTHER_DEVICE = "LOGGED_OTHER_DEVICE";

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {

        logger.error("Responding with unauthorized error. Message - {}", e.getMessage());
        String jwt = DtsJwtUtil.getJwtFromRequest(httpServletRequest);
        boolean check = true;
        try {
            Jwts.parserBuilder().setSigningKey(tokenProvider.getKey()).build().parseClaimsJws(jwt);
        } catch (Exception ex) {
            check = false;
        }
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, check ? e.getMessage() : LOGGED_OTHER_DEVICE);
    }
}