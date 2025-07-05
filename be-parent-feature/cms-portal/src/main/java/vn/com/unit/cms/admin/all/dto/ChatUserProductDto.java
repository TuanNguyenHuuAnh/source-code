/*******************************************************************************
 * Class        ：ChatUserProductDto
 * Created date ：2017/08/24
 * Lasted date  ：2017/08/24
 * Author       ：phunghn
 * Change log   ：2017/08/24：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

/**
 * ChatUserProductDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public class ChatUserProductDto {

	private String username;
	
	private String productName;
	
	private Long mProductTypeId;
	
	private String fullname;
	
	private String customerTypeName;
	
	private String productCategoryName;
	
    private List<String> fieldValues;
    
    private String fieldSearch;
    
    private String roleName;

	/**
	 * Get username
	 * @return String
	 * @author phunghn
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set username
	 * @param   username
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get mProductTypeId
	 * @return Long
	 * @author phunghn
	 */
	public Long getmProductTypeId() {
		return mProductTypeId;
	}

	/**
	 * Set mProductTypeId
	 * @param   mProductTypeId
	 *          type Long
	 * @return
	 * @author  phunghn
	 */
	public void setmProductTypeId(Long mProductTypeId) {
		this.mProductTypeId = mProductTypeId;
	}

	/**
	 * Get productName
	 * @return String
	 * @author phunghn
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * Set productName
	 * @param   productName
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * Get fullname
	 * @return String
	 * @author phunghn
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * Set fullname
	 * @param   fullname
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * Get customerTypeName
	 * @return String
	 * @author phunghn
	 */
	public String getCustomerTypeName() {
		return customerTypeName;
	}

	/**
	 * Set customerTypeName
	 * @param   customerTypeName
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}

	/**
	 * Get productCategoryName
	 * @return String
	 * @author phunghn
	 */
	public String getProductCategoryName() {
		return productCategoryName;
	}

	/**
	 * Set productCategoryName
	 * @param   productCategoryName
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}

	public List<String> getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(List<String> fieldValues) {
		this.fieldValues = fieldValues;
	}

	public String getFieldSearch() {
		return fieldSearch;
	}

	public void setFieldSearch(String fieldSearch) {
		this.fieldSearch = fieldSearch;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
