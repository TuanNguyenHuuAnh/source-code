package vn.com.unit.cms.admin.all.entity;

import javax.persistence.Lob;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * 
 * Job Type Language
 * 
 * @author hand
 *
 */
@Table(name = "m_job_type_detail_language")
public class JobTypeDetailLanguage extends AbstractTracking {

	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_JOB_TYPE_DETAIL_LANGUAGE")
	private Long id;

	@Column(name = "m_language_code")
	private String languageCode;

	@Column(name = "m_type_detail")
	private Long typeId;

	@Column(name = "title")
	private String title;

	@Lob
	private byte[] description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public byte[] getDescription() {
		return description;
	}

	public void setDescription(byte[] description) {
		this.description = description;
	}
}
