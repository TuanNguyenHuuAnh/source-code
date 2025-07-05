/*******************************************************************************
 * Class        ：QrtzMJobScheduleRepository
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/

package vn.com.unit.quartz.job.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.quartz.job.dto.QrtzMJobScheduleDto;
import vn.com.unit.quartz.job.dto.QrtzMJobScheduleSearchDto;
import vn.com.unit.quartz.job.dto.QrtzTriggerDto;
import vn.com.unit.quartz.job.entity.QrtzMJobSchedule;


/**
 * <p>
 * QrtzMJobScheduleRepository
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public interface QrtzMJobScheduleRepository extends DbRepository<QrtzMJobSchedule, Long> {


	/**
     * <p>
     * Count job schedule by condition.
     * </p>
     *
     * @author khadm
     * @param qMJobSchedule
     *            type {@link QrtzMJobScheduleSearchDto}
     * @return {@link int}
     */
	public int countJobScheduleByCondition(@Param("qMJobSchedule") QrtzMJobScheduleSearchDto qMJobSchedule);

	/**
     * <p>
     * Get job schedule by name ref.
     * </p>
     *
     * @author khadm
     * @param jobNameRef
     *            type {@link String}
     * @return {@link QrtzMJobSchedule}
     */
	public QrtzMJobSchedule getJobScheduleByNameRef(@Param("jobNameRef") String jobNameRef);

	/**
     * <p>
     * Get job schedules.
     * </p>
     *
     * @author khadm
     * @param qMJobSchedule
     *            type {@link QrtzMJobScheduleSearchDto}
     * @param companyIds
     *            type {@link List<Long>}
     * @param dateTimePattern
     *            type {@link String}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link Page<QrtzMJobScheduleDto>}
     */
	public Page<QrtzMJobScheduleDto> getJobSchedules(@Param("qMJobSchedule") QrtzMJobScheduleSearchDto qMJobSchedule, 
                                                     Pageable pageable);

	/**
     * <p>
     * Get trigger by job name.
     * </p>
     *
     * @author khadm
     * @param jobName
     *            type {@link String}
     * @return {@link QrtzTriggerDto}
     */
	public QrtzTriggerDto getTriggerByJobName(@Param("jobName") String jobName);
	
}
