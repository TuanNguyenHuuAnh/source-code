/**
 * 
 */
package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;

@Table(name = "m_exchange_rate")
public class ExchangeRate extends AbstractTracking{
	@Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.APPLICATION)
	private Long id;

	@Id
    @Column(name = "m_currency_id")
    @PrimaryKey(generationType = GenerationType.APPLICATION)
	private Long mCurrencyId;
	
	@Column(name = "display_date")
    private Date displayDate;
		
	@Column(name = "buying")
    private String buying;
	
	@Column(name = "transfer")
    private String transfer;
	
	@Column(name = "selling")
    private String selling;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getmCurrencyId() {
		return mCurrencyId;
	}

	public void setmCurrencyId(Long mCurrencyId) {
		this.mCurrencyId = mCurrencyId;
	}

	public Date getDisplayDate() {
		return displayDate;
	}

	public void setDisplayDate(Date displayDate) {
		this.displayDate = displayDate;
	}

	public String getBuying() {
		return buying;
	}

	public void setBuying(String buying) {
		this.buying = buying;
	}

	public String getTransfer() {
		return transfer;
	}

	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}

	public String getSelling() {
		return selling;
	}

	public void setSelling(String selling) {
		this.selling = selling;
	}



	
	
}
