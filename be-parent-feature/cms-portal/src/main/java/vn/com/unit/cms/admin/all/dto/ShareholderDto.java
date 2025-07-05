/*******************************************************************************
 * Class        ：ShareHolderDto
 * Created date ：2017/02/20
 * Lasted date  ：2017/02/20
 * Author       ：thuydtn
 * Change log   ：2017/02/20：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Required;

import vn.com.unit.cms.admin.all.entity.ShareHolder;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;

/**
 * ShareHolderDto
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@SuppressWarnings("deprecation")
public class ShareholderDto extends DocumentActionReq {

    private Long id;

    @NotEmpty
    private String code;

    @NotEmpty
    private String name;
    
    @NotEmpty
    private String address;

    @NotEmpty
    private String idNumber;
    
    private Date dateOfIssue;
    
    @NotEmpty
    private String placeOfIssue;
    
    private Long sortOrder;
    
    private Double ownedSharesAmount;
    
    private String strOwnedSharesAmount;
    
    private String strDividendAmount;
    
    private Double dividendAmount;
    
    private Date createDate;    
    
    
    private String status;
    private Long processId;
    
    private String url;
    private String comment;
    
    private Long referenceId;
    private String referenceType;
     
    public ShareholderDto(){
        this.sortOrder = 0L;
    }
    
    public ShareholderDto(ShareHolder entity) {
        this.setId(entity.getId());
        this.setCode(entity.getCode());
        this.setName(entity.getName());
        this.setAddress(entity.getAddress());
        this.setIdNumber(entity.getIdNumber());
        this.setDividendAmount(entity.getDividendAmount());
        this.setOwnedSharesAmount(entity.getOwnedSharesAmount());
        this.setDateOfIssue(entity.getDateOfIssue());
        this.setPlaceOfIssue(entity.getPlaceOfIssue());
        this.setStatus(entity.getStatus());
        this.setProcessId(entity.getProcessId());
        this.setCreateDate(entity.getCreateDate());
        this.setSortOrder(entity.getSort());
    }
    /**
     * Get id
     * @return Long
     * @author thuydtn
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  thuydtn
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get code
     * @return String
     * @author thuydtn
     */
    public String getCode() {
        return code;
    }

    /**
     * Set code
     * @param   code
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setCode(String code) {
        if(code != null){
            this.code = StringUtils.upperCase(code);
        }else{
            this.code = code;
        }
    }

    /**
     * Get name
     * @return String
     * @author thuydtn
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * @param   name
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get address
     * @return String
     * @author thuydtn
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set address
     * @param   address
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get idNumber
     * @return String
     * @author thuydtn
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * Set idNumber
     * @param   idNumber
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    /**
     * Get dateOfIssue
     * @return String
     * @author thuydtn
     */
    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    /**
     * Set dateOfIssue
     * @param   dateOfIssue
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    /**
     * Get placeOfIssue
     * @return String
     * @author thuydtn
     */
    public String getPlaceOfIssue() {
        return placeOfIssue;
    }

    /**
     * Set placeOfIssue
     * @param   placeOfIssue
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setPlaceOfIssue(String placeOfIssue) {
        this.placeOfIssue = placeOfIssue;
    }

    /**
     * Get ownedSharesAmount
     * @return double
     * @author thuydtn
     */
    public double getOwnedSharesAmount() {
        return ownedSharesAmount;
    }

    /**
     * Set ownedSharesAmount
     * @param   ownedSharesAmount
     *          type double
     * @return
     * @author  thuydtn
     */
    public void setOwnedSharesAmount(double ownedSharesAmount) {
        this.ownedSharesAmount = ownedSharesAmount;
        this.strOwnedSharesAmount = String.format("%,.2f", (double)ownedSharesAmount);
    }

    /**
     * Get dividendAmount
     * @return double
     * @author thuydtn
     */
    public double getDividendAmount() {
        return dividendAmount;
    }

    /**
     * Set dividendAmount
     * @param   dividendAmount
     *          type double
     * @return
     * @author  thuydtn
     */
    public void setDividendAmount(double dividendAmount) {
        this.dividendAmount = dividendAmount;
        this.strDividendAmount = String.format("%,.2f", (double)dividendAmount);
    }

    /**
     * Get status
     * @return String
     * @author thuydtn
     */
    @Required
    public String getStatus() {
        return status;
    }

    /**
     * Set status
     * @param   status
     *          type String
     * @return
     * @author  thuydtn
     */
    @Required
    public void setStatus(String status) {
        this.status = status;
    }
    
    public ShareHolder createEntity(){
        ShareHolder entity = new ShareHolder();
        entity .setId(this.getId());
        entity.setCode(this.getCode());
        entity.setName(this.getName());
        entity.setAddress(this.getAddress());
        entity.setIdNumber(this.getIdNumber());
        entity.setDividendAmount(this.getDividendAmount());
        entity.setOwnedSharesAmount(this.getOwnedSharesAmount());
        entity.setDateOfIssue(this.getDateOfIssue());
        entity.setPlaceOfIssue(this.getPlaceOfIssue());
        entity.setStatus(this.getStatus());
        entity.setSort(this.sortOrder);
        return entity;
    }

    /**
     * Get strOwnedSharesAmount
     * @return String
     * @author thuydtn
     */
    public String getStrOwnedSharesAmount() {
        return strOwnedSharesAmount;
    }

    /**
     * Set strOwnedSharesAmount
     * @param   strOwnedSharesAmount
     *          type String
     * @return
     * @author  thuydtn
     * @throws ParseException 
     */
    public void setStrOwnedSharesAmount(String strOwnedSharesAmount) throws ParseException {
        if(strOwnedSharesAmount != null && !strOwnedSharesAmount.trim().isEmpty()){
            this.strOwnedSharesAmount = strOwnedSharesAmount;
            NumberFormat numberFormat = NumberFormat.getInstance();
            this.ownedSharesAmount = numberFormat.parse(strOwnedSharesAmount).doubleValue();
        }else{
            this.strOwnedSharesAmount = strOwnedSharesAmount;
            this.ownedSharesAmount = 0.0;
        }
    }

    /**
     * Get strDividendAmount
     * @return String
     * @author thuydtn
     */
    public String getStrDividendAmount() {
        return strDividendAmount;
    }

    /**
     * Set strDividendAmount
     * @param   strDividendAmount
     *          type String
     * @return
     * @author  thuydtn
     * @throws ParseException 
     */
    public void setStrDividendAmount(String strDividendAmount) throws ParseException {
        if(strDividendAmount != null && !strDividendAmount.trim().isEmpty()){
            this.strDividendAmount = strDividendAmount;
            NumberFormat numberFormat = NumberFormat.getInstance();
            this.dividendAmount = numberFormat.parse(strDividendAmount).doubleValue();
        }else{
            this.strDividendAmount = strDividendAmount;
            this.dividendAmount = 0.0;
        }
    }

    /**
     * Get url
     * @return String
     * @author thuydtn
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set url
     * @param   url
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get createDate
     * @return Date
     * @author thuydtn
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Set createDate
     * @param   createDate
     *          type Date
     * @return
     * @author  thuydtn
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Get processId
     * @return Long
     * @author thuydtn
     */
    public Long getProcessId() {
        return processId;
    }

    /**
     * Set processId
     * @param   processId
     *          type Long
     * @return
     * @author  thuydtn
     */
    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    /**
     * Get comment
     * @return String
     * @author tuanh
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set comment
     * @param   comment
     *          type String
     * @return
     * @author  tuanh
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Get referenceId
     * @return Long
     * @author tuanh
     */
    public Long getReferenceId() {
        return referenceId;
    }

    /**
     * Set referenceId
     * @param   referenceId
     *          type Long
     * @return
     * @author  tuanh
     */
    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * Get referenceType
     * @return String
     * @author tuanh
     */
    public String getReferenceType() {
        return referenceType;
    }

    /**
     * Set referenceType
     * @param   referenceType
     *          type String
     * @return
     * @author  tuanh
     */
    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    /**
     * 
     * @return
     */
	public Long getSortOrder() {
		return sortOrder;
	}

	/**
	 * 
	 * @param sortOrder
	 */
	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}
}
