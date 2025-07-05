/*******************************************************************************
 * Class        SystemConfigController
 * Created date 2019/01/22
 * Lasted date  2019/01/22
 * Author       VinhLT
 * Change log   2019/01/2201-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

import com.amazonaws.regions.Regions;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.exception.AppException;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JRepositoryService;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.dto.ResultDto;
import vn.com.unit.ep2p.admin.enumdef.ResultStatus;
import vn.com.unit.ep2p.admin.service.AppSystemConfigService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.PopupService;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.constant.AppSystemSettingKey;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.dto.AppSystemConfigDto;
import vn.com.unit.ep2p.enumdef.ComplexityEnum;
import vn.com.unit.ep2p.export.util.SearchUtil;
import vn.com.unit.ep2p.utils.ExecMessage;
import vn.com.unit.storage.entity.JcaRepository;

/**
 * SystemConfigController
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
@Controller
@RequestMapping(UrlConst.SYSTEM_CONFIG)
public class SystemConfigController {

	private static final Logger logger = LoggerFactory.getLogger(SystemConfigController.class);

	@Autowired
	private MessageSource msg;

	public static final String MASTER_DATA_SYSTEM_CONFIG = "/views/system-config/system-config.html";
	public static final String MASTER_DATA_SYSTEM_CONFIG_DETAIL = "/views/system-config/system-config-detail.html";
	public static final String DATE_FORMAT_DEFAULT = "dd/MM/yyyy";

	/** systemLogsService */
	@Autowired
	private SystemLogsService systemLogsService;

	@Autowired
	JcaConstantService jcaConstantService;

	@Autowired
	private JRepositoryService repositoryService;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	CompanyService companyService;

	@Autowired
	@Qualifier("jcaSystemConfigServiceImpl")
	JcaSystemConfigService jcaSystemConfigService;

	@Autowired
	PopupService popupService;

	private static final String SCREEN_FUNCTION_CODE = RoleConstant.SYSTEM_CONFIG;

	@Autowired
	private AppSystemConfigService appSystemConfigService;

	public AppSystemConfigDto buildSystemConfigAbtract(AppSystemConfigDto systemConfigDto, Long companyId) {
		if (systemConfigDto == null) {
			systemConfigDto = new AppSystemConfigDto();
		}
		return appSystemConfigService.buildSystemConfig(systemConfigDto, companyId);
	}

	/**
	 * Initial
	 * 
	 * @param binder
	 * @param locale
	 * @param request
	 * @author KhoaNA
	 */
	@InitBinder
	public void dateBinder(WebDataBinder binder, Locale locale, HttpServletRequest request) {
		// The date format to parse or output your dates
		String patternDate = (String) request.getSession().getAttribute(ConstantCore.FORMAT_DATE);
		SimpleDateFormat dateFormat;
		if (patternDate != null) {
			dateFormat = new SimpleDateFormat(patternDate);
		} else {
			dateFormat = new SimpleDateFormat(DATE_FORMAT_DEFAULT);
		}
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);
	}

	// overload old params
	/**
	 * @param bindingResult
	 * @param model
	 * @param req
	 * @param resp
	 * @param locale
	 * @author PhucNV
	 * @return
	 */
	public ModelAndView buildConfig(BindingResult bindingResult, Model model, HttpServletRequest req,
			HttpServletResponse resp, Locale locale) {
		return this.buildConfig(req, resp, locale);
	}

	/**
	 * Build view System Configure
	 * 
	 * @return ModelAndView
	 * @author KhoaNA
	 */
	@RequestMapping(value = UrlConst.CONFIG, method = RequestMethod.GET)
	public ModelAndView buildConfig(HttpServletRequest req, HttpServletResponse resp, Locale locale) {

		ModelAndView mav;
		mav = new ModelAndView(MASTER_DATA_SYSTEM_CONFIG);
		AppSystemConfigDto systemConfigDto = new AppSystemConfigDto();
		try {
			// Security for this page.
//          if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
//                  && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
//                  && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
//              return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//          }

			systemConfigDto = buildSystemConfigAbtract(systemConfigDto, UserProfileUtils.getCompanyId());
			SearchUtil.setSearchSelect(ComplexityEnum.class, mav);

			mav.addObject("messageLst", systemConfigDto.getMessageList());

			initDataScreen(mav, systemConfigDto.getCompanyId());

			// Add company list
			List<Select2Dto> companyList = companyService.findByListCompanyId(Arrays.asList(1L, 2L), false);
			mav.addObject("companyList", companyList);
			mav.addObject("lstRegion", getListRegion());
			mav.addObject("lstPopup", getListPopup());

		} catch (Exception e) {
			logger.error("##SystemConfigController_GET##", e);
			MessageList messageLst = new MessageList(Message.ERROR);
			mav.addObject("messageLst", messageLst);
		}

		mav.addObject("systemConfigDto", systemConfigDto);
		return mav;

	}
	public List<Select2Dto> getListRegion(){
		Regions[] regions =  Regions.values();
		List<Select2Dto> result = new LinkedList<Select2Dto>();
		for (Regions region : regions) {
			Select2Dto dto = new Select2Dto();
			dto.setId(region.getName());
			dto.setName(region.getDescription());
			result.add(dto);
		}
		return result;
	}
	public List<Select2Dto> getListPopup(){
		return popupService.getListPopup();
	}
	/**
	 * buildConfigAjax
	 * 
	 * @param companyId
	 * @param req
	 * @param resp
	 * @param locale
	 * @return
	 * @author HungHT
	 */
	@RequestMapping(value = UrlConst.AJAXLIST, method = RequestMethod.POST)
	public ModelAndView buildConfigAjax(@RequestParam(value = "companyId", required = true) Long companyId,
			HttpServletRequest req, HttpServletResponse resp, Locale locale) {

		ModelAndView mav;
		mav = new ModelAndView(MASTER_DATA_SYSTEM_CONFIG_DETAIL);
		AppSystemConfigDto systemConfigDto = null;
		try {
			// Security for this page.
//            if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
//                    && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
//                    && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
//                return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//            }
			systemConfigDto = buildSystemConfigAbtract(systemConfigDto, companyId);
			SearchUtil.setSearchSelect(ComplexityEnum.class, mav);

			mav.addObject("messageLst", systemConfigDto.getMessageList());

			initDataScreen(mav, systemConfigDto.getCompanyId());

		} catch (Exception e) {
			logger.error("##SystemConfigController_GET##", e);
			MessageList messageLst = new MessageList(Message.ERROR);
			mav.addObject("messageLst", messageLst);
		}

		mav.addObject("systemConfigDto", systemConfigDto);
		return mav;
	}

	/**
	 * buildAjaxFirebaseConfig
	 * 
	 * @param companyId
	 * @return
	 * @author datnv
	 */
	@RequestMapping(value = UrlConst.AJAX_FIREBASE_CONFIG, method = RequestMethod.GET)
	@ResponseBody
	public String buildAjaxFirebaseConfig() {
		String result = "";
		// T systemConfigDto = null;
		HashMap<String, String> firebaseConfig = new HashMap<String, String>();
		try {

			String firebaseWebApiKey = jcaSystemConfigService.getValueByKey(AppSystemSettingKey.FIREBASE_WEB_API_KEY,
					UserProfileUtils.getCompanyId());
			firebaseConfig.put("firebaseWebApiKey", firebaseWebApiKey);

			String firebaseAuthDomain = jcaSystemConfigService.getValueByKey(AppSystemSettingKey.FIREBASE_AUTH_DOMAIN,
					UserProfileUtils.getCompanyId());
			firebaseConfig.put("firebaseAuthDomain", firebaseAuthDomain);

			String firebaseDatabaseUrl = jcaSystemConfigService.getValueByKey(AppSystemSettingKey.FIREBASE_DATABASE_URL,
					UserProfileUtils.getCompanyId());
			firebaseConfig.put("firebaseDatabaseUrl", firebaseDatabaseUrl);

			String firebaseProjectId = jcaSystemConfigService.getValueByKey(AppSystemSettingKey.FIREBASE_PROJECT_ID,
					UserProfileUtils.getCompanyId());
			firebaseConfig.put("firebaseProjectId", firebaseProjectId);

			String firebaseStoreBucket = jcaSystemConfigService
					.getValueByKey(AppSystemSettingKey.FIREBASE_STORAGE_BUCKET, UserProfileUtils.getCompanyId());
			firebaseConfig.put("firebaseStoreBucket", firebaseStoreBucket);

			String firebaseMessageId = jcaSystemConfigService.getValueByKey(AppSystemSettingKey.FIREBASE_MESSAGE_ID,
					UserProfileUtils.getCompanyId());
			firebaseConfig.put("firebaseMessageId", firebaseMessageId);

			String firebaseAppId = jcaSystemConfigService.getValueByKey(AppSystemSettingKey.FIREBASE_APP_ID,
					UserProfileUtils.getCompanyId());
			firebaseConfig.put("firebaseAppId", firebaseAppId);

			String firebaseMeasurementId = jcaSystemConfigService
					.getValueByKey(AppSystemSettingKey.FIREBASE_MEASUREMENT_ID, UserProfileUtils.getCompanyId());
			firebaseConfig.put("firebaseMeasurementId", firebaseMeasurementId);

			String firebasePublicVapidKey = jcaSystemConfigService
					.getValueByKey(AppSystemSettingKey.FIREBASE_PUBLIC_VAPID_KEY, UserProfileUtils.getCompanyId());
			firebaseConfig.put("firebasePublicVapidKey", firebasePublicVapidKey);

			// systemConfigDto = buildSystemConfigAbtract(systemConfigDto, companyId);
			result = CommonJsonUtil.convertObjectToJsonString(firebaseConfig);
		} catch (Exception e) {
			return "build ajax firebase config fail";
		}

		return result;
	}

	/**
	 * Update System Configure
	 * 
	 * @param systemConfigDto
	 * @param bindingResult
	 * @param locale
	 * @return ModelAndView
	 * @author KhoaNA
	 * @throws Exception
	 */
