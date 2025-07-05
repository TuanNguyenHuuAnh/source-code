/*******************************************************************************
 * Class        ：DocumentAppValidator
 * Created date ：2021/01/29
 * Lasted date  ：2021/01/29
 * Author       ：tantm
 * Change log   ：2021/01/29：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.ep2p.core.efo.dto.EfoDocDto;

/**
 * DocumentAppValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Component
public class DocumentAppValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return EfoDocDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
    	EfoDocDto objectDto = (EfoDocDto) target;
        String docTitle = objectDto.getDocTitle();
        String priority = objectDto.getPriority();

        if (CommonStringUtil.isBlank(docTitle)) {
            errors.rejectValue("docTitle", "doc.title.null", new String[] {"1"} , "Doc title is null");
        }
        if (CommonStringUtil.isBlank(priority)) {
            errors.rejectValue("priority", "doc.priority.null", null, CoreConstant.EMPTY);
        }

        if (CommonStringUtil.isNotBlank(docTitle) && docTitle.length() > 255) {
            errors.rejectValue("docTitle", "doc.title.max.length", null, CoreConstant.EMPTY);
        }

    }

}
