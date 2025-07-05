package vn.com.unit.cms.core.module.trainingCourse.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class TrainingCourseSearchDto extends CommonSearchWithPagingDto {
	private Object courseCode;          // mã khóa học
	private Object courseName;          // tên khóa học
	private Object agentCode;           // mã đại lý
	private String agentType;           // loại đại lý
	private String agentGroupType;
	private String attendance;
	private String fromDate;
	private String toDate;
	private String status;
	private String agentFlg;
	private String agentLeaderFlg;
	private List<String> offices;
}
