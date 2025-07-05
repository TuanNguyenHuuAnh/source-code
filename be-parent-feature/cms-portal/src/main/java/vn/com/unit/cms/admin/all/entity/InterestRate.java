package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import vn.com.unit.cms.core.entity.AbstractTracking;

@Table(name = "m_interest_rate")
public class InterestRate extends AbstractTracking{

	@Id
    @Column(name = "id")
	@PrimaryKey(generationType = GenerationType.APPLICATION)
	private Long interestId;
	
	@Id
    @Column(name = "currency_id")
	@PrimaryKey(generationType = GenerationType.APPLICATION)
	private Long currencyId;
	
	@Id
    @Column(name = "m_term_id")
	@PrimaryKey(generationType = GenerationType.APPLICATION)
	private Long termId;
	
	@Id
    @Column(name = "m_city_id")
	@PrimaryKey(generationType = GenerationType.APPLICATION)
	private Long cityId;
	
	@Column(name = "display_date")
	private Date displayDate;
	
	@Column(name = "value")
	private String value;

	/**
	 * get interest id
	 * @return
	 */
	public Long getInterestId() {
		return interestId;
	}

	/**
	 * set interest id
	 * @param interestId
	 */
	public void setInterestId(Long interestId) {
		this.interestId = interestId;
	}

	/**
	 * get currency id
	 * @return
	 */
	public Long getCurrencyId() {
		return currencyId;
	}

	/**
	 * set currency id
	 * @param currencyId
	 */
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	/**
	 * get term id
	 * @return
	 */
	public Long getTermId() {
		return termId;
	}

	/**
	 * set term id
	 * @param termId
	 */
	public void setTermId(Long termId) {
		this.termId = termId;
	}
	
	/**
	 * get city id
	 * @return
	 */
	public Long getCityId() {
		return cityId;
	}

	/**
	 * set city id
	 * @param cityId
	 */
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	/**
	 * get display date
	 * @return
	 */
	public Date getDisplayDate() {
		return displayDate;
	}

	/**
	 * set display date
	 * @param displayDate
	 */
	public void setDisplayDate(Date displayDate) {
		this.displayDate = displayDate;
	}

	/**
	 * get value
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * set value
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
