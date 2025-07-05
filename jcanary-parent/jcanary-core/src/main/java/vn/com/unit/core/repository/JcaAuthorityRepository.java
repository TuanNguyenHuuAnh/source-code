/*******************************************************************************
 * Class        ：JcaAuthorityRepository
 * Created date ：2020/12/24
 * Lasted date  ：2020/12/24
 * Author       ：ngannh
 * Change log   ：2020/12/24：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaAuthorityDto;
import vn.com.unit.core.dto.JcaAuthorityProcessSearchDto;
import vn.com.unit.core.dto.JcaAuthoritySearchDto;
import vn.com.unit.core.entity.JcaAuthority;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaAuthorityRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
public interface JcaAuthorityRepository extends DbRepository<JcaAuthority, Long>{

    /**
     * countAuthorityByCondition.
     *
     * @param reqSearch
     *            the req search
     * @return the int
     * @author ngannh
     */
     public int countAuthorityByCondition(@Param("jcaAuthoritySearchDto") JcaAuthoritySearchDto reqSearch);
     
    
     /**
      * Gets the authority dto by condition.
      *
      * @param jcaAuthoritySearchDto
      *            the jca authority search dto
      * @param pageable
      *            the pageable
      * @return the authority dto by condition
      * @author ngannh
      */
     Page<JcaAuthorityDto> getAuthorityDtoByCondition(@Param("jcaAuthoritySearchDto") JcaAuthoritySearchDto jcaAuthoritySearchDto,            
             Pageable pageable);


    /**
     * deleteAuthorityDtoByRoleIdAndFunctionType.
     *
     * @param roleId
     *            the role id
     * @param funcType
     *            the func type
     * @author ngannh
     */
     @Modifying
    public void deleteAuthorityDtoByRoleIdAndFunctionType(@Param("roleId") Long roleId,@Param("funcType") String funcType);


    /**
     * Gets the jca authority dto by id.
     *
     * @param id
     *            the id
     * @return the jca authority dto by id
     * @author ngannh
     */
    public JcaAuthorityDto getJcaAuthorityDtoById(@Param("authorityId") Long id);

    /**
     * <p>
     * getAuthorityByProcess
     * </p>
     * .
     *
     * @param reqSearch
     *            type {@link JcaAuthorityProcessSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link List<JcaAuthorityDto>}
     * @author KhuongTH
     */
    List<JcaAuthorityDto> getAuthorityByProcess(@Param("searchDto") JcaAuthorityProcessSearchDto reqSearch, Pageable pageable);
    
    /**
     * <p>
     * Count authority by process.
     * </p>
     *
     * @param reqSearch
     *            type {@link JcaAuthorityProcessSearchDto}
     * @return {@link int}
     * @author KhuongTH
     */
    int countAuthorityByProcess(@Param("searchDto") JcaAuthorityProcessSearchDto reqSearch);
    
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
    public List<JcaAuthorityDto> getJcaRoleForItemByRoleId(@Param("roleId")Long roleId, @Param("companyId") Long companyId);
}
