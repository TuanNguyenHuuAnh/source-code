package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * 
 * Job Type
 * 
 * @author hand
 *
 */
@Table(name = "m_job_type_detail")
public class JobTypeDetail extends AbstractTracking{
	
	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_JOB_TYPE_DETAIL")
	private Long id;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "m_type_id")
	private Long mTypeId;
	
	@Column(name = "m_type_sub_id")
	private Long mTypeSubId;
	
	@Column(name = "note")
	private String note;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getmTypeId() {
		return mTypeId;
	}

	public void setmTypeId(Long mTypeId) {
		this.mTypeId = mTypeId;
	}

	public Long getmTypeSubId() {
		return mTypeSubId;
	}

	public void setmTypeSubId(Long mTypeSubId) {
		this.mTypeSubId = mTypeSubId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
