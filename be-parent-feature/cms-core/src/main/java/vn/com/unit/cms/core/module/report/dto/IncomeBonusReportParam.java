package vn.com.unit.cms.core.module.report.dto;

import java.io.Serializable;
import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

@SuppressWarnings("serial")
public class IncomeBonusReportParam<T> implements Serializable {
	@In
	public String agentCode;
	@In
	public String agentOrg;
	@In
	public String agentGroup;
	@In
	public String time;		//yyyyMM
	@ResultSet
	public List<T> data;	//MO/TQP/TSM
}
