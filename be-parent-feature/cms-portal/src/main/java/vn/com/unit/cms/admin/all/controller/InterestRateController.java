package vn.com.unit.cms.admin.all.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hornetq.utils.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.cms.admin.all.constant.AdminConstant;
import vn.com.unit.cms.admin.all.dto.InterestRateHistoryDto;
import vn.com.unit.cms.admin.all.dto.InterestRateListDto;
import vn.com.unit.cms.admin.all.dto.InterestRateSearchDto;
import vn.com.unit.cms.admin.all.service.CmsFileService;
import vn.com.unit.cms.admin.all.service.InterestRateHistoryService;
import vn.com.unit.cms.admin.all.service.InterestRateService;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.cms.admin.all.jcanary.entity.CityLanguage;
import vn.com.unit.core.service.LanguageService;

/**
 * Interest rate controller
 * @version 01-00
 * @since 03-05-2017
 * @author sonnt
 *
 */
@Controller
//@RequestMapping(value = UrlConst.ROOT + UrlConst.INTEREST_RATE)
public class InterestRateController {
	
	@Autowired
    private MessageSource msg;
	
	@Autowired
	InterestRateHistoryService interestRateHistoryService;
	
	@Autowired
	InterestRateService interestRateService;
	
	@Autowired
    private LanguageService languageService;
	
	@Autowired
    CmsFileService fileService;
	
	@Autowired
	SystemConfig systemConfig;
	
	private static final String INTEREST_LIST = "interestRate.list";

    private static final String INTEREST_EDIT = "interestRate.edit";
    
    private static final String INTEREST_HISTORY = "interestRate.table.history";
    
    @InitBinder
    public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
        request.getSession().setAttribute("formatDate", systemConfig.getConfig(SystemConfig.DATE_PATTERN));
        // The date format to parse or output your dates
        String patternDate = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
        SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
        // Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        // Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);

        binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
    }
    
    @RequestMapping(value = UrlConst.LIST, method = RequestMethod.GET)
	public ModelAndView get(
			@ModelAttribute(value = "interestRateSearchDto") InterestRateSearchDto interestRateSearchDto,
			@RequestParam(value = "cityId", required = false) Long cityId,
			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page,
			Locale locale){
    	ModelAndView modelAndView = new ModelAndView(INTEREST_LIST);
    	InterestRateListDto interestRateListDto = new InterestRateListDto();
    	List<CityLanguage> cityList = interestRateService.getCityList(locale.toString());
    	
    	interestRateService.enrichInterestRateDtoListInfo(interestRateListDto, interestRateSearchDto, cityList, locale);
    	
    	if(interestRateSearchDto.getCityId() == null){
    		interestRateSearchDto.setCityId(cityList.get(0).getmCityId());
    		interestRateListDto.setCityId(cityList.get(0).getmCityId());
    	}
    	else{
    		interestRateListDto.setCityId(interestRateSearchDto.getCityId());
    	}
    	
    	PageWrapper<InterestRateHistoryDto> pageWrapper = interestRateHistoryService.list(interestRateSearchDto.getCityId(), page, locale);
    	
    	List<LanguageDto> languageList = languageService.getLanguageDtoList();

    	modelAndView.addObject("languageList", languageList);
    	modelAndView.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
    	modelAndView.addObject("cityList",interestRateService.getCityList(locale.toString()));
    	modelAndView.addObject("personalCurrencyList",interestRateService.getPersonalCurrencyList());
    	modelAndView.addObject("organisationCurrencyList",interestRateService.getOrganisationCurrencyList());
    	modelAndView.addObject("interestRateList", interestRateListDto);
    	
    	return modelAndView;
    	
    }
    
    @RequestMapping(value = UrlConst.AJAXLIST, method = RequestMethod.POST)
	public ModelAndView ajaxList(
			@ModelAttribute(value = "interestRateSearchDto") InterestRateSearchDto interestRateSearchDto,
			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page,
			Locale locale
			){
		ModelAndView modelAndView = new ModelAndView();
    	InterestRateListDto interestRateListDto = new InterestRateListDto();
    	List<CityLanguage> cityList = interestRateService.getCityList(locale.toString());
    	
    	interestRateService.enrichInterestRateDtoListInfo(interestRateListDto, interestRateSearchDto, cityList, locale);
    		
    	if(interestRateSearchDto.getCityId() == null){
    		interestRateSearchDto.setCityId(cityList.get(0).getmCityId());
    		interestRateListDto.setCityId(cityList.get(0).getmCityId());
    	}
    	else{
    		interestRateListDto.setCityId(interestRateSearchDto.getCityId());
    	}
    	
    	PageWrapper<InterestRateHistoryDto> pageWrapper = interestRateHistoryService.list(interestRateSearchDto.getCityId(), page, locale);
    	
    	modelAndView.setViewName(INTEREST_HISTORY);
    	modelAndView.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
    	modelAndView.addObject("personalCurrencyList",interestRateService.getPersonalCurrencyList());
    	modelAndView.addObject("organisationCurrencyList",interestRateService.getOrganisationCurrencyList());
    	modelAndView.addObject("interestRateList", interestRateListDto);
    	
    	return modelAndView;
	}

    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.GET)
	public ModelAndView getEdit(
			@ModelAttribute(value = "interestRateSearchDto") InterestRateSearchDto interestRateSearchDto,
			Locale locale
			){
		ModelAndView modelAndView = new ModelAndView(INTEREST_EDIT);
		InterestRateListDto interestRateListDto = new InterestRateListDto();
    	
    	List<CityLanguage> cityList = interestRateService.getCityList(locale.toString());
    	
    	interestRateService.enrichInterestRateDtoListInfo(interestRateListDto, interestRateSearchDto, cityList, locale);
    	
    	List<LanguageDto> languageList = languageService.getLanguageDtoList();

    	modelAndView.addObject("languageList", languageList);
    	modelAndView.addObject("interestRateListDto", interestRateListDto);
    	modelAndView.addObject("personalCurrencyList",interestRateService.getPersonalCurrencyList());
    	modelAndView.addObject("organisationCurrencyList",interestRateService.getOrganisationCurrencyList());
    	
		return modelAndView;
	}
    
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.POST)       
    public ModelAndView postEdit(@ModelAttribute(value = "interestRateListDto") InterestRateListDto interestRateListDto,
    		BindingResult bindingResult,
    		RedirectAttributes redirectAttributes,
    		Locale locale
    		){
        
        ModelAndView modelAndView = new ModelAndView(INTEREST_EDIT);
        
        MessageList messageList = new MessageList(Message.SUCCESS);
        
        if (bindingResult.hasErrors()) {
        	// updating or validation failed
        	messageList.setStatus(Message.ERROR);
    	    messageList.add(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
    	    
    	    modelAndView.addObject("cityId", interestRateListDto.getCityId());
    		return modelAndView;
        }
        
        interestRateService.edit(interestRateListDto);
        messageList.add(msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale));
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(UrlConst.INTEREST_RATE).concat(UrlConst.EDIT);
        redirectAttributes.addAttribute("cityId", interestRateListDto.getCityId()); 
        modelAndView.setViewName(viewName);
        
        return modelAndView;
    }
    
    @RequestMapping(value = "/upload-excel", method = RequestMethod.POST)  
	@ResponseBody
    public ModelAndView  uploadExcel(MultipartHttpServletRequest multipartHttpServletRequest,
    		Locale locale,
    		RedirectAttributes redirectAttributes) throws Exception {
    	MessageList messageList = new MessageList(Message.SUCCESS);
    	
		InterestRateListDto interestRateListDto = interestRateService.importFromExcel(multipartHttpServletRequest, locale, messageList, msg);
		
		String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(UrlConst.INTEREST_RATE).concat(UrlConst.EDIT);
        ModelAndView modelAndView = new ModelAndView(viewName);
		
		if(messageList.getStatus().equalsIgnoreCase(Message.SUCCESS)){
			String url = UrlConst.INTEREST_RATE.concat(UrlConst.EDIT);
			interestRateListDto.setUrl(url);
			modelAndView.setViewName(INTEREST_EDIT);
			List<LanguageDto> languageList = languageService.getLanguageDtoList();
			
			interestRateListDto.setInterestRateLanguageDtoList(interestRateService.getLanguageList(interestRateListDto.getInterestId()));

	    	modelAndView.addObject("languageList", languageList);
			modelAndView.addObject(ConstantCore.MSG_LIST, messageList);
	    	modelAndView.addObject("interestRateListDto", interestRateListDto);
	    	modelAndView.addObject("personalCurrencyList",interestRateService.getPersonalCurrencyList());
	    	modelAndView.addObject("organisationCurrencyList",interestRateService.getOrganisationCurrencyList());
			
			return modelAndView;
		}
		
		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
		return modelAndView;
    }
    
    @RequestMapping(value = "/ajaxListHistory", method = { RequestMethod.POST })
	@ResponseBody
	public ModelAndView ajaxListHistory(
			@RequestParam(value = "cityId", required = false) Long cityId,
			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page,
			Locale locale) {
		ModelAndView mav = new ModelAndView("interestRate.history.paging");

		PageWrapper<InterestRateHistoryDto> pageWrapper = interestRateHistoryService.list(cityId, page, locale);
		
		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
		return mav;
	}
    
    @RequestMapping(value = UrlConst.URL_EDITOR_DOWNLOAD, method = RequestMethod.GET)
    public void editorDownload(@RequestParam(required = true, value = "url") String fileUrl, HttpServletRequest request,
            HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String url = AdminConstant.INTEREST_RATE_EDITOR_FOLDER + "/" + fileUrl;
        interestRateService.requestEditorDownload(url, request, response);
    }
    
    @RequestMapping(value = UrlConst.URL_EDITOR_UPLOAD, method = RequestMethod.POST)
    public @ResponseBody String editorUpload(MultipartHttpServletRequest request, HttpServletRequest request2,
            Model model, HttpServletResponse response,
            @RequestParam(required = true, value = "requesttoken") String requestToken,
            @RequestParam(required = true, value = "CKEditorFuncNum") String funcNum)
            throws JSONException, IOException {
        String fileName = fileService.uploadTemp(request, request2, model, response, AdminConstant.INTEREST_RATE_EDITOR_FOLDER,requestToken);
        String fileUrl = URLEncoder.encode(fileName, "UTF-8");
        String downloadUrl = request2.getContextPath().concat(UrlConst.ROOT).concat(UrlConst.INTEREST_RATE)
                .concat(UrlConst.URL_EDITOR_DOWNLOAD).concat("?url=").concat(fileUrl);
        return "<script> " + "window.parent.CKEDITOR.tools.callFunction(" + funcNum + ", \"" + downloadUrl + "\");"
                + "</script>";
    }
}
