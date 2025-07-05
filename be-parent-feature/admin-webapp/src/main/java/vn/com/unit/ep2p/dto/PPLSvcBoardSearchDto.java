/*******************************************************************************
 * Class        :SvcBoardSearchDto
 * Created date :2019/04/22
 * Lasted date  :2019/04/22
 * Author       :HungHT
 * Change log   :2019/04/22:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.List;

import vn.com.unit.common.dto.Select2Dto;

/**
 * SvcBoardSearchDto
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class PPLSvcBoardSearchDto {

    private Long companyId;
    private Long categoryId;
    private List<Long> companyList;
    private List<Select2Dto> categoryList;
    private String deviceType;
    private Integer isPaging;
    private String filterType;
    private Long formId;

    /**
     * Get companyId
     * 
     * @return Long
     * @author HungHT
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * Set companyId
     * 
     * @param companyId
     *            type Long
     * @return
     * @author HungHT
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * Get categoryId
     * 
     * @return Long
     * @author HungHT
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * Set categoryId
     * 
     * @param categoryId
     *            type Long
     * @return
     * @author HungHT
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Get categoryList
     * 
     * @return List<Select2Dto>
     * @author HungHT
     */
    public List<Select2Dto> getCategoryList() {
        return categoryList;
    }

    /**
     * Set categoryList
     * 
     * @param categoryList
     *            type List<Select2Dto>
     * @return
     * @author HungHT
     */
    public void setCategoryList(List<Select2Dto> categoryList) {
        this.categoryList = categoryList;
    }

    /**
     * Get deviceType
     * 
     * @return String
     * @author HungHT
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * Set deviceType
     * 
     * @param deviceType
     *            type String
     * @return
     * @author HungHT
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * Get isPaging
     * 
     * @return Integer
     * @author HungHT
     */
    public Integer getIsPaging() {
        return isPaging == null ? 1 : isPaging;
    }

    /**
     * Set isPaging
     * 
     * @param isPaging
     *            type Integer
     * @return
     * @author HungHT
     */
    public void setIsPaging(Integer isPaging) {
        this.isPaging = isPaging;
    }

	public List<Long> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<Long> companyList) {
		this.companyList = companyList;
	}

	/**
	 * Get filterType
	 * @return String
	 * @author taitt
	 */
	public String getFilterType() {
		return filterType;
	}

	/**
	 * Set filterType
	 * @param   filterType
	 *          type String
	 * @return
	 * @author  taitt
	 */
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

}