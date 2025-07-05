package vn.com.unit.cms.core.constant;

import org.springframework.stereotype.Component;

@Component("cmsRoleConstant")
public class CmsRoleConstant {

    public static final String CMS_ROLE_ADMIN = "CMS#ROLE_ADMIN";
    
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_AGENT = "ROLE_AGENT";
    public static final String ROLE_CHAT = "ROLE_CHAT";
    public static final String ROLE_USER_ONLINE = "ROLE_USERONLINE";

    // ROLE FOR CHAT
    public static final String CHAT_USER_ROLE = "CHAT_USER_ROLE";
    public static final String CHAT_HISTORY_ROLE = "CHAT_HISTORY_ROLE";
    public static final String CHAT_LIST_OFFLINE = "CHAT_LIST_OFFLINE";
    public static final String CHAT_LIST_ONLINE = "CHAT_LIST_ONLINE";
    public static final String CHAT_CONFIG_OFFLINE = "CHAT_CONFIG_OFFLINE";
    public static final String CHAT_CONFIG_ONLINE = "CHAT_CONFIG_ONLINE";

    /**
     * Role for Menu/List
     */
    public static final String PAGE_LIST_BANNER = "AN1#S01_BannerList";
    public static final String PAGE_LIST_BANNER_SETTING = "AN1#S01_BannerSetting_List";
    public final static String PAGE_LIST_BRANCH = "SCREEN#CMS#PAGE_LIST_BRANCH";
    public static final String PAGE_LIST_INTRODUCTION = "INTRODUCTION_LIST";
    public static final String PAGE_LIST_INTRODUCTION_CATEGORY = "AI2#S01_IntroductionCategoryList";
    public static final String PAGE_LIST_DOCUMENT_TYPE = "CN_DOCUMENT_TYPE_LIST";
    public static final String PAGE_LIST_DOCUMENT_CATEGORY = "SCREEN#CMS#PAGE_LIST_DOCUMENT_CATEGORY";
    public static final String PAGE_LIST_DOCUMENT = "CN_DOCUMENT_LIST";
    public static final String PAGE_LIST_HOME = "AH1#S01_HomeList";
    public static final String PAGE_LIST_INTERNET_BANKING = "INTERNET_BANKING_LIST";
    public static final String PAGE_LIST_NEWS = "CN_NEWS_LIST";
    public static final String PAGE_LIST_NEWS_CATEGORY = "CN_NEWS_CATEGORY_LIST";
    public static final String PAGE_LIST_FAQ_CATEGORY = "CN_FAQ_CATEGORY_LIST";
    public static final String PAGE_LIST_INTEREST_RATE_TITLE = "CN_INTEREST_RATE_TITLE";
    public static final String PAGE_LIST_FAQS = "AF1#S01_CN_FAQList";
    public static final String PAGE_LIST_EMULATE = "SCREEN#CMS#PAGE_LIST_EMULATE";
    public static final String PAGE_LIST_EMULATE_RESULT = "SCREEN#CMS#PAGE_LIST_EMULATE_RESULT";
    public static final String PAGE_LIST_ECARD = "SCREEN#CMS#PAGE_LIST_ECARD";
    public static final String PAGE_LIST_NOTIFYS = "SCREEN#CMS#PAGE_LIST_NOTIFYS";

    /**
     * Role for Button Edit: Thêm/Xoá/Sửa/Sắp xếp lại
     */
    public static final String BUTTON_BANNER_EDIT = "BUTTON#CMS#BANNER#EDIT";
    public static final String BUTTON_BANNER_EDIT_SETTING = "BUTTON#CMS#BANNERSETTING#EDIT";
    public static final String BUTTON_NEWS_EDIT = "BUTTON#CMS#NEWS#EDIT";
    public static final String BUTTON_NEWS_CATEGORY_EDIT = "BUTTON#CMS#NEWS_CATEGORY#EDIT";
    public static final String BUTTON_FAQS_CATEGORY_EDIT = "BUTTON#CMS#FAQS_CATEGORY#EDIT";
    public static final String BUTTON_FAQS_EDIT = "BUTTON#CMS#FAQS#EDIT";
    public static final String BUTTON_EMULATE_EDIT = "BUTTON#CMS#EMULATE#EDIT";
    public static final String BUTTON_ECARD_EDIT = "BUTTON#CMS#ECARD#EDIT";
    public static final String BUTTON_BRANCH_EDIT = "BUTTON#CMS#BRANCH#EDIT";

