package vn.com.unit.cms.core.module.candidate.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ScheduleCandidateDto{
	private Integer no;
	private String area;
	private String areaName;
	private String region;
	private String regionName;
	private String office;
	private String officeName;
	private String courseCode;//ma khoa hoc
	private String courseName;
	private Date organizationDate;//ngay to chuc
	private String examDate;//ngay thi
	private String studyPlace;//dia diem hoc
	private String organizationPlace;
	private Date ngayKetThuc;
}
