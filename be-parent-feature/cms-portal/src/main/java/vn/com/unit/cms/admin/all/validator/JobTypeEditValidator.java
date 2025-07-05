package vn.com.unit.cms.admin.all.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.cms.admin.all.dto.JobTypeEditDto;
import vn.com.unit.cms.admin.all.entity.JobType;
import vn.com.unit.cms.admin.all.service.JobTypeService;

@Component
public class JobTypeEditValidator implements Validator{

	@Autowired 
	JobTypeService jobTypeService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return JobTypeEditDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		JobTypeEditDto dto = (JobTypeEditDto) target; 
		
		//check exists code
		checkExistsCode(dto, errors);
		
	}

	

	private void checkExistsCode(JobTypeEditDto dto, Errors errors) {
		String code = dto.getCode();
		JobType entity = jobTypeService.findJobTypeByCode(code);
		if (entity != null) {
            Long jobTypeId = dto.getId();
            Long idDb = entity.getId();

            if (jobTypeId == null || !jobTypeId.equals(idDb)) {
                String[] errorArgs = new String[1];
                errorArgs[0] = code;
                errors.rejectValue("code", "message.error.job.type.code.existed", errorArgs, ConstantCore.EMPTY);
            }
        }
		
	}

}
