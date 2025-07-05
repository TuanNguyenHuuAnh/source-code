package vn.com.unit.cms.admin.all.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import vn.com.unit.cms.admin.all.dto.BranchEditDto;
import vn.com.unit.cms.admin.all.entity.Branch;
import vn.com.unit.cms.admin.all.service.CmsBranchService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;

@Component
public class BranchEditValidator implements Validator {

	@Autowired
    private CmsBranchService branchService;

    @Override
    public boolean supports(Class<?> clazz) {
        return BranchEditDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        // BranchEditDto
    	BranchEditDto dto = (BranchEditDto) target;

        // check code not no special characters and spaces
//        checkSpecialCharacters(dto.getCode(), errors);

        // check exists banner code
        checkExistsCode(dto, errors);
    }

    /**
     * check code not no special characters and spaces
     *
     * @param code
     * @param errors
     * @author hand
     */
//    private void checkSpecialCharacters(String code, Errors errors) {
//        if (!code.matches("[a-zA-Z0-9-_/s.]+?[0-9]")) {
//            String[] errorArgs = new String[1];
//            errorArgs[0] = code;
//            errors.rejectValue("code", "data.validate.code", errorArgs, ConstantCore.EMPTY);
//        }
//    }

    /**
     * check exists banner code
     *
     * @param dto
     * @param errors
     * @author hand
     */
    private void checkExistsCode(BranchEditDto dto, Errors errors) {
        String code = dto.getCode();
        
        if (StringUtils.isNotEmpty(code))
        {
        	Branch entity = branchService.findByCode(code);
            if (entity != null) {
                Long branchId = dto.getId();
                Long idDb = entity.getId();

                // Check unique bankNo
                if (branchId == null || !branchId.equals(idDb)) {
                    String[] errorArgs = new String[1];
                    errorArgs[0] = code;
                    errors.rejectValue("code", "message.error.branch.code.existed", errorArgs, ConstantCore.EMPTY);
                }
            }
        }    
    }

}
