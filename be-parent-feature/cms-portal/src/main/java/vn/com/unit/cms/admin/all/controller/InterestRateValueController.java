
package vn.com.unit.cms.admin.all.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.dto.InterestRateSetupDto;
import vn.com.unit.cms.admin.all.dto.InterestRateTitleDto;
import vn.com.unit.cms.admin.all.dto.InterestRateValueDto;
import vn.com.unit.cms.admin.all.service.InterestRateTitleService;
import vn.com.unit.cms.admin.all.service.InterestRateValueService;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.cms.core.module.banner.enumdef.StepStatusEnum;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
//import vn.com.unit.jcanary.constant.RoleConstant;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.core.controller.DocumentWorkflowCommonController;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;

@Controller
@RequestMapping(value = "/{customerAlias}/" + UrlConst.INTEREST_RATE_VALUE)
public class InterestRateValueController
        extends DocumentWorkflowCommonController<InterestRateValueDto, InterestRateValueDto> {

    @Autowired
    InterestRateValueService interestRateValueService;
    
    @Autowired
    InterestRateTitleService interestTitleService;

//    @Autowired
//    ConstantDisplayService constantDisplayService;

    @Autowired
    LanguageService languageService;

    @Autowired
    MessageSource msg;

    @Autowired
    SystemConfig systemConfig;
    
//    @Autowired
//	HistoryApproveService historyApproveService;
//    
//    @Autowired
//	JProcessService jprocessService;

    @Autowired
	private JcaConstantService jcaConstantService;

    private static final Logger logger = LoggerFactory.getLogger(InterestRateValueController.class);

    private static final String INTEREST_RATE_VALUE_EDIT = "interest.rate.value.edit";

    private final long CORPORATE_TYPE_ID = 10l;

    private final long PERSONAL_TYPE_ID = 9l;
    
    private final String INTEREST_RATE_TYPE = "M14";

    @InitBinder
    public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
        if (null == request.getSession().getAttribute(ConstantCore.FORMAT_DATE)) {
            request.getSession().setAttribute("formatDate", systemConfig.getConfig(SystemConfig.DATE_PATTERN));
        }
        // The date format to parse or output your dates
        String patternDate = ConstantCore.FORMAT_DATE_FULL;
        SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
        // Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        // Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);

        binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
    }

