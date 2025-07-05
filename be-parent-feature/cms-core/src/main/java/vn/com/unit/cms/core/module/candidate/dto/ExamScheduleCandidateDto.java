package vn.com.unit.cms.core.module.candidate.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ExamScheduleCandidateDto {
	private Integer no;
	private String region;//mien
	private String regionName;
	private String area;//khu vuc
	private String areaName;
	private String territory;//vung
	private String territoryName;
	private String office;//van phong
	private String officeName;
	private String examCode;
	private Date examDate;
	private String examTime;
	private String examStatus;
	private String examFormat; //hinh thuc thi
	private String organizationPlace; //dia diem
	private String courseCode;
	private String vidicode;
}
