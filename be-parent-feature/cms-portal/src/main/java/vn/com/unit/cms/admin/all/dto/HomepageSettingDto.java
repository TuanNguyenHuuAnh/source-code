/*******************************************************************************
 * Class        ：HomePage
 * Created date ：2017/05/29
 * Lasted date  ：2017/05/29
 * Author       ：sonnt
 * Change log   ：2017/05/29：01-00 sonnt create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

import vn.com.unit.cms.core.module.banner.entity.BannerLanguage;

//import vn.com.unit.cms.admin.all.entity.BannerLanguage;

/**
 * Homepage entity
 * 
 * @version 01-00
 * @since 01-00
 * @author sonnt
 */
public class HomepageSettingDto {

	private Long id;

	private int speedRoll;

	private String bannerTopId;

	private String bannerFixId;

	private List<String> bannerTop;

	private List<BannerLanguage> bannerTopList;

	private List<String> bannerTopMobile;

	private List<BannerLanguage> bannerTopMobileList;

	private String bannerTopMobileId;

	private String bannerFixMobileId;

	private Date effectiveDateFrom;

	private Date effectiveDateTo;

	private String description;

	private Integer status;

	private String statusCode;

	private String note;

	private String bannerPage;

	private Date createDate;

	private String processId;

	private String bannerPageName;

	private String createBy;

	private String approveBy;

	private Date approveDate;

	private String publishBy;

	private Date publishDate;

	private String statusName;

	/**
	 * get id
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * set id
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * get rolling speed
	 * 
	 * @return
	 */
	public int getSpeedRoll() {
		return speedRoll;
	}

	/**
	 * set rolling speed
	 * 
	 * @param speedRoll
	 */
	public void setSpeedRoll(int speedRoll) {
		this.speedRoll = speedRoll;
	}

	/**
	 * get banner top id
	 * 
	 * @return
	 */
	public String getBannerTopId() {
		return bannerTopId;
	}

	/**
	 * set banner top id
	 * 
	 * @param bannerTopId
	 */
	public void setBannerTopId(String bannerTopId) {
		this.bannerTopId = bannerTopId;
	}

	/**
	 * get banner fix id
	 * 
	 * @return
	 */
	public String getBannerFixId() {
		return bannerFixId;
	}

	/**
	 * set banner fix id
	 * 
	 * @param bannerFixId
	 */
	public void setBannerFixId(String bannerFixId) {
		this.bannerFixId = bannerFixId;
	}

	/**
	 * get banner top mobile id
	 * 
	 * @return
	 */
	public String getBannerTopMobileId() {
		return bannerTopMobileId;
	}

	/**
	 * set banner top mobile id
	 * 
	 * @param bannerTopMobileId
	 */
	public void setBannerTopMobileId(String bannerTopMobileId) {
		this.bannerTopMobileId = bannerTopMobileId;
	}

	/**
	 * get banner fix mobile id
	 * 
	 * @return
	 */
	public String getBannerFixMobileId() {
		return bannerFixMobileId;
	}

	/**
	 * set banner fix mobile id
	 * 
	 * @param bannerFixMobileId
	 */
	public void setBannerFixMobileId(String bannerFixMobileId) {
		this.bannerFixMobileId = bannerFixMobileId;
	}

	/**
	 * get effective date from
	 * 
	 * @return
	 */
	public Date getEffectiveDateFrom() {
		return effectiveDateFrom;
	}

	/**
	 * set effective date from
	 * 
	 * @param effectiveDateFrom
	 */
	public void setEffectiveDateFrom(Date effectiveDateFrom) {
		this.effectiveDateFrom = effectiveDateFrom;
	}

	/**
	 * get effective date to
	 * 
	 * @return
	 */
	public Date getEffectiveDateTo() {
		return effectiveDateTo;
	}

	/**
	 * set effective date to
	 * 
	 * @param effectiveDateTo
	 */
	public void setEffectiveDateTo(Date effectiveDateTo) {
		this.effectiveDateTo = effectiveDateTo;
	}

