package vn.com.unit.cms.admin.all.dto;
import vn.com.unit.cms.core.entity.AbstractTracking;

public class JobTypeSubDto extends AbstractTracking{
	/** id **/
	private Long id;
	
	/** code **/
	private String code;
	
	/** m_type_id **/
	private Long m_type_id;
	
	/** description **/
	private String description;
	
	/** note **/
	private String note;
	
	/** sort **/
	private Long sort;
	
	/** name **/
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
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

	public Long getM_type_id() {
		return m_type_id;
	}

	public void setM_type_id(Long m_type_id) {
		this.m_type_id = m_type_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
