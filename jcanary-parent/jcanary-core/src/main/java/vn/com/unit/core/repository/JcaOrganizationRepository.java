/*******************************************************************************
 * Class        :JcaOrganizationRepository
 * Created date :2020/12/14
 * Lasted date  :2020/12/14
 * Author       :SonND
 * Change log   :2020/12/14:01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.core.dto.JcaOrganizationDto;
import vn.com.unit.core.entity.JcaOrganization;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaOrganizationRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface JcaOrganizationRepository extends DbRepository<JcaOrganization, Long> {
    
    /**
     * <p>
     * Get jca organization by id.
     * </p>
     *
     * @param orgId
     *            type {@link Long}
     * @return {@link JcaOrganization}
     * @author tantm
     */
    JcaOrganization getJcaOrganizationById(@Param("orgId") Long orgId);

    /**
     * <p>
     * Get list organization
     * </p>
     * .
     *
     * @return List<JcaOrganizationDto>
     * @author SonND
     */
    List<JcaOrganizationDto> getJcaOrganzitionDto();

    /**
     * <p>
     * Get JcaOrganizationDto by id
     * </p>
     * .
     *
     * @param id
     *            type {@link Long}
     * @return JcaOrganizationDto
     * @author SonND
     */
    JcaOrganizationDto getJcaOrganizationDtoById(@Param("id") Long id);

    /**
     * <p>
     * Count jca organization dto by org code.
     * </p>
     *
     * @param orgCode
     *            type {@link String}
     * @return {@link int}
     * @author sonnd
     */
    int countJcaOrganizationDtoByOrgCode(@Param("orgCode") String orgCode);
    
    /**
     * <p>
     * Get jca organization dto child by parent id and depth.
     * </p>
     *
     * @param parentId
     *            type {@link Long}
     * @param depth
     *            type {@link Long}
     * @return {@link List<JcaOrganizationDto>}
     * @author TrieuVD
     */
    public List<JcaOrganizationDto> getJcaOrganizationDtoChildByParentIdAndDepth(@Param("parentId") Long parentId, @Param("depth") Long depth);
}