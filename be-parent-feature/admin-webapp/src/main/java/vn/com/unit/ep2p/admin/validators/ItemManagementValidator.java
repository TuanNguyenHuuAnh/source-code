/*******************************************************************************
 * Class        ItemManagementValidator
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

import vn.com.unit.core.dto.JcaItemDto;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.service.ItemManagementService;

/**
 * ItemManagementValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Component
public class ItemManagementValidator implements Validator {

    @Autowired
    ItemManagementService itemManagementService;

    @Override
    public boolean supports(Class<?> clazz) {
        return JcaItemDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        JcaItemDto itemModel = (JcaItemDto) target;

        String functionCode = itemModel.getFunctionCode();
        Long companyId = itemModel.getCompanyId();
        JcaItemDto itemDB = itemManagementService.findByFunctionCodeAndCompanyId(functionCode, companyId);
        if (null != itemDB && (null == itemModel.getItemId() || itemDB.getItemId().longValue() != itemModel.getItemId().longValue())) {
            String[] errorArgs = new String[1];
            errorArgs[0] = functionCode;
            errors.rejectValue("functionCode", "message.error.code.existed", errorArgs, ConstantCore.EMPTY);
        }
    }
}