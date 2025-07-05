/*******************************************************************************
 * Class        :RegisterSvcController
 * Created date :2019/04/16
 * Lasted date  :2019/04/16
 * Author       :HungHT
 * Change log   :2019/04/16:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.exception.AppException;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.dts.api.enumdef.APIStatus;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.ConstantDisplayDto;
import vn.com.unit.ep2p.admin.dto.ResReportList;
import vn.com.unit.ep2p.admin.dto.ResultDto;
import vn.com.unit.ep2p.admin.enumdef.ConstantDisplayType;
import vn.com.unit.ep2p.admin.enumdef.ResultStatus;
import vn.com.unit.ep2p.admin.service.CategoryService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.ConstantDisplayService;
import vn.com.unit.ep2p.admin.service.JpmRegisterSvcService;
import vn.com.unit.ep2p.admin.utils.AccessLogger;
import vn.com.unit.ep2p.constant.AppConstantCore;
import vn.com.unit.ep2p.constant.AppUrlConst;
import vn.com.unit.ep2p.constant.ItemConstant;
import vn.com.unit.ep2p.dto.PPLRegisterSvcDto;
import vn.com.unit.ep2p.dto.PPLRegisterSvcSearchDto;
import vn.com.unit.ep2p.dto.PPLResReportList;
import vn.com.unit.ep2p.utils.ExecMessage;

/**
 * RegisterSvcController
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Controller
@RequestMapping(AppUrlConst.JPM_REGISTER_SVC)
public class JpmRegisterSvcController {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(JpmRegisterSvcController.class);

    @Autowired
    JpmRegisterSvcService jpmRegisterSvcService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    MessageSource msg;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    CompanyService companyService;

    @Autowired
    ConstantDisplayService constantDisplayService;
    
    @Autowired
    public JcaConstantService jcaConstantService;
    
    @Autowired
    private ObjectMapper mapper;

    private ModelMapper modelMapper = new ModelMapper();

    private static final String SCREEN_FUNCTION_CODE = ItemConstant.ITEM_SCREEN_REGISTER_SVC;

    private static final String MAV_REGISTER_SVC_LIST = "/views/jpm-register-svc/jpm-register-svc-list.html";

    private static final String MAV_REGISTER_SVC_TABLE = "/views/jpm-register-svc/jpm-register-svc-table.html";

    private static final String OBJ_SEARCH = "search";

    private static final String OBJECT_REGISTER_LIST = "registerList";

    private static final String OBJ_CATEGORY_LIST = "categoryList";

    private static final String OBJ_BUSINESS_LIST = "businessList";

    private static final String FREE_FORM = "Free form";

    private static final String DEVICE_TYPE_DEFAULT = "any";

    /**
     * getRegisterSvcList
     * 
     * @param search
     * @param pageSizeParam
     * @param pageParam
     * @param locale
     * @return
     * @author HungHT
     */
    @GetMapping(value = { AppUrlConst.ROOT, AppUrlConst.LIST })
    public ModelAndView getRegisterSvcList(@ModelAttribute(value = OBJ_SEARCH) PPLRegisterSvcSearchDto search,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, Locale locale) {
        AccessLogger debugLogger = new AccessLogger();
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ModelAndView mav = new ModelAndView(MAV_REGISTER_SVC_LIST);
        String lang = locale.getLanguage();

        MessageList messageList = new MessageList();
        if (null == search.getCompanyId()) {
            search.setCompanyId(UserProfileUtils.getCompanyId());
        }

        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
                UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        mav.addObject("companyId", UserProfileUtils.getCompanyId());

        // Init company
        if (null == search.getCompanyName()) {
            search.setCompanyName(companyService.getSystemCodeByCompanyId(search.getCompanyId()));
        }

        // EfoForm type list
        ConstantDisplayDto condition = new ConstantDisplayDto();
        condition.setType(ConstantDisplayType.FORM_TYPE.toString());
        //List<ConstantDisplayDto> formTypeList = constantDisplayService.findConstantDisplayDtoByCondition(condition, locale);
        List<JcaConstantDto> formTypeList = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind("JCA_APP_BUSINESS", "BUSINESS_FORM_TYPE", locale.getLanguage());
        mav.addObject("formTypeList", formTypeList);

        // Init formType
        if (null == search.getFormType() && null != formTypeList && !formTypeList.isEmpty()) {
            //search.setFormType(formTypeList.get(0).getCat());
            search.setFormType(formTypeList.get(0).getCode());
        }

        // Get report list
        PPLResReportList reportList;
        List<PPLRegisterSvcDto> registerList = null;
        String msgContent = null;
        try {
            // free form
            if (null != search.getFormType() && ConstantCore.FREE_FORM_TYPE.equals(search.getFormType())) {
                PPLRegisterSvcDto registerSvcDto = initFreeForm(locale);
                List<PPLRegisterSvcDto> resultObj = new ArrayList<>();
                resultObj.add(registerSvcDto);
                reportList = new PPLResReportList();
                reportList.setResultObj(resultObj);
                reportList.setStatus(APIStatus.SUCCESS.toString());
            } else {
                PPLRegisterSvcSearchDto searchDtoCore = modelMapper.map(search, PPLRegisterSvcSearchDto.class);
//                reportList = jpmRegisterSvcService.getRegisterSvcList(searchDtoCore);
                ResReportList reportListCore = jpmRegisterSvcService.getRegisterSvcList(searchDtoCore);
                reportList = mapper.convertValue(reportListCore, PPLResReportList.class);
                modelMapper.map(searchDtoCore, search);
            }

            if (reportList != null && APIStatus.SUCCESS.toString().equalsIgnoreCase(reportList.getStatus())
                    && reportList.getResultObj() != null && reportList.getResultObj().size() > 0) {
                // Get result
                registerList = reportList.getResultObj();
            } else if (reportList != null && reportList.getObjErrors() != null && reportList.getObjErrors().size() > 0) {
                // Get error
                messageList.setStatus(Message.ERROR);
                msgContent = reportList.getObjErrors().get(0).getErrorDesc();
            } else {
                messageList.setStatus(Message.INFO);
                msgContent = ExecMessage.getErrorMessage(msg, "B109", locale).getErrorDesc();
            }
        } catch (AppException e) {
            messageList.setStatus(Message.ERROR);
            msgContent = ExecMessage.getErrorMessage(msg, e.getCode(), e.getArgs(), locale).getErrorDesc();
            logger.error(e.getMessage());
        }

        // Get category list
        List<Select2Dto> categoryList = categoryService.getCategoryListByCompanyId(null, search.getCompanyId(), true, lang);
        messageList.add(msgContent);
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        mav.addObject(OBJ_SEARCH, search);
        mav.addObject(OBJ_CATEGORY_LIST, categoryList);
        mav.addObject(OBJ_BUSINESS_LIST, null);
        mav.addObject(OBJECT_REGISTER_LIST, registerList);
        debugLogger.setEndTime();
//        AccessLogger.debug("%d,%s,%s,%s,%s,%s,%s,%s,%d,%s", UserProfileUtils.getAccountId(), UserProfileUtils.getUserNameLogin(), UserProfileUtils.getUserProfile().getFullname(), UserProfileUtils.getUserProfile().getSystemCode(), 
//                UserProfileUtils.getUserProfile().getCompanyName(), "JPM REGISTER SVC - GET LIST", "/jpm-register-svc/list",
//                debugLogger.getEnd(), debugLogger.getElapsedTime(), "PPL");
        return mav;
    }

    /**
     * getRegisterSvcTable
     * 
     * @param search
     * @param pageSizeParam
     * @param pageParam
     * @param locale
     * @return
     * @author HungHT
     */
    @PostMapping(value = AppUrlConst.AJAXLIST)
    public ModelAndView getRegisterSvcTable(@ModelAttribute(value = OBJ_SEARCH) PPLRegisterSvcSearchDto search,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, Locale locale) {
        AccessLogger debugLogger = new AccessLogger();
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ModelAndView mav = new ModelAndView(MAV_REGISTER_SVC_TABLE);
        String lang = locale.getLanguage();

        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgContent = null;
        List<PPLRegisterSvcDto> registerList = null;
        List<Select2Dto> categoryList = null;

        if (ResultStatus.SUCCESS.toInt() == search.getStatus()) {
            // Get report list
            PPLResReportList reportList;
            try {
                // free form
                if (null != search.getFormType() && ConstantCore.FREE_FORM_TYPE.equals(search.getFormType())) {
                    PPLRegisterSvcDto registerSvcDto = initFreeForm(locale);
                    List<PPLRegisterSvcDto> resultObj = new ArrayList<>();
                    resultObj.add(registerSvcDto);
                    reportList = new PPLResReportList();
                    reportList.setResultObj(resultObj);
                    reportList.setStatus(APIStatus.SUCCESS.toString());
                } else {
                    PPLRegisterSvcSearchDto searchDtoCore = modelMapper.map(search, PPLRegisterSvcSearchDto.class);
//                    reportList = jpmRegisterSvcService.getRegisterSvcList(searchDtoCore);
                    ResReportList reportListCore = jpmRegisterSvcService.getRegisterSvcList(searchDtoCore);
                    reportList = modelMapper.map(reportListCore, PPLResReportList.class);
                    modelMapper.map(searchDtoCore, search);
                }
                if (reportList != null && APIStatus.SUCCESS.toString().equalsIgnoreCase(reportList.getStatus())
                        && reportList.getResultObj() != null && reportList.getResultObj().size() > 0) {
                    // Get result
                    registerList = reportList.getResultObj();
                    messageList.setStatus(Message.SUCCESS);
                } else if (reportList != null && reportList.getObjErrors() != null && reportList.getObjErrors().size() > 0) {
                    // Get error
                    messageList.setStatus(Message.ERROR);
                    msgContent = reportList.getObjErrors().get(0).getErrorDesc();
                    messageList.add(msgContent);
                }
            } catch (AppException e) {
                messageList.setStatus(Message.ERROR);
                msgContent = ExecMessage.getErrorMessage(msg, e.getCode(), e.getArgs(), locale).getErrorDesc();
                messageList.add(msgContent);
                logger.error(e.getMessage());
            }

            // Get category list
            categoryList = categoryService.getCategoryListByCompanyId(null, search.getCompanyId(), true, lang);

            // Set message
            msgContent = search.getMessage();
        } else {
            // Set message
            messageList.setStatus(Message.ERROR);
            if (StringUtils.isNotBlank(search.getMessage())) {
                msgContent = search.getMessage();
            } else {
                msgContent = msg.getMessage(AppConstantCore.MSG_ERROR_REGISTER_SERVICE, new String[] { search.getServiceName() }, null);
            }
        }
        messageList.add(msgContent);
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        mav.addObject(OBJ_SEARCH, search);
        mav.addObject(OBJ_CATEGORY_LIST, categoryList);
        mav.addObject(OBJ_BUSINESS_LIST, null);
        mav.addObject(OBJECT_REGISTER_LIST, registerList);
        debugLogger.setEndTime();
//        AccessLogger.debug("%d,%s,%s,%s,%s,%s,%s,%s,%d,%s", UserProfileUtils.getAccountId(), UserProfileUtils.getUserNameLogin(), UserProfileUtils.getUserProfile().getFullname(), UserProfileUtils.getUserProfile().getSystemCode(), 
//                UserProfileUtils.getUserProfile().getCompanyName(), "JPM REGISTER SVC - AJAX LIST", "/jpm-register-svc/ajaxList",
//                debugLogger.getEnd(), debugLogger.getElapsedTime(), "PPL");
        return mav;
    }

    /**
     * registerSvc
     * 
     * @param register
     * @param bindingResult
     * @param locale
     * @param redirectAttributes
     * @param request
     * @return
     * @author HungHT
     */
    @PostMapping(value = AppUrlConst.REGISTER)
    @ResponseBody
    public ResultDto registerSvc(@ModelAttribute(value = OBJ_SEARCH) PPLRegisterSvcSearchDto register, BindingResult bindingResult,
            Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        AccessLogger debugLogger = new AccessLogger();
        ResultDto result = new ResultDto();
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            result.setStatus(0);
            result.setMessage(ExecMessage.getErrorMessage(msg, "B006", locale).getErrorDesc());
            debugLogger.setEndTime();
//            AccessLogger.debug("%d,%s,%s,%s,%s,%s,%s,%s,%d,%s", UserProfileUtils.getAccountId(), UserProfileUtils.getUserNameLogin(), UserProfileUtils.getUserProfile().getFullname(), UserProfileUtils.getUserProfile().getSystemCode(), 
//                    UserProfileUtils.getUserProfile().getCompanyName(), "JPM REGISTER SVC - REGISTER", "/jpm-register-svc/register",
//                    debugLogger.getEnd(), debugLogger.getElapsedTime(), "PPL");
            return result;
        }

        try {
            PPLRegisterSvcSearchDto searchDtoCore = modelMapper.map(register, PPLRegisterSvcSearchDto.class);
            result = jpmRegisterSvcService.registerSvc(searchDtoCore, locale);
        } catch (AppException e) {
            result.setStatus(0);
            result.setMessage(ExecMessage.getErrorMessage(msg, e.getCode(), e.getArgs(), locale).getErrorDesc());
            logger.error(e.getMessage());
        } catch (Exception e) {
            result.setStatus(0);
            logger.error(e.getMessage());
        }
        debugLogger.setEndTime();
