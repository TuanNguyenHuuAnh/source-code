package vn.com.unit.ep2p.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonSearchUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.AuthorityDetailDto;
import vn.com.unit.ep2p.admin.service.AuthorityDetailService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.ConstantDisplayService;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.enumdef.AuthorityReportSearchEnum;
import vn.com.unit.ep2p.export.util.ExcelWithPasswordUtil;

@Controller
@RequestMapping(UrlConst.REPORT_AUTH)
public class AuthoriryDetailController {

	/** systemLogsService */
	@Autowired
	AuthorityDetailService authorityDetailService;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	ConstantDisplayService constantDisplayService;

	@Autowired
	CompanyService companyService;

	private static final String SCREEN_FUNCTION_CODE = RoleConstant.AUTHORITY_DETAIL;

	@RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getSystemLog(@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam,
			@ModelAttribute(value = "authorityDetailDto") AuthorityDetailDto authorityDetailDto, Locale locale) {
		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		ModelAndView mav;
		int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
		int page = pageParam.orElse(1);
		
		if(authorityDetailDto.getCompanyId() == null)
		{
			authorityDetailDto.setCompanyId(UserProfileUtils.getCompanyId());
		}
		
		if (pageParam.orElse(0) == 0) {
			mav = new ModelAndView("/views/authority-detail/authority-detail.html");
		} else {
			mav = new ModelAndView("/views/authority-detail/authority-detail-table.html");
		}

		// Set init search
		CommonSearchUtil.setSearchSelect(AuthorityReportSearchEnum.class, mav);

		mav.addObject("authorityDetailDto", authorityDetailDto);
		mav.addObject("pageWrapper", authorityDetailService.search(authorityDetailDto,page,pageSize, locale));

		// Add company list
		List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
		mav.addObject("companyList", companyList);
		return mav;
	}

//	public void setSearchSelectFromDB(String kind, String type, ModelAndView mav) {
//		List<JcaConstant> lstConstantDisplay = constantDisplayService.findByTypeAndKind(type, kind);
//		List<SelectItem> fieldSelect = new ArrayList<>();
//		for (ConstantDisplay constantDisplay : lstConstantDisplay) {
//			if (constantDisplay.getDelFlg() == 0) {
//				SelectItem item = new SelectItem(constantDisplay.getCat(), constantDisplay.getCode());
//				fieldSelect.add(item);
//			}
//		}
//		mav.addObject("fieldSelect", fieldSelect);
//	}

	@InitBinder
	public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
		request.getSession().setAttribute("formatDate", systemConfig.getConfig(SystemConfig.DATE_PATTERN));
		// The date format to parse or output your dates
		String patternDate = (String) request.getSession().getAttribute(ConstantCore.FORMAT_DATE);
		SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);

		binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
	}

	@RequestMapping(value = "/export", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public void exportList(@ModelAttribute(value = "authorityDetailDto") AuthorityDetailDto authorityDetailDto,
			HttpServletRequest request, HttpServletResponse response, Locale locale) {

		ExcelWithPasswordUtil.setTokenToCookie(authorityDetailDto.getToken(), request, response);
		try {
			if (authorityDetailDto.getPassExport() != null && !authorityDetailDto.getPassExport().equals("")) {
				// set password
				Biff8EncryptionKey.setCurrentUserPassword(authorityDetailDto.getPassExport());
			}

			authorityDetailService.exportExcel(authorityDetailDto, response, locale);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
