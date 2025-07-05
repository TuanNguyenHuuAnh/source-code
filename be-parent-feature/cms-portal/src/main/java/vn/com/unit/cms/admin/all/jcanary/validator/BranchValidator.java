/*******************************************************************************
 * Class        BranchValidator
 * Created date 2017/03/15
 * Lasted date  2017/03/15
 * Author       TranLTH
 * Change log   2017/03/1501-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.cms.admin.all.jcanary.dto.BranchDto;
import vn.com.unit.cms.admin.all.jcanary.service.BranchService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;

//import vn.com.unit.constant.ConstantCore;
//import vn.com.unit.jcanary.dto.BranchDto;
//import vn.com.unit.jcanary.service.BranchService;

/**
 * BranchValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Component
public class BranchValidator implements Validator{
    
    /** BranchService */
    @Autowired
    BranchService branchService;

    @Override
    public boolean supports(Class<?> clazz) {
        return BranchDto.class.equals(clazz);
    }
    
    @Override
    public void validate(Object target, Errors errors) {
        BranchDto branchDto = (BranchDto) target;
        if (null == branchDto.getId() ){
            checkDuplicateCode(branchDto, errors);
        } else{
            checkDeletedBranchId(branchDto, errors);
        }
    }
    /**
     * checkDuplicateCode
     *
     * @param branchDto
     * @param errors
     * @author TranLTH
     */
    private void checkDuplicateCode(BranchDto branchDto, Errors errors){
        String code = branchDto.getCode();
        BranchDto checkBranchDto = branchService.findByCode(code);
        if (null != checkBranchDto){
            String checkBranchCode = checkBranchDto.getCode();
            if (code.equals(checkBranchCode)){
                String[] errorArgs = new String[1];
                errorArgs[0] = code;
                errors.rejectValue("code", "message.error.branch.code.existed", errorArgs, ConstantCore.EMPTY);
            }
        }
    }
    /**
     * checkDeletedBranchId
     *
     * @param branchDto
     * @param errors
     * @author TranLTH
     */
    private void checkDeletedBranchId(BranchDto branchDto, Errors errors){
        BranchDto listBranchDto = branchService.getBranchDto(branchDto.getId());
        if (null == listBranchDto){
            String[] errorArgs = new String[1];
            errorArgs[0] = branchDto.getId() + "";
            errors.rejectValue("id", "message.error.branch.id.delete", errorArgs, ConstantCore.EMPTY);
        }
    }
}
