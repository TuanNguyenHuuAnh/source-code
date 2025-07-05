/*******************************************************************************
 * Class        ：RoleConstant
 * Created date ：2017/03/14
 * Lasted date  ：2017/03/14
 * Author       ：trieunh <trieunh@unit.com.vn>
 * Change log   ：2017/03/14：01-00 trieunh <trieunh@unit.com.vn> create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.constant;

/**
 * RoleConstant
 * 
 * @version 01-00
 * @since 01-00
 * @author trieunh <trieunh@unit.com.vn>
 */
public class AdminRoleConstant {
    // Main role 
    public static final String ROLE_JBPM_ADMIN = "admin";
    public static final String ROLE_USER = "vccbuser";
    public static final String ROLE_MANAGER = "vccbmanager";
    
    // System configure
    public static final String PAGE_SYSTEM_CONFIG = "SC1#S01_SystemConfig";

    // Role management
    public static final String PAGE_ROLE_LIST = "SR1#S01_RoleList";
    public static final String PAGE_ROLE_ADD = "SR1#S01_RoleAdd";
    public static final String PAGE_ROLE_EDIT = "SR1#S01_RoleEdit";

    // News management
    public static final String PAGE_NEWS_LIST = "AN1#S01_NewsList";
    public static final String PAGE_NEWS_EDIT = "AN1#S01_NewsEdit";
    public static final String PAGE_NEWS_DETAIL = "AN1#S01_NewsDetail";
    // News management - Button
    public static final String BUTTON_NEWS_ADD = "AN1#S01#B01_CREATE";
    public static final String BUTTON_NEWS_EDIT = "AN1#S01#B02_EDIT";
    public static final String BUTTON_NEWS_DELETE = "AN1#S01#B03_DELETE";
    public static final String BUTTON_NEWS_DETAIL = "AN1#S01#B04_DETAIL";
    public static final String BUTTON_NEWS_SUBMIT = "AN1#S01#GB1_SUBMIT";
    public static final String BUTTON_NEWS_APPROVER = "AN1#S01#B07_APPROVE";
    public static final String BUTTON_NEWS_REJECT = "AN1#S01#B06_REJECT";

    // News category management
    public static final String PAGE_NEWS_CATEGORY_LIST = "AN1#S02_NewsCategoryList";
    public static final String PAGE_NEWS_CATEGORY_EDIT = "AN1#S02_NewsCategoryEdit";

    // News type management
    public static final String PAGE_NEWS_TYPE_LIST = "AN1#S03_NewsTypeList";
    public static final String PAGE_NEWS_TYPE_EDIT = "AN1#S03_NewsTypeEdit";

    // Shareholder management
    public static final String PAGE_INVENDOR_CATEGORY_LIST = "AS1#S01_ShareholdersList";
    public static final String PAGE_INVENDOR_CATEGORY_EDIT = "AS1#S01_ShareholdersEdit";
    public static final String PAGE_INVENDOR_CATEGORY_DETAIL = "AS1#S01_ShareholdersDetail";
    public static final String BUTTON_INVENDOR_CATEGORY_DELETE = "AS1#S01#B03_DELETE";
    public static final String BUTTON_INVENDOR_CATEGORY_EDIT = "AS1#S01#B02_EDIT";
    public static final String BUTTON_INVENDOR_CATEGORY_DETAIL = "AS1#S01#B04_DETAIL";
    public static final String BUTTON_INVENDOR_CATEGORY_ADD = "AS1#S01#B01_CREATE";
    //TODO change const value
    public static final String BUTTON_INVENDOR_CATEGORY_SUBMIT_GROUP = "AS1#S01#B05_SUBMIT";
    public static final String BUTTON_INVENDOR_CATEGORY_APPROVE = "AS1#S01#B07_APPROVE";
    public static final String BUTTON_INVENDOR_CATEGORY_REJECT = "AS1#S01#B06_REJECT";

