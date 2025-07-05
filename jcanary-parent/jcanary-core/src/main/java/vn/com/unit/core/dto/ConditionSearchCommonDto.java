package vn.com.unit.core.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ConditionSearchCommonDto implements Serializable {

    private static final long serialVersionUID = -5302701023517336629L;

    @JsonIgnore
    private Integer page;

    private Integer pageSize;

    private String jsonSearch;

    private String sessionText;

    private boolean isExport = false;

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

    public boolean isExport() {
        return isExport;
    }

    public void setExport(boolean isExport) {
        this.isExport = isExport;
    }
}
