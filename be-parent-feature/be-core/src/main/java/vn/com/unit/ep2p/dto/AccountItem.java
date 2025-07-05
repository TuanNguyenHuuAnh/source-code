/*******************************************************************************
 * Class        ：AccountItem
 * Created date ：2019/03/04
 * Lasted date  ：2019/03/04
 * Author       ：VinhLT
 * Change log   ：2019/03/04：01-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

/**
 * AccountItem
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
public class AccountItem {

	private String username;

	private String fullname;

	private String email;

	private String itemCode;

	private String costCenterCodes;
	
	private String costCenterCode;

	/**
	 * Get username
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set username
	 * 
	 * @param username type String
	 * @return
	 * @author VinhLT
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get fullname
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * Set fullname
	 * 
	 * @param fullname type String
	 * @return
	 * @author VinhLT
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * Get email
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set email
	 * 
	 * @param email type String
	 * @return
	 * @author VinhLT
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get itemCode
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * Set itemCode
	 * 
	 * @param itemCode type String
	 * @return
	 * @author VinhLT
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * Get costCenterCodes
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getCostCenterCodes() {
		return costCenterCodes;
	}

	/**
	 * Set costCenterCodes
	 * 
	 * @param costCenterCodes type String
	 * @return
	 * @author VinhLT
	 */
	public void setCostCenterCodes(String costCenterCodes) {
		this.costCenterCodes = costCenterCodes;
	}

	public String getCostCenterCode() {
		return costCenterCode;
	}

	public void setCostCenterCode(String costCenterCode) {
		this.costCenterCode = costCenterCode;
	}

}
