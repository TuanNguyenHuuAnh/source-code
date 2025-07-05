package vn.com.unit.cms.admin.all.dto;

import java.util.List;

import vn.com.unit.ep2p.admin.dto.SortOrderDto;

//import vn.com.unit.ep2p.admin.dto.SortOrderDto;

public class SortPageDto {
	private List<SortOrderDto> sortOderList;

	public List<SortOrderDto> getSortList() {
		return sortOderList;
	}

	public void setSortList(List<SortOrderDto> sortOderList) {
		this.sortOderList = sortOderList;
	}

}
