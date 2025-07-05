/*******************************************************************************
 * Class        :JcaOrganizationPathRepository
 * Created date :2020/12/10
 * Lasted date  :2020/12/10
 * Author       :SonND
 * Change log   :2020/12/10:01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaOrganizationPathDto;
import vn.com.unit.core.entity.JcaOrganizationPath;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaOrganizationPathRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface JcaOrganizationPathRepository extends DbRepository<JcaOrganizationPath, Long> {

    /**
     * <p>
     * Get JcaOrganizationPathDto by descendantId
     * </p>
     * .
     *
     * @param descendantId
     *            type {@link Long}
     * @return JcaOrganizationPathDto
     * @author SonND
     */
    JcaOrganizationPathDto getJcaOrganizationPathDtoByDescendantId(@Param("descendantId") Long descendantId);

    /**
     * <p>
     * Gets the jca organization path dto by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return the jca organization path dto by id
     * @author SonND
     */
    JcaOrganizationPathDto getJcaOrganizationPathDtoById(@Param("id")Long id);

    /**
     * <p>
     * Delete organization path by descendant id.
     * </p>
     *
     * @param descendantId
     *            type {@link Long}
     * @author sonnd
     */
    @Modifying
    void deleteOrganizationPathByDescendantId(@Param("descendantId") Long descendantId);

}