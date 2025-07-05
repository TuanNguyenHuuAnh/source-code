/**
 * 
 */
package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * @author phunghn
 *
 */
@Table(name = "t_exchange_rate_history")
public class ExchnageRateHistory extends AbstractTracking{
	@Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_T_EXCHANGE_RATE_HISTORY")
	private Long id;
	
	@Column(name = "m_exchange_rate_id")
	private Long mRxchangeRateId;
	
	@Column(name = "m_currency_id")
	private Long mCurrencyId;
	
	@Column(name = "update_date_time")
	private Date updateDateTime;
	
	@Column(name = "buying")
	private String buying;
	
	@Column(name = "tranfers")
	private String tranfers;
	
	@Column(name = "selling")
	private String selling;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getmRxchangeRateId() {
		return mRxchangeRateId;
	}

	public void setmRxchangeRateId(Long mRxchangeRateId) {
		this.mRxchangeRateId = mRxchangeRateId;
	}

	public Long getmCurrencyId() {
		return mCurrencyId;
	}

	public void setmCurrencyId(Long mCurrencyId) {
		this.mCurrencyId = mCurrencyId;
	}

	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public String getBuying() {
		return buying;
	}

	public void setBuying(String buying) {
		this.buying = buying;
	}

	public String getTranfers() {
		return tranfers;
	}

	public void setTranfers(String tranfers) {
		this.tranfers = tranfers;
	}

	public String getSelling() {
		return selling;
	}

	public void setSelling(String selling) {
		this.selling = selling;
	}
}
