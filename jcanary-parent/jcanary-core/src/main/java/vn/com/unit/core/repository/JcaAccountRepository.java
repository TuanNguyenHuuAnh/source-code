/*******************************************************************************
 * Class        :AccountRepository
 * Created date :2020/12/01
 * Lasted date  :2020/12/01
 * Author       :SonND
 * Change log   :2020/12/01:01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.dto.JcaAccountSearchDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.db.repository.DbRepository;


/**
 * AccountRepository.
 *
 * @author SonND
 * @version 01-00
 * @since 01-00
 */
public interface JcaAccountRepository extends DbRepository<JcaAccount, Long> {
    
    /**
     * <p>
     * total account by condition
     * </p>
     * .
     *
     * @author SonND
     * @param jcaAccountSearchDto            type {@link JcaAccountSearchDto}
     * @return int
     */
    int countAccountDtoByCondition(@Param("jcaAccountSearchDto") JcaAccountSearchDto jcaAccountSearchDto);
	
    /**
     * <p>
     * get list accountDto by condition
     * </p>
     * .
     *
     * @author SonND
     * @param jcaAccountSearchDto            type {@link JcaAccountSearchDto}
     * @param pageable            type {@link Pageable}
     * @return List<AccountDto>
     */
    Page<JcaAccountDto> getAccountDtoByCondition(@Param("jcaAccountSearchDto") JcaAccountSearchDto jcaAccountSearchDto,            
            Pageable pageable);
    
    
    /**
     * <p>
     * get account information detail by id
     * </p>
     * .
     *
     * @author SonND
     * @param id            type {@link Long}
     * @return AccountDto
     */
    JcaAccountDto getJcaAccountDtoById(@Param("id") Long id);

    
    /**
     * <p>
     * Update password.
     * </p>
     *
     * @author SonND
     * @param accountId            type {@link Long}
     * @param passwordNew            type {@link String}
     */
    @Modifying
    void updatePassword(@Param("accountId")Long accountId, @Param("passwordNew") String passwordNew);
    
    /**
     * <p>
     * Get list email by account id.
     * </p>
     *
     * @author khadm
     * @param accountIds            type {@link List<Long>}
     * @return {@link List<String>}
     */
    List<String> getListEmailByAccountId(@Param("accountIds") List<Long> accountIds);

    /**
     * <p>
     * Count jca account dto by username.
     * </p>
     *
     * @author sonnd
     * @param username            type {@link String}
     * @param userId            type {@link Long}
     * @return {@link int}
     */
    int countJcaAccountDtoByUsername(@Param("username")String username,@Param("userId") Long userId);

    /**
     * <p>
     * Count jca account dto by email.
     * </p>
     *
     * @author sonnd
     * @param email            type {@link String}
     * @param userId            type {@link Long}
     * @return {@link int}
     */
    int countJcaAccountDtoByEmail(@Param("email") String email, @Param("userId") Long userId);
    
    int countJcaAccountDtoByPhone(@Param("phone") String phone, @Param("userId") Long userId);

    /**
     * <p>
     * Count jca account dto by code.
     * </p>
     *
     * @author SonND
     * @param code            type {@link String}
     * @return {@link int}
     */
    int countJcaAccountDtoByCode(@Param("code")String code);
    
    
    /**
     * <p>Get acc ids by function id.</p>
     *
     * @author Tan Tai
     * @param functionId type {@link Long}
     * @return {@link List<Long>}
     */
    List<Long> getAccIdsByRoleIds(@Param("roleIds") List<Long> roleIds);

	/**
	 * @param userName
	 * @return
	 */
	List<JcaAccount> getListByUserName(@Param("userName")String userName);
	
	/**
	 * @param userName
	 * @return
	 */
	List<JcaAccount> getListByUserNameList(@Param("userNameList")String userNameList);


    /**
     * @author TaiTM
     * @param userName
     * @return
     */
    List<String> findListEmailByUserName(@Param("userName") String userName);

    JcaAccount findAccountByUid(@Param("uid") String uid);
    
    String findUsername(@Param("agentCode") String agentCode);
}