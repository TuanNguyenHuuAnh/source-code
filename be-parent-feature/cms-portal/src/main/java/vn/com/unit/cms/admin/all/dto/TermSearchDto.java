package vn.com.unit.cms.admin.all.dto;

import java.util.List;

public class TermSearchDto {
	
	/** code */
	private String code;
	
	/** name */
	private String name;

	/** title */
	private String title;
	
	private String status;
	
	private String description;
	
	/** language code */
	private String languageCode;

	/** selectedField */
	private List<String> selectedField;
	
	/** searchValue */
	private String searchKeyWord;
	
	/** depositsEnable */
    private String depositsEnable;

    /** loanEnable */
    private String loanEnable;
	
	/** url */
	private String url;
	
	private Integer pageSize;

	/**
	 * get code
	 * @return
	 */
	public String getCode() {
		return code;
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

    /**
     * Get description
     * @return String
     * @author tungns <tungns@unit.com.vn>
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     * @param   description
     *          type String
     * @return
     * @author  tungns <tungns@unit.com.vn>
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Get name
     * @return String
     * @author diennv
     */
	public String getName() {
		return name;
	}
	
	/**
     * Set name
     * @return String
     * @author diennv
     */
	public void setName(String name) {
		this.name = name;
	}

	public String getDepositsEnable() {
		return depositsEnable;
	}

	public void setDepositsEnable(String depositsEnable) {
		this.depositsEnable = depositsEnable;
	}

	public String getLoanEnable() {
		return loanEnable;
	}

	public void setLoanEnable(String loanEnable) {
		this.loanEnable = loanEnable;
	}


	public Integer getPageSize() {
		return pageSize;
	}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
