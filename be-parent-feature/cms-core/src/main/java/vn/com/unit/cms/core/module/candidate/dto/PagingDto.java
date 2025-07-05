package vn.com.unit.cms.core.module.candidate.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingDto {
	private Integer page;
	private Integer pageSize;
	private String sort;
	private String keyword;
	private String searchType;
	private Integer size;

}
