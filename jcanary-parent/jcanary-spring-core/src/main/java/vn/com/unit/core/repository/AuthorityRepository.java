/*******************************************************************************
 * Class        ：AuthorityRepository
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：vinhlt
 * Change log   ：2021/02/01：01-00 vinhlt create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import vn.com.unit.core.dto.AuthorityDto;
import vn.com.unit.core.dto.UserAuthorityDto;
import vn.com.unit.db.repository.DbRepository;

/**
 * AuthorityRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhlt
 */
public interface AuthorityRepository extends DbRepository<Object, Long> {

    /**
     * findAllRoleForAccountByAccountId
     * 
     * @param id
     * @return
     * @author vinhlt
     */
    List<AuthorityDto> findAllRoleForAccountByAccountId(@Param("accountId") Long id);

    /**
     * findOneWithAuthoritiesByLogin
     * 
     * @param lowercaseLogin
     * @return
     * @author vinhlt
     */
    Optional<UserAuthorityDto> findOneWithAuthoritiesByLogin(@Param("username") String lowercaseLogin);

    /**
     * findAccountById
     * 
     * @param accountId
     * @return
     * @author vinhlt
     */
    UserAuthorityDto findAccountById(@Param("accountId") Long accountId);

}
