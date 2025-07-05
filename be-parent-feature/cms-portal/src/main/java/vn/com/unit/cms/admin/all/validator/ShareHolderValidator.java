/*******************************************************************************
 * Class        ：ShareHolderValidator
 * Created date ：2016/02/20
 * Lasted date  ：2016/02/20
 * Author       ：ThuyDTN
 * Change log   ：2016/02/20：01-00 ThuyDTN create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.cms.admin.all.dto.ShareholderDto;
import vn.com.unit.cms.admin.all.entity.ShareHolder;
import vn.com.unit.cms.admin.all.service.ShareHolderManService;

/**
 * ShareHolderValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author ThuyDTN
 */
@Component
public class ShareHolderValidator implements Validator {

    @Autowired
    ShareHolderManService sHolderService;

    @Override
    public boolean supports(Class<?> clazz) {
        return ShareHolder.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ShareholderDto sHolderDto = (ShareholderDto) target;
        if (sHolderDto.getId() == null) {
            int codeSHolderCount = sHolderService
                    .countByCode(sHolderDto.getCode());
            if (codeSHolderCount > 0) {
                String[] errorArgs = new String[1];
                errorArgs[0] = sHolderDto.getCode();
                errors.rejectValue("code",
                        "message.error.shareholder.code.existed", errorArgs,
                        ConstantCore.EMPTY);
            }
        } else {
            ShareholderDto validateObj = sHolderService
                    .getDetailById(sHolderDto.getId());
            if (validateObj != null && validateObj.getCode() != null
                    && !validateObj.getCode()
                            .equals(sHolderDto.getCode())) {
                String[] errorArgs = new String[1];
                errorArgs[0] = sHolderDto.getCode();
                errors.rejectValue("code",
                        "message.error.shareholder.code.existed", errorArgs,
                        ConstantCore.EMPTY);
            }
        }

    }

}