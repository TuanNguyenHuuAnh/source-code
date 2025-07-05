package vn.com.unit.cms.admin.all.dto;

public class InvestorSearchDto {
	private Long id;
	private Integer status;
	private String statusName;
	private Integer kind;
	private Long categoryId;
	private String name;
	private String url;
	private Integer enabled;
	private Integer pageSize;
	private String language;

	/**
	 * @return the status
	 * @author taitm
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 * @author taitm
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the kind
	 * @author taitm
	 */
	public Integer getKind() {
		return kind;
	}

	/**
	 * @param kind
	 *            the kind to set
	 * @author taitm
	 */
	public void setKind(Integer kind) {
		this.kind = kind;
	}

	/**
	 * @return the categoryId
	 * @author taitm
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId
	 *            the categoryId to set
	 * @author taitm
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the name
	 * @author taitm
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 * @author taitm
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the url
	 * @author taitm
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 * @author taitm
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the enabled
	 * @author taitm
	 */
	public Integer getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 * @author taitm
	 */
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the pageSize
	 * @author taitm
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 * @author taitm
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the statusName
	 * @author taitm
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName
	 *            the statusName to set
	 * @author taitm
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return the language
	 * @author taitm
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 * @author taitm
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the id
	 * @author taitm
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 * @author taitm
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
