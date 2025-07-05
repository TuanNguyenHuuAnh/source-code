package vn.com.unit.cms.admin.all.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.cms.admin.all.controller.common.CmsCommonController;
import vn.com.unit.cms.admin.all.service.CmsCommonSearchFillterService;
import vn.com.unit.cms.admin.all.service.EmulateResultService;
import vn.com.unit.cms.admin.all.service.EmulateService;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultEditDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultSearchDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateResultSearchResultDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateSearchDto;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.constant.UrlConst;

@Controller
@RequestMapping(UrlConst.EMULATE_RESULT)
public class EmulateResultController
		extends CmsCommonController<EmulateResultSearchDto, EmulateResultSearchResultDto, EmulateResultEditDto> {

	@Autowired
	private EmulateResultService emulateResultService;

	@Autowired
	private EmulateService emulateService;

	@Autowired
	private JcaConstantService jcaConstantService;

	private static final String VIEW_LIST = "views/CMS/all/emulate-result/emulate-result-list.html";
	private static final String VIEW_TABLE = "views/CMS/all/emulate-result/emulate-result-table.html";

	private static final String VIEW_LIST_SORT = "views/CMS/all/emulate-result/emulate-result-list-sort.html";
	private static final String VIEW_TABLE_SORT = "views/CMS/all/emulate-result/emulate-result-table-sort.html";

	@Override
	public void initScreenEdit(ModelAndView mav, EmulateResultEditDto editDto, Locale locale) {
	}

	@Override
	public void initScreenListSort(ModelAndView mav, EmulateResultSearchDto searchDto, Locale locale) {
		List<EmulateSearchDto> listContestType = emulateService.getType();
		mav.addObject("listContestType", listContestType);
	}

	@Override
	public String getFunctionCode() {
		return "M_EMULATE_RESULT";
	}

	@Override
	public String getTableForGenCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPrefixCode() {
		// TODO Auto-generated method stub
		return null;
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
	public String viewListSort() {
		return VIEW_LIST_SORT;
	}

	@Override
	public String viewListSortTable() {
		return VIEW_TABLE_SORT;
	}

	@Override
	public String viewEdit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUrlByAlias() {
		return UrlConst.EMULATE_RESULT;
	}

	@Override
	public Class<EmulateResultSearchResultDto> getClassSearchResult() {
		return EmulateResultSearchResultDto.class;
	}

	public boolean showButtonImport() {
		return UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_EMULATE_RESULT_IMPORT.concat(ConstantCore.COLON_EDIT));
	}

	@Override
	public boolean hasRoleForList() {
		return UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_EMULATE_RESULT);
	}

	@Override
	public boolean hasRoleForEdit() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean hasRoleForDetail() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public CmsCommonSearchFillterService<EmulateResultSearchDto, EmulateResultSearchResultDto, EmulateResultEditDto> getCmsCommonSearchFillterService() {
		return emulateResultService;
	}

	@Override
	public void validate(EmulateResultEditDto editDto, BindingResult bindingResult) {
		// TODO Auto-generated method stub

	}

	@Override
	public void customRedirectAttributesForSaveListSort(RedirectAttributes redirectAttributes,
			EmulateResultSearchDto searchDto, Locale locale) {
		super.customRedirectAttributesForSaveListSort(redirectAttributes, searchDto, locale);

		redirectAttributes.addAttribute("contestType", searchDto.getContestType());
	}
}
