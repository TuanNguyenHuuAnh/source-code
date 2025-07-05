/*******************************************************************************
 * Class        AccountRepository
 * Created date 2017/02/14
 * Lasted date  2017/02/14
 * Author       KhoaNA
 * Change log   2017/02/1401-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.dto.JcaAccountSearchDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.repository.JcaAccountRepository;
import vn.com.unit.ep2p.admin.dto.AccountListDto;
import vn.com.unit.ep2p.admin.dto.account.JcaAccountDetailDto;
import vn.com.unit.ep2p.admin.dto.account.JcaAccountInfoSelect2Dto;
import vn.com.unit.ep2p.admin.dto.account.JcaAccountItem;

/**
 * AccountRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface AccountRepository extends JcaAccountRepository {

	/**
	 * Update all JcaAccount to disable. Exclude 'admin' account. '0' is disable '1'
	 * is enable
	 * 
	 * void
	 * 
	 * @author trieunh <trieunh@unit.com.vn>
	 */
	@Modifying
	public void updateAllAccountToDisable();

	@Modifying
	public void updateAllAccountToInactive();

	/**
	 * Update JcaAccount from LDAP
	 * 
	 * @param JcaAccount void
	 * @author trieunh <trieunh@unit.com.vn>
	 */
	@Modifying
	public void updateLDAPAccountOracle(@Param("account") JcaAccount account);

	@Modifying
	public void updateLDAPAccountMSSQL(@Param("account") JcaAccount account);

	/**
	 * Find JcaAccount by username
	 *
	 * @param username type String
	 * @return Account
	 * @author KhoaNA
	 */
	JcaAccount findByUserNameAndCompanyId(@Param("username") String username, @Param("companyId") Long companyId);

	List<JcaAccount> getListByUserName(@Param("username") String username);

	List<JcaAccount> findByUserNameOracle(@Param("username") String username);

	/**
	 * Count all JcaAccount by condition
	 * 
	 * @param accountSearchDto type AccountSearchDto
	 * @return int
	 * @author KhoaNA
	 */
	int countByAccountSearchDto(@Param("accountSearchDto") JcaAccountSearchDto accountSearchDto);

	/**
	 * Find all JcaAccount by condition
	 * 
	 * @param offset           type int
	 * @param sizeOfPage       type int
	 * @param accountSearchDto type AccountSearchDto
	 * @return List<AccountListDto>
	 * @author KhoaNA
	 */
	List<AccountListDto> findListByAccountSearchDtoSQLServer(@Param("offset") int offset,
			@Param("sizeOfPage") int sizeOfPage, @Param("accountSearchDto") JcaAccountSearchDto accountSearchDto);

	List<AccountListDto> findListByAccountSearchDtoMYSQL(@Param("offset") int offset,
			@Param("sizeOfPage") int sizeOfPage, @Param("accountSearchDto") JcaAccountSearchDto accountSearchDto);

	/**
	 * findListByAccountSearchDto
	 * 
	 * @param offset
	 * @param sizeOfPage
	 * @param accountSearchDto
	 * @return
	 * @author HungHT
	 */
	List<AccountListDto> findListByAccountSearchDto(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
			@Param("accountSearchDto") JcaAccountSearchDto accountSearchDto);

	/**
	 * 
	 * @param id
	 * @param deletedBy
	 * @param deletedDate
	 * @return
	 */
	@Modifying
	public int updateDeleteFields(@Param("id") long id, @Param("deletedBy") String deletedBy,
			@Param("deletedDate") Date deletedDate);

	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public JcaAccountDetailDto findAccountDetailDtoById(@Param("id") Long accountId);

	/**
	 * 
	 * @param accountSearchDto
	 * @return
	 */
	int countByAccountSearchDtoOracle(@Param("accountSearchDto") JcaAccountSearchDto accountSearchDto);

	/**
	 * findListByAccountSearchDtoOracle
	 *
	 * @param offset
	 * @param sizeOfPage
	 * @param accountSearchDto
	 * @return
	 * @author phatvt
	 */
	List<AccountListDto> findListByAccountSearchDtoOracle(@Param("offset") int offset,
			@Param("sizeOfPage") int sizeOfPage, @Param("accountSearchDto") JcaAccountSearchDto accountSearchDto);

	// AccountRepository_updatePasswordByUsername
	@Modifying
	public void updatePasswordByUsername(@Param("username") String username, @Param("password") String password);

	/**
	 * updateFailedLoginCount
	 *
	 * @param accountId
	 * @param failedLoginCount
	 * @param loginDate
	 * @author HUNGHT
	 */
	@Modifying
	public void updateFailedLoginCount(@Param("accountId") Long accountId,
			@Param("failedLoginCount") int failedLoginCount, @Param("loginDate") Date loginDate);

	/**
	 * disabledAccount
	 *
	 * @param accountId
	 * @param updatedBy
	 * @param updatedDate
	 * @author HUNGHT
	 */
	@Modifying
	public void disabledAccount(@Param("accountId") Long accountId, @Param("updatedBy") String updatedBy,
			@Param("updatedDate") Date updatedDate);

	/**
	 * countRoleTypeOfAccount
	 * 
	 * @param accountId
	 * @param teamIdList
	 * @return
	 * @author HungHT
	 */
	Long countRoleTypeOfAccount(@Param("accountId") Long accountId, @Param("teamIdList") List<Long> teamIdList);

	/**
	 * updatePassword
	 *
	 * @param id
	 * @param pass
	 * @author phatvt
	 */
	@Modifying
	void updatePassword(@Param("accountId") Long id, @Param("pass") String pass);

	/**
	 * findByInputQuery
	 *
	 * @param inputQuery
	 * @return
	 * @author VinhLT
	 */
	List<JcaAccountDto> findByInputQuery(@Param("inputQuery") String inputQuery);

	/**
	 * findListAccountHasRoleByItem
	 *
	 * @param itemCode
	 * @return
	 * @author VinhLT
	 */
	List<JcaAccount> findListAccountHasRoleByItem(@Param("itemCode") String itemCode);

	/**
	 * findListAccountItem
	 *
	 * @param itemCode
	 * @param companyId
	 * @return
	 * @author VinhLT
	 */
	public List<JcaAccountItem> findListAccountItem(@Param("itemCode") String itemCode,
			@Param("companyId") Long companyId);

	/**
	 * findLstEmailByLstUsername
	 *
	 * @param lstUsername
	 * @return
	 * @author VinhLT
	 */
	public JcaAccountDto findLstEmailByLstUsername(@Param("lstUsername") String lstUsername);

	/**
	 * countNumberAccountByCompanyId
	 * 
	 * @param companyId
	 * @return
	 * @author HungHT
	 */
	Long countNumberAccountByCompanyId(@Param("companyId") Long companyId);

	/**
	 * findListAccountItemByItemList
	 *
	 * @param lstItemCode
	 * @param companyId
	 * @return
	 * @author VinhLT
	 */
	public List<JcaAccountItem> findListAccountItemByItemList(@Param("lstItemCode") List<String> lstItemCode,
			@Param("companyId") Long companyId);

	/**
	 * getAllCompanyId
	 * 
	 * @return
	 * @author HungHT
	 */
	List<Long> getAllCompanyId();

	public List<JcaAccountDto> findListAccountByCompanyId(@Param("id") Long companyId);

	public List<JcaAccountDto> findListAccountByTeamId(@Param("id") Long teamId);

	/**
	 * findByEmail
	 * 
	 * @param email
	 * @return
	 * @author trieuvd
	 */
	JcaAccount findByEmail(@Param("email") String email);

	/**
	 * findByPhone
	 * 
	 * @param phone
	 * @return
	 * @author TaiTM
	 */
	JcaAccount findByPhone(@Param("phone") String phone);

	/**
	 * findByCmnd
	 * 
	 * @param cmnd
	 * @return
	 * @author hungp
	 */
	JcaAccount findByCmnd(@Param("cmnd") String cmnd);

	/**
	 * findAllActiveAccount
	 * 
	 * @return
	 * @author TuyenTD
	 */
	public List<JcaAccountDto> findAllActiveAccount(@Param("isPaging") boolean isPaging);

	/**
	 * findListAccountByKeyAndListCompanyId
	 * 
	 * @param key
	 * @param orgId
	 * @param listCompanyId
	 * @param isPaging
	 * @return List<Select2Dto>
	 * @author KhuongTH
	 */
	public List<Select2Dto> findListAccountByKeyAndListCompanyId(@Param("key") String key, @Param("orgId") Long orgId,
			@Param("accountId") Long accountId, @Param("listCompanyId") List<Long> listCompanyId,
			@Param("isPaging") boolean isPaging);

	/**
	 * 
	 * findListAccountByKeyAndListCompanyId
	 * 
	 * @param key
	 * @param listCompanyId
	 * @param pageIndex
	 * @param pageSize
	 * @param isPaging
	 * @return
	 * @author taitt
	 */
	public List<JcaAccountDto> findListAccountByKeyAndListCompanyIdByPaging(@Param("key") String key,
			@Param("listCompanyId") List<Long> listCompanyId, @Param("pageIndex") int pageIndex,
			@Param("pageSize") int pageSize, @Param("isPaging") int isPaging);

	/**
	 * 
	 * findListAccountByKeyAndListCompanyIdByPaging
	 * 
	 * @param key
	 * @param listCompanyId
	 * @param pageIndex
	 * @param pageSize
	 * @param isPaging
	 * @return
	 * @author taitt
	 */
	public Long countListAccountByKeyAndListCompanyId(@Param("key") String key, @Param("orgId") Long orgId,
			@Param("accountId") Long accountId, @Param("listCompanyId") List<Long> listCompanyId);

	/**
	 * findListAccountByListAccountId
	 * 
	 * @param listAccountId
	 * @return List<JcaAccountDto>
	 * @author KhuongTH
	 */
	public List<JcaAccountDto> findListAccountByListAccountId(@Param("listAccountId") List<Long> listAccountId,
			@Param("checkPushNotification") boolean checkPushNotification,
			@Param("checkPushEmail") boolean checkPushEmail);

	/**
	 * getByCode
	 * 
	 * @param code
	 * @param companyId
	 * @return
	 * @author trieuvd
	 */
	public JcaAccount getByCode(@Param("code") String code, @Param("companyId") Long companyId);

	/**
	 * updatePositionIdByPositionCode
	 * 
	 * @param positionId
	 * @param positionCode
	 * @param companyId
	 * @author trieuvd
	 */
	@Modifying
	public void updatePositionIdByPositionCode(@Param("positionId") Long positionId,
			@Param("positionCode") String positionCode, @Param("companyId") Long companyId);

	/**
	 * getListByCodeAndCompanyId
	 * 
	 * @param code
	 * @param companyId
	 * @return
	 * @author trieuvd
	 */
	List<JcaAccount> getListByCodeAndCompanyId(@Param("code") String code, @Param("companyId") Long companyId);

	/**
	 * getListByUserNameAndCompanyId
	 * 
	 * @param userName
	 * @param companyId
	 * @return
	 * @author trieuvd
	 */
	List<JcaAccount> getListByUserNameAndCompanyId(@Param("userName") String userName,
			@Param("companyId") Long companyId);

	/**
	 * findListSelect2DtoForCA
	 * 
	 * @param keySearch
	 * @param companyId
	 * @param isPaging
	 * @return
	 * @author trieuvd
	 */
	List<Select2Dto> findListSelect2DtoForCA(@Param("keySearch") String keySearch, @Param("companyId") Long companyId,
			@Param("accountId") Long accountId, @Param("isPaging") boolean isPaging);

	/**
	 * findListEmailByListEmail
	 * 
	 * @param listEmail
	 * @param checkPushEmail
	 * @return
	 * @author trieuvd
	 */
	public List<String> findListEmailByListEmail(@Param("listEmail") List<String> listEmail,
			@Param("checkPushEmail") boolean checkPushEmail);

	/**
	 * @param key
	 * @param companyId
	 * @param isPaging
	 * @return
	 */
	List<Select2Dto> findListSelect2Dto(@Param("key") String key, @Param("companyId") Long companyId,
			@Param("isPaging") boolean isPaging);

	/**
	 * 
	 * getListByListUserNameAndCompanyId
	 * 
	 * @param userName
	 * @param companyId
	 * @return
	 * @author taitt
	 */
	List<JcaAccount> getListByListUserNameAndCompanyId(@Param("userName") List<String> userName,
			@Param("companyId") Long companyId);

	/**
	 * findSelect2DtoByMultipleConditions
	 * 
	 * @param key
	 * @param isPaging
	 * @param companyId
	 * @return
	 * @author trieuvd
	 */
	List<Select2Dto> findSelect2DtoByMultipleConditions(@Param("key") String key, @Param("isPaging") boolean isPaging,
			@Param("companyId") Long companyId);

	/**
	 * @param key
	 * @param isPaging
	 * @return
	 */
	List<Select2Dto> findAllEmailofAllCompany(@Param("accountIds") List<Long> accountIds, @Param("key") String key,
			@Param("isPaging") boolean isPaging);

	/**
	 * getAccountActiveByCodeAndCompanyId
	 * 
	 * @param code
	 * @param companyId
	 * @return
	 * @author trieuvd
	 */
	JcaAccount getAccountActiveByCodeAndCompanyId(@Param("code") String code, @Param("companyId") Long companyId);

	/**
	 * findListAccountInfoSelect2DtoByKeyAndListCompanyId
	 * 
	 * @param key
	 * @param orgId
	 * @param accountId
	 * @param listCompanyId
	 * @param isPaging
	 * @return
	 * @author DaiTrieu
	 */
	public List<JcaAccountInfoSelect2Dto> findListAccountInfoSelect2DtoByKeyAndListCompanyId(@Param("key") String key,
			@Param("orgId") Long orgId, @Param("accountId") Long accountId,
			@Param("listCompanyId") List<Long> listCompanyId, @Param("isPaging") boolean isPaging);

	public List<JcaAccountInfoSelect2Dto> findListAccountInfoSelect2DtoByListAccountId(
			@Param("listAccountId") List<Long> listAccountId,
			@Param("checkPushNotification") boolean checkPushNotification,
			@Param("checkPushEmail") boolean checkPushEmail);

	/**
	 * @author vunt
	 * @param lstIdLdapActive
	 */
	@Modifying
	public void updateDiableAccountLDAP(@Param("lstIdLdapActive") List<Long> lstIdLdapActive);

	public JcaAccount getJcaAccountForLogin(@Param("email") String email, @Param("phone") String phone,
			@Param("accountType") String accountType, @Param("providerId") String providerId);

	public JcaAccount findByUserName(@Param("username") String username);


    @Modifying
    void updateSocialNetwork(@Param("account")JcaAccount acc);

    @Modifying
	 void updatePasswordGad(@Param("accountId")Long accountId, @Param("passwordNew")String passwordNew);

	@Modifying
	 void updateCheckResetPassword(@Param("accountId")Long accountId);
	
	public String checkGad(@Param("agent") String agent);


    JcaAccount findByEmailDto(@Param("email") String email);
}
