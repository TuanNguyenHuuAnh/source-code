package vn.com.unit.cms.admin.all.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.cms.admin.all.dto.ServiceDto;
import vn.com.unit.cms.admin.all.service.ServiceService;

@Component
public class ServiceValidator implements Validator {

    @Autowired
    private ServiceService serviceService;

    @Override
    public boolean supports(Class<?> clazz) {
        return ServiceDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // TODO Auto-generated method stub
        ServiceDto serviceDto = (ServiceDto) target;
        // Check for all
        if (checkExistCode(serviceDto, errors)) {
            return;
        }
    }

    private boolean checkExistCode(ServiceDto dto, Errors errors) {
        String code = dto.getCode();
        ServiceDto serviceDto = serviceService.getServiceByCode(code);
        if (serviceDto != null) {
            if (dto.getId() != serviceDto.getId()) {
                String[] errorArgs = new String[1];
                errorArgs[0] = code;
                errors.rejectValue("code", "service.validation.code", errorArgs,
                        ConstantCore.EMPTY);
                return true;
            }
        }

        return false;
    }
}