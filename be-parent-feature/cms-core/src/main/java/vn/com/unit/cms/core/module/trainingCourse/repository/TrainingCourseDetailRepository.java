package vn.com.unit.cms.core.module.trainingCourse.repository;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.core.module.trainingCourse.entity.TrainingCoursesDetailEntity;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.TrainingTraineeDB2Dto;

import java.util.List;

public interface TrainingCourseDetailRepository extends DbRepository<TrainingCoursesDetailEntity, Long>{
	List<TrainingTraineeDB2Dto> getListCourseGuest(@Param("courseId") Long courseId);

	@Modifying
	void deleteByCourseId(@Param("courseId") Long courseId);
	
	@Modifying
	void deleteTrainees(@Param("courseId") Long courseId, @Param("keys") List<String> keys);
		
	@Modifying
	int updateAttendance(@Param("courseCode") String courseCode, @Param("agentCode") String agentCode);
}
