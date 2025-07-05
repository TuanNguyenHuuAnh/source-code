package vn.com.unit.cms.admin.all.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.core.module.contact.dto.CmsContactDto;
import vn.com.unit.cms.core.module.contact.repository.CmsContactRepository;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.config.SystemConfig;
//import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
//import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.core.utils.Utility;

@Controller
@RequestMapping("cms-contact")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CmsContactController {

	@Autowired
	CmsContactRepository cmsContactRepository;

	@Autowired
	private SystemConfig systemConfig;

	private static final String CMS_CONTACT_LIST = "views/CMS/all/contact/contact-list";
	private static final String CMS_CONTACT_TABLE = "views/CMS/all/contact/contact-table";

	private void initDataTable(ModelAndView mav, Optional<Integer> pageSizeParam, int page, Locale locale) {
		int sizeOfPage = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
		PageWrapper<CmsContactDto> pageWrapper = new PageWrapper<CmsContactDto>(page, sizeOfPage);

		List<Integer> listPageSize = systemConfig.getListPageSize();
		pageWrapper.setListPageSize(listPageSize);
		pageWrapper.setSizeOfPage(sizeOfPage);

		String lang = locale.toString();
		int count = cmsContactRepository.countByCondition(lang); // dto, lang
		List<CmsContactDto> result = new ArrayList<>();
		if (count > 0) {
			// int currentPage = pageWrapper.getCurrentPage();
			// int startIndex = (currentPage - 1) * sizeOfPage;

			int offset = Utility.calculateOffsetSQL(page, sizeOfPage);
			result = cmsContactRepository.searchByCondition(offset, sizeOfPage, lang); // search, startIndex,
																						// sizeOfPage, lang
		}

		pageWrapper.setDataAndCount(result, count);

		mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);
	}

	@GetMapping(UrlConst.LIST)
	public ModelAndView list(Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {

//		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
//				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
//				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
//			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		}

		ModelAndView mav = new ModelAndView(CMS_CONTACT_LIST);

		initDataTable(mav, pageSizeParam, page, locale);

		return mav;
	}

	@PostMapping(UrlConst.AJAXLIST)
	@ResponseBody
	public ModelAndView ajaxList(@RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam,
			@RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {

//		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
//				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
//				&& !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
//			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//		}

		ModelAndView mav = new ModelAndView(CMS_CONTACT_TABLE);

		initDataTable(mav, pageSizeParam, page, locale);

		return mav;
	}

}
