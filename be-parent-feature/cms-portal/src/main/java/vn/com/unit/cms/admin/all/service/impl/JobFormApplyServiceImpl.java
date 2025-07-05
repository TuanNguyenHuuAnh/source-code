/*******************************************************************************
 * Class        ：JobFormApplyServiceImpl
 * Created date ：2017/08/15
 * Lasted date  ：2017/08/15
 * Author       ：HoangNP
 * Change log   ：2017/08/09：01-00 HoangNP create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.cms.admin.all.constant.ConstDispType;
import vn.com.unit.cms.admin.all.dto.JobFormApllySearchDto;
import vn.com.unit.cms.admin.all.dto.JobFormApplyEditDto;
import vn.com.unit.cms.admin.all.entity.JobFormApply;
import vn.com.unit.cms.admin.all.enumdef.JobFormApplySearchEnum;
import vn.com.unit.cms.admin.all.repository.JobFormApplyRepository;
import vn.com.unit.cms.admin.all.service.JobFormApplyService;
import vn.com.unit.core.dto.JcaConstantDto;
//import vn.com.unit.jcanary.authentication.UserProfile;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.jcanary.service.ConstantDisplayService;
import vn.com.unit.ep2p.core.utils.Utility;

@Service
@Transactional(readOnly=false, rollbackFor = Exception.class)
public class JobFormApplyServiceImpl implements JobFormApplyService{

	@Autowired
    SystemConfig systemConfig;
	
//	@Autowired
//	private ConstantDisplayService constDispService;
	 
	@Autowired
	JobFormApplyRepository jobFormApplyRepository;
	
    @Autowired
	private JcaConstantService jcaConstantService;
	
	@Override
	public PageWrapper<JobFormApllySearchDto> findJobFormApplyList(JobFormApllySearchDto condition, int page) {
		int sizeOfPage = systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
		
		PageWrapper<JobFormApllySearchDto> pageWrapper = new PageWrapper<JobFormApllySearchDto>(page, sizeOfPage);
		if(null == condition)
			condition = new JobFormApllySearchDto();
		// set SearchParm
        setSearchParm(condition);
		
        int count = jobFormApplyRepository.countJobFormApply(condition);
        List<JobFormApllySearchDto> result = null;
        if (count > 0) {
            int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);
            result = jobFormApplyRepository.findJobFormApplyList(offsetSQL, sizeOfPage, condition);
        }
        if (CollectionUtils.isNotEmpty(result)) {
            for (int i = 0; i < result.size(); i++) {
                JobFormApllySearchDto jobFormApllySearchDto = result.get(i);
                if (null != jobFormApllySearchDto.getContent() && jobFormApllySearchDto.getContent().length() > 100) {
                    jobFormApllySearchDto.setContent(jobFormApllySearchDto.getContent().substring(0, 100) + " ...");
                }
            }
        }
        pageWrapper.setDataAndCount(result, count);
		return pageWrapper;
		
	}
	
	private void setSearchParm(JobFormApllySearchDto condition) {
        if (null == condition.getFieldValues()) {
            condition.setFieldValues(new ArrayList<String>());
        }

        if (condition.getFieldValues().isEmpty()) {
        	condition.setTitle(condition.getFieldSearch());
            condition.setName(condition.getFieldSearch());
            condition.setEmail(condition.getFieldSearch());
            condition.setTelephone(condition.getFieldSearch());
            
        } else {
            for (String field : condition.getFieldValues()) {
                if (StringUtils.equals(field, JobFormApplySearchEnum.TITLE.name())) {
                    condition.setTitle(condition.getFieldSearch());
                    continue;
                }
                if (StringUtils.equals(field, JobFormApplySearchEnum.NAME.name())) {
                    condition.setName(condition.getFieldSearch());
                    continue;
                }
                if (StringUtils.equals(field, JobFormApplySearchEnum.EMAIL.name())) {
                    condition.setEmail(condition.getFieldSearch());
                    continue;
                }
                if (StringUtils.equals(field, JobFormApplySearchEnum.PHONE.name())) {
                    condition.setTelephone(condition.getFieldSearch());
                    continue;
                }
            }
        }
    }

	@Override
	public JobFormApplyEditDto findJobFormApply(Long jobFormApplyId) {
		JobFormApplyEditDto resultDto = new JobFormApplyEditDto();
		
		JobFormApply entity = jobFormApplyRepository.findOne(jobFormApplyId);
		
		if(null != entity){
			resultDto.setId(entity.getId());
			resultDto.setName(entity.getName());
			resultDto.setTelephone(entity.getTelephone());
			resultDto.setEmail(entity.getEmail());
			resultDto.setIdNumber(entity.getIdNumber());
			resultDto.setTitle(entity.getTitle());
			resultDto.setContent(entity.getContent());
			resultDto.setReason(entity.getReason());
			resultDto.setStatus(entity.getStatus());
			resultDto.setPhysicalFileName(entity.getPhysicalFileName());
			resultDto.setPosition(entity.getPosition());
		}		
		return resultDto;
	}

	@Override
	public void initScreenTypeList(ModelAndView mav) {
		
		// ${constantDisplay.cat} => ${constantDisplay.kind}
		// #{${constantDisplay.code}} => #{${constantDisplay.code}}
		// constDispService.findByType("M10");
		// => List<JcaConstantDto> statusList = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M10.toString(), "EN");

		// type => groupCode
		// cat	=> kind
		// code => code
		
		// catOfficialName => name
		
		// ConstantDisplay motive = constantDisplayService.findByTypeAndCat(ConstDispType.MOTIVE.toString(), emailModel.getMotive().toString());
		// JcaConstantDto motive = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind(ConstDispType.MOTIVE.toString(), emailModel.getMotive().toString(), "EN").get(0);

		// List<ConstantDisplay> listBannerPage = constDispService.findByType(ConstDispType.B01);
    	// List<JcaConstantDto> listBannerPage = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.B01.toString(), "EN");
		
//		List<ConstantDisplay> typeList = constDispService.findByType(ConstDispType.J03);
		
		List<JcaConstantDto> typeList = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.J03.toString(), "EN");
		
		mav.addObject("typeList",typeList);
	}

	@Override
	@Transactional
	public void editJobFormApply(JobFormApplyEditDto condition) {
//		UserProfile userProfile = UserProfileUtils.getUserProfile();
		
		
		JobFormApply jobFormApply = jobFormApplyRepository.findOne(condition.getId());
		
		if (null == jobFormApply) {
            throw new BusinessException("Not found JobFormApply with id=" + condition.getId());
        }
		
		jobFormApply.setUpdateBy(UserProfileUtils.getFullName());
		jobFormApply.setUpdateDate(new Date());
		jobFormApply.setEmail(condition.getEmail());
		jobFormApply.setTelephone(condition.getTelephone());
		jobFormApply.setStatus(condition.getStatus());
		jobFormApply.setReason(condition.getReason());
		jobFormApply.setPosition(condition.getPosition());
		
		try{
			jobFormApplyRepository.save(jobFormApply);
		}catch(Exception e){
			System.out.println("Msg: Error: " + e);
		}
		
	}
	
	
	

}
