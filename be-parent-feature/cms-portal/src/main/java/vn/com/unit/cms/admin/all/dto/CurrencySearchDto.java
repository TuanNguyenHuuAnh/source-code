package vn.com.unit.cms.admin.all.dto;

import java.util.List;

public class CurrencySearchDto {

	/** code */
	private String code;
	
	/** name */
	private String name;

	/** title */
	private String title;
	
	/** language code */
	private String languageCode;

	/** selectedField */
	private List<String> selectedField;
	
	/** searchValue */
	private String searchKeyWord;
	
	/** url */
	private String url;
	
	private String status;
	
	/** pageSize */
	private Integer pageSize;

	/**
	 * get code
	 * @return
	 */
	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * set code
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * get title
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * set title
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * get language code
	 * @return
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * set language code
	 * @param languageCode
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	
	/**
	 * set selected field
	 * @return
	 */
	public List<String> getSelectedField() {
		return selectedField;
	}

	/**
	 * set selected field
	 * @param selectedField
	 */
	public void setSelectedField(List<String> selectedField) {
		this.selectedField = selectedField;
	}

	/**
	 * get search keyword
	 * @return
	 */
	public String getSearchKeyWord() {
		return searchKeyWord;
	}

	/**
	 * set search keyword
	 * @param searchKeyWord
	 */
	public void setSearchKeyWord(String searchKeyWord) {
		this.searchKeyWord = searchKeyWord;
	}

	/**
	 * get url
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * set url
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

    public String getStatus() {
        return status;
    }

    
    public void setStatus(String status) {
        this.status = status;
    }

	/**
	 * get pageSize
	 * @return
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * set pageSize
	 * @param pageSize
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
