/*******************************************************************************
 * Class        DistrictController
 * Created date 2017/02/21
 * Lasted date  2017/02/21
 * Author       TranLTH
 * Change log   2017/02/2101-00 TranLTH create a new
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

import vn.com.unit.cms.admin.all.jcanary.dto.DistrictDto;
import vn.com.unit.cms.admin.all.jcanary.dto.DistrictSearchDto;
import vn.com.unit.cms.admin.all.jcanary.enumdef.DistrictSearchEnum;
import vn.com.unit.cms.admin.all.jcanary.service.DistrictService;
import vn.com.unit.cms.admin.all.jcanary.validator.DistrictValidator;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.ReturnObject;
import vn.com.unit.ep2p.admin.service.SystemLogsService;

//import vn.com.unit.constant.ConstantCore;
//import vn.com.unit.constant.Message;
//import vn.com.unit.constant.MessageList;
//import vn.com.unit.jcanary.authentication.UserProfileUtils;
//import vn.com.unit.jcanary.common.PageWrapper;
//import vn.com.unit.jcanary.constant.RoleConstant;
//import vn.com.unit.jcanary.constant.UrlConst;
//import vn.com.unit.jcanary.constant.ViewConstant;
//import vn.com.unit.jcanary.dto.DistrictDto;
//import vn.com.unit.jcanary.dto.DistrictSearchDto;
//import vn.com.unit.jcanary.dto.ReturnObject;
//import vn.com.unit.jcanary.dto.Select2Dto;
//import vn.com.unit.jcanary.enumdef.DistrictSearchEnum;
//import vn.com.unit.jcanary.service.DistrictService;
//import vn.com.unit.jcanary.service.SystemLogsService;
//import vn.com.unit.jcanary.utils.SearchUtil;
//import vn.com.unit.jcanary.validator.DistrictValidator;

import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.utils.SearchUtil;

/**
 * DistrictController
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Controller
@RequestMapping(UrlConst.DISTRICT)
public class DistrictController {    
    private static final Logger logger = LoggerFactory.getLogger(DistrictController.class);

    @Autowired
    private DistrictService districtService;    
    
    @Autowired
    private MessageSource msg;
  
    @Autowired
    DistrictValidator districtValidator;
    
    @Autowired
    private SystemLogsService systemLogsService;
    
    private static final String SCREEN_FUNCTION_CODE = RoleConstant.DISTRICT;
    
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
        ModelAndView mav = new ModelAndView("district.detail");        
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        DistrictDto districtDto = districtService.getDistrictDto(id);
        // set url ajax
        String url = UrlConst.DISTRICT.concat(UrlConst.EDIT);
        if (null != id) {
            url = url.concat("?id=").concat(id.toString());
        }
        districtDto.setUrl(url);
        
        mav.addObject("districtDto", districtDto);
        // Init master data          
        districtService.initScreenDistrictList(mav, districtDto, locale.getLanguage());              
        return mav;
    }  
    /**
     * getEdit
     *
     * @param districtId
     * @return ModelAndView
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.GET)
    public ModelAndView getEdit(
    	@ModelAttribute(value = "districtSearch") DistrictSearchDto districtSearch,	
        @RequestParam(value = "id", required = false) Long id, Locale locale) {
        ModelAndView mav = new ModelAndView("views/CMS/all/district/district-edit.html"); // "district.edit"
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        DistrictDto districtDto = districtService.getDistrictDto(id);
        // set url ajax
        String url = UrlConst.DISTRICT.concat(UrlConst.EDIT);
        if (null != id) {
            url = url.concat("?id=").concat(id.toString());
        }
        districtDto.setUrl(url);
        districtDto.setFieldSearch(districtSearch.getFieldSearch());
        districtDto.setFieldValues(districtSearch.getFieldValues());
        
        mav.addObject("districtDto", districtDto);
        
        // Init master data
        districtService.initScreenDistrictList(mav , districtDto, locale.getLanguage());        
        return mav;
    }
    /**
     * postEdit
     *
     * @param districModel
     * @param bindingResult
     * @param locale
     * @param redirectAttributes
     * @param request
     * @return ModelAndView
     * @author TranLTH
     * @throws Exception 
     */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.POST)
    public ModelAndView postEdit(@Valid @ModelAttribute(value = "districtDto") DistrictDto districtDto, 
            BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request)  {
        
        // Write system logs
        if (null == districtDto.getDistrictId()) {
            systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Save Add District",
                    "Save District(code: " + districtDto.getDistrictCode() + ")", request);
        } else {
            systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Save Edit District",
                    "Save District(code: " + districtDto.getDistrictCode() + ")", request);
        }
        
        ModelAndView mav = new ModelAndView("views/CMS/all/district/district-edit.html"); // "district.edit"
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        //Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        districtValidator.validate(districtDto, bindingResult);        
        
        // Validation
        if (bindingResult.hasErrors()) {
            // Add message error
            messageList.setStatus(Message.ERROR);
            String msgError = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
            messageList.add(msgError);
            // Init master data          
            districtService.initScreenDistrictList(mav, districtDto, locale.getLanguage());
            
            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject("districtDto", districtDto);
            return mav;
        }        
        Long id = districtDto.getDistrictId();
        // Update district
        districtService.addOrEditDistrict(districtDto);  
        
        String msgInfo = "";        
        if (null != id){
            msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
        }else{
            msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
        }
        messageList.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        
        String viewName = UrlConst.REDIRECT.concat(UrlConst.DISTRICT).concat(UrlConst.EDIT);
        
        redirectAttributes.addAttribute("fieldSearch", districtDto.getFieldSearch());
		redirectAttributes.addAttribute("fieldValues", districtDto.getFieldValues());
        redirectAttributes.addAttribute("id", districtDto.getDistrictId());
        mav.setViewName(viewName);
                
        return mav;
    }
    /**
     * postDelete
     *
     * @param districtId
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
        systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Delete District", "Delete District(id: " + id + ")", request);
        
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        districtService.deleteDistrict(id);
        
        //Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
        messageList.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        
        // Delete success redirect list page
        String viewName = UrlConst.REDIRECT.concat(UrlConst.DISTRICT).concat(UrlConst.LIST);
        ModelAndView mav = new ModelAndView(viewName);               
        return mav;
    }
    /**
     * getPostList
     *
     * @param districtSearch
     * @param page
     * @param locale
     * @return
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getPostList(@ModelAttribute(value = "districtSearch") DistrictSearchDto districtSearch
            , @RequestParam(value = "page", required = false, defaultValue = "1") int page, Locale locale)  {
        ModelAndView mav = new ModelAndView("views/CMS/all/district/district-list.html"); // "district.list"
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // set url ajax
        districtSearch.setUrl(UrlConst.DISTRICT.concat(UrlConst.LIST));
        
        // set init search
        SearchUtil.setSearchSelect(DistrictSearchEnum.class, mav);
        // set language code
        districtSearch.setLanguageCode(locale.getLanguage());
        
        PageWrapper<DistrictDto> pageWrapper = districtService.search(page, districtSearch);
        if( pageWrapper.getCountAll() == 0 ) {
            // Init message list
            MessageList messageList = new MessageList(Message.INFO);
            String msgInfo = msg.getMessage(ConstantCore.MSG_INFO_SEARCH_NO_DATA, null, locale);
            messageList.add(msgInfo);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
        }          
        mav.addObject("pageWrapper", pageWrapper);
        mav.addObject("districtSearch", districtSearch);
        return mav;
    } 
    /**
     * ajaxList
     *
     * @param districtSearch
     * @param page
     * @param locale
     * @return
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView ajaxList(@ModelAttribute(value = "districtSearch") DistrictSearchDto districtSearch
            , @RequestParam(value = "page", required = false, defaultValue = "1") int page, Locale locale)  {
        ModelAndView mav = new ModelAndView("views/CMS/all/district/district-table.html"); // "district.table"
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // set init search
        SearchUtil.setSearchSelect(DistrictSearchEnum.class, mav);
        // set language code
        districtSearch.setLanguageCode(locale.getLanguage());
        
        PageWrapper<DistrictDto> pageWrapper = districtService.search(page, districtSearch);
        if( pageWrapper.getCountAll() == 0 ) {
            // Init message list
            MessageList messageList = new MessageList(Message.INFO);
            String msgInfo = msg.getMessage(ConstantCore.MSG_INFO_SEARCH_NO_DATA, null, locale);
            messageList.add(msgInfo);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
        }          
        mav.addObject("pageWrapper", pageWrapper);
        mav.addObject("districtSearch", districtSearch);
        return mav;
    }
    /** getDistrictSelect
     *
     * @param districtSearch
     * @param cityRegionId
     * @param locale
     * @return
     * @author hangnkm
     */
    @RequestMapping(value = "/getDistrictSelect", method = { RequestMethod.POST })
    @ResponseBody
    public ReturnObject getDistrictSelect(@ModelAttribute(value = "districtSearch") DistrictSearchDto districtSearch
            , @RequestParam(value = "cityRegionId", required = true) String cityRegionId, Locale locale)  {
        ReturnObject returnObject = new ReturnObject();
        try {
            String[] cityRegion = StringUtils.split(cityRegionId, "@");
            List<String> dtypes = new ArrayList<String>(Arrays.asList("4","6","5"));
            List<Select2Dto> lstDistrict =  districtService.findAllDistrictListByCityIdByType(Long.valueOf(cityRegion[0]),locale.getLanguage(),dtypes);
            returnObject.setRetObj(lstDistrict);
            returnObject.setMessageSuccess(Message.SUCCESS);
        }catch(Exception e) {
            logger.error("##getDistrictSelect##", e);
            returnObject.setMessageError(Message.ERROR);
        }
        return returnObject;
    }
}