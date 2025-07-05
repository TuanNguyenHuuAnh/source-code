/*******************************************************************************
 * Class        BranchController
 * Created date 2017/03/13
 * Lasted date  2017/03/13
 * Author       TranLTH
 * Change log   2017/03/1301-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.controller;

import java.text.ParseException;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.cms.admin.all.jcanary.dto.BranchDto;
import vn.com.unit.cms.admin.all.jcanary.dto.BranchSearchDto;
import vn.com.unit.cms.admin.all.jcanary.enumdef.BranchSearchEnum;
import vn.com.unit.cms.admin.all.jcanary.service.BranchService;
import vn.com.unit.cms.admin.all.jcanary.validator.BranchValidator;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.exception.SystemException;
//import vn.com.unit.constant.ConstantCore;
//import vn.com.unit.constant.Message;
//import vn.com.unit.constant.MessageList;
import vn.com.unit.core.security.UserProfileUtils;
//import vn.com.unit.exception.SystemException;
//import vn.com.unit.jcanary.authentication.UserProfileUtils;
//import vn.com.unit.jcanary.common.PageWrapper;
//import vn.com.unit.jcanary.constant.RoleConstant;
//import vn.com.unit.jcanary.constant.UrlConst;
//import vn.com.unit.jcanary.constant.ViewConstant;
//import vn.com.unit.jcanary.dto.BranchDto;
//import vn.com.unit.jcanary.dto.BranchSearchDto;
//import vn.com.unit.jcanary.enumdef.BranchSearchEnum;
//import vn.com.unit.jcanary.service.BranchService;
//import vn.com.unit.jcanary.service.ProcessService;
//import vn.com.unit.jcanary.service.SystemLogsService;
//import vn.com.unit.jcanary.utils.SearchUtil;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
//import vn.com.unit.jcanary.validator.BranchValidator;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.utils.SearchUtil;

/**
 * BranchController
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Controller
@RequestMapping("/jcanary"+UrlConst.BRANCH)
public class BranchController {

    @Autowired
    private BranchService branchService;    
    
    @Autowired
    private BranchValidator branchValidator;
    
    @Autowired
    private MessageSource msg;
    
//    @Autowired
//    ProcessService processService;

    @Autowired
    private SystemLogsService systemLogsService;
    
    private static final String SCREEN_FUNCTION_CODE = CmsRoleConstant.PAGE_LIST_BRANCH;
    
    /**
     * getEdit
     *
     * @param id
     * @return
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.GET)
    public ModelAndView getEdit(
        @RequestParam(value = "id", required = false) Long id) {
        ModelAndView mav = new ModelAndView("views/CMS/all/branch/branch-edit.html"); // "branch.edit"
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE) 
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // End security for this page.
                                                
        BranchDto branchDto = branchService.getBranchDto(id);
        
        String url = UrlConst.BRANCH.concat(UrlConst.EDIT);
        if (null != id) {
            url = url.concat("?id=").concat(id.toString());
        }
        branchDto.setUrl(url);
        
        mav.addObject("branchDto", branchDto);
        // Init master data          
        branchService.initScreenTypeList(mav);
        return mav;
    }
    /**
     * postEdit
     *
     * @param branchDto
     * @param bindingResult
     * @param locale
     * @param redirectAttributes
     * @return
     * @throws Exception
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.POST)
    public ModelAndView postEdit(@Valid @ModelAttribute(value = "branchDto") BranchDto branchDto,
            BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("views/CMS/all/branch/branch-edit.html"); // "branch.edit"
        
        // Write system logs
        if (null == branchDto.getId()) {
            systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Save Add Branch", "Save Branch(code: " + branchDto.getCode() + ")",
                    request);
        } else {
            systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Save Edit Branch", "Save Branch(code: " + branchDto.getCode() + ")",
                    request);
        }
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE) 
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // Security for this page.
//        if (!UserProfileUtils.hasRole(RoleConstant.BUTTON_BRANCH_EDIT)
//                && !UserProfileUtils.hasRole(RoleConstant.BUTTON_BRANCH_EDIT.concat(ConstantCore.COLON_EDIT))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        // Validate business
        branchValidator.validate(branchDto, bindingResult);
        // Validation
        if (bindingResult.hasErrors()) {
            // Add message error
            messageList.setStatus(Message.ERROR);
            String msgError = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
            messageList.add(msgError);
            
            // Init master data          
            branchService.initScreenTypeList(mav);
            
            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject("branchDto", branchDto);
            return mav;
        }
        
        String msgSuccess = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
        messageList.add(msgSuccess);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        
        // Update item
        branchService.addOrEditBranch(branchDto);
        String viewName = UrlConst.REDIRECT.concat(UrlConst.BRANCH)
                                           .concat(UrlConst.EDIT);
        mav.setViewName(viewName);
        redirectAttributes.addAttribute("id", branchDto.getId());
        
        return mav;
    }
    /**
     * postDelete
     *
     * @param branchId
     * @param locale
     * @param redirectAttributes
     * @return
     * @throws Exception
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.DELETE, method = RequestMethod.POST)
    public ModelAndView postDelete(
            @RequestParam(value = "branchId", required = true) Long branchId, Locale locale,
            RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception {
        
    	 if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE) 
                 && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
             return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
         }
        // Write system logs
        systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Delete Branch", "Delete Branch(id: " + branchId + ")", request);
        
        // Security for this page.
//        if (!UserProfileUtils.hasRole(RoleConstant.BUTTON_BRANCH_DELETE)
//                && !UserProfileUtils.hasRole(RoleConstant.BUTTON_BRANCH_DELETE.concat(ConstantCore.COLON_EDIT))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
        branchService.deleteBranch(branchId);           
       //Init message list
       MessageList messageList = new MessageList(Message.SUCCESS);
       String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
       messageList.add(msgInfo);
       redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
       
        // Delete success redirect list page
        String viewName = UrlConst.REDIRECT.concat(UrlConst.BRANCH).concat(UrlConst.LIST);
        ModelAndView mav = new ModelAndView(viewName);               
        return mav;
    }
    
    /**
     * postList
     *
     * @param branchDto
     * @param page
     * @param locale
     * @return
     * @throws ParseException
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getPostList(@ModelAttribute(value = "branchSearch") BranchSearchDto branchSearch,
            @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) throws ParseException {
        ModelAndView mav = new ModelAndView("views/CMS/all/branch/branch-list.html"); // "branch.list"
        
        branchSearch.setUrl(UrlConst.BRANCH.concat(UrlConst.LIST));
        
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE) 
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // set init search
        SearchUtil.setSearchSelect(BranchSearchEnum.class, mav);

        // Init PageWrapper
        PageWrapper<BranchDto> pageWrapper = branchService.search(page, branchSearch);
        
        if( pageWrapper.getCountAll() == 0 ) {
            // Init message list
            MessageList messageList = new MessageList(Message.INFO);
            String msgInfo = msg.getMessage(ConstantCore.MSG_INFO_SEARCH_NO_DATA, null, locale);
            messageList.add(msgInfo);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
        }   
        String result = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.writeValueAsString(pageWrapper.getData());
        } catch (JsonProcessingException e) {
            throw new SystemException("Error Ajax Upload File: Convert json");
        }
        
        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);          
        mav.addObject("resultList", result);
        mav.addObject("branchSearch", branchSearch);
        return mav;
    }
    /**
     * getDetail
     *
     * @param id
     * @return
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.DETAIL, method = RequestMethod.GET)
    public ModelAndView getDetail(
        @RequestParam(value = "id", required = false) Long id) {
        ModelAndView mav = new ModelAndView("branch.detail");        
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE) 
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        BranchDto branchDto = branchService.getBranchDto(id);
        
        String url = UrlConst.BRANCH.concat(UrlConst.EDIT);
        if (null != id) {
            url = url.concat("?id=").concat(id.toString());
        }
        branchDto.setUrl(url);
        // Init master data          
        branchService.initScreenTypeList(mav);
        mav.addObject("branchDto", branchDto);
       
        return mav;
    }
    /**
     * ajaxList
     *
     * @param branchSearch
     * @param page
     * @param locale
     * @return
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView ajaxList(@ModelAttribute(value = "branchSearch") BranchSearchDto branchSearch
            , @RequestParam(value = "page", required = false, defaultValue = "1") int page, Locale locale)  {
        ModelAndView mav = new ModelAndView("views/CMS/all/branch/branch-table.html"); // "branch.table"
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE) 
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // set init search
        SearchUtil.setSearchSelect(BranchSearchEnum.class, mav);

        // Init PageWrapper
        PageWrapper<BranchDto> pageWrapper = branchService.search(page, branchSearch);
        
        if( pageWrapper.getCountAll() == 0 ) {
            // Init message list
            MessageList messageList = new MessageList(Message.INFO);
            String msgInfo = msg.getMessage(ConstantCore.MSG_INFO_SEARCH_NO_DATA, null, locale);
            messageList.add(msgInfo);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
        }        
        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);         
        mav.addObject("branchSearch", branchSearch);
        return mav;
    }
}