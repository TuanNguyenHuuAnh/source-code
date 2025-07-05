/*******************************************************************************
 * Class        ：ProductNodeDto
 * Created date ：2017/06/29
 * Lasted date  ：2017/06/29
 * Author       ：phunghn
 * Change log   ：2017/06/29：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

/**
 * ProductNodeDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public class ProductNodeDto {

	private String id;
	
	private String text;
	
	private String iconCls;
	
	private int isProduct;
	
	private List<ProductNodeDto> children;
	
	private Long idType;
	
	private Boolean checked;

	/**
	 * Get id
	 * @return String
	 * @author phunghn
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set id
	 * @param   id
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get text
	 * @return String
	 * @author phunghn
	 */
	public String getText() {
		return text;
	}

	/**
	 * Set text
	 * @param   text
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Get iconCls
	 * @return String
	 * @author phunghn
	 */
	public String getIconCls() {
		return iconCls;
	}

	/**
	 * Set iconCls
	 * @param   iconCls
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	/**
	 * Get isProduct
	 * @return int
	 * @author phunghn
	 */
	public int getIsProduct() {
		return isProduct;
	}

	/**
	 * Set isProduct
	 * @param   isProduct
	 *          type int
	 * @return
	 * @author  phunghn
	 */
	public void setIsProduct(int isProduct) {
		this.isProduct = isProduct;
	}

	/**
	 * Get children
	 * @return List<ProductNodeDto>
	 * @author phunghn
	 */
	public List<ProductNodeDto> getChildren() {
		return children;
	}

	/**
	 * Set children
	 * @param   children
	 *          type List<ProductNodeDto>
	 * @return
	 * @author  phunghn
	 */
	public void setChildren(List<ProductNodeDto> children) {
		this.children = children;
	}

	/**
	 * Get idType
	 * @return Long
	 * @author phunghn
	 */
	public Long getIdType() {
		return idType;
	}

	/**
	 * Set idType
	 * @param   idType
	 *          type Long
	 * @return
	 * @author  phunghn
	 */
	public void setIdType(Long idType) {
		this.idType = idType;
	}

	/**
	 * Get checked
	 * @return Boolean
	 * @author phunghn
	 */
	public Boolean getChecked() {
		return checked;
	}

	/**
	 * Set checked
	 * @param   checked
	 *          type Boolean
	 * @return
	 * @author  phunghn
	 */
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	
}
