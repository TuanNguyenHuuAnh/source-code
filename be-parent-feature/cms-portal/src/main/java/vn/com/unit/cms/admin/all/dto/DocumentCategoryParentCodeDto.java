/*******************************************************************************
 * Class        ：DocumentCategoryDto
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：thuydtn
 * Change log   ：2017/05/03:01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

/**
 * 
 * DocumentCategoryDto
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public class DocumentCategoryParentCodeDto{
    private Long id;
    private String code;
    private String parentCode;
    /**
     * Get code
     * @return String
     * @author thuydtn
     */
    public String getCode() {
        return code;
    }
    /**
     * Set code
     * @param   code
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * Get parentCode
     * @return String
     * @author thuydtn
     */
    public String getParentCode() {
        return parentCode;
    }
    /**
     * Set parentCode
     * @param   parentCode
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
    /**
     * Get id
     * @return Long
     * @author thuydtn
     */
    public Long getId() {
        return id;
    }
    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  thuydtn
     */
    public void setId(Long id) {
        this.id = id;
    }
}
