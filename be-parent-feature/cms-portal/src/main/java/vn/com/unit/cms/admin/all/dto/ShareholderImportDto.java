/*******************************************************************************
 * Class        ：AgentBankImportDto
 * Created date ：2017/07/25
 * Lasted date  ：2017/07/25
 * Author       ：phunghn
 * Change log   ：2017/07/25：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * AgentBankImportDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
@SuppressWarnings("deprecation")
public class ShareholderImportDto {
	
	@NotEmpty
    private String code;

    @NotEmpty
    private String name;
    
    @NotEmpty
    private String address;

    @NotEmpty
    private String idNumber;
    
    @NotEmpty
    private Date dateOfIssue;
    
    @NotEmpty
    private String placeOfIssue;
    
    private Long sortOrder;
    
    private Double ownedSharesAmount;
    
    private String strOwnedSharesAmount;
    
    private String strDividendAmount;
    
    private Double dividendAmount;
    
    private Date createDate;
    
    private Date updateDate;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Date getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public String getPlaceOfIssue() {
		return placeOfIssue;
	}

	public void setPlaceOfIssue(String placeOfIssue) {
		this.placeOfIssue = placeOfIssue;
	}

	public Long getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Double getOwnedSharesAmount() {
		return ownedSharesAmount;
	}

	public void setOwnedSharesAmount(Double ownedSharesAmount) {
		this.ownedSharesAmount = ownedSharesAmount;
	}

	public String getStrOwnedSharesAmount() {
		return strOwnedSharesAmount;
	}

	public void setStrOwnedSharesAmount(String strOwnedSharesAmount) {
		this.strOwnedSharesAmount = strOwnedSharesAmount;
	}

	public String getStrDividendAmount() {
		return strDividendAmount;
	}

	public void setStrDividendAmount(String strDividendAmount) {
		this.strDividendAmount = strDividendAmount;
	}

	public Double getDividendAmount() {
		return dividendAmount;
	}

	public void setDividendAmount(Double dividendAmount) {
		this.dividendAmount = dividendAmount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}    
}
