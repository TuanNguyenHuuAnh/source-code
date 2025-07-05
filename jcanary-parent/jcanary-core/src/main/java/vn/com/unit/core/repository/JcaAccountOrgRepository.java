/*******************************************************************************
 * Class        :JcaAccountOrgRepository
 * Created date :2020/12/16
 * Lasted date  :2020/12/16
 * Author       :SonND
 * Change log   :2020/12/16:01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaAccountOrgDto;
import vn.com.unit.core.dto.JcaAccountOrgSearchDto;
import vn.com.unit.core.entity.JcaAccountOrg;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaAccountOrgRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface JcaAccountOrgRepository extends DbRepository<JcaAccountOrg, Long> {

    /**
     * <p>
     * Count jca account org dto by condition.
     * </p>
     *
     * @param jcaAccountOrgSearchDto
     *            type {@link JcaAccountOrgSearchDto}
     * @return {@link int}
     * @author sonnd
     */
    int countJcaAccountOrgDtoByCondition(@Param("jcaAccountOrgSearchDto") JcaAccountOrgSearchDto jcaAccountOrgSearchDto);
    
    /**
     * <p>
     * Gets the jca account org dto by condition.
     * </p>
     *
     * @param jcaAccountOrgSearchDto
     *            type {@link JcaAccountOrgSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return the jca account org dto by condition
     * @author sonnd
     */
    List<JcaAccountOrgDto> getJcaAccountOrgDtoByCondition(@Param("jcaAccountOrgSearchDto") JcaAccountOrgSearchDto jcaAccountOrgSearchDto, Pageable pageable);

    /**
     * <p>
     * Gets the jca account org dto by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return the jca account org dto by id
     * @author sonnd
     */
    JcaAccountOrgDto getJcaAccountOrgDtoById(@Param("id") Long id);

    /**
     * <p>
     * Gets the jca account org dto by account id.
     * </p>
     *
     * @param accountId
     *            type {@link Long}
     * @return the jca account org dto by account id
     * @author sonnd
     */
    List<JcaAccountOrgDto> getJcaAccountOrgDtoByAccountId(@Param("accountId")Long accountId);
    
    /**
     * <p>
     * Get main jca account org dto by account id.
     * </p>
     *
     * @param accountId
     *            type {@link Long}
     * @return {@link JcaAccountOrgDto}
     * @author tantm
     */
    JcaAccountOrgDto getMainJcaAccountOrgDtoByAccountId(@Param("accountId")Long accountId);
    
    /**
     * <p>
     * Delete jca account org by PK.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @param orgId
     *            type {@link Long}
     * @param positionId
     *            type {@link Long}
     * @author tantm
     */
    @Modifying
    void deleteJcaAccountOrgByPK(@Param("accountId")Long id, @Param("orgId")Long orgId, @Param("positionId")Long positionId);

}