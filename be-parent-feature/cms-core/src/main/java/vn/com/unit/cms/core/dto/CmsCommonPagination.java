package vn.com.unit.cms.core.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsCommonPagination<T> {
	private Integer page;
	private Integer size;
	private Integer totalData;
	private List<T> data;

	private Integer totalPromoteDemote; //so luong agent thang chuc/ giang chuc
}