//    @SuppressWarnings("unchecked")
	@RequestMapping(value = UrlConst.CONFIG, method = RequestMethod.POST)
	public ModelAndView updateConfig(@Valid @ModelAttribute("systemConfigDto") AppSystemConfigDto systemConfigDto,
			BindingResult bindingResult, Locale locale, HttpServletRequest request,
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "status", required = false) Long status) throws Exception {
		if (null == status) {
			ModelAndView mav = new ModelAndView(MASTER_DATA_SYSTEM_CONFIG_DETAIL);
	
			// Write system logs
			systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Update System Config", "Update System Config",
					request);
	
			// Security for this page.
	//      if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
	//              && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
	//              && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
	//          return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
	//      }
			// Init message list
			MessageList messageLst = new MessageList(Message.SUCCESS);
	
			MessageList msgErrorLst = appSystemConfigService.validateSystemConfig(systemConfigDto, locale);
	
			if (bindingResult.hasErrors() || !msgErrorLst.isEmpty()) {
				// Add message error
				messageLst.setStatus(Message.ERROR);
	
				String msgError = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
				messageLst.add(msgError);
				if (!msgErrorLst.isEmpty()) {
					messageLst.add(msgErrorLst);
				}
	
				mav.addObject("messageLst", messageLst);
				mav.addObject("systemConfigDto", systemConfigDto);
				return mav;
			}
	
			updateSystemConfigAbstract(systemConfigDto);
	
			SearchUtil.setSearchSelect(ComplexityEnum.class, mav);
	
			String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
			messageLst.add(msgInfo);
	
			initDataScreen(mav, systemConfigDto.getCompanyId());
	
			mav.addObject("messageLst", messageLst);
			mav.addObject("systemConfigDto", systemConfigDto);
			return mav;
		} else {
			ModelAndView mav;
			mav = new ModelAndView(MASTER_DATA_SYSTEM_CONFIG);
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
	                msgContent = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
	            }
	        }
	        messageList.add(msgContent);
	        mav.addObject(ConstantCore.MSG_LIST, messageList);
			return mav;
	    }
		
	}
    @PostMapping(value = UrlConst.SAVE)
    @ResponseBody
	public ResultDto updateFileConfig(@Valid @ModelAttribute("systemConfigDto") AppSystemConfigDto systemConfigDto,
			BindingResult bindingResult, Locale locale, HttpServletRequest request) throws Exception {

		// Write system logs
		systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Update System Config", "Update System Config",
				request);

		// Security for this page.
