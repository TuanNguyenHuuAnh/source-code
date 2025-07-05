package vn.com.unit.ep2p.admin.controller;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.enumdef.PageMode;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.core.dto.JcaOrganizationDto;
import vn.com.unit.core.entity.JcaOrganization;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.AccountListDto;
import vn.com.unit.ep2p.admin.dto.OrgNode;
import vn.com.unit.ep2p.admin.enumdef.OrgType;
import vn.com.unit.ep2p.admin.service.AccountOrgService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.OrgInfoService;
import vn.com.unit.ep2p.admin.service.OrganizationService;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.admin.validators.OrganizationValidator;
import vn.com.unit.ep2p.constant.UrlConst;

@Controller
@RequestMapping(UrlConst.ORGANIZATION)
public class OrganizationController {
    private static final String VIEW_ORGANIZATION_MAIN = "/views/organization/organization-main.html";
    private static final String VIEW_ORGANIZATION_TREE_DETAIL = "/views/organization/organization-detail.html";
    private static final String VIEW_ORGANIZATION_ACCOUNT_LIST = "/views/organization/account-list.html";

    @Autowired
    private OrganizationService organizationService;
    
    @Autowired
    private MessageSource msg;
    
    /** OrganizationValidator */
    @Autowired
    private OrganizationValidator organizationValidator;
    
    private PageMode orgViewMode = PageMode.VIEW;
    
    @Autowired
    private SystemConfig systemConfig;
    
    /** systemLogsService */
    @Autowired
    private SystemLogsService systemLogsService;
    
    @Autowired
    private AccountOrgService accountOrgService;
    
    @Autowired
    private OrgInfoService orgService;
    
    @Autowired
    private CompanyService companyService;
    
    private static final String SCREEN_FUNCTION_CODE = RoleConstant.ORGANIZATION;
    private static final String ORG_TYPE_SECTION = OrgType.SECTION.toString();
    private static final Long ORG_LEVEL_ROOT = 1L;

    @RequestMapping(value = UrlConst.MAIN, method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView initialOrganization() {
        
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE) 
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        ModelAndView mav = new ModelAndView(VIEW_ORGANIZATION_MAIN);
        return mav;
    }

