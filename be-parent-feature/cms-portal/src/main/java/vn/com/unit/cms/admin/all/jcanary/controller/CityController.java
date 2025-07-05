/*******************************************************************************
 * Class        CityController
 * Created date 2017/02/17
 * Lasted date  2017/02/17
 * Author       TranLTH
 * Change log   2017/02/1701-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import vn.com.unit.cms.admin.all.jcanary.dto.CityDto;
import vn.com.unit.cms.admin.all.jcanary.dto.CitySearchDto;
import vn.com.unit.cms.admin.all.jcanary.service.CityService;
import vn.com.unit.cms.admin.all.jcanary.validator.CityValidator;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.ReturnObject;
import vn.com.unit.ep2p.admin.enumdef.CitySearchEnum;
import vn.com.unit.ep2p.admin.service.SystemLogsService;

//import vn.com.unit.constant.ConstantCore;
//import vn.com.unit.constant.Message;
//import vn.com.unit.constant.MessageList;
//import vn.com.unit.jcanary.authentication.UserProfileUtils;
//import vn.com.unit.jcanary.common.PageWrapper;
//import vn.com.unit.jcanary.constant.RoleConstant;
//import vn.com.unit.jcanary.constant.UrlConst;
//import vn.com.unit.jcanary.constant.ViewConstant;
//import vn.com.unit.jcanary.dto.CityDto;
//import vn.com.unit.jcanary.dto.CitySearchDto;
//import vn.com.unit.jcanary.dto.ReturnObject;
//import vn.com.unit.jcanary.dto.Select2Dto;
//import vn.com.unit.jcanary.enumdef.CitySearchEnum;
//import vn.com.unit.jcanary.service.CityService;
//import vn.com.unit.jcanary.service.SystemLogsService;
//import vn.com.unit.jcanary.utils.SearchUtil;
//import vn.com.unit.jcanary.validator.CityValidator;

import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.utils.SearchUtil;

/**
 * CityController
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Controller
@RequestMapping(UrlConst.CITY)
public class CityController {
    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    @Autowired
    private CityService cityService;
    
    @Autowired
    private MessageSource msg;
    
    @Autowired
    private CityValidator cityValidator;

    @Autowired
    private SystemLogsService systemLogsService;

    private static final String SCREEN_FUNCTION_CODE = RoleConstant.CITY;
    
    /**
     * getDetail
     *
     * @param id
     * @param locale
     * @return
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.DETAIL, method = RequestMethod.GET)
    public ModelAndView getDetail(@RequestParam(value = "id", required = true) Long id, Locale locale) {
        ModelAndView mav = new ModelAndView("city.detail");        
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        CityDto cityDto = cityService.getCityDto(id);
        // set url ajax
        String url = UrlConst.CITY.concat(UrlConst.EDIT);
        if (null != id) {
            url = url.concat("?id=").concat(id.toString());
        }
        cityDto.setUrl(url);
        
        mav.addObject("cityDto", cityDto);
        // Init master data          
        cityService.initScreenCityList(mav,cityDto, locale.getLanguage());              
        return mav;
    }  
    /**
     * getEdit
     *
     * @param cityId
     * @return ModelAndView
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.GET)
    public ModelAndView getEdit(
    	@ModelAttribute(value = "citySearch") CitySearchDto citySearch,	
        @RequestParam(value = "id", required = false) Long id, Locale locale) {
    	
        ModelAndView mav = new ModelAndView("views/CMS/all/city/city-edit.html"); // "city.edit"
        
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        CityDto cityDto = cityService.getCityDto(id);
        // set url ajax
        String url = UrlConst.CITY.concat(UrlConst.EDIT);
        if (null != id) {
            url = url.concat("?id=").concat(id.toString());
        }
        
        // Init master data
        cityService.initScreenCityList(mav,cityDto, locale.getLanguage());
        
        cityDto.setUrl(url);
        cityDto.setFieldSearch(citySearch.getFieldSearch());
        cityDto.setFieldValues(citySearch.getFieldValues());
        
        mav.addObject("cityDto", cityDto);
        return mav;
    }
    /**
     * postEdit
     *
     * @param cityDto
     * @param bindingResult
     * @param locale
     * @param redirectAttributes
     * @return
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.POST)
    public ModelAndView postEdit(@Valid @ModelAttribute(value = "cityDto") CityDto cityDto, BindingResult bindingResult,
            Locale locale, final RedirectAttributes redirectAttributes, HttpServletRequest request) {
        
        // Write system logs
        if (null == cityDto.getCityId()) {
            systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Save Add City", "Save City(code: " + cityDto.getCityCode() + ")",
                    request);
        } else {
            systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Save Edit City", "Save City(code: " + cityDto.getCityCode() + ")",
                    request);
        }
        
        ModelAndView mav = new ModelAndView("views/CMS/all/city/city-edit.html"); // "city.edit"
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        //Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        cityValidator.validate(cityDto, bindingResult);
        
        // Validation
        if (bindingResult.hasErrors()) {
            // Add message error
            messageList.setStatus(Message.ERROR);
            String msgError = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
            messageList.add(msgError);
            // Init master data          
            cityService.initScreenCityList(mav,cityDto , locale.getLanguage());
            
            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject("cityDto", cityDto);
            return mav;
        }        
        Long id = cityDto.getCityId();
        // Update city
        cityService.addOrEditCity(cityDto);        
        
        String msgInfo = "";        
        if (null != id){
            msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
        }else{
            msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
        }
        messageList.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);

        redirectAttributes.addAttribute("id", cityDto.getCityId());
        redirectAttributes.addAttribute("fieldSearch", cityDto.getFieldSearch());
		redirectAttributes.addAttribute("fieldValues", cityDto.getFieldValues());
        String viewName = UrlConst.REDIRECT.concat(UrlConst.CITY).concat(UrlConst.EDIT);
        mav.setViewName(viewName);  
        
        return mav;
    }
    /**
     * postDelete
     *
     * @param cityId
     * @param locale
     * @param redirectAttributes
     * @return ModelAndView
     * @author TranLTH
     * @throws Exception 
     */
    @RequestMapping(value = UrlConst.DELETE, method = RequestMethod.POST)
    public ModelAndView postDelete(@RequestParam(value = "id", required = true) String id, Locale locale,
            RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception {    
        
        // Write system logs
        systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Delete City", "Delete City(id: " + id + ")", request);
        
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        cityService.deleteCity(id);
             
       //Init message list
       MessageList messageList = new MessageList(Message.SUCCESS);
       String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
       messageList.add(msgInfo);
       redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
       
        // Delete success redirect list page
        String viewName = UrlConst.REDIRECT.concat(UrlConst.CITY).concat(UrlConst.LIST);
        ModelAndView mav = new ModelAndView(viewName);               
        return mav;
    }
    /**
     * getPostList
     *
     * @param citySearch
     * @param page
     * @param locale
     * @return
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getPostList(@ModelAttribute(value = "citySearch") CitySearchDto citySearch
            , @RequestParam(value = "page", required = false, defaultValue = "1") int page, Locale locale)  {
        ModelAndView mav = new ModelAndView("views/CMS/all/city/city-list.html"); // "city.list"
        // set url ajax
        citySearch.setUrl(UrlConst.CITY.concat(UrlConst.LIST));
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // set init search
        SearchUtil.setSearchSelect(CitySearchEnum.class, mav);

        // set language code
        citySearch.setLanguageCode(locale.getLanguage().toUpperCase());
        
        PageWrapper<CityDto> pageWrapper = cityService.search(page, citySearch);
        if( pageWrapper.getCountAll() == 0 ) {
            // Init message list
            MessageList messageList = new MessageList(Message.INFO);
            String msgInfo = msg.getMessage(ConstantCore.MSG_INFO_SEARCH_NO_DATA, null, locale);
            messageList.add(msgInfo);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
        } 
        
        mav.addObject("pageWrapper", pageWrapper);
        mav.addObject("citySearch", citySearch);
        return mav;
    } 
    /**
     * ajaxList
     *
     * @param citySearch
     * @param page
     * @param locale
     * @return
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView ajaxList(@ModelAttribute(value = "citySearch") CitySearchDto citySearch
            , @RequestParam(value = "page", required = false, defaultValue = "1") int page, Locale locale)  {
        ModelAndView mav = new ModelAndView("views/CMS/all/city/city-table.html"); // "city.table"
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // set init search
        SearchUtil.setSearchSelect(CitySearchEnum.class, mav);

        // set language code
        citySearch.setLanguageCode(locale.getLanguage());
        
        PageWrapper<CityDto> pageWrapper = cityService.search(page, citySearch);
        if( pageWrapper.getCountAll() == 0 ) {
            // Init message list
            MessageList messageList = new MessageList(Message.INFO);
            String msgInfo = msg.getMessage(ConstantCore.MSG_INFO_SEARCH_NO_DATA, null, locale);
            messageList.add(msgInfo);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
        } 
        
        mav.addObject("pageWrapper", pageWrapper);
        mav.addObject("citySearch", citySearch);
        return mav;
    }
    
    @RequestMapping(value = "/getCitySelect", method = { RequestMethod.POST })
    @ResponseBody
    public ReturnObject getCitySelect(@ModelAttribute(value = "citySearch") CitySearchDto citySearch
            , @RequestParam(value = "regionId", required = true) String regionId, Locale locale)  {
        ReturnObject returnObject = new ReturnObject();
        try {
            String[] region = StringUtils.split(regionId, "@");
            List<String> ctypes = new ArrayList<String>(Arrays.asList("1","2"));
            List<Select2Dto> lstCity =  cityService.findAllCityByRegionId(locale.getLanguage(), Long.valueOf(region[1]),ctypes);
            returnObject.setRetObj(lstCity);
            returnObject.setMessageSuccess(Message.SUCCESS);
        }catch(Exception e) {
            logger.error("##getCitySelect##", e);
            returnObject.setMessageError(Message.ERROR);
        }
        return returnObject;
    }
}
