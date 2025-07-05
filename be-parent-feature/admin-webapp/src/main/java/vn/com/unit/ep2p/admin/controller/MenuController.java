package vn.com.unit.ep2p.admin.controller;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.core.dto.JcaMenuDto;
import vn.com.unit.core.dto.JcaMenuLangDto;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.entity.JcaMenu;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.MenuDto;
import vn.com.unit.ep2p.admin.dto.MenuLanguageDto;
import vn.com.unit.ep2p.admin.dto.MenuTree;
import vn.com.unit.ep2p.admin.enumdef.MenuProcessEnum;
import vn.com.unit.ep2p.admin.enumdef.MenuSearchEnum;
import vn.com.unit.ep2p.admin.enumdef.MenuTypeEnum;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.MenuService;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.admin.validators.MenuValidator;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.export.util.SearchUtil;
import vn.com.unit.ep2p.treebuilder.utils.MenuNode;

@Controller
@RequestMapping(UrlConst.MENU_ROOT)
public class MenuController {
    /** menuService */
    @Autowired
    private MenuService menuService;

    /** MessageSource */
    @Autowired
    private MessageSource msg;
    
    /** MenuValidator */
    @Autowired
    private MenuValidator menuValidator;

    
    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
    
//    private static final String MENU_LEFT_VIEW = "jcanary.leftsidebar";
    private static final String MENU_LEFT_VIEW = "/templates/unit-left-sidebar.html";    
//    private static final String BREAD_CRUMB_VIEW = "jcanary.breadcrumb";
    private static final String BREAD_CRUMB_VIEW = "/templates/unit-breadcrumb.html";
    private static final String MENU_EDIT_VIEW = "/views/menu/menu-edit.html";
    private static final String MENU_LIST_VIEW = "/views/menu/menu-list.html";
    private static final String MENU_DETAIL_VIEW = "/views/menu/menu-detail.html";
    
    private static final String VIEW_SORT = "/views/menu/menu-list-sort.html";

    private static final String VIEW_TABLE_SORT = "/views/menu/menu-table-sort.html";
    
    private static final String SORT_COMPANY_ID = "SORT_COMPANY_ID";
    
    /** systemLogsService */
    @Autowired
    private SystemLogsService systemLogsService;
    
    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Load breadcrumb
     * @param request
     * @param locale
     * @param pathname
     * @return navigation
     * @author trieunh <trieunh@unit.com.vn>
     */
    
