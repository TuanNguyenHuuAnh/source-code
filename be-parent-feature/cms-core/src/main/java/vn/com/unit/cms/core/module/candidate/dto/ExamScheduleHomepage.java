package vn.com.unit.cms.core.module.candidate.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExamScheduleHomepage {
	private String courseName;
	private String courseCode;
	private String timeClean; // date thoi gian hoc
	private String addressClean;
	private String timeExam;// date thoi gian thi
	private String address;
	private String formatExam;
	private String result;
	private String dateExam;
}
