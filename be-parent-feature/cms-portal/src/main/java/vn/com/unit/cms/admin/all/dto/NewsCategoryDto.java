/*******************************************************************************
 * Class        ：FaqsCategoryDto
 * Created date ：2017/03/19
 * Lasted date  ：2017/03/19
 * Author       ：hand
 * Change log   ：2017/03/19：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

/**
 * FaqsCategoryDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class NewsCategoryDto {

    /** id */
    private Long id;

    /** code */
    private String code;

    /** label */
    private String label;
    
    /** channel */
    private String channel;

    /**
     * Get id
     * @return Long
     * @author hand
     */
    public Long getId() {
        return id;
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
     * Get code
     * @return String
     * @author hand
     */
    public String getCode() {
        return code;
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
    
    public String getChannel() {
    	return channel;
    }

    public void setChannel(String channel) {
    	this.channel = channel;
    }
}
