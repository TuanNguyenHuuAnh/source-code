/*******************************************************************************
 * Class        ：QrtzMJobTypeService
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.quartz.job.dto.QrtzMJobTypeDto;

/**
 * <p>
 * QrtzMJobTypeService
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public interface QrtzMJobTypeService {
    // SERVICE NEW

    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @author khadm
     * @param qrtzMJobType
     *            type {@link QrtzMJobTypeDto}
     * @return {@link QrtzMJobTypeDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMJobTypeDto create(QrtzMJobTypeDto qrtzMJobType) throws DetailException;
    
    /**
     * <p>
     * Update.
     * </p>
     *
     * @author khadm
     * @param qrtzMJobType
     *            type {@link QrtzMJobTypeDto}
     * @return {@link QrtzMJobTypeDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMJobTypeDto update(QrtzMJobTypeDto qrtzMJobType) throws DetailException;
    
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
     * @return {@link List<QrtzMJobTypeDto>}
     * @throws DetailException
     *             the detail exception
     */
    public List<QrtzMJobTypeDto> list(Pageable pageable) throws DetailException;
    
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
     * @return {@link QrtzMJobTypeDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMJobTypeDto detail(Long id) throws DetailException;
    
}
