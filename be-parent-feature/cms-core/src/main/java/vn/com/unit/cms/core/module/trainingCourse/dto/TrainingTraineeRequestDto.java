package vn.com.unit.cms.core.module.trainingCourse.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.module.trainingCourse.dto.TrainingTraineeDto;

import java.util.List;

@Getter
@Setter
public class TrainingTraineeRequestDto {
	private List<TrainingTraineeDto> trainees;
}
