/*******************************************************************************
 * Class        ：DocumentCategoryValidator
 * Created date ：2016/03/24
 * Lasted date  ：2016/03/24
 * Author       ：TaiTM
 * Change log   ：2016/02/20：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.cms.admin.all.dto.DocumentCategoryEditDto;
import vn.com.unit.cms.admin.all.service.DocumentService;

/**
 * DocumentCategoryValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Component
public class DocumentCategoryValidator implements Validator {

    @Autowired
    DocumentService documentService;

    @Override
    public boolean supports(Class<?> clazz) {
        return DocumentCategoryEditDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
//        DocumentCategoryEditDto editDto = (DocumentCategoryEditDto) target;
    }
}