/*******************************************************************************
 * Class        ：LoanInterestExpressionServiceImpl
 * Created date ：2017/06/21
 * Lasted date  ：2017/06/21
 * Author       ：thuydtn
 * Change log   ：2017/06/21：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.constant.ConstantHistoryApprove;
import vn.com.unit.cms.admin.all.constant.CmsStepNoStatusConstant;
import vn.com.unit.cms.admin.all.dto.MathExpressionDto;
import vn.com.unit.cms.admin.all.dto.MathExpressionSearchDto;
import vn.com.unit.cms.admin.all.dto.MathExpressionTypeDto;
import vn.com.unit.cms.admin.all.entity.MathExpression;
import vn.com.unit.cms.admin.all.enumdef.MathExpressionCorporateExportEnum;
import vn.com.unit.cms.admin.all.enumdef.MathExpressionPersonalExportEnum;
import vn.com.unit.cms.admin.all.enumdef.MathExpressionProcessEnum;
import vn.com.unit.cms.admin.all.enumdef.MathExpressionSearchEnum;
import vn.com.unit.cms.admin.all.enumdef.MathExpressionTypeEnum;
import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;
import vn.com.unit.cms.admin.all.jcanary.dto.CustomerTypeSelectionDto;
import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
import vn.com.unit.cms.admin.all.repository.CustomerTypeRepository;
import vn.com.unit.cms.admin.all.repository.MathExpressionRepository;
import vn.com.unit.cms.admin.all.service.MathExpressionService;
import vn.com.unit.cms.core.module.banner.enumdef.StepStatusEnum;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.SearchKeyDto;
//import vn.com.unit.jcanary.authentication.UserProfile;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
//import vn.com.unit.dto.ItemColsExcelDto;
import vn.com.unit.ep2p.core.exception.BusinessException;
//import vn.com.unit.util.Util;
import vn.com.unit.ep2p.core.utils.Utility;
//import vn.com.unit.jcanary.utils.ExportExcelUtil;
//import vn.com.unit.jcanary.utils.ImportExcelUtil;
import vn.com.unit.ep2p.utils.SearchUtil;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

/**
 * LoanInterestExpressionServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class MathExpressionServiceImpl extends MathExpressionBaseService implements MathExpressionService {

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private MessageSource msg;

//    @Autowired
//    private ProcessRepository processRepository;
//
//    @Autowired
//    private JBPMService jbpmService;

    @Autowired
    private CustomerTypeRepository customerTypeRepository;

    @Autowired
    private MathExpressionRepository mathExpressionRepository;

    @Autowired
    ServletContext servletContext;

//    @Autowired
//    JProcessService jprocessService;
    
    private static final Logger logger = LoggerFactory.getLogger(MathExpressionServiceImpl.class);

//    private static final String PERSONAL = "personal";

//    private static final String CORPORATE = "corporate";

    private static final Long PERSONAL_TYPE = 9l;

    private static final Long CORPORATE_TYPE = 10l;

    public static final String PATTERN_INTEREST_RATE = "#.0#"; 
    
    @Override
    public MathExpressionDto saveAddExpressionModel(MathExpressionDto expressionModel, Locale locale,
            HttpServletRequest request) {
        MathExpressionDto expressionDto = new MathExpressionDto();
        return expressionDto;
    }

    @Override
    public MathExpressionDto getExpression(Long id, Locale locale) {

        MathExpressionDto mathExpressionDto = new MathExpressionDto();
        if (id == null) {
            // mathExpressionDto.setEnabled(Boolean.TRUE);
            mathExpressionDto.setStatus(StepStatusEnum.DRAFT.getStepNo());
            mathExpressionDto.setCreateBy(UserProfileUtils.getUserNameLogin());
        } else {
            MathExpression entity = mathExpressionRepository.findOne(id);
      	  	// dữ liệu ko tồn tại hoặc đã bị xóa
            if (entity == null || entity.getDeleteDate() != null) {
            	throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
            }
            if (null != entity) {
                mathExpressionDto.setId(entity.getId());
                mathExpressionDto.setCode(entity.getCode());
                mathExpressionDto.setExpression(entity.getExpression());
                mathExpressionDto.setDescription(entity.getDescription());
                mathExpressionDto.setName(entity.getName());
                mathExpressionDto.setExpressionType(entity.getExpressionType());
                mathExpressionDto.setStrCustomerTypeId(entity.getCustomerTypeId());
                mathExpressionDto.setAvailableDateFrom(entity.getAvailableDateFrom());
                mathExpressionDto.setAvailableDateTo(entity.getAvailableDateTo());
                mathExpressionDto.setExpressionType(entity.getExpressionType());
                mathExpressionDto.setProcessId(entity.getProcessId());
                mathExpressionDto.setProcessInstanceId(entity.getProcessIntanceId());
                mathExpressionDto.setStatus(entity.getStatus());

                mathExpressionDto.setCreateDate(entity.getCreateDate());
                mathExpressionDto.setCreateBy(entity.getCreateBy());
                mathExpressionDto.setApprovedDate(entity.getApprovedDate());
                mathExpressionDto.setApprovedBy(entity.getApprovedBy());
                mathExpressionDto.setPublishedDate(entity.getPublishedDate());
                mathExpressionDto.setPublishedBy(entity.getPublishedBy());

                mathExpressionDto.setComment(entity.getComment());
                mathExpressionDto.setMaxLoanAmountStr(
                        CmsUtils.convertBigDcimalToString(entity.getMaxLoanAmount(), CmsUtils.PATTERN_MONEY));
                mathExpressionDto.setTermType(entity.getTermType());
                mathExpressionDto.setTermValue(entity.getTermValue());
                mathExpressionDto.setIsHighlights(entity.getHighlights());

                mathExpressionDto.setLinkAlias(entity.getLinkAlias());
                mathExpressionDto.setIcon(entity.getIcon());
                mathExpressionDto.setPhysicalIcon(entity.getPhysicalIcon());
                mathExpressionDto.setKeywords(entity.getKeywords());
                mathExpressionDto.setMaxInterestRate(entity.getMaxInterestRate());
                mathExpressionDto.setMaxInterestRateStr(CmsUtils.convertDoubleToString(entity.getMaxInterestRate(),PATTERN_INTEREST_RATE));
                mathExpressionDto.setReferenceType(ConstantHistoryApprove.APPROVE_MATH_EXPRESSION);
                mathExpressionDto.setReferenceId(entity.getId());
                mathExpressionDto.setUpdateDate(entity.getUpdateDate());
                mathExpressionDto.setComment(entity.getComment());
            }
            // processID
//            Long processId = mathExpressionDto.getProcessId();
//            if (processId == null) {
//                // First step
//                if (mathExpressionDto.getStrCustomerTypeId().equals(PERSONAL_TYPE.toString())) {
//                    JProcessStepDto processDto = jprocessService
//                            .findFirstStepOfProcess(CommonConstant.CN_BUSINESS_MATH_EXPRESSION, locale.toString());
//                    processId = processDto.getProcessId();
//                } else if (mathExpressionDto.getStrCustomerTypeId().equals(CORPORATE_TYPE.toString())) {
//                    JProcessStepDto processDto = jprocessService
//                            .findFirstStepOfProcess(CommonConstant.CN_BUSINESS_MATH_EXPRESSION, locale.toString());
//                    processId = processDto.getProcessId();
//                }
//            }
//            // List button of step
//            List<JProcessStepDto> stepButtonList = jprocessService.findStepButtonListHasRole(processId,
//                    mathExpressionDto.getStatus(), locale.toString());
//            mathExpressionDto.setStepBtnList(stepButtonList);
        }

        // set statusName
//        String statusName = jprocessService.getStatusName(mathExpressionDto.getProcessId(),
//                mathExpressionDto.getStatus(), locale);
//        mathExpressionDto.setStatusName(statusName);
//        
//        String statusCode = jprocessService.getStatusCode(mathExpressionDto.getProcessId(), mathExpressionDto.getStatus(), locale);
//        mathExpressionDto.setStatusCode(statusCode);

        return mathExpressionDto;
    }

    @Override
    public void deleteById(Long id) {
        MathExpression expression = mathExpressionRepository.findOne(id);
        // this.abortJbpmProcess(expression.getProcessId(),
        // expression.getProcessIntanceId());
        if (expression != null) {
//            UserProfile userProfile = UserProfileUtils.getUserProfile();
            expression.setDeleteBy(UserProfileUtils.getUserNameLogin());
            expression.setDeleteDate(new Date());
            mathExpressionRepository.save(expression);
        }
    }

    @Override
    public PageWrapper<MathExpressionDto> searchExpression(int page, CommonSearchDto searchDto, Long customerTypeId,
            Locale locale) {
        if (null == searchDto)
            searchDto = new CommonSearchDto();

        // set status name
        searchDto.setStatusName(msg.getMessage(StepStatusEnum.DRAFT.getStatusName(), null, locale));
        searchDto.setLanguageCode(locale.toString());

        int sizeOfPage = searchDto.getPageSize() != null ? searchDto.getPageSize()
                : systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);

        PageWrapper<MathExpressionDto> pageWrapper = new PageWrapper<MathExpressionDto>(page, sizeOfPage);

        // set SearchParm
        MathExpressionSearchDto searchCondition = new MathExpressionSearchDto();
        searchCondition.setCustomerTypeId(customerTypeId.toString());
        searchCondition.setCode(searchDto.getCode());
        searchCondition.setName(searchDto.getName());
        searchCondition.setStatus(searchDto.getStatus());
        searchCondition.setStatusName(searchDto.getStatusName());
        searchCondition.setTypeText(searchDto.getTypeText());
        searchCondition.setLanguageCode(searchDto.getLanguageCode());

        int count = mathExpressionRepository.countBySearchDto(searchCondition);
        List<MathExpressionDto> result = new ArrayList<MathExpressionDto>();
        if (count > 0) {
            int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);

            result = mathExpressionRepository.findBySearchDto(offsetSQL, sizeOfPage, searchCondition);
        }

        pageWrapper.setDataAndCount(result, count);

        return pageWrapper;
    }

    public MathExpressionDto initExpressionAddModel(String customerAlias, Locale locale) {
        MathExpressionDto retVal = new MathExpressionDto();

        retVal.setStatus(StepStatusEnum.DRAFT.getStepNo());

        @SuppressWarnings("unused")
		Long processId = retVal.getProcessId();
//        if (processId == null) {
//            // First step
//            if (PERSONAL.equals(customerAlias)) {
//                JProcessStepDto processDto = jprocessService
//                        .findFirstStepOfProcess(CommonConstant.CN_BUSINESS_MATH_EXPRESSION, locale.toString());
//                processId = processDto.getProcessId();
//            } else if (CORPORATE.equals(customerAlias)) {
//                JProcessStepDto processDto = jprocessService
//                        .findFirstStepOfProcess(CommonConstant.CN_BUSINESS_MATH_EXPRESSION, locale.toString());
//                processId = processDto.getProcessId();
//            }
//        }
        // List button of step
//        List<JProcessStepDto> stepButtonList = jprocessService.findStepButtonListHasRole(processId, retVal.getStatus(),
//                locale.toString());
//        retVal.setStepBtnList(stepButtonList);
//        // set status name
//        String statusName = jprocessService.getStatusName(retVal.getProcessId(), retVal.getStatus(), locale);
//        retVal.setStatusName(statusName);
      retVal.setCreateBy(UserProfileUtils.getUserNameLogin());
      retVal.setReferenceType(ConstantHistoryApprove.APPROVE_MATH_EXPRESSION);
        
        return retVal;
    }

    @Override
    public int countByCode(String code) {
        return mathExpressionRepository.countByCode(code);
    }

    @Override
    public List<SearchKeyDto> genSearchKeyList(Locale locale) {
        List<SearchKeyDto> searchKeys = SearchUtil.genSearchKeyList(MathExpressionSearchEnum.class, msg, locale);
        return searchKeys;
    }

    @Override
    public List<MathExpressionTypeDto> initExpressionTypeSelection() {
        List<MathExpressionTypeDto> typeSelection = new ArrayList<MathExpressionTypeDto>();
        // loop enum
        for (MathExpressionTypeEnum en : MathExpressionTypeEnum.class.getEnumConstants()) {
            MathExpressionTypeDto item = new MathExpressionTypeDto(en.name(), en.toString());
            typeSelection.add(item);
        }
        return typeSelection;
    }

    @Override
    public MathExpressionDto getExpressionViewModel(Long id) {
        MathExpressionDto expressionViewModel = mathExpressionRepository.findExpressionViewDtoById(id);
        for (MathExpressionTypeEnum en : MathExpressionTypeEnum.class.getEnumConstants()) {
            if (en.name().equals(expressionViewModel.getExpressionType())) {
                expressionViewModel.setExpressionTypeNameMessageKey(en.toString());
                break;
            }
        }
        if (expressionViewModel.getLstCustomerTypeId() != null
                && expressionViewModel.getLstCustomerTypeId().size() > 0) {
            List<CustomerTypeSelectionDto> customerTypes = customerTypeRepository.findSelectionList();
            String strCustomerTypeNames = null;
            for (CustomerTypeSelectionDto customerType : customerTypes) {
                if (expressionViewModel.getLstCustomerTypeId().contains(customerType.getId())) {
                    if (strCustomerTypeNames == null) {
                        strCustomerTypeNames = customerType.getName();
                    } else {
                        strCustomerTypeNames = strCustomerTypeNames.concat(", ").concat(customerType.getName());
                    }
                }
            }
            expressionViewModel.setCustomerTypeName(strCustomerTypeNames);
        }

        return expressionViewModel;
    }

    @Override
    public MathExpressionDto getExpressionConflictEffectedDateAndType(Long id, Date availableDateFrom,
            Date availableDateTo, String expressionType, String cusTypeIdText) {
        return mathExpressionRepository.findExpressionWithTypeAndEfftectTimeExceptId(id, availableDateFrom,
                availableDateTo, expressionType, cusTypeIdText);
    }

    @Override
    @Transactional
    public boolean saveAddDraftExpressionModel(MathExpressionDto expressionModel, Locale locale,
            HttpServletRequest request) throws Exception{

        // get từ saveAddExpressionModel
        saveMathExpressionModel(expressionModel, locale, request);

        return true;
    }

    @Override
    public MathExpressionDto saveAddSubmitExpressionModel(MathExpressionDto expressionModel, Locale locale,
            HttpServletRequest request) {
        MathExpressionDto retVal = this.saveAddExpressionModel(expressionModel, locale, request);
        return retVal;
    }

    @Override
    @Transactional
    public boolean saveEditDraftExpressionModel(MathExpressionDto expressionModel, Locale locale,
            HttpServletRequest request) throws Exception {
        
        saveMathExpressionModel(expressionModel, locale, request);
        
        return true;
    }

    public MathExpressionDto updateStatusExpressionModel(MathExpressionDto expressionModel, String status) {
        MathExpression entity = mathExpressionRepository.findOne(expressionModel.getId());
//        this.updateProcess(entity.getProcessId(), entity.getProcessIntanceId(), status);
        // entity.setStatus(status);
        return new MathExpressionDto(mathExpressionRepository.save(entity));
    }

    /**
     * updateProcess
     *
     * @param id
     * @param processEnum
     * @author hand
     */
