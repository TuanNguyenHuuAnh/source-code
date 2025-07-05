/*******************************************************************************
 * Class        AccountServiceImpl
 * Created date 2016/07/21
 * Lasted date  2016/07/21
 * Author       KhoaNA
 * Change log   2016/07/1901-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.PagedResultsResponseControl;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.PartnerDto;
import vn.com.unit.common.dto.SearchKeyDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.exception.AppException;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonSearchUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.constant.CoreExceptionCodeConstant;
import vn.com.unit.core.dto.FileResultDto;
import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.dto.JcaAccountOrgDto;
import vn.com.unit.core.dto.JcaAccountSearchDto;
import vn.com.unit.core.dto.JcaAccountTeamDto;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.JcaPositionDto;
import vn.com.unit.core.dto.JcaRoleForAccountDto;
import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.entity.JcaAccountTeam;
import vn.com.unit.core.entity.JcaCaManagement;
import vn.com.unit.core.entity.JcaRoleForAccount;
import vn.com.unit.core.enumdef.AccountTypeEnum;
import vn.com.unit.core.enumdef.param.JcaAccountSearchEnum;
import vn.com.unit.core.security.UserPrincipal;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.core.service.JRepositoryService;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.core.service.impl.JcaAccountServiceImpl;
import vn.com.unit.dts.constant.DtsConstant;
import vn.com.unit.dts.exception.CoreException;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.DirectoryConstant;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.Utils;
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
import vn.com.unit.ep2p.admin.enumdef.ConstantDisplayType;
import vn.com.unit.ep2p.admin.exception.BusinessException;
import vn.com.unit.ep2p.admin.repository.AccountOrgRepository;
import vn.com.unit.ep2p.admin.repository.AccountRepository;
import vn.com.unit.ep2p.admin.repository.AccountTeamRepository;
import vn.com.unit.ep2p.admin.repository.CaManagementRepository;
import vn.com.unit.ep2p.admin.repository.ConfirmDecreeRepository;
import vn.com.unit.ep2p.admin.repository.ConfirmSopRepository;
import vn.com.unit.ep2p.admin.repository.RoleForAccountRepository;
import vn.com.unit.ep2p.admin.service.AbstractCommonService;
import vn.com.unit.ep2p.admin.service.AccountPasswordService;
import vn.com.unit.ep2p.admin.service.AccountService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.admin.service.JpmIdentityService;
import vn.com.unit.ep2p.admin.service.PositionService;
import vn.com.unit.ep2p.admin.service.RoleService;
import vn.com.unit.ep2p.admin.service.TeamService;
import vn.com.unit.ep2p.admin.utils.JCanaryPasswordUtil;
import vn.com.unit.ep2p.constant.ApiAccountTypeConstant;
import vn.com.unit.ep2p.constant.CommonConstant;
import vn.com.unit.ep2p.dto.AccountItem;
import vn.com.unit.ep2p.dto.CompanyDto;
import vn.com.unit.ep2p.utils.LangugeUtil;
import vn.com.unit.imp.excel.annotation.CoreTx;
import vn.com.unit.storage.dto.FileUploadParamDto;
import vn.com.unit.storage.dto.FileUploadResultDto;
import vn.com.unit.storage.entity.JcaRepository;
import vn.com.unit.storage.service.FileStorageService;

/**
 * AccountServiceImpl.
 *
 * @author KhoaNA
 * @version 01-00
 * @since 01-00
 */