    public static final String BUTTON_DOCUMENT_EDIT = "BUTTON#CMS#DOCUMENT#EDIT";
    public static final String BUTTON_DOCUMENT_TYPE_EDIT = "BUTTON#CMS#DOCUMENT_TYPE#EDIT";
    public static final String BUTTON_DOCUMENT_CATEGORY_EDIT = "BUTTON#CMS#DOCUMENT_CATEGORY#EDIT";
    public static final String BUTTON_HOMEPAGE_EDIT = "BUTTON#CMS#HOMEPAGE#EDIT";
    public static final String BUTTON_INTEREST_RATE_TITLE_EDIT = "BUTTON#CMS#INTEREST_RATE_TITLE#EDIT";
    public static final String BUTTON_INTEREST_RATE_VALUE_EDIT = "BUTTON#CMS#INTEREST_RATE_VALUE#EDIT";
    public static final String BUTTON_INTERNET_BANKING_EDIT = "BUTTON#CMS#INTERNET_BANKING#EDIT";
    public static final String BUTTON_INTRODUCTION_CATEGORY_EDIT = "BUTTON#CMS#INTRODUCTION_CATEGORY#EDIT";
    public static final String BUTTON_INTRODUCTION_EDIT = "BUTTON#CMS#INTRODUCTION#EDIT";
    public static final String BUTTON_PAGE_NEW_EDIT = "BUTTON#CMS#PAGENEW#EDIT";
    public static final String BUTTON_NOTIFYS_EDIT = "BUTTON#CMS#NOTIFYS#EDIT";
    public static final String BUTTON_NOTIFYS_SEND_MAIL = "BUTTON#CMS#NOTIFYS#SEND_MAIL";
    public static final String BUTTON_FAQS_IMPORT = "BUTTON#CMS#FAQS#IMPORT";
    public static final String BUTTON_DOCUMENT_IMPORT = "BUTTON#CMS#DOCUMENT#IMPORT";
    public static final String BUTTON_EMULATE_IMPORT = "BUTTON#CMS#EMULATE#IMPORT";
    public static final String BUTTON_EMULATE_RESULT_IMPORT = "BUTTON#CMS#EMULATERESULT#IMPORT";
    public static final String BUTTON_PRODUCT_EDIT = "BUTTON#CMS#PRODUCT#EDIT";
    public static final String BUTTON_ORDER_EDIT = "BUTTON#CMS#ORDER#EDIT";

    /**
     * Role for Detail
     */
    public static final String PAGE_DETAIL_BANNER = "SCREEN#CMS#PAGE_DETAIL_BANNER";
    public static final String PAGE_DETAIL_BANNER_SETTING = "SCREEN#CMS#PAGE_DETAIL_BANNERSETTING";
    public final static String PAGE_DETAIL_BRANCH = "BUTTON#CMS#BRANCH#DETAIL";
    public static final String PAGE_DETAIL_INTRODUCTION = "SCREEN#CMS#PAGE_DETAIL_INTRODUCTION";
    public static final String PAGE_DETAIL_INTRODUCTION_CATEGORY = "SCREEN#CMS#PAGE_DETAIL_INTRODUCTION_CATEGORY";
    public static final String PAGE_DETAIL_DOCUMENT_TYPE = "SCREEN#CMS#PAGE_DETAIL_DOCUMENT_TYPE";
    public static final String PAGE_DETAIL_DOCUMENT_CATEGORY = "SCREEN#CMS#PAGE_DETAIL_DOCUMENT_CATEGORY";
    public static final String PAGE_DETAIL_DOCUMENT = "SCREEN#CMS#PAGE_DETAIL_DOCUMENT";
    public static final String PAGE_DETAIL_HOME = "SCREEN#CMS#PAGE_DETAIL_HOME";
    public static final String PAGE_DETAIL_INTERNET_BANKING = "SCREEN#CMS#PAGE_DETAIL_INTERNET_BANKING";
    public static final String PAGE_DETAIL_NEWS = "SCREEN#CMS#PAGE_DETAIL_NEWS";
    public static final String PAGE_DETAIL_NEWS_CATEGORY = "SCREEN#CMS#PAGE_DETAIL_NEWS_CATEGORY";
    public static final String PAGE_DETAIL_FAQS_CATEGORY = "SCREEN#CMS#PAGE_DETAIL_FAQ_CATEGORY";
    public static final String PAGE_DETAIL_PAGE_INTEREST_RATE_TITLE = "SCREEN#CMS#PAGE_DETAIL_PAGE_INTEREST_RATE_TITLE";
    public static final String PAGE_DETAIL_FAQS = "SCREEN#CMS#PAGE_DETAIL_FAQS";
    public static final String PAGE_DETAIL_EMULATE = "SCREEN#CMS#PAGE_DETAIL_EMULATE";
    public static final String PAGE_DETAIL_ECARD = "SCREEN#CMS#PAGE_DETAIL_ECARD";
    public static final String PAGE_DETAIL_NOTIFYS = "SCREEN#CMS#PAGE_DETAIL_NOTIFYS";
    public static final String PAGE_DETAIL_PRODUCT = "SCREEN#CMS#PAGE_DETAIL_PRODUCT";
    public static final String PAGE_DETAIL_ORDER = "SCREEN#CMS#PAGE_DETAIL_ORDER";
}
