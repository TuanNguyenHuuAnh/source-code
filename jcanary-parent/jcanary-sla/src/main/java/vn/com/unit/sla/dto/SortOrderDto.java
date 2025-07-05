package vn.com.unit.sla.dto;

@Deprecated
public class SortOrderDto {
	private Long objectId;
	private Long sortValue;
	
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
}