    @RequestMapping(value = UrlConst.DETAIL, method = RequestMethod.GET)
    @ResponseBody
    public Object buildOrgDetail(@RequestParam(name = "orgId", required = false) long orgId,
            HttpServletRequest request, HttpServletResponse response) throws DetailException {
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE) 
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        JcaOrganizationDto orgDto = organizationService.buildOrgModel(orgId, true);
        ModelAndView mav = new ModelAndView(VIEW_ORGANIZATION_TREE_DETAIL);
        orgDto.setMode(PageMode.VIEW);
        orgViewMode = PageMode.VIEW;
        Boolean delete = ORG_LEVEL_ROOT == orgDto.getOrgId() ||(null == orgDto.getCompanyId() && !UserProfileUtils.isCompanyAdmin())? false : true;
        List<JcaOrganizationDto> count = organizationService.getJcaOrganizationDtoChildByParentIdAndDepth(orgId, 1L);
        if(count != null && !count.isEmpty()) {
            delete = false;
        }
        Boolean create = ORG_TYPE_SECTION.equals(orgDto.getOrgType()) ||(null == orgDto.getCompanyId() && !UserProfileUtils.isCompanyAdmin())? false : true;
        Boolean edit = (null == orgDto.getCompanyId() && !UserProfileUtils.isCompanyAdmin())? false : true;
        mav.addObject("create", create);
        mav.addObject("delete", delete);
        mav.addObject("edit", edit);
        mav.addObject("orgViewMode", orgViewMode);
        mav.addObject("orgDto", orgDto);
        return mav;
    }

    // --------- Controller for Organization
    // --------------------------------------------------------------------------

    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView editOrg(@RequestParam(required = true) long orgId, HttpServletRequest request,
            HttpServletResponse response) {
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE) 
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        JcaOrganizationDto orgDto = organizationService.buildOrgModel(orgId, true);
        ModelAndView mav = new ModelAndView(VIEW_ORGANIZATION_TREE_DETAIL);
        orgDto.setMode(PageMode.EDIT);
        orgViewMode = PageMode.EDIT;
        mav.addObject("orgViewMode", PageMode.EDIT);
        mav.addObject("orgDto", orgDto);
        return mav;
    }

    @RequestMapping(value = UrlConst.UPDATE, method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody       
    public ModelAndView saveOrg(@Valid @ModelAttribute(value = "orgDto") JcaOrganizationDto orgDto, BindingResult bindingResult,
            Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView(VIEW_ORGANIZATION_TREE_DETAIL);
        // Write system logs
        systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Update Organization", "Update Organization", request);
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE) 
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        //Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        organizationValidator.validate(orgDto, bindingResult);
        // Validation
        if (bindingResult.hasErrors()) {
            // Add message error
            messageList.setStatus(Message.ERROR);
            String msgError = null;
            if(orgDto.getMode()==PageMode.CREATE) {
                msgError = msg.getMessage(ConstantCore.MSG_ERROR_CREATE, null, locale);
            }else {
                msgError = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
            }
            messageList.add(msgError);
            
            mav.addObject(ConstantCore.MSG_LIST, messageList);
//            Boolean editable = orgDto.getMode() == PageMode.EDIT ? true : false;
//            JcaOrganizationDto organizationDto = organizationService.buildOrgModel(orgDto.getId(), editable);
//            orgDto.setListOrgType(organizationDto.getListOrgType());
            //orgDto.setListOrgSubType1(organizationService.listDisplaySubOrgType());
            orgDto.setMode(PageMode.CREATE);
            mav.addObject("orgViewMode", orgViewMode);
            mav.addObject("orgDto", orgDto);
           
            return mav;
        }   
        orgDto.setDisplayOrder(0);
        JcaOrganization jcaOrganization = organizationService.saveJcaOrganizationDto(orgDto);
        String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
        
        if (orgDto.getMode() == PageMode.EDIT){
            msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
        }
        
        messageList.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ORGANIZATION).concat(UrlConst.DETAIL);
        redirectAttributes.addAttribute("orgId", jcaOrganization.getId());
        mav.setViewName(viewName);
               
        return mav;
    }
    
    @RequestMapping(value = UrlConst.ADD, method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView newOrg(@RequestParam(required = true) long orgId, HttpServletRequest request,
            HttpServletResponse response) {
        JcaOrganizationDto orgDto = organizationService.buildOrgModel(orgId, false);

        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE) 
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        ModelAndView mav = new ModelAndView(VIEW_ORGANIZATION_TREE_DETAIL);
        orgDto.setMode(PageMode.CREATE);
        orgDto.setActived(true);
        orgViewMode = PageMode.EDIT;
        mav.addObject("orgViewMode", orgViewMode);
        mav.addObject("orgDto", orgDto);
        
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        
        Long companyIdSelect = UserProfileUtils.getCompanyId();
        mav.addObject("companyIdSelect", companyIdSelect);
        
        return mav;
    }

    @PostMapping(UrlConst.DELETE)
    @ResponseBody
    public MessageList deleteOrg(@RequestParam(required = true) long orgId, HttpServletRequest request,
            HttpServletResponse response, Locale locale) {
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE) 
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return null;
        }
        // Write system logs
        systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Delete Organization", "Delete Organization(id: " + orgId + ")", request);
        //Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        try {
            organizationService.deleteOrg(orgId);
        }catch (Exception e) {
            // Add message error
            messageList.setStatus(Message.ERROR);
            String msgError = msg.getMessage(ConstantCore.MSG_ERROR_DELETE, null, locale);
            messageList.add(msgError);
        }
        String msgSuccess = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
        messageList.add(msgSuccess);
        
        return messageList;
    }
    
    @RequestMapping(value = UrlConst.AJAXLIST, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView postListAccount(@RequestParam(value = "orgId") Long orgId,
            @RequestParam(value = ConstantCore.PAGE_SIZE, required = false) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE, required = false) Optional<Integer> pageParam, Locale locale) {
        ModelAndView mav = new ModelAndView(VIEW_ORGANIZATION_ACCOUNT_LIST);

        // Init PageWrapper
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        int page = pageParam.orElse(1);
        PageWrapper<AccountListDto> pageWrapper = accountOrgService.findAccountByOrgId(page, pageSize, orgId);
        
        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);

        return mav;
    }
    
    /**
     * getDepByCompany
     * @param companyId
     * @return
     * @author DaiTrieu
     */
    @RequestMapping(value = "/get_org_by_company", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getDepByCompany(@RequestParam(value = "companyId", required = true) Long companyId) {
        List<OrgNode> orgList = orgService.getNodeByCompanyId(companyId);
        String orgTreeJson = CommonJsonUtil.convertObjectToJsonString(orgList);
        return StringUtils.isNotBlank(orgTreeJson)?orgTreeJson:CommonJsonUtil.convertObjectToJsonString(new OrgNode());
    }
    
    /**
     * getNode
     * @param id
     * @return
     * @author DaiTrieu
     */
    @RequestMapping(value = "/get-node", method = RequestMethod.POST)
    @ResponseBody
    public List<OrgNode> getNode(@RequestParam(name = "id", required = false) Long id) {
        id = id != null? id: 0L;
        return organizationService.findOrgByOrgParent(id);
    }
    
    /**
     * buildOrgTreeByNodeSelect
     * @param id
     * @return
     * @author DaiTrieu
     */
    @RequestMapping(value = "/build-tree-by-node-select", method = RequestMethod.POST)
    @ResponseBody
    public String buildTreeByNodeSelect(@RequestParam(name = "orgId", required = false) Long orgId, @RequestParam(name = "companyId", required = false) Long companyId) {
        String result = "";
        try {
            List<OrgNode> orgNodes = organizationService.buildOrgTreeByNodeSelect(orgId, companyId);
            result = CommonJsonUtil.convertObjectToJsonString(orgNodes);
        } catch (Exception e) {
            throw new SystemException("Error build Tree By NodeSelect");
        }
        return result;
    }
}