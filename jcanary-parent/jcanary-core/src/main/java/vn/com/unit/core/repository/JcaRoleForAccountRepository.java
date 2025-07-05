/*******************************************************************************
 * Class        :JcaRoleForAccountRepository
 * Created date :2021/01/21
 * Lasted date  :2021/01/21
 * Author       :SonND
 * Change log   :2021/01/21:01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaRoleForAccountDto;
import vn.com.unit.core.entity.JcaRoleForAccount;
import vn.com.unit.db.repository.DbRepository;


/**
 * JcaRoleForAccountRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface JcaRoleForAccountRepository extends DbRepository<JcaRoleForAccount, Long> {
    
    /**
     * <p>
     * Gets the jca role for account dto by role for account id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return the jca role for account dto by role for account id
     * @author sonnd
     */
    JcaRoleForAccountDto getJcaRoleForAccountDtoById(@Param("id") Long id);

    /**
     * <p>
     * Gets the jca role for account dto by user id.
     * </p>
     *
     * @param userId
     *            type {@link Long}
     * @return the jca role for account dto by user id
     * @author sonnd
     */
    List<JcaRoleForAccountDto> getJcaRoleForAccountDtoByUserId(@Param("userId") Long userId);

    /**
     * <p>
     * Delete jca role for account by user id.
     * </p>
     *
     * @param userId
     *            type {@link Long}
     * @author sonnd
     */
    @Modifying
    void deleteJcaRoleForAccountByUserId(@Param("userId")Long userId);
    
    /**
     * <p>
     * Delete jca role for account by ids.
     * </p>
     *
     * @param userId
     *            type {@link Long}
     * @param ids
     *            type {@link List<Long>}
     * @param userLoginId
     *            type {@link Long}
     * @param sysDate
     *            type {@link Date}
     * @author sonnd
     */
    @Modifying
    void deleteJcaRoleForAccountByIds(@Param("userId")Long userId, @Param("ids")List<Long> ids, @Param("userLoginId") Long userLoginId, @Param("sysDate") Date sysDate);
    
    
}