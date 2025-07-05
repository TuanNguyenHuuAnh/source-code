/*******************************************************************************
 * Class        ：JcaRoleForItemService
 * Created date ：2020/12/24
 * Lasted date  ：2020/12/24
 * Author       ：ngannh
 * Change log   ：2020/12/24：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaAuthorityDto;
import vn.com.unit.core.dto.JcaAuthorityProcessSearchDto;
import vn.com.unit.core.dto.JcaAuthoritySearchDto;
import vn.com.unit.core.entity.JcaAuthority;
import vn.com.unit.db.service.DbRepositoryService;

/**
 * JcaRoleForItemService.
 *
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
public interface JcaAuthorityService extends DbRepositoryService<JcaAuthority, Long>{
    /** The Constant TABLE_ALIAS_JCA_ROLE_FOR_ITEM. */
    static final String TABLE_ALIAS_JCA_AUTHORITY = "item";
    /**
     * getAuthorityDtoByCondition.
     *
     * @param reqSearch
     *            the req search
     * @param pageableAfterBuild
     *            the pageable after build
     * @return the role for company dto by condition
     * @author ngannh
     */
    List<JcaAuthorityDto> getAuthorityDtoByCondition(JcaAuthoritySearchDto reqSearch, Pageable pageableAfterBuild);

    /**
     * countAuthorityDtoByCondition.
     *
     * @param reqSearch
     *            the req search
     * @return the int
     * @author ngannh
     */
    int countAuthorityDtoByCondition(JcaAuthoritySearchDto reqSearch);

    /**
     * saveJcaAuthorityDto.
     *
     * @param objectDto
     *            the object dto
     * @return the jca role for company
     * @author ngannh
     */
    public JcaAuthority saveJcaAuthorityDto(JcaAuthorityDto objectDto);

    
    /**
     * Gets the jca authority dto by id.
     *
     * @param id
     *            the id
     * @return the jca authority dto by id
     * @author ngannh
     */
    JcaAuthorityDto getJcaAuthorityDtoById(Long id);


    /**
     * Gets the jca authority by id.
     *
     * @param id
     *            the id
     * @return the jca authority by id
     * @author ngannh
     */
    JcaAuthority getJcaAuthorityById(Long id);
    
    /**
     * Delete authority dto by role id and function type.
     *
     * @param roleId
     *            the role id
     * @param functionType
     *            the function type
     * @author ngannh
     */
    public void deleteAuthorityDtoByRoleIdAndFunctionType(Long roleId,
            String functionType);

    /**
     * <p>
     * Gets the authority by process.
     * </p>
     *
     * @param reqSearch
     *            type {@link JcaAuthorityProcessSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return the authority by process
     * @author KhuongTH
     */
    List<JcaAuthorityDto> getAuthorityByProcess(JcaAuthorityProcessSearchDto reqSearch, Pageable pageable);

    /**
     * <p>
     * countAuthorityByProcess
     * </p>
     * .
     *
     * @param reqSearch
     *            type {@link JcaAuthorityProcessSearchDto}
     * @return {@link int}
     * @author KhuongTH
     */
    int countAuthorityByProcess(JcaAuthorityProcessSearchDto reqSearch);

    /**
     * <p>
     * Get jca role for item by role id.
     * </p>
     *
     * @param roleId
     *            type {@link Long}
     * @param companyId
     *            type {@link Long}
     * @return {@link List<JcaItemDto>}
     * @author SonND
     */
    List<JcaAuthorityDto> getJcaRoleForItemByRoleId(Long roleId, Long companyId);
}
