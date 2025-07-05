/*******************************************************************************
 * Class        ConstDispType
 * Created date 2016/07/21
 * Lasted date  2016/07/21
 * Author       KhoaNA
 * Change log   2016/07/1901-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.core.enumdef.param;

/**
 * Enum ConstDispType
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public enum ConstDispType {
    // Group formatDate --- Screen system config
    M01("M01"),
    
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
    M07("M07"),
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
    SENDEMAILOPT("SENDEMAILOPT"),
    STRUCTURE("STRUCTURE"),
    ACCOUNTTYPE("ACCOUNT_TYPE"),

    CHANNEL("CHANNEL"),
    //object type
	OBJECT_TYPE("OBJECT_TYPE"),
	
	//display
	DISPLAY("DISPLAY"),
	
	//level
	LEVEL_AGENT_TYPE("LEVEL_AGENT_TYPE"),
	
	//SUB_STRUCTURE
	SUB_STRUCTURE("SUB_STRUCTURE"),
	
	CENTRALIZE_CODE("CENTRALIZE_CODE");


    private String value;

    private ConstDispType(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
