/*******************************************************************************
 * Class        ：ProductCategorySearchDto
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

import vn.com.unit.cms.core.utils.CmsUtils;

//import vn.com.unit.util.Util;

/**
 * ProductCategorySearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class ProductCategorySearchDto {

    /** code */
    private String code;

    /** description */
    private String description;

    private String name;

    private String status;

    /** title */
    private String title;

    /** languageCode */
    private String languageCode;

    /** customerTypeName */
    private String customerTypeName;

    private Long customerId;

    private Long bannerDesktopId;

    private Long bannerMobileId;

    /** fieldValues */
    private List<String> fieldValues;

    /** fieldSearch */
    private String fieldSearch;

    /** url */
    private String url;

    private Integer pageSize;
    
    private Integer enabled;
    
    private String statusName;
    
    private String businessCode;

    /**
     * Get code
     * 
     * @return String
     * @author hand
     */
    public String getCode() {
        return code;
    }

    /**
     * Set code
     * 
     * @param code
     *            type String
     * @return
     * @author hand
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Get description
     * 
     * @return String
     * @author hand
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     * 
     * @param description
     *            type String
     * @return
     * @author hand
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get title
     * 
     * @return String
     * @author hand
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title
     * 
     * @param title
     *            type String
     * @return
     * @author hand
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get languageCode
     * 
     * @return String
     * @author hand
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Set languageCode
     * 
     * @param languageCode
     *            type String
     * @return
     * @author hand
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * Get customerTypeName
     * 
     * @return String
     * @author hand
     */
    public String getCustomerTypeName() {
        return customerTypeName;
    }

    /**
     * Set customerTypeName
     * 
     * @param customerTypeName
     *            type String
     * @return
     * @author hand
     */
    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * Get fieldValues
     * 
     * @return List<String>
     * @author hand
     */
    public List<String> getFieldValues() {
        return fieldValues;
    }

    /**
     * Set fieldValues
     * 
     * @param fieldValues
     *            type List<String>
     * @return
     * @author hand
     */
    public void setFieldValues(List<String> fieldValues) {
        this.fieldValues = fieldValues;
    }

    /**
     * Get fieldSearch
     * 
     * @return String
     * @author hand
     */
    public String getFieldSearch() {
        return fieldSearch;
    }

    /**
     * Set fieldSearch
     * 
     * @param fieldSearch
     *            type String
     * @return
     * @author hand
     */
    public void setFieldSearch(String fieldSearch) {
        this.fieldSearch = CmsUtils.trimForSearch(fieldSearch);
    }

    /**
     * Get url
     * 
     * @return String
     * @author hand
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set url
     * 
     * @param url
     *            type String
     * @return
     * @author hand
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getBannerDesktopId() {
        return bannerDesktopId;
    }

    public void setBannerDesktopId(Long bannerDesktopId) {
        this.bannerDesktopId = bannerDesktopId;
    }

    public Long getBannerMobileId() {
        return bannerMobileId;
    }

    public void setBannerMobileId(Long bannerMobileId) {
        this.bannerMobileId = bannerMobileId;
    }

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

}
