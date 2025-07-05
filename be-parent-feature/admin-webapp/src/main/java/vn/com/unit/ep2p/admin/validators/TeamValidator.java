/*******************************************************************************
 * Class        TeamValidator
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

import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.service.TeamService;

/**
 * TeamValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Component
public class TeamValidator implements Validator {

    /** RoleService */
	@Autowired
	TeamService teamService;

	@Override
	public boolean supports(Class<?> clazz) {
		return JcaTeamDto.class.equals(clazz);
	}

	@Override
    public void validate(Object target, Errors errors) {

        JcaTeamDto teamModel = (JcaTeamDto) target;

        String code = teamModel.getCode().trim();
        Long companyId = teamModel.getCompanyId();
        JcaTeamDto teamDB = teamService.findByCodeAndCompanyId(code, companyId);
        if (null != teamDB && (null == teamModel.getTeamId() || teamDB.getTeamId().longValue() != teamModel.getTeamId().longValue())) {
            String[] errorArgs = new String[1];
            errorArgs[0] = code;
            errors.rejectValue("code", "message.error.code.existed", errorArgs, ConstantCore.EMPTY);
        }
    }
}