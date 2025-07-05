/*******************************************************************************
 * Class        ：AuthorityService
 * Created date ：2021/01/28
 * Lasted date  ：2021/01/28
 * Author       ：vinhlt
 * Change log   ：2021/01/28：01-00 vinhlt create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import vn.com.unit.core.dto.MenuInfo;
import vn.com.unit.core.dto.UserAuthorityDto;

/**
 * AuthorityService
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhlt
 */
public interface AuthorityService {

    /**
     * findAuthorityDetail
     * @param account
     * @return
     * @author vinhlt
     */
    List<GrantedAuthority> findAuthorityDetail(UserAuthorityDto account);

    /**
     * getAccountById
     * @param valueOf
     * @return
     * @author vinhlt
     */
    UserAuthorityDto getAccountById(Long accountId);

    int countByToken(String token);

    void insertToken(String token);
    
    void removeToken(String token);
    
    List<MenuInfo> getMenuInfo(Long accountId);
}
