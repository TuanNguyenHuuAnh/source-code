/*******************************************************************************
 * Class        RepositorySearchDto
 * Created date 2018/08/08
 * Lasted date  2018/08/08
 * Author       KhoaNA
 * Change log   2018/08/08 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.dto;

import java.util.List;

/**
 * RepositorySearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class RepositorySearchDto {
    /** fieldSearch */
    private String fieldSearch;

    /** fieldValues */
    private List<String> fieldValues;
    
    /** code */
    private String code;

    /** name */
    private String name;
    
    /** physicalPath */
    private String physicalPath;

    /** subFolderRule */
    private String subFolderRule;
    
    /** url */
    private String url;

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
	
	public String getPhysicalPath() {
		return physicalPath;
	}

	public void setPhysicalPath(String physicalPath) {
		this.physicalPath = physicalPath;
	}

	public String getSubFolderRule() {
		return subFolderRule;
	}

	public void setSubFolderRule(String subFolderRule) {
		this.subFolderRule = subFolderRule;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
