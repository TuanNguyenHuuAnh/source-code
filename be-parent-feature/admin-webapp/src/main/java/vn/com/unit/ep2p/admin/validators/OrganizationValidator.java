/*******************************************************************************
 * Class        OrganizationValidator
 * Created date 2017/04/10
 * Lasted date  2017/04/10
 * Author       TranLTH
 * Change log   2017/04/1001-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.common.enumdef.PageMode;
import vn.com.unit.core.dto.JcaOrganizationDto;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.service.OrganizationService;

/**
 * OrganizationValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Component
public class OrganizationValidator implements Validator{
    /** OrganizationService */
    @Autowired
    OrganizationService organizationService;    
    
    @Override
    public boolean supports(Class<?> clazz) {
        return JcaOrganizationDto.class.equals(clazz);
    }
    
    @Override
    public void validate(Object target, Errors errors) {
        JcaOrganizationDto organizationDto = (JcaOrganizationDto) target;          
        if (organizationDto != null && organizationDto.getMode() == PageMode.CREATE){
            checkDuplicateCode(organizationDto, errors);
        }                    
    }
    
    private void checkDuplicateCode(JcaOrganizationDto organizationDto, Errors errors){
        String orgCode = organizationDto.getCode().toUpperCase();
        Long companyId = organizationDto.getCompanyId();
       
        JcaOrganizationDto checkOrgDto = organizationService.findByOrgCondition(orgCode, companyId);
        
        if (null != checkOrgDto){
            String[] errorArgs = new String[1];
            errorArgs[0] = orgCode;
            errors.rejectValue("code", "message.error.org.code.existed", errorArgs, ConstantCore.EMPTY);                           
        }
    }   
}