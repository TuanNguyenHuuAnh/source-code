package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.QrtzTriggerDto;
import vn.com.unit.quartz.job.dto.QrtzMJobScheduleDto;
import vn.com.unit.quartz.job.dto.QrtzMJobScheduleSearchDto;
import vn.com.unit.quartz.job.entity.QrtzMJobSchedule;

public interface AppQrtzMJobScheduleRepository extends DbRepository<QrtzMJobSchedule, Long> {

	public int countJobScheduleByCondition(@Param("qMJobSchedule") QrtzMJobScheduleSearchDto qMJobSchedule);

	public QrtzMJobSchedule getJobScheduleByNameRef(@Param("jobNameRef") String jobNameRef);

	public List<QrtzMJobScheduleDto> getJobSchedules(@Param("qMJobSchedule") QrtzMJobScheduleSearchDto qMJobSchedule, 
													 @Param("companyIds") List<Long> companyIds, 
                                                     @Param("dateTimePattern") String dateTimePattern, @Param("offset") int offset,
                                                     @Param("sizeOfPage") int sizeOfPage);

	public QrtzTriggerDto getTriggerByJobName(@Param("jobName") String jobName);

}
