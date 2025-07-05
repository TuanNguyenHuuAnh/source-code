/*******************************************************************************
 * Class        ：MathExpressionRepository
 * Created date ：2017/06/21
 * Lasted date  ：2017/06/21
 * Author       ：thuydtn
 * Change log   ：2017/06/21：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.dto.MathExpressionDto;
import vn.com.unit.cms.admin.all.dto.MathExpressionSearchDto;
import vn.com.unit.cms.admin.all.entity.MathExpression;
import vn.com.unit.db.repository.DbRepository;

/**
 * MathExpressionRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public interface MathExpressionRepository extends DbRepository<MathExpression, Long> {

	/**
	 * @param lOAN_INTEREST_EXPRESSION_TYPE
	 * @param customerTypeId
	 * @return
	 */
	public MathExpression findExpressionByTypeAndCustomer(@Param("expressionType") String expressionType,
			@Param("customerTypeId") Long customerTypeId);

	/**
	 * @param expressionType
	 */
	public List<MathExpressionDto> findExpressionByType(@Param("expressionType") String expressionType);

	/**
	 * @param customerTypeId
	 * @param expressionType
	 * @param subType
	 * @return
	 */
	public MathExpression findExpressionByCustomerAndTypeAndSubType(@Param("customerTypeId") Long customerTypeId,
			@Param("expressionType") String expressionType, @Param("subType") String subType);

	/**
	 * @param searchDto
	 * @param languageCode
	 * @return
	 */
	public int countBySearchDto(@Param("searchDto") MathExpressionSearchDto searchDto);

	/**
	 * @param offsetSQL
	 * @param sizeOfPage
	 * @param searchDto
	 * @return
	 */
	public List<MathExpressionDto> findBySearchDto(@Param("offset") int offsetSQL, @Param("limit") int sizeOfPage,
			@Param("searchDto") MathExpressionSearchDto searchDto);

	/**
	 * 
	 * @param code
	 * @return
	 */
	public int countByCode(@Param("code") String code);

	/**
	 * @param id
	 * @return
	 */
	public MathExpressionDto findExpressionViewDtoById(@Param("id") Long id);

	/**
	 * @param id
	 * @param availableDateFrom
	 * @param availableDateTo
	 * @param expressionType
	 * @return
	 */
	public MathExpressionDto findExpressionWithTypeAndEfftectTimeExceptId(@Param("exceptId") Long id,
			@Param("availableDateFrom") Date availableDateFrom, @Param("availableDateTo") Date availableDateTo,
			@Param("expressionType") String expressionType, @Param("cusTypeIdText") String cusTypeIdText);

	/**
	 * getMaxCode
	 *
	 * @author nhutnn
	 */
	String getMaxCode();

	/**
	 * @param mathExpression list by expressionType and customerTypeId
	 */
	public List<MathExpressionDto> findExpressionByExpressionTypeAndCustomerTypeId(
			@Param("expressionType") String expressionType, @Param("customerTypeId") String customerTypeId,
			@Param("status") Integer status);

	public MathExpression findByCode(@Param("code") String code);

	public List<MathExpressionDto> ExportExcelfindBySearchDto(@Param("searchDto") MathExpressionSearchDto searchDto);

	public int countDependencies(@Param("mathId") Long productId, @Param("lstStatus") List<Long> lstStatus);

	/**
	 * Danh sách tổng số con phụ thuộc
	 * 
	 * @author tranlth - 22/05/2019
	 * @param mathId
	 * @param lstStatus
	 * 
	 * @return List<Map <String, String>>
	 */
	public List<Map<String, String>> listDependencies(@Param("mathId") Long productId,
			@Param("lstStatus") List<Long> lstStatus);

}
