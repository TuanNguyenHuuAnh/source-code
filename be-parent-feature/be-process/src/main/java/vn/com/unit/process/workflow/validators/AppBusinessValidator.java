/*******************************************************************************
 * Class        JpmBusinessValidator
 * Created date 2019/06/23
 * Lasted date  2019/06/23
 * Author       KhoaNA
 * Change log   2019/06/23 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.process.workflow.dto.AppBusinessDto;
import vn.com.unit.process.workflow.service.AppBusinessService;

/**
 * JpmBusinessValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Component
public class AppBusinessValidator implements Validator {
	
    /** SerialInfoService */
    @Autowired
    AppBusinessService jpmBusinessService;
	
    @Override
    public boolean supports(Class<?> clazz) {
        return AppBusinessDto.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
    	AppBusinessDto objectDto = (AppBusinessDto) target;        

    	Long id = objectDto.getId();
    	String code = objectDto.getCode();
    	Long companyId = objectDto.getCompanyId();
    	
    	// validate code when create new data
    	if( id == null ) {
    		AppBusinessDto jpmBusinessData = jpmBusinessService.getJpmBusinessByCodeAndCompanyId(code, companyId);
        	validateCode(jpmBusinessData, errors);
    	}
    }
    
    /**
     * Validate duplicate code by companyId
     * 
     * @param jpmBusinessDto
     * 			type JpmBusinessDto
     * @param errors
     * 			type Errors
     * @author KhoaNA
     */
    private void validateCode(AppBusinessDto jpmBusinessDto, Errors errors) {
        if( jpmBusinessDto != null ) {
            String[] errorArgs = new String[1];
            errorArgs[0] = "code";
            errors.rejectValue("code", "message.error.jpm.business.code.existed", errorArgs, ConstantCore.EMPTY);
        }
    }
}
