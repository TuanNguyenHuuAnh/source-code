/*******************************************************************************
 * Class        SystemLogsController
 * Created date 2018/01/09
 * Lasted date  2018/01/09
 * Author       HUNGHT
 * Change log   2018/01/0901-00 HUNGHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.SystemLogsDto;
import vn.com.unit.ep2p.admin.enumdef.SystemLogsEnum;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.export.util.ExcelWithPasswordUtil;
import vn.com.unit.ep2p.export.util.SearchUtil;

/**
 * SystemLogsController
 * 
 * @version 01-00
 * @since 01-00
 * @author HUNGHT
 */
@RequestMapping("/system-logs")
@Controller
public class SystemLogsController {

	/** systemLogsService */
	@Autowired
	SystemLogsService systemLogsService;

	@Autowired
	SystemConfig systemConfig;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
    private CommonService comService;

	private static final String SCREEN_FUNCTION_CODE = RoleConstant.SYSTEM_LOG;

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

	/**
	 * writeSystemLogs
	 *
	 * @param functionCode
	 * @param logSummary
	 * @param logType
	 * @param logDetail
	 * @param request
	 * @author HUNGHT
	 */
	@RequestMapping(value = "/writeSystemLogs", method = { RequestMethod.POST })
	public void writeSystemLogs(@RequestParam(value = "functionCode", required = true) String functionCode,
			@RequestParam(value = "logSummary", required = true) String logSummary,
			@RequestParam(value = "logType", required = false, defaultValue = "400") int logType,
			@RequestParam(value = "logDetail", required = false) String logDetail,
			@RequestParam(value = "username", required = false) String username, HttpServletRequest request) {

		// Write system logs
		systemLogsService.writeSystemLogs(functionCode, logSummary, logType, logDetail, username, request);
	}

    @RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getSystemLog(@ModelAttribute(value = "systemLogsDto") SystemLogsDto systemLogsDto,
            @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
            @RequestParam(value = ConstantCore.PAGE) Optional<Integer> pageParam, Locale locale) {
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        ModelAndView mav;
        // Init page size
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        int page = pageParam.orElse(1);
        if (pageParam.orElse(0) == 0) {
            mav = new ModelAndView("/views/system-logs/system-logs-list.html");
            systemLogsDto.setFromDate(comService.getSystemDateTime());
//            systemLogsDto.setToDate(comService.getSystemDateTime());
            systemLogsDto.setCompanyId(UserProfileUtils.getCompanyId());
        } else {
            mav = new ModelAndView("/views/system-logs/system-logs-list-table.html");
        }
        SearchUtil.setSearchSelect(SystemLogsEnum.class, mav);
        mav.addObject("systemLogsDto", systemLogsDto);
        mav.addObject("pageWrapper", systemLogsService.search(systemLogsDto, page, pageSize));

        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        return mav;
    }

	@RequestMapping(value = "/export", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public void exportList(@ModelAttribute(value = "systemLogsDto") SystemLogsDto systemLogsDto, HttpServletRequest req,
			HttpServletResponse res, Locale locale) {
		ExcelWithPasswordUtil.setTokenToCookie(systemLogsDto.getToken(), req, res);
		try {
			// set password
			String passExport = systemLogsDto.getPassExport();
			if (StringUtils.isNotBlank(passExport)) {
				Biff8EncryptionKey.setCurrentUserPassword(passExport);
			}
			systemLogsService.exportExcel(systemLogsDto, res, locale);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/export-non-password", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public void exportListNonExport(@ModelAttribute(value = "systemLogsDto") SystemLogsDto systemLogsDto, HttpServletRequest req,
            HttpServletResponse res, Locale locale) {
        try {
            systemLogsService.exportExcelNonPassword(systemLogsDto, res, locale);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
