
package vn.com.unit.cms.admin.all.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
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
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.cms.admin.all.dto.InterestRateTitleDto;
import vn.com.unit.cms.admin.all.service.InterestRateTitleService;
import vn.com.unit.cms.admin.all.service.InterestRateValueService;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.core.security.UserProfileUtils;
//import vn.com.unit.jcanary.config.SystemConfig;
//import vn.com.unit.jcanary.constant.RoleConstant;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.LanguageDto;
//import vn.com.unit.jcanary.entity.ConstantDisplay;
//import vn.com.unit.jcanary.service.ConstantDisplayService;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.core.service.LanguageService;

@Controller
@RequestMapping(value = "/{customerAlias}/" + UrlConst.INTEREST_RATE_TITLE)
public class InterestRateTitleController {

    @Autowired
    InterestRateTitleService interestRatetitleService;
    
    @Autowired
    InterestRateValueService interestRateValueService;

//    @Autowired
//    ConstantDisplayService constantDisplayService;

    @Autowired
    LanguageService languageService;

    @Autowired
    MessageSource msg;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
	private JcaConstantService jcaConstantService;

    private static final Logger logger = LoggerFactory.getLogger(InterestRateTitleController.class);

    private static final String INTEREST_RATE_TITLE_EDIT = "interest.rate.title.edit";

    private final long CORPORATE_TYPE_ID = 10l;

    private final long PERSONAL_TYPE_ID = 9l;

    @InitBinder
    public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
        request.getSession().setAttribute("formatDate", "dd-MMM-yyyy");
        // The date format to parse or output your dates
        String patternDate = "dd-MMM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
        // Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        // Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);

        binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));

        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        CustomNumberEditor customNumberEditor = new CustomNumberEditor(BigDecimal.class, numberFormat, true);
        binder.registerCustomEditor(BigDecimal.class, customNumberEditor);
    }

    @RequestMapping(value = "/edit", method = { RequestMethod.GET })
    public ModelAndView editInterestRateTitleDto(@PathVariable(value = "customerAlias") String customerAlias,
            @ModelAttribute(value = "interestRateTitleDto") InterestRateTitleDto interestRateTitleDto, Locale locale) {
        ModelAndView mav = new ModelAndView(INTEREST_RATE_TITLE_EDIT);
        mav.addObject("customerAlias", customerAlias);
        try {
            Long customerTypeId;
            if (UrlConst.CORPORATE.equals(customerAlias)) {
                customerTypeId = CORPORATE_TYPE_ID;
            } else {
                /*---- taitm - phân quyền theo menu -------*/
                if (!hasRoleAccess()) {
                    return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
                }
                /*---End---*/
                
                customerTypeId = PERSONAL_TYPE_ID;
            }
            
            List<LanguageDto> languageList = languageService.getLanguageDtoList();
            mav.addObject("languageList", languageList);
            
            
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

//            List<ConstantDisplay> lstType = constantDisplayService.findByType("M14");

            List<JcaConstantDto> lstType = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang("M14", "EN");
            
            mav.addObject("lstType", lstType);

            if(interestRateTitleDto.getInterestRateType() == null){
                interestRateTitleDto.setInterestRateType(lstType.get(0).getKind());
            }
            setListTitleEdit(interestRateTitleDto, mav, customerTypeId, languageList);
            
            Integer totalTitle = interestRateValueService.countTotalTitleHaveValue(interestRateTitleDto.getInterestRateType());
            mav.addObject("totalTitle", totalTitle);
        } catch (Exception e) {
            logger.error("__editInterestRateTitleDto__", e);
        }
        return mav;
    }

    private void setListTitleEdit(InterestRateTitleDto interestRateTitleDto, ModelAndView mav, Long customerTypeId,
            List<LanguageDto> languageList) {
        List<InterestRateTitleDto> lsTitleDtos = interestRatetitleService.findInterestRateTitleByType(interestRateTitleDto.getInterestRateType(), customerTypeId);
        if (CollectionUtils.isNotEmpty(lsTitleDtos)) {
            List<List<InterestRateTitleDto>> lstDtoEdit = new ArrayList<>();
            int i = 0, sz = lsTitleDtos.size(), szlg = languageList.size();
            while (i < sz) {
                List<InterestRateTitleDto> lstTmp = new ArrayList<>();
                for (int j = 0; j < szlg; j++) {
                    lstTmp.add(lsTitleDtos.get(i++));
                }
                lstDtoEdit.add(lstTmp);
            }
            mav.addObject("lstDtoEdit", lstDtoEdit);
        }
    }

    @RequestMapping(value = "/doSave", method = { RequestMethod.POST })
    public ModelAndView saveInterestRateTitleDto(@PathVariable(value = "customerAlias") String customerAlias,
            @ModelAttribute(name = "interestRateTitleDto") InterestRateTitleDto interestRateTitleDto,
            Locale locale) {
        
        /*---- taitm - phân quyền theo menu -------*/
        if (!hasRoleEdit()) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        /*---End---*/
        
        ModelAndView mav = new ModelAndView(INTEREST_RATE_TITLE_EDIT);
        mav.addObject("customerAlias", customerAlias);
        Long customerTypeId;
        if (UrlConst.CORPORATE.equals(customerAlias)) {
            interestRateTitleDto.setCustomerTypeId(CORPORATE_TYPE_ID);
            customerTypeId = CORPORATE_TYPE_ID;
        } else {
            interestRateTitleDto.setCustomerTypeId(PERSONAL_TYPE_ID);
            customerTypeId = PERSONAL_TYPE_ID;
        }
        
        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
        try {
            interestRatetitleService.saveInterestRateTitle(interestRateTitleDto);
            
            List<LanguageDto> languageList = setInfoCommonInterestRateTitle(mav);            
            setListTitleEdit(interestRateTitleDto, mav, customerTypeId, languageList);
            
            Integer totalTitle = interestRateValueService.countTotalTitleHaveValue(interestRateTitleDto.getInterestRateType());
            mav.addObject("totalTitle", totalTitle);
        } catch (Exception e) {
            logger.error("__saveInterestRateTitleDto__", e);
            messageList.setStatus(Message.ERROR);
            msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
        }
        messageList.add(msgInfo);
        mav.addObject(ConstantCore.MSG_LIST, messageList);

        return mav;
    }

    @RequestMapping(value = UrlConst.DELETE, method = { RequestMethod.POST })
    public ModelAndView doDeleteTitle(@PathVariable(value = "customerAlias") String customerAlias,
            @ModelAttribute(value = "interestRateTitleDto") InterestRateTitleDto interestRateTitleDto,
            @RequestParam(value = "lstId[]", required = false) List<Long> lstId, Locale locale) {
        
        /*---- taitm - phân quyền theo menu -------*/
        if (!hasRoleEdit()) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        /*---End---*/
        
        ModelAndView mav = new ModelAndView(INTEREST_RATE_TITLE_EDIT);      
        
        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
        try {
            mav.addObject("customerAlias", customerAlias);
            Long customerTypeId;
            if (UrlConst.CORPORATE.equals(customerAlias)) {
                customerTypeId = CORPORATE_TYPE_ID;
            } else {
                customerTypeId = PERSONAL_TYPE_ID;
            }
            
            interestRatetitleService.deleteByListId(lstId);
            
            List<LanguageDto> languageList = setInfoCommonInterestRateTitle(mav);            
            setListTitleEdit(interestRateTitleDto, mav, customerTypeId, languageList);
            
            Integer totalTitle = interestRateValueService.countTotalTitleHaveValue(interestRateTitleDto.getInterestRateType());
            mav.addObject("totalTitle", totalTitle);
        } catch (Exception e) {
            logger.error("__doDeleteTitle__", e);
            messageList.setStatus(Message.ERROR);
            msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_DELETE, null, locale);
        }
        messageList.add(msgInfo);
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        return mav;
    }

    private List<LanguageDto> setInfoCommonInterestRateTitle(ModelAndView mav) {
        List<LanguageDto> languageList = languageService.getLanguageDtoList();
        mav.addObject("languageList", languageList);


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

//        List<ConstantDisplay> lstType = constantDisplayService.findByType("M14");
        
        List<JcaConstantDto> lstType = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang("M14", "EN");
        
        mav.addObject("lstType", lstType);
        return languageList;
    }
    
    private boolean hasRoleAccess(){
        // Security for this page.
        if (!UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_INTEREST_RATE_TITLE)) {
            return false;
        }
        
        return true;
    }
    
    private boolean hasRoleEdit(){
        // Security for this page.
        if (!UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_INTEREST_RATE_TITLE.concat(ConstantCore.COLON_EDIT))) {
            return false;
        }
        
        return true;
    }

}
