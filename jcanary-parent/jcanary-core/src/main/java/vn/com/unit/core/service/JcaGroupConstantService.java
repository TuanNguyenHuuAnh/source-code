/*******************************************************************************
 * Class        ：JcaGroupConstantService
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：tantm
 * Change log   ：2020/12/23：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaGroupConstantDto;
import vn.com.unit.core.dto.JcaGroupConstantSearchDto;
import vn.com.unit.core.entity.JcaGroupConstant;

/**
 * JcaGroupConstantService.
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface JcaGroupConstantService {
	JcaGroupConstant saveJcaGroupConstantDto(JcaGroupConstant objectDto);
//    /** The Constant TABLE_ALIAS_JCA_GROUP_CONSTANT. */
//    static final String TABLE_ALIAS_JCA_GROUP_CONSTANT = "grpc";
//
//    /**
//     * Save jca group constant.
//     *
//     * @param jcaGroupConstant
//     *            the jca group constant type JcaGroupConstant
//     * @return the jca group constant
//     * @author tantm
//     */
//    JcaGroupConstant saveJcaGroupConstant(JcaGroupConstant jcaGroupConstant);
//
//    /**
//     * Save jca group constant dto.
//     *
//     * @param objectDto
//     *            the object dto type JcaGroupConstantDto
//     * @return the jca group constant
//     * @author tantm
//     */
//    JcaGroupConstantDto saveJcaGroupConstantDto(JcaGroupConstantDto objectDto);
//
//    /**
//     * Gets the jca group constant by id.
//     *
//     * @param id
//     *            the id type Long
//     * @return the jca group constant by id
//     * @author tantm
//     */
//    JcaGroupConstant getJcaGroupConstantById(Long id);
//
//    /**
//     * Gets the jca group constant dto by id.
//     *
//     * @param id
//     *            the id type Long
//     * @return the jca group constant dto by id
//     * @author tantm
//     */
//    JcaGroupConstantDto getJcaGroupConstantDtoById(Long id);
//
//    /**
//     * Count jca group constant by condition.
//     *
//     * @param jcaGroupConstantSearchDto
//     *            the jca group constant search dto type JcaGroupConstantSearchDto
//     * @return the int
//     * @author tantm
//     */
//    int countJcaGroupConstantByCondition(JcaGroupConstantSearchDto jcaGroupConstantSearchDto);
//
//    /**
//     * Gets the list jca group constant dto by condition.
//     *
//     * @param jcaGroupConstantSearchDto
//     *            the jca group constant search dto type JcaGroupConstantSearchDto
//     * @param pageable
//     *            the pageable type Pageable
//     * @return the list jca group constant dto by condition
//     * @author tantm
//     */
//    List<JcaGroupConstantDto> getListJcaGroupConstantDtoByCondition(JcaGroupConstantSearchDto jcaGroupConstantSearchDto, Pageable pageable);
//
//    /**
//     * Gets the list jca group constant dto have constant by condition.
//     *
//     * @param jcaGroupConstantSearchDto
//     *            the jca group constant search dto type JcaGroupConstantSearchDto
//     * @param pageable
//     *            the pageable type Pageable
//     * @return the list jca group constant dto have constant by condition
//     * @author tantm
//     */
//    List<JcaGroupConstantDto> getListJcaGroupConstantDtoHaveConstantByCondition(JcaGroupConstantSearchDto jcaGroupConstantSearchDto,
//            Pageable pageable);
//
//    /**
//     * <p>
//     * Check group code exist.
//     * </p>
//     *
//     * @param code
//     *            type {@link String}
//     * @param companyId
//     *            type {@link Long}
//     * @return true, if successful
//     * @author tantm
//     */
//    boolean checkGroupCodeExists(String code, Long companyId);
}
