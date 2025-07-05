/*******************************************************************************
 * Class        ShareHolderSearchDto
 * Created date 2017/02/15
 * Lasted date  2017/02/15
 * Author       thuydtn
 * Change log   2017/02/1501-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * ShareHolderSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public class CommonSearchDto {

	private List<String> searchKeyIds;

	private String searchValue;

	private Long customerTypeId;

	private Long categoryId;

	private Long parentId;

	private Long typeId;

	private Integer pageSize;

	private Integer enabled;

	private String status;

	private String code;

	private String name;

	private String typeText;

	private String note;

	private String statusName;

	private String languageCode;

	private Long processId;

	private String customerAlias;

	private String token;
	
	private Integer typeOfMain;
	
	private Integer pictureIntroduction;
	
	private String url;

	public CommonSearchDto() {
		this.searchKeyIds = new ArrayList<String>();
	}

	/**
	 * Get searchKeyId
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public List<String> getSearchKeyIds() {
		return searchKeyIds;
	}

	/**
	 * Set searchKeyId
	 * 
	 * @param searchKeyId type String
	 * @return
	 * @author thuydtn
	 */
	public void setSearchKeyIds(List<String> searchKeyIds) {
		this.searchKeyIds = searchKeyIds;
	}

	/**
	 * Get searchValue
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getSearchValue() {
		return searchValue;
	}

	/**
	 * Set searchValue
	 * 
	 * @param searchValue type String
	 * @return
	 * @author thuydtn
	 */
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getTypeText() {
		return typeText;
	}

	public void setTypeText(String typeText) {
		this.typeText = typeText;
	}

	public Long getCustomerTypeId() {
		return customerTypeId;
	}

	/**
	 * @return the processId
	 */
	protected Long getProcessId() {
		return processId;
	}

	/**
	 * @param processId the processId to set
	 */
	protected void setProcessId(Long processId) {
		this.processId = processId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return the languageCode
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * @param languageCode the languageCode to set
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * @param customerTypeId the customerTypeId to set
	 * @author taitm
	 */
	public synchronized void setCustomerTypeId(Long customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	/**
	 * @return the customerAlias
	 */
	public String getCustomerAlias() {
		return customerAlias;
	}

	/**
	 * @param customerAlias the customerAlias to set
	 */
	public void setCustomerAlias(String customerAlias) {
		this.customerAlias = customerAlias;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

    /**
     * @return the typeOfMain
     * @author taitm
     */
    public Integer getTypeOfMain() {
        return typeOfMain;
    }

    /**
     * @param typeOfMain the typeOfMain to set
     * @author taitm
     */
    public void setTypeOfMain(Integer typeOfMain) {
        this.typeOfMain = typeOfMain;
    }

    /**
     * @return the pictureIntroduction
     * @author taitm
     */
    public Integer getPictureIntroduction() {
        return pictureIntroduction;
    }

    /**
     * @param pictureIntroduction
     *            the pictureIntroduction to set
     * @author taitm
     */
    public void setPictureIntroduction(Integer pictureIntroduction) {
        this.pictureIntroduction = pictureIntroduction;
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}