/*******************************************************************************
 * Class        ：AdminUrlConst
 * Created date ：2017/02/14
 * Lasted date  ：2017/02/14
 * Author       ：trieunh
 * Change log   ：2017/02/14：01-00 trieunh create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.constant;

/**
 * AdminUrlConst
 * 
 * @version 01-00
 * @since 01-00
 * @author trieunh
 */
public class AdminUrlConst {

    // URL ROOT
    public static final String ROOT = "/";
    public static final String SHAREHOLDER = "/shareholder";
    public static final String DOCUMENT = "/document";

    // Link for menu
    public static final String MENU = "menu";
    public static final String MENU_ROOT = ROOT + MENU;
    public static final String MENULEFT = "/menu-left";
    public static final String BREADCRUMB = "/breadcrumb";

    public static final String MENUHOME = "menu-home";
    public static final String MENUHOME_ROOT = ROOT + MENUHOME;

    /** URL INTRODUCE INTERNET BANKING **/
    public static final String INTRODUCE_INTERNET_BANKING = "introduce-internet-banking";

    public static final String DOWLOAD = "/download";

    public static final String PRODUCT_CONSULTING = "/product-consulting";
    public static final String INVENDOR_CATEGORY = "/invendor/category";
    public static final String LIST = "/list";
    public static final String JOB = "/job";
    
    public static final String POPUP = "popup";
    public static final String POPUP_ROOT = ROOT + POPUP;

    /** LIST SORT **/
    public static final String LIST_SORT = "list/sort";
    
    public static final String AJAX_LIST_SORT = "ajax/list/sort";

    /** CUSTOMER CHANGE **/
    public static final String CUSTOMER_CHANGE = "customer/change";

    public static final String CONSTANT_TYPE = "constant-type";
    
    public static final String CONSTANT_TYPE_ROOT = ROOT + CONSTANT_TYPE;

    public static final String URL_EDITOR_FILE_UPLOAD = "/editor/file/upload";

    public static final String URL_EDITOR_FILE_DOWNLOAD = "/editor/file/download";

    /** URL PRODUCT CATEGORY. */
    public static final String PRODUCT_CATEGORY_SUB = "product-category";
    public static final String PRODUCT_CATEGORY_SUB_ROOT = ROOT + PRODUCT_CATEGORY_SUB;
    public static final String INTRODUCTION_CATEGORY = "introduction-category";
    
    /**URL JOB TYPE DETAIL. **/
    public static final String JOB_TYPE_DETAIL = "job-type-detail";
    /**URL JOB TYPE DETAIL. **/
    public static final String JOB_TYPE_DETAIL_ROOT = ROOT + JOB_TYPE_DETAIL;
    
    /**URL GUARANTEE CERTIFICATE */
    public static final String GUARANTEE_CERTIFICATE = "guarantee-certificate";
    
    /** URL NEWS. */
    public static final String NEWS = "news";
    /** URL NEWS CATEGORY. */
    public static final String NEWS_CATEGORY = "news-category";
    /** URL NEWS TYPE. */
    public static final String NEWS_TYPE = "news-type";
    /** URL NEWS TYPE ROOT. */
    public static final String NEWS_TYPE_ROOT = ROOT + NEWS_TYPE;
    
    /**URL ABOUT*/
    public static final String ABOUT = "about";
    
    /*URL EXPORT EXCEL*/
    public static final String EXPORT_EXCEL = "export-excel";
    
    public static final String URL_CACHES_COMMONS = "/clear-cache?apiName=commons";
    
    public static final String URL_CACHES_PERSONAL = "/clear-cache?apiName=personal";
    
    public static final String URL_CACHES_CORPORATE = "/clear-cache?apiName=corporate";
    
    public static final String URL_CACHES_HOME = "/clear-cache?apiName=home";
    
    public static final String URL_CACHES_INVESTORS = "/clear-cache?apiName=investors";
    
    public static final String URL_CACHES_ABOUT = "/clear-cache?apiName=about";
}
