/*******************************************************************************
 * Class        :AccountService
 * Created date :2020/12/01
 * Lasted date  :2020/12/01
 * Author       :SonND
 * Change log   :2020/12/01 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;

import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.dto.JcaAccountSearchDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.entity.JcaAccountRegister;
import vn.com.unit.core.security.UserPrincipal;
import vn.com.unit.db.service.DbRepositoryService;
import vn.com.unit.dts.exception.DetailException;

/**
 * AccountService.
 *
 * @author SonND
 * @version 01-00
 * @since 01-00
 */
public interface JcaAccountService extends DbRepositoryService<JcaAccount, Long> {

	/** The Constant TABLE_ALIAS_JCA_ACCOUNT. */
	static final String TABLE_ALIAS_JCA_ACCOUNT = "acc";

	/**
	 * <p>
	 * Total account by condition
	 * </p>
	 * .
	 *
	 * @author SonND
	 * @param jcaAccountSearchDto type {@link JcaAccountSearchDto}
	 * @return int
	 */
	public int countAccountDtoByCondition(JcaAccountSearchDto jcaAccountSearchDto);

	/**
	 * <p>
	 * Get all account by condition
	 * </p>
	 * .
	 *
	 * @author SonND
	 * @param jcaAccountSearchDto type {@link JcaAccountSearchDto}
	 * @param pagable             type {@link Pageable}
	 * @return List<AccountDto>
	 */
	public List<JcaAccountDto> getAccountDtoByCondition(JcaAccountSearchDto jcaAccountSearchDto, Pageable pagable);

	/**
	 * <p>
	 * Get account by id
	 * </p>
	 * .
	 *
	 * @author SonND
	 * @param id type {@link Long}
	 * @return AccountDto
	 */
	public JcaAccount getAccountById(Long id);

	/**
	 * <p>
	 * Save JcaAccount with create, update
	 * </p>
	 * .
	 *
	 * @author SonND
	 * @param jcaAccount type {@link JcaAccount}
	 * @return JcaAccount
	 */
	public JcaAccount saveJcaAccount(JcaAccount jcaAccount);

	/**
	 * <p>
	 * Save JcaAccount with JcaAccountDto
	 * </p>
	 * .
	 *
	 * @author SonND
	 * @param jcaAccountDto type {@link JcaAccountDto}
	 * @return JcaAccount
	 * @throws DetailException the detail exception
	 */
	public JcaAccount saveJcaAccountDto(JcaAccountDto jcaAccountDto) throws DetailException;

	/**
	 * <p>
	 * Get account information detail by id
	 * </p>
	 * .
	 *
	 * @author SonND
	 * @param id type {@link Long}
	 * @return JcaAccountDto
	 */
	public JcaAccountDto getJcaAccountDtoById(Long id);

	/**
	 * <p>
	 * Update password.
	 * </p>
	 *
	 * @author SonND
	 * @param accountId   type {@link Long}
	 * @param passwordNew type {@link String}
	 * @throws DetailException the detail exception
	 */
	public void updatePassword(Long accountId, String passwordNew) throws DetailException;

	/**
	 * <p>
	 * Check password old.
	 * </p>
	 *
	 * @author SonND
	 * @param passwordOld type {@link String}
	 * @param password    type {@link String}
	 * @return true, if successful
	 * @throws DetailException the detail exception
	 */
	public boolean checkPasswordOld(String passwordOld, String password) throws DetailException;

	/**
	 * <p>
	 * Delete jca account by id.
	 * </p>
	 *
	 * @author SonND
	 * @param id type {@link Long}
	 */
	public void deleteJcaAccountById(Long id);

	/**
	 * <p>
	 * Get list email by account id.
	 * </p>
	 *
	 * @author khadm
	 * @param accountIds type {@link List<Long>}
	 * @return {@link List<String>}
	 */
	public List<String> getListEmailByAccountId(List<Long> accountIds);

	/**
	 * <p>
	 * Count jca account dto by username.
	 * </p>
	 *
	 * @author sonnd
	 * @param username type {@link String}
	 * @param userId   type {@link Long}
	 * @return {@link int}
	 */
	public int countJcaAccountDtoByUsername(String username, Long userId);

	/**
	 * <p>
	 * Count jca account dto by email.
	 * </p>
	 *
	 * @author sonnd
	 * @param email  type {@link String}
	 * @param userId type {@link Long}
	 * @return {@link int}
	 */
	public int countJcaAccountDtoByEmail(String email, Long userId);
	
    public int countJcaAccountDtoByPhone(String phone, Long userId);

	/**
	 * <p>
	 * Count jca account dto by code.
	 * </p>
	 *
	 * @author SonND
	 * @param code type {@link String}
	 * @return {@link int}
	 */
	public int countJcaAccountDtoByCode(String code);

	/**
	 * getAccIdsByFunctionId.
	 *
	 * @author Tan Tai
	 * @param functionId type {@link Long}
	 * @return {@link List<Long>}
	 */
	List<Long> getAccIdsByRoleIds(List<Long> roleIds);

	/**
	 * @author vunt
	 * @param userName
	 * @return
	 */
	public List<JcaAccount> getListByUserName(String userName);

	/**
	 * @param account
	 * @param authorities
	 * @return
	 */
	public UserPrincipal buildUserProfile(JcaAccount account, List<GrantedAuthority> authorities);

	boolean syncADLDAP();
	
	   /**
     * <p>
     * Get list email by user name
     * </p>
     *
     * @author TaiTM
     * @param username
     */
    public List<String> getListEmailByAccountId(String username);

	public void saveJcaAccountRegister(JcaAccountDto jacAccount);

    JcaAccount getAccountByUid(String uid);

	JcaAccountRegister saveJcaAccountRegisterContacted(Long id) throws Exception;
	
	/**
	 * getListByUserName
	 * @author HaND
	 * @param userNameList
	 * @return List<JcaAccount>
	 */
	public List<JcaAccount> getListByUserNameList(String userNameList);
}
