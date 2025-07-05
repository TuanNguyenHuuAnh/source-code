package vn.com.unit.ep2p.admin.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.core.entity.JcaEmailTemplate;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.service.TemplateService;
import vn.com.unit.ep2p.dto.TemplateEmailDto;

@Component
public class TemplateValidator implements Validator {

    @Autowired
    TemplateService templateService;

    @Override
    public boolean supports(Class<?> clazz) {
        return JcaEmailTemplate.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        TemplateEmailDto template = (TemplateEmailDto) target;

        Long id = template.getTemplateId();
        String code = template.getCode();
        Long companyId = template.getCompanyId();

        // check dup code
        JcaEmailTemplate templateCheckCode = templateService.getByCodeAndCompanyId(code, companyId);
        if (templateCheckCode != null) {
            if (id == null || (id != null && !templateCheckCode.getId().equals(id))) {
                String[] errorArgs = new String[1];
                errorArgs[0] = code;
                errors.rejectValue("code", "message.error.email.template.code.existed", errorArgs, ConstantCore.EMPTY);
            }
        }
    }
}