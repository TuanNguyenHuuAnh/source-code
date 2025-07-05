/*******************************************************************************
 * Class        ：ShareHolderDto
 * Created date ：2017/02/20
 * Lasted date  ：2017/02/20
 * Author       ：thuydtn
 * Change log   ：2017/02/20：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import org.springframework.beans.factory.annotation.Required;

/**
 * ShareHolderDto
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@SuppressWarnings("deprecation")
public class ShareHolderSearchDto {

	private Long id;

	private String code;

	private String name;

	private String address;

	private String idNumber;

	private String dateOfIssue;

	private String placeOfIssue;

	private Double ownedSharesAmount;

	private Double dividendAmount;

	private String status;

	public ShareHolderSearchDto() {

	}

	/**
	 * Get id
	 * 
	 * @return Long
	 * @author thuydtn
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set id
	 * 
	 * @param id type Long
	 * @return
	 * @author thuydtn
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Get code
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Set code
	 * 
	 * @param code type String
	 * @return
	 * @author thuydtn
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Get name
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name
	 * 
	 * @param name type String
	 * @return
	 * @author thuydtn
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get address
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Set address
	 * 
	 * @param address type String
	 * @return
	 * @author thuydtn
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Get idNumber
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getIdNumber() {
		return idNumber;
	}

	/**
	 * Set idNumber
	 * 
	 * @param idNumber type String
	 * @return
	 * @author thuydtn
	 */
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	/**
	 * Get dateOfIssue
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getDateOfIssue() {
		return dateOfIssue;
	}

	/**
	 * Set dateOfIssue
	 * 
	 * @param dateOfIssue type String
	 * @return
	 * @author thuydtn
	 */
	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	/**
	 * Get placeOfIssue
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getPlaceOfIssue() {
		return placeOfIssue;
	}

	/**
	 * Set placeOfIssue
	 * 
	 * @param placeOfIssue type String
	 * @return
	 * @author thuydtn
	 */
	public void setPlaceOfIssue(String placeOfIssue) {
		this.placeOfIssue = placeOfIssue;
	}

	/**
	 * Get ownedSharesAmount
	 * 
	 * @return Double
	 * @author thuydtn
	 */
	public Double getOwnedSharesAmount() {
		return ownedSharesAmount;
	}

	/**
	 * Set ownedSharesAmount
	 * 
	 * @param ownedSharesAmount type Double
	 * @return
	 * @author thuydtn
	 */
	public void setOwnedSharesAmount(Double ownedSharesAmount) {
		this.ownedSharesAmount = ownedSharesAmount;
	}

	/**
	 * Get dividendAmount
	 * 
	 * @return Double
	 * @author thuydtn
	 */
	public Double getDividendAmount() {
		return dividendAmount;
	}

	/**
	 * Set dividendAmount
	 * 
	 * @param dividendAmount type Double
	 * @return
	 * @author thuydtn
	 */
	public void setDividendAmount(Double dividendAmount) {
		this.dividendAmount = dividendAmount;
	}

	/**
	 * Get status
	 * 
	 * @return String
	 * @author thuydtn
	 */
	@Required
	public String getStatus() {
		return status;
	}

	/**
	 * Set status
	 * 
	 * @param status type String
	 * @return
	 * @author thuydtn
	 */
	@Required
	public void setStatus(String status) {
		this.status = status;
	}

}
