/*******************************************************************************
 * Class        ：ShareHolder
 * Created date ：2017/02/14
 * Lasted date  ：2017/02/14
 * Author       ：thuydtn
 * Change log   ：2017/02/14：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.admin.all.dto.ShareholderDto;
import vn.com.unit.cms.core.entity.AbstractTracking;

@Table(name = "m_shareholders")
public class ShareHolder extends AbstractTracking{
    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_SHAREHOLDERS")
    private Long id;

    // share holder code
    @Column(name = "code")
    private String code;
    // share holder name
    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;
    // human identifier
    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "date_of_issue")
    private Date dateOfIssue;

    @Column(name = "place_of_issue")
    private String placeOfIssue;

    @Column(name = "owned_shares_amount")
    private double ownedSharesAmount;

    @Column(name = "dividend_amount")
    private double dividendAmount;
    
    @Column(name="sort")
    private Long sort;

    @Column(name = "status")
    private String status;
    
    @Column(name = "process_id")
    private Long processId;
    
    @Column(name = "process_intance_id")
    private Long processIntanceId;
    
    @Column(name = "owner_id")
    private Long ownerId;
    
    @Column(name = "owner_section_id")
    private Long ownerSectionId;
    
    @Column(name = "owner_branch_id")
    private Long ownerBranchId;
    
    @Column(name = "assigner_id")
    private Long assignerId;
    
    @Column(name = "assigner_section_id")
    private Long assignerSectionId;
    
    @Column(name = "assigner_branch_id")
    private Long assignerBranchId;

    public ShareHolder() {
        super();
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
     * @param id
     *            type Long
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
     * @param code
     *            type String
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
     * @param name
     *            type String
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
     * @param address
     *            type String
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
     * @param idNumber
     *            type String
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
    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    /**
     * Set dateOfIssue
     * 
     * @param dateOfIssue
     *            type String
     * @return
     * @author thuydtn
     */
    public void setDateOfIssue(Date dateOfIssue) {
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
     * @param placeOfIssue
     *            type String
     * @return
     * @author thuydtn
     */
    public void setPlaceOfIssue(String placeOfIssue) {
        this.placeOfIssue = placeOfIssue;
    }

    /**
     * Get ownedSharesAmount
     * 
     * @return double
     * @author thuydtn
     */
    public double getOwnedSharesAmount() {
        return ownedSharesAmount;
    }

    /**
     * Set ownedSharesAmount
     * 
     * @param ownedSharesAmount
     *            type double
     * @return
     * @author thuydtn
     */
    public void setOwnedSharesAmount(double ownedSharesAmount) {
        this.ownedSharesAmount = ownedSharesAmount;
    }

    /**
     * Get dividendAmount
     * 
     * @return double
     * @author thuydtn
     */
    public double getDividendAmount() {
        return dividendAmount;
    }

    /**
     * Set dividendAmount
     * 
     * @param dividendAmount
     *            type double
     * @return
     * @author thuydtn
     */
    public void setDividendAmount(double dividendAmount) {
        this.dividendAmount = dividendAmount;
    }

    /**
     * Get status
     * 
     * @return String
     * @author thuydtn
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set status
     * 
     * @param status
     *            type String
     * @return
     * @author thuydtn
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Copy update properties from dto
     * 
     * @param dto ShareHolderDto
     *            
     * @return
     * @author thuydtn
     */
    public void copyDtoProperties(ShareholderDto dto) {
        this.setName(dto.getName());
        this.setAddress(dto.getAddress());
        this.setIdNumber(dto.getIdNumber());
        this.setDividendAmount(dto.getDividendAmount());
        this.setOwnedSharesAmount(dto.getOwnedSharesAmount());
        this.setDateOfIssue(dto.getDateOfIssue());
        this.setPlaceOfIssue(dto.getPlaceOfIssue());
        this.setSort(dto.getSortOrder());
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
     * Get ownerId
     * @return Long
     * @author thuydtn
     */
    public Long getOwnerId() {
        return ownerId;
    }

    /**
     * Set ownerId
     * @param   ownerId
     *          type Long
     * @return
     * @author  thuydtn
     */
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Get ownerSectionId
     * @return Long
     * @author thuydtn
     */
    public Long getOwnerSectionId() {
        return ownerSectionId;
    }

    /**
     * Set ownerSectionId
     * @param   ownerSectionId
     *          type Long
     * @return
     * @author  thuydtn
     */
    public void setOwnerSectionId(Long ownerSectionId) {
        this.ownerSectionId = ownerSectionId;
    }

    /**
     * Get ownerBranchId
     * @return Long
     * @author thuydtn
     */
    public Long getOwnerBranchId() {
        return ownerBranchId;
    }

    /**
     * Set ownerBranchId
     * @param   ownerBranchId
     *          type Long
     * @return
     * @author  thuydtn
     */
    public void setOwnerBranchId(Long ownerBranchId) {
        this.ownerBranchId = ownerBranchId;
    }

    /**
     * Get assignerId
     * @return Long
     * @author thuydtn
     */
    public Long getAssignerId() {
        return assignerId;
    }

    /**
     * Set assignerId
     * @param   assignerId
     *          type Long
     * @return
     * @author  thuydtn
     */
    public void setAssignerId(Long assignerId) {
        this.assignerId = assignerId;
    }

    /**
     * Get assignerSectionId
     * @return Long
     * @author thuydtn
     */
    public Long getAssignerSectionId() {
        return assignerSectionId;
    }

    /**
     * Set assignerSectionId
     * @param   assignerSectionId
     *          type Long
     * @return
     * @author  thuydtn
     */
    public void setAssignerSectionId(Long assignerSectionId) {
        this.assignerSectionId = assignerSectionId;
    }

    /**
     * Get assignerBranchId
     * @return Long
     * @author thuydtn
     */
    public Long getAssignerBranchId() {
        return assignerBranchId;
    }

    /**
     * Set assignerBranchId
     * @param   assignerBranchId
     *          type Long
     * @return
     * @author  thuydtn
     */
    public void setAssignerBranchId(Long assignerBranchId) {
        this.assignerBranchId = assignerBranchId;
    }

    /**
     * Get processIntanceId
     * @return Long
     * @author thuydtn
     */
    public Long getProcessIntanceId() {
        return processIntanceId;
    }

    /**
     * Set processIntanceId
     * @param   processIntanceId
     *          type Long
     * @return
     * @author  thuydtn
     */
    public void setProcessIntanceId(Long processIntanceId) {
        this.processIntanceId = processIntanceId;
    }

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
}
