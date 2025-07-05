package vn.com.unit.process.workflow.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.process.workflow.dto.AppStatusDto;
import vn.com.unit.process.workflow.service.AppStatusService;
import vn.com.unit.workflow.entity.JpmStatus;

@Component
public class AppStatusValidator implements Validator {

    @Autowired
    AppStatusService appStatusService;

    @Override
    public boolean supports(Class<?> clazz) {
        return JpmStatus.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AppStatusDto objectDto = (AppStatusDto) target;

        // validate button name unique in process
        if (!appStatusService.validateStatusWithCodeAndProcessId(objectDto.getId(), objectDto.getStatusCode(), objectDto.getProcessId())) {
            String[] errorArgs = new String[1];
            errorArgs[0] = "statusCode";
            errors.rejectValue("statusCode", "message.error.jpm.process.status.code.existed", errorArgs, ConstantCore.EMPTY);
        }
    }
}