//    @RequestMapping(value = "/edit", method = { RequestMethod.GET })
//	public ModelAndView editInterestRateValueDto(@PathVariable(value = "customerAlias") String customerAlias,
//			@ModelAttribute(value = "interestRateValueDto") InterestRateValueDto interestRateValueDto, Locale locale,
//			@RequestParam(required = false, value = "interestRateType") String interestRateType) {
//        ModelAndView mav = new ModelAndView(INTEREST_RATE_VALUE_EDIT);
//        mav.addObject("customerAlias", customerAlias);
//        try {
//            Long customerTypeId;
//            if (UrlConst.CORPORATE.equals(customerAlias)) {
//                customerTypeId = CORPORATE_TYPE_ID;
//            } else {
//                /*---- taitm - phân quyền theo menu -------*/
//                if (!hasRoleAccess()) {
//                    return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//                }
//                /*---End---*/
//                customerTypeId = PERSONAL_TYPE_ID;
//            }
//            interestRateValueDto.setCustomerTypeId(customerTypeId);
//
//    		// ${constantDisplay.cat} => ${constantDisplay.kind}
//    		// #{${constantDisplay.code}} => #{${constantDisplay.code}}
//    		// constDispService.findByType("M10");
//    		// => List<JcaConstantDto> statusList = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M10.toString(), "EN");
//
//    		// type => groupCode
//    		// cat	=> kind
//    		// code => code
//    		
//    		// catOfficialName => name
//    		
//    		// ConstantDisplay motive = constantDisplayService.findByTypeAndCat(ConstDispType.MOTIVE.toString(), emailModel.getMotive().toString());
//    		// JcaConstantDto motive = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind(ConstDispType.MOTIVE.toString(), emailModel.getMotive().toString(), "EN").get(0);
//
//    		// List<ConstantDisplay> listBannerPage = constDispService.findByType(ConstDispType.B01);
//        	// List<JcaConstantDto> listBannerPage = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.B01.toString(), "EN");
//
////            List<ConstantDisplay> lstType = constantDisplayService.findByType(INTEREST_RATE_TYPE);
//            
//            List<JcaConstantDto> lstType = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(INTEREST_RATE_TYPE, "EN");
//            
//            mav.addObject("lstType", lstType);
//            
//            if (interestRateType == null) {
//                interestRateValueDto.setInterestRateType(lstType.get(0).getKind());
//            }
////            interestRateValueDto.setInterestRateType(interestRateType);
//            
//            List<LanguageDto> languageList = languageService.getLanguageDtoList();
//            mav.addObject("languageList", languageList);
//            
//            // inItScreenEdits
//  			this.inItScreenEdit(mav, interestRateValueDto, locale);
//  			
//            // set list interest rate
//            List<InterestRateSetupDto> lstTitleSetup = setListInterestRateSetup(interestRateValueDto, mav, customerTypeId, languageList,locale);
//            mav.addObject("lstTitleSetup", lstTitleSetup);
//            
//        } catch (Exception e) {
//            logger.error("__editInterestRateValueDto__", e);
//        }
//        return mav;
//    }

	private void inItScreenEdit(ModelAndView mav, InterestRateValueDto interestRateValueDto, Locale locale) {
		// Init PageWrapper History Approval
//		PageWrapper<HistoryApproveDto> pageWrapper = historyApproveService.doSearch(1,Long.parseLong(interestRateValueDto.getInterestRateType()),
//				interestRateValueDto.getProcessId(), ConstantHistoryApprove.APPROVE_INTEREST_RATE_VALUE, locale);
//		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
	

	}
    
    private List<InterestRateSetupDto> setListInterestRateSetup(InterestRateValueDto interestRateValueDto, ModelAndView mav, Long customerTypeId,
            List<LanguageDto> languageList,Locale locale) {
        List<InterestRateSetupDto> lstTitleSetup = new ArrayList<>();
        
        InterestRateTitleDto titleDto = new InterestRateTitleDto();
        titleDto.setInterestRateType(interestRateValueDto.getInterestRateType());
        titleDto.setCustomerTypeId(customerTypeId);
        
        for (LanguageDto languageDto : languageList) {
            // list title
            titleDto.setLanguageCode(languageDto.getCode().toUpperCase());
            List<InterestRateTitleDto> lstTitleTmp = interestTitleService.findInterestRateTitleByDto(titleDto);

            // list value
            interestRateValueDto.setLanguageCode(languageDto.getCode().toUpperCase());
            List<InterestRateValueDto> lstValues = interestRateValueService.findInterestRateValueByDto(interestRateValueDto,locale);
            if (CollectionUtils.isNotEmpty(lstValues) && CollectionUtils.isNotEmpty(lstTitleTmp)) {
                for (InterestRateValueDto value : lstValues) {
                    for (int i = value.getLstValues().size() - 1; i > lstTitleTmp.size() - 1; i--) {
                        value.getLstValues().remove(i);
                    }
                }
            }
            // list setup
            InterestRateSetupDto setupDto = new InterestRateSetupDto();
            setupDto.setLanguageDto(languageDto);
            setupDto.setLstTitleDto(lstTitleTmp);
            setupDto.setLstValueDto(lstValues);
            lstTitleSetup.add(setupDto);
        }
        if(CollectionUtils.isNotEmpty(lstTitleSetup) && CollectionUtils.isNotEmpty(lstTitleSetup.get(0).getLstValueDto())) {
        	
        	Integer status = lstTitleSetup.get(0).getLstValueDto().get(0).getStatus();
        	String note = lstTitleSetup.get(0).getLstValueDto().get(0).getNote();
        	String statusName = lstTitleSetup.get(0).getLstValueDto().get(0).getStatusName();
        	Date updateDate = lstTitleSetup.get(0).getLstValueDto().get(0).getUpdateDate();
        	List<InterestRateValueDto> lstValueDto =  lstTitleSetup.get(0).getLstValueDto();
        	if(status == null ) {
        		status = StepStatusEnum.DRAFT.getStepNo();
        		statusName = "";
        	}
        	interestRateValueDto.setStatus(status);
        	interestRateValueDto.setNote(note);
        	interestRateValueDto.setStatusName(statusName);
        	interestRateValueDto.setUpdateDate(updateDate);
        	interestRateValueDto.setDatas(lstValueDto);
        	interestRateValueDto.setReferenceId(lstTitleSetup.get(0).getLstValueDto().get(0).getId());
        	
//        	Long processId = lstTitleSetup.get(0).getLstValueDto().get(0).getProcessId();
//            if (processId == null) {
//            	// First step
//            	JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(CommonConstant.BUSINESS_INTEREST_RATE, locale.toString());
//            	processId = processDto.getProcessId();
//            }
//            
//            // List button of step
//            List<JProcessStepDto> stepButtonList = jprocessService.findStepButtonListHasRole(processId, status, locale.toString());
//            interestRateValueDto.setStepBtnList(stepButtonList);
//            interestRateValueDto.setProcessId(processId);
            //getinItScreenEdit
            this.inItScreenEdit(mav,interestRateValueDto,locale);
        }
        
        if(interestRateValueDto.getStatus() == null) {
        	interestRateValueDto.setStatus(StepStatusEnum.DRAFT.getStepNo());
        }
        return lstTitleSetup;
    }

