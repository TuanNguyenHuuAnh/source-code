/*******************************************************************************
 * Class        ：QrtzMJobRepository
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
import vn.com.unit.quartz.job.dto.QrtzMJobDto;
import vn.com.unit.quartz.job.dto.QrtzMJobSearchDto;
import vn.com.unit.quartz.job.entity.QrtzMJob;

/**
 * <p>
 * QrtzMJobRepository
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@Repository
public interface QrtzMJobRepository extends DbRepository<QrtzMJob, Long> {
	

	/**
     * <p>
     * Count job by condition.
     * </p>
     *
     * @author khadm
     * @param jobSearch
     *            type {@link QrtzMJobSearchDto}
     * @return {@link int}
     */
	int countJobByCondition(@Param("jobSearch") QrtzMJobSearchDto jobSearch);


	/**
     * <p>
     * Get by job code.
     * </p>
     *
     * @author khadm
     * @param jobCode
     *            type {@link String}
     * @return {@link QrtzMJob}
     */
	QrtzMJob getByJobId(@Param("jobId") Long jobId);


	/**
     * <p>
     * Get job in use.
     * </p>
     *
     * @author khadm
     * @param jobCode
     *            type {@link String}
     * @return {@link Object}
     */
	Object getJobInUse(@Param("jobCode") String jobCode);


	/**
     * <p>
     * Get jobs.
     * </p>
     *
     * @author khadm
     * @param jobSearch
     *            type {@link QrtzMJobSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link Page<QrtzMJobDto>}
     */
	Page<QrtzMJobDto> getJobs(@Param("jobSearch") QrtzMJobSearchDto jobSearch, Pageable pageable);

	/**
     * <p>
     * Get by job group.
     * </p>
     *
     * @author khadm
     * @param companyId
     *            type {@link Long}
     * @param jobGroup
     *            type {@link String}
     * @return {@link QrtzMJob}
     */
	QrtzMJob getByJobGroup(@Param("companyId") Long companyId, @Param("jobGroup") String jobGroup);
	
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
