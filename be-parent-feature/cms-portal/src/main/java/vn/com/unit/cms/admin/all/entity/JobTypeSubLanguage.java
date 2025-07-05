package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;

@Table(name = "m_job_type_sub_language")
public class JobTypeSubLanguage extends AbstractTracking{
	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_JOB_TYPE_SUB_LANGUAGE")
	private Long id;
	
	@Column(name = "m_language_code")
	private String m_language_code;
	
	@Column(name = "m_type_sub_id")
	private Long m_type_sub_id;
	
	@Column(name = "name")
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Long getM_type_sub_id() {
		return m_type_sub_id;
	}

	public void setM_type_sub_id(Long m_type_sub_id) {
		this.m_type_sub_id = m_type_sub_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getM_language_code() {
		return m_language_code;
	}

	public void setM_language_code(String m_language_code) {
		this.m_language_code = m_language_code;
	}
}
