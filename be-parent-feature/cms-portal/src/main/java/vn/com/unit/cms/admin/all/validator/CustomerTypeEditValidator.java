/*******************************************************************************
 * Class        ：CustomerTypeValidator
 * Created date ：2017/05/03
 * Lasted date  ：2017/05/03
 * Author       ：hand
 * Change log   ：2017/05/03：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.cms.admin.all.dto.CustomerTypeEditDto;
import vn.com.unit.cms.admin.all.entity.CustomerType;
import vn.com.unit.cms.admin.all.service.CustomerTypeService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;

/**
 * CustomerTypeValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Component
public class CustomerTypeEditValidator implements Validator {

	/** customerTypeService */
	@Autowired
	CustomerTypeService customerTypeService;

	@Override
	public boolean supports(Class<?> clazz) {
		return CustomerTypeEditDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		// CustomerTypeEditDto
		CustomerTypeEditDto dto = (CustomerTypeEditDto) target;

		// check exists code of Customer
		checkExistsCode(dto, errors);

	}

	/**
	 * check exists code of CustomerType
	 *
	 * @param dto    CustomerTypeEditDto
	 * @param errors
	 * @author hand
	 */
	private void checkExistsCode(CustomerTypeEditDto dto, Errors errors) {
		String code = dto.getCode();

		CustomerType entity = customerTypeService.findByCode(code);
		if (entity != null) {
			Long customerId = dto.getId();
			Long idDb = entity.getId();

			// Check unique bankNo
			if (customerId == null || !customerId.equals(idDb)) {
				String[] errorArgs = new String[1];
				errorArgs[0] = code;
				errors.rejectValue("code", "message.error.customer.type.code.existed", errorArgs, ConstantCore.EMPTY);
			}
		}
	}

}
