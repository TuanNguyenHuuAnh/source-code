/*******************************************************************************
 * Class        :CompanyValidator
 * Created date :2019/05/07
 * Lasted date  :2019/05/07
 * Author       :HungHT
 * Change log   :2019/05/07:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.dto.CompanyDto;

/**
 * CompanyValidator
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Component
public class CompanyValidator implements Validator {

    @Autowired
    CompanyService companyService;

	@Override
    public boolean supports(Class<?> clazz) {
        return CompanyDto.class.equals(clazz);
    }

	@Override
    public void validate(Object target, Errors errors) {
//        CompanyDto destination = (CompanyDto) target;
        /*CompanyDto source = companyService.findByProperties1(destination.getProperties1());
        if (source != null) {
            if ((destination.getId() != null && source.getId().compareTo(destination.getId()) != 0) || null == destination.getId()) {
                String[] errorArgs = new String[1];
                errorArgs[0] = destination.getProperties1();
                errors.rejectValue("properties1", "${classNameDot}.properties1.existed", errorArgs, ConstantCore.EMPTY);
            }
        }*/
	}
}