package vn.com.unit.cms.core.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonSearchWithPagingDto {
	private Integer page;
	private Integer pageSize;
	private String sort;
	private String search;
	private String tableName;
	private Integer offset;
	private String language;
	private String functionCode;
	private String searchType;
	private String keyword;
	private Integer size;
	private Object caoName;
	private Object bdthName;
	private Object bdahName;
	private Object bdrhName;
	private Object bdohName;
	private Object gaName;
	private Object branchName;
	private Object unitName;
	private Object agentAll;
	private String orgId;
}
