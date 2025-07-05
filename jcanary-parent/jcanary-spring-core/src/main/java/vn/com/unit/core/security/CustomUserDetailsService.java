/*******************************************************************************
 * Class        ：CustomUserDetailsService
 * Created date ：2020/12/03
 * Lasted date  ：2020/12/03
 * Author       ：KhoaNA
 * Change log   ：2020/12/03：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.core.security;

import java.util.List;
import java.util.Locale;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.dto.UserAuthorityDto;
import vn.com.unit.core.repository.AuthorityRepository;
import vn.com.unit.core.service.AuthorityService;

/**
 * CustomUserDetailsService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private AuthorityRepository authorityRepository;
    
    @Autowired
    private AuthorityService authorityService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        return authorityRepository.findOneWithAuthoritiesByLogin(lowercaseLogin)
                .map(user -> createSpringSecurityUser(lowercaseLogin, user))
                .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));

    }

    private UserPrincipal createSpringSecurityUser(String lowercaseLogin, UserAuthorityDto user) {
        if (!user.isActived()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }
        // Find all role for account
        List<GrantedAuthority> grantedAuthorities = authorityService.findAuthorityDetail(user);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserPrincipal userPrincipal = modelMapper.map(user, UserPrincipal.class);
        userPrincipal.setAuthorities(grantedAuthorities);
        userPrincipal.setAccountId(user.getId());
        userPrincipal.setEnabled(user.isEnabled());
        return userPrincipal;
    }
}