package vn.com.unit.ep2p.admin.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import vn.com.unit.ep2p.admin.service.PopupService;
import vn.com.unit.ep2p.dto.PopupDto;

@Component
public class PopupValidator implements Validator {

    @Autowired
    PopupService popupService;

    @Override
    public boolean supports(Class<?> clazz) {
        return PopupDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        PopupDto template = (PopupDto) target;

        Long id = template.getId();
        String code = template.getCode();
//        if(null != id){
//            // check dup code
//            PopupDto templateCheckCode = popupService.getPopupDtoById(id);
//            if (templateCheckCode != null) {
//                if (id == null || (id != null && !templateCheckCode.getId().equals(id))) {
//                    String[] errorArgs = new String[1];
//                    errorArgs[0] = code;
//                    errors.rejectValue("code", "message.error.email.template.code.existed", errorArgs, ConstantCore.EMPTY);
//                }
//            }
//        }
    }
}