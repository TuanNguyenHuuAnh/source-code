/*******************************************************************************
 * Class        ConstDispType
 * Created date 2016/07/21
 * Lasted date  2016/07/21
 * Author       KhoaNA
 * Change log   2016/07/1901-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.constant;

/**
 * Enum ConstDispType
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public enum ConstDispType {
	
	// BANNER
    // Group banerType --- Screen banner
    M10("M10"),
	
    // Group formatDate --- Screen system config
    M01("M01"),
    
    //Group formatMonth ---Screen system config
    M15("M15"),

    // Group status account --- Screen user manage
    M02("M02"),

    // Group career --- Screen job manager
    M03("M03"),

    // Group job type --- Screen job manager
    JOB("JOB"),
    
    // Group job type career --- Screen job manager
    JOB_CAREER("1"),
    
    // Group job type position --- Screen job manager
    JOB_POSITION("3"),
    
    // Group job type division --- Screen job manager
    JOB_DIVISION("2"),
    
    // Group status role --- Screen role manage
    M04("M04"),

    // Group status common --- Screen All
    M05("M05"),    
    
    // Group master status --- Screen All
    M06("M06"),    
    
    // Group status common --- Screen menu 
    // Menu type
    M07("M07"),
    // Menu icon
    M08("M08"),
    // Branch type
    M09("M09"),
//    // Group banerType --- Screen banner
//    M10("M10"),
    
    //Introduction view type
    INTRODUCTION_TYPE("INTRODUCTION_TYPE"),
    
    MATH_TYPE_PERSONAL("MATH_TYPE_CUSTOMER"),
    
    MATH_TYPE_CORPORATE("MATH_TYPE_CORPORATE"),
    
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
    SENDEMAILOPT("SENDEMAILOPT"),

    //popup type
    POPUP("POPUP"),
    
    //position popup
    POPCLA("POPCLA"),
    
    NDT("NDT"),
    
    SERVICE("SERVICE"),
    
    MOTIVE("MOTIVE")
    
    ;

    private String value;

    private ConstDispType(String value) {
        this.value = value;
    }

    // LocLT
    // IMPORTANT: DON'T REMOVE @Override
    // FIX BUG ENUM DEPLOY ON JBOSS
    @Override
    public String toString() {
        return value;
    }
}
