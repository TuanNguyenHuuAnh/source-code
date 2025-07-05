/*******************************************************************************
 * Class        RoleConstant
 * Created date 2017/03/14
 * Lasted date  2017/03/14
 * Author       trieunh <trieunh@unit.com.vn>
 * Change log   2017/03/14 01-00 trieunh <trieunh@unit.com.vn> create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.constant;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 * RoleConstant
 * 
 * @version 01-00
 * @since 01-00
 * @author trieunh <trieunh@unit.com.vn>
 */
@Component(value = "roleConstant")
@RequestScope
public class RoleConstant {
    // Main role 
    public static String ROLE_JBPM_ADMIN = "admin";
    public static String ROLE_USER = "vccbuser";
    public static String ROLE_MANAGER = "vccbmanager";

    // Admin Management
    public static String ROLE_ADMIN = "ROLE_ADMIN";
    
    // BEGIN SECTION: SC - System Configuration
    /** System configure **/
    public final static String SYSTEM_CONFIG = "SC1#S01_SystemConfig";
    
    /** System configure **/
    public final static String COMPANY_LIST = "SC1#S06_CompanyList";
    
    public final static String COMPANY_CONFIG = "SC1#S07_CompanyConfig";

    /** Menu management */
    public final static String MENU = "SC1#S02_MenuList";
    
    /** Recommend - Go to page*/
    public final static String RECOMMEND_LINK = "SC1#S03_RecommendLink";
    
    /**Default Link*/
    public final static String CUSTOM_ROLE = "SC1#S04_SetDefaultLink";
    
    /**Quart Job*/
    public final static String QUART_JOB = "SC1#S05_QuartJob";
    
    public final static String CONTACT = "MS1#S14_ContacManagement";
    // END SECTION: SC - System Configuration
    
    
    // BEGIN SECTION: SA - System Authorization
    /**ACCOUNT_LIST*/
    public final static String ACCOUNT = "SA1#S01_UserManagementList";
    
    public final static String RULE = "SA1#S12_RuleManagement";
    
    public final static String RULE_EXCEPTION = "SA1#S13_RuleExceptionManagement";

    /** Role For Account */
    public final static String ROLE_FOR_ACCOUNT = "SA1#S01#C01_RoleForAccount";
    
    /** Role For Account */
    public final static String ROLE_FOR_DEPARTMENT = "SA1#S00#C01_RoleForDepartment";
    
    /** Role management */
    public final static String ROLE = "SA1#S02_RoleList";
    
    /**TEAM_MANAGEMENT*/
    public final static String TEAM_MANAGEMENT = "SA1#S03_GroupManagement";   

    /** Authority management */
    public final static String AUTHORITY = "SA1#S04_AuthorityList";
    
    /**ROLE FOR GROUP*/
    public final static String ROLE_TEAM = "SA1#S05_RoleForGroup";
    
    /**ITEM_MANAGEMENT*/
    public final static String ITEM_MANAGEMENT = "SA1#S06_ItemManagement";
    
    /** Position authority management */
    public final static String POSITION_AUTHORITY = "SA1#S07_RoleForPosition";
    
    /** Show Action column for Display Role by Function*/
    public final static String SHOW_ACTION= "SA1#S04#C01_ShowAction";
    
    /** Role for company*/
    public final static String ROLE_COMPANY = "SA1#S08_RoleForCompany";
    
    /** Permission for comment*/
    public final static String AUTHORITY_COMMENT = "SA1#S10_AuthorityComment";
    
    /** Role for display email*/
    public final static String ROLE_FOR_DISPLAY_EMAIL = "SA1#S11_RoleForDisplayEmail";
    // END SECTION: SA - System Authorization    
    
    // BEGIN SECTION: SRP - System Reports
    /**SYSTEM_LOG*/
    public final static String SYSTEM_LOG = "SRP#S01_SystemLogs";
    
    /**Authority Detail*/
    public final static String AUTHORITY_DETAIL = "SRP#S02_AuthorityReport";
    // END SECTION: SRP - System Reports
    
    
    // BEGIN SECTION: MS - Master Data    
    /**BRANCH*/
    public final static String BRANCH = "MS1#S01_BranchList";

    /**COUNTRY*/
    public final static String COUNTRY = "MS1#S02_Country";
    
    /**REGION*/
    public final static String REGION = "MS1#S03_Region";

    /**CITY*/
    public final static String CITY = "MS1#S04_City_Province";      
    
    /**DISTRICT*/
    public final static String DISTRICT = "MS1#S05_District";
      
    /**ORGANIZATION*/
    public final static String ORGANIZATION = "MS1#S06_Organization";    
    
