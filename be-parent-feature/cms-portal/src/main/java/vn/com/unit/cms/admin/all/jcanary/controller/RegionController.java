/*******************************************************************************
 * Class        RegionController
 * Created date 2017/02/14
 * Lasted date  2017/02/14
 * Author       TranLTH
 * Change log   2017/02/1401-00 tranlth create a new
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

import vn.com.unit.cms.admin.all.jcanary.dto.RegionDto;
import vn.com.unit.cms.admin.all.jcanary.dto.RegionSearchDto;
import vn.com.unit.cms.admin.all.jcanary.enumdef.RegionSearchEnum;
import vn.com.unit.cms.admin.all.jcanary.service.CmsRegionService;
import vn.com.unit.cms.admin.all.jcanary.validator.RegionValidator;
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
//import vn.com.unit.jcanary.dto.RegionDto;
//import vn.com.unit.jcanary.dto.RegionSearchDto;
//import vn.com.unit.jcanary.enumdef.RegionSearchEnum;
//import vn.com.unit.jcanary.service.RegionService;
//import vn.com.unit.jcanary.service.SystemLogsService;
//import vn.com.unit.jcanary.utils.SearchUtil;
//import vn.com.unit.jcanary.validator.RegionValidator;

import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.utils.SearchUtil;

/**
 * Region
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Controller
@RequestMapping(UrlConst.REGION)
public class RegionController {

    @Autowired
    private CmsRegionService regionService;    
    
    @Autowired
    private MessageSource msg;

    @Autowired
    RegionValidator regionValidator;
    
    @Autowired
    private SystemLogsService systemLogsService;
    
    /** SCREEN_FUNCTION_CODE */
    private static final String SCREEN_FUNCTION_CODE = RoleConstant.REGION;
    
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
        ModelAndView mav = new ModelAndView("region.detail");        
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        RegionDto regionDto = regionService.getRegionDto(id);
        // set url ajax
        String url = UrlConst.REGION.concat(UrlConst.DETAIL);
        if (null != id) {
            url = url.concat("?id=").concat(id.toString());
        }
        regionDto.setUrl(url);
        
        mav.addObject("regionDto", regionDto);
        // Init master data          
        regionService.initScreenRegionList(mav, locale.getLanguage());              
        return mav;
    }  
    /**
     *  getEdit
     *
     * @param regionModel
     * @param regionId
     * @return ModelAndView
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.GET)
    public ModelAndView getEdit(@RequestParam(value = "id", required = false) Long id, Locale locale,
    		@ModelAttribute(value = "regionSearch") RegionSearchDto regionSearch) {
        ModelAndView mav = new ModelAndView("views/CMS/all/region/region-edit.html"); // "region.edit"
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        RegionDto regionDto = regionService.getRegionDto(id);
        
        // set url ajax
        String url = UrlConst.REGION.concat(UrlConst.DETAIL);
        if (null != id) {
            url = url.concat("?id=").concat(id.toString());
        }
        regionDto.setUrl(url);
        regionDto.setFieldSearch(regionSearch.getFieldSearch());
        regionDto.setFieldValues(regionSearch.getFieldValues());
        
        mav.addObject("regionDto", regionDto);
        // Init master data          
        regionService.initScreenRegionList(mav, locale.getLanguage());              
        return mav;
    }
    /**
     * postEdit
     *
     * @param regionModel
     * @param bindingResult
     * @param locale
     * @param redirectAttributes
     * @param request
     * @return ModelAndView
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.POST)
    public ModelAndView postEdit(@Valid @ModelAttribute(value = "regionDto") RegionDto regionDto, BindingResult bindingResult,
            Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        
        // Write system logs
        if (null == regionDto.getRegionId()) {
            systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Save Add Region",
                    "Save Region(code: " + regionDto.getRegionCode() + ")", request);
        } else {
            systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Save Edit Region",
                    "Save Region(code: " + regionDto.getRegionCode() + ")", request);
        }
        
        ModelAndView mav = new ModelAndView("views/CMS/all/region/region-edit.html"); // "region.edit"
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        //Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        regionValidator.validate(regionDto, bindingResult);        
        
        //Validation
        if (bindingResult.hasErrors()) {
            // Add message error
            messageList.setStatus(Message.ERROR);
            String msgError = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
            messageList.add(msgError);
            // Init master data          
            regionService.initScreenRegionList(mav, locale.getLanguage());
            
            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject("regionDto", regionDto);
            return mav;
        }      
        Long id = regionDto.getRegionId();
        // Update region
        regionService.addOrEditRegion(regionDto);
        
        String msgInfo = "";        
        if (null != id){
            msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
        }else{
            msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
        }
        messageList.add(msgInfo);
        
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        
        String viewName = UrlConst.REDIRECT.concat(UrlConst.REGION).concat(UrlConst.EDIT); 
        redirectAttributes.addAttribute("id", regionDto.getRegionId());
        redirectAttributes.addAttribute("fieldSearch", regionDto.getFieldSearch());
		redirectAttributes.addAttribute("fieldValues", regionDto.getFieldValues());
        mav.setViewName(viewName);        
        
        return mav;
    }
    /**
     * postDelete
     *
     * @param regionId
     * @param locale
     * @param redirectAttributes
     * @return ModelAndView
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.DELETE, method = RequestMethod.POST)
    public ModelAndView postDelete(
            @RequestParam(value = "regionId", required = true) String regionId, Locale locale,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {
        
        // Write system logs
        systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Delete Region", "Delete Region(id: " + regionId + ")", request);
        
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        regionService.deleteRegion(regionId);
       
       //Init message list
       MessageList messageList = new MessageList(Message.SUCCESS);
       String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
       messageList.add(msgInfo);
       redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
               
        // Delete success redirect list page
        String viewName = UrlConst.REDIRECT.concat(UrlConst.REGION).concat(UrlConst.LIST);
        ModelAndView mav = new ModelAndView(viewName);                                    
        return mav;
    } 
    /**
     * getPostList
     *
     * @param regionSearchDto
     * @param page
     * @param locale
     * @return
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getPostList(@ModelAttribute(value = "regionSearch") RegionSearchDto regionSearch
            , @RequestParam(value = "page", required = false, defaultValue = "1") int page, Locale locale)  {
        ModelAndView mav = new ModelAndView("views/CMS/all/region/region-list.html"); // "region.list"
        // set url ajax
        regionSearch.setUrl(UrlConst.REGION.concat(UrlConst.LIST));
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // set init search
        SearchUtil.setSearchSelect(RegionSearchEnum.class, mav);

        // set language code
        regionSearch.setLanguageCode(locale.getLanguage());
        
        PageWrapper<RegionDto> pageWrapper = regionService.search(page, regionSearch);
        if( pageWrapper.getCountAll() == 0 ) {
            // Init message list
            MessageList messageList = new MessageList(Message.INFO);
            String msgInfo = msg.getMessage(ConstantCore.MSG_INFO_SEARCH_NO_DATA, null, locale);
            messageList.add(msgInfo);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
        }     
        
        mav.addObject("pageWrapper", pageWrapper);
        mav.addObject("regionSearchDto", regionSearch);
        return mav;
    } 
    /**
     * ajaxList
     *
     * @param regionSearch
     * @param page
     * @param locale
     * @return
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView ajaxList(@ModelAttribute(value = "regionSearch") RegionSearchDto regionSearch
            , @RequestParam(value = "page", required = false, defaultValue = "1") int page, Locale locale)  {
        ModelAndView mav = new ModelAndView("views/CMS/all/region/region-table.html"); // "region.table"
        // set language code
        regionSearch.setLanguageCode(locale.getLanguage());
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // Init PageWrapper
        PageWrapper<RegionDto> pageWrapper = regionService.search(page, regionSearch);
        if( pageWrapper.getCountAll() == 0 ) {
            // Init message list
            MessageList messageList = new MessageList(Message.INFO);
            String msgInfo = msg.getMessage(ConstantCore.MSG_INFO_SEARCH_NO_DATA, null, locale);
            messageList.add(msgInfo);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
        }         
        mav.addObject("pageWrapper", pageWrapper);
        mav.addObject("regionSearchDto", regionSearch);
        return mav;
    }
}