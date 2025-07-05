/*******************************************************************************
 * Class        ：JobValidator
 * Created date ：2017/03/07
 * Lasted date  ：2017/03/07
 * Author       ：TranLTH
 * Change log   ：2017/03/07：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.cms.admin.all.dto.JobDto;
import vn.com.unit.cms.admin.all.service.JobService;

/**
 * JobValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Component
public class JobValidator implements Validator {
    
    /** JobService */
    @Autowired
    JobService jobService;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return JobDto.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        JobDto jobDto = (JobDto) target;        
        validateAlias(jobDto, errors); 
    }
    /**
     * checkDuplicateCode
     *
     * @param jobDto
     * @param errors
     * @author TranLTH
     *
    private void checkDuplicateCode(JobDto jobDto, Errors errors){
        String jobCode = jobDto.getJobCode();
        JobDto searchJobDto = new JobDto();
        searchJobDto.setJobCode(jobDto.getJobCode());   
        List<JobDto> checkListJobDto = jobService.searchJobByCondition(searchJobDto);
        if (checkListJobDto.size() > 0){
            for (JobDto checkJobDto : checkListJobDto){
                String checkJobCode = checkJobDto.getJobCode();
                if (jobCode.equals(checkJobCode)){
                    String[] errorArgs = new String[1];
                    errorArgs[0] = jobCode;
                    errors.rejectValue("jobCode", "message.error.job.code.existed", errorArgs, ConstantCore.EMPTY);                    
                }  
            }                          
        }
    } */  
    
    private void validateAlias(JobDto jobDto,  Errors errors){
        Long id = jobDto.getJobId();
        if(jobDto.getLinkAlias() != null){
            JobDto searchJobDto = new JobDto();
            searchJobDto.setLinkAlias(jobDto.getLinkAlias()); 
            searchJobDto = jobService.searchJobByCondition(searchJobDto);
            if (null != searchJobDto){                
                String checkLinkAlias = searchJobDto.getLinkAlias();
                if (!id.equals(searchJobDto.getJobId())){
                    String[] errorArgs = new String[1];
                    errorArgs[0] = checkLinkAlias;
                    errors.rejectValue("linkAlias", "message.error.job.code.existed", errorArgs, ConstantCore.EMPTY);
                }                                                           
            }
        }
    }
}