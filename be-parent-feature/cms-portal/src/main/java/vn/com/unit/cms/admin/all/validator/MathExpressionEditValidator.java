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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.cms.admin.all.dto.MathExpressionDto;
import vn.com.unit.cms.admin.all.repository.CustomerTypeRepository;
import vn.com.unit.cms.admin.all.service.CustomerTypeService;
import vn.com.unit.cms.admin.all.service.MathExpressionService;

/**
 * DocumentTypeEditValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@Component
public class MathExpressionEditValidator implements Validator {

    /** documentTypeService */
    @Autowired
    MathExpressionService mathExpressionService;
    
    @Autowired
    CustomerTypeService customerTypeService;
    
    @Autowired
    CustomerTypeRepository customerTypeRepository;
    
    @Autowired
    MessageSource msg;

    @Override
    public boolean supports(Class<?> clazz) {
        return MathExpressionDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // DocumentTypeEditDto
    	MathExpressionDto dto = (MathExpressionDto) target;
    	checkExistsExpressionInTime(dto, errors);
    }
    
    private void checkExistsExpressionInTime(MathExpressionDto dto, Errors errors){
         MathExpressionDto conflictExpression = mathExpressionService.getExpressionConflictEffectedDateAndType(dto.getId(), dto.getAvailableDateFrom(), dto.getAvailableDateTo(), dto.getExpressionType(), dto.getStrCustomerTypeId());
         if (conflictExpression != null) {
        	 List<String> conflictCustomerTypes = this.checkConflictCustomer(dto, conflictExpression);
        	 if(conflictCustomerTypes.size() > 0){
        		 String customerTypeNames =  conflictCustomerTypes.stream()
        	                .map(i -> i)
        	                .collect(Collectors.joining(", "));
        		 String[] errorArgs = new String[4];
            	 DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            	 errorArgs[0] = customerTypeNames;
            	 errorArgs[1] = df.format(conflictExpression.getAvailableDateFrom());
            	 errorArgs[2] = df.format(conflictExpression.getAvailableDateTo());
            	 errorArgs[3] = conflictExpression.getCode();
            	 errors.rejectValue("availableDateTo", "message.error.math-expression.effected-date", errorArgs,
    						ConstantCore.EMPTY);
        	 }
		}
    }

	private List<String> checkConflictCustomer(MathExpressionDto dto, MathExpressionDto conflictExpression) {
		List<Long> customerTypeIds = new ArrayList<Long>();
		for(Long customerTypeId : dto.getLstCustomerTypeId()){
			for(Long conflictExpCustomerTypeId : conflictExpression.getLstCustomerTypeId()){
				if(customerTypeId.equals(conflictExpCustomerTypeId)){
					customerTypeIds.add(customerTypeId);
					break;
				}
			}
		}
		if(customerTypeIds.size() > 0){
			return  customerTypeService.getTypeNamesByIds(customerTypeIds);
		}else{
			return new ArrayList<String>();
		}
	}
    
    
}