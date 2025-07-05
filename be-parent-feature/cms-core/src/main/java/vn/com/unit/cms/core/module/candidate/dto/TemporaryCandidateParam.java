package vn.com.unit.cms.core.module.candidate.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class TemporaryCandidateParam{
	@In
	public String stringJsonParam;
	@ResultSet
	public List<TemporaryCandidateDto> data;
	@Out
	public Integer totalData;
}
