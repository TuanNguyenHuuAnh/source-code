package vn.com.unit.cms.core.module.trainingCourse.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.Date;
import java.util.List;

public class TrainingTraineeParamDto {
	@In
	public String agentCode;

	@ResultSet
	public List<TrainingTraineeDto> lstData;
}
