/*******************************************************************************
 * Class        ：DocumentTypeEditValidator
 * Created date ：2016/04/18
 * Lasted date  ：2016/04/18
 * Author       ：thuydtn
 * Change log   ：2017/03/01：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.cms.admin.all.dto.MathExpressionDto;
import vn.com.unit.cms.admin.all.entity.MathExpression;
import vn.com.unit.cms.admin.all.service.MathExpressionService;

/**
 * DocumentTypeEditValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@Component
public class MathExpressionAddValidator implements Validator {

	/** documentTypeService */
	@Autowired
	MathExpressionService mathExpressionService;

	@Override
	public boolean supports(Class<?> clazz) {
		return MathExpressionDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// DocumentTypeEditDto
		MathExpressionDto dto = (MathExpressionDto) target;
		// check exists code of Document
		checkExistsCode(dto, errors);
		checkExistsExpressionInTime(dto, errors);
	}

	/**
	 * check exists code of DocumentType
	 *
	 * @param dto
	 *            DocumentTypeEditDto
	 * @param errors
	 * @author thuydtn
	 */
	private void checkExistsCode(MathExpressionDto dto, Errors errors) {
		String code = dto.getCode();
		MathExpression entity = mathExpressionService.findByCode(code);
		if(entity != null) {
			 Long mathId = dto.getId();
	         Long idDb = entity.getId();
	         if (mathId == null || !mathId.equals(idDb)){
				String[] errorArgs = new String[1];
				errorArgs[0] = code;
				errors.rejectValue("code", "message.error.math-expression.code.existed", errorArgs, ConstantCore.EMPTY);
			}
		}else {
			int itemCount = mathExpressionService.countByCode(code);
			if (itemCount > 0) {
				String[] errorArgs = new String[1];
				errorArgs[0] = code;
				errors.rejectValue("code", "message.error.math-expression.code.existed", errorArgs, ConstantCore.EMPTY);
			}
		}
	}

	private void checkExistsExpressionInTime(MathExpressionDto dto, Errors errors) {
		MathExpressionDto conflictExpression = mathExpressionService.getExpressionConflictEffectedDateAndType(
				dto.getId(), dto.getAvailableDateFrom(), dto.getAvailableDateTo(), dto.getExpressionType(), dto.getStrCustomerTypeId());
		if (conflictExpression != null) {
			if (!conflictExpression.getId().equals(dto.getId())) {
				String[] errorArgs = new String[3];
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				errorArgs[0] = df.format(conflictExpression.getAvailableDateFrom());
				errorArgs[1] = df.format(conflictExpression.getAvailableDateTo());
				errorArgs[2] = conflictExpression.getCode();
				errors.rejectValue("availableDateTo", "message.math-expression.effected-date", errorArgs,
						ConstantCore.EMPTY);
			}

		}
	}
}