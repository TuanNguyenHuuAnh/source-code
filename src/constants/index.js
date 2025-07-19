import AES256 from 'aes-everywhere';
import complyDecreeIcon from '../img/icon/Decree.13/comply_decree_icon.svg';
import profileWarningIcon from '../img/icon/Decree.13/profile_warning_icon.svg';
import cancelRequestIcon from '../img/icon/Decree.13/cancelRequestIcon.svg';
import cancelRejectRequestIcon from '../img/icon/Decree.13/cancelRejectRequestIcon.svg';
export const COMPANY_KEY2 = "mcpappnew";
//evn
const {REACT_APP_FE_BASE_URL, REACT_APP_API_BASE_URL, REACT_APP_OAUTH2_BASE_URL, REACT_APP_MAGP_FILE_FOLDER_URL, REACT_APP_PAYMENT_GATEWAY_URL, REACT_APP_GOOGLE_CLIENT_ID, REACT_APP_FACEBOOK_CLIENT_ID, REACT_APP_RECAPTCHA_KEY, REACT_APP_GOOGLE_ANALYTICS_PROPERTY_ID, REACT_APP_GOOGLE_TAG_MANAGER_PROPERTY_ID, REACT_APP_PAGE_POLICY_PAYMENT, REACT_APP_IRACE_SECRET_KEY, REACT_APP_PAGE_IRACE_BASE, REACT_APP_ZOOM_MEETING_SDK_KEY, REACT_APP_FIRE_BASE_DYNAMIC_ENDPOINT, REACT_APP_FIRE_BASE_DYNAMIC_KEY, REACT_APP_DOMAIN_PREFIX} = window.env || process.env;

export const FE_BASE_URL = REACT_APP_FE_BASE_URL;
export const API_BASE_URL = REACT_APP_API_BASE_URL;
export const OAUTH2_BASE_URL = REACT_APP_OAUTH2_BASE_URL;
export const MAGP_FILE_FOLDER_URL = REACT_APP_MAGP_FILE_FOLDER_URL;
export const CATEGORY_IMG_FOLDER_URL = "/img/loyaltyimage/Thumbs/000/";
export const PAYMENT_GATEWAY_URL = REACT_APP_PAYMENT_GATEWAY_URL;
export const PAGE_POLICY_PAYMENT = REACT_APP_PAGE_POLICY_PAYMENT;
export const GOOGLE_CLIENT_ID = AES256.decrypt(REACT_APP_GOOGLE_CLIENT_ID, COMPANY_KEY2);
export const FACEBOOK_CLIENT_ID = AES256.decrypt(REACT_APP_FACEBOOK_CLIENT_ID, COMPANY_KEY2);
export const RECAPTCHA_KEY = AES256.decrypt(REACT_APP_RECAPTCHA_KEY, COMPANY_KEY2);
export const GOOGLE_ANALYTICS_PROPERTY_ID = AES256.decrypt(REACT_APP_GOOGLE_ANALYTICS_PROPERTY_ID, COMPANY_KEY2);
export const GOOGLE_TAG_MANAGER_PROPERTY_ID = AES256.decrypt(REACT_APP_GOOGLE_TAG_MANAGER_PROPERTY_ID, COMPANY_KEY2);
export const NEW_YEAR_START_DATE = "2022-01-15";
export const NEW_YEAR_END_DATE = "9999-02-15";
export const PAGE_ADVISORY = "https://app.stage.my-doc.com/dai-ichi/home";
export const IRACE_SECRET_KEY = AES256.decrypt(REACT_APP_IRACE_SECRET_KEY, COMPANY_KEY2);
export const PAGE_IRACE_BASE = AES256.decrypt(REACT_APP_PAGE_IRACE_BASE, COMPANY_KEY2);
export const ZOOM_MEETING_SDK_KEY = AES256.decrypt(REACT_APP_ZOOM_MEETING_SDK_KEY, COMPANY_KEY2);
export const FIRE_BASE_DYNAMIC_ENDPOINT = AES256.decrypt(REACT_APP_FIRE_BASE_DYNAMIC_ENDPOINT, COMPANY_KEY2);
export const FIRE_BASE_DYNAMIC_KEY = AES256.decrypt(REACT_APP_FIRE_BASE_DYNAMIC_KEY, COMPANY_KEY2);
export const LOGOUT_TIMEOUT = 1800000;
export const DOMAIN_PREFIX = AES256.decrypt(REACT_APP_DOMAIN_PREFIX, COMPANY_KEY2);
export const EDOCTOR_CODE = "8a1795f409094585bb5276fd76d0abca";
//end evn

export const GOOGLE_MAP_URL = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyDgyss3J5QgT0ScAPpjI7CzsT23dA_f9dU&callback=initMap'
export const HEADOFFICE_LAT = "10.794847013921462";
export const HEADOFFICE_LNG = "106.67637338625083";

export const GOOGLE_AUTH_URL = API_BASE_URL + '/oauth2/authorize/google';

export const FACEBOOK_AUTH_URL = API_BASE_URL + '/oauth2/authorize/facebook';

//export const RECAPTCHA_KEY="6LcquGQdAAAAAM1utRJ4EK7G7942J359gCQpBXG9"; //production

//export const GOOGLE_ANALYTICS_PROPERTY_ID = "UA-137256311-1"; //prod

export const DOWNLOAD_APP_ANDROID = "https://play.google.com/store/apps/details?id=com.dlvn.mcustomerportal";
export const DOWNLOAD_APP_IOS = "https://apps.apple.com/us/app/dai-ichi-connect/id1435474783";

export const DEFAULT_CODE_OFFICE_HEAD_DAI_ICHI_LIFE = '7250';
export const SHIPPING_ID_OF_DAIICHI = 'E37055F9-DB5A-44B8-8BAB-6F4DD59E1DD5';
export const SHIPPING_ACCOUNT = 'DAIICHI';
export const SHIPPING_PASSWORD = '26Oct2017_16684';

