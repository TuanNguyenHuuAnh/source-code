/*******************************************************************************
 * Class        ItemDto
 * Created date 2017/03/28
 * Lasted date  2017/03/28
 * Author       trieunh <trieunh@unit.com.vn>
 * Change log   2017/03/2801-00 trieunh <trieunh@unit.com.vn> create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;

/**
 * ItemDto
 * 
 * @version 01-00
 * @since 01-00
 * @author trieunh <trieunh@unit.com.vn>
 */
public class ItemDto {
    private long id;
    private String functionCode;
    private String functionName;
    
    public ItemDto () {
        
    }
    
    public ItemDto (long id, String functionName) {
        this.id = id;
        this.functionName = functionName;
    }

    /**
     * Get id
     * @return long
     * @author trieunh <trieunh@unit.com.vn>
     */
    public long getId() {
        return id;
    }

    /**
     * Set id
     * @param   id
     *          type long
     * @return
     * @author  trieunh <trieunh@unit.com.vn>
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get functionCode
     * @return String
     * @author trieunh <trieunh@unit.com.vn>
     */
    public String getFunctionCode() {
        return functionCode;
    }

    /**
     * Set functionCode
     * @param   functionCode
     *          type String
     * @return
     * @author  trieunh <trieunh@unit.com.vn>
     */
    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    /**
     * Get functionName
     * @return String
     * @author trieunh <trieunh@unit.com.vn>
     */
    public String getFunctionName() {
        return functionName;
    }

    /**
     * Set functionName
     * @param   functionName
     *          type String
     * @return
     * @author  trieunh <trieunh@unit.com.vn>
     */
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
    
    
}
