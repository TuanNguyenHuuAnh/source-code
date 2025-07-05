/*******************************************************************************
 * Class        ：NewsTypeDto
 * Created date ：2017/03/01
 * Lasted date  ：2017/03/01
 * Author       ：hand
 * Change log   ：2017/03/01：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

/**
 * NewsTypeDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class NewsTypeDto {

    /** id */
    private Long id;

    /** code */
    private String code;

    /** name */
    private String label;

    /**
     * Get id
     * @return Long
     * @author hand
     */
    public Long getId() {
        return id;
    }

    /**
     * Get code
     * @return String
     * @author hand
     */
    public String getCode() {
        return code;
    }

    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  hand
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set code
     * @param   code
     *          type String
     * @return
     * @author  hand
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Get label
     * @return String
     * @author hand
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set label
     * @param   label
     *          type String
     * @return
     * @author  hand
     */
    public void setLabel(String label) {
        this.label = label;
    }

}
