/*******************************************************************************
 * Class        ：GuaranteeCertificateServiceImpl
 * Created date ：2017/08/24
 * Lasted date  ：2017/08/24
 * Author       ：hoangnp
 * Change log   ：2017/08/24：01-00 hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.cms.admin.all.dto.GuaranteeCertificateEditDto;
import vn.com.unit.cms.admin.all.dto.GuaranteeCertificateSearchDto;
import vn.com.unit.cms.admin.all.entity.GuaranteeCertificate;
import vn.com.unit.cms.admin.all.enumdef.GuaranteeCertificateDuarationTypeEnum;
import vn.com.unit.cms.admin.all.enumdef.GuaranteeCertificateSearchEnum;
import vn.com.unit.cms.admin.all.repository.GuaranteeCertificateRepository;
import vn.com.unit.cms.admin.all.service.GuaranteeCertificateService;
import vn.com.unit.cms.core.utils.CmsUtils;
//import vn.com.unit.jcanary.authentication.UserProfile;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.jcanary.config.SystemConfig;
//import vn.com.unit.util.Util;
import vn.com.unit.ep2p.core.utils.Utility;

/**
 * GuaranteeCertificateServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */

@Service
@Transactional(readOnly=true, rollbackFor = Exception.class)
public class GuaranteeCertificateServiceImpl implements GuaranteeCertificateService{

	@Autowired
	private GuaranteeCertificateRepository guaranteeCertificateRepository;
	
	@Autowired
    private SystemConfig systemConfig;

	/**
     * getEdit
     *
     * @param id
     * @return GuaranteeCertificateEditDto
     * @author hoangnp
     */
	@Override
	public GuaranteeCertificateEditDto getEdit(Long id) {
	    GuaranteeCertificateEditDto editDto = new GuaranteeCertificateEditDto(); 
	    if(id == null){
	        
	        return editDto;
	    }
	    
		GuaranteeCertificate entity = guaranteeCertificateRepository.findOne(id);
		if(null != entity){
		   editDto.setId(entity.getId());
		   editDto.setBeneficiary(entity.getBeneficiary());
		   editDto.setCertificateNumber(entity.getCertificateNumber());
		   editDto.setCertificateType(entity.getCertificateType());
		   editDto.setGuarantee(entity.getGuarantee());
		   editDto.setGuaranteeAmountStr(CmsUtils.convertBigDcimalToString(entity.getGuaranteeAmount(), CmsUtils.PATTERN_MONEY_COMMA));
		   editDto.setGuaranteeCertificateDuration(entity.getGuaranteeCertificateDuration());
		   editDto.setIssueDate(entity.getIssueDate());
		   
		   // set GuaranteeCertificateDuarationTypeEnum
	        for (GuaranteeCertificateDuarationTypeEnum durationEnum : GuaranteeCertificateDuarationTypeEnum.values()) {
	            if (StringUtils.equals(entity.getGuranteeCertificateDurationType(), durationEnum.getValue())) {
	                editDto.setGuaranteeCertificateDurationType(durationEnum.getName());
	                break;
	            }
	        }
		}
		
		return editDto;
	}

	/**
     * initGuaranteeCertificateDurationType
     *
     * @param modelAndView
     * @author hoangnp
     */
	@Override
	public void initGuaranteeCertificateDurationType(ModelAndView modelAndView) {
	    GuaranteeCertificateDuarationTypeEnum[] durationTypeList = GuaranteeCertificateDuarationTypeEnum.values();
		modelAndView.addObject("durationTypeList",durationTypeList);
	}

