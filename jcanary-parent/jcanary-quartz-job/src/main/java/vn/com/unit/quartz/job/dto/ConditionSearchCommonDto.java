package vn.com.unit.quartz.job.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ConditionSearchCommonDto {

	@JsonIgnore
	private Integer page;
	
	private Integer pageSize;
	
	private String jsonSearch;
	
    private String sessionText;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getJsonSearch() {
		return jsonSearch;
	}

	public void setJsonSearch(String jsonSearch) {
		this.jsonSearch = jsonSearch;
	}

	public String getSessionText() {
		return sessionText;
	}

	public void setSessionText(String sessionText) {
		this.sessionText = sessionText;
	}
}
