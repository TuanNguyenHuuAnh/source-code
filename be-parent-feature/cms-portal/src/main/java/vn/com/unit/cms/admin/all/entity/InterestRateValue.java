
package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;

@Table(name = "m_interest_rate_value")
public class InterestRateValue extends AbstractTracking {

	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_INTEREST_RATE_VALUE")
	private Long id;

	@Column(name = "interest_rate_type")
	private String interestRateType;

	@Column(name = "language_code")
	private String languageCode;

	@Column(name = "value01")
	private String value01;

	@Column(name = "value02")
	private String value02;

	@Column(name = "value03")
	private String value03;

	@Column(name = "value04")
	private String value04;

	@Column(name = "value05")
	private String value05;

	@Column(name = "value06")
	private String value06;

	@Column(name = "value07")
	private String value07;

	@Column(name = "value08")
	private String value08;

	@Column(name = "value09")
	private String value09;

	@Column(name = "value10")
	private String value10;

	@Column(name = "m_customer_type_id")
	private Long customerTypeId;

	@Column(name = "process_id")
	private Long processId;

	@Column(name = "status")
	private Integer status;

	@Column(name = "note")
	private String note;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInterestRateType() {
		return interestRateType;
	}

	public void setInterestRateType(String interestRateType) {
		this.interestRateType = interestRateType;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public Long getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(Long customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public String getValue01() {
		return value01;
	}

	public void setValue01(String value01) {
		this.value01 = value01;
	}

	public String getValue02() {
		return value02;
	}

	public void setValue02(String value02) {
		this.value02 = value02;
	}

	public String getValue03() {
		return value03;
	}

	public void setValue03(String value03) {
		this.value03 = value03;
	}

	public String getValue04() {
		return value04;
	}

	public void setValue04(String value04) {
		this.value04 = value04;
	}

	public String getValue05() {
		return value05;
	}

	public void setValue05(String value05) {
		this.value05 = value05;
	}

	public String getValue06() {
		return value06;
	}

	public void setValue06(String value06) {
		this.value06 = value06;
	}

	public String getValue07() {
		return value07;
	}

	public void setValue07(String value07) {
		this.value07 = value07;
	}

	public String getValue08() {
		return value08;
	}

	public void setValue08(String value08) {
		this.value08 = value08;
	}

	public String getValue09() {
		return value09;
	}

	public void setValue09(String value09) {
		this.value09 = value09;
	}

	public String getValue10() {
		return value10;
	}

	public void setValue10(String value10) {
		this.value10 = value10;
	}

	/**
	 * @return the processId
	 */
	public Long getProcessId() {
		return processId;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

}
