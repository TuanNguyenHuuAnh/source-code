/*******************************************************************************
 * Class        CustomAuthenticationProvider
 * Created date 2017/02/15
 * Lasted date  2017/02/15
 * Author       KhoaNA
 * Change log   2017/02/1501-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.config;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import vn.com.unit.common.utils.CommonBase64Util;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.UserAuthorityDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.enumdef.AccountTypeEnum;
import vn.com.unit.core.security.UserPrincipal;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.exception.CustomAuthenticationException;
import vn.com.unit.ep2p.admin.service.AccountPasswordService;
import vn.com.unit.ep2p.admin.service.AccountService;
import vn.com.unit.ep2p.admin.service.AppAuthorityService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.core.utils.LDAPUtil;
import vn.com.unit.ep2p.dto.CompanyDto;

/**
 * CustomAuthenticationProvider
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    /** accountService */
    @Autowired
    private AccountService accService;

//    @Autowired
//    private UserAttemptsService userAttemptsService;

    @Autowired
    private AccountPasswordService accountPasswordService;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private CompanyService companyService;

//    @Autowired
//    private CommonService comService;

//    @Autowired
//    private JcaSystemConfigService jcaSystemConfigService;

//    @Autowired
//    private IntegSessionLoginService integSessionLoginService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AppAuthorityService authorityService;
    
    /**
     * Authentication database system and LDAP
     * 
     * @param authentication type Authentication
     * @return Authentication
     * @throws AuthenticationException
     * @author KhoaNA
     */
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String[] usernameAndSystem = StringUtils.split(authentication.getName(), ConstantCore.DOLLAR);
        String username = usernameAndSystem[0];
//        String system = usernameAndSystem[1].substring(1);
        String system = "";
        String password = (String) authentication.getCredentials();
        String languge = null;
        JcaAccount account = null;
        CompanyDto company = null;
        boolean isSuccessLogin = false;
        int flagLoginFirst = 0;
        boolean forceChangePass = false;
//        Date sysdate = comService.getSystemDateTime();
        boolean userLocal = false;
