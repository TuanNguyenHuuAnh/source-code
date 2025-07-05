/*******************************************************************************
 * Class        RoleController
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
//import org.kie.api.runtime.KieSession;
//import org.kie.api.runtime.process.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.SearchKeyDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaRoleDto;
import vn.com.unit.core.dto.JcaRoleSearchDto;
import vn.com.unit.core.entity.JcaRole;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.JcaRoleAddDto;
import vn.com.unit.ep2p.admin.dto.RoleEditDto;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.RoleService;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.admin.validators.RoleAddValidator;
import vn.com.unit.ep2p.admin.validators.RoleEditValidator;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.dto.CommonSearchDto;
import vn.com.unit.ep2p.utils.ConditionSearchUtils;

/**
 * RoleController
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Controller
@RequestMapping(UrlConst.ROLE)
public class RoleController {

    /**
     * 
     */
    private static final String ROLE = "role";

    /**
     * 
     */
    private static final String VCCB_ADMIN_ROLE_DETAIL = "/views/role/role-detail.html";

    /**
     * 
     */
    private static final String VCCB_ADMIN_ROLE_TABLE = "/views/role/role-table.html";

    /** roleService */
    @Autowired
    private RoleService roleService;

    /** MessageSource */
    @Autowired
    private MessageSource msg;

    /** RoleAddValidator */
    @Autowired
    private RoleAddValidator roleAddValidator;

    /** RoleEditValidator */
    @Autowired
    private RoleEditValidator roleEditValidator;
    
    /** systemLogsService */
    @Autowired
    private SystemLogsService systemLogsService;
    
    @Autowired
    private CompanyService companyService;
    
    private final String VIEW_ROLE_ADD = "/views/role/role-add.html";
    private final String VIEW_ROLE_EDIT = "/views/role/role-edit.html";
    private final String VIEW_ROLE_LIST = "/views/role/role-list.html";
    
