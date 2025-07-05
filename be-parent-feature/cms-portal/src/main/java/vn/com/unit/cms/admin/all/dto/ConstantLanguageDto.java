/*******************************************************************************
 * Class        ：ConstantLanguageDto
 * Created date ：2017/10/17
 * Lasted date  ：2017/10/17
 * Author       ：TranLTH
 * Change log   ：2017/10/17：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

/**
 * ConstantLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public class ConstantLanguageDto {
    private Long id;
    private String constantCode;
    private String languageCode;
    private String name;
    /**
     * Get id
     * @return Long
     * @author TranLTH
     */
    public Long getId() {
        return id;
    }
    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Get constantCode
     * @return String
     * @author TranLTH
     */
    public String getConstantCode() {
        return constantCode;
    }
    /**
     * Set constantCode
     * @param   constantCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setConstantCode(String constantCode) {
        this.constantCode = constantCode;
    }
    /**
     * Get languageCode
     * @return String
     * @author TranLTH
     */
    public String getLanguageCode() {
        return languageCode;
    }
    /**
     * Set languageCode
     * @param   languageCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
    /**
     * Get name
     * @return String
     * @author TranLTH
     */
    public String getName() {
        return name;
    }
    /**
     * Set name
     * @param   name
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setName(String name) {
        this.name = name;
    }
}
