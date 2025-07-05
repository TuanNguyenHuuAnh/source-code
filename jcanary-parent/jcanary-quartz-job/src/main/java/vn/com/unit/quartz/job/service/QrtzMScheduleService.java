/*******************************************************************************
 * Class        ：QrtzMScheduleService
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.quartz.job.dto.QrtzMScheduleDto;
import vn.com.unit.quartz.job.dto.QrtzMScheduleSearchDto;
import vn.com.unit.quartz.job.entity.QrtzMSchedule;


/**
 * <p>
 * QrtzMScheduleService
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public interface QrtzMScheduleService {


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
	QrtzMSchedule getByScheduleId(Long schedId, Long companyId);

	/**
     * <p>
     * Cron check.
     * </p>
     *
     * @author khadm
     * @param cron
     *            type {@link String}
     * @return {@link Boolean}
     */
	Boolean cronCheck(String cron);

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
     * @return {@link Boolean}
     */
	Boolean isScheduleInUse(String scheduleCode, Long companyId);

	
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
	

	///////////////////////////////////////NEW//////////////////////////////////
	
	/**
     * <p>
     * Creates the.
     * </p>
     *
     * @author khadm
     * @param qrtzMSchedule
     *            type {@link QrtzMScheduleDto}
     * @return {@link QrtzMScheduleDto}
     * @throws DetailException
     *             the detail exception
     */
	public QrtzMScheduleDto create(QrtzMScheduleDto qrtzMSchedule)throws DetailException;
	
	/**
     * <p>
     * Update.
     * </p>
     *
     * @author khadm
     * @param qrtzMSchedule
     *            type {@link QrtzMScheduleDto}
     * @return {@link QrtzMScheduleDto}
     * @throws DetailException
     *             the detail exception
     */
	public QrtzMScheduleDto update(QrtzMScheduleDto qrtzMSchedule)throws DetailException;
	
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
     * @return {@link List<QrtzMScheduleDto>}
     * @throws DetailException
     *             the detail exception
     */
	public List<QrtzMScheduleDto> list(QrtzMScheduleSearchDto searchDto, Pageable pageable)throws DetailException;
	
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
	public int count(QrtzMScheduleSearchDto searchDto) throws DetailException;
	
	/**
     * <p>
     * Detail.
     * </p>
     *
     * @author khadm
     * @param id
     *            type {@link Long}
     * @return {@link QrtzMScheduleDto}
     * @throws DetailException
     *             the detail exception
     */
	public QrtzMScheduleDto detail(Long id)throws DetailException;

}
