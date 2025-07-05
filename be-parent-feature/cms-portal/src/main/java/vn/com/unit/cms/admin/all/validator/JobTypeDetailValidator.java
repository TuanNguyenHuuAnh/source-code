/*******************************************************************************
 * Class        ：JobValidator
 * Created date ：2017/03/07
 * Lasted date  ：2017/03/07
 * Author       ：TranLTH
 * Change log   ：2017/03/07：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.cms.admin.all.dto.JobTypeDetailDto;
import vn.com.unit.cms.admin.all.service.JobTypeDetailService;

/**
 * JobValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Component
public class JobTypeDetailValidator implements Validator {
    
    /** JobService */
    @Autowired
    JobTypeDetailService jtdService;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return JobTypeDetailDto.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        JobTypeDetailDto jtdDto = (JobTypeDetailDto) target;        
        if (null == jtdDto.getId()){
        	System.out.println("IS EXIST");
            checkDuplicateCode(jtdDto, errors);
        }
    }
    /**
     * checkDuplicateCode
     *
     * @param jtdDto
     * @param errors
     */
    private void checkDuplicateCode(JobTypeDetailDto jtdDto, Errors errors){
        String targetCode = jtdDto.getCode().toUpperCase();
        List<JobTypeDetailDto> listJtdDto = jtdService.getAllJtdDto();
        if (listJtdDto.size() > 0){
            for (JobTypeDetailDto jtdbDto : listJtdDto){
                String jtdCode = jtdbDto.getCode();
                if (targetCode.equals(jtdCode)){
                    String[] errorArgs = new String[1];
                    errorArgs[0] = targetCode;
                    errors.rejectValue("code", "message.error.job.code.existed", errorArgs, ConstantCore.EMPTY);                    
                }  
            }                          
        }
    }
}