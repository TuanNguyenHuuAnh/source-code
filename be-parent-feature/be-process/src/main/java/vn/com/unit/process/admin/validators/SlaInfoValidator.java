package vn.com.unit.process.admin.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.sla.dto.SlaInfoDto;
import vn.com.unit.process.admin.sla.service.SlaInfoService;

@Component
public class SlaInfoValidator implements Validator {

    @Autowired
    private SlaInfoService slaInfoService;

    @Override
    public boolean supports(Class<?> clazz) {
        return SlaInfoDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        SlaInfoDto slaInfoDto = (SlaInfoDto) target;

        Long id = slaInfoDto.getId();
//        Long companyId = slaInfoDto.getCompanyId();
        Long processDeployId = slaInfoDto.getProcessDeployId();
//        String name = slaInfoDto.getName();
//        boolean validate = slaInfoService.checkSlaInfoExist(companyId, name);
        boolean hasProcess = slaInfoService.checkProcessExist(processDeployId) && null == id;

//        if (validate) {
//            String[] errorArgs = new String[1];
//            errorArgs[0] = name;
//            errors.rejectValue("name", "message.error.sla.name.exist", errorArgs, ConstantCore.EMPTY);
//        }
        if (hasProcess) {
            Long[] errorArgs = new Long[1];
            errorArgs[0] = processDeployId;
            errors.rejectValue("processDeployId", "message.error.sla.process.exist", errorArgs, ConstantCore.EMPTY);
        }
    }
}