/*******************************************************************************
 * Class        ：QrtzMScheduleRepository
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.quartz.job.dto.QrtzMScheduleDto;
import vn.com.unit.quartz.job.dto.QrtzMScheduleSearchDto;
import vn.com.unit.quartz.job.entity.QrtzMSchedule;

/**
 * <p>
 * QrtzMScheduleRepository
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@Repository
public interface QrtzMScheduleRepository extends DbRepository<QrtzMSchedule, Long> {
	

	/**
     * <p>
     * Get by schedule code.
     * </p>
     *
     * @author khadm
     * @param schedCode
     *            type {@link String}
     * @param companyId
     *            type {@link Long}
     * @return {@link QrtzMSchedule}
     */
	QrtzMSchedule getByScheduleId(@Param("schedId") Long schedId, @Param("companyId") Long companyId);

	/**
     * <p>
     * Checks if is schedule in use.
     * </p>
     *
     * @author khadm
     * @param scheduleCode
     *            type {@link String}
     * @param companyId
     *            type {@link Long}
     * @return {@link Object}
     */
	Object isScheduleInUse(@Param("schedCode") String scheduleCode, @Param("companyId") Long companyId);

	/**
     * <p>
     * Count schedule by condition.
     * </p>
     *
     * @author khadm
     * @param schedSearch
     *            type {@link QrtzMScheduleSearchDto}
     * @return {@link int}
     */
	int countScheduleByCondition(@Param("schedSearch") QrtzMScheduleSearchDto schedSearch);

	/**
     * <p>
     * Get schedules.
     * </p>
     *
     * @author khadm
     * @param schedSearch
     *            type {@link QrtzMScheduleSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link Page<QrtzMScheduleDto>}
     */
	Page<QrtzMScheduleDto> getSchedules(@Param("schedSearch") QrtzMScheduleSearchDto schedSearch, Pageable pageable);

	/**
     * <p>
     * Checks for code.
     * </p>
     *
     * @author khadm
     * @param companyId
     *            type {@link Long}
     * @param code
     *            type {@link String}
     * @param id
     *            type {@link Long}
     * @return {@link int}
     */
	int hasCode(@Param("companyId") Long companyId, @Param("code") String code, @Param("id") Long id);
}