    public static final String PAGE_INTRODUCTION_LIST = "AI1#S01_IntroductionList";
    public static final String PAGE_INTRODUCTION_EDIT = "AI1#S01_IntroductionEdit";
    public static final String BUTTON_INTRODUCTION_DELETE = "AI1#S01#B03_DELETE";
    public static final String BUTTON_INTRODUCTION_EDIT = "AI1#S01#B02_EDIT";
    public static final String BUTTON_INTRODUCTION_ADD = "AI1#S01#B01_CREATE";
    public static final String BUTTON_INTRODUCTION_APPROVE = "AI1#S01#B07_APPROVE";
    public static final String BUTTON_INTRODUCTION_REJECT = "AI1#S01#B06_REJECT";
    public static final String BUTTON_INTRODUCTION_SUBMIT = "AI1#S01#GB1_SUBMIT";
    // Introduction management - Button
    public static final String PAGE_INTRODUCTION_CATEGORY_LIST = "AI2#S01_IntroductionCategoryList";
    public static final String PAGE_INTRODUCTION_CATEGORY_EDIT = "AI2#S01_IntroductionCategoryEdit";
    public static final String BUTTON_INTRODUCTION_CATEGORY_DELETE = "AI2#S01#B03_DELETE";
    public static final String BUTTON_INTRODUCTION_CATEGORY_EDIT = "AI2#S01#B02_EDIT";
    public static final String BUTTON_INTRODUCTION_CATEGORY_ADD = "AI2#S01#B01_CREATE";
    public static final String BUTTON_INTRODUCTION_CATEGORY_DETAIL = "AI2#S01#B04_DETAIL";

    public static final String VIEW_INTRODUCTION = "AI1#S01_IntroductionDetail";
    public static final String VIEW_INTRODUCTION_CATEGORY = "AI2#S01#B04_DETAIL";

    // Job management
    public static final String PAGE_JOB_LIST = "AJ1#S01_JobList";
    public static final String PAGE_JOB_EDIT = "AJ1#S01_JobEdit";
    public static final String PAGE_JOB_DETAIL = "AJ1#S01_JobDetail";
    // Job management - button
    public static final String BUTTON_JOB_EDIT = "AJ1#S01#B01_EDIT";
    public static final String BUTTON_JOB_CREATE = "AJ1#S01#B01_CREATE";
    public static final String BUTTON_JOB_DELETE = "AJ1#S01#B01_DELETE";
    public static final String BUTTON_JOB_DETAIL = "AJ1#S01#B03_DETAIL";
    public static final String GROUP_BUTTON_JOB_SUBMIT = "AJ1#S01#GB1_SUBMIT";
    public static final String BUTTON_JOB_APPROVER = "AJ1#S01#B07_APPROVE";
    public static final String BUTTON_JOB_REJECT = "AJ1#S01#B06_REJECT";

    // admin management
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    // Menu management
    public static final String PAGE_MENU_LIST = "SM1#01_MenuList";
    public static final String PAGE_MENU_EDIT = "SM1#01_MenuEdit";
    public static final String PAGE_MENU_DETAIL = "SM1#01_MenuDetail";
    // Menu management - button
    public static final String BUTTON_MENU_EDIT = "SM1#01#B02_EDIT";
    public static final String BUTTON_MENU_CREATE = "SM1#01#B01_CREATE";
    public static final String BUTTON_MENU_DELETE = "SM1#01#B03_DELETE";
    public static final String BUTTON_MENU_DETAIL = "SM1#01#B04_DETAIL";
    public static final String GROUP_BUTTON_MENU_SUBMIT = "SM1#01#B05_SUBMIT";
    public static final String BUTTON_MENU_APPROVER = "SM1#01#B07_APPROVE";
    public static final String BUTTON_MENU_REJECT = "SM1#01#B06_REJECT";
    // Document
    public static final String DOCUMENT_LIST = "AD1#S01_DocumentList";
    public static final String DOCUMENT_EDIT = "AD1#S01_DocumentEdit";
    public static final String DOCUMENT_DETAIL = "AD1#S01_DocumentDetail";
    public static final String BUTTON_DOCUMENT_CREATE = "AD1#S01#B01_CREATE";
    public static final String BUTTON_DOCUMENT_EDIT = "AD1#S01#B02_EDIT";
    public static final String BUTTON_DOCUMENT_VIEW = "AD1#S01#B04_VIEW";
    public static final String BUTTON_DOCUMENT_SUBMIT_GROUP = "AD1#S01#B05_SUBMIT";
    public static final String BUTTON_DOCUMENT_APPROVE = "AD1#S01#B07_APPROVE";
    public static final String BUTTON_DOCUMENT_REJECT = "AD1#S01#B06_REJECT";
    public static final String BUTTON_DOCUMENT_DELETE = "AD1#S01#B03_DELETE";

    // Document View Authority
    public static final String DOCUMENT_VIEW_GUEST = "AD1@R01_Guest";
    public static final String DOCUMENT_VIEW_MEMBER = "AD1@R02_Member";
    public static final String DOCUMENT_VIEW_AUTHOR = "AD1@R03_Author";

