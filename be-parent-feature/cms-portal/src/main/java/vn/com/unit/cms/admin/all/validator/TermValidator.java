/*******************************************************************************
 * Class        ：TermValidator
 * Created date ：2017/05/24
 * Lasted date  ：2017/05/24
 * Author       ：tungns <tungns@unit.com.vn>
 * Change log   ：2017/05/24：01-00 tungns <tungns@unit.com.vn> create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.validator;

//import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.cms.admin.all.dto.TermAddOrEditDto;
import vn.com.unit.cms.admin.all.entity.Term;
//import vn.com.unit.cms.admin.all.enumdef.TermTypeEnum;
import vn.com.unit.cms.admin.all.service.TermService;

/**
 * TermValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author tungns <tungns@unit.com.vn>
 */
@Component
public class TermValidator implements Validator {

	@Autowired
	TermService termService;

//	@Autowired
//	private MessageSource msg;

	@Override
	public boolean supports(Class<?> clazz) {
		return Term.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		TermAddOrEditDto termDto = (TermAddOrEditDto) target;
		// checkExistsCode
		checkExistsCode(termDto, errors);
//        if(Integer.parseInt(termDto.getUnitType()) != 6){
//            checkExistsValueAndType(termDto, errors);
//        }
		// check from vaue and to value
		checkFromValueToValue(termDto, errors);
		checkExistsTerm(termDto, errors);
	}

	/**
	 * checkExistsCode
	 *
	 * @param termDto
	 * @param errors
	 * @return
	 * @author hand
	 */
	private void checkExistsCode(TermAddOrEditDto termDto, Errors errors) {
		String code = termDto.getCode();
		Term term = termService.findByCode(code);

		if (term != null) {
			if (termDto.getId() == null || !termDto.getId().equals(term.getId())) {
				String[] errorArgs = new String[1];
				errorArgs[0] = code;
				errors.rejectValue("code", "message.error.term.code.existed", errorArgs, ConstantCore.EMPTY);
			}
		}
	}

	/**
	 * checkExistsCode
	 *
	 * @param termDto
	 * @param errors
	 * @return
	 * @author hand
	 */
//	private void checkExistsValueAndType(TermAddOrEditDto termDto, Errors errors) {
//		// termValue
//		int termValue = termDto.getTermValue();
//		// termType
//		String termType = termDto.getTermType();
//
//		Term term = termService.findByValueAndType(termValue, termType);
//
//		if (term != null) {
//			if (!term.getId().equals(termDto.getId())) {
//				// set termType
//				for (TermTypeEnum termEnum : TermTypeEnum.values()) {
//					if (StringUtils.equals(termType, termEnum.toString())) {
//						termType = msg.getMessage(termEnum.getName(), null, termDto.getLocale());
//						break;
//					}
//				}
//
//				String[] errorArgs = new String[2];
//				errorArgs[0] = String.valueOf(termValue);
//				errorArgs[1] = termType;
//				errors.rejectValue("termValue", "message.error.term.value.type.existed", errorArgs, ConstantCore.EMPTY);
//				errors.rejectValue("termType", "message.error.term.value.type.existed", errorArgs, ConstantCore.EMPTY);
//			}
//		}
//	}

	/**
	 * checkFromValueToValue
	 * 
	 * @param termDto
	 * @param errors
	 * @return
	 * @author diennv
	 */
	private void checkFromValueToValue(TermAddOrEditDto termDto, Errors errors) {
		long fromValue = termDto.getFromValue();
		long toValue = termDto.getToValue();
		if (fromValue > toValue) {
			String[] errorArgs = new String[1];
			errorArgs[0] = String.valueOf(toValue);
			errors.rejectValue("toValue", "message.error.term.tovalue", errorArgs, ConstantCore.EMPTY);
		}
	}

	private void checkExistsTerm(TermAddOrEditDto termDto, Errors errors) {
		// termValue
		int termValue = termDto.getTermValue();
		// termType
		String termType = termDto.getTermType();

		String unitType = termDto.getUnitType();

		boolean loanTerm = termDto.isLoanTerm();

		long fromValue = termDto.getFromValue();

		long toValue = termDto.getToValue();

		Term term = new Term();
		if (Integer.parseInt(unitType) == 6) {
			term = termService.findByFromValueAndToValueAndUnitTypeAndTermTypeAndLoanTerm(fromValue, toValue, unitType,
					termType, loanTerm);
		} else {
			term = termService.findByValueAndTypeAndUnitTypeAndLoanType(termValue, termType, unitType, loanTerm);
		}

		if (term != null) {
			if (!term.getId().equals(termDto.getId())) {
				String[] errorArgs = new String[1];
				errorArgs[0] = String.valueOf(loanTerm);
				errors.rejectValue("loanTerm", "message.error.term.existed", errorArgs, ConstantCore.EMPTY);
			}
		}
	}
}
