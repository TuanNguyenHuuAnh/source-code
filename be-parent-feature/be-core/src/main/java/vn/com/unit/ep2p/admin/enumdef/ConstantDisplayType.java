/*******************************************************************************
 * Class        ConstantDisplayType
 * Created date 2016/07/21
 * Lasted date  2016/07/21
 * Author       KhoaNA
 * Change log   2016/07/1901-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.enumdef;

/**
 * Enum ConstantDisplayType
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public enum ConstantDisplayType {
    // Group formatDate --- Screen system config
	JCA_ADMIN_DATE("JCA_ADMIN_DATE"),
    
    //Group formatMonth ---Screen system config
    M15("M15"),

    // Group status account --- Screen user manage
    M02("M02"),

    // Group career --- Screen job manager
    M03("M03"),

    // Group status role --- Screen role manage
    M04("M04"),

    // Group status common --- Screen All
    M05("M05"),    
    
    // Group master status --- Screen All
    M06("M06"),    
    
    // Group status common --- Screen menu 
    // Menu type
    JCA_ADMIN_MENU("JCA_ADMIN_MENU"),
    // Menu icon
    M08("M08"),
    // Branch type
    M09("M09"),
    // Group banerType --- Screen banner
    M10("M10"),
    
    //Introduction view type
    M11("M11"),
	
	M12("M12"),
	
	M14("M14"),
	M16("M16"),
	M18("M18"),
	
    // Agency Type
    AG1("AG1"),
    
    // Agency Sub Type 1
    AG2("AG2"),
    
    // job position
    J01 ("J01"),
    
    // job division
    J02 ("J02"),
    C01 ("C01"),
    
    //job form apply
    J03("J03"),
    
 	// org type
	ORG_TYPE("ORG_TYPE"),
    
    //banner page
    B01("B01"),
    
    // cutoff Type
    CUTOFF("CUTOFF"),
    
    /**SENDEMAILOPT*/
    SLA_SEND_TYPE("SLA_SEND_TYPE"),
    
    /** JPM - BEGIN */
	// 20190807 - KhoaNA - JPM Process Button Class Type
    PROCESS_BUTTON_CLASS_TYPE("PROCESS_BUTTON_CLASS_TYPE"),
	
	// 20190818 - KhoaNA - JPM Process Button Icon
	J_PRP_BTN_002("J_PRP_BTN_002"),
	
	// 20190807 - KhoaNA - JPM Process Status Common
	J_PRP_STATUS_001("J_PRP_STATUS_001"),
	
	// 20191023 - KhuongTH - Jpm Process Type for Business display
	J_PROCESS_TYPE_001("J_PROCESS_TYPE_001"),
	/** JPM - END */
	
    /** APP - BEGIN */
	// 20190807 - KhoaNA - document priority
	A_DOC_PRIORITY_001("A_DOC_PRIORITY_001"),
	
	// 20190807 - KhoaNA - document history activity
	A_DOC_ACTIVITY_001("A_DOC_ACTIVITY_001"),
	/** APP - END */
    
    //20190823 - TrieuVD - genderAccount
	JCA_ADMIN_GENDER("JCA_ADMIN_GENDER"),
	
	// 20190927 - TaiTT - attach file content tpe
	TYPE_ATTACH("TYPE_ATTACH"),
	/** APP - END */
    
	J_BUS_SERVICE_001("J_BUS_SERVICE_001"),
	
	   //20191024 - TrieuVD - form type
    FORM_TYPE("FORM_TYPE"),
    
    FB_STATUS_001("FB_STATUS_001"),
	
    PROFILE_STATUS("PROFILE_STATUS"),
	
	// 20200226 - TaiTT - type user guide system 
    SYSTEM_APP_TYPE("SYSTEM_APP_TYPE"),
    
    SYSTEM_NAME("SYSTEM_NAME"),
    
	//
	CHANNEL("CHANNEL"),
    
    // 20201009 - TaiTT - type appStoreLink
    APP_STORE_LINK("APP_STORE_LINK");
	    
    private String value;

    private ConstantDisplayType(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
