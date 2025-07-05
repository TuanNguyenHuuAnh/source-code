/*******************************************************************************
 * Class        RoleAddValidator
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.core.entity.JcaRole;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.JcaRoleAddDto;
import vn.com.unit.ep2p.admin.service.RoleService;

/**
 * RoleAddValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Component
public class RoleAddValidator implements Validator {

    /** RoleService */
	@Autowired
	RoleService roleService;

	@Override
	public boolean supports(Class<?> clazz) {
		return JcaRoleAddDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

	    JcaRoleAddDto roleModel = (JcaRoleAddDto) target;

		String code = roleModel.getCode().trim();
		Long companyId = roleModel.getCompanyId();
		JcaRole roleDB = roleService.findByCodeAndCompanyId(code, companyId);
		if( null != roleDB ) {
		    String [] errorArgs = new String[1];
            errorArgs[0] = code;
            errors.rejectValue("code", "message.error.code.existed", errorArgs, ConstantCore.EMPTY);
		}
	}
}