package vn.com.unit.cms.core.module.trainingCourse.repository;

import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.core.module.trainingCourse.dto.TrainingCourseDto;
import vn.com.unit.cms.core.module.trainingCourse.dto.TrainingCourseExportDto;
import vn.com.unit.cms.core.module.trainingCourse.dto.TrainingCourseSearchDto;
import vn.com.unit.cms.core.module.trainingCourse.entity.TrainingCoursesEntity;
import vn.com.unit.db.repository.DbRepository;

import java.util.List;

public interface TrainingCourseRepository extends DbRepository<TrainingCoursesEntity, Long>{

    Integer countTrainingCourseByCondition(@Param("search") TrainingCourseSearchDto search);

    TrainingCourseDto getDetailTrainingCourse(@Param("search") TrainingCourseSearchDto search);

    String getMaxCourseCode(@Param("prefix") String prefixCodeNot);

    List<TrainingCourseDto> getListTrainingCourseByCondition(@Param("search") TrainingCourseSearchDto search);

    List<TrainingCourseExportDto> getListExportTrainingCourses(@Param("search") TrainingCourseSearchDto search);
    
    TrainingCourseDto getTrainingCourseByCode(@Param("courseCode") String courseCode, @Param("agentCode") String agentCode);
}
