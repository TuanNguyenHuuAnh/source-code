package vn.com.unit.process.workflow.validators;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.process.workflow.dto.AppButtonForStepDto;
import vn.com.unit.process.workflow.dto.AppStepDto;
import vn.com.unit.process.workflow.service.AppStepService;
import vn.com.unit.workflow.entity.JpmStep;

@Component
public class AppStepValidator implements Validator {

    @Autowired
    AppStepService appStepService;

    @Override
    public boolean supports(Class<?> clazz) {
        return JpmStep.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AppStepDto objectDto = (AppStepDto) target;
        Set<Long> buttonIds = new HashSet<>();
        
        AppButtonForStepDto[] jpmButtonForStepDtoList = objectDto.getListJpmButton();

        if( jpmButtonForStepDtoList != null ) {
        	for (AppButtonForStepDto button : jpmButtonForStepDtoList) {
                if (button.getIsDeleted()) continue;

                if (!buttonIds.add(button.getButtonId())) {
                    String[] errorArgs = new String[1];
                    errorArgs[0] = "listJpmButton";
                    errors.rejectValue("listJpmButton", "message.error.jpm.process.step.button.existed", errorArgs, ConstantCore.EMPTY);
                    break;
                }
                
                if(null == button.getFunctionId()){
                	errors.rejectValue("listJpmButton", "message.error.jpm.process.step.button.not.setting.function", null, ConstantCore.EMPTY);
                	break;
                }
            }
        }
    }

}
