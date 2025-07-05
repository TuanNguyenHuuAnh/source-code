/*******************************************************************************
 * Class        ：QrtzMJobClassService
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.quartz.job.dto.QrtzMJobClassDto;

/**
 * <p>
 * QrtzMJobClassService
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public interface QrtzMJobClassService {
    //SERVICE NEW
    
    /**
     * <p>
     * Creates the.
     * </p>
     *
     * @author khadm
     * @param qrtzMJobClass
     *            type {@link QrtzMJobClassDto}
     * @return {@link QrtzMJobClassDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMJobClassDto create(QrtzMJobClassDto qrtzMJobClass)throws DetailException;
    
    
    /**
     * <p>
     * Update.
     * </p>
     *
     * @author khadm
     * @param qrtzMJobClass
     *            type {@link QrtzMJobClassDto}
     * @return {@link QrtzMJobClassDto}
     * @throws DetailException
     *             the detail exception
     */
    public QrtzMJobClassDto update(QrtzMJobClassDto qrtzMJobClass)throws DetailException;
    
    
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
     * @return {@link List<QrtzMJobClassDto>}
     * @throws DetailException
     *             the detail exception
     */
    public List<QrtzMJobClassDto> list(Pageable pageable)throws DetailException;
    
    
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
    
    public QrtzMJobClassDto detail(Long id)throws DetailException;
}
