package vn.com.unit.ep2p.admin.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.core.dto.JcaNotiTemplateDto;
//import vn.com.unit.core.entity.JcaEmailTemplate;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.service.AppNotiTemplateService;
//import vn.com.unit.ep2p.admin.service.TemplateService;
//import vn.com.unit.ep2p.dto.TemplateEmailDto;

@Component
public class NotiTemplateValidator implements Validator {

    @Autowired
    AppNotiTemplateService appNotiTemplateService;

    @Override
    public boolean supports(Class<?> clazz) {
        return JcaNotiTemplateDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        JcaNotiTemplateDto template = (JcaNotiTemplateDto) target;

        Long id = template.getId();
        String code = template.getCode();
        Long companyId = template.getCompanyId();

        // check dup code
        JcaNotiTemplateDto templateCheckCode = appNotiTemplateService.getJcaNotiTemplateDtoByCodeAndCompnayId(code, companyId);
        if (templateCheckCode != null) {
            if (id == null || (id != null && !templateCheckCode.getId().equals(id))) {
                String[] errorArgs = new String[1];
                errorArgs[0] = code;
                errors.rejectValue("code", "message.error.noti.template.code.existed", errorArgs, ConstantCore.EMPTY);
            }
        }
    }
}