    // Faqs management
    public static final String PAGE_FAQS_LIST = "AF1#S01_FAQList";
    public static final String PAGE_FAQS_EDIT = "AF1#S01_FAQEdit";
    public static final String PAGE_FAQS_DETAIL = "AF1#S01_FAQDetail";
    // Faqs management - Button
    public static final String BUTTON_FAQS_ADD = "AF1#S01#B01_CREATE";
    public static final String BUTTON_FAQS_EDIT = "AF1#S01#B02_EDIT";
    public static final String BUTTON_FAQS_DELETE = "AF1#S01#B03_DELETE";
    public static final String BUTTON_FAQS_DETAIL = "AF1#S01#B04_DETAIL";
    public static final String BUTTON_FAQS_SUBMIT = "AF1#S01#B05_SUBMIT";
    public static final String BUTTON_FAQS_APPROVER = "AF1#S01#B07_APPROVE";
    public static final String BUTTON_FAQS_REJECT = "AF1#S01#B06_REJECT";

    // Product category management
    public static final String PAGE_PRODUCT_CATEGORY_LIST = "AP2#S01_ProductCategoryList";
    public static final String PAGE_PRODUCT_CATEGORY_EDIT = "AP2#S01_ProductCategoryEdit";
    public static final String PAGE_PRODUCT_CATEGORY_DETAIL = "AP2#S01_ProductCategoryDetail";
    // Product category management - Button
    public static final String BUTTON_PRODUCT_CATEGORY_ADD = "AP2#S01#B01_CREATE";
    public static final String BUTTON_PRODUCT_CATEGORY_EDIT = "AP2#S01#B02_EDIT";
    public static final String BUTTON_PRODUCT_CATEGORY_DELETE = "AP2#S01#B03_DELETE";
    public static final String BUTTON_PRODUCT_CATEGORY_DETAIL = "AP2#S01#B04_DETAIL";
    public static final String BUTTON_PRODUCT_CATEGORY_SUBMIT = "AP2#S01#B05_SUBMIT";
    public static final String BUTTON_PRODUCT_CATEGORY_APPROVER = "AP2#S01#B07_APPROVE";
    public static final String BUTTON_PRODUCT_CATEGORY_REJECT = "AP2#S01#B06_REJECT";

    // Product management
    public static final String PAGE_PRODUCT_LIST = "AP1#S01_ProductList";
    public static final String PAGE_PRODUCT_EDIT = "AP1#S01_ProductEdit";
    public static final String PAGE_PRODUCT_DETAIL = "AP1#S01_ProductDetail";
    // Product management - Button
    public static final String BUTTON_PRODUCT_ADD = "AP1#S01#B01_CREATE";
    public static final String BUTTON_PRODUCT_EDIT = "AP1#S01#B02_EDIT";
    public static final String BUTTON_PRODUCT_DELETE = "AP1#S01#B03_DELETE";
    public static final String BUTTON_PRODUCT_DETAIL = "AP1#S01#B04_DETAIL";
    public static final String BUTTON_PRODUCT_SUBMIT = "AP1#S01#B05_SUBMIT";
    public static final String BUTTON_PRODUCT_APPROVER = "AP1#S01#B07_APPROVE";
    public static final String BUTTON_PRODUCT_REJECT = "AP1#S01#B06_REJECT";
    
    // chat
    public static final String ROLE_CHAT = "ROLE_CHAT";
    
    // Math expression
    public static final String MATH_EXPRESSION_LIST = "AT1#S01_ExpresssionsList";
    public static final String MATH_EXPRESSION_EDIT = "AT1#S01_ExpressionsEdit";
    public static final String MATH_EXPRESSION_DETAIL = "AT1#S01_ExpressionsDetail";
    public static final String BUTTON_MATH_EXPRESSION_CREATE = "AT1#S01#B01_CREATE";
    public static final String BUTTON_MATH_EXPRESSION_EDIT = "AT1#S01#B02_EDIT";
    public static final String BUTTON_MATH_EXPRESSION_VIEW = "AT1#S01#B04_DETAIL";
    public static final String BUTTON_MATH_EXPRESSION_SUBMIT_GROUP = "AT1#S01#GB1_SUBMIT";
    public static final String BUTTON_MATH_EXPRESSION_APPROVE = "AT1#S01#B07_APPROVE";
    public static final String BUTTON_MATH_EXPRESSION_REJECT = "AT1#S01#B06_REJECT";
    public static final String BUTTON_MATH_EXPRESSION_DELETE = "AT1#S01#B03_DELETE";
    
    // Role product consulting
    public static final String ROLE_PRODUCT_CONSULTING = "PRD#S01_PRODUCT_CONSULTING";

    //Role contact email
    public static final String ROLE_CONTACT_EMAIL = "PRD#S01_CONTACT_EMAIL";

    //Role contact booking
    public static final String ROLE_CONTACT_BOOKING = "PRD#S01_CONTACT_BOOKING";
}