export const AUTHENTICATION = '094A1E47-1C6D-43F3-95BF-C1890048D390';
export const ACCOUNT_FLOW_STATUS = 'accountFlowStatus';
export const ACCESS_TOKEN = 'accessToken';
export const API_TOKEN = 'apiToken';
export const DEVICE_ID = 'deviceId';
export const CLIENT_ID = 'clientID';
export const CLAIM_ID = 'claim_id';
export const USER_LOGIN = 'userLogin';
export const CLIENT_PROFILE = 'clientProfile';
export const POLICY_NO = 'policyNo';
export const CELL_PHONE = 'cellPhone';
export const FULL_NAME = 'fullName';
export const USER_NAME = 'userName';
export const TRANSACTION_ID = 'transacionID';
export const USER_RULE_AGREE = 'userRuleAgree';
export const ACCOUNT_REGISTER = 'VALID_OTP_PHONE';
export const ACCOUNT_STATUS = 'accountStatus';
export const FORGOT_PASSWORD = 'forgotPassword';
export const FORGOT_PASSWORD_ONLY = 'forgotPasswordOnly';
export const FORGOT_USERNAME_ONLY = 'forgotUsernameOnly';
export const FORGOT_BOTH = 'forgotBoth';
export const FORGOT_PASSWORD_PO = 'forgotPassword_Po';
export const FORGOT_PASSWORD_POTENTIAL = 'forgotPassword_Potential';
export const FORGOT_PASSWORD_NEW = 'ForgotPasswordNew';
export const LOGIN_SOCIAL = 'loginGoogle';
export const LOGIN_FACEBOOK = 'loginFaceBook';
export const DEFAULT_PASS = '1aA*2$3456789';
export const OTP_EMAIL = '';
export const POID = 'poId';
export const DOB = 'dob';
export const CLASSPO = 'classPO';
export const POINT = 'point';
export const ORIGINAL_POINT = 'originalPoint';
export const GENDER = 'gender';
export const ADDRESS = 'address';
export const OTHER_ADDRESS = 'OtherAddress';
export const VERIFY_CELL_PHONE = 'VerifyCellPhone';
export const EMAIL = 'email';
export const USER_PERMISSION = 'userPermission';
export const TWOFA = 'TwoFA';
export const VERIFY_EMAIL = 'VerifyEmail';
export const LINK_FB = 'LinkFB';
export const LINK_GMAIL = 'LinkGmail';
export const DCID = 'dcId';
export const IRACE_ID = 'iraceId';
export const EDOCTOR_ID = 'eDoctorId';
export const DOCTOR_ID = 'doctorId';
export const AKTIVOLABS_ID = 'aktivolabsId';
export const PARTNER_ID_MAP = {'iraceId': '27', 'eDoctorId': '72', 'aktivolabsId': '73'};
export const IS_API_ON = 'apiOn';
export const IS_AUTHENTICATED = 'isAuthenticated';
export const REG_CLIENT_ID = 'regClientID';
export const REG_PHONE_NUM = 'regPhoneNum';
export const EXPIRED_MESSAGE = 'Phiên làm việc đã kết thúc do hết hạn thời gian hay vừa đăng nhập trên một thiết bị khác.';
export const CLIENT_ID_EMPTY_MESSAGE  = 'Không thể tìm thấy thông tin người dùng. Vui lòng thử lại hoặc liên hệ với bộ phận hỗ trợ.';
export const CLIENT_NOT_FOUND_MESSAGE = 'Không tìm thấy thông tin tài khoản. Vui lòng kiểm tra lại thông tin hoặc liên hệ với bộ phận hỗ trợ.';
export const USERNAME_EXIST_MESSAGE = 'Tên người dùng đã tồn tại. Vui lòng chọn tên người dùng khác.';
export const USERNAMEEXIST = 'Tài khoản đã tồn tại trong hệ thống.';
export const CLASSPOMAP = {'NOVIP': 'Thân thiết', 'GOLD': 'Vàng', 'SILVER': 'Bạc', 'DIAMOND': 'Kim Cương'};
export const CLASSPOREVERSEMAP = {'Thân thiết': 'NOVIP', 'Vàng': 'GOLD', 'Bạc': 'SILVER', 'Kim Cương': 'DIAMOND'};
export const MOBIFONE = 'Mobifone';
export const VINAPHONE = 'Vinaphone';
export const VIETTEL = 'Viettel';
export const LINK_SUB_MENU_NAME = "linkSubMenuName";
export const LINK_SUB_MENU_NAME_ID = "linkSubMenuNameId";
export const LINK_MENU_NAME = "linkMenuName";
export const LINK_MENU_NAME_ID = "linkMenuNameId";
export const LINK_CATEGORY_ID = "linkCategoryId";
export const LINK_SUB_CATEGORY_ID = "linkSubCategory";
export const CMS_HOMEPAGE = "CMS_HOMEPAGE";
export const CMS_HEALTH_NEWS = "CMS_HEALTH_NEWS";
export const GIFT_CART_FEE_REFUND = "GiftCartFeeRefund";
export const GIFT_CART_GIVE_POINT = "GiftCartGivePoint";
export const GIFT_CART_MOBILE_CARD = "GiftCartMobileCard";
export const GIFT_CART_ECOMMERCE = "GiftCartEcommerce";
export const GIFT_CART_SUPER_MARKET = "GiftCartEcommerceSuperMarket";
export const GIFT_CART_DOCTOR_CARD = "GiftCartEcommerceDoctorCard";
export const GIFT_CART_WELLNESS_CARD = "GiftCartEcommerceWellnessCard";
export const GIFT_CART_OPEN_INVESTMENT = "GiftCartOpenInvestment";
export const AVATAR_USER = "avatarUser";
export const HAVE_HC_CARD = "haveHCCard";
export const INSURED_IMAGE_LIST = "insuredImageList";
export const EXPAND_ID_LIST = "expandIdList";
export const EXPAND_ID_LAPSE_LIST = "expandIdLapseList";

