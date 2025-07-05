/*******************************************************************************
 * Class        ：QrtzMJobService
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import org.springframework.data.domain.Pageable;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.quartz.job.dto.QrtzMJobDto;
import vn.com.unit.quartz.job.dto.QrtzMJobSearchDto;
import vn.com.unit.quartz.job.entity.QrtzMJob;
import vn.com.unit.quartz.job.entity.QrtzMJobStore;


/**
 * <p>
 * QrtzMJobService
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public interface QrtzMJobService {

	/**
     * <p>
     * Get by job code.
     * </p>
     *
     * @author khadm
     * @param jobCode
     *            type {@link String}
     * @return {@link QrtzMJob}
     * @throws DetailException
     *             the detail exception
     */
	QrtzMJob getByJobId(Long jobId) throws DetailException;

	/**
     * <p>
     * Get class name by path.
     * </p>
     *
     * @author khadm
     * @param path
     *            type {@link String}
     * @return {@link String}
     * @throws DetailException
     *             the detail exception
     */
	String getClassNameByPath(String path) throws DetailException;


	/**
     * <p>
     * Checks if is job in use.
     * </p>
     *
     * @author khadm
     * @param jobCode
     *            type {@link String}
     * @return true, if is job in use
     * @throws DetailException
     *             the detail exception
     */
	boolean isJobInUse(String jobCode) throws DetailException;
	
	/**
     * <p>
     * Save.
     * </p>
     *
     * @author khadm
     * @param jobEntity
     *            type {@link QrtzMJob}
     * @param jobStoreEntity
     *            type {@link QrtzMJobStore}
     * @param locale
     *            type {@link Locale}
     * @throws DetailException
     *             the detail exception
     */
	void save(QrtzMJob jobEntity, QrtzMJobStore jobStoreEntity, Locale locale) throws DetailException;
	
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
     * @throws DetailException
     *             the detail exception
     */
	QrtzMJob getByJobGroup(Long companyId, String jobGroup) throws DetailException;
	
	
	
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
     * @return true, if successful
     * @throws SQLException
     *             the SQL exception
     */
	boolean hasCode(Long companyId, String code, Long id) throws SQLException;
	
	

	///////////////////////////////NEW//////////////////////////////////////////////////

	/**
     * <p>
     * Creates the.
     * </p>
     *
     * @author khadm
     * @param qrtzMJob
     *            type {@link QrtzMJobDto}
     * @return {@link QrtzMJobDto}
     * @throws DetailException
     *             the detail exception
     */
	public QrtzMJobDto create(QrtzMJobDto qrtzMJob)throws DetailException;
	
	/**
     * <p>
     * Update.
     * </p>
     *
     * @author khadm
     * @param qrtzMJob
     *            type {@link QrtzMJobDto}
     * @return {@link QrtzMJobDto}
     * @throws DetailException
     *             the detail exception
     */
	public QrtzMJobDto update(QrtzMJobDto qrtzMJob)throws DetailException;
	
	/**
     * <p>
     * Delete.
     * </p>
     *
     * @author khadm
     * @param id
     *            type {@link Long}
     * @return true, if successful
     * @throws DetailException
     *             the detail exception
     */
	public boolean delete(Long id)throws DetailException;
	
	/**
     * <p>
     * List.
     * </p>
     *
     * @author khadm
     * @param pageable
     *            type {@link Pageable}
     * @return {@link List<QrtzMJobDto>}
     * @throws DetailException
     *             the detail exception
     */
	public List<QrtzMJobDto> list(QrtzMJobSearchDto searchDto, Pageable pageable)throws DetailException;
	
	/**
     * <p>
     * Count.
     * </p>
     *
     * @author khadm
     * @return {@link int}
     * @throws DetailException
     *             the detail exception
     */
	public int count(QrtzMJobSearchDto searchDto) throws DetailException;
	
	/**
     * <p>
     * Detail.
     * </p>
     *
     * @author khadm
     * @param id
     *            type {@link Long}
     * @return {@link QrtzMJobDto}
     * @throws DetailException
     *             the detail exception
     */
	public QrtzMJobDto detail(Long id)throws DetailException;
}
