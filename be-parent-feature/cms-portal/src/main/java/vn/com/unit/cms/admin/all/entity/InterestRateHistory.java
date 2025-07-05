package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import vn.com.unit.cms.core.entity.AbstractTracking;

@Table(name = "m_interest_rate_history")
public class InterestRateHistory extends AbstractTracking{

	@Id
    @Column(name = "id")
	@PrimaryKey(generationType = GenerationType.APPLICATION)
	private Long id;
	
	@Id
    @Column(name = "m_interest_rate_id")
	@PrimaryKey(generationType = GenerationType.APPLICATION)
	private Long interestId;
	
	@Id
    @Column(name = "m_city_id")
	@PrimaryKey(generationType = GenerationType.APPLICATION)
	private Long cityId;
	
	@Column(name = "update_date_time")
	private Date updateDateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInterestId() {
		return interestId;
	}

	public void setInterestId(Long interestId) {
		this.interestId = interestId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
}
