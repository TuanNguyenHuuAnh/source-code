/*******************************************************************************
 * Class        ：CustomUsernamePasswordAuthenticationFilter
 * Created date ：2019/05/13
 * Lasted date  ：2019/05/13
 * Author       ：HungHT
 * Change log   ：2019/05/13：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.enumdef.AuthTypeEnum;
import vn.com.unit.ep2p.admin.service.CompanyService;

/**
 * CustomUsernamePasswordAuthenticationFilter
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    CompanyService companyService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String systemCode = request.getParameter("systemCode");
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        
        String authType = AuthTypeEnum.BASIC_AUTH.toString();
        // Set username with format |{username}$|{systemCode}$|{authType}        
        String usernameSystem = String.format("|%s%s|%s%s|%s", username.trim(), ConstantCore.DOLLAR, systemCode, ConstantCore.DOLLAR,
                authType);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(usernameSystem, password);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
