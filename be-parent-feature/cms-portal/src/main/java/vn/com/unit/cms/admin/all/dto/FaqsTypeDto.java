/*******************************************************************************
 * Class        ：FaqsTypeDto
 * Created date ：2017/03/19
 * Lasted date  ：2017/03/19
 * Author       ：hand
 * Change log   ：2017/03/19：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

/**
 * FaqsTypeDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class FaqsTypeDto {

	/**id*/
	private Long id;
	
	/**code*/
	private String code;
	
	/**name*/
	private String title;
	
	private String linkAlias;
	
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
     * Get title
     * @return String
     * @author hand
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title
     * @param   title
     *          type String
     * @return
     * @author  hand
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get linkAlias
     * @return String
     * @author TranLTH
     */
    public String getLinkAlias() {
        return linkAlias;
    }

    /**
     * Set linkAlias
     * @param   linkAlias
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }
}