/*******************************************************************************
 * Class        ：DocumentViewAuthorityDto
 * Created date ：2017/04/28
 * Lasted date  ：2017/04/28
 * Author       ：thuydtn
 * Change log   ：2017/04/28：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

/**
 * DocumentViewAuthorityDto
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public class DocumentViewAuthoritySelectDto {
    private String functionCode;
    private String dispName;
    /**
     * Get functionCode
     * @return String
     * @author thuydtn
     */
    public String getFunctionCode() {
        return functionCode;
    }
    /**
     * Set functionCode
     * @param   functionCode
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }
    /**
     * Get dispName
     * @return String
     * @author thuydtn
     */
    public String getDispName() {
        return dispName;
    }
    /**
     * Set dispName
     * @param   dispName
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setDispName(String dispName) {
        this.dispName = dispName;
    }

}
