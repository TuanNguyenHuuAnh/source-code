package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

public class HomepageSearchDto {
	
	/** status */
	private Integer status;
	
	/** description */
	private String description;
	
	/** start date */
	private Date startDate;
	
	/** end date*/
	private Date endDate;

	/** selectedField */
	private List<String> selectedStatus;
	
	/** bannerPage */
    private String bannerPage;
	
	/** url */
	private String url;

	private Integer pageSize;
	
	private String lang;
	
    private String statusName;
    
    private String businessCode;
	
	/**
	 * get status
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * set status
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * get selected status
	 * @return
	 */
	public List<String> getSelectedStatus() {
		return selectedStatus;
	}

	/**
	 * set selected status
	 * @param selectedStatus
	 */
	public void setSelectedStatus(List<String> selectedStatus) {
		this.selectedStatus = selectedStatus;
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
	 * get description
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * set description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

    /**
     * Get bannerPage
     * @return String
     * @author hand
     */
    public String getBannerPage() {
        return bannerPage;
    }

    /**
     * Set bannerPage
     * @param   bannerPage
     *          type String
     * @return
     * @author  hand
     */
    public void setBannerPage(String bannerPage) {
        this.bannerPage = bannerPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * @return the statusName
     * @author taitm
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * @param statusName the statusName to set
     * @author taitm
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * @return the businessCode
     * @author taitm
     */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
     * @param businessCode the businessCode to set
     * @author taitm
     */
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

}
