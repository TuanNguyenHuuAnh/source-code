/*******************************************************************************
 * Class        ：ChatUserProductSearchDto
 * Created date ：2017/08/24
 * Lasted date  ：2017/08/24
 * Author       ：phunghn
 * Change log   ：2017/08/24：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

/**
 * ChatUserProductSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public class ChatUserProductSearchDto {

	private String product;
	
	private String user;
	
	private String roleName;
    
    /** fieldValues */
    private List<String> fieldValues;
    
    /** fieldSearch */
    private String fieldSearch;
    
    private Integer pageSize;

	/**
	 * Get product
	 * @return String
	 * @author phunghn
	 */
	public String getProduct() {
		return product;
	}

	/**
	 * Set product
	 * @param   product
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setProduct(String product) {
		this.product = product;
	}

	/**
	 * Get user
	 * @return String
	 * @author phunghn
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Set user
	 * @param   user
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Get fieldValues
	 * @return List<String>
	 * @author phunghn
	 */
	public List<String> getFieldValues() {
		return fieldValues;
	}

	/**
	 * Set fieldValues
	 * @param   fieldValues
	 *          type List<String>
	 * @return
	 * @author  phunghn
	 */
	public void setFieldValues(List<String> fieldValues) {
		this.fieldValues = fieldValues;
	}

	/**
	 * Get fieldSearch
	 * @return String
	 * @author phunghn
	 */
	public String getFieldSearch() {
		return fieldSearch;
	}

	/**
	 * Set fieldSearch
	 * @param   fieldSearch
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setFieldSearch(String fieldSearch) {
		this.fieldSearch = fieldSearch;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
