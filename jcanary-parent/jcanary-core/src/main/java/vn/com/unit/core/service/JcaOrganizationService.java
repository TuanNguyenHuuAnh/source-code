/*******************************************************************************
 * Class        :JcaOrganizationService
 * Created date :2020/12/14
 * Lasted date  :2020/12/14
 * Author       :SonND
 * Change log   :2020/12/14 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import vn.com.unit.core.dto.JcaOrganizationDto;
import vn.com.unit.core.entity.JcaOrganization;

/**
 * JcaOrganizationService.
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
public interface JcaOrganizationService {

    /**
     * <p>
     * Get list JcaOrganizationDto
     * </p>
     * .
     *
     * @return List<JcaOrganizationDto>
     * @author SonND
     */
    public List<JcaOrganizationDto> getJcaOrganizationDto();

    /**
     * <p>
     * Save entity with create, update
     * </p>
     * .
     *
     * @param jcaOrganization
     *            type {@link JcaOrganization}
     * @return JcaOrganization
     * @author SonND
     */
    public JcaOrganization saveJcaOrganization(JcaOrganization jcaOrganization);

    /**
     * <p>
     * Save jcaOrganization with jcaOrganizationDto
     * </p>
     * .
     *
     * @param jcaOrganizationDto
     *            type {@link JcaOrganizationDto}
     * @return JcaOrganization
     * @author SonND
     */
    public JcaOrganization saveJcaOrganizationDto(JcaOrganizationDto jcaOrganizationDto);

    /**
     * <p>
     * Get jcaOrganizationDto by id
     * </p>
     * .
     *
     * @param id
     *            type {@link Long}
     * @return JcaOrganizationDto
     * @author SonND
     */
    public JcaOrganizationDto getJcaOrganizationDtoById(Long id);

    /**
     * <p>
     * Get JcaOrganization by id
     * </p>
     * .
     *
     * @param id
     *            type {@link Long}
     * @return JcaOrganization
     * @author SonND
     */
    public JcaOrganization getJcaOrganizationById(Long id);

    /**
     * <p>
     * Delete jca organization by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @author SonND
     */
    public void deleteJcaOrganizationById(Long id);
    
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
    public int countJcaOrganizationDtoByOrgCode(String orgCode);
    
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
    public List<JcaOrganizationDto> getJcaOrganizationDtoChildByParentIdAndDepth(Long parentId, Long depth);
}
