package vn.com.unit.cms.core.module.candidate.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class ExamScheduleCandidateParam {
	@In
	public Integer page;
	@In
	public Integer pageSize;
	@In
	public String sort;
	@In
	public String search;

	@ResultSet
	public List<ExamScheduleCandidateDto> data;
	@Out
	public Integer total;
}
