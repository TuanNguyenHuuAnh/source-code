/*******************************************************************************
 * Class        ：JobTypeSubEditValidator
 * Created date ：2017/08/10
 * Lasted date  ：2017/08/10
 * Author       ：ToanNT
 * Change log   ：2017/08/10：01-00 ToanNT create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.cms.admin.all.dto.JobTypeSubEditDto;
import vn.com.unit.cms.admin.all.entity.JobTypeSub;
import vn.com.unit.cms.admin.all.service.JobTypeSubService;

@Component
public class JobTypeSubEditValidator implements Validator{
	
	@Autowired
	JobTypeSubService categoryJobTypeSubService;
	
	 @Override
	    public void validate(Object target, Errors errors) {

	        // JobTypSubEditDto
	        JobTypeSubEditDto dto = (JobTypeSubEditDto) target;

	        // check exists code
	        checkExistsCode(dto, errors);

	    }
	 private void checkExistsCode(JobTypeSubEditDto dto, Errors errors) {
	        String code = dto.getCode();

	        JobTypeSub entity = categoryJobTypeSubService.findJobTypeSubByCode(code);
	        if (entity != null) {
	            Long jobTypeSubId = dto.getId();
	            Long idDb = entity.getId();

	            if (jobTypeSubId == null || !jobTypeSubId.equals(idDb)) {
	                String[] errorArgs = new String[1];
	                errorArgs[0] = code;
	                errors.rejectValue("code", "message.error.job.type.sub.code.existed", errorArgs, ConstantCore.EMPTY);
	            }
	        }
	    }
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}
}
