/*******************************************************************************
 * Class        ：GuaranteeCertificateSearchDto
 * Created date ：2017/08/24
 * Lasted date  ：2017/08/24
 * Author       ：toannt
 * Change log   ：2017/08/24：01-00 toant create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;
import java.util.Date;
import java.util.List;
/**
 * 
 * GuaranteeCertificateSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author toannt
 */
public class GuaranteeCertificateSearchDto {
	/** id */
	private Long id;

	/** Certificate Number*/
	private String certificateNumber;

	/** Certificate Type */
	private String certificateType;

	/** Issue Date*/
	private Date issueDate;
	
	/** Create Date*/
    private Date createDate;
	
	/** Guarantee Amount */
	private int guaranteeAmount;

	/**  Guarantee Certificate Duration */
	private int guaranteeCertificateDuration;

	/** Guarantee Certificate DurationType */
	private String guaranteeCertificateDurationType;

	/** Guarantee*/
	private String guarantee;
	
	private String url;

	/** fieldValues */
    private List<String> fieldValues;
    
    /** fieldSearch */
    private String fieldSearch;
    
	/** Beneficiary;*/
	private String beneficiary;

	/**
	 * getId
	 *
	 * @return
	 * @author toannt
	 */
	public Long getId() {
		return id;
	}

	/**
	 * setId
	 *
	 * @param id
	 * @author toannt
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * getCertificateNumber
	 *
	 * @return
	 * @author toannt
	 */
	public String getCertificateNumber() {
		return certificateNumber;
	}

	/**
	 * setCertificateNumber
	 *
	 * @param certificateNumber
	 * @author toannt
	 */
	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	/**
	 * getCertificateType
	 *
	 * @return
	 * @author toannt
	 */
	public String getCertificateType() {
		return certificateType;
	}

	/**
	 * setCertificateType
	 *
	 * @param certificateType
	 * @author toannt
	 */
	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	/**
	 * getIssueDate
	 *
	 * @return
	 * @author toannt
	 */
	public Date getIssueDate() {
		return issueDate;
	}

	/**
	 * setIssueDate
	 *
	 * @param issueDate
	 * @author toannt
	 */
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	/**
	 * getGuaranteeAmount
	 *
	 * @return
	 * @author toannt
	 */
	public int getGuaranteeAmount() {
		return guaranteeAmount;
	}

	/**
	 * setGuaranteeAmount
	 *
	 * @param guaranteeAmount
	 * @author toannt
	 */
	public void setGuaranteeAmount(int guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}

	/**
	 * getGuaranteeCertificateDuration
	 *
	 * @return
	 * @author toannt
	 */
	public int getGuaranteeCertificateDuration() {
		return guaranteeCertificateDuration;
	}

	/**
	 * setGuaranteeCertificateDuration
	 *
	 * @param guaranteeCertificateDuration
	 * @author toannt
	 */
	public void setGuaranteeCertificateDuration(int guaranteeCertificateDuration) {
		this.guaranteeCertificateDuration = guaranteeCertificateDuration;
	}

	/**
	 * getGuaranteeCertificateDurationType
	 *
	 * @return
	 * @author toannt
	 */
	public String getGuaranteeCertificateDurationType() {
		return guaranteeCertificateDurationType;
	}

	/**
	 * setGuaranteeCertificateDurationType
	 *
	 * @param guaranteeCertificateDurationType
	 * @author toannt
	 */
	public void setGuaranteeCertificateDurationType(String guaranteeCertificateDurationType) {
		this.guaranteeCertificateDurationType = guaranteeCertificateDurationType;
	}

	/**
	 * getGuarantee
	 *
	 * @return
	 * @author toannt
	 */
	public String getGuarantee() {
		return guarantee;
	}

	/**
	 * setGuarantee
	 *
	 * @param guarantee
	 * @author toannt
	 */
	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}

	/**
	 * getBeneficiary
	 *
	 * @return
	 * @author toannt
	 */
	public String getBeneficiary() {
		return beneficiary;
	}

	/**
	 * setBeneficiary
	 *
	 * @param beneficiary
	 * @author toannt
	 */
	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	/**
	 * getFieldValues
	 *
	 * @return
	 * @author toannt
	 */
	public List<String> getFieldValues() {
		return fieldValues;
	}

	/**
	 * setFieldValues
	 *
	 * @param fieldValues
	 * @author toannt
	 */
	public void setFieldValues(List<String> fieldValues) {
		this.fieldValues = fieldValues;
	}

	/**
	 * getFieldSearch
	 *
	 * @return
	 * @author toannt
	 */
	public String getFieldSearch() {
		return fieldSearch;
	}

	/**
	 * setFieldSearch
	 *
	 * @param fieldSearch
	 * @author toannt
	 */
	public void setFieldSearch(String fieldSearch) {
		this.fieldSearch = fieldSearch;
	}

	/**
	 * getUrl
	 *
	 * @return
	 * @author toannt
	 */
    public String getUrl() {
        return url;
    }

    /**
     * setUrl
     *
     * @param url
     * @author toannt
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * getCreateDate
     *
     * @return
     * @author toannt
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * setCreateDate
     *
     * @param createDate
     * @author toannt
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