//    private static final String ROLETP = "ROLETP";
    
    @Autowired
    private SystemConfig systemConfig;
    
    /**
     * Screen list role (Init)
     * 
     * @return ModelAndView
     * @author KhoaNA
     * @throws DetailException 
     */
    @RequestMapping(value = UrlConst.LIST, method = RequestMethod.GET)
    public ModelAndView getList(@ModelAttribute(value = "searchDto") CommonSearchDto searchDto,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, HttpServletRequest request,
            Locale locale, Model model) throws DetailException {

        // Security for this page.
        if (!UserProfileUtils.hasRole(RoleConstant.ROLE) 
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ModelAndView mav = new ModelAndView(VIEW_ROLE_LIST);
        
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        
        searchDto.setCompanyId(UserProfileUtils.getCompanyId());
        
        // If redirect page
        Map<String, Object> md = model.asMap();
        MessageList messageList = (MessageList) md.get(ConstantCore.MSG_LIST);
        mav.addObject(ConstantCore.MSG_LIST, messageList);

        // Init JcaRoleSearchDto
        JcaRoleSearchDto roleDto = new JcaRoleSearchDto();
        mav.addObject("roleDto", roleDto);

        // Init master data
        roleService.initScreenRoleList(mav);

        // Init PageWrapper
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        int page = pageParam.orElse(1);
        
        // Session Search
        ConditionSearchUtils<CommonSearchDto> searchUtil = new ConditionSearchUtils<CommonSearchDto>();
        String[] urlContains = new String[] { "role/add", "role/edit", "role/detail", "role/list" };
        searchDto = searchUtil.getConditionSearch(this.getClass(), searchDto, urlContains, request, page, pageSize);
        pageSize = Optional.ofNullable(searchDto.getPageSize()).orElse(pageSize);
        page = Optional.ofNullable(searchDto.getPage()).orElse(page);
        
        PageWrapper<JcaRoleDto> pageWrapper = roleService.search(searchDto, page, pageSize);
        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
        
        //searchDto = new CommonSearchDto();
        List<SearchKeyDto> searchKeyList = roleService.genSearchKeyList(locale);
        
        String url = ROLE.concat(UrlConst.LIST);
        
        if (StringUtils.isNoneBlank(searchDto.getSearchKeyIds())) {
            List<String> list = new ArrayList<>();
            CollectionUtils.addAll(list, searchDto.getSearchKeyIds().split(","));
            searchDto.setListSearchKeyIds(list);
        }
        
        mav.addObject("searchKeyList", searchKeyList);
        mav.addObject("searchDto", searchDto);
        mav.addObject("pageUrl", url);
        return mav;
    }
    
    /**
     * Screen list role (Search)
     * 
     * @return ModelAndView
     * @author KhoaNA
     * @throws DetailException 
     */
    @RequestMapping(value = UrlConst.AJAXLIST, method = RequestMethod.POST)
    public ModelAndView postAjaxList(@ModelAttribute(value = "searchDto") CommonSearchDto searchDto,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, HttpServletRequest request,
            Locale locale) throws DetailException {
        ModelAndView mav = new ModelAndView(VCCB_ADMIN_ROLE_TABLE);

        // Security for this page.
        if (!UserProfileUtils.hasRole(RoleConstant.ROLE)
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        // Init master data
        roleService.initScreenRoleList(mav);

        // Init PageWrapper
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        int page = pageParam.orElse(1);
        PageWrapper<JcaRoleDto> pageWrapper = roleService.search(searchDto, page, pageSize);
        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);

        List<SearchKeyDto> searchKeyList = roleService.genSearchKeyList(locale);
        mav.addObject("searchKeyList", searchKeyList);
        mav.addObject("searchDto", searchDto);
        
        // Session Search
        ConditionSearchUtils<CommonSearchDto> searchUtil = new ConditionSearchUtils<CommonSearchDto>();
        searchUtil.setConditionSearch(request, this.getClass(), searchDto, page, pageSize);

        return mav;
    }
    
    /**
     * Screen add role (Init)
     * 
     * @return ModelAndView
     * @author KhoaNA
     */
    @RequestMapping(value = UrlConst.ADD, method = RequestMethod.GET)
    public ModelAndView getCreate(Model model) {
        // Security for this page.
        if (!UserProfileUtils.hasRole(RoleConstant.ROLE) 
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        ModelAndView mav = new ModelAndView(VIEW_ROLE_ADD);

        JcaRoleAddDto roleDto = new JcaRoleAddDto();
        roleDto.setActive(ConstantCore.BOOLEAN_TRUE);
        String url = ROLE.concat(UrlConst.ADD);
        roleDto.setUrl(url);
        roleDto.setCompanyId(UserProfileUtils.getCompanyId());
        mav.addObject("roleDto", roleDto);
        
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        
        return mav;
    }

    /**
     * Screen add role (Add)
     * 
     * @return ModelAndView
     * @author KhoaNA
     */
    @RequestMapping(value = UrlConst.ADD, method = RequestMethod.POST)
    public ModelAndView postAdd(@Valid @ModelAttribute(value = "roleDto") JcaRoleAddDto roleDto,
            BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(VIEW_ROLE_ADD);
        
        // Write system logs
        systemLogsService.writeSystemLogs(RoleConstant.ROLE, "Save Add Role", "Save Role(code: " + roleDto.getCode() + ")",
                request);
        
        // Security for this page.
        if (!UserProfileUtils.hasRole(RoleConstant.ROLE) 
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);

        // Validate business
        roleAddValidator.validate(roleDto, bindingResult);

        // Validation
        if (bindingResult.hasErrors()) {
//            // Add message error
//            messageList.setStatus(Message.ERROR);
//            String msgError = msg.getMessage(ConstantCore.MSG_ERROR_CREATE, null, locale);
//            messageList.add(msgError);
            
            // Add message error
            messageList.setStatus(Message.ERROR);
            String msgError = msg.getMessage(ConstantCore.MSG_ERROR_CREATE, null, locale);
            messageList.add(msgError);
            
            // Add company list
            List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
            mav.addObject("companyList", companyList);
            
            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject("roleDto", roleDto);
            return mav;
        }

        String msgSuccess = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
        messageList.add(msgSuccess);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);

        // Create item
        JcaRole role = roleService.create(roleDto);
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(ROLE).concat(UrlConst.EDIT);
        mav.setViewName(viewName);
        redirectAttributes.addAttribute("id", role.getId());
        return mav;
    }

    /**
     * Screen edit role (Init)
     * 
     * @param id
     * @return ModelAndView
     * @author KhoaNA
     */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.GET)
    public ModelAndView getEdit(@RequestParam(value = "id", required = true) Long id, Model model) {
        ModelAndView mav = new ModelAndView(VIEW_ROLE_EDIT);
        
        // Security for this page.
        if (!UserProfileUtils.hasRole(RoleConstant.ROLE) 
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        // If redirect page
        Map<String, Object> md = model.asMap();
        MessageList messageList = (MessageList) md.get(ConstantCore.MSG_LIST);
        mav.addObject(ConstantCore.MSG_LIST, messageList);

        // Get data role
        RoleEditDto roleDto = roleService.findRoleEditDtoById(id);
        
        // Security for data
//        if (null == roleDto || !UserProfileUtils.hasRoleForCompany(roleDto.getCompanyId())) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
        String url = ROLE.concat(UrlConst.EDIT).concat("?id=").concat(roleDto.getId().toString());
        roleDto.setUrl(url);
        mav.addObject("roleDto", roleDto);
        
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        return mav;
    }

    @RequestMapping(value = UrlConst.DETAIL, method = RequestMethod.GET)
    public ModelAndView getDetail(@RequestParam(value = "id", required = true) Long id, Model model) {
        ModelAndView mav = new ModelAndView(VCCB_ADMIN_ROLE_DETAIL);
        
        // Security for this page.
        if (!UserProfileUtils.hasRole(RoleConstant.ROLE)
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        // If redirect page
        Map<String, Object> md = model.asMap();
        MessageList messageList = (MessageList) md.get(ConstantCore.MSG_LIST);
        mav.addObject(ConstantCore.MSG_LIST, messageList);

        // Get data role
        RoleEditDto roleDto = roleService.findRoleEditDtoById(id);
        
        // Security for data
//        if (null == roleDto || (roleDto.getCompanyId() != null && !UserProfileUtils.hasRoleForCompany(roleDto.getCompanyId()))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
        // Set role type
       // List<ConstantDisplay> listRoleType = constantDisplayService.findByType(ROLETP);
       // roleDto.setListRoleType(listRoleType);
        mav.addObject("roleDto", roleDto);

        return mav;
    }

    /**
     * Screen edit role (Edit)
     * 
     * @return ModelAndView
     * @author KhoaNA
     */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.POST)
    public ModelAndView postEdit(@Valid @ModelAttribute(value = "roleDto") RoleEditDto roleDto,
            BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        
        // Write system logs
        systemLogsService.writeSystemLogs(RoleConstant.ROLE, "Save Edit Role", "Save Role(code: " + roleDto.getCode() + ")",
                request);
        
        // Security for this page.
        if (!UserProfileUtils.hasRole(RoleConstant.ROLE) 
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        ModelAndView mav = new ModelAndView(VIEW_ROLE_EDIT);
        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);

        // Validate business
        roleEditValidator.validate(roleDto, bindingResult);

        // Validation
        if (bindingResult.hasErrors()) {
            // Add message error
            messageList.setStatus(Message.ERROR);
            String msgError = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
            messageList.add(msgError);

            // Add company list
            List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
            mav.addObject("companyList", companyList);
            
            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject("roleDto", roleDto);
            /*List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors ) {
                System.out.println (error.getObjectName() + " - " + error.getRejectedValue());
            }*/
            return mav;
        }

        String msgSuccess = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
        messageList.add(msgSuccess);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);

        // Update item
        roleService.update(roleDto);
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(ROLE).concat(UrlConst.EDIT);
        mav.setViewName(viewName);
        redirectAttributes.addAttribute("id", roleDto.getId());

        return mav;
    }
    
    /**
     * Screen delete role (Delete)
     * 
     * @return ModelAndView
     * @author KhoaNA
     */
    @RequestMapping(value = UrlConst.DELETE, method = RequestMethod.POST)
    public ModelAndView postDelete(@RequestParam(value = "id", required = true) Long id, Locale locale,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {
        // Write system logs
        systemLogsService.writeSystemLogs(RoleConstant.ROLE, "Delete Role", "Delete Role(code: " + id + ")", request);
        // Delete success redirect list page
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(ROLE).concat(UrlConst.LIST);
        ModelAndView mav = new ModelAndView(viewName);
        
        if( roleService.checkRoleUsedByGroup(id) > 0 ) {
         // Init message success list
            MessageList messageLst = new MessageList(Message.ERROR);
            String msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_DELETE, null, locale);
            messageLst.add(msgInfo);
            redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageLst);
            String url = ROLE.concat(UrlConst.LIST);
            mav.addObject("pageUrl", url);
            return mav;
        }
        roleService.deleteRole(id);
        // Init message success list
        MessageList messageLst = new MessageList(Message.SUCCESS);
        String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
        messageLst.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageLst);
        String url = ROLE.concat(UrlConst.LIST);
        mav.addObject("pageUrl", url);
        return mav;
    }
    
    
    /**
     * getCreateRole
     * @param model
     * @return
     * @author HungHT
     */
    @RequestMapping(value = UrlConst.ROLE_ADD, method = RequestMethod.GET)
    public ModelAndView getCreateRole(Model model) {
        // Security for this page.
        if (!UserProfileUtils.hasRole(RoleConstant.ROLE) 
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        ModelAndView mav = new ModelAndView(VIEW_ROLE_ADD);

        JcaRoleAddDto roleDto = new JcaRoleAddDto();
        roleDto.setActive(ConstantCore.BOOLEAN_TRUE);
        String url = ROLE.concat(UrlConst.ROLE_ADD);
        // Set role type
//        List<ConstantDisplay> listRoleType = constantDisplayService.findByType(ROLETP);
//        roleDto.setListRoleType(listRoleType);
        roleDto.setUrl(url);
        mav.addObject("roleDto", roleDto);
        return mav;
    }
    
    /**
     * postAddRole
     * @param roleDto
     * @param bindingResult
     * @param locale
     * @param redirectAttributes
     * @param request
     * @return
     * @author HungHT
     */
    @RequestMapping(value = UrlConst.ROLE_ADD, method = RequestMethod.POST)
    public ModelAndView postAddRole(@Valid @ModelAttribute(value = "roleDto") JcaRoleAddDto roleDto,
            BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(VIEW_ROLE_ADD);
        
        // Write system logs
        systemLogsService.writeSystemLogs(RoleConstant.ROLE, "Save Add Role", "Save Role(code: " + roleDto.getCode() + ")",
                request);
        
        // Security for this page.
        if (!UserProfileUtils.hasRole(RoleConstant.ROLE) 
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);

        // Validate business
        roleAddValidator.validate(roleDto, bindingResult);

        // Validation
        if (bindingResult.hasErrors()) {
            // Add message error
            messageList.setStatus(Message.ERROR);
            String msgError = msg.getMessage(ConstantCore.MSG_ERROR_CREATE, null, locale);
            messageList.add(msgError);

            mav.addObject(ConstantCore.MSG_LIST, messageList);
            // Set role type
//            List<ConstantDisplay> listRoleType = constantDisplayService.findByType(ROLETP);
//            roleDto.setListRoleType(listRoleType);
            mav.addObject("roleDto", roleDto);
            return mav;
        }

        String msgSuccess = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
        messageList.add(msgSuccess);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);

        // Create item
        JcaRole role = roleService.createWithRoleType(roleDto);
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(ROLE).concat(UrlConst.ROLE_EDIT);
        mav.setViewName(viewName);
        redirectAttributes.addAttribute("id", role.getId());

        return mav;
    }
    
    /**
     * getEditRole
     * @param id
     * @param model
     * @return
     * @author HungHT
     */
    @RequestMapping(value = UrlConst.ROLE_EDIT, method = RequestMethod.GET)
    public ModelAndView getEditRole(@RequestParam(value = "id", required = true) Long id, Model model) {
        ModelAndView mav = new ModelAndView(VIEW_ROLE_EDIT);
        
        // Security for this page.
        if (!UserProfileUtils.hasRole(RoleConstant.ROLE) 
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        // If redirect page
        Map<String, Object> md = model.asMap();
        MessageList messageList = (MessageList) md.get(ConstantCore.MSG_LIST);
        mav.addObject(ConstantCore.MSG_LIST, messageList);

        // Get data role
        RoleEditDto roleDto = roleService.findRoleEditDtoById(id);
        String url = ROLE.concat(UrlConst.ROLE_ADD).concat("?id=").concat(roleDto.getId().toString());
        // Set role type
//        List<ConstantDisplay> listRoleType = constantDisplayService.findByType(ROLETP);
//        roleDto.setListRoleType(listRoleType);
        roleDto.setUrl(url);
        mav.addObject("roleDto", roleDto);

        return mav;
    }
    
    /**
     * postEditRole
     * @param roleDto
     * @param bindingResult
     * @param locale
     * @param redirectAttributes
     * @param request
     * @return
     * @author HungHT
     */
    @RequestMapping(value = UrlConst.ROLE_EDIT, method = RequestMethod.POST)
    public ModelAndView postEditRole(@Valid @ModelAttribute(value = "roleDto") RoleEditDto roleDto,
            BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        
        // Write system logs
        systemLogsService.writeSystemLogs(RoleConstant.ROLE, "Save Edit Role", "Save Role(code: " + roleDto.getCode() + ")",
                request);
        
        // Security for this page.
        if (!UserProfileUtils.hasRole(RoleConstant.ROLE) 
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        ModelAndView mav = new ModelAndView(VIEW_ROLE_EDIT);
        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);

        // Validate business
        roleEditValidator.validate(roleDto, bindingResult);

        // Validation
        if (bindingResult.hasErrors()) {
            // Add message error
            messageList.setStatus(Message.ERROR);
            String msgError = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
            messageList.add(msgError);

            mav.addObject(ConstantCore.MSG_LIST, messageList);
            // Set role type
//            List<ConstantDisplay> listRoleType = constantDisplayService.findByType(ROLETP);
//            roleDto.setListRoleType(listRoleType);
            mav.addObject("roleDto", roleDto);
            return mav;
        }

        String msgSuccess = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
        messageList.add(msgSuccess);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);

        // Update item
        roleService.updateWithRoleType(roleDto);
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(ROLE).concat(UrlConst.ROLE_EDIT);
        mav.setViewName(viewName);
        redirectAttributes.addAttribute("id", roleDto.getId());

        return mav;
    }
    
    /**
     * getListRole
     * @param searchDto
     * @param page
     * @param model
     * @param locale
     * @return
     * @author HungHT
     * @throws DetailException 
     */
    @RequestMapping(value = UrlConst.ROLE_LIST, method = RequestMethod.GET)
    public ModelAndView getListRole(@ModelAttribute(value = "searchDto") CommonSearchDto searchDto, @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page,
            Model model, Locale locale) throws DetailException {

        // Security for this page.
        if (!UserProfileUtils.hasRole(RoleConstant.ROLE) 
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ModelAndView mav = new ModelAndView(VIEW_ROLE_LIST);

        // If redirect page
        Map<String, Object> md = model.asMap();
        MessageList messageList = (MessageList) md.get(ConstantCore.MSG_LIST);
        mav.addObject(ConstantCore.MSG_LIST, messageList);

        // Init JcaRoleSearchDto
        JcaRoleSearchDto roleDto = new JcaRoleSearchDto();
        mav.addObject("roleDto", roleDto);

        // Init master data
        roleService.initScreenRoleList(mav);

        // Init PageWrapper
        PageWrapper<JcaRoleDto> pageWrapper = roleService.searchWithRoleType(page, searchDto);
        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
        
        searchDto = new CommonSearchDto();
        List<SearchKeyDto> searchKeyList = roleService.genSearchKeyList(locale);
        
        String url = ROLE.concat(UrlConst.ROLE_LIST);
        
        mav.addObject("searchKeyList", searchKeyList);
        mav.addObject("searchDto", searchDto);
        mav.addObject("pageUrl", url);
        return mav;
    }
    
    /**
     * postAjaxListRole
     * @param searchDto
     * @param page
     * @param locale
     * @return
     * @author HungHT
     * @throws DetailException 
     */
    @RequestMapping(value = UrlConst.ROLE_AJAXLIST, method = RequestMethod.POST)
    public ModelAndView postAjaxListRole(@ModelAttribute(value = "searchDto") CommonSearchDto searchDto,
            @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) throws DetailException {
        ModelAndView mav = new ModelAndView(VCCB_ADMIN_ROLE_TABLE);
        
        // Security for this page.
        if (!UserProfileUtils.hasRole(RoleConstant.ROLE) 
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(RoleConstant.ROLE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        // Set AccountSearchDto
        mav.addObject("accountDto", searchDto);

        // Init master data
        roleService.initScreenRoleList(mav);

        // Init PageWrapper
        PageWrapper<JcaRoleDto> pageWrapper = roleService.searchWithRoleType(page, searchDto);
        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
        
        List<SearchKeyDto> searchKeyList = roleService.genSearchKeyList(locale);
        mav.addObject("searchKeyList", searchKeyList);
        mav.addObject("searchDto", searchDto);

        return mav;
    }
    
    /**
     * postDeleteRole
     * @param id
     * @param locale
     * @param redirectAttributes
     * @param request
     * @return
     * @author HungHT
     */
    @RequestMapping(value = UrlConst.ROLE_DELETE, method = RequestMethod.POST)
    public ModelAndView postDeleteRole(@RequestParam(value = "id", required = true) Long id, Locale locale,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {

        // Write system logs
        systemLogsService.writeSystemLogs(RoleConstant.ROLE, "Delete Role", "Delete Role(code: " + id + ")", request);
        
        roleService.deleteRole(id);

        // Delete success redirect list page
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(ROLE).concat(UrlConst.ROLE_LIST);
        ModelAndView mav = new ModelAndView(viewName);

        // Init message success list
        MessageList messageLst = new MessageList(Message.SUCCESS);
        String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
        messageLst.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageLst);
        String url = ROLE.concat(UrlConst.ROLE_LIST);
        mav.addObject("pageUrl", url);
        return mav;
    }
}
