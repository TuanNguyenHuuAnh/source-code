/*******************************************************************************
 * Class        ：DocumentCategoryValidator
 * Created date ：2016/03/24
 * Lasted date  ：2016/03/24
 * Author       ：ThuyDTN
 * Change log   ：2016/02/20：01-00 ThuyDTN create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.cms.admin.all.dto.DocumentCategoryEditDto;
import vn.com.unit.cms.admin.all.service.DocumentService;
import vn.com.unit.cms.core.module.document.dto.DocumentEditDto;
import vn.com.unit.ep2p.admin.constant.ConstantCore;

/**
 * DocumentCategoryValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author ThuyDTN
 */
@Component
public class DocumentValidator implements Validator {

    @Autowired
    DocumentService documentService;

    @Override
    public boolean supports(Class<?> clazz) {
        return DocumentCategoryEditDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DocumentEditDto documentDto = (DocumentEditDto) target;
        validateCode(errors, documentDto);
    }

    private void validateCode(Errors errors, DocumentEditDto documentDto) {
        if (documentDto.getId() == null) {
            int codeDocumentCount = documentService.countDocumentByCode(documentDto.getCode());
            if (codeDocumentCount > 0) {
                String[] errorArgs = new String[1];
                errorArgs[0] = documentDto.getCode();
                errors.rejectValue("code", "document.code.existed", errorArgs, ConstantCore.EMPTY);
            }
        } else {
            DocumentEditDto validateObj = documentService.getDocumentObject(documentDto.getId());
            if (validateObj != null && validateObj.getCode() != null
                    && !validateObj.getCode().equals(documentDto.getCode())) {
                String[] errorArgs = new String[1];
                errorArgs[0] = documentDto.getCode();
                errors.rejectValue("code", "document.code.existed", errorArgs, ConstantCore.EMPTY);
            }
        }
    }
}