//        AccessLogger.debug("%d,%s,%s,%s,%s,%s,%s,%s,%d,%s", UserProfileUtils.getAccountId(), UserProfileUtils.getUserNameLogin(), UserProfileUtils.getUserProfile().getFullname(), UserProfileUtils.getUserProfile().getSystemCode(), 
//                UserProfileUtils.getUserProfile().getCompanyName(), "JPM REGISTER SVC - REGISTER", "/jpm-register-svc/register",
//                debugLogger.getEnd(), debugLogger.getElapsedTime(), "PPL");
        return result;
    }

    @RequestMapping(value = "/get-by-company-id", method = { RequestMethod.GET })
    public ModelAndView svcTableByCompanyId(@RequestParam(value = "companyId", required = true) Long companyId,
            @RequestParam(value = "formType", required = false) String formType, Locale locale) {
        AccessLogger debugLogger = new AccessLogger();
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        ModelAndView mav = new ModelAndView(MAV_REGISTER_SVC_TABLE);

        String lang = locale.getLanguage();
        PPLRegisterSvcSearchDto search = new PPLRegisterSvcSearchDto();
        search.setCompanyId(companyId);
        search.setFormType(formType);
        // Init company
        if (null == search.getCompanyName()) {
            search.setCompanyName(companyService.getSystemCodeByCompanyId(search.getCompanyId()));
        }

        MessageList messageList = new MessageList();
        // Get report list
        PPLResReportList reportList;
        List<PPLRegisterSvcDto> registerList = null;
        String msgContent = null;
        try {
            // free form
            if (ConstantCore.FREE_FORM_TYPE.equals(formType)) {
                PPLRegisterSvcDto registerSvcDto = initFreeForm(locale);
                List<PPLRegisterSvcDto> resultObj = new ArrayList<PPLRegisterSvcDto>();
                resultObj.add(registerSvcDto);
                reportList = new PPLResReportList();
                reportList.setResultObj(resultObj);
                reportList.setStatus(APIStatus.SUCCESS.toString());
            } else {
                PPLRegisterSvcSearchDto searchCore = modelMapper.map(search, PPLRegisterSvcSearchDto.class);
                ResReportList reportListCore = jpmRegisterSvcService.getRegisterSvcList(searchCore);
                reportList = modelMapper.map(reportListCore, PPLResReportList.class);
                modelMapper.map(searchCore, search);
            }

            if (reportList != null && APIStatus.SUCCESS.toString().equalsIgnoreCase(reportList.getStatus())
                    && reportList.getResultObj() != null && reportList.getResultObj().size() > 0) {
                // Get result
                registerList = reportList.getResultObj();
            } else if (reportList != null && reportList.getObjErrors() != null && reportList.getObjErrors().size() > 0) {
                // Get error
                messageList.setStatus(Message.ERROR);
                msgContent = reportList.getObjErrors().get(0).getErrorDesc();
            } else {
                messageList.setStatus(Message.INFO);
                msgContent = ExecMessage.getErrorMessage(msg, "B109", locale).getErrorDesc();
            }
        } catch (AppException e) {
            messageList.setStatus(Message.ERROR);
            msgContent = ExecMessage.getErrorMessage(msg, e.getCode(), e.getArgs(), locale).getErrorDesc();
            logger.error(e.getMessage());
        }

        // Get category list
        List<Select2Dto> categoryList = categoryService.getCategoryListByCompanyId(null, search.getCompanyId(), true, lang);
        messageList.add(msgContent);
        mav.addObject(OBJ_SEARCH, search);
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        mav.addObject(OBJ_CATEGORY_LIST, categoryList);
        mav.addObject(OBJ_BUSINESS_LIST, null);
        mav.addObject(OBJECT_REGISTER_LIST, registerList);
        debugLogger.setEndTime();
//        AccessLogger.debug("%d,%s,%s,%s,%s,%s,%s,%s,%d,%s", UserProfileUtils.getAccountId(), UserProfileUtils.getUserNameLogin(), UserProfileUtils.getUserProfile().getFullname(), UserProfileUtils.getUserProfile().getSystemCode(), 
//                UserProfileUtils.getUserProfile().getCompanyName(), "JPM REGISTER SVC - GET BY COMPANY", "/jpm-register-svc/get-by-company-id",
//                debugLogger.getEnd(), debugLogger.getElapsedTime(), "PPL");
        return mav;
    }

    private PPLRegisterSvcDto initFreeForm(Locale locale) {
        PPLRegisterSvcDto registerSvcDto = new PPLRegisterSvcDto();
        ConstantDisplayDto condition = new ConstantDisplayDto();

        // get constantDisplay
        condition.setType(ConstantDisplayType.FORM_TYPE.toString());
        condition.setCat(ConstantCore.FREE_FORM_TYPE);

        // set value
        registerSvcDto.setReportName(FREE_FORM);
        String reportPath = FREE_FORM;//(null != constantDisplayDtos && !constantDisplayDtos.isEmpty())
//                ? constantDisplayDtos.get(0).getCatOfficialNameVi()
//                : FREE_FORM;
        registerSvcDto.setReportPath(reportPath);
        registerSvcDto.setServiceName(FREE_FORM);
        registerSvcDto.setDeviceType(DEVICE_TYPE_DEFAULT);
        registerSvcDto.setFormType(ConstantCore.FREE_FORM_TYPE);
        return registerSvcDto;
    }
}