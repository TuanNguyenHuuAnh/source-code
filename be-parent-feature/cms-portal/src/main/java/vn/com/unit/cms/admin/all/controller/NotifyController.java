package vn.com.unit.cms.admin.all.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.cms.admin.all.controller.common.CmsCommonController;
import vn.com.unit.cms.admin.all.db2.service.Db2Service;
import vn.com.unit.cms.admin.all.service.CmsCommonSearchFillterService;
import vn.com.unit.cms.admin.all.service.NotifyImportService;
import vn.com.unit.cms.admin.all.service.NotifyService;
import vn.com.unit.cms.admin.all.util.CmsLanguageUtils;
import vn.com.unit.cms.core.constant.CmsPrefixCodeConstant;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.cms.core.dto.ConditionSearchDb2;
import vn.com.unit.cms.core.module.notify.dto.NotifyEditDto;
import vn.com.unit.cms.core.module.notify.dto.NotifyImportDto;
import vn.com.unit.cms.core.module.notify.dto.NotifySearchDto;
import vn.com.unit.cms.core.module.notify.dto.NotifySearchResultDto;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.constant.UrlConst;

@Controller
@RequestMapping(value = UrlConst.ROOT + UrlConst.NOTIFY)
public class NotifyController extends CmsCommonController<NotifySearchDto, NotifySearchResultDto, NotifyEditDto> {

	@Autowired
	private NotifyService notifyService;
	
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private NotifyImportService notifyImportService;
	@Autowired
	private Db2Service db2Service;
	
    @Autowired
    private MessageSource msg;