export const FEE_REFUND_LOCAL_IMG = CATEGORY_IMG_FOLDER_URL + "dong_phi_hoan_tra_tam_ung.png";
export const GIVE_POINT_LOCAL_IMG = CATEGORY_IMG_FOLDER_URL + "tang_diem_nguoi_than.png";
export const MOBILE_CARD_LOCAL_IMG = "img/icon/9.1/9.1-icon-tdt.svg";
export const ECOMMERCE_LOCAL_IMG = CATEGORY_IMG_FOLDER_URL + "phieu_qua_tang_dien_tu.png";
export const SUPER_MARKET_LOCAL_IMG = CATEGORY_IMG_FOLDER_URL + "phieu_qua_tang_giay.png";
export const DOCTOR_CARD_LOCAL_IMG = CATEGORY_IMG_FOLDER_URL + "0000218_nhan-phieu-kiem-tra-suc-khoe_450.jpeg";
export const PRICE_TAG_LOCAL_IMG = "img/icon/9.2/9.2-icon-pricetag.svg";
export const WELLNESS_GIFT_IMG = CATEGORY_IMG_FOLDER_URL + "9.2-icon-demoQuaTang.svg";
export const TOTAL_CART_POINT = "TotalCardPoint";
export const UPDATE_POINT = "updatePoint";
export const COMPANY_KEY = "mcp_dlvn";
export const COMPANY_KEY3 = "rDWTW0nrhZKudKbRGS8Oh23K4gK3cpVH";
export const IS_POPUP_VIEWED = "isPopupViewed";
export const POL_LIST_CLIENT = "polListClient";
export const POL_LI_LISTCLAIM_ND13_CLIENT = "polLifeAssuredListClient";
export const POL_LIST_CLIENT4CLAIM = "polListClient4Claim";
export const POL_LIST_INFORCE_CLIENT = "polListInforceClient";
export const POL_LIST_IL_CLIENT = "polListILClient";
export const POL_LIST_PAYMENT_CLIENT = "polListPaymentClient";
export const POL_LIST_LAPSE = "polListLapse";
export const POL_TOTAL_RECORDS = "polTotalRecords";
export const POL_LIST_EPOLICY = "polListEpolicy";
export const POL_LIST_EPOLICY_OFFSET = "polListEpolicyOffset";
export const TEMP_USER_LOGIN = "tempUserLogin";
export const USER_DEVICE_TOKEN = "userDeviceToken";
export const QUANTRICS_SURVERY_CLS = "QSIWebResponsive";
export const LOGIN_TIME = "loginTime";
export const POL_EXPIRE_DATE = "polExpireDate";
export const POL_LI_NAME = "policyLIName";
export const POL_POLICY_STATUS = "policyStatus";
export const POL_CLASS_CD = "policyClassCD";
export const LOG_OUTED = "logOuted";
export const POL_PRODUCT_NAME = "policyProductName";
export const OTP_INCORRECT = "Mã OTP không đúng, Quý khách vui lòng nhập lại.";
export const OTP_EXPIRED = "Mã OTP đã hết hạn, Quý khách vui lòng yêu cầu mã mới.";
export const OTP_SYSTEM_ERROR = "Hệ thống đang bận, vui lòng thử lại sau.";
export const OS = "";
export const LOGINED = "logined";
export const ZALO_LOGINED = "zaloLogined";
export const ENCRYPTED_DATA = "encryptedData";
export const DEADTH_CLAIM_MSG = "deadthClaimMsg";
export const E_SINGNATURE_MAP = {
    'ClientID': 'Mã KH:',
    'PolicyNo': 'Số HĐ:',
    'ClientName': 'Bên mua bảo hiểm:',
    'IssueBy': 'Thư được phát hành bởi:',
    'SignedDate': 'Ngày ký:'
};
export const E_SINGNATURE_CLIENT_ID = "eSingatureClientID";
export const E_SINGNATURE_POLICY_NO = "eSingaturePolicyNO";
export const REQUEST_GET_INFO_PSPROCESS_RESPONSE = "RequestGetInfoPSProcessResponse";
export const SCREENS = {
    UPDATE_POLICY_INFO: '/update-policy-info',
    UPDATE_CONTACT_INFO: '/update-contact-info',
    PAYMENT_CONTRACT: '/payment-contract',
    REINSTATEMENT: '/reinstatement',
    HOME: '/home',
    CREATE_CLAIM: '/myclaim',
    HEALTH: '/song-vui-khoe'
}
export const PREVIOUS_SCREENS = "previousScreeen";
//Fund
export const FUND_STATE = {
    NONE: 0,
    CATEGORY_INFO: 1,
    CHOOSE_POLICY: 2,
    UPDATE_INFO: 3,
    VERIFICATION: 4,
    SDK:5
};

export const SUB_STATE = {
    INIT: 0,
    AGREE: 1
};

export const PAYMENT_SUB_STATE = {
    INIT: 0,
    CHOOSE_METHOD: 1,
    FILL_DETAIL: 2,
    UPLOAD: 3,
};

export const FUND_TYPE = {
    GROWTH: "Quỹ tăng trưởng",
    DEVELOPING: "Quỹ phát triển",
    SECURE: "Quỹ bảo toàn",
    LEADER: "Quỹ dẫn đầu",
    DYNAMIC_FINANCIAL: "Quỹ tài chính năng động"
};

export const FUND_FROM_LIMIT = 1050000;
export const FUND_TO_LIMIT = 1000000;
export const SUBMIT_IN_24 = "submitIn24";
export const SUBMIT_IN_24_RIN = "submitIn24Rin";
export const RIN_DRAFT = "rinDraft";

//eClaim
export const CLAIM_TYPE = {
    DEATH: 'Death',
    HEALTH_CARE: 'Healthcare',
    ILLNESS: 'Illness',
    ACCIDENT: 'Accident',
    HS: 'HS',
    TPD: 'TPD'
}
export const CLAIMTYPEMAP = {'Healthcare': 'Chăm sóc sức khỏe', 'Death': 'Tử vong', 'Illness': 'Bệnh hiểm nghèo', 'Accident': 'Tai nạn', 'HS': 'Hỗ trợ viện phí', 'TPD': 'Thương tật toàn bộ vĩnh viễn'};
export const CLAIM_DOC_ID = {
    DEATH: 'ClaimDeathTPDCertificate',
    ACCIDENT: 'ClaimAccidentDocs'
}
export const WEB_BROWSER_VERSION = "Web";
export const CLAIM_SAVE_LOCAL = "claimSaveLocal";
export const OTHER_HOSPITAL = "CSYT Khác";
export const OTHER_ICD = "KQCĐ Khác";
export const CLAIM_GUID = "https://kh.dai-ichi-life.com.vn/utilities/claim-guide";
export const LIITEM = "liItem";
// health cms content
export const CMS_TYPE = "/get-type-by-lang";
export const CMS_CATEGORY = "/category/";
export const CMS_NEWS = "/news";
export const CMS_TAGS = "/tags";
export const CMS_LIST = "/list/";
export const CMS_RELATIVE = "/relative/";
export const API_NEWS_LIST_ALL_HOTS = "/list-all-hots/";
export const API_NEWS_LIST_ALL_HOTS_BY = "/list-all-hots-by/";
export const API_NEWS_LIST_ALL_HOTS_BY_CATEGORY = "/list-all-hots-by-category/";
export const CMS_DETAIL_BY_LINK = "/detail-by-link";
export const CMS_DETAIL_BY_LINK_ONLY = "/detail-by-link-only";
export const CMS_LIST_HOME = "/list-home";
export const CMS_LIST_ALL_TAGS = "/list-all-tags";
export const API_NEWS_LIST_BY_KEYWORK = "/list-by-keywork/";
export const API_NEWS_LIST_BY_TAG = "/list-by-tag/";
export const API_GET_NETWORK_BY_TYPE = "/get-networks-by-type";
export const CMS_LIST_DIST_META_KEYWORD = "/list-dist-meta-keyword";

export const CMS_PAGE_DEFAULT = 1;
export const CMS_HOME_PAGE_SIZE = 4;
export const CMS_HW_PAGE_SIZE = 8;
export const CMS_HW_PAGE_TIP_SIZE = 16;
export const CMS_HW_SEARCH_SIZE = 6;

export const CMS_CATEGORY_LIST_DATA = "cmsCategoryListData";
export const CMS_SUB_CATEGORY_MAP = "cmsSubCategoryMap";
export const CMS_TAG_LIST_DATA = "cmsTagListData";

export const CMS_CATEGORY_CODE = "cmsCategoryCode";
export const CMS_SUB_CATEGORY_ID = "cmsSubCategoryId";

