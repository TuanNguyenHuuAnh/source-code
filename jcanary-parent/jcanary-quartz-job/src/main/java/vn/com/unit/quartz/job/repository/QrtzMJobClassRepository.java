/*******************************************************************************
 * Class        ：QrtzMJobClassRepository
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
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.quartz.job.dto.QrtzMJobClassDto;
import vn.com.unit.quartz.job.entity.QrtzMJobClass;


/**
 * <p>
 * QrtzMJobClassRepository
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public interface QrtzMJobClassRepository extends DbRepository<QrtzMJobClass, Long> {
	
	/**
     * <p>
     * Find class name by path.
     * </p>
     *
     * @author khadm
     * @param path
     *            type {@link String}
     * @return {@link String}
     * @throws DetailException
     *             the detail exception
     */
	String findClassNameByPath(@Param("path") String path) throws DetailException;
	
	//////////////////////////////NEW/////////////////////////////////////
	
	
	/**
     * <p>
     * Get qrtz M job class.
     * </p>
     *
     * @author khadm
     * @param pageable
     *            type {@link Pageable}
     * @return {@link Page<QrtzMJobClassDto>}
     */
	public Page<QrtzMJobClassDto> getQrtzMJobClass(Pageable pageable);
	
	/**
     * <p>
     * Count qrtz M job class.
     * </p>
     *
     * @author khadm
     * @return {@link int}
     */
	public int countQrtzMJobClass();

}
