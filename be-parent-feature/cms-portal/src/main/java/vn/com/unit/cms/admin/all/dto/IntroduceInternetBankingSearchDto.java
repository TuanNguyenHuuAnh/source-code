/*******************************************************************************
 * Class        ：IntroduceInternetBankingSearchDto
 * Created date ：2017/08/23
 * Lasted date  ：2017/08/23
 * Author       ：hoangnp
 * Change log   ：2017/08/23：01-00 ：hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

/**
 * IntroduceInternetBankingSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */
public class IntroduceInternetBankingSearchDto {
	/** code */
	private String code;

	/** name */
	private String name;
	
	/** title*/
	private String title;
	
	/** introductionType*/
	private String introductionType;
	
	/** title detail*/
	private String titleDetail;
	
	/** languageCode*/
    private String languageCode;
    
    /** customerTypeName */
    private String customerTypeName;
    
    /** categoryName */
    private String categoryName;
    
    /** url*/
    private String url;
    
    /** fieldValues */
    private List<String> fieldValues;
    
    /** fieldSearch */
    private String fieldSearch;
    
    /** description*/
    private String description;

    private Integer pageSize;
    
    /**
     * getCode
     *
     * @return String
     * @author hoangnp
     */
	public String getCode() {
		return code;
	}

	/**
	 * setCode
	 *
	 * @param code
	 * @author hoangnp
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * getName
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getName() {
		return name;
	}

	/**
	 * setName
	 *
	 * @param name
	 * @author hoangnp
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getTitle
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * setTitle
	 *
	 * @param title
	 * @author hoangnp
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * getLanguageCode
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * setLanguageCode
	 *
	 * @param languageCode
	 * @author hoangnp
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * getCustomerTypeName
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getCustomerTypeName() {
		return customerTypeName;
	}

	/**
	 * setCustomerTypeName
	 *
	 * @param customerTypeName
	 * @author hoangnp
	 */
	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}

	/**
	 * getCategoryName
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * setCategoryName
	 *
	 * @param categoryName
	 * @author hoangnp
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * getFieldValues
	 *
	 * @return List<String>
	 * @author hoangnp
	 */
	public List<String> getFieldValues() {
		return fieldValues;
	}

	/**
	 * setFieldValues
	 *
	 * @param fieldValues
	 * @author hoangnp
	 */
	public void setFieldValues(List<String> fieldValues) {
		this.fieldValues = fieldValues;
	}

	/**
	 * getFieldSearch
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getFieldSearch() {
		return fieldSearch;
	}

	/**
	 * setFieldSearch
	 *
	 * @param fieldSearch
	 * @author hoangnp
	 */
	public void setFieldSearch(String fieldSearch) {
		this.fieldSearch = fieldSearch;
	}

	/**
	 * getDescription
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * setDescription
	 *
	 * @param description
	 * @author hoangnp
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * getUrl
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * setUrl
	 *
	 * @param url
	 * @author hoangnp
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * getTitleDetail
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getTitleDetail() {
		return titleDetail;
	}

	/**
	 * setTitleDetail
	 *
	 * @param titleDetail
	 * @author hoangnp
	 */
    public void setTitleDetail(String titleDetail) {
        this.titleDetail = titleDetail;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getIntroductionType() {
        return introductionType;
    }

    public void setIntroductionType(String introductionType) {
        this.introductionType = introductionType;
    }

}