export const CMS_IMAGE_URL = "cmsImageUrl";
export const CMS_KEYWORK = "cmsKeyWork";
export const CMS_LIST_DIST_META_KEYWORD_DATA = "cmsListDistMetaKeywordData";
export const CMS_BASE_URL_NO_LOAD = "dai-ichi-life.com.vn/admin/personal/promotion/download?";
export const CMS_BASE_URL_REPLACE = "dai-ichi-life.com.vn/api/api/v1/app/downloadFile?";
export const CMS_CATEGORY_CODE_SET = Object.freeze({
    MEDICINE: 'MEDICINE',
    FOODS: 'FOODS',
    HABIT: 'HABIT',
    INVESTMENT: 'INVESTMENT',
    NEWS: 'NEWS'
});

export const RELOAD_ARTICLE = "reloadArticle";
export const CMS_TAG_IMAGES = {
    'giam-can': 'biquyetgiamcan_1664419982719.png',
    'an-uong-lanh-manh': 'anuong1_1670572957810.png',
    'song-vui-khoe': 'duytritrangthaivuisong_1664502046214.jpg',
    'tap-the-duc': 'Untitleddesign_1664354133828.png',
    'phong-ngua-ung-thu': 'Banner38_1664505674319.png',
    'chay-bo': 'chayben1_1678679292642.png',
    'giac-ngu': 'MicrosoftTeamsimage290_1664341829650.png',
    'ba-bau': 'dinhduongchobabau_1677491888234.png',
    'dau-tu-online': 'nhungkenhdaututiennhanroi_1677125646345.png',
    'xay-dung-su-nghiep': 'Untitleddesign42_1672305773268.png',
    'cac-loai-vitamin': 'Banner32_1664426621288.png',
    'tai-chinh-ca-nhan': 'taichinhcanhan_1664339727670.jpg',
    'thai-ky': 'dauhieumangthaithumnail1_1676860049291.png',
    'suc-khoe-tinh-than': 'hungthulamviec_1664448097939.jpg',
    'thuc-pham-tot': 'thucphamgiaucanxithumnail_1676864590439.png',
    'nuoi-con': 'canbangcuocsong_1664504215578.jpg',
    'mua-bao-hiem': 'Untitleddesign1_1670560917827.jpg',
    'nguoi-cao-tuoi': 'Banner19_1664333049421.png',
    'ung-thu': 'ungthuvomhong_1664418205827.jpg',
    'phu-khoa': 'benhphukhoa2_1676278940616.png',
    'covid-19': 'covidthumnail1_1682496893022.png',
    'hoi-thao-suc-khoe': 'hoithaoHuethumnail_1684317819834.png',
    'benh-ly-thuong-gap': 'Untitleddesign151_1670397309194.png',
    'cung-duong-yeu-thuong': 'https://cungduongyeuthuong.dai-ichi-life.com.vn/23/seo/dai-ichi-life-cdyt2023.jpg'
};
// from app
export const FROM_HOME = "fromHome";
export const CMS_APP_CATEGORY_ICON_MAP = Object.freeze({
    'MEDICINE': 'ico-app-header-benh-va-thuoc.svg',
    'FOODS': 'ico-app-header-thuc-pham-va-dinh-duong.svg',
    'HABIT': 'ico-app-head-thoi-quen-song-khoe.svg',
    'INVESTMENT': 'ico-app-head-bao-hiem-va-tiet-kiem.svg',
    'NEWS': 'ico-app-head-tin-tuc-va-su-kien.svg'
});
export const CMS_APP_ALIASLINK_CATEGORY_CODE_MAP = Object.freeze({
    'benh-va-thuoc': 'MEDICINE',
    'thuc-pham-va-dinh-duong': 'FOODS',
    'thoi-quen-song-khoe': 'HABIT',
    'bao-hiem-va-dau-tu-tich-luy': 'INVESTMENT',
    'tin-tuc-va-su-kien': 'NEWS'
});
export const CMS_APP_ALIASLINK_CATEGORY_NAME_MAP = Object.freeze({
    'benh-va-thuoc': 'Bệnh và Thuốc',
    'thuc-pham-va-dinh-duong': 'Thực phẩm và Dinh dưỡng',
    'thoi-quen-song-khoe': 'Thói quen sống khỏe',
    'bao-hiem-va-dau-tu-tich-luy': 'Bảo hiểm và Đầu tư tích lũy',
    'tin-tuc-va-su-kien': 'Tin tức và Sự kiện'
});

export const CMS_APP_ALIASLINK_SUB_CATEGORY_NAME_MAP = Object.freeze({
    'benh-thuong-gap': 'Bệnh thường gặp',
    'thuoc-thuc-pham-chuc-nang': 'Thuốc & Thực phẩm chức năng',
    'thuc-pham': 'Thực phẩm',
    'dinh-duong': 'Dinh dưỡng',
    'suc-khoe-the-chat': 'Sức khỏe thể chất',
    'suc-khoe-tinh-than': 'Sức khỏe tinh thần',
    'bao-hiem': 'Bảo hiểm',
    'dau-tu-tich-luy': 'Đầu tư & Tích lũy',
    'tin-tuc': 'Tin tức',
    'su-kien': 'Sự kiện',
    'ban-tin-khach-hang': 'Bản tin khách hàng'
});

export const FROM_APP = "fromApp";
export const FROM_SCREEN_APP = "fromScreenApp";
export const IS_MOBILE = "isMobile";
export const TYPE_PWA = "typePWA";

export const MENU_NAME = "menuName";
export const SUB_MENU_NAME = "subMenuName";
export const HISTORY_LOCATION_PATH = "previousLocationPath";
export const SHOW_IRACE_Q = "showIraceQ";

export const PAGE_ATSH = "https://www.dai-ichi-life.com.vn/ke-hoach-giao-duc/an-tam-song-hanh/339/2671/";
export const PAGE_ATHTTD = "https://dai-ichi-life.com.vn/vi-VN/san-pham-chinh/an-tam-hung-thinh-toan-dien/354/2193/";
export const PAGE_APHTTD = "https://www.dai-ichi-life.com.vn/vi-VN/ke-hoach-tiet-kiem/an-phuc-hung-thinh-toan-dien/341/1492/";
export const PAGE_ANHT = "https://dai-ichi-life.com.vn/vi-VN/ke-hoach-huu-tri/an-nhan-huu-tri/340/1513/";
export const PAGE_ATDT = "https://dai-ichi-life.com.vn/vi-vn/san-pham-chinh/an-thinh-dau-tu/354/1745/";
export const PAGE_DGAP = "https://www.dai-ichi-life.com.vn/ke-hoach-bao-ve/dai-gia-an-phuc/343/2067/";
export const PAGE_RUN = "https://cungduongyeuthuong.dai-ichi-life.com.vn/?utm_source=dai-ichi-connect&utm_medium=homepage-icon&utm_campaign=tong-quan-01102022";
export const PAGE_RUN_HW = "https://cungduongyeuthuong.dai-ichi-life.com.vn/?utm_source=dai-ichi-connect&utm_medium=hw-icon&utm_campaign=tong-quan-01102022";
export const PAGE_RUN_MENU = "https://cungduongyeuthuong.dai-ichi-life.com.vn/?utm_source=dai-ichi-connect&utm_medium=menu&utm_campaign=tong-quan-01102022";
export const PAGE_KCare = "https://daiichi-life-uat.quote.hk/product/KCP104?lang=vi";
export const PAGE_ICHIGO = "https://daiichi-life-uat.quote.hk/product/PAD101?lang=vi";

