package vn.com.unit.ep2p.constant;

public class UrlConstant {
	/** exchange-rate */
	public static final String TTNT_API_HDBANK = "/sp_ThongTinNgoaiTe";
	
	/** gold-exchange-rate */
	public static final String TTTGV_API_HDBANK = "/sp_ThongTinTyGiaVang";
	
	/** query-savings-book */
	public static final String POST_SAVEBOOKINFO = "/sp_ThongTinSoTietKiem";
	
	/** query-guarantee-information */
	public static final String POST_GUARANTEEINFO = "/sp_ThongTinBaoLanh";
	
	/** get-money-type */
	public static final String POST_MONEYTYPEINFO = "/sp_ThongTinLoaiTien";
	
	/** query-customer-name */
	public static final String POST_CUSTOMERGUARANTEEINFO = "/sp_ThongTinTenKhachHangBaoLanh";
    
	public static final String URL_REST_API = "/api";
	public static final String URL_REST_API_PRODUCT = "/api-product";
	public static final String COMMONS = "/commons";
	
	public static final String CATEGORY = "/category";
	
	public static final String PRODUCT_DETAIL = "/product/detail";
	public static final String PRODUCT_LIST = "/product/list";
	public static final String PROMOTION = "/promotion";
	public static final String PROMOTION_LIST = "/promotion/list";
	public static final String PROMOTION_DETAIL = "/promotion/detail";
	public static final String DOWNLOAD = "/download";
	
	public static final String URL_MICROSITE = "/microsite";
	
	public static final String PRIORITY = "/priority";
	
	public static final String ABOUT = "/about";
	
	public static final String INVESTORS = "/investors";
	
	public static final String CONTACT = "/contact";
	
	public static final String CUSTOMER_ALIAS = "/customerAlias";
	
	public static final String PERSONAL = "personal";
	
	public static final String CORPORATE = "corporate";
	
	public static final String HD_BANK = "about-hdbank";
	
	public static final String ROOT = "/";
	
	
	public static final Long PERSONAL_ID = 9L;
	public static final Long CORPORATE_ID = 10L;
	public static final Long ABOUT_BANK_ID = 12L;
	public static final Long ABOUT_HDBANK = 11L;
	
	public static final String FILE_NAME = "?fileName=";
	
	public static final String ERROR = "{ERROR}";
	
	
	public static final String POST_CUSTOMERGUARANTEEINFO_CONTROLLER = "/query-customer-name";
	
	
	public static final String POST_GUARANTEE_INFO_CONTROLLER = "/query-guarantee-information";
	
	/** get-money-type in Controller */
	public static final String POST_MONEYTYPEINFO_CONTROLLER = "/get-money-type";
	
	public static final String POST_QUERY_BOND_INFORMATION_CONTROLLER = "/query-bond-information";
	
	public static final String POST_QUERY_BOND_INFORMATION = "/sp_ThongTinTraiPhieu";
	
	public static final String POST_QUERY_BOND_INFORMATION_CUSTOMER_CONTROLLER = "/query-bond-information/customer";
	
	public static final String POST_QUERY_BOND_CUSTOMER_INFORMATION = "/sp_ThongTinTenKhachHangTraiPhieu";
	
	public static final String POST_QUERY_BOND_INFORMATION_GET_OTP_CONTROLLER = "/query-bond-information/get-otp";
	
	public static final String POST_QUERY_BOND_GET_OTP_INFORMATION = "/sp_ThongTinOTPTraiPhieu";
	
	public static final String REGISTER_SENT_EMAIL = "/register/sent-email";
	
	public static final String REGISTER_MAKE_AN_APPOINTMENT = "/register/make-an-appointment";
	
}
