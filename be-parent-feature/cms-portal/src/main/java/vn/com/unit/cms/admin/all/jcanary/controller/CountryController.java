/*******************************************************************************
 * Class        CountryController
 * Created date 2017/02/23
 * Lasted date  2017/02/23
 * Author       TranLTH
 * Change log   2017/02/2301-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.cms.admin.all.jcanary.dto.CountryDto;
import vn.com.unit.cms.admin.all.jcanary.dto.CountrySearchDto;
import vn.com.unit.cms.admin.all.jcanary.enumdef.CountrySearchEnum;
import vn.com.unit.cms.admin.all.jcanary.service.CountryService;
import vn.com.unit.cms.admin.all.jcanary.validator.CountryValidator;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.service.SystemLogsService;

//import vn.com.unit.constant.ConstantCore;
//import vn.com.unit.constant.Message;
//import vn.com.unit.constant.MessageList;
//import vn.com.unit.jcanary.authentication.UserProfileUtils;
//import vn.com.unit.jcanary.common.PageWrapper;
//import vn.com.unit.jcanary.constant.RoleConstant;
//import vn.com.unit.jcanary.constant.UrlConst;
//import vn.com.unit.jcanary.constant.ViewConstant;
//import vn.com.unit.jcanary.dto.CountryDto;
//import vn.com.unit.jcanary.dto.CountrySearchDto;
//import vn.com.unit.jcanary.enumdef.CountrySearchEnum;
//import vn.com.unit.jcanary.service.CountryService;
//import vn.com.unit.jcanary.service.SystemLogsService;
//import vn.com.unit.jcanary.utils.SearchUtil;
//import vn.com.unit.jcanary.validator.CountryValidator;

import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.utils.SearchUtil;

/**
 * CountryController
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Controller
@RequestMapping(UrlConst.COUNTRY)
public class CountryController {

    @Autowired
    private CountryService countryService;    
    
    @Autowired
    private MessageSource msg;

    @Autowired
    CountryValidator countryValidator;

    @Autowired
    private SystemLogsService systemLogsService;
    
    /** SCREEN_FUNCTION_CODE */
    private static final String SCREEN_FUNCTION_CODE = RoleConstant.COUNTRY;
    
    /**
     * getCreateCountry
     *
     * @param request
     * @return ModelAndView
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.DETAIL, method = RequestMethod.GET)
    public ModelAndView getDetail(
        @RequestParam(value = "id", required = true) Long id) {
        ModelAndView mav = new ModelAndView("country.detail");        
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        CountryDto countryDto = countryService.getCountryDto(id);
        // set url ajax
        String url = UrlConst.COUNTRY.concat(UrlConst.DETAIL);
        if (null != id) {
            url = url.concat("?id=").concat(id.toString());
        }
        countryDto.setUrl(url);
        
        mav.addObject("countryDto", countryDto);
        // Init master data          
        countryService.initScreenCountryList(mav);              
        return mav;
    }  
    /**
     * getEdit
     *
     * @param id
     * @return ModelAndView
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.GET)    
    public ModelAndView getEdit( @RequestParam(value = "id", required = false) Long id,
    		@ModelAttribute(value = "countrySearch") CountrySearchDto countrySearch) {
        ModelAndView mav = new ModelAndView("views/CMS/all/country/country-edit.html"); // "country.edit"
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        CountryDto countryDto = countryService.getCountryDto(id);
        // set url ajax
        String url = UrlConst.BANNER.concat(UrlConst.EDIT);
        if (null != id) {
            url = url.concat("?id=").concat(id.toString());
        }
        countryDto.setUrl(url);
        countryDto.setFieldSearch(countrySearch.getFieldSearch());
        countryDto.setFieldValues(countrySearch.getFieldValues());
        
        mav.addObject("countryDto", countryDto);
        // Init master data          
        countryService.initScreenCountryList(mav);              
        return mav;
    }
    /**
     * postEdit
     *
     * @param countryDto
     * @param bindingResult
     * @param locale
     * @param redirectAttributes
     * @param request
     * @return ModelAndView
     * @throws Exception
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.POST)       
    public ModelAndView postEdit(@Valid @ModelAttribute(value = "countryDto") CountryDto countryDto, BindingResult bindingResult,
            Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception {
        
        // Write system logs
        if (null == countryDto.getCountryId()) {
            systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Save Add Country", "Save Country(code: " + countryDto.getCountryCode() + ")",
                    request);
        } else {
            systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Save Edit Country", "Save Country(code: " + countryDto.getCountryCode() + ")",
                    request);
        }
        
        ModelAndView mav = new ModelAndView("views/CMS/all/country/country-edit.html"); // "country.edit"
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        //Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        countryValidator.validate(countryDto, bindingResult);
        // Validation
        if (bindingResult.hasErrors()) {
            // Add message error
            messageList.setStatus(Message.ERROR);
            String msgError = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
            messageList.add(msgError);
            // Init master data          
            countryService.initScreenCountryList(mav);
            
            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject("countryDto", countryDto);
            return mav;
        }    
        Long id = countryDto.getCountryId();
        // Update country
        countryService.addOrEditCountry(countryDto);
        
        String msgInfo = "";        
        if (null != id){
            msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
        }else{
            msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
        }
        messageList.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        
        String viewName = UrlConst.REDIRECT.concat(UrlConst.COUNTRY).concat(UrlConst.EDIT);
        redirectAttributes.addAttribute("id", countryDto.getCountryId()); 
        redirectAttributes.addAttribute("fieldSearch", countryDto.getFieldSearch());
		redirectAttributes.addAttribute("fieldValues", countryDto.getFieldValues());
        mav.setViewName(viewName);
               
        return mav;
    }
    /**
     * postDelete
     *
     * @param countryId
     * @param locale
     * @param redirectAttributes
     * @return ModelAndView
     * @throws Exception
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.DELETE, method = RequestMethod.POST)
    public ModelAndView postDelete(
            @RequestParam(value = "id", required = true) Long id, Locale locale,
            RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception {
        
        // Write system logs
        systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Delete Country", "Delete Country(id: " + id + ")", request);
        
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        countryService.deleteCountry(id.toString());
        
        //Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
        messageList.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        
        // Delete success redirect list page
        String viewName = UrlConst.REDIRECT.concat(UrlConst.COUNTRY).concat(UrlConst.LIST);
        ModelAndView mav = new ModelAndView(viewName);               
        return mav;
    }
    /**
     * getPostList
     *
     * @param countrySearchDto
     * @param page
     * @param locale
     * @return
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getPostList(@ModelAttribute(value = "countrySearch") CountrySearchDto countrySearch
            , @RequestParam(value = "page", required = false, defaultValue = "1") int page, Locale locale)  {
        ModelAndView mav = new ModelAndView("views/CMS/all/country/country-list.html"); // "country.list"
        // set url ajax
        countrySearch.setUrl(UrlConst.COUNTRY.concat(UrlConst.LIST));
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // set init search
        SearchUtil.setSearchSelect(CountrySearchEnum.class, mav);

        // set language code
        countrySearch.setLanguageCode(locale.getLanguage());
        
        PageWrapper<CountryDto> pageWrapper = countryService.search(page, countrySearch);
        if( pageWrapper.getCountAll() == 0 ) {
            // Init message list
            MessageList messageList = new MessageList(Message.INFO);
            String msgInfo = msg.getMessage(ConstantCore.MSG_INFO_SEARCH_NO_DATA, null, locale);
            messageList.add(msgInfo);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
        } 
        
        mav.addObject("pageWrapper", pageWrapper);
        mav.addObject("countrySearch", countrySearch);
        return mav;
    } 
    /**
     * ajaxList
     *
     * @param countrySearch
     * @param page
     * @param locale
     * @return
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView ajaxList(@ModelAttribute(value = "countrySearch") CountrySearchDto countrySearch
            , @RequestParam(value = "page", required = false, defaultValue = "1") int page, Locale locale)  {
        ModelAndView mav = new ModelAndView("views/CMS/all/country/country-table.html"); // "country.table"
        // set language code
        countrySearch.setLanguageCode(locale.getLanguage());
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // Init PageWrapper
        PageWrapper<CountryDto> pageWrapper = countryService.search(page, countrySearch);
        if( pageWrapper.getCountAll() == 0 ) {
            // Init message list
            MessageList messageList = new MessageList(Message.INFO);
            String msgInfo = msg.getMessage(ConstantCore.MSG_INFO_SEARCH_NO_DATA, null, locale);
            messageList.add(msgInfo);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
        }         
        mav.addObject("pageWrapper", pageWrapper);
        mav.addObject("countrySearch", countrySearch);
        return mav;
    }
}
