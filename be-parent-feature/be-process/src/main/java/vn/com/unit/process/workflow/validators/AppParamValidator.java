package vn.com.unit.process.workflow.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.process.workflow.dto.AppParamDto;
import vn.com.unit.process.workflow.service.AppParamService;
import vn.com.unit.workflow.entity.JpmParam;

@Component
public class AppParamValidator implements Validator {

    @Autowired
    AppParamService appParamService;

    @Override
    public boolean supports(Class<?> clazz) {
        return JpmParam.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AppParamDto objectDto = (AppParamDto) target;

        // validate button name unique in process
        if (!appParamService.validateParamWithNameAndProcessId(objectDto.getId(), objectDto.getFieldName(), objectDto.getProcessId())) {
            String[] errorArgs = new String[1];
            errorArgs[0] = "fieldName";
            errors.rejectValue("fieldName", "message.error.jpm.process.param.name.existed", errorArgs, ConstantCore.EMPTY);
        }
    }
}
