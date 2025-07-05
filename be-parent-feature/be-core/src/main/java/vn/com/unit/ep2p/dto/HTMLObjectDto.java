/*******************************************************************************
 * Class        HTMLObjectDto
 * Created date 2018/08/02
 * Lasted date  2018/08/02
 * Author       VinhLT
 * Change log   2018/08/0201-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

/**
 * HTMLObjectDto
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
public class HTMLObjectDto {

	private Long id;

	private String htmlId;

	private String htmlText;

	private String htmlValue;

	private String htmlClass;

	private String businessCode;

	/**
	 * Get id
	 * 
	 * @return Long
	 * @author VinhLT
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set id
	 * 
	 * @param id
	 *            type Long
	 * @return
	 * @author VinhLT
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Get htmlId
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getHtmlId() {
		return htmlId;
	}

	/**
	 * Set htmlId
	 * 
	 * @param htmlId
	 *            type String
	 * @return
	 * @author VinhLT
	 */
	public void setHtmlId(String htmlId) {
		this.htmlId = htmlId;
	}

	/**
	 * Get htmlText
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getHtmlText() {
		return htmlText;
	}

	/**
	 * Set htmlText
	 * 
	 * @param htmlText
	 *            type String
	 * @return
	 * @author VinhLT
	 */
	public void setHtmlText(String htmlText) {
		this.htmlText = htmlText;
	}

	/**
	 * Get htmlValue
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getHtmlValue() {
		return htmlValue;
	}

	/**
	 * Set htmlValue
	 * 
	 * @param htmlValue
	 *            type String
	 * @return
	 * @author VinhLT
	 */
	public void setHtmlValue(String htmlValue) {
		this.htmlValue = htmlValue;
	}

	/**
	 * Get htmlClass
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getHtmlClass() {
		return htmlClass;
	}

	/**
	 * Set htmlClass
	 * 
	 * @param htmlClass
	 *            type String
	 * @return
	 * @author VinhLT
	 */
	public void setHtmlClass(String htmlClass) {
		this.htmlClass = htmlClass;
	}

	/**
	 * Get businessCode
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getBusinessCode() {
		return businessCode;
	}

	/**
	 * Set businessCode
	 * 
	 * @param businessCode
	 *            type String
	 * @return
	 * @author VinhLT
	 */
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

}
