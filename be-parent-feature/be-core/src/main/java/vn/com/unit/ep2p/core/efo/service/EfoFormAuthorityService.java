/*******************************************************************************
 * Class        FormService
 * Created date 2019/04/12
 * Lasted date  2019/04/12
 * Author       NhanNV
 * Change log   2019/04/12 01-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.service;

import java.util.List;

import vn.com.unit.ep2p.core.efo.dto.EfoFormAuthorityDto;

/**
 * FormService.
 *
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */
public interface EfoFormAuthorityService {

    static final String TABLE_ALIAS_EFO_FORM_AUTHORITY = "formAuthority";

    /**
     * <p>
     * Gets the efo form authority dtos by business id and role id.
     * </p>
     *
     * @param businessId
     *            type {@link Long}
     * @param roleId
     *            type {@link Long}
     * @return the efo form authority dtos by business id and role id
     * @author KhuongTH
     */
    List<EfoFormAuthorityDto> getEfoFormAuthorityDtosByBusinessIdAndRoleId(Long businessId, Long roleId);

    /**
     * <p>
     * Save efo form authority dtos by role id.
     * </p>
     *
     * @param efoFormAuthorityDtos
     *            type {@link List<EfoFormAuthorityDto>}
     * @param businessId
     *            type {@link Long}
     * @param roleId
     *            type {@link Long}
     * @return true, if successful
     * @author KhuongTH
     */
    boolean saveEfoFormAuthorityDtosByRoleId(List<EfoFormAuthorityDto> efoFormAuthorityDtos, Long businessId, Long roleId);
}
