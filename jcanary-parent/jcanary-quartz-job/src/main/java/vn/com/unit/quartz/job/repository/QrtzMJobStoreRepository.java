/*******************************************************************************
 * Class        ：QrtzMJobStoreRepository
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.quartz.job.dto.QrtzMJobStoreDto;
import vn.com.unit.quartz.job.entity.QrtzMJobStore;


/**
 * <p>
 * QrtzMJobStoreRepository
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@Repository
public interface QrtzMJobStoreRepository extends DbRepository<QrtzMJobStore, Long> {
	
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
	public QrtzMJobStore getByGroupCode(@Param("groupCode") String groupCode);

	/**
     * <p>
     * Get group ids.
     * </p>
     *
     * @author khadm
     * @return {@link List<String>}
     */
	public List<String> getGroupIds();
	
	///////////////////////NEW////////////////////////
	
	/**
     * <p>
     * Get qrtz M job store.
     * </p>
     *
     * @author khadm
     * @param pageable
     *            type {@link Pageable}
     * @return {@link Page<QrtzMJobStore>}
     */
	public Page<QrtzMJobStoreDto> getQrtzMJobStore(Pageable pageable);
	
	/**
     * <p>
     * Count qrtz M job store.
     * </p>
     *
     * @author khadm
     * @return {@link int}
     */
	public int countQrtzMJobStore();
	
}
