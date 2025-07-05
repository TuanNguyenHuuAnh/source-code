
package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.constant.ConstantHistoryApprove;
import vn.com.unit.cms.admin.all.dto.EmailCommonDto;
import vn.com.unit.cms.admin.all.dto.InterestRateValueDto;
import vn.com.unit.cms.admin.all.entity.InterestRateValue;
import vn.com.unit.cms.admin.all.enumdef.StepActionEnum;
import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
import vn.com.unit.cms.admin.all.repository.InterestRateValueRepository;
import vn.com.unit.cms.admin.all.service.InterestRateValueService;
import vn.com.unit.cms.core.module.banner.enumdef.StepStatusEnum;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.common.service.EmailService;
import vn.com.unit.core.dto.JcaConstantDto;
//import vn.com.unit.jcanary.authentication.UserProfile;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.core.ers.service.impl.DocumentWorkflowCommonServiceImpl;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.ep2p.core.utils.NullAwareBeanUtils;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class InterestRateValueServiceImpl
        extends DocumentWorkflowCommonServiceImpl<InterestRateValueDto, InterestRateValueDto>
        implements InterestRateValueService {

	@Autowired
	InterestRateValueRepository interestRateValueRepository;

//	@Autowired
//	JProcessService jprocessService;
//
//	@Autowired
//	HistoryApproveService historyApproveService;

	@Autowired
	MessageSource msg;

	@Autowired
	EmailService emailService;

//	@Autowired
//	AccountService accountService;
	
//    @Autowired
//    ConstantDisplayService constantDisplayService;
	
//	@Autowired
//	EmailUtil emailUtil;
	
    @Autowired
	private JcaConstantService jcaConstantService;
	
	private final String INTEREST_RATE_TYPE = "M14";
	
	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(InterestRateValueServiceImpl.class);

	@Override
	public List<InterestRateValueDto> findInterestRateValueByDto(InterestRateValueDto interestRateValueDto,Locale locale) {
		List<InterestRateValueDto> lstValues = new ArrayList<>();
		if(null == interestRateValueDto)
			interestRateValueDto  = new InterestRateValueDto();
		// set status name
		interestRateValueDto.setStatusName(msg.getMessage(StepStatusEnum.DRAFT.getStatusName(), null, locale));
		lstValues = interestRateValueRepository.findInterestRateValueByDto(interestRateValueDto);
		interestRateValueDto.setReferenceType(ConstantHistoryApprove.APPROVE_INTEREST_RATE_VALUE);
		if (CollectionUtils.isNotEmpty(lstValues)) {
			for (InterestRateValueDto value : lstValues) {
				value.setLstValues(new ArrayList<>());
				value.getLstValues().add(value.getValue01());
				value.getLstValues().add(value.getValue02());
				value.getLstValues().add(value.getValue03());
				value.getLstValues().add(value.getValue04());
				value.getLstValues().add(value.getValue05());
				value.getLstValues().add(value.getValue06());
				value.getLstValues().add(value.getValue07());
				value.getLstValues().add(value.getValue08());
				value.getLstValues().add(value.getValue09());
				value.getLstValues().add(value.getValue10());
			}
		}
		return lstValues;
	}

	@Override
	public void deleteByLstId(List<Long> lstId) {
		String userName = UserProfileUtils.getUserNameLogin();
		interestRateValueRepository.deleteByListId(new Date(), userName, lstId);
	}

	@Override
	public Integer countTotalTitleHaveValue(String interestRateType) {
		return interestRateValueRepository.countTotalTitleHaveValue(interestRateType);
	}

	@Override
	public boolean doEdit(InterestRateValueDto interestRateValueDto, Locale locale, HttpServletRequest request)
			throws Exception {
		// user name login
//		UserProfile userProfile = UserProfileUtils.getUserProfile();
		saveInterestRateValue(interestRateValueDto, UserProfileUtils.getUserNameLogin(), locale, request);
		return true;
	}

	public void saveInterestRateValue(InterestRateValueDto interestRateValueDto, String usernameLogin, Locale locale,
            HttpServletRequest request) throws Exception {
	    Integer status = interestRateValueDto.getStatus();
        if (CollectionUtils.isNotEmpty(interestRateValueDto.getDatas())) {
            List<InterestRateValueDto> datas = new ArrayList<>(interestRateValueDto.getDatas());
            // Default status is step 1
            Integer statusOld = interestRateValueDto.getStatus();
            // process id
            Long processId = interestRateValueDto.getProcessId();
            // current step
//            JProcessStepDto currentActionStep = jprocessService.findCurrentProcessStep(processId, statusOld, interestRateValueDto.getButtonId());
//            Integer statusNew = jprocessService.getNextStepNo(currentActionStep, null);
            
            int count = -1;
            for (InterestRateValueDto data : datas) {
                count++;
                data.setInterestRateType(interestRateValueDto.getInterestRateType());
                InterestRateValue entity = new InterestRateValue();
                if (data.getId() == null) {
                    entity.setCustomerTypeId(interestRateValueDto.getCustomerTypeId());
                    entity.setCreateBy(UserProfileUtils.getUserNameLogin());
                    entity.setCreateDate(new Date());
                } else {
                    entity = interestRateValueRepository.findOne(data.getId());
                    
                    if (null == entity) {
                        throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_WITH_ID, null, locale) + data.getId());
                    }
                    
                    if (count == 0 && entity.getUpdateDate() != null && !entity.getStatus().equals(status)){
                        throw new BusinessException(msg.getMessage(ConstantCore.MSG_DATA_IS_UPDATED_BY_OTHERS, null, locale));
                    }
                    
                    entity.setUpdateBy(UserProfileUtils.getUserNameLogin());
                    entity.setUpdateDate(new Date());
                }
                try {
                    NullAwareBeanUtils.copyPropertiesWONull(data, entity);

                    interestRateValueDto.setReferenceType(ConstantHistoryApprove.APPROVE_INTEREST_RATE_VALUE);

                    // Default status is step 1
                    // statusNew = interestRateValueDto.getStatus();

                    // process id
                    processId = interestRateValueDto.getProcessId();
                    // current step
//                    currentActionStep = null;
//                    // if action process
//                    if (!StringUtils.equals(interestRateValueDto.getButtonId(), StepActionEnum.SAVE.getCode())) {
//                        if (processId == null) {
//                            // First step
//                            JProcessStepDto processDto = jprocessService
//                                    .findFirstStepOfProcess(CommonConstant.BUSINESS_INTEREST_RATE, locale.toString());
//                            processId = processDto.getProcessId();
//                        }
//                        currentActionStep = jprocessService.findCurrentProcessStep(processId, statusOld,
//                                interestRateValueDto.getButtonId());
//                        
//                        interestRateValueDto.setOldStatus(statusOld);
//                        interestRateValueDto.setStatus(statusNew);
//                        
//                        interestRateValueDto.setCurrItem(currentActionStep.getItems());
//                    }
//                    entity.setProcessId(processId);
//                    entity.setStatus(statusNew);
                    
                    // Set_Note
                    entity.setNote(interestRateValueDto.getNote());
                    // save entity
                    interestRateValueRepository.save(entity);

                    if (entity.getId() != null) {
                        data.setId(entity.getId());
                    }
                } catch (Exception e) {
                    logger.error("##saveInterestRateValue##", e);
                    throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
                }
            }
			// if action process
			if (!StringUtils.equals(interestRateValueDto.getButtonId().toString(), StepActionEnum.SAVE.getCode())) {
				try {
					// insert comment
					HistoryApproveDto historyApproveDto = new HistoryApproveDto();
					historyApproveDto.setApprover(UserProfileUtils.getFullName());
					historyApproveDto.setProcessId(processId);
//					historyApproveDto.setProcessStep(statusNew.longValue());
					historyApproveDto.setReferenceId(Long.parseLong(interestRateValueDto.getInterestRateType()));
					historyApproveDto.setReferenceType(ConstantHistoryApprove.APPROVE_INTEREST_RATE_VALUE);
					historyApproveDto.setActionId(interestRateValueDto.getButtonId().toString());
					historyApproveDto.setOldStep(statusOld);
					historyApproveDto.setComment(interestRateValueDto.getNote());
					historyApproveDto.setAccountId(UserProfileUtils.getAccountId());
//					historyApproveService.addHistoryApprove(historyApproveDto);
				} catch (Exception e) {
					logger.error("updateHistoryApprove: " + e.getMessage());
					throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
				}

				interestRateValueDto.setOldStatus(statusOld);
				// send mail
				sendMail(interestRateValueDto, request, locale);
			}
        }
    }

	private void sendMail(InterestRateValueDto editDto, HttpServletRequest request, Locale locale){
        try {

            EmailCommonDto emailCommon = new EmailCommonDto();
            emailCommon.setActionName(msg.getMessage("email.template.interest.rate.value", null, locale));
            emailCommon.setButtonAction(editDto.getButtonAction());
            emailCommon.setButtonId(editDto.getButtonId().toString());
            emailCommon.setComment(editDto.getNote());

            // Nội dung
            LinkedHashMap<String, String> content = new LinkedHashMap<>();

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
            
//            List<ConstantDisplay> lstType = constantDisplayService.findByType(INTEREST_RATE_TYPE);
            
            List<JcaConstantDto> lstType = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(INTEREST_RATE_TYPE.toString(), "EN");

            String title = "";
            for (JcaConstantDto type : lstType) {
                if (type.getKind().equals(editDto.getInterestRateType())) {
                    title = type.getName();
                }
            }
            content.put("Tên bảng lãi suất", title);
            emailCommon.setContent(content);

            emailCommon.setCurrItem(editDto.getCurrItem());

            emailCommon.setId(Long.parseLong(editDto.getInterestRateType()));
            emailCommon.setOldStatus(editDto.getOldStatus());
            emailCommon.setProcessId(editDto.getProcessId());
            emailCommon.setReferenceType(editDto.getReferenceType());
            emailCommon.setStatus(editDto.getStatus());

            // Subject của email
            emailCommon.setSubject(msg.getMessage("subject.email.template.interest.rate.value", null, locale));

            emailCommon.setUrl(CmsUtils.getBaseUrl(request) + "/" + editDto.getCustomerAlias()
                    + "/interest-rate-value/edit?id=" + editDto.getId());

            // emailCommon.getCurrItem();
            
//            emailUtil.sendMail(emailCommon, request, locale);
        } catch (Exception e) {
            throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
        }
    }

    @Override
    public InterestRateValueDto getEdit(Long id, String customerAlias, Locale locale) {
        // TODO Auto-generated method stub
        return null;
    }
}
