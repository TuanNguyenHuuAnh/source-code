/*******************************************************************************
 * Class        ：PaymentCard
 * Created date ：2017/06/14
 * Lasted date  ：2017/06/14
 * Author       ：thuydtn
 * Change log   ：2017/06/14：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * PaymentCard
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public class PaymentCardDto extends AbstractTracking{
    private int id;
    private String code;
    private String name;
    private String type;
    private String subType;
    
    /**
     * Get id
     * @return int
     * @author thuydtn
     */
    public int getId() {
        return id;
    }
    
    /**
     * Set id
     * @param   id
     *          type int
     * @return
     * @author  thuydtn
     */
    public void setId(int id) {
        this.id = id;
    }
    
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
     * Get name
     * @return String
     * @author thuydtn
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set name
     * @param   name
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Get type
     * @return String
     * @author thuydtn
     */
    public String getType() {
        return type;
    }
    
    /**
     * Set type
     * @param   type
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * Get subType
     * @return String
     * @author thuydtn
     */
    public String getSubType() {
        return subType;
    }
    
    /**
     * Set subType
     * @param   subType
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setSubType(String subType) {
        this.subType = subType;
    }
}
