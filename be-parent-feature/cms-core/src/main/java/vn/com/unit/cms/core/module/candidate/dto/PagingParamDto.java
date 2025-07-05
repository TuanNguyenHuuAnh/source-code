package vn.com.unit.cms.core.module.candidate.dto;

import jp.sf.amateras.mirage.annotation.In;

public class PagingParamDto {
	@In
	public Integer page;
	@In
	public Integer pageSize;
}
