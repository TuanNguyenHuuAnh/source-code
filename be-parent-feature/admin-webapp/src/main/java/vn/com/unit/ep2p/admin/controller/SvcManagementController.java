/*******************************************************************************
 * Class        :SvcManagementController
 * Created date :2019/04/21
 * Lasted date  :2019/04/21
 * Author       :HungHT
 * Change log   :2019/04/21:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
import vn.com.unit.common.exception.AppException;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.ResultDto;
import vn.com.unit.ep2p.admin.dto.Select2ResultDto;
import vn.com.unit.ep2p.admin.dto.SvcManagementDto;
import vn.com.unit.ep2p.admin.dto.SvcManagementSearchDto;
import vn.com.unit.ep2p.admin.enumdef.ResultStatus;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.ComponentAuthorityJcanaryService;
import vn.com.unit.ep2p.admin.service.SvcManagementService;
import vn.com.unit.ep2p.admin.utils.AccessLogger;
import vn.com.unit.ep2p.admin.validators.SvcManagementValidator;
import vn.com.unit.ep2p.constant.AppConstantCore;
import vn.com.unit.ep2p.constant.AppUrlConst;
import vn.com.unit.ep2p.constant.ItemConstant;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.enumdef.SvcSearchEnum;
import vn.com.unit.ep2p.export.util.SearchUtil;
import vn.com.unit.ep2p.utils.ExecMessage;

/**
 * SvcManagementController
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 * 
 */
@Controller
@RequestMapping(AppUrlConst.SVC_MANAGEMENT)
public class SvcManagementController {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(SvcManagementController.class);

    @Autowired
    SvcManagementValidator svcManagementValidator;

    @Autowired
    SvcManagementService svcManagementService;
    
    @Autowired
    ComponentAuthorityJcanaryService componentAuthorityService;


    @Autowired
    MessageSource msg;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    CompanyService companyService;

    private static final String SCREEN_FUNCTION_CODE = ItemConstant.ITEM_SCREEN_SVC_MANAGEMENT;

    private static final String MAV_SVC_MANAGEMENT_LIST = "/views/svc-management/svc-management-list.html" ;

    private static final String MAV_SVC_MANAGEMENT_TABLE = "/views/svc-management/svc-management-table.html";

    private static final String MAV_SVC_MANAGEMENT_DETAIL = "/views/svc-management/svc-management-detail.html";

    private static final String OBJ_SEARCH = "search";

    private static final String OBJ_PAGE_WRAPPER = "pageWrapper";

    private static final String OBJECT_DTO = "objectDto";

    private static final String URL_REDIRECT = "urlRedirect";

    /**
     * dateBinder
     * 
     * @param binder
     * @param request
     * @param locale
     * @author HungHT
     */
    @InitBinder
    public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
        // The date format to parse or output your dates
        String patternDate = (String) request.getSession().getAttribute(ConstantCore.FORMAT_DATE);
        SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
        // Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        // Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);

        binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
    }

    /**
     * getSvcManagementList
     * 
     * @param search
     * @param pageSizeParam
     * @param pageParam
     * @param locale
     * @return
     * @author HungHT
     * @throws DetailException 
     */
    @RequestMapping(value = { UrlConst.ROOT, UrlConst.LIST }, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getSvcManagementList(@ModelAttribute(value = OBJ_SEARCH) SvcManagementSearchDto search,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, Locale locale) throws DetailException {
        AccessLogger debugLogger = new AccessLogger();
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        ModelAndView mav = new ModelAndView(MAV_SVC_MANAGEMENT_LIST);
        String lang = locale.getLanguage();
        // set init search
        SearchUtil.setSearchSelect(SvcSearchEnum.class, mav);

        // Init page size
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        int page = pageParam.orElse(1);
        
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        
        // Set company id
        search.setCompanyId(UserProfileUtils.getCompanyId());

        // Get List
        PageWrapper<SvcManagementDto> pageWrapper = svcManagementService.getSvcManagementList(search, pageSize, page, lang);
        // Object mav
        mav.addObject(OBJ_SEARCH, search);
        mav.addObject(OBJ_PAGE_WRAPPER, pageWrapper);
        debugLogger.setEndTime();
//        AccessLogger.debug("%d,%s,%s,%s,%s,%s,%s,%s,%d,%s", UserProfileUtils.getAccountId(), UserProfileUtils.getUserNameLogin(), UserProfileUtils.getUserProfile().getFullname(), UserProfileUtils.getUserProfile().getSystemCode(), 
//                UserProfileUtils.getUserProfile().getCompanyName(), "SVC MANAGEMENT - LIST", "/svc-management/list",
//                debugLogger.getEnd(), debugLogger.getElapsedTime(), "PPL");
        return mav;
    }

    /**
     * getSvcManagementTable
     * 
     * @param search
     * @param pageSizeParam
     * @param pageParam
     * @param locale
     * @return
     * @author HungHT
     * @throws DetailException 
     */
    @RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
    public ModelAndView getSvcManagementTable(@ModelAttribute(value = OBJ_SEARCH) SvcManagementSearchDto search,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, Locale locale) throws DetailException {
        AccessLogger debugLogger = new AccessLogger();
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        ModelAndView mav = new ModelAndView(MAV_SVC_MANAGEMENT_TABLE);
        String lang = locale.getLanguage();
        // Init page size
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        int page = pageParam.orElse(1);
        
        // Get List
        PageWrapper<SvcManagementDto> pageWrapper = svcManagementService.getSvcManagementList(search, pageSize, page, lang);

        // Object mav
        mav.addObject(OBJ_PAGE_WRAPPER, pageWrapper);
        debugLogger.setEndTime();
//        AccessLogger.debug("%d,%s,%s,%s,%s,%s,%s,%s,%d,%s", UserProfileUtils.getAccountId(), UserProfileUtils.getUserNameLogin(), UserProfileUtils.getUserProfile().getFullname(), UserProfileUtils.getUserProfile().getSystemCode(), 
//                UserProfileUtils.getUserProfile().getCompanyName(), "SVC MANAGEMENT - AJAX LIST", "/svc-management/ajaxList",
//                debugLogger.getEnd(), debugLogger.getElapsedTime(), "PPL");
        return mav;
    }

    /**
     * getSvcManagementDetail
     * 
     * @param id
     * @param message
     * @param status
     * @param locale
     * @return
     * @author HungHT
     * @throws AppException 
     */
    @RequestMapping(value = { UrlConst.ADD, UrlConst.DETAIL }, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getSvcManagementDetail(@RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "message", required = false) String message,
            @RequestParam(value = "status", required = false) Long status, Locale locale) throws AppException {
//        AccessLogger debugLogger = new AccessLogger();
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        ModelAndView mav = new ModelAndView(MAV_SVC_MANAGEMENT_DETAIL);
        String lang = locale.getLanguage();
        if (null == status) {

            // Object dto
            SvcManagementDto objectDto = null;
            // URL ajax redirect
            StringBuilder urlRedirect = new StringBuilder("svc-management");
            if (id != null) {
                objectDto = svcManagementService.findById(id, lang);
                // Security for data 
//                if (null == objectDto || !UserProfileUtils.hasRoleForCompany(objectDto.getCompanyId())) {
//                    return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//                }
                urlRedirect.append(UrlConst.DETAIL.concat("?id=").concat(id.toString()));
            } else {
                objectDto = new SvcManagementDto();
                objectDto.setFormLangs(new ArrayList<>());
                urlRedirect.append(UrlConst.ADD);
            }
            // Init screen
            svcManagementService.initScreenDetail(mav, objectDto, locale);

            // Object mav
            mav.addObject(OBJECT_DTO, objectDto);
            mav.addObject(URL_REDIRECT, urlRedirect.toString());
        } else {
            mav = new ModelAndView("/views/commons/message-alert.html");
            MessageList messageList = new MessageList(Message.SUCCESS);
            String msgContent = null;
            if (ResultStatus.SUCCESS.toInt() == status) {

                // Set message
                msgContent = message;
            } else {
                // Set message
                messageList.setStatus(Message.ERROR);
                if (StringUtils.isNotBlank(message)) {
                    msgContent = message;
                } else {
                    msgContent = msg.getMessage(AppConstantCore.MSG_ERROR_UPDATE, null, locale);
                }
            }
            messageList.add(msgContent);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
        }
//        debugLogger.setEndTime();
//        AccessLogger.debug("%d,%s,%s,%s,%s,%s,%s,%s,%d,%s", UserProfileUtils.getAccountId(), UserProfileUtils.getUserNameLogin(), UserProfileUtils.getUserProfile().getFullname(), UserProfileUtils.getUserProfile().getSystemCode(), 
//                UserProfileUtils.getUserProfile().getCompanyName(), "SVC MANAGEMENT - ADD", "/svc-management/add",
//                debugLogger.getEnd(), debugLogger.getElapsedTime(), "PPL");
        return mav;
    }

    /**
     * registerSvc
     * 
     * @param objectDto
     * @param bindingResult
     * @param locale
     * @param redirectAttributes
     * @param request
     * @return
     * @author HungHT
     */
    @PostMapping(value = AppUrlConst.SAVE)
    @ResponseBody
    public ResultDto registerSvc(@ModelAttribute(value = OBJ_SEARCH) SvcManagementDto objectDto, BindingResult bindingResult, Locale locale,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {
//        AccessLogger debugLogger = new AccessLogger();
        ResultDto result = new ResultDto();
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            result.setStatus(0);
            result.setMessage(ExecMessage.getErrorMessage(msg, "B006", locale).getErrorDesc());
            return result;
        }

        try {
            result = svcManagementService.saveSvcManagement(objectDto, locale);
        } catch (AppException e) {
            result.setStatus(0);
            result.setMessage(ExecMessage.getErrorMessage(msg, e.getCode(), e.getArgs(), locale).getErrorDesc());
            logger.error(e.getMessage());
        } catch (Exception e) {
            result.setStatus(0);
            logger.error(e.getMessage());
        }
//        debugLogger.setEndTime();
//        AccessLogger.debug("%d,%s,%s,%s,%s,%s,%s,%s,%d,%s", UserProfileUtils.getAccountId(), UserProfileUtils.getUserNameLogin(), UserProfileUtils.getfugetFullname(), UserProfileUtils.getUserProfile().getSystemCode(), 
//                UserProfileUtils.getUserProfile().getCompanyName(), "SVC MANAGEMENT - SAVE", "/svc-management/save",
//                debugLogger.getEnd(), debugLogger.getElapsedTime(), "PPL");
//        AccessLogger.debug("%d,%s,%s,%s,%s,%s,%s,%s,%d,%s", UserProfileUtils.getAccountId(), UserProfileUtils.getUserNameLogin(), UserProfileUtils.getFullName(), UserProfileUtils.findOnlyFunctionCode(), 
//				 "SVC MANAGEMENT - SAVE", "/svc-management/save",
//				debugLogger.getEnd(), debugLogger.getElapsedTime(), "PPL");
        return result;
    }

    /**
     * deleteSvcManagementDetail
     * 
     * @param search
     * @param id
     * @param locale
     * @param redirectAttributes
     * @param request
     * @return
     * @author HungHT
     */
    @PostMapping(UrlConst.DELETE)
    public ModelAndView deleteSvcManagementDetail(@ModelAttribute(value = OBJ_SEARCH) SvcManagementSearchDto search,
            @RequestParam(value = "id") Long id, Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        AccessLogger debugLogger = new AccessLogger();
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        boolean deleteFlg = false;
        String msgContent = null;

        // delete
        try {
            deleteFlg = svcManagementService.deleteSvcManagement(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        // Check delete
        if (deleteFlg) {
            // Add message success
            msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
        } else {
            // Add message error
            messageList.setStatus(Message.ERROR);
            msgContent = msg.getMessage(ConstantCore.MSG_ERROR_DELETE, null, locale);
        }
        messageList.add(msgContent);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        redirectAttributes.addFlashAttribute(OBJ_SEARCH, search);

        // Redirect
        String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat("svc-management").concat(UrlConst.LIST);
        ModelAndView mav = new ModelAndView(viewName);
        debugLogger.setEndTime();
//        AccessLogger.debug("%d,%s,%s,%s,%s,%s,%s,%s,%d,%s", UserProfileUtils.getAccountId(), UserProfileUtils.getUserNameLogin(), UserProfileUtils.getUserProfile().getFullname(), UserProfileUtils.getUserProfile().getSystemCode(), 
//                UserProfileUtils.getUserProfile().getCompanyName(), "SVC MANAGEMENT - DELETE", "/svc-management/delete",
//                debugLogger.getEnd(), debugLogger.getElapsedTime(), "PPL");
        return mav;
    }

    /**
     * getBusinessList
     * 
     * @param keySearch
     * @param isPaging
     * @return
     * @author HungHT
     */
    @PostMapping(value = "/get-business")
    @ResponseBody
    public Object getBusinessList(@RequestParam(required = false) String keySearch, @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) boolean isPaging) {
        AccessLogger debugLogger = new AccessLogger();
        Select2ResultDto obj = new Select2ResultDto();
        List<Select2Dto> lst = svcManagementService.getBusinessList(keySearch, companyId, isPaging);
        obj.setTotal(lst.size());
        obj.setResults(lst);
        debugLogger.setEndTime();
//        AccessLogger.debug("%d,%s,%s,%s,%s,%s,%s,%s,%d,%s", UserProfileUtils.getAccountId(), UserProfileUtils.getUserNameLogin(), UserProfileUtils.getUserProfile().getFullname(), UserProfileUtils.getUserProfile().getSystemCode(), 
//                UserProfileUtils.getUserProfile().getCompanyName(), "SVC MANAGEMENT - GET BUSINESS", "/svc-management/get-business",
//                debugLogger.getEnd(), debugLogger.getElapsedTime(), "PPL");
        return obj;
    }
    
    /**
     * getItemList
     * 
     * @param keySearch
     * @param isPaging
     * @return
     * @author HungHT
     */
    @PostMapping(value = "/get-item")
    @ResponseBody
    public Object getItemList(@RequestParam(required = false) String keySearch, @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) boolean isPaging, @RequestParam(required = false) Long mode) {
        AccessLogger debugLogger = new AccessLogger();
        Select2ResultDto obj = new Select2ResultDto();
        List<Select2Dto> lst = componentAuthorityService.getItemListByForm(keySearch, companyId, isPaging, true, mode);
        obj.setTotal(lst.size());
        obj.setResults(lst);
        debugLogger.setEndTime();
//        AccessLogger.debug("%d,%s,%s,%s,%s,%s,%s,%s,%d,%s", UserProfileUtils.getAccountId(), UserProfileUtils.getUserNameLogin(), UserProfileUtils.getUserProfile().getFullname(), UserProfileUtils.getUserProfile().getSystemCode(), 
//                UserProfileUtils.getUserProfile().getCompanyName(), "SVC MANAGEMENT - GET ITEM", "/svc-management/get-item",
//                debugLogger.getEnd(), debugLogger.getElapsedTime(), "PPL");
        return obj;
    }
}