export const PAGE_RUN_NEW = "https://cungduongyeuthuong.dai-ichi-life.com.vn/?utm_source=dai-ichi-connect&utm_medium=homepage-icon&utm_campaign=tong-quan-01102022";

export const PAGE_REGULATIONS_PAYMENT = PAGE_POLICY_PAYMENT + '/Quy-dinh-bao-ve-va-xu-ly-du-lieu.pdf';

export const MEETING_SHORT_LINK = "meetingShortLink";

export const PO_CONFIRMING_DECREE_13 = 'confirming_decree_13';

export const MAPPING_ALT_IMG_BANNER = [
    {
        imageUuid: "b3fc7147-fbd0-442c-a2aa-7442cb15081e",
        alt: "Banner"
    },
    {
        imageUuid: "c0b978fb-3636-4aaa-98cc-46495cdbb722",
        alt: "Banner - Cung Đường Yêu Thương 2023"
    },
    {
        imageUuid: "7ae43987-2b74-4689-a4f2-4688e2196581",
        alt: "Banner"
    },
    {
        imageUuid: "4ebd265a-3212-4938-aa8c-cce2ad355bf7",
        alt: "Banner - Itrust"
    },
    {
        imageUuid: "5faba4d5-f445-4771-be1c-7249d5b496c8",
        alt: "Banner"
    },
    {
        imageUuid: "c54870d0-5860-4f86-a75f-0e2aa18aa2f5",
        alt: "An Tâm Song Hành"
    },
    {
        imageUuid: "ff91cf14-2020-4faf-85ed-b21192e35912",
        alt: "An Nhàn Hưu Trí"
    },
    {
        imageUuid: "8cb6a5d1-f89a-49d4-91ea-0d45a8716c2f",
        alt: "An Thịnh Đầu Tư"
    },
    {
        imageUuid: "a90c3d6b-a0c0-4308-a0e6-428154563072",
        alt: "Đại Gia An Phúc"
    }
];
export const SUB_CATEGORY_ICON = [
    {
        subCategoryName: 'Thử thách sống khỏe',
        subCategoryIcon: 'icon_healthChallenge.svg',
    },
    {
        subCategoryName: 'Bí quyết sống vui khỏe',
        subCategoryIcon: 'icon_secretHappy.svg',
    },
    {
        subCategoryName: 'Tư vấn sức khỏe',
        subCategoryIcon: 'icon_healthConsulting.svg',
    },
    {
        subCategoryName: 'Chỉ số sức khỏe',
        subCategoryIcon: 'icon_healthIndex.svg',
    },
    {
        subCategoryName: 'Điểm thưởng',
        subCategoryIcon: 'icon_point.svg',
    },
    {
        subCategoryName: 'Vận động',
        subCategoryIcon: 'icon_move.svg',
    },
    {
        subCategoryName: 'Đi/Chạy bộ vì cộng đồng',
        subCategoryIcon: 'icon_runCommunity.svg',
    },
    {
        subCategoryName: 'Yêu cầu bồi thường',
        subCategoryIcon: 'icon_Claim.svg',
    },
    {
        subCategoryName: 'Giải quyết yêu cầu',
        subCategoryIcon: 'Icon_Information.svg',
    },
];


