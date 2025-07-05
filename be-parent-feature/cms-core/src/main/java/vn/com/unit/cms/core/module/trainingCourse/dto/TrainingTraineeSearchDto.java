package vn.com.unit.cms.core.module.trainingCourse.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class TrainingTraineeSearchDto extends CommonSearchWithPagingDto {
	private String courseId;
	private String status;
	private String agentGroupType;
	private String idNumber;
	private String agentCode;
	private String agentName;
	private String attendanceStatus;
	private String officeCode;
}
