package vn.com.unit.ep2p.admin.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.quartz.job.entity.QrtzMJobLog;

@Repository
public interface AppQrtzMJobLogRepository extends DbRepository<QrtzMJobLog, Long> {
	
	/**
	 * @param jobNameRef
	 * @param startTime
	 * @param endDate
	 * @return
	 */
	public QrtzMJobLog getByJobNameRefAndStartTime(@Param("jobScheduleId") Long jobScheduleId);

	/**
	 * 
	 * getByJobNameRef
	 * @param jobNameRef
	 * @return
	 * @author tantm
	 */
    public QrtzMJobLog getByJobNameRef(@Param("jobNameRef") String jobNameRef);

}