//    @RequestMapping(value = "/doSave", method = { RequestMethod.POST })
//    public ModelAndView saveInterestRateValueDto(@PathVariable(value = "customerAlias") String customerAlias,
//            @ModelAttribute(name = "interestRateValueDto") InterestRateValueDto interestRateValueDto,
//            Locale locale,HttpServletRequest request,RedirectAttributes redirectAttributes) throws Exception {
//
//        ModelAndView mav = new ModelAndView(INTEREST_RATE_VALUE_EDIT);
//        /*---- taitm - phân quyền theo menu -------*/
//        if (StringUtils.equals(interestRateValueDto.getButtonId(), StepActionEnum.SAVE.getCode())) {
//            if (!hasRoleEdit()) {
//                return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//            }
//            /*---End---*/
//        } else {
//            // Security for this page.
//            if (!UserProfileUtils.hasRole(interestRateValueDto.getCurrItem())) {
//                return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//            }
//        }
//        // Init message list
//        MessageList messageList = new MessageList(Message.SUCCESS);
//        Long customerTypeId;
//        if (UrlConst.CORPORATE.equals(customerAlias)) {
//            interestRateValueDto.setCustomerTypeId(CORPORATE_TYPE_ID);
//            customerTypeId = CORPORATE_TYPE_ID;
//        } else {
//            interestRateValueDto.setCustomerTypeId(PERSONAL_TYPE_ID);
//            customerTypeId = PERSONAL_TYPE_ID;
//        }
//
//        // set language
//        List<LanguageDto> languageList = setInfoCommonInterestRateValue(mav);
//        // set list interest rate title
//        List<InterestRateSetupDto> lstTitleSetup = new ArrayList<>();
//        InterestRateTitleDto titleDto = new InterestRateTitleDto();
//        titleDto.setInterestRateType(interestRateValueDto.getInterestRateType());
//        titleDto.setCustomerTypeId(customerTypeId);
//        for (LanguageDto languageDto : languageList) {
//            // list title
//            titleDto.setLanguageCode(languageDto.getCode().toUpperCase());
//            List<InterestRateTitleDto> lstTitleTmp = interestTitleService.findInterestRateTitleByDto(titleDto);
//
//            // list setup
//            InterestRateSetupDto setupDto = new InterestRateSetupDto();
//            setupDto.setLanguageDto(languageDto);
//            setupDto.setLstTitleDto(lstTitleTmp);
//            lstTitleSetup.add(setupDto);
//        }
//        interestRateValueDto.setCustomerAlias(customerAlias);
//        
//        // save
//        interestRateValueService.doEdit(interestRateValueDto, locale, request);
//        
//        // set list interest rate value
//        for (InterestRateSetupDto dto : lstTitleSetup) {
//            // list value
//            interestRateValueDto.setLanguageCode(dto.getLanguageDto().getCode().toUpperCase());
//            List<InterestRateValueDto> lstValues = interestRateValueService
//                    .findInterestRateValueByDto(interestRateValueDto, locale);
//            if (CollectionUtils.isNotEmpty(lstValues) && CollectionUtils.isNotEmpty(dto.getLstTitleDto())) {
//                for (InterestRateValueDto value : lstValues) {
//                    for (int i = value.getLstValues().size() - 1; i > dto.getLstTitleDto().size() - 1; i--) {
//                        value.getLstValues().remove(i);
//                    }
//                }
//            }
//            // list setup
//            dto.setLstValueDto(lstValues);
//        }
//        String interestRateType = interestRateValueDto.getInterestRateType();
//        String msgInfo = msg.getMessage("message.success.label",
//                new String[] { interestRateValueDto.getButtonAction() }, locale);
//        messageList.add(msgInfo);
//        // success redirect edit page
//        interestRateValueDto.setInterestRateType(customerAlias);
//        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(customerAlias).concat(UrlConst.ROOT)
//                .concat(UrlConst.INTEREST_RATE_VALUE).concat(UrlConst.EDIT);
//        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
//        viewName += "?interestRateType=" + interestRateType;
//        mav.setViewName(viewName);
//        return mav;
//    }

    @RequestMapping(value = UrlConst.DELETE, method = { RequestMethod.POST })
	public ModelAndView doDeleteValue(@PathVariable(value = "customerAlias") String customerAlias,
			@ModelAttribute(value = "interestRateValueDto") InterestRateValueDto interestRateValueDto,
			@RequestParam(value = "lstId[]", required = false) List<Long> lstId, Locale locale,
			RedirectAttributes redirectAttributes) {
        
        /*---- taitm - phân quyền theo menu -------*/
        if (!hasRoleEdit()) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        /*---End---*/
        
        ModelAndView mav = new ModelAndView(INTEREST_RATE_VALUE_EDIT);
        
        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
        try {
            mav.addObject("customerAlias", customerAlias);
            
            interestRateValueService.deleteByLstId(lstId);
        } catch (Exception e) {
            logger.error("__doDeleteValue__", e);
            messageList.setStatus(Message.ERROR);
            msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_DELETE, null, locale);
        }
        messageList.add(msgInfo);
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(customerAlias).concat(UrlConst.ROOT)
                .concat(UrlConst.INTEREST_RATE_VALUE).concat(UrlConst.EDIT);
        
        redirectAttributes.addAttribute("interestRateType", interestRateValueDto.getInterestRateType());
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        
        mav.setViewName(viewName);
        
        return mav;
    }