	@InitBinder
	public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
		if (null == request.getSession().getAttribute(ConstantCore.FORMAT_DATE)) {
			request.getSession().setAttribute("formatDate", systemConfig.getConfig(SystemConfig.DATE_PATTERN));
		}
		// The date format to parse or output your dates
		String patternDate = ConstantCore.FORMAT_DATE_FULL;
		SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);

		binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
	}

	private static final String VIEW_LIST = "views/CMS/all/notify/notify-list.html";
	private static final String VIEW_TABLE = "views/CMS/all/notify/notify-table.html";
	private static final String VIEW_EDIT = "views/CMS/all/notify/notify-edit.html";

	@Override
	public void initScreenEdit(ModelAndView mav, NotifyEditDto editDto, Locale locale) {
		ConditionSearchDb2 conditionSearchDb2 = new ConditionSearchDb2();
		List<Select2Dto> lstTerritory = db2Service.findAllTerritory(null);
		List<Select2Dto> lstRegion = db2Service.findAllRegion(null);
		List<Select2Dto> lstArea = db2Service.findAllArea(null);
		List<Select2Dto> lstOffice = db2Service.findAllOffice(null);
		List<Select2Dto> lstReporter = db2Service.findAllReporter(conditionSearchDb2);
		List<Select2Dto> lstType = db2Service.findAllAgenttype(conditionSearchDb2);
		PageWrapper<NotifyImportDto> pageWrapper = new PageWrapper<>();
		mav.addObject("lstTerritory",lstTerritory);
		mav.addObject("lstRegion",lstRegion);
		mav.addObject("lstArea",lstArea);
		mav.addObject("lstOffice",lstOffice);
		mav.addObject("lstReporter",lstReporter);
		mav.addObject("lstType",lstType);
		mav.addObject("pageWrapper",pageWrapper);
		if(	mav.getModel().get("searchDto") == null) {
			mav.addObject("searchDto",new NotifyImportDto());
		}
		// IP mode
		if (StringUtils.equalsIgnoreCase(editDto.getApplicableObject(), "IP")) {
			editDto.setNotifyCode("");
		}
	}
	
	@Override
    public String viewList() {
        return VIEW_LIST;
    }

    @Override
    public String viewListTable() {
        return VIEW_TABLE;
    }
	
	@Override
	public String getFunctionCode() {
		return "M_NOTIFYS";
	}

	@Override
	public String getTableForGenCode() {
		return "M_NOTIFYS";
	}

	@Override
	public String getPrefixCode() {
		return CmsPrefixCodeConstant.PREFIX_CODE_NOT;
	}

	@Override
	public String viewListSort() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String viewListSortTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String viewEdit() {
		// TODO Auto-generated method stub
		return VIEW_EDIT;
	}

	@Override
	public String getUrlByAlias() {
		return UrlConst.NOTIFY;
	}

	@Override
	public Class<NotifySearchResultDto> getClassSearchResult() {
		return NotifySearchResultDto.class;
	}

	@Override
	public boolean hasRoleForList() {
		return UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_NOTIFYS);
	}

	@Override
	public boolean hasRoleForEdit() {
		return UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_NOTIFYS_EDIT.concat(CoreConstant.COLON_EDIT));
	}

	@Override
	public boolean hasRoleForDetail() {
		return UserProfileUtils.hasRole(CmsRoleConstant.PAGE_DETAIL_NOTIFYS);
	}

	@Override
	public CmsCommonSearchFillterService<NotifySearchDto, NotifySearchResultDto, NotifyEditDto> getCmsCommonSearchFillterService() {
		return notifyService;
	}

	@Override
	public void validate(NotifyEditDto editDto, BindingResult bindingResult) {
		// TODO Auto-generated method stub

	}
	@Override
	public String getTitlePageList(Locale locale) {
		 return msg.getMessage("notify.title", null, locale);
	}
	
	@Override
	public boolean hasExportExcel() {
		return UserProfileUtils.hasRole(CmsRoleConstant.PAGE_DETAIL_NOTIFYS);
    }

	@RequestMapping(value = "/edit-import", method = RequestMethod.GET)
	public ModelAndView doGetEdit(@RequestParam(value = "searchDtoJson", required = false) String searchDtoJson, Locale locale,
								  @RequestParam(value = "id", required = false) Long id,
								  @RequestParam(value = "sessionKey", required = false) String sessionKey,
								  @RequestParam(value = "isError", required = false) boolean isError,
								  HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(viewEdit());

		// Start check role
		if (!hasRoleForEdit()) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		// End check role

		// Init List Language
		CmsLanguageUtils.initLanguageList(mav);

		// Start get data
		NotifyEditDto editDto = super.getData(id, new NotifySearchDto(), searchDtoJson, true, locale);
		mav.addObject(mavObjectEditName(), editDto);
		// End get data

		initScreenEdit(mav, editDto, locale);
		mav.addObject("hasRoleForEdit", hasRoleForEdit());
		mav.addObject("sessionKey",sessionKey);
		checkError(sessionKey, mav);
		mav.addObject("pageWrapper",getPageWraper(sessionKey, locale));

		return mav;
	}
	public void checkError(String sessionKey, ModelAndView mav) {
		if(StringUtils.isNotBlank(sessionKey)){
			int error = notifyImportService.countError(sessionKey);
			if (error > 0) {
				mav.addObject("isError",true);
			} else {
				mav.addObject("isError",false);
			}
		} else {
			mav.addObject("isError",false);
		}

	}
	public PageWrapper<NotifyImportDto> getPageWraper(String sessionKey, Locale locale) {
		PageWrapper<NotifyImportDto> pageWrapper = new PageWrapper<>();
		if (!StringUtils.isBlank(sessionKey)) {
			systemConfig.settingPageSizeList(5, pageWrapper, 0);
			int count = notifyImportService.countData(sessionKey);
			// pageWrapper
			int sizeOfPage = 5;
			List<NotifyImportDto> results = new ArrayList<NotifyImportDto>();
			if (count > 0) {
				int currentPage = pageWrapper.getCurrentPage();
				int startIndex = (currentPage - 1) * sizeOfPage;
				results = notifyImportService.getListData(startIndex, sessionKey, sizeOfPage);
				try {
					notifyImportService.parseData(results, true, locale);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			pageWrapper.setDataAndCount(results, count);
		}
		return pageWrapper;
	}

	@RequestMapping(value = "/submit-import", method = RequestMethod.POST)
	public ModelAndView doSubmit(@ModelAttribute NotifyEditDto condition, BindingResult bindingResult, Locale locale,
								 RedirectAttributes redirectAttributes) {

		// Start check role
		if (!hasRoleForEdit()) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
		// End check role

		ModelAndView mav = new ModelAndView(viewEdit());

		Long id = condition.getId();
		condition.setLanguageCode(locale.toString());
		
		//

		// Start validate
		validate(condition, bindingResult);
		if (bindingResult.hasErrors()) {
			if (id == null) {
				condition.setCode(null);
				condition.setId(id);
			}

			return super.returnError(condition, locale);
		}
		// End validate

		// Start do Save/Submit
		try {
			getCmsCommonSearchFillterService().saveOrUpdate(condition, locale);
		} catch (Exception e) {
			if (id == null) {
				condition.setCode(null);
				condition.setId(id);
			}

			return returnError(condition, locale);
		}
		// End do Save/Submit

		// Init message list
		MessageList messageList = new MessageList(Message.SUCCESS);
		String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
		messageList.add(msgInfo);
		redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);

		// success redirect edit page
		String viewName = UrlConst.REDIRECT.concat(UrlConst.ROOT).concat(getUrlByAlias()).concat(UrlConst.EDIT);
		redirectAttributes.addAttribute("id", condition.getId());
		redirectAttributes.addAttribute("searchDtoJson", condition.getSearchDto());
		mav.setViewName(viewName);

		return mav;
	}
//	@RequestMapping(value = "/test", method = RequestMethod.GET)
//	public void testFirebaseCloudDb(@ModelAttribute NotifyEditDto condition, BindingResult bindingResult, Locale locale,
//								 RedirectAttributes redirectAttributes) {
//		notifyService.test();
//	}
}
