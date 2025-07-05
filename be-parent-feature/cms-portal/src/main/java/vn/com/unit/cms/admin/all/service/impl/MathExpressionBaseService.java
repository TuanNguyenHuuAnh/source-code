/*******************************************************************************
 * Class        ：MathExpressionBaseService
 * Created date ：2017/06/21
 * Lasted date  ：2017/06/21
 * Author       ：thuydtn
 * Change log   ：2017/06/21：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import vn.com.unit.cms.admin.all.constant.AdminUrlConst;
import vn.com.unit.cms.admin.all.constant.ConstantHistoryApprove;
import vn.com.unit.cms.admin.all.dto.EmailCommonDto;
import vn.com.unit.cms.admin.all.dto.MathExpressionDto;
import vn.com.unit.cms.admin.all.entity.MathExpression;
import vn.com.unit.cms.admin.all.enumdef.StepActionEnum;
import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
import vn.com.unit.cms.admin.all.jcanary.utils.APIUtils;
import vn.com.unit.cms.admin.all.repository.MathExpressionRepository;
import vn.com.unit.cms.admin.all.service.CustomerTypeService;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.common.service.EmailService;
//import vn.com.unit.jcanary.authentication.UserProfile;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.service.AccountService;
import vn.com.unit.ep2p.core.ers.service.impl.DocumentWorkflowCommonServiceImpl;
import vn.com.unit.ep2p.core.exception.BusinessException;
//import vn.com.unit.jcanary.utils.Utils;

/**
 * MathExpressionBaseService
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public class MathExpressionBaseService extends DocumentWorkflowCommonServiceImpl<MathExpressionDto, MathExpressionDto> {
    @Autowired
    MathExpressionRepository mathExpressionRepository;
    
    @Autowired
    CustomerTypeService customerTypeService;
    
//    @Autowired
//    ProcessService processService;
//    
//    @Autowired
//    JProcessService jprocessService;
//    
//    @Autowired
//	HistoryApproveService historyApproveService;
    
    @Autowired
	AccountService accountService;
    
    @Autowired
	MessageSource msg;
    
    @Autowired
	EmailService emailService;
    
//    @Autowired
//    EmailUtil emailUtil;
    
//	private static final Long PERSONAL_TYPE = 9l;

//	private static final Long CORPORATE_TYPE = 10l;
	
    private static final Logger logger = LoggerFactory.getLogger(MathExpressionBaseService.class);

    protected void saveMathExpressionModel(MathExpressionDto expressionModel, Locale locale, HttpServletRequest request) throws Exception {
    	
//    	UserProfile userProfile = UserProfileUtils.getUserProfile();
    	MathExpression expressionEntity = new MathExpression();
    	
		if(expressionModel.getId() != null){
			expressionEntity = mathExpressionRepository.findOne(expressionModel.getId());
			if (expressionEntity == null || expressionEntity.getDeleteDate() != null) {
				throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_WITH_ID, null, locale) + expressionModel.getId());
			}
            
            if (expressionEntity.getUpdateDate() != null && !expressionEntity.getUpdateDate().equals(expressionModel.getUpdateDate())){
                throw new BusinessException(msg.getMessage(ConstantCore.MSG_DATA_IS_UPDATED_BY_OTHERS, null, locale));
            }
            
            expressionEntity.copyDtoProperties(expressionModel);
            
			expressionEntity.setUpdateBy(UserProfileUtils.getUserNameLogin());
			expressionEntity.setUpdateDate(new Date());
			expressionEntity.setComment(expressionModel.getComment());
		}else{
			expressionEntity = expressionModel.createEntity();
			expressionEntity.setOwnerBranchId(UserProfileUtils.getBranchId());
			expressionEntity.setOwnerId(UserProfileUtils.getAccountId());
			expressionEntity.setOwnerSectionId(UserProfileUtils.getDepartmentId());
			expressionEntity.setCreateBy(UserProfileUtils.getUserNameLogin());
			expressionEntity.setCreateDate(new Date());
			expressionEntity.setComment(expressionModel.getComment());
			expressionEntity.setProcessId(expressionModel.getProcessId());
		}
        try {
            // if action process
//            if (!StringUtils.equals(expressionModel.getButtonId().toString(), StepActionEnum.SAVE.getCode())) {
//                if (expressionModel.getProcessId() == null) {
//                    // First step
//                    if (expressionModel.getStrCustomerTypeId().equals(PERSONAL_TYPE.toString())) {
//                        JProcessStepDto processDto = jprocessService
//                                .findFirstStepOfProcess(CommonConstant.CN_BUSINESS_MATH_EXPRESSION, locale.toString());
//                        expressionModel.setProcessId(processDto.getProcessId());
//                    } else if (expressionModel.getStrCustomerTypeId().equals(CORPORATE_TYPE.toString())) {
//                        JProcessStepDto processDto = jprocessService
//                                .findFirstStepOfProcess(CommonConstant.DN_BUSINESS_MATH_EXPRESSION, locale.toString());
//                        expressionModel.setProcessId(processDto.getProcessId());
//                    }
//                }
//
//                // current step
//                JProcessStepDto currentActionStep = jprocessService.findCurrentProcessStep(
//                        expressionModel.getProcessId(), expressionModel.getStatus(), expressionModel.getButtonId());
//                Integer status = jprocessService.getNextStepNo(currentActionStep, null);
//
//                expressionModel.setOldStatus(expressionModel.getStatus());
//                // set status
//                expressionModel.setStatus(status);
//                expressionModel.setCurrItem(currentActionStep.getItems());
//            }
            expressionEntity.setProcessId(expressionModel.getProcessId());
            expressionEntity.setStatus(expressionModel.getStatus());
            // save entity

            expressionEntity = mathExpressionRepository.save(expressionEntity);
        } catch (Exception e) {
            logger.error("##Error Save MathExpression##", e.getMessage());
            throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
        }
        
		if (expressionEntity.getId() != null) {
			expressionModel.setId(expressionEntity.getId());
			expressionModel.setCode(expressionEntity.getCode());
		}
		
		// if action process
		if (!StringUtils.equals(expressionModel.getButtonId().toString(), StepActionEnum.SAVE.getCode())) {
			// insert comment
            try {
                HistoryApproveDto historyApproveDto = new HistoryApproveDto();
                historyApproveDto.setApprover(UserProfileUtils.getFullName());
                historyApproveDto.setComment(expressionModel.getComment());
                historyApproveDto.setProcessId(expressionModel.getProcessId());
                historyApproveDto.setProcessStep(expressionModel.getStatus().longValue());
                historyApproveDto.setReferenceId(expressionModel.getId());
                historyApproveDto.setReferenceType(ConstantHistoryApprove.APPROVE_MATH_EXPRESSION);
                historyApproveDto.setActionId(expressionModel.getButtonId().toString());
                historyApproveDto.setOldStep(expressionModel.getOldStatus());
                historyApproveDto.setAccountId(UserProfileUtils.getAccountId());
//                historyApproveService.addHistoryApprove(historyApproveDto);
            } catch (Exception e) {
                logger.error("updateHistoryApprove: " + e.getMessage());
                throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
            }
			// send mail
			sendMail(expressionModel, request, locale);
			
			// clear cache api /commons
            APIUtils.callApiGet(AdminUrlConst.URL_CACHES_COMMONS);
            
            // clear cache api /personal
            APIUtils.callApiGet(AdminUrlConst.URL_CACHES_PERSONAL);
            
            // clear cache api /corporate
            APIUtils.callApiGet(AdminUrlConst.URL_CACHES_CORPORATE);
		}
    }
    
    private void sendMail(MathExpressionDto editDto, HttpServletRequest request, Locale locale){
        try {
            
            EmailCommonDto emailCommon = new EmailCommonDto();
            emailCommon.setActionName(msg.getMessage("email.template.math.expression", null, locale));
            emailCommon.setButtonAction(editDto.getButtonAction());
            emailCommon.setButtonId(editDto.getButtonId().toString());
            emailCommon.setComment(editDto.getComment());

            // Nội dung
            LinkedHashMap<String, String> content = new LinkedHashMap<>();
            content.put("Mã", editDto.getCode());
            content.put("Tên công cụ", editDto.getName());
            emailCommon.setContent(content);

            emailCommon.setCurrItem(editDto.getCurrItem());

            emailCommon.setId(editDto.getId());
            emailCommon.setOldStatus(editDto.getOldStatus());
            emailCommon.setProcessId(editDto.getProcessId());
            emailCommon.setReferenceType(editDto.getReferenceType());
            emailCommon.setStatus(editDto.getStatus());

            // Subject của email
            emailCommon.setSubject(msg.getMessage("subject.email.template.math.expression", null, locale));

            emailCommon.setUrl(CmsUtils.getBaseUrl(request) + "/" + editDto.getCustomerAlias()
                    + "/math-expression/edit?id=" + editDto.getId());

//            emailUtil.sendMail(emailCommon, request, locale);
        } catch (Exception e) {
            throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
        }
    }
    
}