//        boolean isSaveLoginFail = true;

        if (StringUtils.isEmpty(username)) {
            // Username is required.
            throw new CustomAuthenticationException("B001", username, null, system);
        }

        if (StringUtils.isEmpty(password)) {
            // Password is required.
            throw new CustomAuthenticationException("B002", username, null, system);
        }

        List<JcaAccount> listAccount = new ArrayList<>();
        listAccount = accService.getListByUserName(username);

        if (listAccount == null || listAccount.isEmpty()) {
//            userAttemptsService.updateFailAttempts(username, null, null);
            // Username does not exist.
            throw new CustomAuthenticationException("B003", username, null, system);
        }

        if (StringUtils.isNotBlank(system)) {
            company = companyService.findBySystemCode(system);
            if (null == company) {
                // The system not exists.
                throw new CustomAuthenticationException("B005", username, null, system);
            }
        }

        if (listAccount.size() > 1) {
            if (StringUtils.isBlank(system)) {
                // The system is required.
                throw new CustomAuthenticationException("B004", username, null, system);
            }
            account = accService.findByUserNameAndCompanyId(username, company.getId());
        } else {
            account = listAccount.get(0);
        }

        if (account == null) {
//            userAttemptsService.updateFailAttempts(username, null, null);

            // check userName is account or email
            int email = username.indexOf("@");
            if (email > 0) {
                // email does not exist.
                throw new CustomAuthenticationException("B016", username, null, system);
            }
            // Username does not exist.
            throw new CustomAuthenticationException("B003", username, null, system);
        }

        // Get company from account
        if (null == company || !company.getId().equals(account.getCompanyId())) {
            company = companyService.findById(account.getCompanyId());
        }

        // Check user with system login
        if (StringUtils.isNotBlank(system)) {
            if (!system.equalsIgnoreCase(company.getSystemCode())) {
                // Username does not exist.
                throw new CustomAuthenticationException("B003", account.getUsername(), null, system);
            }
        }

        system = company.getSystemCode();

        if (account.getAccountType() != null && account.getAccountType().equals(AccountTypeEnum.TYPE_STAFF.toString())) {

            // Authentication LDAP
            if (!isSuccessLogin) {
                isSuccessLogin = LDAPUtil.checkLogin(account.getUsername(), password, systemConfig, 2L);
                userLocal = false;
            }
        } else {
            // Authentication DB
            try {
            	if (!isSuccessLogin) {
            		isSuccessLogin = checkLoginDB(account, password);
            	}
                flagLoginFirst = Integer.parseInt(
                        systemConfig.getConfig(SystemConfig.FLAG_FIRST_TIME_LOGIN, account.getCompanyId()) != null
                                ? systemConfig.getConfig(SystemConfig.FLAG_FIRST_TIME_LOGIN, account.getCompanyId())
                                : "0");
                userLocal = true;
            } catch (NoSuchAlgorithmException e) {
                // Login failed.
                throw new CustomAuthenticationException("invalid.user.password", account.getUsername(),
                        account.getCompanyId(), system);
            }
        }

        // Check login
        if (isSuccessLogin) {
            // if (!userAttemptsService.resetFailAttempts(account))
            // Account locked.
            // throw new CustomAuthenticationException("B007", account.getUsername(),
            // account.getCompanyId(), system);
        } else {
//            if (account.isActived()) {
//                if (isSaveLoginFail) {
//                    userAttemptsService.updateFailAttempts(account.getUsername(), account, account.getId());
//                }
//            } else {
//                // Account locked.
//                throw new CustomAuthenticationException("B007", account.getUsername(), account.getCompanyId(), system);
//            }
            // Login failed.
            throw new CustomAuthenticationException("invalid.user.password", account.getUsername(),
                    account.getCompanyId(), system);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        // check first login or expired password
        UserAuthorityDto authorityDto = new UserAuthorityDto();
        authorityDto.setId(account.getId());
        if (flagLoginFirst == 1) {
            if (!accountPasswordService.isFirstLogin(account.getId())
                    && !accountPasswordService.checkPassExpired(account)) {
                // Find all role for account
                authorities = authorityService.findAuthorityDetail(authorityDto);
            } else {
                forceChangePass = true;
            }
        } else {
            authorities = authorityService.findAuthorityDetail(authorityDto);
        }

        // TODO: Test
        authorities.add(new SimpleGrantedAuthority("ROLE_AUTHED"));

        // Build UserProfile
        UserPrincipal userProfile = accService.buildUserProfile(account, authorities, company);
        // Set force change password when login successful
        userProfile.setForceChangePass(forceChangePass);
        userProfile.setLocalAccount(userLocal);
        
        /** END */
        // set languge
        if (!StringUtils.isBlank(languge)) {
            userProfile.setDefaultLang(languge);
            userProfile.setLocale(new Locale(languge));
        } else {
            userProfile.setDefaultLang("vi");
            userProfile.setLocale(new Locale("vi"));
        }

        return new UsernamePasswordAuthenticationToken(userProfile, userProfile.getPassword(), authorities);
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

    /**
     * checkLoginDB
     *
     * @param account  type Account
     * @param password type String
     * @return boolean
     * @author KhoaNA
     */
    public boolean checkLoginDB(JcaAccount account, String password) throws NoSuchAlgorithmException {
        return passwordEncoder.matches(password, account.getPassword());
    }

    public boolean byPassLoginByParam(String paramByPass, HttpServletRequest request) throws AuthenticationException {
        boolean isSuccess = Boolean.FALSE;
//        String authType = AuthTypeEnum.BASIC_AUTH_BP.toString();

//        String sessionId = paramByPass;
        String decodedString = CommonBase64Util.decode(paramByPass);
        String userName = decodedString.substring(0, decodedString.indexOf(ConstantCore.DOLLAR));

        List<JcaAccount> accLst = accService.getListByUserName(userName);
        if (accLst.isEmpty()) {
            throw new CustomAuthenticationException("B004", userName, null, null);
        }
        if (null != accLst && accLst.size() > 1) {
            throw new CustomAuthenticationException("B004", userName, null, null);
        }
//        JcaAccount acc = accLst.get(0);
//        Long accountId = acc.getId();

        return isSuccess;
    }

//    private void addRoleTest(List<GrantedAuthority> authorities) {
//
//        String a = "SA1#S13_RuleExceptionManagement,SA1#S12_RuleManagement,SRP#S00_AdminReport,SRP#S00_AdminReport:Edit,SC0#S00_Administrator,SC0#S00_Administrator:Edit,JPM#10000_0002,JPM#10020_0002,JPM#10400_0002,JPM#10580_0002,JPM#10600_0002,JPM#10640_0002,SA1#S00_AuthenticationManagement,SA1#S00_AuthenticationManagement:Edit,SRP#S02_AuthorityReport,SRP#S02_AuthorityReport:Edit,MS1#S14_Business,MS1#S14_Business:Edit,HDBank#CAR_RENTAL,MS1#S16_CalendarType,MS1#S16_CalendarType:Edit,EF2#S04_CategoryList,EF2#S04_CategoryList:Edit,SA1#S09_CAManagement,SA1#S09_CAManagement:Edit,SC1#S07_CompanyConfig,SC1#S07_CompanyConfig:Edit,SC1#S06_CompanyList,SC1#S06_CompanyList:Edit,MS1#S14_ContacManagement,MS1#S14_ContacManagement:Edit,MS1#S16_CuratorUnit,MS1#S16_CuratorUnit:Edit,PM1#S01_DisplayWorkflow,PM1#S01_DisplayWorkflow:Edit,MS1#S13_ECM_Repository,MS1#S13_ECM_Repository:Edit,SM1#S02_EmailManagement,SM1#S02_EmailManagement:Edit,SM1#S03_EmailTemplate,SM1#S03_EmailTemplate:Edit,FB1#S01_Feedback,FB1#S01_Feedback:Edit,EF2#S05_RoleForComponent,EF2#S05_RoleForComponent:Edit,SA1#S06_ItemManagement,SA1#S06_ItemManagement:Edit,SA1#S03_GroupManagement,SA1#S03_GroupManagement:Edit,SC1#S05_JobManagement,SC1#S05_JobManagement:Edit,SC1#S03_JobMaster,SC1#S03_JobMaster:Edit,SC1#S02_JobSchedule,SC1#S02_JobSchedule:Edit,MS0#S00_Master_Data,MS0#S00_Master_Data:Edit,SC1#S02_MenuList,SC1#S02_MenuList:Edit,EF1#S03_MyDocuments,EF1#S03_MyDocuments:Edit,EF1#S01_MyService,EF1#S01_MyService:Edit,MS1#S06_Organization,MS1#S06_Organization:Edit,MS1#S17_OrgPerson,MS1#S17_OrgPerson:Edit,SA1#S10_AuthorityComment,SA1#S10_AuthorityComment:Edit,SA1#S11_RoleForDisplayEmail,SA1#S11_RoleForDisplayEmail:Edit,MS1#S08_Position,MS1#S08_Position:Edit,MS1#S16_ProcessDeploy,MS1#S16_ProcessDeploy:Edit,EF1#S04_FreeFormService,EF1#S04_FreeFormService:Edit,MS1#S15_Process,MS1#S15_Process:Edit,MS1#S13_ProcessManagement,MS1#S13_ProcessManagement:Edit,MS1#S10_Holiday,MS1#S10_Holiday:Edit,EF2#S02_RegisterService,EF2#S02_RegisterService:Edit,MS1#S12_Repository,MS1#S12_Repository:Edit,SA1#S04_AuthorityList,SA1#S04_AuthorityList:Edit,SA1#S01#C01_RoleForAccount,SA1#S01#C01_RoleForAccount:Edit,SA1#S08_RoleForCompany,SA1#S08_RoleForCompany:Edit,SA1#S05_RoleForGroup,SA1#S05_RoleForGroup:Edit,SA1#S07_RoleForPosition,SA1#S07_RoleForPosition:Edit,SA1#S02_RoleList,SA1#S02_RoleList:Edit,MS1#S13_SLAReminders,MS1#S13_SLAReminders:Edit,SC1#S04_ScheduleMaster,SC1#S04_ScheduleMaster:Edit,SM1#S01_SendMail,SM1#S01_SendMail:Edit,EF1#S02_ServiceBoard,EF1#S02_ServiceBoard:Edit,EF2#S03_ServiceList,EF2#S03_ServiceList:Edit,EF2#S01_ServiceManager,EF2#S01_ServiceManager:Edit,SC1#S04_SetDefaultLink,SC1#S04_SetDefaultLink:Edit,JPM#10002_0001,JPM#10003_0001,JPM#10040_0001,JPM#10043_0001,JPM#10000_0001,JPM#10020_0001,JPM#10400_0001,JPM#10580_0001,JPM#10600_0001,JPM#10640_0001,EF0#S00_Support,EF0#S00_Support:Edit,SC0#S00_System,SC0#S00_System:Edit,SRP#S01_SystemLogs,SRP#S01_SystemLogs:Edit,SC1#S01_SystemConfig,SC1#S01_SystemConfig:Edit,EF0#S00_TaskStatistic,EF0#S00_TaskStatistic:Edit,MS1#S15_UserGuideManagement,MS1#S15_UserGuideManagement:Edit,SA1#S01_UserManagementList,SA1#S01_UserManagementList:Edit,HDBank#Dangkynghi,ROLE_AUTHED";
//        List<String> listA = Arrays.asList(a.split(","));
//        for (String role : listA) {
//            authorities.add(new SimpleGrantedAuthority(role));
//
//        }
//    }
}