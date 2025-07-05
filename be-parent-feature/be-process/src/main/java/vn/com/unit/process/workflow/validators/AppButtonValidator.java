package vn.com.unit.process.workflow.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.process.workflow.dto.AppButtonDto;
import vn.com.unit.process.workflow.service.AppButtonService;
import vn.com.unit.workflow.entity.JpmButton;

@Component
public class AppButtonValidator implements Validator {

    @Autowired
    AppButtonService appButtonService;

    @Override
    public boolean supports(Class<?> clazz) {
        return JpmButton.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AppButtonDto objectDto = (AppButtonDto) target;

        // validate button name unique in process
        if (!appButtonService.validateButtonWithNameAndProcessId(objectDto.getId(), objectDto.getButtonText(), objectDto.getProcessId())) {
            String[] errorArgs = new String[1];
            errorArgs[0] = "buttonText";
            errors.rejectValue("buttonText", "message.error.jpm.process.button.name.existed", errorArgs, ConstantCore.EMPTY);
        }
    }
}
