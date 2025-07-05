package vn.com.unit.cms.core.module.candidate.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class ScheduleCandidateSearchDto extends CommonSearchWithPagingDto{
	private String ngayToChuc;
	private Object areaName;//khu
	private Object territory;//vung
	private Object office;//van phong
	private Object regionName;
	private Object courseCode;
	private Object organizationDate;
	private Object examDate;
	private Object studyPlace;
	private Object officeName;
	private Object  month;
	private Object  year;
	private String functionCode;
	private Object courseName;
	private Object organizationPlace;
	private Object ngayKetThuc;
}