export const MY_HEALTH = "/song-vui-khoe/bi-quyet/tin-tuc-va-su-kien/tin-tuc/song-vui-song-khoe-toan-dien-cung-dai-ichi-connect";
export const PageScreen = Object.freeze({
    HOME_PAGE: 'HomePage',
    POLICY_PAGE: 'PolicyPage',
    NETWORK_PAGE: 'NetworkPage',
    // POLICY_DETAIL_PAGE: 'PolicyDetailPage',
    LOYALTY_PAGE: 'LoyaltyPage',
    TRANS_HISTORY_PAGE: 'TransHistoryPage',
    // NOTIFICATIONS_PAGE: 'NotificationsPage',
    PAYMENT_PAGE: 'PaymentPage',
    FAMILY_PAYMENT_PAGE: 'FamilyPaymentPage',
    CLICK_PAYMENT_INS_FEE: '_Click_PaymentInsFee',
    // SETTING_ACCOUNT_PAGE: 'SettingAccountPage',
    SUBMIT_FORM_PAGE: 'SubmitFormPage',
    CUSTOMER_NEED_KNOW: 'CustomerNeedToKnow',
    HEALTH_WELLBEING: 'HealthWellbeing',
    HEALTH_WELLBEING_ARTICLE: 'HealthWellbeingArticle',
    HEALTH_WELLBEING_CHALLENGES: 'HealthWellbeingChallenges',
    HEALTH_WELLBEING_CHALLENGES_LIST: 'HealthWellbeingChallengesList',
    // SCAN_RECEIPT: 'ScanReceipt',
    // RECEIPT_DETAIL: 'ReceiptDetail',
    LIST_INSURED: 'ListInsured',
    LIST_POLICY_OWNER: 'ListPolicyOwner',
    // LIST_INSURED_DETAIL: 'ListInsuredDetail',
    DOCUMENT: 'Document',

    // LOYALTY_CATEGORY: 'LoyaltyCategory',
    // LOYALTY_SHIP_HOME: 'LoyaltyShipHome',
    // LOYALTY_POINT_MECHANISM: 'LoyaltyPointMechanism',
    // LOYALTY_SHIP_ADD: 'LoyaltyShipAdd',
    LOYALTY_CARD: 'LoyaltyCard',
    LOYALTY_GUIDE_GIFTS: 'LoyaltyGuideGifts',
    LOYALTY_GIFTS_CARD: 'LoyaltyGiftsCard',

    // PAYMENT_FEE_DETAIL: 'PaymentFeeDetail',
    // PAYMENT_FEE_BANK_OPT: 'PaymentFeeBankOpt',
    PAYMENT_FEE_HISTORY: 'PaymentFeeHistory',
    HEALTH_CARD: 'HealthCard',

    POL_TRANS_HOME: 'PolTransHome',
    POL_TRANS_EMAIL: 'PolTransEmail',
    POL_TRANS_EMAIL_CONTACT_INFO: 'PolTransEmailContactInfo',
    POL_TRANS_EMAIL_CONTACT_SUBMIT: 'PolTransEmailContactSubmit',
    POL_TRANS_ADDRESS: 'PolTransAddress',
    POL_TRANS_ADDRESS_CONTACT_INFO: 'PolTransAddressContactInfo',
    POL_TRANS_ADDRESS_CONTACT_SUBMIT: 'PolTransAddressContactSubmit',
    POL_TRANS_CHANGE_RATE: 'PolTransChangeRate',
    POL_TRANS_CHANGE_RATE_CHOOSE_CONTRACT: 'PolTransChangeRateChooseContract',
    POL_TRANS_CHANGE_RATE_CONTACT: 'PolTransChangeRateContact',
    POL_TRANS_CHANGE_RATE_REVIEW: 'PolTransChangeRateReview',
    POL_TRANS_CHANGE_RATE_SUBMIT: 'PolTransChangeRateSubmit',
    POL_TRANS_REINST: 'PolTransReInst',
    POL_TRANS_CHANGE_PAYMODE_CHOOSE_CONTRACT: 'PolTransChangePayModeChooseContract',
    POL_TRANS_DECREASE_SA_CHOOSE_CONTRACT: 'PolTransDecreaseSAChooseContract',
    POL_TRANS_CHANGE_PERSONAL_INFO: 'ChangePersonalInfo',
    POL_TRANS_CHANGE_PAYMENT: 'ChangePayment',
    POL_TRANS_CHANGE_PAYMODE: 'ChangePaymode',
    POL_TRANS_CHANGE_SUNDRY_AMOUNT: 'ChangeSundryAmount',
    POL_TRANS_CHANGE_DECREASE_SA: 'DecreaseSA',
    CHOICEPOL: 'ChoicePol',
    KEYINDATA: 'KeyInData',
    REVIEW: 'Review',
    SUBMITCLICK: 'SubmitClick',
    CLAIM_LIST_INSURED: 'ClaimListInsured',
    CLAIM_CHOICE_BENEFIT: 'ClaimChoiceBenefit',
    CLAIM_KEY_IN_TREATMENT: 'ClaimKeyInTreatment',
    CLAIM_PAYMENT: 'ClaimPayment',
    CLAIM_CONTACT: 'ClaimContact',
    CLAIM_ATTACH_DOCUMENT: 'ClaimAttDocPage',
    CLAIM_REVIEW: 'ClaimReview',
    CLAIM_SUBMISSION: 'ClaimSubmitSuccessfully',
    CLAIM_REQUEST_PAGE: 'ClaimRequestPage',
    // CLAIM_LIST_PAGE: 'ClaimListPage',
    CLAIM_LIST_HOLD_PAGE: 'ClaimListHoldPage',
    // CLAIM_HOLD_PAGE: 'ClaimHoldPage',
    CLAIM_HISTORY: 'ClaimHistory',

    E_POLICY_POL_LIST: 'EPolicyPolList',
    E_POLICY_POL_SDK: 'EPolicyPolSDK',
    E_POLICY_POL_MANAGEMENT: 'EPolicyPolManagement',
    E_POLICY_POL_VIEW: 'EPolicyPolView',
    PREMIUM_LETTER_SDK: 'PremiumLetterSDK',
    PREMIUM_LETTER_LIST_SDK: 'PremiumLetterListSDK',

    // E_POLICY_INDEX_PAGE: 'EPolicyIndexPage',
    // E_POLICY_DETAIL: 'EPolicyDetail',
    // E_POLICY_APPEND: 'EPolicyAppend',
    // POLICY_LIST_RECEIPT: 'PolicyListReceipt',
    // MAP_OFFICE: 'MapOffice',
    GUIDE_PAY_FEE: 'GuidePayFee',
    GUIDE_CLAIM: 'GuideClaim',
    GUIDE_PARTIC_INSURANCE: 'GuideParticInsurance',
    GUIDE_CONTRACT_TRANS: 'GuideContractTrans',
    RATE_FUND_UNIT: 'RateFundUnit',
    OCCUPATION: 'Occupation',
    // LIST_FORM: 'ListForm',
    FAQ: 'FAQ',
    FEEDBACK: 'Feedback',
    USER_MANUAL: 'UserManual',
    TERM_OF_USE: 'TermOfUse',
    ABOUT_DLVN: 'AboutDLVN',
    SDK_ND13: 'SDKND13',
    SETTINGACCOUNTPAGE: 'SettingAccountPage'
});
export const BACK_PATH = "backPath";
export const AKTIVO_ACCESS_TOKEN = "aktivoAccessToken";
export const AKTIVO_MEMBER_ID = "aktivoMemberId";
export const DATA_SECTION = [
    {index: '2.1', label: 'Tab Phí bảo hiểm', path: '/mypolicyinfo'},
    {index: '2.2', label: 'Sản phẩm', path: '/mypolicyinfo'},
    {index: '2.3', label: 'Giá trị hợp đồng', path: '/mypolicyinfo'},
    {index: '2.4', label: 'Người thụ hưởng', path: '/mypolicyinfo'},
    {index: '2.5', label: 'Thông tin TVTC', path: '/mypolicyinfo'},
    {index: '34.2', label: 'Đóng phí', path: '/utilities/policy-payment-la'}
];

export const DATA_DECREE = {
    PROFILE_DC_WARNING: {
        index: 1,
        title: 'Yêu cầu của Quý khách không thể được thực hiện tiếp trên Dai-ichi Connect do Quý khách không đồng ý cho DLVN xử lý DLCN. Quý khách vui lòng thực hiện lại hoặc hủy Yêu cầu trực tuyến này để lập Phiếu yêu cầu bằng giấy và nộp tại Văn phòng/Tổng Đại lý DLVN gần nhất.',
        msg: 'Trường hợp cần trao đổi thêm, Quý khách vui lòng liên hệ Tổng đài (028) 38 100 888 hoặc thư điện tử',
        link: '',
        image: cancelRejectRequestIcon,
    },
    PROFILE_WARNING: {
        index: 1,
        title: 'Nhằm tuân thủ Nghị định 13/2023/NĐ-CP ngày 17/04/2023 về bảo vệ Dữ liệu cá nhân(DLCN), Chủ thể dữ liệu cần phải đồng ý cho phép Dai-ichi Life Việt Nam xử lý DLCN.',
        msg: 'Trường hợp Quý khách không đồng ý, Bên mua bảo hiểm sẽ không thể hoàn tất yêu cầu trực tuyến. Thay vào đó, Bên mua bảo hiểm sẽ lập yêu cầu bằng giấy với xác nhận đồng ý của Quý khách để nộp tại Văn phòng/Tổng Đại lý DLVN gần nhất',
        link: '',
        image: cancelRejectRequestIcon,
    },
    COMPLY_WITH_DECREE: {
        index: 2,
        title: 'Nhằm tuân thủ Nghị định số 13/2023/NĐ-CP ngày 17/04/2023 về bảo vệ dữ liệu cá nhân (DLCN), Quý khách có thể được yêu cầu cung cấp số điện thoại/hộp thư điện tử của Chủ thể dữ liệu (NĐBH và/hoặc những người khác liên quan đến giao dịch) để (những) người này xác nhận trực tuyến về việc đồng ý cung cấp dữ liệu cá nhân. Chi tiết sẽ được hướng dẫn tại các màn hình tương ứng tiếp theo. <br/> Lưu ý rằng, đồng thời với việc lập Yêu cầu Giải quyết quyền lợi bảo hiểm, sự đồng ý của Chủ thể dữ liệu (Bên mua bảo hiểm, Người được bảo hiểm, Cha/Mẹ/Người giám hộ của Người được bảo hiểm dưới 18 tuổi) cho phép DLVN xử lý DLCN sẽ có hiệu lực áp dụng ngay cả trường hợp Yêu cầu Giải quyết quyền lợi bảo hiểm này bị hủy bỏ hoặc bị từ chối.',
        msg: 'Quý khách xem Quy định về Bảo vệ và Xử lý dữ liệu Cá nhân',
        urlPath: 'https://dai-ichi-life.com.vn/dich-vu-19',
        image: complyDecreeIcon,
    },
    CANCEL_REQUEST_DECREE: {
        index: 3,
        title: 'Yêu cầu của Quý khách sẽ bị hủy',
        msg: 'Quý khách cần tạo lại yêu cầu mới hoặc lập Phiếu yêu cầu bằng giấy và nộp tại Văn phòng/Tổng Đại lý DLVN gần nhất.',
        urlPath: '',
        image: cancelRejectRequestIcon,
    },
    INSURANCE_REQUEST_CANCEL_CONFIRM_DECREE: {
        index: 4,
        title: 'Yêu cầu của Quý khách sẽ bị hủy',
        msg: 'Quý khách cần tạo lại yêu cầu mới hoặc lập Phiếu yêu cầu bằng giấy và nộp tại Văn phòng/Tổng Đại lý DLVN gần nhất.',
        urlPath: '',
        image: cancelRejectRequestIcon,
    },

};

