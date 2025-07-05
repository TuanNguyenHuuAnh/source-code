package vn.com.unit.cms.admin.all.dto;
import vn.com.unit.cms.core.entity.AbstractTracking;

public class JobTypeSubLanguageDto extends AbstractTracking{
	
	/** id **/
	private Long id;
	
	/** m_language_id **/
	private String m_language_code;
	
	public String getM_language_code() {
		return m_language_code;
	}

	public void setM_language_code(String m_language_code) {
		this.m_language_code = m_language_code;
	}

	/** m_type_sub_id **/
	private Long m_type_sub_id;
	
	/** name **/
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

	
}