//      if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
//              && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
//              && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
//          return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//      }
		// Init message list
		ResultDto result = new ResultDto();
		MessageList messageLst = new MessageList(Message.SUCCESS);

		MessageList msgErrorLst = appSystemConfigService.validateSystemConfig(systemConfigDto, locale);

		if (bindingResult.hasErrors() || !msgErrorLst.isEmpty()) {
			// Add message error
			messageLst.setStatus(Message.ERROR);

			String msgError = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
			messageLst.add(msgError);
			if (!msgErrorLst.isEmpty()) {
				messageLst.add(msgErrorLst);
			}

	            result.setStatus(0);
	            result.setMessage(ExecMessage.getErrorMessage(msg, "B006", locale).getErrorDesc());
	            return result;
		}

		try {
			updateSystemConfigAbstract(systemConfigDto);
			result.setStatus(ResultStatus.SUCCESS.toInt());
	        result.setMessage(msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale));
	        result.setId(systemConfigDto.getCompanyId());
        } catch (AppException e) {
            result.setStatus(0);
            result.setMessage(ExecMessage.getErrorMessage(msg, e.getCode(), e.getArgs(), locale).getErrorDesc());
            logger.error(e.getMessage());
        } catch (Exception e) {
            result.setStatus(0);
            logger.error(e.getMessage());
        }
        return result;
	}

//    @SuppressWarnings("unchecked")
	public void updateSystemConfigAbstract(AppSystemConfigDto systemConfigDto) throws Exception {
		appSystemConfigService.updateSystemConfig(systemConfigDto);
	}

	/**
	 * initDataScreen
	 * 
	 * @param mav type ModelAndView
	 * @author KhoaNA
	 */
	public void initDataScreen(ModelAndView mav, Long companyId) {
		List<JcaConstantDto> listOptionType = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind("JCA_APP_SLA",
				"SLA_SEND_TYPE", UserProfileUtils.getLanguage());
		mav.addObject("listOptionType", listOptionType);
		List<JcaRepository> listRepo = repositoryService.getAllRepositoryByCompanyId(companyId, null);
		mav.addObject("listRepo", listRepo);
		List<Integer> listPageSize = systemConfig.getListPageSize();
		mav.addObject("listPageSize", listPageSize);
		mav.addObject("lstRegion", getListRegion());
	}
}