	/**
	 * get description
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * set description
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * get status code
	 * 
	 * @return
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * set status code
	 * 
	 * @param statusCode
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * Get bannerPage
	 * 
	 * @return String
	 * @author TranLTH
	 */
	public String getBannerPage() {
		return bannerPage;
	}

	/**
	 * Set bannerPage
	 * 
	 * @param bannerPage type String
	 * @return
	 * @author TranLTH
	 */
	public void setBannerPage(String bannerPage) {
		this.bannerPage = bannerPage;
	}

	/**
	 * Get createDate
	 * 
	 * @return Date
	 * @author TranLTH
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Set createDate
	 * 
	 * @param createDate type Date
	 * @return
	 * @author TranLTH
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Get processId
	 * 
	 * @return String
	 * @author TranLTH
	 */
	public String getProcessId() {
		return processId;
	}

	/**
	 * Set processId
	 * 
	 * @param processId type String
	 * @return
	 * @author TranLTH
	 */
	public void setProcessId(String processId) {
		this.processId = processId;
	}

	/**
	 * Get bannerPageName
	 * 
	 * @return String
	 * @author TranLTH
	 */
	public String getBannerPageName() {
		return bannerPageName;
	}

	/**
	 * Set bannerPageName
	 * 
	 * @param bannerPageName type String
	 * @return
	 * @author TranLTH
	 */
	public void setBannerPageName(String bannerPageName) {
		this.bannerPageName = bannerPageName;
	}

	/**
	 * Get getCreateBy
	 * 
	 * @return String
	 * @author TaiTM
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * Set setCreateBy
	 * 
	 * @param createBy type String
	 * @return
	 * @author TaiTM
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * Get bannerPageName
	 * 
	 * @return String
	 * @author TaiTM
	 */
	public String getApproveBy() {
		return approveBy;
	}

	/**
	 * Set setApproveBy
	 * 
	 * @param approveBy type String
	 * @return
	 * @author TaiTM
	 */
	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	/**
	 * Get getApproveDate
	 * 
	 * @return String
	 * @author TaiTM
	 */
	public Date getApproveDate() {
		return approveDate;
	}

	/**
	 * Set approveDate
	 * 
	 * @param approveDate type Date
	 * @return
	 * @author TaiTM
	 */
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	/**
	 * Get getPublishBy
	 * 
	 * @return String
	 * @author TaiTM
	 */
	public String getPublishBy() {
		return publishBy;
	}

	/**
	 * Set setPublishBy
	 * 
	 * @param publishBy type String
	 * @return
	 * @author TaiTM
	 */
	public void setPublishBy(String publishBy) {
		this.publishBy = publishBy;
	}

	/**
	 * Get getPublishDate
	 * 
	 * @return String
	 * @author TaiTM
	 */
	public Date getPublishDate() {
		return publishDate;
	}

	/**
	 * Set publishDate
	 * 
	 * @param publishDate type Date
	 * @return
	 * @author TaiTM
	 */
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public List<String> getBannerTop() {
		return bannerTop;
	}

	public void setBannerTop(List<String> bannerTop) {
		this.bannerTop = bannerTop;
	}

	public List<String> getBannerTopMobile() {
		return bannerTopMobile;
	}

	public void setBannerTopMobile(List<String> bannerTopMobile) {
		this.bannerTopMobile = bannerTopMobile;
	}

	public List<BannerLanguage> getBannerTopList() {
		return bannerTopList;
	}

	public void setBannerTopList(List<BannerLanguage> bannerTopList) {
		this.bannerTopList = bannerTopList;
	}

	public List<BannerLanguage> getBannerTopMobileList() {
		return bannerTopMobileList;
	}

	public void setBannerTopMobileList(List<BannerLanguage> bannerTopMobileList) {
		this.bannerTopMobileList = bannerTopMobileList;
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
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

}