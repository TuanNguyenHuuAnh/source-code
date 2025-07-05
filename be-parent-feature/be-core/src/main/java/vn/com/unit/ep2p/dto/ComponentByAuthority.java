/*******************************************************************************
 * Class        :ComponentByAuthority
 * Created date :2019/04/23
 * Lasted date  :2019/04/23
 * Author       :HungHT
 * Change log   :2019/04/23:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

/**
 * ComponentByAuthority
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class ComponentByAuthority {

	private String compId;
	private String compName;
	private String functionCode;
	private boolean canAccessFlg;
    private boolean canDispFlg;
    private boolean canEditFlg;
    
    /**
     * Get compId
     * @return String
     * @author HungHT
     */
    public String getCompId() {
        return compId;
    }
    
    /**
     * Set compId
     * @param   compId
     *          type String
     * @return
     * @author  HungHT
     */
    public void setCompId(String compId) {
        this.compId = compId;
    }
    
    /**
     * Get compName
     * @return String
     * @author HungHT
     */
    public String getCompName() {
        return compName;
    }
    
    /**
     * Set compName
     * @param   compName
     *          type String
     * @return
     * @author  HungHT
     */
    public void setCompName(String compName) {
        this.compName = compName;
    }
    
    /**
     * Get functionCode
     * @return String
     * @author HungHT
     */
    public String getFunctionCode() {
        return functionCode;
    }
    
    /**
     * Set functionCode
     * @param   functionCode
     *          type String
     * @return
     * @author  HungHT
     */
    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }
    
    /**
     * Get canAccessFlg
     * @return boolean
     * @author HungHT
     */
    public boolean isCanAccessFlg() {
        return canAccessFlg;
    }
    
    /**
     * Set canAccessFlg
     * @param   canAccessFlg
     *          type boolean
     * @return
     * @author  HungHT
     */
    public void setCanAccessFlg(boolean canAccessFlg) {
        this.canAccessFlg = canAccessFlg;
    }
    
    /**
     * Get canDispFlg
     * @return boolean
     * @author HungHT
     */
    public boolean isCanDispFlg() {
        return canDispFlg;
    }
    
    /**
     * Set canDispFlg
     * @param   canDispFlg
     *          type boolean
     * @return
     * @author  HungHT
     */
    public void setCanDispFlg(boolean canDispFlg) {
        this.canDispFlg = canDispFlg;
    }
    
    /**
     * Get canEditFlg
     * @return boolean
     * @author HungHT
     */
    public boolean isCanEditFlg() {
        return canEditFlg;
    }
    
    /**
     * Set canEditFlg
     * @param   canEditFlg
     *          type boolean
     * @return
     * @author  HungHT
     */
    public void setCanEditFlg(boolean canEditFlg) {
        this.canEditFlg = canEditFlg;
    }
}