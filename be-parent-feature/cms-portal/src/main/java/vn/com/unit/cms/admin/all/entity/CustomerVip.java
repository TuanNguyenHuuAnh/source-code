package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import vn.com.unit.cms.core.entity.AbstractTracking;

@Table(name = "M_CUSTOMER_VIP")
public class CustomerVip extends AbstractTracking {
	@Id
	@Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_CUSTOMER_VIP")
	private Long id;

	@Column(name = "CODE")
	private String code;

	@Column(name = "IMAGE_URL")
	private String imgUrl;

	@Column(name = "IMAGE_NAME")
	private String imgName;

	@Column(name = "IMAGE_MOBILE_URL")
	private String imgMobileUrl;

	@Column(name = "IMAGE_MOBILE_NAME")
	private String imgMobileName;

	@Column(name = "ICON_URL")
	private String iconUrl;

	@Column(name = "ICON_NAME")
	private String iconName;

	@Column(name = "ICON_MOBILE_URL")
	private String iconMobileUrl;

	@Column(name = "ICON_MOBILE_NAME")
	private String iconMobileName;
	
	@Column(name = "IMAGE_BANNER_URL")
	private String imgBannerUrl;

	@Column(name = "IMAGE_BANNER_NAME")
	private String imgBannerName;

	@Column(name = "IMAGE_BANNER_MOBILE_URL")
	private String imgBannerMobileUrl;

	@Column(name = "IMAGE_BANNER_MOBILE_NAME")
	private String imgBannerMobileName;

	@Column(name = "SORT")
	private Long sort;

	@Column(name = "ENABLED")
	private boolean enabled;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "CUSTOMER_COMMENT")
	private String comment;

	@Column(name = "APPROVED_BY")
	private String approvedBy;

	@Column(name = "APPROVED_DATE")
	private Date approvedDate;

	@Column(name = "PUBLISHED_BY")
	private String publishedBy;

	@Column(name = "PUBLISHED_DATE")
	private Date publishedDate;

	@Column(name = "PROCESS_ID")
	private Long processId;
	
	@Column(name = "VIP")
	private Integer vip;
	
	@Column(name = "FDI")
	private Integer fdi;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the imgUrl
	 */
	public String getImgUrl() {
		return imgUrl;
	}

	/**
	 * @param imgUrl the imgUrl to set
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	/**
	 * @return the imgName
	 */
	public String getImgName() {
		return imgName;
	}

	/**
	 * @param imgName the imgName to set
	 */
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	/**
	 * @return the imgMobileUrl
	 */
	public String getImgMobileUrl() {
		return imgMobileUrl;
	}

	/**
	 * @param imgMobileUrl the imgMobileUrl to set
	 */
	public void setImgMobileUrl(String imgMobileUrl) {
		this.imgMobileUrl = imgMobileUrl;
	}

	/**
	 * @return the imgMobileName
	 */
	public String getImgMobileName() {
		return imgMobileName;
	}

	/**
	 * @param imgMobileName the imgMobileName to set
	 */
	public void setImgMobileName(String imgMobileName) {
		this.imgMobileName = imgMobileName;
	}

	/**
	 * @return the iconUrl
	 */
	public String getIconUrl() {
		return iconUrl;
	}

	/**
	 * @param iconUrl the iconUrl to set
	 */
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	/**
	 * @return the iconName
	 */
	public String getIconName() {
		return iconName;
	}

	/**
	 * @param iconName the iconName to set
	 */
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	/**
	 * @return the iconMobileUrl
	 */
	public String getIconMobileUrl() {
		return iconMobileUrl;
	}

	/**
	 * @param iconMobileUrl the iconMobileUrl to set
	 */
	public void setIconMobileUrl(String iconMobileUrl) {
		this.iconMobileUrl = iconMobileUrl;
	}

	/**
	 * @return the iconMobileName
	 */
	public String getIconMobileName() {
		return iconMobileName;
	}

	/**
	 * @param iconMobileName the iconMobileName to set
	 */
	public void setIconMobileName(String iconMobileName) {
		this.iconMobileName = iconMobileName;
	}

	/**
	 * @return the sort
	 */
	public Long getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(Long sort) {
		this.sort = sort;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the approvedBy
	 */
	public String getApprovedBy() {
		return approvedBy;
	}

	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * @return the approvedDate
	 */
	public Date getApprovedDate() {
		return approvedDate;
	}

	/**
	 * @param approvedDate the approvedDate to set
	 */
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	/**
	 * @return the publishedBy
	 */
	public String getPublishedBy() {
		return publishedBy;
	}

	/**
	 * @param publishedBy the publishedBy to set
	 */
	public void setPublishedBy(String publishedBy) {
		this.publishedBy = publishedBy;
	}

	/**
	 * @return the publishedDate
	 */
	public Date getPublishedDate() {
		return publishedDate;
	}

	/**
	 * @param publishedDate the publishedDate to set
	 */
	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	/**
	 * @return the processId
	 */
	public Long getProcessId() {
		return processId;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	/**
	 * @return the vip
	 */
	public Integer getVip() {
		return vip;
	}

	/**
	 * @param vip the vip to set
	 */
	public void setVip(Integer vip) {
		this.vip = vip;
	}

	/**
	 * @return the fdi
	 */
	public Integer getFdi() {
		return fdi;
	}

	/**
	 * @param fdi the fdi to set
	 */
	public void setFdi(Integer fdi) {
		this.fdi = fdi;
	}

	/**
	 * @return the imgBannerUrl
	 */
	public String getImgBannerUrl() {
		return imgBannerUrl;
	}

	/**
	 * @param imgBannerUrl the imgBannerUrl to set
	 */
	public void setImgBannerUrl(String imgBannerUrl) {
		this.imgBannerUrl = imgBannerUrl;
	}

	/**
	 * @return the imgBannerName
	 */
	public String getImgBannerName() {
		return imgBannerName;
	}

	/**
	 * @param imgBannerName the imgBannerName to set
	 */
	public void setImgBannerName(String imgBannerName) {
		this.imgBannerName = imgBannerName;
	}

	/**
	 * @return the imgBannerMobileUrl
	 */
	public String getImgBannerMobileUrl() {
		return imgBannerMobileUrl;
	}

	/**
	 * @param imgBannerMobileUrl the imgBannerMobileUrl to set
	 */
	public void setImgBannerMobileUrl(String imgBannerMobileUrl) {
		this.imgBannerMobileUrl = imgBannerMobileUrl;
	}

	/**
	 * @return the imgBannerMobileName
	 */
	public String getImgBannerMobileName() {
		return imgBannerMobileName;
	}

	/**
	 * @param imgBannerMobileName the imgBannerMobileName to set
	 */
	public void setImgBannerMobileName(String imgBannerMobileName) {
		this.imgBannerMobileName = imgBannerMobileName;
	}
}
