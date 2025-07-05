/*******************************************************************************
 * Class        ：QrtzMJobLogService
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.quartz.job.dto.QrtzMJobLogDto;
import vn.com.unit.quartz.job.entity.QrtzMJobLog;

/**
 * <p>
 * QrtzMJobLogService
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public interface QrtzMJobLogService {

    /**
     * <p>
     * Get by job name ref and start time.
     * </p>
     *
     * @author khadm
     * @param id
     *            type {@link Long}
     * @return {@link QrtzMJobLog}
     */
    public QrtzMJobLog getByJobNameRefAndStartTime(Long id);

    /////////////////////////////////////////////// NEW /////////////////////////////////////////////////////////

    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @author khadm
     * @param qrtzMJobLog
     *            type {@link QrtzMJobLogDto}
     * @return {@link QrtzMJobLogDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMJobLogDto create(QrtzMJobLogDto qrtzMJobLog) throws DetailException;

    /**
     * <p>
     * Update.
     * </p>
     *
     * @author khadm
     * @param qrtzMJobLog
     *            type {@link QrtzMJobLogDto}
     * @return {@link QrtzMJobLogDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMJobLogDto update(QrtzMJobLogDto qrtzMJobLog) throws DetailException;

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
    public boolean delete(Long id) throws DetailException;
    
    /**
     * <p>
     * List.
     * </p>
     *
     * @author khadm
     * @param pageable
     *            type {@link Pageable}
     * @return {@link List<QrtzMJobLogDto>}
     * @throws DetailException
     *             the detail exception
     */
    public List<QrtzMJobLogDto> list(Pageable pageable) throws DetailException;
    
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
    public int count() throws DetailException;
    
    /**
     * <p>
     * Detail.
     * </p>
     *
     * @author khadm
     * @param id
     *            type {@link Long}
     * @return {@link QrtzMJobLogDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMJobLogDto detail(Long id) throws DetailException;
}
