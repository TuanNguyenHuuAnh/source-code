/*******************************************************************************
 * Class        ：JcaConstantRepository
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
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.JcaConstantSearchDto;
import vn.com.unit.core.dto.JcaGroupConstantSearchDto;
import vn.com.unit.core.entity.JcaConstant;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaConstantRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface JcaConstantRepository extends DbRepository<JcaConstant, Long> {

    /**
     * Count constant dto by condition.
     *
     * @param jcaConstantSearchDto
     *            the jca constant search dto type JcaConstantSearchDto
     * @return the int
     * @author tantm
     */
    Integer countJcaConstantByCondition(@Param("jcaConstantSearchDto") JcaConstantSearchDto jcaConstantSearchDto);

    /**
     * Gets the constant dto by condition.
     *
     * @param jcaConstantSearchDto
     *            the jca constant search dto type JcaConstantSearchDto
     * @param pageable
     *            the pageable type Pageable
     * @return the constant dto by condition
     * @author tantm
     */
    Page<JcaConstantDto> getListJcaConstantDtoByCondition(@Param("jcaConstantSearchDto") JcaConstantSearchDto jcaConstantSearchDto,
            @Param("pageable") Pageable pageable);
    
    List<Select2Dto> getAllType(@Param("lang") String lang);
    
    boolean checkCodeExistsByGroupCodeAndKind(@Param("code") String code, @Param("groupCode") String groupCode,
            @Param("kind") String kind, @Param("langId") Long langId);
    
    JcaConstantDto getJcaConstantDtoByCodeAndGroupCodeAndKind(@Param("code") String code, @Param("groupCode") String groupCode, @Param("kind") String kind, @Param("lang") String lang);

    JcaConstant getJcaConstantByCodeAndGroupCodeAndKind(@Param("code") String code, @Param("groupCode") String groupCode, @Param("kind") String kind, @Param("langId") Long langId);
    List<JcaConstantDto> getListJcaConstantByGroupCodeAndKind(@Param("groupCode") String groupCode,@Param("kind") String kind, @Param("lang") String lang);
    List<JcaConstantDto> getListJcaConstantByKind(@Param("kind") String kind, @Param("lang") String lang);
    List<JcaConstantDto> getListJcaConstantByKindAndCode(@Param("kind") String kind,@Param("code") String code, @Param("lang") String lang);

    /**
     * Gets the list jca constant dto by group.
     *
     * @param jcaGroupConstantSearchDto
     *            the jca group constant search dto type JcaGroupConstantSearchDto
     * @param pageable
     *            the pageable type Pageable
     * @return the list jca constant dto by group
     * @author tantm
     */
    Page<JcaConstantDto> getListJcaConstantDtoByGroup(
            @Param("jcaGroupConstantSearchDto") JcaGroupConstantSearchDto jcaGroupConstantSearchDto, @Param("pageable") Pageable pageable);

    /**
     * <p>
     * Get constant text by code.
     * </p>
     *
     * @param code
     *            type {@link String}
     * @param lang
     *            type {@link String}
     * @param companyId
     *            type {@link Long}
     * @return {@link String}
     * @author tantm
     */
    String getConstantTextByCodeAndLang(@Param("code") String code, @Param("lang") String lang, @Param("companyId") Long companyId);

    /**
     * <p>
     * Get list jca constant dto by group code.
     * </p>
     *
     * @param groupCode
     *            type {@link String}
     * @param lang
     *            type {@link String}
     * @param companyId
     *            type {@link Long}
     * @return {@link List<JcaConstantDto>}
     * @author tantm
     */
    List<JcaConstantDto> getListJcaConstantDtoByGroupCodeAndLang(@Param("groupCode") String groupCode, @Param("lang") String lang);

    /**
     * <p>
     * Count jca constant by code.
     * </p>
     *
     * @param code
     *            type {@link String}
     * @param companyId
     *            type {@link Long}
     * @return {@link int}
     * @author tantm
     */
    int countJcaConstantByCode(@Param("code") String code, @Param("companyId") Long companyId);

    /**
     * <p>
     * Get list jca constant dto by group code.
     * </p>
     *
     * @param groupCode
     *            type {@link String}
     * @param companyId
     *            type {@link Long}
     * @return {@link List<JcaConstantDto>}
     * @author tantm
     */
    List<JcaConstantDto> getListJcaConstantDtoByGroupCode(@Param("groupCode") String groupCode, @Param("companyId") Long companyId);

	/**
	 * @param code
	 * @param groupCode
	 * @param kind
	 * @return
	 */
	List<JcaConstant> getListJcaConstantDtoByCodeGroupAndKind(@Param("code")String code
			, @Param("groupCode")String groupCode,@Param("kind") String kind);

	/**
	 * @param code
	 * @param groupCode
	 * @param kind
	 * @return
	 */
	JcaConstantDto getJcaConstantDtoByCodeGroupAndKind(@Param("code")String code
			, @Param("groupCode")String groupCode,@Param("kind") String kind);

	/**
	 * @param groupCode
	 * @return
	 */
	List<JcaConstantDto> getListJcaConstantDtoByGroupCodePivot(@Param("groupCode")String groupCode);

	/**
	 * @param jcaConstant
	 */
	@Modifying
    void updateData(@Param("entity") JcaConstant jcaConstant, @Param("oldGroupCode") String groupCode,
            @Param("oldKind") String kind, @Param("oldCode") String code);

    /**
     * @param jcaConstant
     */
	@Modifying
	void createData(@Param("entity")JcaConstant jcaConstant);

	String findGroupId(@Param("groupCode") String groupCode);
	
	String findGroupIdMax();
	
	
	// 2021 06 09 LocLT
    Integer countJcaConstantByConditionSearchLike(@Param("jcaConstantSearchDto") JcaConstantSearchDto jcaConstantSearchDto);
    Page<JcaConstantDto> getListJcaConstantDtoByConditionSearchLike(@Param("jcaConstantSearchDto") JcaConstantSearchDto jcaConstantSearchDto,
            @Param("pageable") Pageable pageable);
    
    @Modifying
    void deleteConstantDisplay(@Param("code")String code
			, @Param("groupCode")String groupCode,@Param("kind") String kind);
    
    List<JcaConstantDto> getListJcaConstantDisplayByType(@Param("type") String type);

}
