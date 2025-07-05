package vn.com.unit.cms.core.module.candidate.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

public class CandidateInfoParam {
	@In
	public String userName;
	@In
	public String orgId;
	@ResultSet
	public List<CandidateInfoDto>data;
}