@Service
@Primary
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl extends JcaAccountServiceImpl implements AccountService, AbstractCommonService {

    /** The Constant USERNAME. */
    private static final String USERNAME = "USERNAME";

    /** The Constant FULLNAME. */
    private static final String FULLNAME = "FULLNAME";

    /** The Constant EMAIL. */
    private static final String EMAIL = "EMAIL";

    // private static final String BIRTHDAY = "birthday";

    /** The Constant PHONE. */
    private static final String PHONE = "PHONE";

    // private static final String STATUS = "status";

    // private static final String DEPARTMENT_NAME = "departmentName";

    /** The Constant POSITION_NAME. */
//    private static final String POSITION_NAME = "POSITIONNAME";

    /** The Constant ORG_NAME. */
//    private static final String ORG_NAME = "ORGNAME";

    /** The Constant accountSearchFieldDispNames. */
    private static final String[] accountSearchFieldDispNames = { "searchfield.account.username",
            "searchfield.account.fullname", "searchfield.account.email"
            /*
             * "searchfield.account.birthday", "searchfield.account.positionname", ,
             * "searchfield.account.org"
             */, "searchfield.account.phone" };

    /** The Constant accountSearchFieldIds. */
    private static final String[] accountSearchFieldIds = { USERNAME, FULLNAME, EMAIL /* POSITION_NAME, , ORG_NAME */,
            PHONE };

    /** logger. */
    // private static final Logger logger =
    // LoggerFactory.getLogger(AccountServiceImpl.class);

    /** accountRepository */
    @Autowired
    private AccountRepository accRepository;

    /** positionService. */
    @Autowired
    private PositionService positionService;

    /** systemConfig. */
    @Autowired
    private SystemConfig systemConfig;

    /** The role for account repository. */
    @Autowired
    private RoleForAccountRepository roleForAccountRepository;

    /** The msg. */
    @Autowired
    private MessageSource msg;

    /** The jca constant service. */
    @Autowired
    private JcaConstantService jcaConstantService;

    /** The account password service. */
    @Autowired
    private AccountPasswordService accountPasswordService;

    /** The j repository service. */
    @Autowired
    private JRepositoryService jRepositoryService;

    /** The role service. */
    @Autowired
    private RoleService roleService;

    /** The language service. */
//    @Autowired
//    private AppLanguageService languageService;

    /** The com service. */
    @Autowired
    private CommonService comService;

    /** The company service. */
    @Autowired
    private CompanyService companyService;

    /** The account team repository. */
    @Autowired
    private AccountTeamRepository accountTeamRepository;

    /** The jpm identity service. */
    @Autowired
    private JpmIdentityService jpmIdentityService;

    /** The team service. */
    @Autowired
    private TeamService teamService;

    /** The acc org repository. */
    @Autowired
    private AccountOrgRepository accOrgRepository;

    /** The ca management repository. */
    @Autowired
    private CaManagementRepository caManagementRepository;

    /** The file storage service. */
    @Autowired
    private FileStorageService fileStorageService;
    
    /** The db2 service **/
    @Autowired
    private Db2ApiService db2ApiService;

    /** The password encoder. */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /** The model mapper. */
    // Model mapper
    ModelMapper modelMapper = new ModelMapper();

    /** The object mapper. */
    @Autowired
    private ObjectMapper objectMapper;
    
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private  HttpServletRequest request;
    

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private String ldapInitialContextFactory = "";
    private String ldapProviderUrl = "";
    private String ldapSecurityAuthentication = "";
    private String ldapSecurityPrincipal = "";
    private String ldapSecurityCredentials = "";

    private String ldapMainGroup = "";
    @SuppressWarnings("unused")
	private String ldapPeopleGroup = "";
    private String ldapServiceGroup = "";

    @SuppressWarnings("unused")
	private String ldapCnFilter = "";
    @SuppressWarnings("unused")
	private String ldapAccountFilter = "";
    private String ldapPrefixFilter = "";

    @SuppressWarnings("unused")
	private String ldapDomain = "";
    
	@Autowired
    private ConfirmDecreeRepository confirmDecreeRepository;
	
	@Autowired
    private ConfirmSopRepository confirmSopRepository;
//    private static final String USERNAME_FILTER = "%USERNAME%";
    private static final String COMMA = ",";
    private static final String DISPLAY_NAME = "displayName";
    private static final String MAIL = "mail";
    private static final String SAMACCOUNT_NAME = "sAMAccountName";
//    private static final String DEPARTMENT = "department";
//    private static final String DIVISION = "division";

    DirContext context = null;

    private void initialParam() {
        ldapInitialContextFactory = systemConfig.getConfig(SystemConfig.LDAP_INITIAL_CONTEXT_FACTORY);
        ldapProviderUrl = systemConfig.getConfig(SystemConfig.LDAP_PROVIDER_URL);
        ldapSecurityAuthentication = systemConfig.getConfig(SystemConfig.LDAP_SECURITY_AUTHENTICATION);
        ldapSecurityPrincipal = systemConfig.getConfig(SystemConfig.LDAP_SECURITY_PRINCIPAL);
        try {
            ldapSecurityCredentials = JCanaryPasswordUtil
                    .decryptString(systemConfig.getConfig(SystemConfig.LDAP_SECURITY_CREDENTIALS));
        } catch (Exception e) {
            logger.error(Message.ERROR, e);
        }
        ldapMainGroup = systemConfig.getConfig(SystemConfig.LDAP_MAIN_GROUP);
        ldapPeopleGroup = systemConfig.getConfig(SystemConfig.LDAP_PEOPLE_GROUP);
        ldapServiceGroup = systemConfig.getConfig(SystemConfig.LDAP_SERVICE_GROUP);
        ldapCnFilter = systemConfig.getConfig(SystemConfig.LDAP_CN_FILTER);
        ldapAccountFilter = systemConfig.getConfig(SystemConfig.LDAP_ACCOUNT_FILTER);
        ldapPrefixFilter = systemConfig.getConfig(SystemConfig.LDAP_PREFIX_FILTER);
        ldapDomain = systemConfig.getConfig(SystemConfig.LDAP_DOMAIN);
    }

    @SuppressWarnings("unused")
	private void initialBindEnvironment() {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, ldapInitialContextFactory);
        env.put(Context.PROVIDER_URL, ldapProviderUrl);
        env.put(Context.SECURITY_AUTHENTICATION, ldapSecurityAuthentication);
        // env.put(Context.SECURITY_PRINCIPAL, CN_EQUAL + ldapSecurityPrincipal + COMMA
        // + ldapPeopleGroup);
        env.put(Context.SECURITY_PRINCIPAL,
                ldapPrefixFilter + ConstantCore.EQUAL + ldapSecurityPrincipal + COMMA + ldapServiceGroup);
        env.put(Context.SECURITY_CREDENTIALS, ldapSecurityCredentials);

        if (context == null) {
            try {
                context = new InitialDirContext(env);
            } catch (NamingException e) {
                logger.error(Message.ERROR, e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#genSearchKeyList(java.util.
     * Locale)
     */
    @Override
    public List<SearchKeyDto> genSearchKeyList(Locale locale) {
        List<SearchKeyDto> searchKeys = CommonSearchUtil.genSearchKeyList(accountSearchFieldIds,
                accountSearchFieldDispNames, locale, msg);
        return searchKeys;
    }

    /**
     * <p>
     * Builds the jca account search dto.
     * </p>
     *
     * @author Tan Tai
     * @param commonSearch type {@link MultiValueMap<String,String>}
     * @return {@link JcaAccountSearchDto}
     */
    private JcaAccountSearchDto buildJcaAccountSearchDto(MultiValueMap<String, String> commonSearch) {
        JcaAccountSearchDto reqSearch = new JcaAccountSearchDto();
        String keySearch = CommonStringUtil.isNotBlank(commonSearch.getFirst("searchValue"))
                ? commonSearch.getFirst("searchValue")
                : DtsConstant.EMPTY;
        List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("searchKeyIds"))
                ? java.util.Arrays.asList(CommonStringUtil.split(commonSearch.getFirst("searchKeyIds"), ","))
                : null;
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId"))
                : null;
        Boolean locked = null != commonSearch.getFirst("locked") ? Boolean.valueOf(commonSearch.getFirst("locked"))
                : false;
        Boolean enabled = null != commonSearch.getFirst("enabled") ? Boolean.valueOf(commonSearch.getFirst("enabled"))
                : false;

        reqSearch.setActived(!locked);
        reqSearch.setEnabled(enabled);
        reqSearch.setCompanyId(companyId);

        if (CommonCollectionUtil.isNotEmpty(enumsValues)) {
            for (String enumsValue : enumsValues) {
                try {
                    if (enumsValue.equals("POSITIONNAME")) {
                        reqSearch.setPositionName(keySearch);
                    }

                    if (enumsValue.equals("ORGNAME")) {
                        reqSearch.setOrgName(keySearch);
                    }

                    JcaAccountSearchEnum accountSearch = null;

                    try {
                        accountSearch = JcaAccountSearchEnum.valueOf(enumsValue);
                    } catch (Exception e) {
                    }

                    if (null != accountSearch) {
                        switch (accountSearch) {
                        case EMAIL:
                            reqSearch.setEmail(keySearch);
                            break;
                        case FULLNAME:
                            reqSearch.setFullName(keySearch);
                            break;
                        case PHONE:
                            reqSearch.setPhone(keySearch);
                            break;
                        case USERNAME:
                            reqSearch.setUsername(keySearch);
                            break;
                        default:
                            reqSearch.setEmail(keySearch);
                            reqSearch.setUsername(keySearch);
                            reqSearch.setPhone(keySearch);
                            reqSearch.setFullName(keySearch);
                            break;
                        }
                    }
                } catch (Exception e) {
                    reqSearch.setEmail(keySearch);
                    reqSearch.setUsername(keySearch);
                    reqSearch.setPhone(keySearch);
                    reqSearch.setFullName(keySearch);
                }
            }
        } else {
            reqSearch.setEmail(keySearch);
            reqSearch.setUsername(keySearch);
            reqSearch.setPhone(keySearch);
            reqSearch.setFullName(keySearch);
        }
        return reqSearch;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.admin.service.AccountService#search(int, int,
     * vn.com.unit.core.dto.JcaAccountSearchDto)
     */
    @Override
    public PageWrapper<JcaAccountDto> search(int page, int pageSize, JcaAccountSearchDto searchDto)
            throws DetailException {

        // Init PageWrapper
        PageWrapper<JcaAccountDto> pageWrapper = new PageWrapper<JcaAccountDto>();
        int sizeOfPage = systemConfig.settingPageSizeList(pageSize, pageWrapper, page);
        
        /** init pageable */
        Pageable pageableAfterBuild = this.buildPageable(PageRequest.of(page - 1, sizeOfPage), JcaAccount.class,
                TABLE_ALIAS_JCA_ACCOUNT);

        /** init param search repository */
        MultiValueMap<String, String> commonSearch = CommonUtil.convert(searchDto, objectMapper);
        JcaAccountSearchDto reqSearch = this.buildJcaAccountSearchDto(commonSearch);

        int count = this.countAccountDtoByCondition(reqSearch);
        List<JcaAccountDto> accountList = new ArrayList<>();
        if (count > 0) {
            accountList = this.getAccountDtoByCondition(reqSearch, pageableAfterBuild);
        }

        for (JcaAccountDto accountListDto : accountList) {
            List<JcaAccountOrgDto> listAccountOrgDto = accOrgRepository
                    .findAccountOrgDtoByAccountId(accountListDto.getUserId());
            String orgNames = listAccountOrgDto.stream().map(JcaAccountOrgDto::getOrgName).distinct()
                    .collect(Collectors.joining(", "));
            String positionNames = listAccountOrgDto.stream().map(JcaAccountOrgDto::getPositionName).distinct()
                    .collect(Collectors.joining(", "));
            accountListDto.setOrgName(orgNames);
            accountListDto.setPositionName(positionNames);
        }

        try {
            if (CommonStringUtil.isNotBlank(reqSearch.getPositionName())
                    || CommonStringUtil.isNotBlank(reqSearch.getOrgName())) {
                accountList = accountList.stream().filter(acc -> isMatchSearch(acc, reqSearch))
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            logger.debug("[Acount search]", e);
        }
        
        pageWrapper.setDataAndCount(accountList, count);
        return pageWrapper;
    }

    private boolean isMatchSearch(JcaAccountDto acc, JcaAccountSearchDto reqSearch) {
        if (CommonStringUtil.isNotBlank(reqSearch.getPositionName())) {
            if (null != acc.getPositionName()
                    && acc.getPositionName().toLowerCase().contains(reqSearch.getPositionName().toLowerCase())) {
                return true;
            }
        }

        if (CommonStringUtil.isNotBlank(reqSearch.getOrgName())) {
            if (null != acc.getCompanyName()
                    && acc.getCompanyName().toLowerCase().contains(reqSearch.getOrgName().toLowerCase())) {
                return true;
            }
        }

        return false;
    }

//	private AccountSearchDto genSearchCondition(AccountSearchDto searchDto) {
//		String searchValue = searchDto.getSearchValue() == null ? null : searchDto.getSearchValue().trim();
//		AccountSearchDto searchCondition = new AccountSearchDto();
//		if (searchDto.getSearchKeyIds() == null || searchDto.getSearchKeyIds().isEmpty()) {
//			searchCondition.setUserName(searchValue);
//			searchCondition.setFullName(searchValue);
//			searchCondition.setPhone(searchValue);
//			searchCondition.setEmail(searchValue);
//			if ("0".equals(searchValue) || "1".equals(searchValue)) {
//				searchCondition.setStatus(searchValue);
//			}
//			searchCondition.setBranchName(searchValue);
//			searchCondition.setPositionName(searchValue);
//			searchCondition.setDepartmentName(searchValue);
//		} else {
//			List<String> searchKeyIds = searchDto.getSearchKeyIds();
//			for (String searchKeyId : searchKeyIds) {
//				if (searchKeyId.equals(USER_NAME)) {
//					searchCondition.setUserName(searchValue);
//				}
//				if (searchKeyId.equals(FULL_NAME)) {
//					searchCondition.setFullName(searchValue);
//				}
//
//				if (searchKeyId.equals(EMAIL)) {
//					searchCondition.setEmail(searchValue);
//				}
//				if (searchKeyId.equals(PHONE)) {
//					searchCondition.setPhone(searchValue);
//				}
//				// if (searchKeyId.equals(BIRTHDAY)) {
//				// String[] datePatterm = new String[] { "dd/MM/yyyy",
//				// "dd-MM-yyyy" };
//				// Date birthDate;
//				// try {
//				// birthDate = DateUtils.parseDate(searchValue, datePatterm);
//				// } catch (ParseException e) {
//				// birthDate = null;
//				// }
//				// searchCondition.setBirthday(birthDate);
//				// }
//				if (searchKeyId.equals(BRANCH_NAME)) {
//					searchCondition.setBranchName(searchValue);
//				}
//				if (searchKeyId.equals(POSITION_NAME)) {
//					searchCondition.setPositionName(searchValue);
//				}
//				if (searchKeyId.equals(ORG_NAME)) {
//					searchCondition.setOrgName(searchValue);
//				}
//			}
//		}
//		// Add search CompanyId
//		searchCondition.setCompanyId(searchDto.getCompanyId());
////		searchCondition.setCompanyIdList(UserProfileUtils.getCompanyIdList());
////		searchCondition.setCompanyAdmin(UserProfileUtils.isCompanyAdmin());
//		
//		searchCondition.setEnabled(searchDto.getEnabled());
//		searchCondition.setLocked(searchDto.getLocked());
//		searchCondition.setUnknownOrg(searchDto.getUnknownOrg());
//		searchCondition.setEmptyOrg(searchDto.getEmptyOrg());
//		searchCondition.setUnknownPosition(searchDto.getUnknownPosition());
//		searchCondition.setEmptyPosition(searchDto.getEmptyPosition());
//		searchCondition.setBOD(searchDto.getBOD());
//		searchCondition.setSentBOD(searchDto.getSentBOD());
//		
//		return searchCondition;
//	}

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.admin.service.AccountService#findByUserName(java.lang.
     * String, java.lang.Long)
     */
    @Override
    public JcaAccount findByUserName(String username, Long companyId) {
        JcaAccount account = null;
        if (null == companyId) {
            List<JcaAccount> accountList = accRepository.getListByUserName(username);
            if (null != accountList && accountList.size() > 1) {
                account = accRepository.findByUserNameAndCompanyId(username, companyId);
            } else if (null != accountList && accountList.size() > 0) {
                account = accountList.get(0);
            }
        } else {
            account = accRepository.findByUserNameAndCompanyId(username, companyId);
        }
        return account;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#buildUserProfile(vn.com.unit.
     * core.entity.JcaAccount, java.util.List, vn.com.unit.mbal.dto.CompanyDto)
     */
    @Override
    public UserPrincipal buildUserProfile(JcaAccount account, List<GrantedAuthority> authorities, CompanyDto company) {
        String username = account.getUsername();
        String password = account.getPassword();
//		boolean fingerprint = account.isFingerprint();
//		boolean pushNotification = account.getPushNotification();
//		boolean pushEmail = account.getPushEmail();
//		String cmnd = account.getCmnd();
        UserPrincipal uProfile = new UserPrincipal(username, password, true, true, true, true, authorities,
                account.getCreatedDate(), account.getPositionId(), account.getFaceMask());

        // Map<String, RuntimeEngine> runtimeEngines = new HashMap<String,
        // RuntimeEngine>();
        // uProfile.setRuntimeEngines(runtimeEngines);

        Long accountId = account.getId();
        uProfile.setAccountId(accountId);

        String fullname = account.getFullname();
        uProfile.setFullname(fullname);

        String email = account.getEmail();
        uProfile.setEmail(email);

        Date birthday = account.getBirthday();
        uProfile.setBirthday(birthday);

        String avatar = account.getAvatar();
        uProfile.setAvatar(avatar);

        uProfile.setAvatarRepoId(account.getAvatarRepoId());

        uProfile.setChannel(account.getChannel());
//		Long branchId = account.getBranchId();
//		uProfile.setBranchId(branchId);

//		Long departmentId = account.getDepartmentId();
//		uProfile.setDepartmentId(departmentId);

//		uProfile.setPushNotification(pushNotification);

//		uProfile.setFingerprint(fingerprint);
//		uProfile.setPushEmail(pushEmail);

//		String phone = account.getPhone();
//		uProfile.setPhone(phone);

        List<Long> companyIdList = new ArrayList<>();
        List<Long> companyIdDataList = new ArrayList<>();
        List<Long> companyIdEmailList = new ArrayList<>();
        if (systemConfig.getIntConfig(SystemConfig.LIMIT_NUMBER_COMPANY_ID_ADMIN) >= company.getId()) {
            uProfile.setCompanyAdmin(true);
            List<Long> companyList = accRepository.getAllCompanyId();
            companyIdList.addAll(companyList);
            companyIdDataList.addAll(companyList);
            companyIdEmailList.addAll(companyList);
        } else {
            companyIdList.add(company.getId());
            companyIdDataList.add(company.getId());
            companyIdEmailList.add(company.getId());
//            List<CompanyPermissionDto> companyPermissionList = accRepository.getCompanyIdListByAccountId(accountId, company.getId());
//            if (null != companyPermissionList && !companyPermissionList.isEmpty()) {
//                Long beforeCompanyId = null;
//                Long beforeCompanyIdOrg = null;
//                Long beforeEmailCompanyId = null;
//                Long companyId = null;
//                for (CompanyPermissionDto companyPermission : companyPermissionList) {
//                    companyId = companyPermission.getCompanyId();
//                    if ((null == beforeCompanyId || beforeCompanyId != companyId) && companyPermission.getIsAdmin() == 1) {
//                        companyIdList.add(companyId);
//                        beforeCompanyId = companyId;
//                    }
//                    if ((null == beforeCompanyIdOrg || beforeCompanyIdOrg != companyId) && null != companyPermission.getOrgId()) {
//                        companyIdDataList.add(companyId);
//                        beforeCompanyIdOrg = companyId;
//                    }
//                    if ((null == beforeEmailCompanyId || beforeEmailCompanyId != companyId) && companyPermission.getIsDisplayEmail() == 1) {
//                    	companyIdEmailList.add(companyId);
//                    	beforeEmailCompanyId = companyId;
//                    }
//                }
//            }
        }

//		List<OrgInfo> departmentList = new ArrayList<>();
//		List<JcaAccountOrgDto> depListSelect2Dto = accOrgRepository.findOrgByAccountId(null, accountId, false);
//		try {
//		    /*Set<Long> orgIds = CollectionUtils.isEmpty(depListSelect2Dto) ? null : depListSelect2Dto.stream().map(JcaAccountOrgDto::getId).collect(Collectors.toSet());
//            List<JcaAccountOrgDto> orgsForDelegator = CollectionUtils.isEmpty(orgIds) ? getOrgsForDelegator(accountId, null) 
//                    : getOrgsForDelegator(accountId, new ArrayList<>(orgIds));
//            if(CollectionUtils.isNotEmpty(orgsForDelegator)) {
//                depListSelect2Dto.addAll(orgsForDelegator);
//            }*/
//		    
//		    List<JcaAccountOrgDto> orgsForDelegator = getOrgsForDelegator(accountId, null);
//		    
//		    if(CollectionUtils.isEmpty(depListSelect2Dto)) {
//		        depListSelect2Dto = new ArrayList<>();
//		    }
//		    
//		    if(CollectionUtils.isNotEmpty(orgsForDelegator)) {
//                depListSelect2Dto.addAll(orgsForDelegator);
//            }
//		    
//		    depListSelect2Dto = depListSelect2Dto.stream().filter(distinctByKey(JcaAccountOrgDto::getAccountOrgId)).collect(Collectors.toList());
//		    
//        } catch (SQLException e) {
//            logger.error("Exception ", e);
//        }

//		for (JcaAccountOrgDto orgInfo: depListSelect2Dto){
//			OrgInfo org = new OrgInfo();
//			org.setId(orgInfo.getAccountOrgId());
//			org.setOrgName(orgInfo.getOrgName());
//			//org.setCompanyId(orgInfo.getCompanyId());
//			departmentList.add(org);
//		}
//		uProfile.setDepartmentList(departmentList);
//		uProfile.setLdapFlag(account.getLdapFlag());
        uProfile.setCompanyId(account.getCompanyId());

//		uProfile.setDepartmentList(departmentList);
        uProfile.setPositionId(account.getPositionId());

        uProfile.setCompanyIdList(companyIdList);
        uProfile.setCompanyIdDataList(companyIdDataList);
        uProfile.setCompanyIdEmailList(companyIdEmailList);
        uProfile.setCompanyName(company.getName());
        uProfile.setSystemCode(company.getSystemCode());
        uProfile.setSystemName(company.getSystemName());
        uProfile.setPackageShortcutIcon(company.getPackageShortcutIcon());
        uProfile.setPackageLogoMini(company.getPackageLogoMini());
        uProfile.setPackageLogoLarge(company.getPackageLogoLarge());
        uProfile.setShortcutIcon(company.getShortcutIcon());
        uProfile.setShortcutIconRepoId(company.getShortcutIconRepoId());
        uProfile.setLogoLarge(company.getLogoLarge());
        uProfile.setLogoLargeRepoId(company.getLogoLargeRepoId());
        uProfile.setLogoMini(company.getLogoMini());
        uProfile.setLogoMiniRepoId(company.getLogoMiniRepoId());
        String lang = company.getLanguage();
        if (StringUtils.isBlank(lang)) {
            lang = systemConfig.getConfig(SystemConfig.LANGUAGE_DEFAULT);
        }
        String style = company.getStyle();
        if (StringUtils.isBlank(style)) {
            style = systemConfig.getConfig(SystemConfig.STYLE_DEFAULT);
        }
        uProfile.setDefaultLang(lang);
        uProfile.setStyle(style);
//        LanguageDto langDto = languageService.findByCode(lang);
//		uProfile.setLanguageDefault(langDto);
        // localAccount
//        if (account.getLdapFlag() == 1 || (account.getApiFlag() != null && account.getApiFlag().equals(1L))) {
//            uProfile.setLocalAccount(false);
//        }else {
//            uProfile.setLocalAccount(true);
//        }

        uProfile.setCode(account.getCode());

//        uProfile.setArchiveFlag(account.isArchiveFlag());
//        
//        uProfile.setCanSendHO(account.getCanSendHO());
//        uProfile.setIsHO(account.getIsHO());

        return uProfile;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.admin.service.AccountService#initScreenAccountList(org.
     * springframework.web.servlet.ModelAndView, java.util.Locale)
     */
    @Override
    public void initScreenAccountList(ModelAndView mav, Locale locale) {
        // Init account status
//		List<ConstantDisplay> statusList = constDispService.findByType(ConstantDisplayType.M02);
//		mav.addObject("statusList", statusList);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.admin.service.AccountService#initScreenAccountAdd(org.
     * springframework.web.servlet.ModelAndView,
     * vn.com.unit.mbal.admin.dto.JcaAccountAddDto, java.util.Locale)
     */
    @Override
    public void initScreenAccountAdd(ModelAndView mav, JcaAccountAddDto accountAddDto, Locale locale) {
        /*
         * // Init list position List<PositionDto> positionDtoList =
         * positionService.getPositionDtoList();
         * 
         * // Init list branch Long companyId = UserProfileUtils.getCompanyId();
         * List<OrgNode> branchList =
         * orgService.getNodeByOrgTypeCompanyId(OrgType.BRANCH, companyId); String
         * branchTreeJson = Util.convertObjectToJsonString(branchList);
         * 
         * List<OrgNode> deptList = orgService.getNodeByParentIdOrgTypeCompanyId(null,
         * OrgType.SECTION, companyId); String depTreeJson =
         * Util.convertObjectToJsonString(deptList);
         * 
         * //gender List<ConstantDisplayDto> genders =
         * constDispService.findConstantDisplayByTypeAndLang(ConstantDisplayType.
         * ACC_GENDER.toString(), locale); mav.addObject("genders", genders);
         * 
         * mav.addObject("positionDtoList", positionDtoList);
         * mav.addObject("branchList", branchTreeJson); mav.addObject("departmentList",
         * depTreeJson);
         */

        Long companyId = accountAddDto.getCompanyId();

        // gender
        // List<ConstantDisplayDto> genders =
        // constDispService.findConstantDisplayByTypeAndLang(ConstantDisplayType.ACC_GENDER.toString(),
        // locale);
        List<JcaConstantDto> genders = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(
                ConstantDisplayType.JCA_ADMIN_GENDER.toString(), locale.getLanguage());
        mav.addObject("genders", genders);

        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
                UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        
        // Add channel list
        List<JcaConstantDto> channels = jcaConstantService.getListJcaConstantDisplayByType(ConstantDisplayType.CHANNEL.toString());
        mav.addObject("channels", channels);
        
        if (accountAddDto.getChannel() != null) {
            String[] lstChannel = accountAddDto.getChannel().split(",");
            List<String> channelsList = Arrays.asList(lstChannel);
            accountAddDto.setChannelList(channelsList);
        }
                      
        //Add partner list
        List<PartnerDto> partners = db2ApiService.getListPartnerByChannel("AD");
        mav.addObject("partners", partners);

        if (accountAddDto.getPartner() != null) {
        	String[] lstPartner = accountAddDto.getPartner().split(",");
        	List<String> partnerList = Arrays.asList(lstPartner);
        	accountAddDto.setPartnerList(partnerList);
        }
        // position, group
        /*
         * List<PositionDto> positionDtoList =
         * positionService.findPositionDtoByCompany(companyId);
         */
        List<JcaTeamDto> groupDtoList = teamService.findTeamByCompanyIdForUser(companyId);
        List<Long> listGroupId = accountAddDto.getGroupId();
        List<Long> accountTeamIds = new ArrayList<Long>();
        if (listGroupId != null && !listGroupId.isEmpty()) {
            accountTeamIds = listGroupId;
        }
        List<JcaTeamDto> groupDtoListDisable = new ArrayList<JcaTeamDto>();
        if (!UserProfileUtils.isCompanyAdmin()) {
            for (JcaTeamDto teamDto : groupDtoList) {
                if (null == teamDto.getCompanyId())
                    groupDtoListDisable.add(teamDto);
            }
        }

        mav.addObject("groupDtoListDisable", groupDtoListDisable);
        mav.addObject("accountTeamIds", accountTeamIds);
        /* mav.addObject("positionDtoList", positionDtoList); */
        mav.addObject("groupDtoList", groupDtoList);

    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.admin.service.AccountService#initScreenAccountEdit(org.
     * springframework.web.servlet.ModelAndView, org.springframework.ui.Model,
     * vn.com.unit.mbal.admin.account.dto.JcaAccountEditDto, java.util.Locale)
     */
    @Override
    public void initScreenAccountEdit(ModelAndView mav, Model model, JcaAccountEditDto jcaAccountEditDto,
            Locale locale) {
        /*
         * // Init list position List<PositionDto> positionDtoList =
         * positionService.getPositionDtoList();
         * 
         * // Init list branch Long companyId = accountEditDto.getCompanyId();
         * List<OrgNode> branchList =
         * orgService.getNodeByOrgTypeCompanyId(OrgType.BRANCH, companyId); String
         * branchTreeJson = Util.convertObjectToJsonString(branchList);
         * 
         * Long branchId = accountEditDto.getBranchId(); List<OrgNode> deptList =
         * orgService.getNodeByParentIdOrgTypeCompanyId(branchId, OrgType.SECTION,
         * companyId); String depTreeJson = Util.convertObjectToJsonString(deptList);
         * 
         * //gender List<ConstantDisplayDto> genders =
         * constDispService.findConstantDisplayByTypeAndLang(ConstantDisplayType.
         * ACC_GENDER.toString(), locale); mav.addObject("genders", genders);
         * 
         * model.addAttribute("positionDtoList", positionDtoList);
         * model.addAttribute("branchList", branchTreeJson);
         * model.addAttribute("departmentList", depTreeJson);
         */

        Long companyId = jcaAccountEditDto.getCompanyId();

        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
                UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);

        // gender
        // List<ConstantDisplayDto> genders =
        // constDispService.findConstantDisplayByTypeAndLang(ConstantDisplayType.ACC_GENDER.toString(),
        // locale);
        List<JcaConstantDto> genders = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(
                ConstantDisplayType.JCA_ADMIN_GENDER.toString(), locale.getLanguage());
        mav.addObject("genders", genders);
        
        List<JcaConstantDto> channels = jcaConstantService.getListJcaConstantDisplayByType(ConstantDisplayType.CHANNEL.toString());
        mav.addObject("channels", channels);
        
        if (jcaAccountEditDto.getChannel() != null) {
            String[] lstChannel = jcaAccountEditDto.getChannel().split(",");
            List<String> channelsList = Arrays.asList(lstChannel);
            jcaAccountEditDto.setChannelList(channelsList);
        }
        
        List<PartnerDto> partners = db2ApiService.getListPartnerByChannel("AD");
        mav.addObject("partners", partners);
        
        if (jcaAccountEditDto.getPartner() != null) {
        	String[] lstPartner = jcaAccountEditDto.getPartner().split(",");
        	List<String> partnerList = Arrays.asList(lstPartner);
        	jcaAccountEditDto.setPartnerList(partnerList);
        }
       
        // position, group
        /*
         * List<PositionDto> positionDtoList =
         * positionService.findPositionDtoByCompany(companyId);
         */
//        List<TeamDto> groupDtoList = teamService.findTeamByCompanyIdForUser(companyId);
        
        
        List<JcaTeamDto> groupDtoList = teamService.findTeamByCompanyIdForUser(companyId);
        List<Long> listGroupId = jcaAccountEditDto.getGroupId();
        List<Long> accountTeamIds = new ArrayList<Long>();
        if (listGroupId != null && !listGroupId.isEmpty()) {
            accountTeamIds = listGroupId;
        }
        List<JcaTeamDto> groupDtoListDisable = new ArrayList<JcaTeamDto>();
//        if(!UserProfileUtils.isCompanyAdmin()) {
//            for (TeamDto teamDto : groupDtoList) {
//                if(null == teamDto.getCompanyId())
//                    groupDtoListDisable.add(teamDto);
//            }
//        }
        mav.addObject("groupDtoListDisable", groupDtoListDisable);
        mav.addObject("accountTeamIds", accountTeamIds);
        /* mav.addObject("positionDtoList", positionDtoList); */
        mav.addObject("groupDtoList", groupDtoList);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#findAccountEditDtoById(java.
     * lang.Long)
     */
    @Override
    public JcaAccountEditDto findAccountEditDtoById(Long accountId) {
        JcaAccountEditDto jcaAccountEditDto = new JcaAccountEditDto();

        if (accountId != null) {
            JcaAccount account = accRepository.findOne(accountId);

            if (account != null) {
                jcaAccountEditDto = this.accountToAccountEditDto(account);
            }
        }

        return jcaAccountEditDto;
    }

    /**
     * Find all role for account.
     *
     * @author trieunh <trieunh@unit.com.vn>
     * @param accountId type {@link long}
     * @return List<JcaRoleForAccountDto>
     */
    public List<JcaRoleForAccountDto> findRoleForAccount(long accountId) {
        return roleForAccountRepository.findByAccountId(accountId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#findRoleForAccountDetail(long)
     */
    public List<JcaRoleForAccountDto> findRoleForAccountDetail(long accountId) {
        return roleForAccountRepository.findViewDetailByAccountId(accountId);
    }

    /**
     * Find all role.
     *
     * @author trieunh <trieunh@unit.com.vn>
     * @param companyId type {@link Long}
     * @return List<RoleDto> list
     */
    public List<RoleEditDto> findAllRole(Long companyId) {
        return roleService.findRoleListByRoleType(null, companyId, false);
    }

    /**
     * Delete role for account.
     *
     * @author trieunh <trieunh@unit.com.vn>
     * @param roleId    type {@link Long}
     * @param accountId type {@link Long}
     * @throws AppException the app exception
     */
    @Transactional
    public void deleteRoleForAccount(Long roleId, Long accountId) throws AppException {
        roleForAccountRepository.deleteRoleIdAndAccountId(roleId, accountId);
        /*
         * JcaRoleForAccount roleForAccount =
         * roleForAccountRepository.findOne(roleForAccountId); if (roleForAccount !=
         * null) { roleForAccount.setDelFlg(true); roleForAccount.setDeletedBy("admin");
         * roleForAccount.setDeletedDate(new Date());
         * roleForAccountRepository.save(roleForAccount); }
         */

//        JcaRoleForAccount roleForAccount = roleForAccountRepository.findOne(roleForAccountId);
        // roleForAccountRepository.delete(roleForAccountId);
        /**
         * UPDATE ACT MEMBERSHIP REPLACE BY CALL STORE PROCEDURE
         **/
//        Long accountId = roleForAccount.getAccountId();
//		Long roleId = roleForAccount.getRoleId();
//		Account account = accRepository.findOne(accountId);
//		String username = account.getUsername();

//		Long companyId = account.getCompanyId();
//		List<AuthorityDetailDto> authorityDetailDtoList = authoDetailService
//				.getAuthorityDetailDtoListByUserNameAndRoleIdAndFunctionType(username, roleId, ConstantCore.PROCESS_FUNCTION_TYPE,
//						companyId);
//
//		List<String> functionCodeList = new ArrayList<>();
//		for (AuthorityDetailDto authorityDetailDto : authorityDetailDtoList) {
//			String functionCode = authorityDetailDto.getFunctionCode();
//			functionCodeList.add(functionCode);
//		}
//		
//		//String userId = Utils.buildActUserIdByCompanyIdAndUserName(companyId, username);
//		String userId = account.getId().toString();
//		if(!functionCodeList.isEmpty()) {
//		    jpmIdentityService.deleteMembershipByUserIdAndGroupIdList(userId, functionCodeList);
//
//		}
//        try {
//            UpdateRoleAccountParamDto updateRoleAccountParamDto = new UpdateRoleAccountParamDto();
//            updateRoleAccountParamDto.accountId = accountId;
//            sqlManager.call("SP_UPDATE_ROLE_FOR_ACCOUNT", updateRoleAccountParamDto);
//            String mes = updateRoleAccountParamDto.mes;
//            if(ConstantCore.SP_MSG_FAIL.equals(mes)){
//                throw new AppException("A001","A001", null);
//            }
//        } catch (Exception e) {
//            throw new AppException("A001","A001", null);
//        }

    }

    /**
     * Update list role for account.
     *
     * @author trieunh <trieunh@unit.com.vn>
     * @param accountDto type {@link JcaAccountEditDto}
     * @throws AppException the app exception
     */
    @Transactional
    public void updateRoleForAccount(JcaAccountEditDto accountDto) throws AppException {
        if (accountDto != null) {
            List<JcaRoleForAccountDto> roleForAccDtoList = accountDto.getListRoleForAccount();
            if (roleForAccDtoList != null) {
                Long accountId = accountDto.getId();
                roleForAccountRepository.deleteAccountId(accountId);
                for (JcaRoleForAccountDto rfad : accountDto.getListRoleForAccount()) {
                    JcaRoleForAccount roleForAccountNew = new JcaRoleForAccount();
                    roleForAccountNew.setAccountId(accountDto.getId());
                    roleForAccountNew.setRoleId(rfad.getRoleId());
                    roleForAccountNew.setCreatedId(UserProfileUtils.getAccountId());
                    roleForAccountNew.setCreatedDate(comService.getSystemDateTime());
                    roleForAccountRepository.create(roleForAccountNew);
                    rfad.setUserId(roleForAccountNew.getAccountId());
                }
            }
        }
    }

    /**
     * UpdateRoleAccountParamDto use for call procedure.
     *
     * @author KhuongTH
     * @version 01-00
     * @since 01-00
     */
    @SuppressWarnings("unused")
	private class UpdateRoleAccountParamDto {

        /** The account id. */
        @In
        public Long accountId;

        /** The mes. */
        @Out
        public String mes;
    }

    /**
     * Check duplicate role. True is duplicated.
     *
     * @author trieunh <trieunh@unit.com.vn>
     * @param listRoleForAccountDto type {@link List<JcaRoleForAccountDto>}
     * @return boolean
     */
    public boolean checkDuplicateRole(List<JcaRoleForAccountDto> listRoleForAccountDto) {
        boolean duplicate = false;
        for (int i = 0; i < listRoleForAccountDto.size(); i++) {
            for (int j = i + 1; j < listRoleForAccountDto.size(); j++) {
                if (listRoleForAccountDto.get(i).getRoleId() == listRoleForAccountDto.get(j).getRoleId()) {
//					if (listRoleForAccountDto.get(i).getStartDate()
//							.compareTo(listRoleForAccountDto.get(j).getEndDate()) == 0
//							|| listRoleForAccountDto.get(i).getStartDate()
//									.compareTo(listRoleForAccountDto.get(j).getStartDate()) == 0
//							|| (listRoleForAccountDto.get(i).getStartDate()
//									.after(listRoleForAccountDto.get(j).getStartDate())
//									&& listRoleForAccountDto.get(i).getStartDate()
//											.before(listRoleForAccountDto.get(j).getEndDate()))
//							|| (listRoleForAccountDto.get(j).getStartDate()
//									.after(listRoleForAccountDto.get(i).getStartDate())
//									&& listRoleForAccountDto.get(j).getStartDate()
//											.before(listRoleForAccountDto.get(i).getEndDate()))) {
//						duplicate = true;
//						break;
//					}
                    duplicate = true;
                    break;

//					if (listRoleForAccountDto.get(i).getEndDate()
//							.compareTo(listRoleForAccountDto.get(j).getStartDate()) == 0
//							|| listRoleForAccountDto.get(i).getEndDate()
//									.compareTo(listRoleForAccountDto.get(j).getEndDate()) == 0
//							|| (listRoleForAccountDto.get(i).getEndDate()
//									.after(listRoleForAccountDto.get(j).getStartDate())
//									&& listRoleForAccountDto.get(i).getEndDate()
//											.before(listRoleForAccountDto.get(j).getEndDate()))
//							|| (listRoleForAccountDto.get(j).getEndDate()
//									.after(listRoleForAccountDto.get(i).getStartDate())
//									&& listRoleForAccountDto.get(j).getEndDate()
//											.before(listRoleForAccountDto.get(i).getEndDate()))) {
//						duplicate = true;
//						break;
//					}
                }
            }
        }
        return duplicate;
    }

    /**
     * Check start date and end date.
     *
     * @author trieunh <trieunh@unit.com.vn>
     * @param listRoleForAccountDto type {@link List<JcaRoleForAccountDto>}
     * @return boolean
     */
    public boolean checkStartEndDate(List<JcaRoleForAccountDto> listRoleForAccountDto) {
        boolean error = false;
        for (JcaRoleForAccountDto rfad : listRoleForAccountDto) {
            if (rfad.getStartDate() != null && rfad.getEndDate() != null) {
                if (rfad.getStartDate().after(rfad.getEndDate())) {
                    error = true;
                    break;
                }
            } else {
                error = true;
                break;
            }

        }
        return error;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#accountToAccountEditDto(vn.com.
     * unit.core.entity.JcaAccount)
     */
    @Override
    public JcaAccountEditDto accountToAccountEditDto(JcaAccount account) {
        JcaAccountEditDto accEditDto = new JcaAccountEditDto();

        String avatar = account.getAvatar();
        accEditDto.setAvatar(avatar);

        Date birthday = account.getBirthday();
        accEditDto.setBirthday(birthday);

//		Long branchId = account.getBranchId();
//		accEditDto.setBranchId(branchId);
//
//		Long departmentId = account.getDepartmentId();
//		accEditDto.setDepartmentId(departmentId);

        String email = account.getEmail();
        accEditDto.setEmail(email);

        Boolean enabled = account.isEnabled();
        accEditDto.setEnabled(enabled);

        String fullname = account.getFullname();
        accEditDto.setFullname(fullname);

        Long id = account.getId();
        accEditDto.setId(id);

        String phone = account.getPhone();
        accEditDto.setPhone(phone);
        
        String channel = account.getChannel();
        accEditDto.setChannel(channel);
        
        String partner = account.getPartner();
        accEditDto.setPartner(partner);
//        String cmnd = account.getCmnd();
//        accEditDto.setCmnd(cmnd);

        Long positionId = account.getPositionId();
        accEditDto.setPositionId(positionId);

        JcaPositionDto positionDto = positionService.getPositionDtoById(positionId);
        String positionName = null == positionDto ? StringUtils.EMPTY : positionDto.getName();
        accEditDto.setPositionName(positionName);

        String username = account.getUsername();
        accEditDto.setUsername(username);

//		String description = account.getDescription();
//		accEditDto.setDescription(description);

        // Add company_id
        accEditDto.setCompanyId(account.getCompanyId());
//		accEditDto.setActived(account.getActived());
        accEditDto.setLocked(!account.isActived());
        accEditDto.setRepositoryId(account.getAvatarRepoId());

        accEditDto.setCode(account.getCode());
        accEditDto.setGender(account.getGender());

        accEditDto.setPushNotification(account.isReceivedNotification());
        accEditDto.setPushEmail(account.isReceivedEmail());

        // localAccount
//        if (account.getLdapFlag() == 1 || (account.getApiFlag() != null && account.getApiFlag().equals(1L))) {
//            accEditDto.setLocalAccount(false);
//        }else {
//            accEditDto.setLocalAccount(true);
//        }

        accEditDto.setPassword(CommonConstant.PASSWORD_ENCRYPT);
//		accEditDto.setArchiveFlag(account.isArchiveFlag());
//		accEditDto.setCanSendHO(account.getCanSendHO());
//		accEditDto.setIsHO(account.getIsHO());

        return accEditDto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#create(vn.com.unit.mbal.admin.
     * dto.JcaAccountAddDto, java.util.Locale)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public JcaAccount create(JcaAccountAddDto accountAddDto, Locale locale) throws AppException, ParseException {
        // Create account
        JcaAccount account = new JcaAccount();
        // Set username
        String username = accountAddDto.getUsername();
        account.setUsername(username);
        // Set fullname
        String fullname = accountAddDto.getFullname();
        account.setFullname(fullname);
        // Set email
        String email = accountAddDto.getEmail();
        account.setEmail(email);
        // Set phone
        String phone = accountAddDto.getPhone();
        account.setPhone(phone);
        // Set birthday
        Date birthday = accountAddDto.getBirthday();
        account.setBirthday(birthday);
        //Set channel
        if (accountAddDto.getChannelList() != null) {         
        	account.setChannel(String.join(",", accountAddDto.getChannelList())); 
        } else {
        	account.setChannel(null);
        }
        //Set partner
        if (accountAddDto.getPartnerList() != null) {
        	account.setPartner(String.join(",", accountAddDto.getPartnerList()));
        } else {
        	account.setPartner(null);
        }
        // Set departmentId
//		Long departmentId = accountAddDto.getDepartmentId();
//		account.setDepartmentId(departmentId);
//		// Set branchId
//		Long branchId = accountAddDto.getBranchId();
//		account.setBranchId(branchId);
        // Set positionId
        Long positionId = accountAddDto.getPositionId();
        account.setPositionId(positionId);
        // Set enabled
        boolean enabled = accountAddDto.getEnabled();
        account.setEnabled(enabled);
        //Set Channel
//        List<String> channel = accountAddDto.getChannel();
//        account.setChannel(channel);
        
        // Set cmnd
//		String cmnd = accountAddDto.getCmnd();
//		account.setCmnd(cmnd);

        // Set password
//		String password = accountAddDto.getPassword();
//		try {
//			password = Util.encryptMD5(password);
//		} catch (NoSuchAlgorithmException e) {
//			throw new SystemException("Error! Create account. EncryptMD5 password fail");
//		}
        String encryptedPassword = passwordEncoder.encode(accountAddDto.getPassword());
        account.setPassword(encryptedPassword);

        // Set created by
        account.setCreatedDate(comService.getSystemDateTime());
//		String usernameLogin = UserProfileUtils.getUserNameLogin();
//		account.setCreatedBy(usernameLogin);

//		if (accountAddDto.getDescription() != null && !accountAddDto.getDescription().equals("")) {
//			account.setDescription(accountAddDto.getDescription());
//		}

        // thaonv: update
        account.setActived(!accountAddDto.isLocked());

        // Ad company_id
        Long companyId = accountAddDto.getCompanyId();
        account.setCompanyId(companyId);

        // set code
        account.setCode(accountAddDto.getCode());
        // set gender
        account.setGender(accountAddDto.getGender());

//        if(accountAddDto.getApiFlag()!= null) {
//            account.setApiFlag(accountAddDto.getApiFlag());
//            account.setCreatedBy(accountAddDto.getCreateBy());
//        }

        account.setReceivedNotification(accountAddDto.isPushNotification());
        account.setReceivedEmail(accountAddDto.isPushEmail());
//        
//        account.setArchiveFlag(accountAddDto.isArchiveFlag());
        account.setActived(!accountAddDto.isLocked());
        if(StringUtils.equalsIgnoreCase(accountAddDto.getAccountType(), "LDAP")) {
        	account.setAccountType(AccountTypeEnum.TYPE_STAFF.toString());
        } else {
        	account.setAccountType(AccountTypeEnum.TYPE_GUEST.toString());
        }
        	
        accRepository.create(account);

        if (null != accountAddDto.getAvatarFile()) {
            try {
                String systemCode = companyService.getSystemCodeByCompanyId(companyId);
                String subFilePath = DirectoryConstant.AVATAR_FOLDER.concat(systemCode);
                JcaRepository repo = systemConfig.getRepoByKey(SystemConfig.REPO_UPLOADED_MAIN, companyId, null);
                FileUploadParamDto param = new FileUploadParamDto();

                String extension = FilenameUtils.getExtension(accountAddDto.getAvatarFile().getOriginalFilename());
                String nameFile = null;
                // Check rename file when upload

                nameFile = String.valueOf(UUID.randomUUID());
                if (StringUtils.isNotBlank(accountAddDto.getUsername())) {
                    nameFile = accountAddDto.getUsername().concat("_").concat(nameFile);
                } else {
                    nameFile = FilenameUtils.getBaseName(accountAddDto.getAvatarFile().getOriginalFilename())
                            .concat("_").concat(nameFile);
                }
                String fullFileName = nameFile.concat(".").concat(extension);

                param.setFileByteArray(accountAddDto.getAvatarFile().getBytes());
                param.setFileName(fullFileName);
                param.setTypeRule(1);
                param.setSubFilePath(subFilePath);
                param.setCompanyId(accountAddDto.getCompanyId());
                param.setRename(nameFile);
                param.setExtension(extension);

                param.setRepositoryId(repo.getId());
                FileUploadResultDto uploadResultDto = fileStorageService.upload(param);
                String filePath = uploadResultDto.getFilePath();
                account.setAvatarRepoId(repo.getId());
                account.setAvatar(filePath);
            } catch (Exception e) {
                logger.error("AccountServiceImpl.java => create() => save ", e);
                // TODO: handle exception
            }

            // Save file
            if (account.getId() != null) {
                accRepository.update(account);
            } else {
                accRepository.create(account);
            }

        }
        // set group for account
        setGroupForAccount(account.getId(), accountAddDto.getGroupId());

        // add user to activiti
        // String userIdStr = Utils.buildActUserIdByCompanyIdAndUserName(companyId,
        // username);
        String userIdStr = account.getId().toString();
        jpmIdentityService.createUser(userIdStr, fullname, fullname, userIdStr, email, null, null, null);

        // update membership by userId
        /** REPLACE BY STORE PROCEDURE */
//		jpmIdentityService.deleteMembershipByUserId(userIdStr);
//		List<AuthorityDetailDto> authorityDetailDtoList = authoDetailService
//				.getAuthorityDetailDtoListByUserNameAndRoleIdAndFunctionType(username, null, ConstantCore.PROCESS_FUNCTION_TYPE,
//						companyId);
//		for (AuthorityDetailDto authorityDetailDto : authorityDetailDtoList) {
//			String groupId = authorityDetailDto.getFunctionCode();
//			jpmIdentityService.createMembership(userIdStr, groupId);
//		}
//		try {
//		    Long accountId = account.getId();
//            UpdateRoleAccountParamDto updateRoleAccountParamDto = new UpdateRoleAccountParamDto();
//            updateRoleAccountParamDto.accountId = accountId;
//            sqlManager.call("SP_UPDATE_ROLE_FOR_ACCOUNT", updateRoleAccountParamDto);
//            String mes = updateRoleAccountParamDto.mes;
//            if(ConstantCore.SP_MSG_FAIL.equals(mes)){
//                throw new AppException("A001","A001", null);
//            }
//        } catch (Exception e) {
//            throw new AppException("A001","A001", null);
//        }
        return account;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#update(vn.com.unit.mbal.admin.
     * account.dto.JcaAccountEditDto, java.util.Locale)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(JcaAccountEditDto jcaAccountEditDto, Locale locale) throws AppException, ParseException {

        // Get data
        JcaAccount account = null;
        Long accountId = jcaAccountEditDto.getId();
        if (accountId != null) {
            account = accRepository.findOne(accountId);
        }

        if (account == null) {
            throw new BusinessException("Not found account by id=" + accountId);
        }

        // Set fullname
        String fullname = jcaAccountEditDto.getFullname();
        account.setFullname(fullname);
        // Set email
        String email = jcaAccountEditDto.getEmail();
        account.setEmail(email);
        // Set phone
        String phone = jcaAccountEditDto.getPhone();
        account.setPhone(phone);
        // Set channel
        String channel = jcaAccountEditDto.getChannel();
        account.setChannel(channel);
        // Set partner
        String partner = jcaAccountEditDto.getPartner();
        account.setPartner(partner);
        // Set birthday
        Date birthday = jcaAccountEditDto.getBirthday();
        account.setBirthday(birthday);
        // Set departmentId
//		Long departmentId = accountEditDto.getDepartmentId();
//		account.setDepartmentId(departmentId);
//
//		// Set branchId
//		Long branchId = accountEditDto.getBranchId();
//		account.setBranchId(branchId);
        // Set positionId
        Long positionId = jcaAccountEditDto.getPositionId();
        account.setPositionId(positionId);
        // Set enabled
        boolean enabled = jcaAccountEditDto.getEnabled();
        account.setEnabled(enabled);

        // Set updated by
        account.setUpdatedDate(comService.getSystemDateTime());
//		String usernameLogin = UserProfileUtils.getUserNameLogin();
//		account.setUpdatedBy(usernameLogin);

//		if (accountEditDto.getDescription() != null && !accountEditDto.getDescription().equals("")) {
//			account.setDescription(accountEditDto.getDescription());
//		}

        // Add locked
        account.setActived(!jcaAccountEditDto.isLocked());
        // Add company_id
        account.setCompanyId(jcaAccountEditDto.getCompanyId());
        // set code
        account.setCode(jcaAccountEditDto.getCode());
        // set gender
        account.setGender(jcaAccountEditDto.getGender());
        // Set cmnd
//        account.setCmnd(accountEditDto.getCmnd());

//        if (accountEditDto.getApiFlag() != null) {
//            account.setApiFlag(accountEditDto.getApiFlag());
//            account.setUsername(accountEditDto.getUsername());
//            account.setUpdatedBy(accountEditDto.getUpdatedBy());
//        } 

//        account.setPushNotification(accountEditDto.isPushNotification());
        account.setReceivedNotification(jcaAccountEditDto.isPushNotification());
//        account.setPushEmail(accountEditDto.isPushEmail());
        account.setReceivedEmail(jcaAccountEditDto.isPushEmail());
//        account.setArchiveFlag(accountEditDto.isArchiveFlag());
//        account.setIsHO(accountEditDto.getIsHO());
//        account.setCanSendHO(accountEditDto.getCanSendHO());
        // Set password
        String password = jcaAccountEditDto.getPassword();
        if (StringUtils.isNotBlank(password) && !CommonConstant.PASSWORD_ENCRYPT.equals(password)
                && jcaAccountEditDto.isLocalAccount()) {
            password = passwordEncoder.encode(password);
            account.setPassword(password);
            accountPasswordService.updateExpiredDateAccountPass(accountId);
        }

        accRepository.update(account);

        if (null != jcaAccountEditDto.getAvatarFile()) {
            try {
                String systemCode = companyService.getSystemCodeByCompanyId(jcaAccountEditDto.getCompanyId());
                String subFilePath = DirectoryConstant.AVATAR_FOLDER.concat(systemCode);
                JcaRepository repo = systemConfig.getRepoByKey(SystemConfig.REPO_UPLOADED_MAIN,
                        jcaAccountEditDto.getCompanyId(), null);
                FileUploadParamDto param = new FileUploadParamDto();

                String extension = FilenameUtils.getExtension(jcaAccountEditDto.getAvatarFile().getOriginalFilename());
                String nameFile = null;
                // Check rename file when upload

                nameFile = String.valueOf(UUID.randomUUID());
                if (StringUtils.isNotBlank(jcaAccountEditDto.getUsername())) {
                    nameFile = jcaAccountEditDto.getUsername().concat("_").concat(nameFile);
                } else {
                    nameFile = FilenameUtils.getBaseName(jcaAccountEditDto.getAvatarFile().getOriginalFilename())
                            .concat("_").concat(nameFile);
                }
                String fullFileName = nameFile.concat(".").concat(extension);

                param.setFileByteArray(jcaAccountEditDto.getAvatarFile().getBytes());
                param.setFileName(fullFileName);
                param.setTypeRule(1);
                param.setSubFilePath(subFilePath);
                param.setCompanyId(jcaAccountEditDto.getCompanyId());
                param.setRename(nameFile);
                param.setExtension(extension);

                param.setRepositoryId(repo.getId());
                FileUploadResultDto uploadResultDto = fileStorageService.upload(param);
                String filePath = uploadResultDto.getFilePath();
                account.setAvatarRepoId(repo.getId());
                account.setAvatar(filePath);
            } catch (Exception e) {
                // TODO: handle exception
            }

            // Save file
            accRepository.update(account);

        }

        // set group for account
        setGroupForAccount(account.getId(), jcaAccountEditDto.getGroupId());

        /** UPDATE ACT_MEMBERSHIP BY ACCOUNT_ID */
//		try {
//            UpdateRoleAccountParamDto updateRoleAccountParamDto = new UpdateRoleAccountParamDto();
//            updateRoleAccountParamDto.accountId = accountId;
//            sqlManager.call("SP_UPDATE_ROLE_FOR_ACCOUNT", updateRoleAccountParamDto);
//            String mes = updateRoleAccountParamDto.mes;
//            if(ConstantCore.SP_MSG_FAIL.equals(mes)){
//                throw new AppException("A001","A001", null);
//            }
//        } catch (Exception e) {
//            throw new AppException("A001","A001", null);
//        }

    }

    /**
     * move avatar image from temp upload repository to account avatar repository
     * directory.
     *
     * @author Tan Tai
     * @param id type {@link Long}
     */
    // private String updateAvatar(String oldAvatar, String tmpAvatar) throws
    // IOException {
    // String physicalImgTmpName = tmpAvatar;
    // String movedAvatar = null;
    // // Delete file previous
    // if (StringUtils.isNotEmpty(oldAvatar)) {
    // String path = jRepositoryService.getPathByRepository(oldAvatar,
    // SystemConfig.REPO_UPLOADED_TEMP);
    // FileUtil.deleteFile(path);
    // }
    // // upload images
    // if (StringUtils.isNotEmpty(physicalImgTmpName)) {
    // String newPhiscalName =
    // fileService.moveFileFromTempToFolderUploadMain(physicalImgTmpName,
    // DirectoryConstant.AVATAR_FOLDER);
    // movedAvatar = newPhiscalName;
    // }
    // return movedAvatar;
    // }

    // /**
    // * Update avatar return newFileName
    // *
    // * @param fileNamePrevious
    // * type String
    // * @param fileNameCurrent
    // * type String
    // * @return String
    // * @author KhoaNA
    // */
    // private String updateAvatar(String fileNamePrevious,
    // String fileNameCurrent) {
    // String fileName = fileNameCurrent;
    //
    // String rootFolder =
    // systemConfig.getConfig(SystemConfig.REPOSITORY_ROOT_FOLDER);
    //
    // // Delete file previous
    // if( StringUtils.isNotEmpty(fileNamePrevious) ) {
    // File avatarPrevious = new File(rootFolder + fileNamePrevious);
    // avatarPrevious.delete();
    // }
    //
    // if (fileNameCurrent.startsWith("@@@") == true) {
    // int lastIndex = fileNameCurrent.lastIndexOf("@");
    // fileName = fileNameCurrent.substring(lastIndex + 1);
    //
    // String tempFolder = systemConfig.getConfig(SystemConfig.TEMP_FOLDER);
    //
    // FileUtil.moveFile(fileNameCurrent, tempFolder, fileName, rootFolder);
    // }
    //
    // return fileName;
    // }

    @Override
    public void delete(Long id) {
        // JcaAccount account = accRepository.findOne(id);
        //
        // UserProfileUtils userProfile = UserProfileUtils.getUserProfile();
        // String userName = userProfile.getUsername();
        // accRepository.updateDeleteFields(id, userName,
        // comService.getSystemDateTime());
        this.deleteJcaAccountById(id);
        String userId = id.toString();// account.getId().toString();
        jpmIdentityService.deleteUser(userId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#findAccountDetailDtoById(java.
     * lang.Long)
     */
    @Override
    public JcaAccountDetailDto findAccountDetailDtoById(Long accountId) {
        JcaAccountDetailDto jcaAccountDetailDto = new JcaAccountDetailDto();

        if (accountId != null && accountId != 0L) {
            jcaAccountDetailDto = accRepository.findAccountDetailDtoById(accountId);
            jcaAccountDetailDto.setLocked(!jcaAccountDetailDto.isActived());
        }

        return jcaAccountDetailDto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.jcanary.service.AccountService#updateFailedLoginCount(java.
     * lang.Long, int, java.util.Date)
     */
    @Override
    public void updateFailedLoginCount(Long accountId, int failedLoginCount, Date loginDate) {
        accRepository.updateFailedLoginCount(accountId, failedLoginCount, loginDate);

    }

    /**
     * disabledAccount.
     *
     * @author HUNGHT
     * @param accountId   type {@link Long}
     * @param updatedBy   type {@link String}
     * @param updatedDate type {@link Date}
     */
    @Override
    public void disabledAccount(Long accountId, String updatedBy, Date updatedDate) {
        accRepository.disabledAccount(accountId, updatedBy, updatedDate);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#changePassword(java.lang.Long,
     * java.lang.String)
     */
    @Override
    public void changePassword(Long accountId, String password) {
        try {
            String enPassword = passwordEncoder.encode(password);
            accRepository.updatePassword(accountId, enPassword);
            accountPasswordService.updateAccountPass(accountId, enPassword);
        } catch (Exception e) {
            logger.error("Exception ", e);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#findByInputQuery(java.lang.
     * String)
     */
    @Override
    public List<JcaAccountDto> findByInputQuery(String inputQuery) {

        return accRepository.findByInputQuery(inputQuery);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.jcanary.service.AccountService#findListAccountHasRoleByItem(
     * java. lang.String)
     */
    @Override
    public List<JcaAccount> findListAccountHasRoleByItem(String itemCode) {
        return accRepository.findListAccountHasRoleByItem(itemCode);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.jcanary.service.AccountService#findListAccountItem(java.lang.
     * String)
     */
    @Override
    public List<JcaAccountItem> findListAccountItem(String itemCode, Long companyId) {
        return accRepository.findListAccountItem(itemCode, companyId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.jcanary.service.AccountService#findLstEmailByLstUsername(
     * java. util.List)
     */
    @Override
    public JcaAccountDto findLstEmailByLstUsername(String lstUsername) {
        return accRepository.findLstEmailByLstUsername(lstUsername);
    }

    /**
     * countNumberAccountByCompanyId.
     *
     * @author HungHT
     * @param companyId type {@link Long}
     * @return {@link long}
     */
    public long countNumberAccountByCompanyId(Long companyId) {
        long numberAccount = 0;
        Long number = accRepository.countNumberAccountByCompanyId(companyId);
        if (null != number) {
            numberAccount = number.longValue();
        }
        return numberAccount;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#findByUserNameAndCompanyId(java
     * .lang.String, java.lang.Long)
     */
    @Override
    public JcaAccount findByUserNameAndCompanyId(String username, Long companyId) {
        return accRepository.findByUserNameAndCompanyId(username, companyId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#getListByUserName(java.lang.
     * String)
     */
    @Override
    public List<JcaAccount> getListByUserName(String username) {
        return accRepository.getListByUserName(username);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#findListAccountItemByItemList(
     * java.util.List, java.lang.Long)
     */
    @Override
    public List<JcaAccountItem> findListAccountItemByItemList(List<String> lstItemCode, Long companyId) {
        return accRepository.findListAccountItemByItemList(lstItemCode, companyId);
    }

    /**
     * <p>
     * Sets the group for account.
     * </p>
     *
     * @author Tan Tai
     * @param accountId type {@link Long}
     * @param teamIds   type {@link List<Long>}
     * @throws ParseException the parse exception
     * @throws AppException   the app exception
     */
    public void setGroupForAccount(Long accountId, List<Long> teamIds) throws ParseException, AppException {
        Date date = comService.getSystemDateTime();
        try {
            accountTeamRepository.deleteByAccountId(accountId);
            // set group
            if (CollectionUtils.isEmpty(teamIds)) {
                return;
            }
            for (Long teamId : teamIds) {
                JcaAccountTeam accTeam = new JcaAccountTeam();
                accTeam.setAccountId(accountId);
                accTeam.setTeamId(teamId);
                accTeam.setCreatedId(UserProfileUtils.getAccountId());
                accTeam.setCreatedDate(date);
                accountTeamRepository.create(accTeam);
            }
        } catch (Exception e) {
            throw new AppException(e.getMessage(), null, null);
            // logger.error("Exception ", e);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#findAccountTeamByAccountId(java
     * .lang.Long)
     */
    @Override
    public List<JcaAccountTeamDto> findAccountTeamByAccountId(Long accountId) {
        List<JcaAccountTeamDto> accountTeams = new ArrayList<JcaAccountTeamDto>();
        accountTeams = accountTeamRepository.findByAccountId(accountId);
        return accountTeams;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#updateUserProfile(java.lang.
     * Long, boolean, java.lang.String, boolean, boolean, java.lang.String)
     */
    @Override
    public void updateUserProfile(Long userId, boolean pushNotification, String phoneNumber, boolean pushEmail,
            boolean archiveFlag, String lang) throws AppException {

        JcaAccount acc = accRepository.findOne(userId);
        if (null != phoneNumber) {
            acc.setPhone(phoneNumber);
        }
//		acc.setPushNotification(pushNotification);
//		acc.setPushEmail(pushEmail);
//		acc.setArchiveFlag(archiveFlag);
        try {
            accRepository.save(acc);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), null, null);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#getListAccountByCompanyId(java.
     * lang.Long)
     */
    @Override
    public List<JcaAccountDto> getListAccountByCompanyId(Long companyId) throws Exception {
        return accRepository.findListAccountByCompanyId(companyId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#updateAvatar(java.lang.Long,
     * org.springframework.web.multipart.MultipartFile, java.lang.String,
     * java.util.Locale)
     */
    @Override
    public void updateAvatar(Long userId, MultipartFile avatarFile, String lang, Locale locale) throws AppException {
        JcaAccount acc = accRepository.findOne(userId);

        if (null != avatarFile) {
            String systemCode = companyService.getSystemCodeByCompanyId(acc.getCompanyId());
            String subFilePath = DirectoryConstant.AVATAR_FOLDER.concat(systemCode);
            FileResultDto repoResultDto = jRepositoryService.uploadFileBySettingKey(avatarFile, acc.getUsername(),
                    SystemConfig.REPO_UPLOADED_MAIN, 2, null, subFilePath, acc.getCompanyId(), locale);
            if (repoResultDto.isStatus()) {
                acc.setAvatar(repoResultDto.getFilePath());
//				acc.setRepositoryId(repoResultDto.getRepositoryId());
            } else {
                throw new AppException(repoResultDto.getMessage(), null, null);
            }

            try {
                accRepository.update(acc);
            } catch (Exception e) {
                throw new AppException("B500", "B500", null);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#findByEmail(java.lang.String)
     */
    @Override
    public JcaAccount findByEmail(String email) {
        return accRepository.findByEmail(email);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#findByCmnd(java.lang.String)
     */
    @Override
    public JcaAccount findByCmnd(String cmnd) {
        return accRepository.findByCmnd(cmnd);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#getAllAcountActive(java.lang.
     * Boolean)
     */
    @Override
    public List<JcaAccountDto> getAllAcountActive(Boolean isPaging) throws SQLException {
        try {
            return accRepository.findAllActiveAccount(isPaging);
        } catch (Exception e) {
        }

        return null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.admin.service.AccountService#
     * getSelect2DtoListByKeyAndListCompanyId(java.lang.String, java.lang.Long,
     * java.lang.Long, java.util.List, boolean)
     */
    @Override
    public List<Select2Dto> getSelect2DtoListByKeyAndListCompanyId(String key, Long orgId, Long accountId,
            List<Long> listCompanyId, boolean isPaging) {
        return accRepository.findListAccountByKeyAndListCompanyId(key, orgId, accountId, listCompanyId, isPaging);
    }

    /**
     * <p>
     * Get select 2 dto list by list account id.
     * </p>
     *
     * @author KhuongTH
     * @param listAccountId type {@link List<Long>}
     * @return {@link List<Select2Dto>}
     */
    @Override
    public List<Select2Dto> getSelect2DtoListByListAccountId(List<Long> listAccountId) {

        List<JcaAccountDto> accountDtoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(listAccountId)) {
            List<Long> allIds = new ArrayList<>(listAccountId);
            while (CollectionUtils.isNotEmpty(allIds)) {
                List<Long> tempIds = null;
                if (CollectionUtils.size(allIds) > 999) {
                    tempIds = allIds.subList(0, 999);
                } else {
                    tempIds = allIds;
                }
                accountDtoList.addAll(accRepository.findListAccountByListAccountId(tempIds, false, false));
                allIds.removeAll(tempIds);
            }
        }

        List<Select2Dto> result = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(accountDtoList)) {
            for (JcaAccountDto accountDto : accountDtoList) {
                Long id = accountDto.getUserId();
                String email = accountDto.getEmail();
                String fullName = accountDto.getFullname();
                String text = fullName.concat(" (").concat(email).concat(")");

                Select2Dto item = new Select2Dto(id.toString(), text, email);
                result.add(item);
            }
        }
        return result;
    }

//    @Override
//    public Account findByUserName(String code, Long companyId) {
//        return accRepository.getByCode(code, companyId);
//    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#updatePositionIdByPositionCode(
     * java.lang.Long, java.lang.String, java.lang.Long)
     */
    @Override
    public void updatePositionIdByPositionCode(Long positionId, String positionCode, Long companyId) {
        if (positionId != null && positionCode != null) {
            accRepository.updatePositionIdByPositionCode(positionId, positionCode, companyId);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.admin.service.AccountService#
     * getSelect2DtoListByKeyAndListCompanyIdByPaging(java.lang.String,
     * java.util.List, int, int, int)
     */
    @Override
    public List<Select2Dto> getSelect2DtoListByKeyAndListCompanyIdByPaging(String key, List<Long> listCompanyId,
            int pageSize, int pageIndex, int isPaging) {

        List<JcaAccountDto> accountDtoList = accRepository.findListAccountByKeyAndListCompanyIdByPaging(key,
                listCompanyId, pageIndex, pageSize, isPaging);

        List<Select2Dto> result = new ArrayList<>();
        if (accountDtoList != null && !accountDtoList.isEmpty()) {
            for (JcaAccountDto accountDto : accountDtoList) {
                Long id = accountDto.getUserId();
                String name = accountDto.getEmail();
                String code = accountDto.getUsername();

                Select2Dto item = Utils.createSelect2Dto(id.toString(), name, new String[] { code, name });
                result.add(item);
            }
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#findListSelect2DtoForCA(java.
     * lang.String, java.lang.Long, java.lang.Long, boolean)
     */
    @Override
    public List<Select2Dto> findListSelect2DtoForCA(String keySearch, Long companyId, Long caId, boolean isPaging) {
        Long accountId = null;
        if (null != caId) {
            JcaCaManagement caManagement = caManagementRepository.findOne(caId);
            if (null != caManagement) {
                accountId = caManagement.getAccountId();
            }
        }
        return accRepository.findListSelect2DtoForCA(keySearch, companyId, accountId, isPaging);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#getAccountListByIds(java.util.
     * List)
     */
    @Override
    public List<JcaAccountDto> getAccountListByIds(List<Long> ids) throws Exception {
        List<JcaAccountDto> accountDtos = new ArrayList<>();
        List<Long> allIds = new ArrayList<>(ids);
        while (CollectionUtils.isNotEmpty(allIds)) {
            List<Long> tempIds = null;
            if (CollectionUtils.size(allIds) > 999) {
                tempIds = allIds.subList(0, 999);
            } else {
                tempIds = allIds;
            }
            // accountDtos.addAll(null);
            accountDtos.addAll(accRepository.findListAccountByListAccountId(tempIds, false, false));
            allIds.removeAll(tempIds);
        }
        return accountDtos;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.admin.service.AccountService#findById(java.lang.Long)
     */
    @Override
    public JcaAccount findById(Long id) {
        JcaAccount resObj = new JcaAccount();
        if (null != id) {
            resObj = accRepository.findOne(id);
        }
        return resObj;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.admin.service.AccountService#
     * findAccountDtoForPushNotificationByListId(java.util.List)
     */
    @Override
    public List<JcaAccountDto> findAccountDtoForPushNotificationByListId(List<Long> ids) throws Exception {
        List<JcaAccountDto> accountDtos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ids)) {
            List<Long> allIds = new ArrayList<>(ids);
            while (CollectionUtils.isNotEmpty(allIds)) {
                List<Long> tempIds = null;
                if (CollectionUtils.size(allIds) > 999) {
                    tempIds = allIds.subList(0, 999);
                } else {
                    tempIds = allIds;
                }
                accountDtos.addAll(accRepository.findListAccountByListAccountId(tempIds, true, false));
                allIds.removeAll(tempIds);
            }
        }
        return accountDtos;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.admin.service.AccountService#
     * findAccountDtoForPushEmailByListId(java.util.List)
     */
    @Override
    public List<JcaAccountDto> findAccountDtoForPushEmailByListId(List<Long> ids) throws Exception {
        List<JcaAccountDto> accountDtos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ids)) {
            List<Long> allIds = new ArrayList<>(ids);
            while (CollectionUtils.isNotEmpty(allIds)) {
                List<Long> tempIds = null;
                if (CollectionUtils.size(allIds) > 999) {
                    tempIds = allIds.subList(0, 999);
                } else {
                    tempIds = allIds;
                }
                accountDtos.addAll(accRepository.findListAccountByListAccountId(tempIds, false, true));
                allIds.removeAll(tempIds);
            }
        }
        return accountDtos;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.admin.service.AccountService#
     * countAccountListByKeyAndListCompanyId(java.lang.String, java.lang.Long,
     * java.lang.Long, java.util.List)
     */
    @Override
    public Long countAccountListByKeyAndListCompanyId(String key, Long orgId, Long accountId, List<Long> companyIds) {
        return accRepository.countListAccountByKeyAndListCompanyId(key, orgId, accountId, companyIds);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#updateInfo(vn.com.unit.mbal.
     * admin.account.dto.JcaAccountEditDto, java.util.Locale)
     */
    @Override
    public void updateInfo(JcaAccountEditDto jcaAccountEditDto, Locale locale) throws AppException {
        // Get data
        JcaAccount account = null;
        Long accountId = jcaAccountEditDto.getId();
        if (accountId != null) {
            account = accRepository.findOne(accountId);
        }

        if (account == null) {
            throw new BusinessException("Not found account by id=" + accountId);
        }
        // Set phone
        // String phone = accountEditDto.getPhone();
        // account.setPhone(phone);
        // Set birthday
        // Date birthday = accountEditDto.getBirthday();
        // account.setBirthday(birthday);
        // Set cmnd
        // String cmnd = accountEditDto.getCmnd();
        // account.setCmnd(cmnd);
        // Set updated by
        account.setUpdatedDate(comService.getSystemDateTime());
//        String usernameLogin = UserProfileUtils.getUserNameLogin();
//        account.setUpdatedBy(usernameLogin);
        // set gender
        // account.setGender(accountEditDto.getGender());
//        account.setPushNotification(accountEditDto.isPushNotification());
//        account.setPushEmail(accountEditDto.isPushEmail());
//        account.setArchiveFlag(accountEditDto.isArchiveFlag());
        accRepository.update(account);

        if (null != jcaAccountEditDto.getAvatarFile()) {

            try {
                String systemCode = companyService.getSystemCodeByCompanyId(jcaAccountEditDto.getCompanyId());
                String subFilePath = DirectoryConstant.AVATAR_FOLDER.concat(systemCode);
                JcaRepository repo = systemConfig.getRepoByKey(SystemConfig.REPO_UPLOADED_MAIN,
                        jcaAccountEditDto.getCompanyId(), null);
                FileUploadParamDto param = new FileUploadParamDto();

                String extension = FilenameUtils.getExtension(jcaAccountEditDto.getAvatarFile().getOriginalFilename());
                String nameFile = null;
                // Check rename file when upload

                nameFile = String.valueOf(UUID.randomUUID());
                if (StringUtils.isNotBlank(jcaAccountEditDto.getUsername())) {
                    nameFile = jcaAccountEditDto.getUsername().concat("_").concat(nameFile);
                } else {
                    nameFile = FilenameUtils.getBaseName(jcaAccountEditDto.getAvatarFile().getOriginalFilename())
                            .concat("_").concat(nameFile);
                }
                String fullFileName = nameFile.concat(".").concat(extension);

                param.setFileByteArray(jcaAccountEditDto.getAvatarFile().getBytes());
                param.setFileName(fullFileName);
                param.setTypeRule(1);
                param.setSubFilePath(subFilePath);
                param.setCompanyId(jcaAccountEditDto.getCompanyId());
                param.setRename(nameFile);
                param.setExtension(extension);

                param.setRepositoryId(repo.getId());
                FileUploadResultDto uploadResultDto = fileStorageService.upload(param);
                String filePath = uploadResultDto.getFilePath();
                account.setAvatarRepoId(repo.getId());
                account.setAvatar(filePath);
            } catch (Exception e) {
                // TODO: handle exception
            }
            // Save file
            accRepository.update(account);
        }
        /** set user profile util */
//        UserProfileUtils.getUserProfile().setArchiveFlag(accountEditDto.isArchiveFlag());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#getListSelect2Dto(java.lang.
     * String, java.lang.Long, boolean)
     */
    @Override
    public List<Select2Dto> getListSelect2Dto(String key, Long companyId, boolean isPaging) throws Exception {
        return accRepository.findListSelect2Dto(key, companyId, isPaging);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#findByCode(java.lang.String,
     * java.lang.Long)
     */
    @Override
    public JcaAccount findByCode(String code, Long companyId) {
        return accRepository.getByCode(code, companyId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.admin.service.AccountService#
     * getListAccountByListUserNameAndCompany(java.util.List, java.lang.Long)
     */
    @Override
    public List<JcaAccount> getListAccountByListUserNameAndCompany(List<String> userName, Long companyId) {
        List<JcaAccount> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(userName)) {
            List<String> allUsernames = new ArrayList<>(userName);
            while (CollectionUtils.isNotEmpty(allUsernames)) {
                List<String> tempUsernames = null;
                if (CollectionUtils.size(allUsernames) > 999) {
                    tempUsernames = allUsernames.subList(0, 999);
                } else {
                    tempUsernames = allUsernames;
                }
                result.addAll(accRepository.getListByListUserNameAndCompanyId(userName, companyId));
                allUsernames.removeAll(tempUsernames);
            }
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#findOrgByAccountId(java.lang.
     * Long)
     */
    @Override
    public List<JcaAccountOrgDto> findOrgByAccountId(Long accountId) {
        return accOrgRepository.findOrgByAccountId(null, accountId, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.admin.service.AccountService#
     * findSelect2DtoByMultipleConditions(java.lang.String, boolean, java.lang.Long)
     */
    @Override
    public List<Select2Dto> findSelect2DtoByMultipleConditions(String key, boolean isPaging, Long companyId) {
        return accRepository.findSelect2DtoByMultipleConditions(key, isPaging, companyId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#getAllEmailofAllCompany(java.
     * util.List, java.lang.String, boolean)
     */
    @Override
    public Select2ResultDto getAllEmailofAllCompany(List<Long> accountIds, String key, boolean isPaging) {
        Select2ResultDto obj = new Select2ResultDto();
        List<Select2Dto> result = accRepository.findAllEmailofAllCompany(accountIds, key, isPaging);
        if (result == null) {
            result = new ArrayList<>();
        }
        obj.setTotal(result.size());
        obj.setResults(result);
        return obj;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#getGroupIdsByCodes(java.util.
     * List, java.lang.Long)
     */
    @Override
    public List<Long> getGroupIdsByCodes(List<String> codes, Long companyId) throws SQLException {
        return accountTeamRepository.findGroupIdsByCodes(codes, companyId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#getSelect2DtoByOrg(java.lang.
     * String, java.lang.Long, boolean)
     */
    @Override
    public List<Select2Dto> getSelect2DtoByOrg(String key, Long orgId, boolean isPaging) {
        return accOrgRepository.findAccountByOrgAndKey(key, orgId, isPaging);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.admin.service.AccountService#
     * getAccountActiveByCodeAndCompanyId(java.lang.String, java.lang.Long)
     */
    @Override
    public JcaAccount getAccountActiveByCodeAndCompanyId(String code, Long companyId) {
        return accRepository.getAccountActiveByCodeAndCompanyId(code, companyId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#getGroupsByAccountId(java.lang.
     * Long)
     */
    @Override
    public List<Long> getGroupsByAccountId(Long accountId) throws SQLException {
        return accountTeamRepository.findGroupsByAccountId(accountId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#getPositionsByAccountId(java.
     * lang.Long)
     */
    @Override
    public List<Long> getPositionsByAccountId(Long accountId) throws SQLException {
        Date startDate = CommonDateUtil.removeTime(comService.getSystemDateTime());
        Date endDate = CommonDateUtil.setMaxTime(startDate);
        return accOrgRepository.findPositionsByAccountId(accountId, startDate, endDate);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#getOrgsOfOwnerById(java.lang.
     * Long)
     */
    @Override
    public List<JcaAccountOrgDto> getOrgsOfOwnerById(Long accountId) throws SQLException {
        return accOrgRepository.findOrgByAccountId(null, accountId, false);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#getOldOrgForDelegate(java.lang.
     * Long, java.lang.Long, java.lang.String, java.lang.String, int, int)
     */
    @Override
    public List<JcaAccountOrgDelegateDto> getOldOrgForDelegate(Long id, Long accountId, String orgName,
            String deletedBy, int startIndex, int sizeOfPage) throws SQLException {
        return accOrgRepository.findOldOrgForDelegate(id, accountId, orgName, startIndex, sizeOfPage);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#getOrgsForDelegator(java.lang.
     * Long, java.util.List)
     */
    @Override
    public List<JcaAccountOrgDto> getOrgsForDelegator(Long accountId, List<Long> ids) throws SQLException {
        Date expiredDate = CommonDateUtil.removeTime(comService.getSystemDateTime());
        return accOrgRepository.findOrgsForDelegator(accountId, ids, expiredDate);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.admin.service.AccountService#countOldOrgForDelegate(java.
     * lang.Long, java.lang.Long, java.lang.String)
     */
    @Override
    public Integer countOldOrgForDelegate(Long id, Long accountId, String orgName) throws SQLException {
        return accOrgRepository.countOldOrgForDelegate(id, accountId, orgName);
    }

    /**
     * <p>
     * Distinct by key.
     * </p>
     *
     * @author Tan Tai
     * @param <T>          the generic type
     * @param keyExtractor type {@link Function<? super T,?>}
     * @return {@link Predicate<T>}
     */
    @SuppressWarnings("unused")
	private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.admin.service.AccountService#
     * getAccountInfoSelect2DtoListByKeyAndListCompanyId(java.lang.String,
     * java.lang.Long, java.lang.Long, java.util.List, boolean)
     */
    @Override
    public List<JcaAccountInfoSelect2Dto> getAccountInfoSelect2DtoListByKeyAndListCompanyId(String key, Long orgId,
            Long accountId, List<Long> companyIds, boolean isPaging) {
        String keyUpper = StringUtils.upperCase(key);
        return accRepository.findListAccountInfoSelect2DtoByKeyAndListCompanyId(keyUpper, orgId, accountId, companyIds,
                isPaging);
    }

    /*
     * (non-Javadoc)
     *
     * @see vn.com.unit.mbal.admin.service.AccountService#
     * getAccountInfoSelect2DtoListByListAccountId(java.util.List)
     */
    @Override
    public List<JcaAccountInfoSelect2Dto> getAccountInfoSelect2DtoListByListAccountId(List<Long> listAccountId) {

        List<JcaAccountInfoSelect2Dto> accountDtoList = new LinkedList<>();
        if (CollectionUtils.isNotEmpty(listAccountId)) {
            List<Long> allIds = new ArrayList<>(listAccountId);
            while (CollectionUtils.isNotEmpty(allIds)) {
                List<Long> tempIds = null;
                if (CollectionUtils.size(allIds) > 999) {
                    tempIds = allIds.subList(0, 999);
                } else {
                    tempIds = allIds;
                }
                accountDtoList
                        .addAll(accRepository.findListAccountInfoSelect2DtoByListAccountId(tempIds, false, false));
                allIds.removeAll(tempIds);
            }
        }

        return accountDtoList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.admin.service.AbstractCommonService#getCommonService()
     */
    @Override
    public vn.com.unit.common.service.JCommonService getCommonService() {
        return comService;
    }

    @Override
    public void syncData() {
        syncLDAP();
        syncDataJob();
    }

    @Override
    @CoreTx
    public boolean syncLDAP() {
        List<JcaAccount> listAccountLDAP = syncAccountLdap();
        boolean isSync = syncAccount(listAccountLDAP, AccountTypeEnum.TYPE_STAFF.toString());
        return isSync;
    }

    @Override
    @CoreTx
    public boolean syncDataJob() {
        List<JcaAccount> listAccountLDAP = mappingDataFormJob();
        boolean isSync = syncAccount(listAccountLDAP, AccountTypeEnum.TYPE_STAFF.toString());
        return isSync;
    }

    /**
     * @return
     */
    private List<JcaAccount> mappingDataFormJob() {
        // TODO Auto-generated method stub
        return null;
    }

    private List<JcaAccount> syncAccountLdap() {

        initialParam();

        List<JcaAccount> listAccount = new ArrayList<JcaAccount>();
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, ldapInitialContextFactory);
        env.put(Context.PROVIDER_URL, ldapProviderUrl);
        env.put(Context.SECURITY_AUTHENTICATION, ldapSecurityAuthentication);
        env.put(Context.SECURITY_PRINCIPAL, ldapSecurityPrincipal);
        env.put(Context.SECURITY_CREDENTIALS, ldapSecurityCredentials);
        System.out.println("############## syncAccountLdap ###########");
        try {

            LdapContext ctx = new InitialLdapContext(env, null);
            // Activate paged results
            int pageSize = 5; // 5 entries per page
            byte[] cookie = null;
            int total;
            ctx.setRequestControls(new Control[] { new PagedResultsControl(pageSize, Control.CRITICAL) });

            SearchControls searchControls = new SearchControls();
            searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            do {
                NamingEnumeration<SearchResult> results = ctx.search(ldapMainGroup,
                        "(&(objectclass=user)(!(userAccountControl:1.2.840.113556.1.4.803:=2)))", searchControls);
                JcaAccount account = null;
                /* for each entry print out name + all attrs and values */
                while (results != null && results.hasMore()) {
                    SearchResult searchResult = (SearchResult) results.next();
                    Attributes att = searchResult.getAttributes();
                    account = new JcaAccount();
                    try {
//                        String division = att.get(DIVISION).get().toString().trim();
//                        String department = att.get(DEPARTMENT).get().toString().trim();
                    } catch (Exception e) {
                    }
                    account.setUsername(att.get(SAMACCOUNT_NAME).get().toString().trim());
                    System.out.println("############## SAMACCOUNT_NAME "
                            + att.get(SAMACCOUNT_NAME).get().toString().trim() + " ###########");
                    if (null != att.get(DISPLAY_NAME) && null != att.get(DISPLAY_NAME).get()) {
                        account.setFullname(att.get(DISPLAY_NAME).get().toString().trim());
                        System.out.println("############## DISPLAY_NAME "
                                + att.get(DISPLAY_NAME).get().toString().trim() + " ###########");
                    }

                    if (null != att.get(MAIL) && null != att.get(MAIL).get()) {
                        account.setEmail(att.get(MAIL).get().toString().trim());
                        System.out.println(
                                "############## MAIL " + att.get(MAIL).get().toString().trim() + " ###########");
                    }

                    listAccount.add(account);
                    System.out.println("------ACCOUNT-----" + account);

                }

                // Examine the paged results control response
                Control[] controls = ctx.getResponseControls();
                if (controls != null) {
                    for (int i = 0; i < controls.length; i++) {
                        if (controls[i] instanceof PagedResultsResponseControl) {
                            PagedResultsResponseControl prrc = (PagedResultsResponseControl) controls[i];
                            total = prrc.getResultSize();
                            if (total != 0) {
                                System.out.println("***************** END-OF-PAGE " + "(total : " + total
                                        + ") *****************\n");
                            } else {
                                System.out.println(
                                        "***************** END-OF-PAGE " + "(total: unknown) ***************\n");
                            }
                            cookie = prrc.getCookie();
                        }
                    }
                } else {
                    System.out.println("No controls were sent from the server");
                }
                // Re-activate paged results
                ctx.setRequestControls(new Control[] { new PagedResultsControl(pageSize, cookie, Control.CRITICAL) });

            } while (cookie != null);

            ctx.close();

        } catch (Exception e) {
            System.out.println("---ERROR---- " + e);
            // logger.error(e.getMessage());
            return null;
        }
        return listAccount;
    }

    private boolean syncAccount(List<JcaAccount> listAccount, String accountType) {
        try {
            System.out.println("listAccountLDAP : " + listAccount);
            Long companyId = UserProfileUtils.getUserPrincipal().getCompanyId();
            if (CollectionUtils.isNotEmpty(listAccount)) {
                List<Long> lstIdActive = new ArrayList<Long>();
                for (JcaAccount acc : listAccount) {
                    System.out.println("Account INSERT : " + acc);
                    JcaAccount item = accRepository.findByUserNameAndCompanyId(acc.getUsername(), companyId);
                    if (item == null) {
                        // insert
                        acc.setActived(true);
                        acc.setEnabled(true);
                        acc.setAccountType(accountType);
                        acc.setCreatedId(UserProfileUtils.getUserPrincipal().getAccountId());
                        acc.setCreatedDate(new Date());
                        accRepository.create(acc);
                        lstIdActive.add(acc.getId());
                    } else {
                        System.out.println("Account UPDATE : " + item);
                        // update
                        item.setActived(true);
                        item.setEnabled(true);
                        item.setEmail(acc.getEmail());
                        item.setFullname(acc.getFullname());
                        item.setUpdatedId(UserProfileUtils.getUserPrincipal().getAccountId());
                        item.setUpdatedDate(new Date());
                        item.setAccountType(accountType);
                        accRepository.update(item);
                        lstIdActive.add(item.getId());
                    }
                }
                if (CollectionUtils.isNotEmpty(lstIdActive)) {
                    // disable LDAP when sync list account remove from LDAP
                    accRepository.updateDiableAccountLDAP(lstIdActive);
                }

            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<AccountItem> findListAccountItemByItemList(List<String> lstItemCode) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<AccountItem> listAllUser() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<AccountItem> findListAccountItemByItemAndDepartment(List<String> lstItemCode,
            List<String> lstDepartment) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JcaAccount getByPhone(String phone) {
        return accRepository.findByPhone(phone);
    }

	@Override
	public JcaAccount getJcaAccountForLogin(String email, String phone, String accountType, String providerId) {
		return accRepository.getJcaAccountForLogin(email, phone, accountType, providerId);
	}

	@Override
	public ReturnObject syncAccountByUsername(String username, Locale locale) {
		ReturnObject returnObject = new ReturnObject();
		JcaAccount account = accRepository.findByUserName(username);
		JcaAccount accountFromLDAP = null;
		// account exist
		if (account != null) {
			// if account exist in DMS and type is system
			if (!StringUtils.equalsIgnoreCase(account.getAccountType(), ApiAccountTypeConstant.TYPE_OTHER)) {
				// update info
				returnObject.setMessageError(messageSource.getMessage("sync.ldap.success", null, locale));
			} else {
				try {
					accountFromLDAP = findAccountLdap(username);
//					accountFromLDAP = account;
				} catch (Exception e) {
					returnObject.setMessageError("System error: " + e.getMessage());
					return returnObject;
				}
				// if account exist in DMS and type is LDAP
				if (accountFromLDAP != null) {
					// update info
					account.setFullname(accountFromLDAP.getFullname());
					account.setEmail(accountFromLDAP.getEmail());
					account.setDeletedDate(null);
					returnObject.setMessageSuccess(messageSource.getMessage("sync.ldap.success", null, locale));
				} else {
					// disable account
					account.setEnabled(false);
					returnObject.setMessageSuccess(messageSource.getMessage("sync.ldap.account.not.found.ldap", null, locale));

				}
			}
		
		} else {
			account = new JcaAccount();
			account.setAccountType(ApiAccountTypeConstant.TYPE_OTHER);
			try {
				accountFromLDAP = findAccountLdap(username);
				account.setFullname(accountFromLDAP.getFullname());
				account.setEmail(accountFromLDAP.getEmail());
			} catch (Exception e) {
				returnObject.setMessageError("System error: " + e.getMessage());
				return returnObject;
			}
			// if exist LDAP
			if (accountFromLDAP != null) {
				// update info
				account.setFullname(accountFromLDAP.getFullname());
				account.setEmail(accountFromLDAP.getEmail());
				returnObject.setMessageSuccess(messageSource.getMessage("sync.ldap.success", null, locale));
			}
			returnObject.setRetObj(account);

		}
		return returnObject;
	}

	private JcaAccount findAccountLdap(String username) throws Exception {
		JcaAccount account = null;
		initialParam();
		Hashtable<String, String> env = new Hashtable<>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, ldapInitialContextFactory);
		env.put(Context.PROVIDER_URL, ldapProviderUrl);
		env.put(Context.SECURITY_AUTHENTICATION, ldapSecurityAuthentication);
		env.put(Context.SECURITY_PRINCIPAL, ldapDomain + "\\" + ldapSecurityPrincipal);
		env.put(Context.SECURITY_CREDENTIALS, ldapSecurityCredentials);
		try {

			LdapContext ctx = new InitialLdapContext(env, null);
			// Activate paged results
			int pageSize = 5; // 5 entries per page
			ctx.setRequestControls(new Control[] { new PagedResultsControl(pageSize, Control.CRITICAL) });

			SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			NamingEnumeration<SearchResult> results = ctx.search(ldapMainGroup,
					"(&(objectclass=user)(!(userAccountControl:1.2.840.113556.1.4.803:=2))(sAMAccountName=" + username
							+ "))",
					searchControls);

			if (results != null && results.hasMore()) {
				SearchResult searchResult = results.next();
				Attributes att = searchResult.getAttributes();
				account = new JcaAccount();

				account.setUsername(username);

				if (null != att.get(DISPLAY_NAME) && null != att.get(DISPLAY_NAME).get()) {
					account.setFullname(att.get(DISPLAY_NAME).get().toString().trim());
				}

				if (null != att.get(MAIL) && null != att.get(MAIL).get()) {
					account.setEmail(att.get(MAIL).get().toString().trim());
				}

				account.setEnabled(true);
			}
			ctx.close();
		} catch (Exception e) {
			logger.error("##sycnLDAP##", e);
			throw e;
		}
		return account;
	}

	@Override
	public JcaAccountDto switchAccount(Long userId, JcaAccountDto account) throws AppException,CoreException {
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		JcaAccount acc = null;
	       try {
	        	acc = accRepository.findOne(userId);
	        }catch(Exception ex) {
	        	String messageError = checkErrorMessage("users.id.must.not.be",null,locale);
				throw new CoreException(messageError);
	        }
        try {
        	acc.setGoogleLogin(account.getGoogleFlag());;
        	acc.setFacebookLogin(account.getFacebookFlag());
        	acc.setAppleLogin(account.getAppleFlag());
        	acc.setUid(account.getUid());
            accRepository.updateSocialNetwork(acc);
            return account;
        } catch (NullPointerException e) {
            throw new AppException("B500", "B500", null);
        }
	}
	


	@Override
	public void updatePasswordGad(Long accountId, String passwordNew) throws DetailException {
//        String enPassword;
        try {
//            enPassword = CommonPasswordUtil.encryptString(passwordNew);
        } catch (Exception e) {
            throw new DetailException(CoreExceptionCodeConstant.E301901_ENCRYPT_PASSWORD_ERROR, true);
        }
        accRepository.updatePasswordGad(accountId, passwordNew);
    }

    @Override
    public void isCheckResetPassword(Long userId) throws DetailException {
        accRepository.updateCheckResetPassword(userId);
    }

    @Override
    public String checkGad(String agent) {
        return accRepository.checkGad(agent);
    }


    public String checkErrorMessage(String codeError, Object[] param, Locale locale) {
		String messageError = messageSource.getMessage(codeError,param,locale);
		
		return messageError;
	}

	@Override
	public JcaAccountDto updateAccountAvatar(Long userId, String urlZalo, String urlFacebook, MultipartFile avatarFile,
			String lang, Locale locale) throws AppException, CoreException {
		JcaAccount acc = null;
		try {
			acc = accRepository.findOne(userId);
		} catch (Exception ex) {
			String messageError = checkErrorMessage("users.id.must.not.be", null, locale);
			throw new CoreException(messageError);
		}
		JcaAccountDto dto = new JcaAccountDto();
		if (null != avatarFile) {
	        List<String> names = new ArrayList<>();
	        CollectionUtils.addAll(names, avatarFile.getOriginalFilename().split("/"));
	        String name = avatarFile.getOriginalFilename();
	        if(CollectionUtils.isNotEmpty(names)) {
	            name = names.get(names.size() - 1);
	        }
			String contentTypeEx = getContentType(name);
			if (!contentTypeEx.contains("image") && !contentTypeEx.equals("application/pdf")) {
				throw new AppException("B500", "B500", null);
			}
			String contentType = avatarFile.getContentType();

			try {
				byte[] imageByte = avatarFile.getBytes();
				ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
				if (ImageIO.read(bis) == null) {
					bis.close();
					throw new AppException("B500", "B500", null);
				}
				bis.close();
			} catch (IOException e) {
				throw new AppException("B500", "B500", null);
			}
//			if (!StringUtils.isNotEmpty(contentType) || !contentType.contains("image")) {
//				throw new AppException("B500", "B500", null);
//			}
			String systemCode = companyService.getSystemCodeByCompanyId(acc.getCompanyId());
			String subFilePath = DirectoryConstant.AVATAR_FOLDER.concat(systemCode);
			FileResultDto repoResultDto = jRepositoryService.uploadFileBySettingKey(avatarFile, acc.getUsername(),
					SystemConfig.REPO_UPLOADED_MAIN, 2, null, subFilePath, acc.getCompanyId(), locale);
			if (repoResultDto.isStatus()) {
				acc.setAvatar(repoResultDto.getFilePath());
//				acc.setRepositoryId(repoResultDto.getRepositoryId());
			} else {
				throw new AppException(repoResultDto.getMessage(), null, null);
			}
		}
		try {
			acc.setUrlZalo(urlZalo);
			acc.setUrlFacebook(urlFacebook);
			accRepository.update(acc);
			dto.setAvatar(acc.getAvatar());
			dto.setUrlZalo(urlZalo);
			dto.setUrlFacebook(urlFacebook);
			return dto;
		} catch (Exception e) {
			throw new AppException("B500", "B500", null);
		}
	}

	public static String getContentType(String fileNameFull) {
		String contentType = "application/octet-stream";

		String extensionFile = getFileExtension(fileNameFull);

		if (StringUtils.isNotEmpty(extensionFile)) {
			if (extensionFile.equals("pdf"))
				contentType = "application/pdf";
			else if (extensionFile.equals("txt"))
				contentType = "text/plain";
			else if (extensionFile.equals("exe"))
				contentType = "application/octet-stream";
			else if (extensionFile.equals("zip"))
				contentType = "application/zip";
			else if (extensionFile.equals("doc"))
				contentType = "application/msword";
			else if (extensionFile.equals("xls"))
				contentType = "application/vnd.ms-excel";
			else if (extensionFile.equals("xlsx"))
				contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
			else if (extensionFile.equals("ppt"))
				contentType = "application/vnd.ms-powerpoint";
			else if (extensionFile.equals("gif"))
				contentType = "image/gif";
			else if (extensionFile.equals("png"))
				contentType = "image/png";
			else if (extensionFile.equals("jpeg"))
				contentType = "image/jpeg";
			else if (extensionFile.equals("jpg"))
				contentType = "image/jpeg";
			else if (extensionFile.equals("mp3"))
				contentType = "audio/mpeg";
			else if (extensionFile.equals("wav"))
				contentType = "audio/x-wav";
			else if (extensionFile.equals("mpeg"))
				contentType = "video/mpeg";
			else if (extensionFile.equals("mpg"))
				contentType = "video/mpeg";
			else if (extensionFile.equals("mpe"))
				contentType = "video/mpeg";
			else if (extensionFile.equals("mov"))
				contentType = "video/quicktime";
			else if (extensionFile.equals("avi"))
				contentType = "video/x-msvideo";
			else if (extensionFile.equals("flv"))
				contentType = "video/flv";
		}
		return contentType;
	}

	public static String getFileExtension(String fileNameFull) {
		String result = null;

		if (StringUtils.isNotEmpty(fileNameFull)) {
			int beginIndex = fileNameFull.lastIndexOf('.');

			if (beginIndex != -1) {
				beginIndex = beginIndex + 1;
				int endIndex = fileNameFull.length();

				result = fileNameFull.substring(beginIndex, endIndex).toLowerCase();
			}
		}

		return result;
	}

	@Override
	public JcaAccountDto updateFaceMask(Long userId, String faceMask) throws AppException,CoreException {
		Locale locale = LangugeUtil.getLanguageFromHeader(request);
		JcaAccount acc = null;
		JcaAccountDto account = new JcaAccountDto();
	       try {
	        	acc = accRepository.findOne(userId);
	        }catch(Exception ex) {
	        	String messageError = checkErrorMessage("users.id.must.not.be",null,locale);
				throw new CoreException(messageError);
	        }
        try {
        	acc.setFaceMask(faceMask);
            accRepository.update(acc);
            UserProfileUtils.getUserPrincipal().setFaceMask(faceMask);
            account.setUserId(userId);
            account.setFaceMask(faceMask);
            return account;
        } catch (NullPointerException e) {
            throw new AppException("B500", "B500", null);
        }
	}

    @Override
    public JcaAccount findByEmailDto(String email) {
        return accRepository.findByEmailDto(email);
    }
    
    @Override
    public ConfirmDecreeDto saveConfirmDecree(ConfirmDecreeDto confirm){
    	ConfirmDecree confirmDecree = new ConfirmDecree() ;
  
		confirmDecree.setUserName(confirm.getUserName());
    	confirmDecree.setIdNumber(confirm.getIdNumber());     	
    	confirmDecree.setAgentCode(confirm.getAgentCode()); 
    	confirmDecree.setAgentName(confirm.getAgentName()); 
    	confirmDecree.setAgentType(confirm.getAgentType()); 
    	confirmDecree.setConfirmTime(new Date()); 
    	confirmDecree.setFunctionType(confirm.getFunction()); 
    	confirmDecree.setNewAgentName(confirm.getNewAgentName());
    	confirmDecree.setNewIdNumber(confirm.getNewIdNumber());
    	confirmDecree.setNewDateOfIssue(confirm.getNewDateOfIssue());
    	confirmDecree.setNewPlaceOfIssue(confirm.getNewPlaceOfIssue());
    	confirmDecree.setNewDateOfBirth(confirm.getNewDateOfBirth());
    	confirmDecree.setNewMobilePhone(confirm.getNewMobilePhone());
    	confirmDecree.setNewEmail(confirm.getNewEmail());
    	confirmDecree.setNewAddress(confirm.getNewAddress());
    	confirmDecree.setNewZipCode(confirm.getNewZipCode());
    	confirmDecree.setNewTaxCode(confirm.getNewTaxCode());
    	confirmDecree.setNewBussinessHouseHolds(confirm.getNewBussinessHouseHolds());
    	confirmDecree.setNewTaxCodeInb(confirm.getNewTaxCodeInb());

    	confirmDecreeRepository.save(confirmDecree); 

		return confirm ; 
	}

    public boolean checkConfirmDecree(String userName) {
    	ConfirmDecree decreeInfo = confirmDecreeRepository.findByUserName(userName);
    	if (decreeInfo == null) {
    		return false;
    	}
    	return true;
    }
    
    @Override
    public boolean saveConfirmSop() {
    	if (!checkConfirmSop(UserProfileUtils.getFaceMask())) {
    		ConfirmSop entity = new ConfirmSop();
        	entity.setUserName(UserProfileUtils.getFaceMask());
        	entity.setConfirmTime(new Date());

        	confirmSopRepository.save(entity);
    	}

		return true;
	}
    
    public boolean checkConfirmSop(String userName) {
    	int count = confirmDecreeRepository.checkConfirmSopByUserName(userName);
    	if (count > 0) {
    		return true;
    	}
    	return false;
    }
}
