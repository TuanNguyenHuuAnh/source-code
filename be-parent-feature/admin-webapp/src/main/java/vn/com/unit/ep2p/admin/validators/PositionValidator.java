/*******************************************************************************
 * Class        PositionValidator
 * Created date 2018/08/08
 * Lasted date  2018/08/08
 * Author       KhoaNA
 * Change log   2018/08/0801-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.core.dto.JcaPositionDto;
import vn.com.unit.core.entity.JcaPosition;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.service.PositionService;

/**
 * PositionValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Component
public class PositionValidator implements Validator {
	
	/** PositionService */
    @Autowired
    PositionService positionService;
	
    @Override
    public boolean supports(Class<?> clazz) {
        return JcaPosition.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
    	JcaPositionDto positionDto = (JcaPositionDto) target;        
        this.checkDuplicateCode(positionDto, errors);
    }
    
    /**
     * checkDuplicateCode
     *
     * @param positionDto
     * 			type PositionDto
     * @param errors
     * 			type Errors
     * @author KhoaNA
     */
    private void checkDuplicateCode(JcaPositionDto positionDto, Errors errors){
        String positionCode = positionDto.getCode();
        Long companyId = positionDto.getCompanyId();
        Long positionId = positionDto.getPositionId();
        JcaPosition positionDB = positionService.getByCodeAndCompanyId(positionCode, companyId, positionId);
        if( positionDB != null) {
        	String[] errorArgs = new String[1];
            errorArgs[0] = positionCode;
            errors.rejectValue("code", "message.error.position.code.existed", errorArgs, ConstantCore.EMPTY);
        }
    }
}
