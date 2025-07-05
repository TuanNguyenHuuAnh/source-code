/*******************************************************************************
 * Class        AppUrlConst
 * Created date 2018/11/27
 * Lasted date  2018/11/27
 * Author       KhoaNA
 * Change log   2018/11/27 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.constant;

/**
 * AppUrlConst
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class AppUrlConst extends UrlConst {
	/** Submit */
	public static final String AJAX_SUBMIT = "/ajax/submit";
	
	/** URL Doc */
	public static final String DOC = "/doc";
	public static final String DOC_EXPORT = "/export";
	
	/** URL Reports */
	//public static final String REPORTS = "/reports";
	public static final String REPORTS_AJAX_VALIDATE_LIMIT_TRANSACTION = "/ajax/validate-limit-transaction";
	public static final String REPORTS_ALERT = "/ajax/alert";
	
	/** API_V1_FORM_REPORTS */
	public static final String API_V1_FORM_REPORTS = "/api/v1/form/reports";
	
	/** API_V1_FORM_REPORTS */
    public static final String API_V1_FORM_REPORTS_COMPONENT = "/api/v1/form/reports/component";
	
	/** REGISTER_SVC */
	public static final String REGISTER_SVC = "/register-svc";
	public static final String JPM_REGISTER_SVC = "/jpm-register-svc";
	
	/** REGISTER */
    public static final String REGISTER = "/register";
    
    /** COMPONENT_AUTHORITY */
    public static final String COMPONENT_AUTHORITY = "/component-authority";
    
    /** REGISTER_SVC */
    public static final String SVC_MANAGEMENT = "/svc-management";
    public static final String JPM_SVC_MANAGEMENT = "/jpm-svc-management";
    
    /** SERVER */
    public static final String SERVER = "/server";

    public static final String DOWNLOAD = "/download";
	public static final String REVERT = "/revert";
	public static final String UPLOAD_OZ = "/uploadOz";
	
	public static final String CONTACT = "/contact";
	public static final String CONTACT_MANAGEMENT = "/contact-management";
}
