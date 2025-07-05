/*******************************************************************************
 * Class        OrgType
 * Created date 2016/07/21
 * Lasted date  2016/07/21
 * Author       KhoaNA
 * Change log   2016/07/1901-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.enumdef;

/**
 * Enum OrgType
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public enum PositionType {
    /** Region */
    REGION("R"),
    
    /** Branch */
    BRANCH("B"),

    /** Section */
    SECTION("S"),

    /** Team */
    TEAM("T"),
    
    /** Company */
    COMPANY("C"),

    /** View only Organization Chart */
    VIEW_ONLY_CHART("V"),
    
	/** AREA */
    AREA("A"),
    
    ALL("");
	
    /** value */
    private String value;
    
    /**
     * Contructor default
     * @param value
     *          type String
     * @author KhoaNA
     */
    private PositionType (String value) {
        this.value = value;
    }
    
    public String toString() {
        return value;
    }
}
