package vn.com.unit.cms.core.module.trainingCourse.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainingTraineeDto {
	private String idNumber;
	private String name;
	private String agentCode;
	private Date effectivedDate;
	private Date attendanceTime;
	private String agentType;
	private String umCode;
	private String umName;
	private String bmCode;
	private String bmName;
	private String officeCode;
	private String officeName;
}
