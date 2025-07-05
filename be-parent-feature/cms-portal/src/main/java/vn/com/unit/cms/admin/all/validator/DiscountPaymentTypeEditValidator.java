/*******************************************************************************
 * Class        ：DiscountPaymentTypeEditValidator
 * Created date ：2016/04/18
 * Lasted date  ：2016/04/18
 * Author       ：thuydtn
 * Change log   ：2017/03/01：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.cms.admin.all.dto.DiscountPaymentTypeDto;
import vn.com.unit.cms.admin.all.service.DiscountPaymentService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;

/**
 * DiscountPaymentTypeEditValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@Component
public class DiscountPaymentTypeEditValidator implements Validator {

	@Autowired
	DiscountPaymentService discountPaymentService;

	@Override
	public boolean supports(Class<?> clazz) {
		return DiscountPaymentTypeDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		DiscountPaymentTypeDto dto = (DiscountPaymentTypeDto) target;

		checkExistsCode(dto, errors);
	}

	private void checkExistsCode(DiscountPaymentTypeDto dto, Errors errors) {
		String code = dto.getCode();

		int count = discountPaymentService.getPaymentCountByCode(code);
		if (count > 0) {
			String[] errorArgs = new String[1];
			errorArgs[0] = code;
			errors.rejectValue("code", "message.error.document.type.code.existed", errorArgs, ConstantCore.EMPTY);
		}
	}

}