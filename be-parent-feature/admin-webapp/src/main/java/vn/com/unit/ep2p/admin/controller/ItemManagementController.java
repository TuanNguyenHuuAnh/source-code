package vn.com.unit.ep2p.admin.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.JcaItemDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.ItemManagementSearchDto;
import vn.com.unit.ep2p.admin.dto.Select2ResultDto;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.ItemManagementService;
import vn.com.unit.ep2p.admin.validators.ItemManagementValidator;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.enumdef.ItemManagementSearchEnum;
import vn.com.unit.ep2p.export.util.SearchUtil;
//import vn.com.unit.ep2p.workflow.service.AppBusinessService;
import vn.com.unit.ep2p.utils.ConditionSearchUtils;

/**
 * Created by quangnd on 7/27/2018.
 */
@Controller
@RequestMapping(UrlConst.ITEM_MANAGEMENT)
public class ItemManagementController {

    @Autowired
    ItemManagementService itemManagementService;

    @Autowired
    MessageSource msg;

    @Autowired
    CompanyService companyService;

    @Autowired
    ItemManagementValidator itemManagementValidator;

    @Autowired
    private SystemConfig systemConfig;

//    @Autowired
//    AppBusinessService appBusinessService;
    
    @Autowired
    private JcaConstantService jcaConstantService;

    /** SCREEN_FUNCTION_CODE */
    private static final String SCREEN_FUNCTION_CODE = RoleConstant.ITEM_MANAGEMENT;

    private static final Logger logger = LoggerFactory.getLogger(ItemManagementController.class);

    private static final String ITEM_MANAGEMENT_LIST = "/views/item-management/item-management-list.html";

    private static final String ITEM_MANAGEMENT_TABLE = "/views/item-management/item-management-table.html";

    private static final String ITEM_MANAGEMENT_EDIT = "/views/item-management/item-management-edit.html";

    private static final String ITEM_MANAGEMENT_DETAIL = "/views/item-management/item-management-detail.html";

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

    @RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getItemManagement(@ModelAttribute(value = "searchDto") ItemManagementSearchDto searchDto,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, HttpServletRequest request,
            Locale locale) {
        ModelAndView mav = new ModelAndView(ITEM_MANAGEMENT_LIST);
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
                UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);

        searchDto.setCompanyId(UserProfileUtils.getCompanyId());

