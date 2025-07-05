/*******************************************************************************
 * Class        ：JcaAppInboxRepository
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：TrieuVD
 * Change log   ：2021/02/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaAppInboxDto;
import vn.com.unit.core.entity.JcaAppInbox;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaAppInboxRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface JcaAppInboxRepository extends DbRepository<JcaAppInbox, Long> {

    int countJcaAppInbox(@Param("userId")Long userId);
    
    /**
     * <p>
     * Get jca app inbox dto by user id.
     * </p>
     *
     * @param userId
     *            type {@link Long}
     * @return {@link List<JcaAppInboxDto>}
     * @author SonND
     */
    Page<JcaAppInboxDto> getJcaAppInboxDtoByUserId(@Param("userId") Long userId, Pageable pageable);
    
    /**
     * <p>
     * Get jca app inbox dto by app inbox id.
     * </p>
     *
     * @param appInboxId
     *            type {@link Long}
     * @return {@link JcaAppInboxDto}
     * @author SonND
     */
    JcaAppInboxDto getJcaAppInboxDtoByAppInboxId(@Param("appInboxId") Long appInboxId);

    /**
     * <p>
     * Delete all jca app inbox dto by user id.
     * </p>
     *
     * @param userId
     *            type {@link Long}
     * @param sysDate
     *            type {@link Date}
     * @author SonND
     */
    @Modifying
    void deleteAllJcaAppInboxDtoByUserId(@Param("userId") Long userId, @Param("sysDate") Date sysDate);

    @Modifying
    void readAllJcaAppInbox(@Param("userId")Long userId, @Param("readFlag") boolean readFlag);

}
