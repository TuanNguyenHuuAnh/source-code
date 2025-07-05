/*******************************************************************************
 * Class        ProcessSearchDto
 * Created date 2018/07/26
 * Lasted date  2018/07/26
 * Author       VinhLT
 * Change log   2018/07/2601-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Date;

//import vn.com.unit.jcanary.dto.AbstractDTO;

/**
 * ProcessSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
public class ProcessSearchDto extends AbstractDTO{

	private String businessCode;

	private String processCode;

	private Date effectiveDate;

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

	/**
	 * Get processCode
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getProcessCode() {
		return processCode;
	}

	/**
	 * Set processCode
	 * 
	 * @param processCode
	 *            type String
	 * @return
	 * @author VinhLT
	 */
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	/**
	 * Get effectiveDate
	 * 
	 * @return Date
	 * @author VinhLT
	 */
	public Date getEffectiveDate() {
		return effectiveDate != null ? (Date) effectiveDate.clone() : null;
	}

	/**
	 * Set effectiveDate
	 * 
	 * @param effectiveDate
	 *            type Date
	 * @return
	 * @author VinhLT
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate != null ? (Date) effectiveDate.clone() : null;
	}

}
