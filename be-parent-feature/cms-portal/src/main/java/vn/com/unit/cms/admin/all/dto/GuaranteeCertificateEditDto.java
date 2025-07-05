/*******************************************************************************
 * Class        ：GuaranteeCertificateEditDto
 * Created date ：2017/08/24
 * Lasted date  ：2017/08/24
 * Author       ：hoangnp
 * Change log   ：2017/08/24：01-00 hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * GuaranteeCertificateEditDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */

public class GuaranteeCertificateEditDto {
	
    /** id*/
	private Long id;
	
	/** certificateNumber*/
	private String certificateNumber;
	
	/** certificateType*/
	private String certificateType;
	
	/** issueDate*/
	private Date issueDate;
	
	/** guaranteeAmount*/
	private BigDecimal guaranteeAmount;
	
	/** guaranteeAmount String*/
	private String guaranteeAmountStr;
	
	/** guaranteeCertificateDuration*/
	private int guaranteeCertificateDuration;
	
	/** guaranteeCertificateDurationType*/
	private String guaranteeCertificateDurationType;
	
	/** guarantee*/
	private String guarantee;
	
	/** beneficiary*/
	private String beneficiary;
	
	/** url*/
	private String url;

	/**
	 * getId
	 *
	 * @return Long
	 * @author hoangnp
	 */
	public Long getId() {
		return id;
	}

	/**
	 * setId
	 *
	 * @param id
	 * @author hoangnp
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * getCertificateNumber
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getCertificateNumber() {
		return certificateNumber;
	}

	/**
	 * setCertificateNumber
	 *
	 * @param certificateNumber
	 * @author hoangnp
	 */
	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	/**
	 * getCertificateType
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getCertificateType() {
		return certificateType;
	}

	/**
	 * setCertificateType
	 *
	 * @param certificateType
	 * @author hoangnp
	 */
	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	/**
	 * getIssueDate
	 *
	 * @return date
	 * @author hoangnp
	 */
	public Date getIssueDate() {
		return issueDate;
	}

	/**
	 * setIssueDate
	 *
	 * @param issueDate
	 * @author hoangnp
	 */
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	/**
	 * getGuaranteeAmount
	 *
	 * @return BigDecimal
	 * @author hoangnp
	 */
    public BigDecimal getGuaranteeAmount() {
        return guaranteeAmount;
    }

    /**
     * setGuaranteeAmount
     *
     * @param guaranteeAmount
     * @author hoangnp
     */
    public void setGuaranteeAmount(BigDecimal guaranteeAmount) {
        this.guaranteeAmount = guaranteeAmount;
    }

    /**
     * getGuaranteeCertificateDuration
     *
     * @return String
     * @author hoangnp
     */
    public int getGuaranteeCertificateDuration() {
		return guaranteeCertificateDuration;
	}

    /**
     * setGuaranteeCertificateDuration
     *
     * @param guaranteeCertificateDuration
     * @author hoangnp
     */
	public void setGuaranteeCertificateDuration(int guaranteeCertificateDuration) {
		this.guaranteeCertificateDuration = guaranteeCertificateDuration;
	}

	/**
	 * getGuaranteeCertificateDurationType
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getGuaranteeCertificateDurationType() {
		return guaranteeCertificateDurationType;
	}

	/**
	 * setGuaranteeCertificateDurationType
	 *
	 * @param guaranteeCertificateDurationType
	 * @author hoangnp
	 */
	public void setGuaranteeCertificateDurationType(String guaranteeCertificateDurationType) {
		this.guaranteeCertificateDurationType = guaranteeCertificateDurationType;
	}

	/**
	 * getGuarantee
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getGuarantee() {
		return guarantee;
	}

	/**
	 * setGuarantee
	 *
	 * @param guarantee
	 * @author hoangnp
	 */
	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}

	/**
	 * getBeneficiary
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getBeneficiary() {
		return beneficiary;
	}

	/**
	 * setBeneficiary
	 *
	 * @param beneficiary
	 * @author hoangnp
	 */
	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	/**
	 * getUrl
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * setUrl
	 *
	 * @param url
	 * @author hoangnp
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * getGuaranteeAmountStr
	 *
	 * @return String
	 * @author hoangnp
	 */
    public String getGuaranteeAmountStr() {
        return guaranteeAmountStr;
    }

    /**
     * setGuaranteeAmountStr
     *
     * @param guaranteeAmountStr
     * @author hoangnp
     */
    public void setGuaranteeAmountStr(String guaranteeAmountStr) {
        this.guaranteeAmountStr = guaranteeAmountStr;
    }

	
	
	

}
