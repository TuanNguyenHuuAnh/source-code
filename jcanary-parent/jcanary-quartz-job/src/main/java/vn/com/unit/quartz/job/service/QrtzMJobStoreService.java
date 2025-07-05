/*******************************************************************************
 * Class        ：QrtzMJobStoreService
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.quartz.job.dto.QrtzMJobStoreDto;
import vn.com.unit.quartz.job.entity.QrtzMJobStore;


/**
 * <p>
 * QrtzMJobStoreService
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public interface QrtzMJobStoreService {
    
	/**
     * <p>
     * Get by group code.
     * </p>
     *
     * @author khadm
     * @param groupCode
     *            type {@link String}
     * @return {@link QrtzMJobStore}
     */
	public QrtzMJobStore getByGroupCode(String groupCode);

	/**
     * <p>
     * Get group ids.
     * </p>
     *
     * @author khadm
     * @return {@link List<String>}
     */
	public List<String> getGroupIds();

	/**
     * <p>
     * Checks if is group code exists.
     * </p>
     *
     * @author khadm
     * @param groupCode
     *            type {@link String}
     * @return true, if is group code exists
     */
	boolean isGroupCodeExists(String groupCode);

	//////////////////////////////////NEW//////////////////////////////////////////
	
	/**
     * <p>
     * Creates the.
     * </p>
     *
     * @author khadm
     * @param qrtzMJobStore
     *            type {@link QrtzMJobStoreDto}
     * @return {@link QrtzMJobStoreDto}
     * @throws DetailException
     *             the detail exception
     */
	public QrtzMJobStoreDto create(QrtzMJobStoreDto qrtzMJobStore)throws DetailException;
	
	/**
     * <p>
     * Update.
     * </p>
     *
     * @author khadm
     * @param qrtzMJobStore
     *            type {@link QrtzMJobStoreDto}
     * @return {@link QrtzMJobStoreDto}
     * @throws DetailException
     *             the detail exception
     */
	public QrtzMJobStoreDto update(QrtzMJobStoreDto qrtzMJobStore)throws DetailException;
	
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
     * @return {@link List<QrtzMJobStoreDto>}
     * @throws DetailException
     *             the detail exception
     */
	public List<QrtzMJobStoreDto> list(Pageable pageable)throws DetailException;
	
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
     * @return {@link QrtzMJobStoreDto}
     * @throws DetailException
     *             the detail exception
     */
	public QrtzMJobStoreDto detail(Long id)throws DetailException;
	
}
