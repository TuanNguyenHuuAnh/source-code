/*******************************************************************************
 * Class        ：QrtzMJobTypeRepository
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.quartz.job.dto.QrtzMJobTypeDto;
import vn.com.unit.quartz.job.entity.QrtzMJobType;



/**
 * <p>
 * QrtzMJobTypeRepository
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public interface QrtzMJobTypeRepository extends DbRepository<QrtzMJobType, Long> {
	
	/////////////////////////////////NEW//////////////////////////////
	
	/**
     * <p>
     * Get qrtz M job type.
     * </p>
     *
     * @author khadm
     * @param pageable
     *            type {@link Pageable}
     * @return {@link Page<QrtzMJobTypeDto>}
     */
	public Page<QrtzMJobTypeDto> getQrtzMJobType(Pageable pageable);
	
	/**
     * <p>
     * Count qrtz M job type.
     * </p>
     *
     * @author khadm
     * @return {@link int}
     */
	public int countQrtzMJobType();

}
