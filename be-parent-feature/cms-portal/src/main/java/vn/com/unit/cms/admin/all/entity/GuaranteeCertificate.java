/*******************************************************************************
 * Class        ：GuaranteeCertificate
 * Created date ：2017/08/24
 * Lasted date  ：2017/08/24
 * Author       ：hoangnp
 * Change log   ：2017/08/24：01-00 hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * GuaranteeCertificate
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */

@Table(name = "m_guarantee_certificate")
public class GuaranteeCertificate extends AbstractTracking {
	
	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_GUARANTEE_CERTIFICATE")
	private Long id;
	
	@Column(name = "certificate_number")
	private String certificateNumber;
	
	@Column(name = "certificate_type")
	private String certificateType;
	
	@Column(name = "issue_date")
	private Date issueDate;
	
	@Column(name = "guarantee_amount")
	private BigDecimal guaranteeAmount;
	
	@Column(name = "guarantee_certificate_duration")
	private int guaranteeCertificateDuration;
	
	@Column(name = "guarantee_certificate_duration_type")
	private String guranteeCertificateDurationType;
	
	@Column(name = "guarantee")
	private String guarantee;
	
	@Column(name = "beneficiary")
	private String beneficiary;

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
	 * @return
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
     * @return int
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
	 * getGuranteeCertificateDurationType
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getGuranteeCertificateDurationType() {
		return guranteeCertificateDurationType;
	}

	/**
	 * setGuranteeCertificateDurationType
	 *
	 * @param guranteeCertificateDurationType
	 * @author hoangnp
	 */
	public void setGuranteeCertificateDurationType(String guranteeCertificateDurationType) {
		this.guranteeCertificateDurationType = guranteeCertificateDurationType;
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
}
