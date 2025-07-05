/*******************************************************************************
 * Class        BusinessDefinitionSearchDto
 * Created date 2018/07/27
 * Lasted date  2018/07/27
 * Author       VinhLT
 * Change log   2018/07/2701-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

/**
 * BusinessDefinitionSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
public class BusinessDefinitionSearchDto extends AbstractDTO {

	private String code;

	private String name;

	/**
	 * Get code
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Set code
	 * 
	 * @param code
	 *            type String
	 * @return
	 * @author VinhLT
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Get name
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name
	 * 
	 * @param name
	 *            type String
	 * @return
	 * @author VinhLT
	 */
	public void setName(String name) {
		this.name = name;
	}

}
