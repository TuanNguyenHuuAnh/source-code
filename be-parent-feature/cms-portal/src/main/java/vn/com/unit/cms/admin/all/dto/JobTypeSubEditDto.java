package vn.com.unit.cms.admin.all.dto;

import java.util.List;

public class JobTypeSubEditDto {
	
	/** id **/
	private Long id;
	
	/** code **/
	private String code;
	
	/** m_type_id **/
	private Long typeId;
	
	/** description **/
	private String description;
	
	/** note **/
	private String note;
	
	/** sort **/
	private Long sort;
	 /** JobTypeSubLanguageDtoList */
    private List<JobTypeSubLanguageDto> jobTypeSubLanguageList;

    /** **/
    private List<JobTypeSubDto> jobTypeSubDtosTitleList;
    public List<JobTypeSubDto> getJobTypeSubDtosTitleList() {
		return jobTypeSubDtosTitleList;
	}

	public void setJobTypeSubDtosTitleList(List<JobTypeSubDto> jobTypeSubDtosTitleList) {
		this.jobTypeSubDtosTitleList = jobTypeSubDtosTitleList;
	}

	/** pageUrl */
    private String pageUrl;

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

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public List<JobTypeSubLanguageDto> getJobTypeSubLanguageList() {
		return jobTypeSubLanguageList;
	}

	public void setJobTypeSubLanguageList(List<JobTypeSubLanguageDto> jobTypeSubLanguageList) {
		this.jobTypeSubLanguageList = jobTypeSubLanguageList;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
}