	/** createOrEdit
    *
    * @param editDto
    * @author hoangnp
    */
    @Override
    @Transactional
    public void createOrEdit(GuaranteeCertificateEditDto editDto) {
//        UserProfile userProfile = UserProfileUtils.getUserProfile();
        Long id = editDto.getId();
        GuaranteeCertificate entity = new GuaranteeCertificate();
        
        if(null != id){
            entity = guaranteeCertificateRepository.findOne(id);
            if(entity == null){
                throw new BusinessException("Not found GuaranteeCertificate with id=" + editDto.getId());
            } else {
                entity.setUpdateBy(UserProfileUtils.getFullName());
                entity.setUpdateDate(new Date());
            }
        } else {
           entity.setCreateBy(UserProfileUtils.getFullName());
           entity.setCreateDate(new Date());
        }
        
        if (entity != null) {
            entity.setId(editDto.getId());
            entity.setBeneficiary(editDto.getBeneficiary());
            entity.setCertificateNumber(editDto.getCertificateNumber().toUpperCase());
            entity.setCertificateType(editDto.getCertificateType());
            entity.setGuarantee(editDto.getGuarantee());
            entity.setGuaranteeAmount(CmsUtils.convertStringToBigDcimal(editDto.getGuaranteeAmountStr(), CmsUtils.PATTERN_MONEY_COMMA));
            entity.setGuaranteeCertificateDuration(editDto.getGuaranteeCertificateDuration());
            entity.setGuranteeCertificateDurationType(editDto.getGuaranteeCertificateDurationType());
            entity.setIssueDate(editDto.getIssueDate());

            guaranteeCertificateRepository.save(entity);
            editDto.setId(entity.getId());
        }
    }

    /** findCertificateNumber
    *
    * @param certificateNumber
    * @return GuaranteeCertificate
    * @author hoangnp
    */
    @Override
    public GuaranteeCertificate findCertificateNumber(String certificateNumber) {
        return guaranteeCertificateRepository.findGuaranteeCertificateNumber(certificateNumber);
    }
    
    @Override
    public PageWrapper<GuaranteeCertificateSearchDto> search(int page,
            GuaranteeCertificateSearchDto searchDto) {
        // TODO Auto-generated method stub
        int sizeOfPage = systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
        
        PageWrapper<GuaranteeCertificateSearchDto> pageWrapper = new PageWrapper<GuaranteeCertificateSearchDto>(
                page, sizeOfPage);
        if (null == searchDto)
            searchDto =  new GuaranteeCertificateSearchDto();
        
        // set SearchParm
        setSearchParm(searchDto);
        
        int count = guaranteeCertificateRepository.countByGuaranteeCertificateSearchDto(searchDto); 
        
        List<GuaranteeCertificateSearchDto> result = new ArrayList<GuaranteeCertificateSearchDto>();
        if(count > 0){
            int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);
            
            result = guaranteeCertificateRepository.findByGuaranteeCertificateSearchDto(offsetSQL, sizeOfPage, searchDto);
            
        }
        pageWrapper.setDataAndCount(result, count);
        
        return pageWrapper;
    }
    
    /**
     * setSearchParm
     *
     * @param condition
     * @author toannt
     */
    private void setSearchParm(GuaranteeCertificateSearchDto condition) {
        if (null == condition.getFieldValues()) {
            condition.setFieldValues(new ArrayList<String>());
        }
    
        if (condition.getFieldValues().isEmpty()) {
            condition.setCertificateNumber(condition.getFieldSearch());
            condition.setCertificateType(condition.getFieldSearch());
            condition.setGuarantee(condition.getFieldSearch());
            condition.setBeneficiary(condition.getFieldSearch());
            
        } else {
            for (String field : condition.getFieldValues()) {
                if (StringUtils.equals(field, GuaranteeCertificateSearchEnum.CERTIFICATE_NUMBER.name())) {
                    condition.setCertificateNumber(condition.getFieldSearch());
                    continue;
                }
                if (StringUtils.equals(field, GuaranteeCertificateSearchEnum.CERTIFICATE_TYPE.name())) {
                    condition.setCertificateType(condition.getFieldSearch());
                    continue;
                }
                if (StringUtils.equals(field, GuaranteeCertificateSearchEnum.GUARANTEE.name())) {
                    condition.setGuarantee(condition.getFieldSearch());
                    continue;
                }
                if (StringUtils.equals(field, GuaranteeCertificateSearchEnum.BENEFICIARY.name())) {
                    condition.setBeneficiary(condition.getFieldSearch());
                    continue;
                }
                
            }

        }
    }

  
    @Override
    @Transactional
    public void deleteGuaranteeCertificate(Long id) {
        
      //check exits id
        GuaranteeCertificate guaranteeCertificate = guaranteeCertificateRepository.findOne(id);
        if(null == guaranteeCertificate){
             throw new BusinessException("Not found guarantee certificate with id=" + id);
        }
        
     // user name login
        String userName = UserProfileUtils.getUserNameLogin();
        
      //delete introduce
        guaranteeCertificate.setDeleteDate(new Date());
        guaranteeCertificate.setDeleteBy(userName);
        guaranteeCertificateRepository.save(guaranteeCertificate);
    }
}
