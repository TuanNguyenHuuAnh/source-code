/*******************************************************************************
 * Class        ：ConstDispType
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/

package vn.com.unit.quartz.job.enumdef;

/**
 * <p>
 * ConstDispType
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public enum ConstDispType {
    
    /** The m01. */
    // Group formatDate --- Screen system config
    M01("M01"),
    
    /** The m15. */
    //Group formatMonth ---Screen system config
    M15("M15"),

    /** The m02. */
    // Group status account --- Screen user manage
    M02("M02"),

    /** The m03. */
    // Group career --- Screen job manager
    M03("M03"),

    /** The m04. */
    // Group status role --- Screen role manage
    M04("M04"),

    /** The m05. */
    // Group status common --- Screen All
    M05("M05"),    
    
    /** The m06. */
    // Group master status --- Screen All
    M06("M06"),    
    
    // Group status common --- Screen menu 
    /** The m07. */
    // Menu type
    M07("M07"),
    
    /** The m08. */
    // Menu icon
    M08("M08"),
    
    /** The m09. */
    // Branch type
    M09("M09"),
    
    /** The m10. */
    // Group banerType --- Screen banner
    M10("M10"),
    
    /** The m11. */
    //Introduction view type
    M11("M11"),
	
	/** The m12. */
	M12("M12"),
	
	/** The m14. */
	M14("M14"),
	
    /** The ag1. */
    // Agency Type
    AG1("AG1"),
    
    /** The ag2. */
    // Agency Sub Type 1
    AG2("AG2"),
    
    /** The j01. */
    // job position
    J01 ("J01"),
    
    /** The j02. */
    // job division
    J02 ("J02"),
    
    /** The c01. */
    C01 ("C01"),
    
    /** The j03. */
    //job form apply
    J03("J03"),
    
 	/** The org type. */
	 // org type
	ORG_TYPE("ORG_TYPE"),
    
    /** The b01. */
    //banner page
    B01("B01"),
    
    /** The cutoff. */
    // cutoff Type
    CUTOFF("CUTOFF");

    /** The value. */
    private String value;

    /**
     * <p>
     * Instantiates a new const disp type.
     * </p>
     *
     * @author khadm
     * @param value
     *            type {@link String}
     */
    private ConstDispType(String value) {
        this.value = value;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    public String toString() {
        return value;
    }
}
