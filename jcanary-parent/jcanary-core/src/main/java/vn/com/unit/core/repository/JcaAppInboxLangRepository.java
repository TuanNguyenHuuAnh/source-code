/*******************************************************************************
 * Class        ：JcaAppInboxLangRepository
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：TrieuVD
 * Change log   ：2021/02/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.Date;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaAppInboxLangDto;
import vn.com.unit.core.entity.JcaAppInboxLang;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaAppInboxLangRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface JcaAppInboxLangRepository extends DbRepository<JcaAppInboxLang, Long> {

    /**
     * <p>
     * Get jca app inbox lang dto by app inbox id.
     * </p>
     *
     * @param appInboxId
     *            type {@link Long}
     * @return {@link JcaAppInboxLangDto}
     * @author SonND
     */
    JcaAppInboxLangDto getJcaAppInboxLangDtoByAppInboxId(@Param("appInboxId") Long appInboxId);

    /**
     * <p>
     * Delete all jca app inbox lang dto by app inbox id.
     * </p>
     *
     * @param appInboxId
     *            type {@link Long}
     * @author SonND
     */
    @Modifying
    void deleteAllJcaAppInboxLangDtoByAppInboxId(@Param("userId") Long userId,@Param("sysDate") Date sysDate);

    /**
     * <p>
     * Delete jca app inbox lang dto by app inbox id.
     * </p>
     *
     * @param appInboxId
     *            type {@link Long}
     * @param userId
     *            type {@link Long}
     * @param sysDate
     *            type {@link Date}
     * @author SonND
     */
    @Modifying
    void deleteJcaAppInboxLangDtoByAppInboxId(@Param("appInboxId")Long appInboxId,@Param("userId") Long userId,@Param("sysDate") Date sysDate);

}
