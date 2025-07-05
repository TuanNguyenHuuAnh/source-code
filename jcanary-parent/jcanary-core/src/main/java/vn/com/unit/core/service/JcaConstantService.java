/*******************************************************************************
 * Class        ：JcaConstantService
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：tantm
 * Change log   ：2020/12/23：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.JcaConstantSearchDto;
import vn.com.unit.core.dto.JcaGroupConstantDto;
import vn.com.unit.core.entity.JcaConstant;
import vn.com.unit.core.entity.JcaGroupConstant;
import vn.com.unit.db.service.DbRepositoryService;

/**
 * JcaConstantService.
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface JcaConstantService extends DbRepositoryService<JcaConstant, Long>{

    /** The Constant TABLE_ALIAS_JCA_GROUP_CONSTANT. */
    static final String TABLE_ALIAS_JCA_CONSTANT = "con";

	/**
	 * countJcaConstantByCondition
	 * @param jcaConstantSearchDto
	 * @return
	 * @author Tan Tai
	 */
	int countJcaConstantByCondition(JcaConstantSearchDto jcaConstantSearchDto);

	/**
	 * getListJcaConstantDtoByCondition
	 * @param jcaConstantSearchDto
	 * @param pageable
	 * @return
	 * @author Tan Tai
	 */
	List<JcaConstantDto> getListJcaConstantDtoByCondition(JcaConstantSearchDto jcaConstantSearchDto, Pageable pageable);

	/**
	 * checkCodeExistsByGroupCodeAndKind
	 * @param code
	 * @param groupCode
	 * @param kind
	 * @return
	 * @author Tan Tai
	 */
    boolean checkCodeExistsByGroupCodeAndKind(String code, String groupCode, String kind, Long langId);

	/**
	 * saveJcaConstantDto
	 * @param objectDto
	 * @return
	 * @author Tan Tai
	 */
	JcaConstant saveJcaConstantDto(JcaConstantDto objectDto);

	/**
	 * saveJcaConstant
	 * @param jcaConstant
	 * @return
	 * @author Tan Tai
	 */
	JcaConstant saveJcaConstant(JcaConstant jcaConstant,JcaConstantDto objectDto);

	/**
	 * getListJcaConstantDtoByGroupCodeAndLang
	 * @param groupCode
	 * @param langCode
	 * @return
	 * @author Tan Tai
	 */
	List<JcaConstantDto> getListJcaConstantDtoByGroupCodeAndLang(String groupCode, String langCode);

	/**
	 * getListJcaConstantDtoByGroupCodeAndKind
	 * @param groupCode
	 * @param kind
	 * @param langCode
	 * @return
	 * @author Tan Tai
	 */
	List<JcaConstantDto> getListJcaConstantDtoByGroupCodeAndKind(String groupCode, String kind, String langCode);

	/**
	 * getJcaConstantDtoByCodeAndGroupCodeAndKind
	 * @param code
	 * @param groupCode
	 * @param kind
	 * @param langCode
	 * @return
	 * @author Tan Tai
	 */
	JcaConstantDto getJcaConstantDtoByCodeAndGroupCodeAndKind(String code, String groupCode, String kind,
			String langCode);

	/**
	 * getListJcaConstantDtoByKind
	 * @param kind
	 * @param langCode
	 * @return
	 * @author Tan Tai
	 */
	List<JcaConstantDto> getListJcaConstantDtoByKind(String kind, String langCode);

	/**
	 * getListJcaConstantDtoByKindAndCode
	 * @param kind
	 * @param code
	 * @param langCode
	 * @return
	 * @author Tan Tai
	 */
	List<JcaConstantDto> getListJcaConstantDtoByKindAndCode(String kind, String code, String langCode);

	/**
	 * getAllType
	 * @param lang
	 * @return
	 * @author Tan Tai
	 */
	List<Select2Dto> getAllType(String lang);
	/**
	 * vunt gen screen constant
	 * @param page
	 * @param searchDto
	 * @param pageSize
	 * @return
	 */
	public void doDeleteConstantByCodeAndKind(String code, String groupCode, String kind);
	
	public String validateDeleteConstantByCodeAndKind(String code, String groupCode, String kind);


	List<JcaConstant> getListJcaConstantDtoByCodeGroupAndKind(String code, String groupCode, String kind);

	/**
	 * @param code
	 * @param groupCode
	 * @param kind
	 * @return
	 */
	JcaConstantDto getJcaConstantDtoByCodeGroupAndKind(String code, String groupCode, String kind);

	/**
	 * @param groupCode
	 * @return
	 */
	JcaGroupConstantDto getAllJcaConstantDtoByGroupCode(String groupCode);

	/**
	 * @param groupCode
	 * @return
	 */
	JcaGroupConstantDto getJcaGroupConstantDtoByGroupCode(String groupCode);

	/**
	 * @param objectDto
	 * @return
	 */
	JcaGroupConstant saveJcaGroupConstantDto(JcaGroupConstant objectDto);
	
	JcaGroupConstantDto getJcaGroupConstantDtoByGroupCodeAndLang(String groupCode, String langCode);
	
	List<JcaConstantDto> getListJcaConstantDisplayByType(String type);
	    
}
