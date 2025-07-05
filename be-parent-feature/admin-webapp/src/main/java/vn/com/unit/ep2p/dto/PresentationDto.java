package vn.com.unit.ep2p.dto;

import java.util.Date;

public class PresentationDto {

	private Long id;
	
	private Integer no;
	
	private String channel;
	
	private String typeLink;
	
	private String title;
	
	private String linkUrl;
	
	private String typeOpenPage;
	
	private Integer orderOnPage;
	
	private String content;
	
	private String statusItem;
	
	private String createdBy;
	
	private Date createdDate;
	
	private String updatedBy;
	
	private Date updatedDate;
	
	private String deletedBy;
	
	private Date deletedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getTypeLink() {
		return typeLink;
	}

	public void setTypeLink(String typeLink) {
		this.typeLink = typeLink;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Integer getOrderOnPage() {
		return orderOnPage;
	}

	public void setOrderOnPage(Integer orderOnPage) {
		this.orderOnPage = orderOnPage;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatusItem() {
		return statusItem;
	}

	public void setStatusItem(String statusItem) {
		this.statusItem = statusItem;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}

	public Date getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	public String getTypeOpenPage() {
		return typeOpenPage;
	}

	public void setTypeOpenPage(String typeOpenPage) {
		this.typeOpenPage = typeOpenPage;
	}
	
}