//    public void updateProcess(Long processId, Long processInstanceId, String status) {
//        vn.com.unit.jcanary.entity.Process process = processRepository.findOne(processId);
//        if (process != null) {
//            if (UserProfileUtils.hasRole(RoleConstant.ROLE_MANAGER.concat(ConstantCore.COLON_EDIT))
//                    || UserProfileUtils.hasRole(RoleConstant.ROLE_MANAGER.concat(ConstantCore.COLON_DISP))) {
//                Hashtable<String, Object> params = new Hashtable<String, Object>();
//                if (DocumentProcessEnum.APPROVAL.toString().equals(status)) {
//                    params.put(CommonConstant.PARAM_ACTION, CommonConstant.ACTION_VALUE_APPROVE);
//                } else {
//                    params.put(CommonConstant.PARAM_ACTION, CommonConstant.ACTION_VALUE_REJECT);
//                }
//                List<String> listCheck = new ArrayList<String>();
//                listCheck.add(CommonConstant.STATUS_SUBMITTED);
//                jbpmService.updateJBPMStatus(process.getDeploymentId(), processInstanceId, RoleConstant.ROLE_MANAGER,
//                        RoleConstant.ROLE_MANAGER, params, CommonConstant.PARAM_STATUS, listCheck);
//            }
//        }
//    }

    @Override
    public MathExpressionDto saveApproveExpressionModel(MathExpressionDto expressionModel) {
        MathExpressionDto expressionDto = this.updateStatusExpressionModel(expressionModel,
                MathExpressionProcessEnum.APPROVAL.toString());
        for (MathExpressionTypeEnum en : MathExpressionTypeEnum.class.getEnumConstants()) {
            if (en.name().equals(expressionDto.getExpressionType())) {
                expressionDto.setExpressionTypeNameMessageKey(en.toString());
                break;
            }
        }
//        UserProfile userProfile = UserProfileUtils.getUserProfile();
        HistoryApproveDto historyApproveDto = new HistoryApproveDto();
        historyApproveDto.setComment(expressionModel.getComment());
        historyApproveDto.setProcessId(expressionDto.getProcessId());
        historyApproveDto.setReferenceId(expressionDto.getId().longValue());
        historyApproveDto.setReferenceType(ConstantHistoryApprove.APPROVE_MATH_EXPRESSION);
        historyApproveDto.setStatusCode(MathExpressionProcessEnum.APPROVAL.toString());
//        historyApproveDto.setApprover(userProfile.getUsername());
        historyApproveDto.setApprover(UserProfileUtils.getUserNameLogin());

        // historyApproveService.addHistoryApprove(historyApproveDto,
        // MathExpressionProcessEnum.BUSINESS_CODE.toString());
        return expressionDto;
    }

    @Override
    public MathExpressionDto saveRejectExpressionModel(MathExpressionDto expressionModel) {
        MathExpressionDto expressionDto = this.updateStatusExpressionModel(expressionModel,
                MathExpressionProcessEnum.REJECT.toString());
        for (MathExpressionTypeEnum en : MathExpressionTypeEnum.class.getEnumConstants()) {
            if (en.name().equals(expressionDto.getExpressionType())) {
                expressionDto.setExpressionTypeNameMessageKey(en.toString());
                break;
            }
        }
        HistoryApproveDto historyApproveDto = new HistoryApproveDto();
//        UserProfile userProfile = UserProfileUtils.getUserProfile();
        historyApproveDto.setComment(expressionModel.getComment());
        historyApproveDto.setProcessId(expressionDto.getProcessId());
        historyApproveDto.setReferenceId(expressionDto.getId().longValue());
        historyApproveDto.setReferenceType(ConstantHistoryApprove.APPROVE_MATH_EXPRESSION);
        historyApproveDto.setStatusCode(MathExpressionProcessEnum.REJECT.toString());
        historyApproveDto.setApprover(UserProfileUtils.getUserNameLogin());

        // historyApproveService.addHistoryApprove(historyApproveDto,
        // MathExpressionProcessEnum.BUSINESS_CODE.toString());
        return expressionDto;
    }

    @Override
    public MathExpressionDto saveSubmitExpressionModel(MathExpressionDto expressionModel) {
        MathExpressionDto expressionDto = this.updateStatusExpressionModel(expressionModel,
                MathExpressionProcessEnum.SUBMIT.toString());

        for (MathExpressionTypeEnum en : MathExpressionTypeEnum.class.getEnumConstants()) {
            if (en.name().equals(expressionDto.getExpressionType())) {
                expressionDto.setExpressionTypeNameMessageKey(en.toString());
                break;
            }
        }
        HistoryApproveDto historyApproveDto = new HistoryApproveDto();
//        UserProfile userProfile = UserProfileUtils.getUserProfile();
        historyApproveDto.setComment(expressionModel.getComment());
        historyApproveDto.setProcessId(expressionDto.getProcessId());
        historyApproveDto.setReferenceId(expressionDto.getId().longValue());
        historyApproveDto.setReferenceType(ConstantHistoryApprove.APPROVE_MATH_EXPRESSION);
        historyApproveDto.setStatusCode(MathExpressionProcessEnum.SUBMIT.toString());
        historyApproveDto.setApprover(UserProfileUtils.getUserNameLogin());

        // historyApproveService.addHistoryApprove(historyApproveDto,
        // MathExpressionProcessEnum.BUSINESS_CODE.toString());
        return expressionDto;
    }

    /**
     * getMaxCode
     *
     * @author nhutnn
     * @return max code
     */
    @Override
    public String getMaxCode() {
        return mathExpressionRepository.getMaxCode();
    }

    /**
     * findExpressionByExpressionTypeAndCustomerTypeId
     *
     * @author diennv
     * @return List<MathExpressionDto>
     */
    @Override
    public List<MathExpressionDto> findExpressionByExpressionTypeAndCustomerTypeId(String expressionType,
            String customerTypeId) {
        List<MathExpressionDto> listMathExpression = mathExpressionRepository
                .findExpressionByExpressionTypeAndCustomerTypeId(expressionType, customerTypeId,
                        CmsStepNoStatusConstant.STEP_APPROVED);
        return listMathExpression;
    }

    /**
     * findExpressionByCustomerTypeId
     *
     * @author taitm
     * @return List<MathExpressionDto>
     */
    @Override
    public List<MathExpressionDto> getExpressionByCustomerTypeId(Long customerId, Integer status) {
        List<MathExpressionDto> result = mathExpressionRepository.findExpressionByExpressionTypeAndCustomerTypeId(null,
                customerId.toString(), status);
        if (result == null) {
            result = new ArrayList<MathExpressionDto>();
        }
        return result;
    }

    @Override
    public MathExpression findByCode(String code) {
        return mathExpressionRepository.findByCode(code);
    }

    @Override
    public void exportExcelMathExpression(CommonSearchDto searchDto, HttpServletResponse response, Long customerTypeId,
            Locale locale) {
        try {
            // set status name
            searchDto.setStatusName(msg.getMessage(StepStatusEnum.DRAFT.getStatusName(), null, locale));
            String templateName = "";
            
            if(customerTypeId.equals(PERSONAL_TYPE)) {
                templateName = vn.com.unit.cms.admin.all.constant.CmsCommonConstant.TEMPLATE_TOOL_PERSONAL;
            }else if(customerTypeId.equals(CORPORATE_TYPE)){
            	templateName = vn.com.unit.cms.admin.all.constant.CmsCommonConstant.TEMPLATE_TOOL_CORPORATE;
            }
            
            @SuppressWarnings("unused")
			String template = servletContext.getRealPath(CmsCommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + templateName
                    + CmsCommonConstant.TYPE_EXCEL;
            @SuppressWarnings("unused")
			String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);

            // set SearchParm
            MathExpressionSearchDto searchCondition = new MathExpressionSearchDto();
            searchCondition.setCustomerTypeId(customerTypeId.toString());
            searchCondition.setCode(searchDto.getCode());
            searchCondition.setName(searchDto.getName());
            searchCondition.setStatus(searchDto.getStatus());
            searchCondition.setStatusName(searchDto.getStatusName());
            searchCondition.setTypeText(searchDto.getTypeText());
            searchCondition.setLanguageCode(searchDto.getLanguageCode());
            @SuppressWarnings("unused")
			List<MathExpressionDto> lstDatas = null;

            // listData
            lstDatas = mathExpressionRepository.ExportExcelfindBySearchDto(searchCondition);
            List<ItemColsExcelDto> cols = new ArrayList<>();
            // start fill data to workbook
            
            if(customerTypeId.equals(PERSONAL_TYPE)) {
            	ImportExcelUtil.setListColumnExcel(MathExpressionPersonalExportEnum.class, cols);
            }else if(customerTypeId.equals(CORPORATE_TYPE)){
            	ImportExcelUtil.setListColumnExcel(MathExpressionCorporateExportEnum.class, cols);
            }            
            
            @SuppressWarnings("unused")
			ExportExcelUtil<MathExpressionDto> exportExcel = new ExportExcelUtil<>();
            // do export
//            exportExcel.exportExcelWithXSSFTools(template, locale, lstDatas, MathExpressionDto.class, cols,
//                    datePattern, response, templateName);
        } catch (Exception e) {
            logger.error("##exportExcelMathExpression##", e);
        }
    }

	@Override
	public int countDependencies(Long mathExpressionId, List<Long> lstStatus) {
		return mathExpressionRepository.countDependencies(mathExpressionId, lstStatus);
	}
	
	@Override
	public List<Map <String, String>> listDependencies(Long mathExpressionId, List<Long> lstStatus) {
		return mathExpressionRepository.listDependencies(mathExpressionId, lstStatus);
	}

    @Override
    public MathExpressionDto getEdit(Long id, String customerAlias, Locale locale) {
        return getExpression(id, locale);
    }
}