        try {
            // set init search
            SearchUtil.setSearchSelect(ItemManagementSearchEnum.class, mav);

            int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
            int page = pageParam.orElse(1);

            // Session Search
            ConditionSearchUtils<ItemManagementSearchDto> searchUtil = new ConditionSearchUtils<ItemManagementSearchDto>();
            String[] urlContains = new String[] { "item/add", "item/edit", "item/detail", "item/list" };
            searchDto = searchUtil.getConditionSearch(this.getClass(), searchDto, urlContains, request, page, pageSize);
            pageSize = Optional.ofNullable(searchDto.getPageSize()).orElse(pageSize);
            page = Optional.ofNullable(searchDto.getPage()).orElse(page);

            PageWrapper<JcaItemDto> pageWrapper = itemManagementService.search(page, pageSize, searchDto);
            mav.addObject("pageWrapper", pageWrapper);
            mav.addObject("searchDto", searchDto);
        } catch (Exception e) {
            logger.error("##getItemManagement##", e);
        }
        return mav;
    }

    @RequestMapping(value = UrlConst.AJAX_LIST, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView getAjaxItemManagement(@ModelAttribute(value = "searchDto") ItemManagementSearchDto searchDto,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, HttpServletRequest request,
            Locale locale) {
        ModelAndView mav = new ModelAndView(ITEM_MANAGEMENT_TABLE);
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // set init search
        SearchUtil.setSearchSelect(ItemManagementSearchEnum.class, mav);
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        int page = pageParam.orElse(1);

        if (null == searchDto.getCompanyId()) {
            searchDto.setCompanyId(UserProfileUtils.getCompanyId());
        }
        
        PageWrapper<JcaItemDto> pageWrapper = itemManagementService.search(page, pageSize, searchDto);
        mav.addObject("pageWrapper", pageWrapper);
        
        // Session Search
        ConditionSearchUtils<ItemManagementSearchDto> searchUtil = new ConditionSearchUtils<ItemManagementSearchDto>();
        searchUtil.setConditionSearch(request, this.getClass(), searchDto, page, pageSize);
        
        return mav;
    }

    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.GET)
    public ModelAndView editItemManagement(@RequestParam(value = "id", required = false) Long id, Model model, Locale locale) {

        ModelAndView mav = new ModelAndView(ITEM_MANAGEMENT_EDIT);

        // If redirect page
        Map<String, Object> md = model.asMap();
        MessageList messageList = (MessageList) md.get(ConstantCore.MSG_LIST);
        mav.addObject(ConstantCore.MSG_LIST, messageList);

        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
                UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);

        try {
            JcaItemDto objectDto = null;
            // URL ajax redirect
            StringBuilder urlRedirect = new StringBuilder(UrlConst.ITEM_MANAGEMENT.substring(1));
            if (null != id) {
                objectDto = itemManagementService.findById(id);
                // Security for data edit
                // if (null == objectDto || !UserProfileUtils.hasRoleForCompany(objectDto.getCompanyId())) {
                // return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
                // }
//                itemManagementService.getConstantDisplay(mav);
                urlRedirect.append(UrlConst.EDIT.concat("?id=").concat(id.toString()));
            } else {
                objectDto = new JcaItemDto();
                objectDto.setDisplayOrder(itemManagementService.getMaxDisplayOrder());
                objectDto.setCompanyId(UserProfileUtils.getCompanyId());
                urlRedirect.append(UrlConst.EDIT);
            }
            itemManagementService.getConstantDisplay(mav, locale.getLanguage());
            mav.addObject("itemManagementDto", objectDto);
            mav.addObject("urlRedirect", urlRedirect.toString());
        } catch (Exception e) {
            logger.error("##editItemManagement##", e);
        }
        return mav;
    }

    @RequestMapping(value = "/doSaveItemManagement", method = { RequestMethod.POST })
    public ModelAndView doSaveItemManagement(@Valid @ModelAttribute(value = "itemManagementDto") JcaItemDto jcaItemDto,
            BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(ITEM_MANAGEMENT_EDIT);

        // Add company list
//         List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
//         UserProfileUtils.isCompanyAdmin());
//         mav.addObject("companyList", companyList);
      
        
        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgInfo = StringUtils.EMPTY;
//        if (null == jcaItemDto.getItemId()) {
//            msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
//        } else {
//            msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
//        }

        // Validation
        itemManagementValidator.validate(jcaItemDto, bindingResult);
        if (bindingResult.hasErrors()) {
            messageList.setStatus(Message.ERROR);
            String[] errorArgs = new String[1];
            errorArgs[0] = jcaItemDto.getFunctionCode();
            msgInfo = msg.getMessage("message.error.code.existed", errorArgs, locale);
            if (jcaItemDto.getItemId() == null) {
                msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_CREATE, null, locale);
            } else {
                msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
            }
            
            
            itemManagementService.getConstantDisplay(mav, locale.getLanguage());
            // Add company list
            List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
            mav.addObject("companyList", companyList);
            messageList.add(msgInfo);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject("itemManagementDto", jcaItemDto);
           
            return mav;
        }

        try {
            // Validation
            // itemManagementValidator.validate(jcaItemDto, bindingResult);
            // if (bindingResult.hasErrors()) {
            // messageList.setStatus(Message.ERROR);
            // String[] errorArgs = new String[1];
            // errorArgs[0] = jcaItemDto.getFunctionCode();
            // msgInfo = msg.getMessage("message.error.code.existed", errorArgs, locale);
            // if (jcaItemDto.getItemId() == null) {
            // msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_CREATE, null, locale);
            // } else {
            // msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
            // }
            // itemManagementService.getConstantDisplay(mav);
            // messageList.add(msgInfo);
            // mav.addObject(ConstantCore.MSG_LIST, messageList);
            // mav.addObject("itemManagementDto", jcaItemDto);
            // return mav;
            // } else {
            itemManagementService.doSaveItemManagement(jcaItemDto);
            
          if (null == jcaItemDto.getItemId()) {
          msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
      } else {
          msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
      }
            messageList.add(msgInfo);
            redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
            // }

        } catch (Exception e) {
            messageList.setStatus(Message.ERROR);
            if (jcaItemDto.getItemId() == null) {
                msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_CREATE, null, locale);
            } else {
                msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
            }
            itemManagementService.getConstantDisplay(mav, locale.getLanguage());
            messageList.add(msgInfo);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject("itemManagementDto", jcaItemDto);
            
            return mav;
        }
        // Redirect
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ITEM_MANAGEMENT).concat(UrlConst.EDIT);
        mav.setViewName(viewName);
        redirectAttributes.addAttribute("id", jcaItemDto.getItemId());

        return mav;
    }

    @RequestMapping(value = UrlConst.DELETE, method = { RequestMethod.POST })
    public ModelAndView doDeleteItemManagement(@ModelAttribute(value = "searchDto") ItemManagementSearchDto searchDto,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, @RequestParam(value = "id", required = false) Long id,
            Locale locale) {
        ModelAndView mav = new ModelAndView(ITEM_MANAGEMENT_LIST);
        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
        try {
            itemManagementService.doDeleteItemManagement(id);
            int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
            int page = pageParam.orElse(1);
            searchDto.setCompanyId(UserProfileUtils.getCompanyId());
            PageWrapper<JcaItemDto> pageWrapper = itemManagementService.search(page, pageSize, searchDto);
            mav.addObject("pageWrapper", pageWrapper);
            // set init search
            SearchUtil.setSearchSelect(ItemManagementSearchEnum.class, mav);
            itemManagementService.getConstantDisplay(mav, locale.getLanguage());
        } catch (Exception e) {
            logger.error("##doDeleteItemManagement##", e);
            messageList.setStatus(Message.ERROR);
            msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_DELETE, null, locale);
        }
        messageList.add(msgInfo);
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        return mav;
    }

    @RequestMapping(value = "/getBusinessByCompanyId", method = RequestMethod.POST)
    @ResponseBody
    public Object getBusinessByCompanyId(@RequestParam(required = false) String term, @RequestParam(required = false) Long companyId) {
        Select2ResultDto obj = new Select2ResultDto();
        List<Select2Dto> lst = new ArrayList<Select2Dto>();
        lst = itemManagementService.getBusinessSelect2ByTermAndCompanyId(term, companyId);
        obj.setTotal(lst.size());
        obj.setResults(lst);
        return obj;
    }

    @RequestMapping(value = "/get-item", method = RequestMethod.POST)
    @ResponseBody
    public Object getItemByCondition(@RequestParam(required = false) String keySearch, @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) List<String> functionTypes, @RequestParam(required = false) String subType,
            @RequestParam(required = false) Boolean isPaging, Locale locale) {
        Select2ResultDto obj = new Select2ResultDto();
        List<Select2Dto> lst = new ArrayList<Select2Dto>();
        lst = itemManagementService.getSelect2ByCondition(keySearch, companyId, functionTypes, subType, isPaging);
        String defaultItemName = msg.getMessage(ConstantCore.MESSAGE_NONE, null, locale);
        Select2Dto defaultItem = new Select2Dto(ConstantCore.STR_ZERO, defaultItemName, defaultItemName);
        lst.add(0, defaultItem);
        obj.setTotal(lst.size());
        obj.setResults(lst);
        return obj;
    }

    /**
     * getDetail
     * 
     * @param id
     * @param locale
     * @return
     * @author trieuvd
     */
    @RequestMapping(value = UrlConst.DETAIL, method = RequestMethod.GET)
    public ModelAndView getDetail(@RequestParam(value = "id", required = true) Long id, Locale locale) {
        ModelAndView mav = new ModelAndView(ITEM_MANAGEMENT_DETAIL);
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        JcaItemDto objectDto = itemManagementService.findById(id);

        // Security for data
        // if (null == objectDto || (objectDto.getCompanyId() != null && !UserProfileUtils.hasRoleForCompany(objectDto.getCompanyId()))) {
        // return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        // }
        // // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
                UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        List<JcaConstantDto> lstFunctionType = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind("JCA_ADMIN_ITEM", "ITEM_TYPE", locale.getLanguage());
        for (JcaConstantDto jcaConstantLangDto : lstFunctionType) {
            if ("1".equals(objectDto.getFunctionType())) {
                objectDto.setFunctionType("System");
            } else if (objectDto.getFunctionType().equals(jcaConstantLangDto.getCode())) {
                objectDto.setFunctionType(jcaConstantLangDto.getName());
                break;
            }
        }
        mav.addObject("itemManagementDto", objectDto);
        
        //itemManagementService.getConstantDisplay(mav, locale.getLanguage());
        return mav;
    }
}