    @RequestMapping(value = UrlConst.BREADCRUMB, method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView loadLeftHeader(HttpServletRequest request, Locale locale, @RequestParam(value = "pathname", required = false) String pathname) {
        ModelAndView mav = new ModelAndView(BREAD_CRUMB_VIEW);
        String context = "";
        String languageCode = "en";
        if (request != null && locale != null) {
            context = request.getContextPath();
            languageCode = locale.getLanguage();
        }
        pathname = pathname.trim().replace(context, "");
        mav.addObject("navigation", menuService.buildNavigationBar(pathname, languageCode));
        return mav;
    }

    /**
     * Build left menu
     * @param request
     * @param locale
     * @return ModelAndView
     * @author trieunh <trieunh@unit.com.vn>
     */
    @RequestMapping(value = UrlConst.MENULEFT, method = RequestMethod.GET)
    public ModelAndView loadLeftMenu(HttpServletRequest request, Locale locale) {
        ModelAndView mav = new ModelAndView(MENU_LEFT_VIEW);
        mav.addObject("menuDto", menuService.buildMenuStructure(locale.getLanguage(), UserProfileUtils.getCompanyId()));
        return mav;
    }

    /**
     * get all list left menu
     * 
     * @param condition
     * @param page
     * @return ModelAndView
     * @author vinhnht
     */
    @RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView loadMenuAjax(@ModelAttribute(value = "menuModel") MenuDto condition, Locale locale,
            @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(MENU_LIST_VIEW);
        // set url ajax
        condition.setUrl(UrlConst.MENU.concat(UrlConst.LIST));
        
        // Security for this page.
        if (!UserProfileUtils.hasRole(RoleConstant.MENU) 
                && !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        // Set default company
        if (null == condition.getCompanyId()) {
            condition.setCompanyId(UserProfileUtils.getCompanyId());
        }
        WebUtils.setSessionAttribute(request, SORT_COMPANY_ID, condition.getCompanyId());
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        
        // set init search
        SearchUtil.setSearchSelect(MenuSearchEnum.class, mav);
        try {
            // Set front-end menu type
            condition.setMenuTypeList(menuService.getMenuTypeList(true));
            
            String lang = locale.getLanguage();
            PageWrapper<MenuDto> pageWrapper = menuService.search(page, condition, lang);

            mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
            mav.addObject("menuModel", condition);
        } catch (Exception e) {
            logger.error("#list#",e);
        }
        return mav;
    }

    /**
     * Load menu edit
     * 
     * @param locale
     * @param menuId
     * @return ModelAndView
     * @author vinhnht
     */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.GET)
    public ModelAndView getMenuEdit(Locale locale, @RequestParam(value = "id", required = false) Long menuId) {
        ModelAndView mav = new ModelAndView(MENU_EDIT_VIEW);
        // Security for this page.
        if (!UserProfileUtils.hasRole(RoleConstant.MENU) 
                && !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        try {
            // set url ajax
            String url = UrlConst.MENU.concat(UrlConst.EDIT);
            String language = locale.getLanguage();
            MenuDto menuDto = new MenuDto();
            if (null != menuId) {
                menuDto = menuService.getMenuDto(menuId, language, locale);
                // Security for data 
//                if (null == menuDto || !UserProfileUtils.hasRoleForCompany(menuDto.getCompanyId())) {
//                    return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//                }
                url = url.concat("?id=").concat(menuId.toString());
            } else {
                Long maxSort = menuService.getMaxMenuSort(0l, MenuTypeEnum.BACK_END.toString());
                maxSort = maxSort == null ? 1 : maxSort;
                menuDto.setSort(maxSort.intValue() + 1);
                // set process status
                menuDto.setStatusCode(MenuProcessEnum.CREATE.toString());
//                menuDto.setProcessId(processService.getProcessIdByBusinessCode("SM1"));
                // Set list item
                //menuDto.setListItemDto(menuService.buildListItemDto(locale));
                menuDto.setCompanyId(UserProfileUtils.getCompanyId());
                menuDto.setActiveMenu(true);
                menuDto.setItemId(0L);
                String itemName = msg.getMessage(ConstantCore.MESSAGE_NONE, null, locale);
                menuDto.setItemName(itemName);
            }
            menuDto.setUrlHidden(url);
            mav.addObject("menuModel", menuDto);
            
            // Init master data
            menuService.initScreenMenuEdit(mav, locale, MenuTypeEnum.BACK_END.toString(), menuDto);
        } catch (Exception e) {
            logger.error("#edit Get#",e);
        }
        return mav;
    }

    public Boolean checkLanguageInList(LanguageDto language, List<MenuLanguageDto> listMenuLanguage) {
        if (null != language) {
            for (MenuLanguageDto menuLangDto : listMenuLanguage) {
                if (menuLangDto.getLanguageId().equals(language.getId()))
                    return true;
            }
        }
        return false;
    }

    /**
     * Save menu
     * 
     * @param menuDto
     * @param bindingResult
     * @param locale
     * @param redirectAttributes
     * @return ModelAndView
     * @author vinhnht
     */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView saveMenu(@Valid @ModelAttribute(value = "menuModel") MenuDto menuDto,
            BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest  request) {
        
        // Security for this page.
        if (!UserProfileUtils.hasRole(RoleConstant.MENU) 
                && !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        // Write system logs
        if (null == menuDto.getMenuId()) {
            systemLogsService.writeSystemLogs(RoleConstant.MENU, "Save Add Menu",
                    "Save Menu(code: " + menuDto.getMenuCode() + ")", request);
        } else {
            systemLogsService.writeSystemLogs(RoleConstant.MENU, "Save Edit Menu",
                    "Save Menu(code: " + menuDto.getMenuCode() + ")", request);
        }
        
        ModelAndView mav = new ModelAndView(MENU_EDIT_VIEW);
        if (menuDto != null) {
            // Set list item
        	//TODO tantm menuDto.setListItemDto
//            menuDto.setListItemDto(menuService.buildListItemDto(locale));
        }
            
            // Init message list
            MessageList messageList = new MessageList(Message.SUCCESS);
            menuValidator.validate(menuDto, bindingResult);
            
            try {
                if (bindingResult.hasErrors()) {
                    // Add message error
                    messageList.setStatus(Message.ERROR);
                    String msgError = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
                    messageList.add(msgError);
                    
                    // Set list item
//                    menuDto.setListItemDto(menuService.buildListItemDto(locale));
                    
                    // Init master data          
                    menuService.initScreenMenuEdit(mav, locale, MenuTypeEnum.BACK_END.toString(), menuDto);
    
                    mav.addObject(ConstantCore.MSG_LIST, messageList);
                    mav.addObject("menuModel", menuDto);
                    
                    return mav;
                }
                
				Long id = menuDto.getMenuId();
//                menuService.saveMenuDMS(menuDto, locale);
				JcaMenuDto jcaMenuDto = objectMapper.convertValue(menuDto, JcaMenuDto.class);
				jcaMenuDto.setId(menuDto.getMenuId());
				jcaMenuDto.setActived(menuDto.getActiveMenu());
				jcaMenuDto.setDisplayOrder(menuDto.getSort());
				jcaMenuDto.setCode(menuDto.getMenuCode());
				jcaMenuDto.setMenuType(Integer.parseInt(menuDto.getMenuType()));
				jcaMenuDto.setClassName(menuDto.getIcon());
				jcaMenuDto.setMenuModule(menuDto.getMenuModule());
				
				List<JcaMenuLangDto> listLangTem = menuDto.getListMenuLanguage().stream().map(p -> {
					JcaMenuLangDto l = new JcaMenuLangDto();
					l.setMenuId(menuDto.getMenuId());
					l.setLangId(p.getLanguageId());
					l.setLangCode(p.getLanguageCode());
					l.setName(p.getLanguageName());
					l.setNameAbv(p.getAlias());
					return l;
				}).collect(Collectors.toList());
				jcaMenuDto.setLanguages(listLangTem);
				
                menuService.saveJcaMenuDto(jcaMenuDto);
                if(id == null){
                	id = jcaMenuDto.getId();
                }
                String viewName = StringUtils.EMPTY;
                String msgSuccess = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
                messageList.add(msgSuccess);
                
                viewName = UrlConst.REDIRECT.concat(UrlConst.MENU_ROOT).concat(UrlConst.EDIT);
                redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
                redirectAttributes.addAttribute("id", id);
               
                mav.setViewName(viewName);
            } catch (Exception e) {
                logger.error("edit POST",e);
            }
        return mav;
    }

    /**
     * Delete menu
     * 
     * @param menuId
     * @param menuViId
     * @param menuEnId
     * @return ModelAndView
     * @author vinhnht
     */
    @RequestMapping(value = UrlConst.DELETE, method = RequestMethod.POST)
    public ModelAndView deleteMenu(@RequestParam(value = "menuId", required = false) Long menuId, Locale locale,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {   
    	// don't check role because the project will aplly process later.
    	
        // Security for this page.
//        String role = new StringBuilder(RoleConstant.BUTTON_MENU_DELETE).append(ConstantCore.COLON_EDIT).append(ConstantCore.COLON).append(menu.getStatusCode())
//                .append(ConstantCore.COLON).append(menu.getProcessId()).toString();
//        // Security for this page.
//        if (!UserProfileUtils.hasRole(role)) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
        
        // Write system logs
        systemLogsService.writeSystemLogs(RoleConstant.MENU, "Delete Menu", "Delete Menu(id: " + menuId + ")", request);
        
//        menuService.deleteMenuDMS(menuId);
        menuService.deleteJcaMenuById(menuId);
      //Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
        messageList.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        
        // Delete success redirect list page
        String viewName = UrlConst.REDIRECT.concat(UrlConst.MENU_ROOT).concat(UrlConst.LIST);
        ModelAndView mav = new ModelAndView(viewName);               
        return mav;        
    }

    @RequestMapping(value = "/changeParent", method = RequestMethod.POST)
    public @ResponseBody Long changeParentMenu(@RequestParam(value = "parentId", required = false) Long parentId) {
        Long maxMenuSort = menuService.getMaxMenuSort(parentId, MenuTypeEnum.BACK_END.toString());
        return maxMenuSort == null ? 1 : maxMenuSort + 1;
    }
    /**
     * ajaxList
     *
     * @param condition
     * @param locale
     * @param page
     * @return
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView ajaxList(@ModelAttribute(value = "menuModel") MenuDto condition, Locale locale,
            @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/views/menu/menu-table.html");
        // set init search
        SearchUtil.setSearchSelect(MenuSearchEnum.class, mav);

        // Set front-end menu type
        condition.setMenuTypeList(menuService.getMenuTypeList(true));
        WebUtils.setSessionAttribute(request, SORT_COMPANY_ID, condition.getCompanyId());
        try {
            String lang = locale.getLanguage();
            PageWrapper<MenuDto> pageWrapper = menuService.search(page, condition, lang);

            mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
            mav.addObject("menuModel", condition);
        } catch (Exception e) {
            logger.error("AJAXLIST", e);
        }

        return mav;
    }
    /**
     * getDetail
     *
     * @param locale
     * @param menuId
     * @return ModelAndView
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.DETAIL, method = RequestMethod.GET)
    public ModelAndView getDetail(Locale locale, @RequestParam(value = "id", required = false) Long menuId, 
            @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page) {
        ModelAndView mav = new ModelAndView(MENU_DETAIL_VIEW);        

        // Security for this page.
        if (!UserProfileUtils.hasRole(RoleConstant.MENU) 
                && !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        } 
       
        try {
            // set url ajax
            String url = UrlConst.MENU.concat(UrlConst.DETAIL);
            url = url.concat("?id=").concat(menuId.toString());        
            
            String language = locale.getLanguage();                   
            MenuDto menuDto = new MenuDto();
            menuDto = menuService.getMenuDto(menuId, language, locale);     
            // Security for data 
//            if (null == menuDto || !UserProfileUtils.hasRoleForCompany(menuDto.getCompanyId())) {
//                return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//            }                                 
            menuDto.setUrlHidden(url);
            mav.addObject("menuModel", menuDto);     
            
            // Init master data          
            menuService.initScreenMenuEdit(mav, locale, MenuTypeEnum.BACK_END.toString(), menuDto);
            
            // Init PageWrapper           
//            PageWrapper<HistoryApproveDto> pageWrapper = historyApproveService.searchHistory(page, menuId, ConstantHistoryApprove.APPROVE_MENU);
//                           
//            mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
            
        } catch (Exception e) {
           logger.error("#DETAIL##",e);
        }
                
        return mav;
    }
    /**
     * postApprover
     *
     * @param menuDto
     * @param locale
     * @param redirectAttributes
     * @return
     * @throws Exception
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.DETAIL, method = RequestMethod.POST)
    public ModelAndView postApprover(@Valid @ModelAttribute(value = "menuModel") MenuDto menuDto,
            Locale locale, RedirectAttributes redirectAttributes) throws Exception {
        ModelAndView mav = new ModelAndView(MENU_DETAIL_VIEW);
//        String role = new StringBuilder(RoleConstant.BUTTON_MENU_APPROVER).append(ConstantCore.COLON_EDIT).append(ConstantCore.COLON).append(menuDto.getStatusCode())
//                .append(ConstantCore.COLON).append(menuDto.getProcessId()).toString();
//        
//        String role1 = new StringBuilder(RoleConstant.BUTTON_MENU_REJECT).append(ConstantCore.COLON_EDIT).append(ConstantCore.COLON).append(menuDto.getStatusCode())
//                .append(ConstantCore.COLON).append(menuDto.getProcessId()).toString();
//        // Security for this page.
//        if (!UserProfileUtils.hasRole(role) && !UserProfileUtils.hasRole(role1)) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }        
//               
        // Approve          
//        menuService.approver(menuDto);
        String msgInfo = "";
        if (!menuDto.isAction()) {
            msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_RETURN, null, locale);            
        } else {
            msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_APPROVE, null, locale);            
        }
        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        messageList.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        
        String viewName = UrlConst.REDIRECT.concat(UrlConst.MENU_ROOT).concat(UrlConst.DETAIL);
        mav.setViewName(viewName);
        redirectAttributes.addAttribute("id", menuDto.getMenuId());
        
        mav.setViewName(viewName);
               
        return mav;
    }

    /**
     * changeMenuType
     *
     * @param menuType
     * @return
     * @author TranLTH
     */
    @RequestMapping(value = "/changeMenuType", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" }, consumes = { "application/json; charset=utf-8" })
    public @ResponseBody String changeMenuType(@RequestParam(value = "menuType", required = false) String menuType,
            @RequestParam(value = "companyId", required = false) Long companyId, Locale locale) {
        // Init root id
        Long rootId = 0L;
        JcaMenu root = menuService.findMenuRoot(companyId);
        if (null != root) {
            rootId = root.getId();
        }
        MenuTree menuTreeDto = new MenuTree();
        List<MenuNode> menuTree = menuService.findMenuTree(locale.getLanguage(), menuType, companyId, rootId);
        if (null != menuTree && !menuTree.isEmpty()) {
            menuTreeDto.setMenuTree(menuTree);
        }
        menuTreeDto.setRootId(rootId);
        String menuTreeJson = CommonJsonUtil.convertObjectToJsonString(menuTreeDto);
        return menuTreeJson;
    }
    
    /**
     * ajaxHistoryList
     *
     * @param condition
     * @param locale
     * @param page
     * @return
     * @author TranLTH
     */
//    @RequestMapping(value = UrlConst.AJAX_HISTORY, method = { RequestMethod.POST })
//    @ResponseBody
//    public ModelAndView ajaxHistoryList(@ModelAttribute(value = "historySearh") HistoryApproveSearcbDto condition,
//            Locale locale, @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page) {
//        ModelAndView mav = new ModelAndView(ViewConstant.VIEW_HISTORY_TABLE);
//
//        // Init PageWrapper
//        PageWrapper<HistoryApproveDto> pageWrapper = historyApproveService.searchHistory(page,
//                condition.getReferenceId(), condition.getReferenceType());
//        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
//
//        return mav;
//    }
    
    /**
     * getSortPage
     *
     * @param locale
     * @param model
     * @return ModelAndView
     * @author hand
     */
    @RequestMapping(value = "list/sort", method = RequestMethod.GET)
    public ModelAndView getSortPage(@ModelAttribute(value = "menuSearch") MenuDto dto, Locale locale, HttpServletRequest request) {

        // Security for this page.
        if (!UserProfileUtils.hasRole(RoleConstant.MENU) && !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        // ModelAndView
        ModelAndView mav = new ModelAndView(VIEW_SORT);

        // languageCode
        String languageCode = locale.toString();
        // set languageCode
        dto.setLanguageCode(languageCode);

        // menuTree
        try {
            Long companyId = Long.parseLong(WebUtils.getSessionAttribute(request, SORT_COMPANY_ID).toString());
            dto.setCompanyId(companyId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        // Init root id
        Long rootId = 0L;
        JcaMenu root = menuService.findMenuRoot(dto.getCompanyId());
        if (null != root) {
            rootId = root.getId();
        }
        List<MenuNode> menuTree = menuService.findMenuTree(locale.getLanguage(), "1", dto.getCompanyId(), rootId);
        String menuTreeJson = CommonJsonUtil.convertObjectToJsonString(menuTree);
        mav.addObject("menuList", menuTreeJson);

        if (dto.getParentId() == null) {
            dto.setParentId(rootId);
        }

        List<MenuDto> sortPageModel = menuService.findMenuListForSorting(dto.getParentId(), languageCode);
        mav.addObject("sortPageModel", sortPageModel);

        return mav;
    }

    /**
     * postSortPage
     *
     * @param locale
     * @return ModelAndView
     * @author hand
     */
    @RequestMapping(value = "list/sort", method = RequestMethod.POST)
    public ModelAndView postSortPage(@RequestBody MenuDto dto, RedirectAttributes redirectAttributes, Locale locale) {

        // Security for this page.
        if (!UserProfileUtils.hasRole(RoleConstant.MENU) 
                && !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        // Init message success list
        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
        messageList.add(msgInfo);

        // uppdate sort all
        menuService.updateSortAll(dto.getSortOderList());

        // update success redirect sort list page
        ModelAndView mav = new ModelAndView("/views/commons/message-alert.html");

        mav.addObject(ConstantCore.MSG_LIST, messageList);

        return mav;
    }

    /**
     * getSortPage
     *
     * @param locale
     * @param model
     * @return ModelAndView
     * @author hand
     */
    @RequestMapping(value = "search/list/sort", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView getListByTypeId(@ModelAttribute(value = "menuSearch") MenuDto dto, Locale locale) {
        
           // Security for this page.
        if (!UserProfileUtils.hasRole(RoleConstant.MENU) 
                && !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(RoleConstant.MENU.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        // ModelAndView
        ModelAndView mav = new ModelAndView(VIEW_TABLE_SORT);

        // languageCode
        String languageCode = locale.toString();
        dto.setLanguageCode(languageCode);

        List<MenuDto> sortPageModel = menuService.findMenuListForSorting(dto.getParentId(), languageCode);
        mav.addObject("sortPageModel", sortPageModel);

        return mav;
    }
    /**
     * select2Menu
     *
     * @param term
     * @return
     * @author phatvt
     */
//    @RequestMapping(value = "search/menu", method = RequestMethod.POST)
//    @ResponseBody
//    public Select2ResultDto select2Menu(@RequestParam(required = false) String term){
//        Select2ResultDto obj = new Select2ResultDto();
//        List<Select2Dto> lst = 
//        obj.setTotal(lst.size());
//        obj.setResults(lst);
//        return obj;
//    }
}