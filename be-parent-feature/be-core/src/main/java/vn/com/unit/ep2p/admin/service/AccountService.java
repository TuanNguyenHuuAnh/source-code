/*******************************************************************************
 * Class        AccountService
 * Created date 2016/07/21
 * Lasted date  2016/07/21
 * Author       KhoaNA
 * Change log   2016/07/1901-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.SearchKeyDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.exception.AppException;
import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.dto.JcaAccountOrgDto;
import vn.com.unit.core.dto.JcaAccountSearchDto;
import vn.com.unit.core.dto.JcaAccountTeamDto;
import vn.com.unit.core.dto.JcaRoleForAccountDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.security.UserPrincipal;
import vn.com.unit.core.service.JcaAccountService;
import vn.com.unit.dts.exception.CoreException;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.dto.ConfirmDecreeDto;
import vn.com.unit.ep2p.admin.dto.JcaAccountAddDto;
import vn.com.unit.ep2p.admin.dto.ReturnObject;
import vn.com.unit.ep2p.admin.dto.RoleEditDto;
import vn.com.unit.ep2p.admin.dto.Select2ResultDto;
import vn.com.unit.ep2p.admin.dto.account.JcaAccountDetailDto;
import vn.com.unit.ep2p.admin.dto.account.JcaAccountEditDto;
import vn.com.unit.ep2p.admin.dto.account.JcaAccountInfoSelect2Dto;
import vn.com.unit.ep2p.admin.dto.account.JcaAccountItem;
import vn.com.unit.ep2p.admin.dto.account.JcaAccountOrgDelegateDto;
import vn.com.unit.ep2p.admin.entity.ConfirmDecree;
import vn.com.unit.ep2p.admin.entity.ConfirmSop;
import vn.com.unit.ep2p.dto.AccountItem;
import vn.com.unit.ep2p.dto.CompanyDto;
/**
 * AccountService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface AccountService extends JcaAccountService {

    /**
     * Search account
     *
     * @param page
     * @param accountSearchDto
     * @return PageWrapper<AccountListDto>
     * @author KhoaNA
     * @throws DetailException
     */
    public PageWrapper<JcaAccountDto> search(int page, int pageSize, JcaAccountSearchDto searchDto)
            throws DetailException;

    /**
     * Find account by username
     * 
     * @param username type String
     * @return Account
     * @author KhoaNA
     */
    public JcaAccount findByUserName(String username, Long companyId);

    /**
     * Build UserProfile
     * 
     * @param account     type Account
     * @param authorities type List<GrantedAuthority>
     * @return UserProfile
     * @author KhoaNA
     */
    public UserPrincipal buildUserProfile(JcaAccount account, List<GrantedAuthority> authorities, CompanyDto company);

    /**
     * Init screen account List
     *
     * @param mav    type ModelAndView
     * @param locale type Locale
     * @author KhoaNA
     */
    public void initScreenAccountList(ModelAndView mav, Locale locale);

    /**
     * Init screen account Add or Edit
     *
     * @param mav type ModelAndView
     * @author KhoaNA
     */
    public void initScreenAccountAdd(ModelAndView mav, JcaAccountAddDto accountDto, Locale locale);

    /**
     * Init screen account Add or Edit
     *
     * @param mav               type ModelAndView
     * @param jcaAccountEditDto type JcaAccountEditDto
     * @author KhoaNA
     */
    public void initScreenAccountEdit(ModelAndView mav, Model model, JcaAccountEditDto jcaAccountEditDto,
            Locale locale);

    /**
     * Get JcaAccountEditDto by id
     * 
     * @param id type Long
     * @return JcaAccountEditDto
     * @author KhoaNA
     */
    public JcaAccountEditDto findAccountEditDtoById(Long id);

    /**
     * @param id
     * @return
     */
    public JcaAccountDetailDto findAccountDetailDtoById(Long id);

    /**
     * Convert account To accountEditDto
     *
     * @param account type Account
     * @return JcaAccountEditDto
     * @author KhoaNA
     */
    public JcaAccountEditDto accountToAccountEditDto(JcaAccount account);

    /**
     * Create account
     * 
     * @param accountAddDto type AccountAddDto
     * @param locale
     * @return Account
     * @author KhoaNA
     * @throws AppException
     * @throws ParseException
     */
    public JcaAccount create(JcaAccountAddDto accountAddDto, Locale locale) throws AppException, ParseException;

    /**
     * Update account
     * 
     * @param jcaAccountEditDto type JcaAccountEditDto
     * @param locale
     * @author KhoaNA
     * @throws AppException
     * @throws ParseException
     */
    public void update(JcaAccountEditDto jcaAccountEditDto, Locale locale) throws AppException, ParseException;

    /**
     * Find all role for account
     * 
     * @param accountId
     * @return List<JcaRoleForAccountDto>
     * @author trieunh <trieunh@unit.com.vn>
     */
    public List<JcaRoleForAccountDto> findRoleForAccount(long accountId);

    /**
     * @param accountId
     * @return
     */
    public List<JcaRoleForAccountDto> findRoleForAccountDetail(long accountId);

    /**
     * Find all role
     * 
     * @return List<RoleDto> list
     * @author trieunh <trieunh@unit.com.vn>
     */
    public List<RoleEditDto> findAllRole(Long companyId);

    /**
     * Update list role for account
     * 
     * @param accountDto
     * @author trieunh <trieunh@unit.com.vn>
     * @throws AppException
     */
    public void updateRoleForAccount(JcaAccountEditDto accountDto) throws AppException;

    /**
     * Delete role for account.
     * 
     * @param roleForAccountId
     * @author trieunh <trieunh@unit.com.vn>
     * @throws AppException
     */
    public void deleteRoleForAccount(Long roleId, Long accountId) throws AppException;

    /**
     * Check duplicate role. True is duplicated.
     * 
     * @param List<JcaRoleForAccountDto> listRoleForAccountDto
     * @return boolean
     * @author trieunh <trieunh@unit.com.vn>
     */
    public boolean checkDuplicateRole(List<JcaRoleForAccountDto> listRoleForAccountDto);

    /**
     * Check start date and end date.
     * 
     * @param listRoleForAccountDto
     * @return boolean
     * @author trieunh <trieunh@unit.com.vn>
     */
    public boolean checkStartEndDate(List<JcaRoleForAccountDto> listRoleForAccountDto);

    /**
     * 
     * @param locale
     * @return
     */
    public List<SearchKeyDto> genSearchKeyList(Locale locale);

    /**
     * @param id
     */
    public void delete(Long id);

    /**
     * updateFailedLoginCount
     *
     * @param accountId
     * @param failedLoginCount
     * @author HUNGHT
     */
    public void updateFailedLoginCount(Long accountId, int failedLoginCount, Date loginDate);

    /**
     * disabledAccount
     *
     * @param accountId
     * @param updatedBy
     * @param updatedDate
     * @author HUNGHT
     */
    public void disabledAccount(Long accountId, String updatedBy, Date updatedDate);

    /**
     * changePassword
     *
     * @param accountId
     * @param password
     * @author phatvt
     */
    void changePassword(Long accountId, String password);

    /**
     * findByInputQuery
     *
     * @param inputQuery
     * @return
     * @author VinhLT
     */
    List<JcaAccountDto> findByInputQuery(String inputQuery);

    /**
     * findListAccountHasRoleByItem
     *
     * @param itemCode
     * @return
     * @author VinhLT
     */
    List<JcaAccount> findListAccountHasRoleByItem(String itemCode);

    /**
     * findListAccountItem
     *
     * @param itemCode
     * @param companyId
     * @return
     * @author VinhLT
     */
    List<JcaAccountItem> findListAccountItem(String itemCode, Long companyId);

    /**
     * findLstEmailByLstUsername
     *
     * @param lstUsername
     * @return
     * @author VinhLT
     */
    JcaAccountDto findLstEmailByLstUsername(String lstUsername);

    /**
     * countNumberAccountByCompanyId
     * 
     * @param companyId
     * @return
     * @author HungHT
     */
    long countNumberAccountByCompanyId(Long companyId);

    /**
     * findByUserNameAndCompanyId
     * 
     * @param username
     * @param companyId
     * @return
     * @author HungHT
     */
    JcaAccount findByUserNameAndCompanyId(String username, Long companyId);

    /**
     * getListByUserName
     * 
     * @param username
     * @return
     * @author HungHT
     */
    List<JcaAccount> getListByUserName(String username);

    /**
     * findListAccountItemByItemList
     *
     * @param lstItemCode
     * @param companyId
     * @return
     * @author VinhLT
     */
    List<JcaAccountItem> findListAccountItemByItemList(List<String> lstItemCode, Long companyId);

    /**
     * findAccountTeamByAccountId
     * 
     * @param accountId
     * @return
     * @author trieuvd
     */
    List<JcaAccountTeamDto> findAccountTeamByAccountId(Long accountId);

    void updateUserProfile(Long userId, boolean pushNotification, String phoneNumber, boolean pushEmail,
            boolean archiveFlag, String lang) throws AppException;

    List<JcaAccountDto> getListAccountByCompanyId(Long companyId) throws Exception;

    void updateAvatar(Long userId, MultipartFile avatarFile, String lang, Locale locale) throws AppException;

    /**
     * findByEmail
     * 
     * @param email
     * @return
     * @author trieuvd
     */
    JcaAccount findByEmail(String email);
    
    /**
     * getByPhone
     * 
     * @param phone
     * @return
     * @author TaiTM
     */
    public JcaAccount getByPhone(String phone);

    /**
     * findByCmnd
     * 
     * @param cmnd
     * @return
     * @author hungp
     */
    JcaAccount findByCmnd(String cmnd);

    /**
     * getAllAcountActive
     * 
     * @return
     * @author TuyenTD
     */
    List<JcaAccountDto> getAllAcountActive(Boolean isPaging) throws SQLException;

    /**
     * getSelect2DtoListByKeyAndListCompanyId
     * 
     * @param key
     * @param orgId
     * @param accountId
     * @param listCompanyId
     * @param isPaging
     * @return List<Select2Dto>
     * @author KhuongTH
     */
    List<Select2Dto> getSelect2DtoListByKeyAndListCompanyId(String key, Long orgId, Long accountId,
            List<Long> listCompanyId, boolean isPaging);

    /**
     * 
     * getResAssigneDtoListByKeyAndListCompanyId
     * 
     * @param key
     * @param listCompanyId
     * @param pageSize
     * @param pageIndex
     * @param isPaging
     * @return
     * @author taitt
     */
    List<Select2Dto> getSelect2DtoListByKeyAndListCompanyIdByPaging(String key, List<Long> listCompanyId, int pageSize,
            int pageIndex, int isPaging);

    /**
     * 
     * countAccountListByKeyAndListCompanyId
     * 
     * @param key
     * @param listCompanyId
     * @return
     * @author taitt
     */
    Long countAccountListByKeyAndListCompanyId(String key, Long orgId, Long accountId, List<Long> companyIds);

    /**
     * getSelect2DtoListByListAccountId
     * 
     * @param listAccountId
     * @return List<Select2Dto>
     * @author KhuongTH
     */
    List<Select2Dto> getSelect2DtoListByListAccountId(List<Long> listAccountId);

    /**
     * findByCode
     * 
     * @param code
     * @param companyId
     * @return
     * @author trieuvd
     */
    JcaAccount findByCode(String code, Long companyId);

    /**
     * updatePositionIdByPositionCode
     * 
     * @param positionId
     * @param positionCode
     * @param companyId
     * @author trieuvd
     */
    void updatePositionIdByPositionCode(Long positionId, String positionCode, Long companyId);

    /**
     * findListSelect2DtoForCA
     * 
     * @param keySearch
     * @param companyId
     * @param isPaging
     * @return
     * @author trieuvd
     */
    List<Select2Dto> findListSelect2DtoForCA(String keySearch, Long companyId, Long caId, boolean isPaging);

    /**
     * @param ids
     * @return
     * @throws Exception
     */
    List<JcaAccountDto> getAccountListByIds(List<Long> ids) throws Exception;

    /**
     * @param id
     * @return
     * @throws Exception
     */
    JcaAccount findById(Long id);

    /**
     * findAccountDtoForPushNotificationByListId
     * 
     * @param ids
     * @return
     * @author trieuvd
     * @throws Exception
     */
    List<JcaAccountDto> findAccountDtoForPushNotificationByListId(List<Long> ids) throws Exception;

    /**
     * findAccountDtoForPushEmailByListId
     * 
     * @param ids
     * @return
     * @author trieuvd
     * @throws Exception
     */
    List<JcaAccountDto> findAccountDtoForPushEmailByListId(List<Long> ids) throws Exception;

    /**
     * updateInfo
     * 
     * @param jcaAccountEditDto
     * @param locale
     * @throws AppException
     * @throws ParseException
     * @author trieuvd
     */
    public void updateInfo(JcaAccountEditDto jcaAccountEditDto, Locale locale) throws AppException;

    /**
     * @param key
     * @param companyId
     * @param isPaging
     * @return
     */
    List<Select2Dto> getListSelect2Dto(String key, Long companyId, boolean isPaging) throws Exception;

    /**
     * 
     * getListAccountByListUserNameAndCompany
     * 
     * @param userName
     * @param companyId
     * @return
     * @author taitt
     */
    List<JcaAccount> getListAccountByListUserNameAndCompany(List<String> userName, Long companyId);

    /**
     * 
     * findOrgByAccountId
     * 
     * @param accountId
     * @return
     * @author taitt
     */
    List<JcaAccountOrgDto> findOrgByAccountId(Long accountId);

    /**
     * findSelect2DtoByMultipleConditions
     * 
     * @param key
     * @param isPaging
     * @param companyId
     * @return
     * @author trieuvd
     */
    List<Select2Dto> findSelect2DtoByMultipleConditions(String key, boolean isPaging, Long companyId);

    /**
     * @param accountIds
     * @param key
     * @param isPaging
     * @return
     * @throws SQLException
     * @throws NullPointerException
     */
    Select2ResultDto getAllEmailofAllCompany(List<Long> accountIds, String key, boolean isPaging)
            throws SQLException, NullPointerException;

    /**
     * @param codes
     * @param companyId
     * @return
     * @throws SQLException
     */
    List<Long> getGroupIdsByCodes(List<String> codes, Long companyId) throws SQLException;

    /**
     * getSelect2DtoByOrg
     * 
     * @param key
     * @param orgId
     * @param isPaging
     * @return
     * @author KhuongTH
     */
    List<Select2Dto> getSelect2DtoByOrg(String key, Long orgId, boolean isPaging);

    /**
     * getAccountActiveByCodeAndCompanyId
     * 
     * @param code
     * @param companyId
     * @return
     * @author trieuvd
     */
    JcaAccount getAccountActiveByCodeAndCompanyId(String code, Long companyId);

    /**
     * getGroupsByAccountId
     * 
     * @param accountId
     * @return
     */
    List<Long> getGroupsByAccountId(Long accountId) throws SQLException;

    /**
     * getPositionsByAccountId
     * 
     * @param accountId
     * @return
     * @throws SQLException
     */
    List<Long> getPositionsByAccountId(Long accountId) throws SQLException;

    /**
     * getOrgsOfOwnerById
     * 
     * @param accountId
     * @return
     * @throws SQLException
     */
    List<JcaAccountOrgDto> getOrgsOfOwnerById(Long accountId) throws SQLException;

    /**
     * countOldOrgForDelegate
     * 
     * @param id
     * @param accountId
     * @param orgName
     * @return
     * @throws SQLException
     * @author hiennt
     */
    Integer countOldOrgForDelegate(Long id, Long accountId, String orgName) throws SQLException;

    /**
     * getOldOrgForDelegate
     * 
     * @param id
     * @param accountId
     * @param orgName
     * @param deletedBy
     * @param startIndex
     * @param sizeOfPage
     * @return
     * @throws SQLException
     * @author hiennt
     */
    List<JcaAccountOrgDelegateDto> getOldOrgForDelegate(Long id, Long accountId, String orgName, String deletedBy,
            int startIndex, int sizeOfPage) throws SQLException;

    /**
     * getOrgsForDelegator
     * 
     * @param accountId
     * @param ids
     * @return
     * @throws SQLException
     * @author hiennt
     */
    List<JcaAccountOrgDto> getOrgsForDelegator(Long accountId, List<Long> ids) throws SQLException;

    /**
     * getAccountInfoSelect2DtoListByKeyAndListCompanyId
     * 
     * @param key
     * @param orgId
     * @param accountId
     * @param companyIds
     * @param isPaging
     * @return
     * @author DaiTrieu
     */
    public List<JcaAccountInfoSelect2Dto> getAccountInfoSelect2DtoListByKeyAndListCompanyId(String key, Long orgId,
            Long accountId, List<Long> companyIds, boolean isPaging);

    List<JcaAccountInfoSelect2Dto> getAccountInfoSelect2DtoListByListAccountId(List<Long> listAccountId);

    /**
     * @return
     */
    boolean syncDataJob();

    /**
     * @return
     */
    boolean syncLDAP();

    /**
     * @author vunt
     */
    void syncData();

    List<AccountItem> findListAccountItemByItemList(List<String> lstItemCode);

    List<AccountItem> listAllUser();

    List<AccountItem> findListAccountItemByItemAndDepartment(List<String> lstItemCode, List<String> lstDepartment);

	public JcaAccount getJcaAccountForLogin(String email, String phone, String accountType, String providerId);

	JcaAccountDto updateAccountAvatar(Long userId, String urlZalo, String urlFacebook, MultipartFile avatarFile, String lang,
			Locale locale) throws AppException, CoreException;

	public ReturnObject syncAccountByUsername(String username, Locale locale);

	public JcaAccountDto switchAccount(Long userId, JcaAccountDto account) throws AppException, CoreException;

	public void updatePasswordGad(Long userId, String encryptedPassword) throws DetailException;

	public void isCheckResetPassword(Long userId) throws DetailException;

	public String checkGad(String agent);

	public Object updateFaceMask(Long userId, String faceMask) throws AppException, CoreException;

    JcaAccount findByEmailDto(String email);
    public ConfirmDecreeDto saveConfirmDecree(ConfirmDecreeDto confirmDto);
    public boolean checkConfirmDecree(String userName);
    public boolean saveConfirmSop();
    public boolean checkConfirmSop(String userName);
}
