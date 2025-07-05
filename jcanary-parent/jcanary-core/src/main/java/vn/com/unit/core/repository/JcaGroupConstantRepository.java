/*******************************************************************************
 * Class        ：JcaGroupConstantRepository
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：tantm
 * Change log   ：2020/12/23：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaGroupConstantDto;
import vn.com.unit.core.dto.JcaGroupConstantSearchDto;
import vn.com.unit.core.entity.JcaGroupConstant;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaGroupConstantRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface JcaGroupConstantRepository extends DbRepository<JcaGroupConstant, Long> {

    /**
     * Gets the jca group constant dto by id.
     *
     * @param id
     *            the id type Long
     * @return the jca group constant dto by id
     * @author tantm
     */
    List<JcaGroupConstantDto> getJcaGroupConstantDtoById(@Param("id") Long id);

    /**
     * Count group constant dto by condition.
     *
     * @param jcaGroupConstantSearchDto
     *            the jca group constant search dto type JcaGroupConstantSearchDto
     * @return the int
     * @author tantm
     */
    Integer countJcaGroupConstantByCondition(@Param("jcaGroupConstantSearchDto") JcaGroupConstantSearchDto jcaGroupConstantSearchDto);

    /**
     * Gets the group constant dto by condition.
     *
     * @param jcaGroupConstantSearchDto
     *            the jca group constant search dto type JcaGroupConstantSearchDto
     * @param offset
     *            the offset type long
     * @param pageSize
     *            the page size type int
     * @return the group constant dto by condition
     * @author tantm
     */
    Page<JcaGroupConstantDto> getListJcaGroupConstantDtoByCondition(
            @Param("jcaGroupConstantSearchDto") JcaGroupConstantSearchDto jcaGroupConstantSearchDto, @Param("pageable") Pageable pageable);

    /**
     * <p>
     * Count jca group constant by group code.
     * </p>
     *
     * @param code
     *            type {@link String}
     * @param companyId
     *            type {@link Long}
     * @return {@link int}
     * @author tantm
     */
    int countJcaGroupConstantByGroupCode(@Param("code") String code, @Param("companyId") Long companyId);
    

    JcaGroupConstantDto getJcaGroupConstantDtoByGroupCode(@Param("code") String code);

	/**
	 * @param objectDto
	 */
    @Modifying
	void updateData(@Param("entity")JcaGroupConstant objectDto);

	/**
	 * @param objectDto
	 */
    @Modifying
	void createData(@Param("entity")JcaGroupConstant objectDto);
    
    JcaGroupConstantDto getJcaGroupConstantDtoByGroupCodeAndLang(@Param("code")String groupCode, @Param("langCode")String langCode);

}
