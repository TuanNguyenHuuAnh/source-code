package vn.com.unit.ep2p.admin.dto;

import java.util.Date;

public class SortOrderDto {
	private Long objectId;
	private Long sortValue;
	private Date updateDate;
	
	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectItemId) {
		this.objectId = objectItemId;
	}
	public Long getSortValue() {
		return sortValue;
	}
	public void setSortValue(Long objectItemSort) {
		this.sortValue = objectItemSort;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
