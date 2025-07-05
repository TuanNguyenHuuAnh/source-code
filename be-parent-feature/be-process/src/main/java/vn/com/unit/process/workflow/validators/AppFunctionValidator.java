package vn.com.unit.process.workflow.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.process.workflow.dto.AppFunctionDto;
import vn.com.unit.process.workflow.service.AppFunctionService;
import vn.com.unit.workflow.entity.JpmPermission;

@Component
public class AppFunctionValidator implements Validator {

    @Autowired
    AppFunctionService appFunctionService;

    @Override
    public boolean supports(Class<?> clazz) {
        return JpmPermission.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AppFunctionDto objectDto = (AppFunctionDto) target;

        // validate button name unique in process
        if (!appFunctionService.validateFunctionWithNameAndProcessId(objectDto.getId(), objectDto.getName(), objectDto.getProcessId())) {
            String[] errorArgs = new String[1];
            errorArgs[0] = "name";
            errors.rejectValue("name", "message.error.jpm.process.function.name.existed", errorArgs, ConstantCore.EMPTY);
        }
    }
}
