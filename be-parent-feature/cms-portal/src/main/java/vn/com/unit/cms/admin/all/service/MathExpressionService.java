/*******************************************************************************
 * Class        ：LoanInterestExpressionService
 * Created date ：2017/06/21
 * Lasted date  ：2017/06/21
 * Author       ：thuydtn
 * Change log   ：2017/06/21：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vn.com.unit.cms.admin.all.dto.MathExpressionDto;
import vn.com.unit.cms.admin.all.dto.MathExpressionTypeDto;
import vn.com.unit.cms.admin.all.entity.MathExpression;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.SearchKeyDto;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;
//import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;
//import vn.com.unit.common.dto.SearchKeyDto;
import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;

/**
 * LoanInterestExpressionService
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public interface MathExpressionService extends DocumentWorkflowCommonService<MathExpressionDto, MathExpressionDto> {

	/**
	 * @param expressionModel
	 * @param status
	 * @return
	 */
//    public MathExpressionDto saveEditExpressionModel(MathExpressionDto expressionModel, Locale locale, HttpServletRequest request);

	/**
	 * @param id
	 * @return
	 */
	public MathExpressionDto getExpression(Long id, Locale locale);

	/**
	 * @param id
	 */
	public void deleteById(Long id);

	/**
	 * @param page
	 * @param searchDto
	 * @param languageCode
	 * @return
	 */
	public PageWrapper<MathExpressionDto> searchExpression(int page, CommonSearchDto searchDto, Long customerTypeId,
			Locale locale);

	/**
	 * 
	 * @return
	 */
	public MathExpressionDto initExpressionAddModel(String customerAlias,Locale locale);

	/**
	 * @param expressionModel
	 * @return
	 */
	public MathExpressionDto saveAddExpressionModel(MathExpressionDto expressionModel, Locale locale,
			HttpServletRequest request);

	/**
	 * @param code
	 * @param id
	 * @return
	 */
	public int countByCode(String code);

	/**
	 * 
	 * @param locale
	 * @return
	 */
	public List<SearchKeyDto> genSearchKeyList(Locale locale);

	/**
	 * 
	 * @return
	 */
	public List<MathExpressionTypeDto> initExpressionTypeSelection();

	/**
	 * 
	 * @param id
	 * @return
	 */
	public MathExpressionDto getExpressionViewModel(Long id);

	/**
	 * @param id
	 * @param availableDateFrom
	 * @param availableDateTo
	 * @param expressionType
	 * @return
	 */
	public MathExpressionDto getExpressionConflictEffectedDateAndType(Long id, Date availableDateFrom,
			Date availableDateTo, String expressionType, String cusTypeIdText);

	/**
	 * @param expressionModel
	 * @return
	 */
//	public MathExpressionDto saveEditSubmitExpressionModel(MathExpressionDto expressionModel,Locale locale, HttpServletRequest request);

	/**
	 * @param expressionModel
	 * @return
	 */
	public boolean saveAddDraftExpressionModel(MathExpressionDto expressionModel, Locale locale,
			HttpServletRequest request) throws Exception;

	/**
	 * @param expressionModel
	 * @return
	 */
	public MathExpressionDto saveAddSubmitExpressionModel(MathExpressionDto expressionModel, Locale locale,
			HttpServletRequest request);

	/**
	 * @param expressionModel
	 * @return
	 */
	public boolean saveEditDraftExpressionModel(MathExpressionDto expressionModel, Locale locale,
			HttpServletRequest request) throws Exception;

	/**
	 * @param expressionModel
	 * @return
	 */
	public MathExpressionDto saveApproveExpressionModel(MathExpressionDto expressionModel);

	/**
	 * @param expressionModel
	 * @return
	 */
	public MathExpressionDto saveRejectExpressionModel(MathExpressionDto expressionModel);

	/**
	 * 
	 * @param expressionModel
	 * @return
	 */
	public MathExpressionDto saveSubmitExpressionModel(MathExpressionDto expressionModel);

	/**
	 * getMaxCode
	 *
	 * @author nhutnn
	 */
	String getMaxCode();

	/**
	 * findExpressionByExpressionTypeAndCustomerTypeId
	 *
	 * @author diennv
	 */
	public List<MathExpressionDto> findExpressionByExpressionTypeAndCustomerTypeId(String expressionType,
			String customerTypeId);

	public List<MathExpressionDto> getExpressionByCustomerTypeId(Long customerId,Integer status);

	public MathExpression findByCode(String code);

	public void exportExcelMathExpression(CommonSearchDto commonSearchDto, HttpServletResponse response,
			Long customerTypeId, Locale locale);
	
	public int countDependencies(Long mathExpressionId, List<Long> lstStatus);
	
	/** Get list dependencies of Product categories
     * @author tranlth - 23/05/219
     * @param mathExpressionId
     * @param lstStatus
     * @return List<Map <String, String>>
     */
    public List<Map <String, String>> listDependencies(Long mathExpressionId, List<Long> lstStatus);
}