export const ConsentStatus = {
    NULL_OR_EMPTY: 'NULL_OR_EMPTY',
    WAIT_CONFIRM: 'WaitConfirm',
    DECLINED: 'Declined',
    AGREED: 'Agreed',
    EXPIRED: 'Expired'
};

export const TranslatedStatus = {
    NULL_OR_EMPTY: 'Chờ xác nhận',
    WAIT_CONFIRM: 'Chờ xác nhận',
    DECLINED: 'Từ chối',
    AGREED: 'Xác nhận',
    EXPIRED: 'Hết hạn'
};
export const ICALLBACK = 'icallback';
export const RELATION_SHIP_MAPPING = {'MOTHR': 'Mẹ', 'FATHR': 'Cha', 'Parent': 'Cha/Mẹ/Người giám hộ', 'GRDCS': 'Người giám hộ hợp pháp', 'PO': 'BMBH'};
export const TRACKINGID = 'trackingID';
export const RELOAD_BELL = 'reloadBell';
export const ONLY_PAYMENT = 'onlyPayment';

export const NUM_OF_RETRY = 3;
export const NUM_OF_MANUAL_RETRY = 3;

export const UPDATE_POLICY_INFO_SAVE_LOCAL="updatePolicyInfoSaveLocal";
export const DEVICE_KEY = 'DeviceKey';
export const SUBMISSION_TYPE_MAPPING = {'CLAIM': 'Giải quyết quyền lợi bảo hiểm', 'RIN': 'Khôi phục hiệu lực hợp đồng', 'PRM': 'Thay đổi định kỳ đóng phí', 'CSA': 'Thay đổi chi tiết sản phẩm bảo hiểm', 'Loan':'Tạm ứng từ giá trị hoàn lại', 'Maturity': 'Nhận quyền lợi bảo hiểm đáo hạn', 'Surrender': 'Chấm dứt hợp đồng bảo hiểm trước hạn', 'Premium Refund':'Nhận phí bảo hiểm đóng dư','Coupon':'Nhận quyền lợi tiền mặt định kỳ', 'Dividend':'Nhận lãi chia tích lũy', 'Partial Withdrawal': 'Rút một phần giá trị tài khoản/giá trị quỹ', 'EML': 'Thay đổi email', 'ADR': 'Thay đổi địa chỉ', 'FAP': 'Thay đổi tỷ lệ đầu tư', 'FPM': 'Thay đổi tỷ lệ đầu tư', 'CCI': 'Điều chỉnh thông tin cá nhân', 'SundryAmountS': 'Thay đổi phí dự tính định kỳ'}
export const CHANGE_PAY_MODE_SAVE_LOCAL="changePayModeSaveLocal";
export const CLIENT_CLASS="clientClass";
export const NOTE_MAPPING = {'RIN': 'VALID_OTP_REINSTATEMENT', 'PRM': 'PRMProcessConfirm', 'CSA': 'CSAProcessConfirm', 'Loan': 'LoanProcessConfirm', 'Maturity': 'MaturityProcessConfirm', 'Surrender': 'SurrenderProcessConfirm', 'Premium Refund': 'Premium RefundProcessConfirm', 'Coupon': 'CouponProcessConfirm', 'Dividend': 'DividendProcessConfirm', 'Partial Withdrawal': 'Partial WithdrawalProcessConfirm', 'EML': 'VALID_OTP_CHANGE_EMAIL', 'ADR': 'VALID_OTP_CHANGE_ADDRESS', 'FAP': 'VALID_OTP_CHANGE_RATE_FUND', 'FPM': 'VALID_OTP_CHANGE_RATE_FUND'}
export const CONFIRM_ACTION_MAPPING = {'RIN': 'ReInstatementConfirm', 'PRM': 'SinglePSProcessConfirm', 'EML': 'PSConfirm', 'ADR': 'PSConfirm', 'FPM': 'PSConfirm', 'FAP': 'PSConfirm'}

export const ND_13 = Object.freeze({
    NONE: 0,
    ND13_INFO_CONFIRMATION: 9,
    ND13_INFO_PO_CONTACT_INFO_OVER_18: 10,
    ND13_INFO_FOLLOW_CONFIRMATION: 11,
})
export const CATEGORY_NAME_MAPPING = {'Loan': 'TẠM ỨNG TỪ GIÁ TRỊ HOÀN LẠI', 'Maturity': 'NHẬN QUYỀN LỢI BẢO HIỂM ĐÁO HẠN', 'Surrender': 'CHẤM DỨT HỢP ĐỒNG BẢO HIỂM TRƯỚC HẠN', 'Premium Refund':'NHẬN PHÍ BẢO HIỂM ĐÓNG DƯ', 'Coupon':'NHẬN QUYỀN LỢI TIỀN MẶT ĐỊNH KỲ', 'Dividend':'NHẬN LÃI CHIA TÍCH LŨY', 'Partial Withdrawal': 'RÚT MỘT PHẦN GIÁ TRỊ TÀI KHOẢN'}
export const CATEGORY_NAME_MAPPING_REVIEW = {'Loan': 'TẠM ỨNG TỪ GIÁ TRỊ HOÀN LẠI', 'Maturity': 'NHẬN QUYỀN LỢI BẢO HIỂM ĐÁO HẠN', 'Surrender': 'CHI TIẾT YÊU CẦU', 'Premium Refund':'NHẬN PHÍ BẢO HIỂM ĐÓNG DƯ', 'Coupon':'NHẬN QUYỀN LỢI TIỀN MẶT ĐỊNH KỲ', 'Dividend':'NHẬN LÃI CHIA TÍCH LŨY', 'Partial Withdrawal': 'RÚT MỘT PHẦN GIÁ TRỊ TÀI KHOẢN'}
export const TEMP_OF_USE = '/terms-of-use';
export const PRIVACY_POLICY = '/privacy-policy';
export const OTP_DISPLAYED = 'OTP_Displayed';
export const OTP_SUBMITTED = 'OTP_Submitted';