//    private List<LanguageDto> setInfoCommonInterestRateValue(ModelAndView mav) {
//        List<LanguageDto> languageList = languageService.getLanguageDtoList();
//        return languageList;
//    }
//    
//    private boolean hasRoleAccess(){
//        // Security for this page.
//        if (!UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_INTEREST_RATE_VALUE_EDIT)) {
//            return false;
//        }
//        
//        return true;
//    }
    
    private boolean hasRoleEdit(){
        // Security for this page.
        if (!UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_INTEREST_RATE_VALUE_EDIT.concat(ConstantCore.COLON_EDIT))) {
            return false;
        }
        
        return true;
    }

    @Override
    public String getPermisionItem() {
        return CmsRoleConstant.BUTTON_INTEREST_RATE_VALUE_EDIT;
    }

    @Override
    public DocumentWorkflowCommonService<InterestRateValueDto, InterestRateValueDto> getService() {
        return interestRateValueService;
    }

    @Override
    public String getControllerURL(String customerAlias) {
        return customerAlias + "/" + UrlConst.INTEREST_RATE_VALUE;
    }

    @Override
    public String getBusinessCode(String customerAlias) {
        return CmsCommonConstant.BUSINESS_INTEREST_RATE;
    }

    @Override
    public String viewEdit(String customerAlias) {
        return INTEREST_RATE_VALUE_EDIT;
    }

    @Override
    public void sendEmailAction(InterestRateValueDto editDto, Long buttonId) {

    }

    @Override
    public void sendEmailEdit(InterestRateValueDto editDto, Long userUpdated) {

    }

    @Override
    public String objectEditName() {
        return "interestRateType";
    }

    @Override
    public String firstStepInProcess(String customerAlias) {
        return "submit";
    }

    @Override
    public String roleForAttachment(String customerAlias) {
        return CmsRoleConstant.BUTTON_INTEREST_RATE_VALUE_EDIT;
    }

    @Override
    public void initDataEdit(ModelAndView mav, String customerAlias, InterestRateValueDto editDto, boolean isDetail,
            Locale locale) {
        inItScreenEdit(mav, editDto, locale);

        List<JcaConstantDto> lstType = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(INTEREST_RATE_TYPE,
                "EN");

        mav.addObject("lstType", lstType);

        if (editDto != null) {
            if (CollectionUtils.isNotEmpty(lstType)) {
                editDto.setInterestRateType(lstType.get(0).getKind());
            }
        }

        List<LanguageDto> languageList = languageService.getLanguageDtoList();
        mav.addObject("languageList", languageList);

        Long customerTypeId;
        if (UrlConst.CORPORATE.equals(customerAlias)) {
            customerTypeId = CORPORATE_TYPE_ID;
        } else {
            customerTypeId = PERSONAL_TYPE_ID;
        }

        // set list interest rate
        List<InterestRateSetupDto> lstTitleSetup = setListInterestRateSetup(editDto, mav, customerTypeId, languageList,
                locale);
        mav.addObject("lstTitleSetup", lstTitleSetup);
    }

    @Override
    public void validate(InterestRateValueDto editDto, BindingResult bindingResult) {

    }
}
