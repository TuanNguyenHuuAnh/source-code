package vn.com.unit.cms.core.module.candidate.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExamScheduleCandidateSearchDto extends PagingDto{
	private String ngayThi;
	private Object office;//van phong
	private Object examCode;
	private Object courseCode;
	private Object vidicode;
	private Object examDate;
	private Object examTime;
	private String functionCode;
	private Object examFormat;
	private Object examStatus;
	private Object regionName;
	private Object areaName;
	private Object officeName;
	private Object courseName;
	private Object organizationDate;
	private Object studyPlace;
	private Object organizationPlace;
}
