package vn.com.unit.ep2p.service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import com.google.zxing.WriterException;

import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.trainingCourse.dto.TrainingCourseDto;
import vn.com.unit.cms.core.module.trainingCourse.dto.TrainingCourseSearchDto;
import vn.com.unit.cms.core.module.trainingCourse.dto.TrainingTraineeSearchDto;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.dto.OfficeDto;
import vn.com.unit.ep2p.admin.dto.TrainingTraineeDB2Dto;

public interface TrainingCourseService {

    public TrainingCourseDto addTrainingCourse(TrainingCourseDto dto) throws WriterException, IOException, DetailException;

    public TrainingCourseDto updateTrainingCourse(TrainingCourseDto dto) throws DetailException;
    
    CmsCommonPagination<TrainingCourseDto> getListTrainingCourseByCondition(TrainingCourseSearchDto searchDto);

    TrainingCourseDto getDetailTrainingCourse(TrainingCourseSearchDto searchDto) throws DetailException;

    CmsCommonPagination<TrainingTraineeDB2Dto> getListGuestsOfTraining(TrainingTraineeSearchDto searchDto);

    List<OfficeDto> getListOfficeByAgent(String agentCode);
    
    public void updateSatus(TrainingCourseDto dto) throws DetailException;
    
    public int checkinTrainingCourse(String courseCode, String agentCode) throws DetailException;
    
    public ResponseEntity exportListTrainingCourses(TrainingCourseSearchDto searchDto,
			HttpServletResponse response, Locale locale);
    
    public TrainingCourseDto getTrainingCourseByCode(String courseCode, String agentCode);
}