export const FROM_SDK = 'fromSDK';
export const REVIEW_LINK = 'reviewLink';
export const SYSTEM_DCONNECT = "DConnect";
export const SIGNATURE = "signature";

export const CMS_CATEGORY_LIST_DATA_JSON = [
    {
        "code": "MEDICINE",
        "enabled": 0,
        "label": "Bệnh và Thuốc",
        "linkAlias": "benh-va-thuoc",
        "sort": 0
    },
    {
        "code": "FOODS",
        "enabled": 0,
        "label": "Thực phẩm và Dinh dưỡng",
        "linkAlias": "thuc-pham-va-dinh-duong",
        "sort": 0
    },
    {
        "code": "HABIT",
        "enabled": 0,
        "label": "Thói quen sống khỏe",
        "linkAlias": "thoi-quen-song-khoe",
        "sort": 0
    },
    {
        "code": "INVESTMENT",
        "enabled": 0,
        "label": "Bảo hiểm và Đầu tư tích lũy",
        "linkAlias": "bao-hiem-va-dau-tu-tich-luy",
        "sort": 0
    },
    {
        "forCandidate": 0,
        "code": "NEWS",
        "enabled": 0,
        "isVideo": 0,
        "isVideoYoutube": 0,
        "no": 0,
        "countUsed": 0,
        "homepage": 0,
        "event": 0,
        "label": "Tin tức và Sự kiện",
        "linkAlias": "tin-tuc-va-su-kien",
        "sort": 0,
        "robotIndexTag": false,
        "robotFollowTag": false,
        "hasEvent": false
    }
];
export const CMS_SUB_CATEGORY_MAP_JSON = {
    "MEDICINE": [
        {
            "id": 100241,
            "forCandidate": 0,
            "enabled": 0,
            "isVideo": 0,
            "isVideoYoutube": 0,
            "no": 0,
            "countUsed": 0,
            "homepage": 0,
            "event": 0,
            "label": "Bệnh thường gặp",
            "sort": 0,
            "robotIndexTag": false,
            "robotFollowTag": false,
            "hasEvent": false
        },
        {
            "id": 100252,
            "forCandidate": 0,
            "enabled": 0,
            "isVideo": 0,
            "isVideoYoutube": 0,
            "no": 0,
            "countUsed": 0,
            "homepage": 0,
            "event": 0,
            "label": "Thuốc & Thực phẩm chức năng",
            "sort": 0,
            "robotIndexTag": false,
            "robotFollowTag": false,
            "hasEvent": false
        }
    ],
    "HABIT": [
        {
            "id": 100243,
            "forCandidate": 0,
            "enabled": 0,
            "isVideo": 0,
            "isVideoYoutube": 0,
            "no": 0,
            "countUsed": 0,
            "homepage": 0,
            "event": 0,
            "label": "Sức khỏe thể chất",
            "sort": 0,
            "robotIndexTag": false,
            "robotFollowTag": false,
            "hasEvent": false
        },
        {
            "id": 100245,
            "forCandidate": 0,
            "enabled": 0,
            "isVideo": 0,
            "isVideoYoutube": 0,
            "no": 0,
            "countUsed": 0,
            "homepage": 0,
            "event": 0,
            "label": "Sức khỏe tinh thần",
            "sort": 0,
            "robotIndexTag": false,
            "robotFollowTag": false,
            "hasEvent": false
        }
    ],
    "FOODS": [
        {
            "id": 100246,
            "forCandidate": 0,
            "enabled": 0,
            "isVideo": 0,
            "isVideoYoutube": 0,
            "no": 0,
            "countUsed": 0,
            "homepage": 0,
            "event": 0,
            "label": "Thực phẩm",
            "sort": 0,
            "robotIndexTag": false,
            "robotFollowTag": false,
            "hasEvent": false
        },
        {
            "id": 100247,
            "forCandidate": 0,
            "enabled": 0,
            "isVideo": 0,
            "isVideoYoutube": 0,
            "no": 0,
            "countUsed": 0,
            "homepage": 0,
            "event": 0,
            "label": "Dinh dưỡng",
            "sort": 0,
            "robotIndexTag": false,
            "robotFollowTag": false,
            "hasEvent": false
        }
    ],
    "NEWS": [
        {
            "id": 100250,
            "forCandidate": 0,
            "enabled": 0,
            "isVideo": 0,
            "isVideoYoutube": 0,
            "no": 0,
            "countUsed": 0,
            "homepage": 0,
            "event": 0,
            "label": "Tin tức",
            "sort": 0,
            "robotIndexTag": false,
            "robotFollowTag": false,
            "hasEvent": false
        },
        {
            "id": 100251,
            "forCandidate": 0,
            "enabled": 0,
            "isVideo": 0,
            "isVideoYoutube": 0,
            "no": 0,
            "countUsed": 0,
            "homepage": 0,
            "event": 0,
            "label": "Sự kiện",
            "sort": 0,
            "robotIndexTag": false,
            "robotFollowTag": false,
            "hasEvent": false
        },
        {
            "id": 100253,
            "forCandidate": 0,
            "enabled": 0,
            "isVideo": 0,
            "isVideoYoutube": 0,
            "no": 0,
            "countUsed": 0,
            "homepage": 0,
            "event": 0,
            "label": "Bản tin khách hàng",
            "sort": 0,
            "robotIndexTag": false,
            "robotFollowTag": false,
            "hasEvent": false
        }
    ],
    "INVESTMENT": [
        {
            "id": 100248,
            "forCandidate": 0,
            "enabled": 0,
            "isVideo": 0,
            "isVideoYoutube": 0,
            "no": 0,
            "countUsed": 0,
            "homepage": 0,
            "event": 0,
            "label": "Bảo hiểm",
            "sort": 0,
            "robotIndexTag": false,
            "robotFollowTag": false,
            "hasEvent": false
        },
        {
            "id": 100249,
            "forCandidate": 0,
            "enabled": 0,
            "isVideo": 0,
            "isVideoYoutube": 0,
            "no": 0,
            "countUsed": 0,
            "homepage": 0,
            "event": 0,
            "label": "Đầu tư & Tích lũy",
            "sort": 0,
            "robotIndexTag": false,
            "robotFollowTag": false,
            "hasEvent": false
        }
    ]
};