    /**CONSTANT_DISPLAY*/
    public final static String CONSTANT_DISPLAY = "MS1#S07_ConstantDisplay";
    
    /**POSITION*/
    public final static String POSITION = "MS1#S08_Position";
    
    /**SERIAL_INFO*/
    public final static String SERIAL_INFO = "MS1#S09_SerialInfo";
    
    /**SERIAL_SETUP*/
    public final static String SERIAL_SETUP = "MS1#S11_SerialSetup";
    
    /**ISSUE*/
    public final static String ISSUE = "MS1#S08_Position";
    
    /**HOLIDAY*/
    public final static String HOLIDAY = "MS1#S10_Holiday";
    
    /**REPOSITORY*/
    public final static String REPOSITORY = "MS1#S12_Repository";
    
    /**BUSINESS*/
    public final static String BUSINESS = "MS1#S14_Business";
    
    /**PROCESS*/
    public final static String PROCESS = "MS1#S15_Process";
    
    /**PROCESS*/
    public final static String SLA = "MS1#S13_SLAReminders";
    
    /**CALENDAR_TYPE*/
    public final static String CALENDAR_TYPE = "MS1#S16_CalendarType";
    
    /**ORGANIZATION PERSON*/
    public final static String ORG_PERSON = "MS1#S17_OrgPerson";
    
    /**HANDOVER*/
    public final static String HANDOVER = "SC1#H01_HandoverMaster";
    
    /**LOG_API*/
    public final static String LOG_API = "SCREEN#CMS#PAGE_LIST_LOG";
    
    /**USER_LOGIN*/
    public final static String USER_LOGIN = "SCREEN#CMS#TRACKING_USER_LOGIN";
  
    // END SECTION: MS - Master Data 
    
    //BEGIN SECTION: SM - SEND MAIL    
    public final static String SEND_MAIL = "SM1#S01_SendMail";
    public final static String EMAIL_MANAGEMENT = "SM1#S02_EmailManagement";
    public final static String EMAIL_TEMPLATE = "SM1#S03_EmailTemplate";
    //END SECTION: SM - SEND MAIL    
    
    /**ROLE ADMIN CONFIG*/
    public final static String JBPM = "SC1#S01#C01_jBPM";
    public final static String NODEJS= "SC1#S01#C01_NodeJS";
    
    public static final String SVC_BOARD = "EF1#S02_ServiceBoard";
    public static final String CATEGORY_LIST = "EF2#S04_CategoryList";
    
    public static final String MY_DOCUMENTS = "EF1#S03_MyDocuments";
    
    public static final String USER_GUIDE = "MS1#S15_UserGuideManagement";
    
    /**Feedback*/
    public final static String FEEDBACK = "FB1#S01_Feedback";
    
    /** Curator unit */
    public final static String CURATOR_UNIT = "MS1#S16_CuratorUnit";
    // END SECTION: SC - System Configuration
    
    public final static String DISPLAY_WORKFLOW = "PM1#S01_DisplayWorkflow";
    
    /**ARCHIVE*/
    public static final String ARCHIVE = "AR0#S01_ArchiveManagement";
    public static final String ARCHIVE_ROLLBACK = "AR0#S02_ArchiveRollbackManagement";
    public static final String ARCHIVE_REVIEW = "AR0#S03_ArchiveCheck";
    
    /**CONSTANT**/
	public static final String SCREEN_JCA_CONSTANT = "SCREEN#JCA_CONSTANT";
	public static final String BUTTON_JCA_CONSTANT_ADD = "BUTTON#JCA_CONSTANT#ADD";
	public static final String BUTTON_JCA_CONSTANT_EDIT = "BUTTON#JCA_CONSTANT#EDIT";
	public static final String BUTTON_JCA_CONSTANT_DELETE = "BUTTON#JCA_CONSTANT#DELETE";
	public static final String BUTTON_JCA_CONSTANT_EXPORT = "BUTTON#JCA_CONSTANT#EXPORT";
    /**Presentation*/
    public static final String SCREEN_PRESENTATION = "SCREEN#PRESENTATION";
    public static final String ADD_PRESENTATION = "ADD#PRESENTATION";
    public static final String EDIT_PRESENTATION = "EDIT#PRESENTATION";
    public static final String DELETE_PRESENTATION = "DELETE#PRESENTATION";
    
    /**Cop import*/
    public static final String SCREEN_COP_IMPORT = "SCREEN#COP_IMPORT";

    /**Reset password**/
    public static final String RESET_PASSWORD = "MS1#S18_ResetPassword";
}
