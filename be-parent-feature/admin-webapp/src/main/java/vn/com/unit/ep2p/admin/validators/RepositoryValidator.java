/*******************************************************************************
 * Class        RepositoryValidator
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

import vn.com.unit.core.service.JRepositoryService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.core.service.JRepositoryService;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.entity.JcaRepository;

/**
 * RepositoryValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Component
public class RepositoryValidator implements Validator {
	
	/** RepositoryService */
    @Autowired
    JRepositoryService repositoryService;
	
    @Override
    public boolean supports(Class<?> clazz) {
        return JcaRepositoryDto.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
    	JcaRepositoryDto repositoryDto = (JcaRepositoryDto) target;        
        this.checkDuplicateCode(repositoryDto, errors);
    }
    
    /**
     * checkDuplicateCode
     *
     * @param repositoryDto
     * 			type RepositoryDto
     * @param errors
     * 			type Errors
     * @author KhoaNA
     */
    private void checkDuplicateCode(JcaRepositoryDto repositoryDto, Errors errors){
        String code = repositoryDto.getCode();
        Long id = repositoryDto.getId();
        
        JcaRepository repositoryDB = repositoryService.getRepositoryByCode(code,id);
        if( repositoryDB != null ) {
        	String[] errorArgs = new String[1];
            errorArgs[0] = code;
            errors.rejectValue("code", "message.error.repository.code.existed", errorArgs, ConstantCore.EMPTY);
        }
    }
}
