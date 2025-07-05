package vn.com.unit.cms.admin.all.dto;

import java.util.List;

public class JobTypeEditDto {

	private Long id;
	
	private String code;
	
	private String description;
	
	private String colUrl;

	private Long sort;
	
	private List<JobTypeLanguageDto> jobTypeLanguageList;
	
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
	
	public String getColUrl() {
		return colUrl;
	}
	
	public void setColUrl(String colUrl) {
		this.colUrl = colUrl;
	}
	
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	public List<JobTypeLanguageDto> getJobTypeLanguageList() {
		return jobTypeLanguageList;
	}

	public void setJobTypeLanguageList(List<JobTypeLanguageDto> jobTypeLanguageList) {
		this.jobTypeLanguageList = jobTypeLanguageList;
	}
	


	
}
