package vn.com.unit.cms.core.module.candidate.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class RegulationExamScheduleParam {
	@In
	public String agentCode;
	@ResultSet
	public List<RegulationExamScheduleDto> data;
}
