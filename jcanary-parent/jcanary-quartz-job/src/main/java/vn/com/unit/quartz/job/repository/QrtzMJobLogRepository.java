/*******************************************************************************
 * Class        ：QrtzMJobLogRepository
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
import vn.com.unit.quartz.job.dto.QrtzMJobLogDto;
import vn.com.unit.quartz.job.entity.QrtzMJobLog;


/**
 * <p>
 * QrtzMJobLogRepository
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@Repository
public interface QrtzMJobLogRepository extends DbRepository<QrtzMJobLog, Long> {

	/**
     * <p>
     * Get by job name ref and start time.
     * </p>
     *
     * @author khadm
     * @param jobScheduleId
     *            type {@link Long}
     * @return {@link QrtzMJobLog}
     */
	public QrtzMJobLog getByJobNameRefAndStartTime(@Param("jobScheduleId") Long jobScheduleId);
	
	/////////////////////////////////NEW////////////////////////////////////
	
	/**
     * <p>
     * Find qrtz M job log.
     * </p>
     *
     * @author khadm
     * @param pageable
     *            type {@link Pageable}
     * @return {@link Page<QrtzMJobLogDto>}
     */
	public Page<QrtzMJobLogDto> findQrtzMJobLog(Pageable pageable);
	
	/**
     * <p>
     * Count qrtz M job log.
     * </p>
     *
     * @author khadm
     * @return {@link int}
     */
	public int countQrtzMJobLog();

}
