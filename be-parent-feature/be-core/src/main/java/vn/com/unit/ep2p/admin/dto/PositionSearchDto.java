/*******************************************************************************
 * Class        PositionSearchDto
 * Created date 2018/08/08
 * Lasted date  2018/08/08
 * Author       KhoaNA
 * Change log   2018/08/0801-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;

import java.util.List;

import vn.com.unit.core.dto.ConditionSearchCommonDto;

/**
 * PositionSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class PositionSearchDto extends ConditionSearchCommonDto  {
    /** fieldSearch */
    private String fieldSearch;

    /** fieldValues */
    private List<String> fieldValues;
    
    /** code */
    private String code;

    /** name */
    private String name;
    
    /** nameAbv */
    private String nameAbv;

    /** description */
    private String description;
    
    /** url */
    private String url;
    
    private Long companyId;
    
    private String companyName;
    
    private boolean companyAdmin;
    
    private List<Long> companyIdList;
    
    private List<String> listSearchKeyIds;
    
    private String searchKeyIds;

	public String getFieldSearch() {
		return fieldSearch;
	}

	public void setFieldSearch(String fieldSearch) {
		this.fieldSearch = fieldSearch;
	}

	public List<String> getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(List<String> fieldValues) {
		this.fieldValues = fieldValues;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameAbv() {
		return nameAbv;
	}

	public void setNameAbv(String nameAbv) {
		this.nameAbv = nameAbv;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public boolean isCompanyAdmin() {
		return companyAdmin;
	}

	public void setCompanyAdmin(boolean companyAdmin) {
		this.companyAdmin = companyAdmin;
	}

	public List<Long> getCompanyIdList() {
		return companyIdList;
	}

	public void setCompanyIdList(List<Long> companyIdList) {
		this.companyIdList = companyIdList;
	}

	public List<String> getListSearchKeyIds() {
		return listSearchKeyIds;
	}

	public void setListSearchKeyIds(List<String> listSearchKeyIds) {
		this.listSearchKeyIds = listSearchKeyIds;
	}

	public String getSearchKeyIds() {
		return searchKeyIds;
	}

	public void setSearchKeyIds(String searchKeyIds) {
		this.searchKeyIds = searchKeyIds;
	}
	
}
