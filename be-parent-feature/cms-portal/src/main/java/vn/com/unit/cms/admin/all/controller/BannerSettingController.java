package vn.com.unit.cms.admin.all.controller;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.controller.common.CmsCommonController;
import vn.com.unit.cms.admin.all.dto.BannerEditDto;
import vn.com.unit.cms.admin.all.service.BannerService;
import vn.com.unit.cms.admin.all.service.BannerSettingService;
import vn.com.unit.cms.admin.all.service.CmsCommonSearchFillterService;
import vn.com.unit.cms.admin.all.util.CmsLanguageUtils;
import vn.com.unit.cms.admin.all.validator.BannerSettingEditValidator;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.cms.core.module.banner.dto.setting.BannerSettingDto;
import vn.com.unit.cms.core.module.banner.dto.setting.BannerSettingEditDto;
import vn.com.unit.cms.core.module.banner.dto.setting.BannerSettingSearchDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.core.ers.service.Select2DataService;

@Controller
@RequestMapping(value = UrlConst.ROOT + UrlConst.BANNER + UrlConst.ROOT + UrlConst.HOMEPAGE_SETTING)
public class BannerSettingController
		extends CmsCommonController<BannerSettingDto, BannerSettingSearchDto, BannerSettingEditDto> {

	@Autowired
	private BannerSettingService bannerSettingService;

	@Autowired
	private BannerSettingEditValidator bannerSettingEditValidator;

	@Autowired
	private Select2DataService select2DataService;

	@Autowired
	private BannerService bannerService;
	
    @Autowired
    private JcaConstantService jcaConstantService;

	private static final String VIEW_LIST = "views/CMS/all/banner-setting/banner-setting-list.html";
	private static final String VIEW_TABLE = "views/CMS/all/banner-setting/banner-setting-table.html";
	private static final String VIEW_EDIT = "views/CMS/all/banner-setting/banner-setting-edit.html";
	private static final String HOMEPAGE_EDIT_IMAGE = "views/CMS/all/banner-setting/banner-setting-edit-img.html";

	@RequestMapping(value = "/ajax/loadBannerImage", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView ajaxList(@RequestParam(value = "id", required = true) Long bannerId, Locale locale) {
		ModelAndView modelAndView = new ModelAndView(HOMEPAGE_EDIT_IMAGE);

		BannerEditDto bannerEditDto = bannerService.getBannerEdit(bannerId, locale);
		if (null == bannerId || bannerId == 0) {
			bannerEditDto = new BannerEditDto();
		}
		modelAndView.addObject("bannerEditDto", bannerEditDto);
		return modelAndView;
	}

	@RequestMapping(value = "change/banner-type", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getBannerList(@RequestParam(value = "bannerDevice", required = false) String bannerDevice,
			@RequestParam(value = "bannerType", required = false) String bannerType, Locale locale) {
		List<Select2Dto> listBanner = select2DataService.getListBanner( bannerDevice, bannerType);
		String stringJson = CommonJsonUtil.convertObjectToJsonString(listBanner);
		return stringJson;
	}

	@Override
	public void customDateFormat(ModelAndView mav) {
		super.customDateFormat(mav);
		mav.addObject("dateFormat", "FULL_DATE");
	}

	private void inItScreenEdit(ModelAndView mav, BannerSettingEditDto bannerEdit, Locale locale) {
		CmsLanguageUtils.initLanguageList(mav);

		// Dữ liệu lúc đầu khi vào màn hình thêm mới
		if (StringUtils.isBlank(bannerEdit.getBannerType())) {
			bannerEdit.setBannerType("1");
		}

		List<Select2Dto> listBannerDesktop = select2DataService.getListBanner("1", bannerEdit.getBannerType());
		mav.addObject("listBannerDesktop", listBannerDesktop);

		List<Select2Dto> listBannerMobile = select2DataService.getListBanner("2", bannerEdit.getBannerType());
		mav.addObject("listBannerMobile", listBannerMobile);

		List<Select2Dto> listDataPage = bannerSettingService.findByBannerPage("PAGE_TYPE", locale.toString());
		mav.addObject("listDataPage", listDataPage);

		List<Select2Dto> listDataType = select2DataService.getConstantData("M_BANNER", "TYPE", locale.toString());
		mav.addObject("listDataType", listDataType);

		List<Select2Dto> listDataPro = select2DataService.getConstantData("HOMEPAGE", "PROPERTIES", locale.toString());
		mav.addObject("listDataPro", listDataPro);

		List<Select2Dto> listDataEff = select2DataService.getConstantData("HOMEPAGE", "EFFECT", locale.toString());
		mav.addObject("listDataEff", listDataEff);
//		

		// check statusCode published
		boolean checkPublishedAndHaveData = false;
		mav.addObject("checkPublishedAndHaveData", checkPublishedAndHaveData);
		
        List<JcaConstantDto> channels = jcaConstantService.getListJcaConstantDisplayByType("CHANNEL");
        mav.addObject("channels", channels);

//		mav.addObject(super.mavObjectEditName(), bannerEdit);

	}

	@Override
	public void initScreenEdit(ModelAndView mav, BannerSettingEditDto editDto, Locale locale) {
		inItScreenEdit(mav, editDto, locale);

	}

	@Override
	public String getFunctionCode() {
		return "M_BANNER_SETTING";

	}

	@Override
	public String getTableForGenCode() {
		return null;
	}

	@Override
	public String getPrefixCode() {
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
		return null;
	}

	@Override
	public String viewListSortTable() {
		return null;
	}

	@Override
	public String viewEdit() {
		return VIEW_EDIT;
	}

	@Override
	public String getUrlByAlias() {
		return UrlConst.BANNER + UrlConst.ROOT + UrlConst.HOMEPAGE_SETTING;

	}

	@Override
	public Class<BannerSettingSearchDto> getClassSearchResult() {
		return BannerSettingSearchDto.class;

	}

	@Override
	public boolean hasRoleForList() {
		return UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_BANNER_SETTING);
	}

	@Override
	public boolean hasRoleForEdit() {
		return UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_BANNER_EDIT_SETTING.concat(CoreConstant.COLON_EDIT));

	}

	@Override
	public boolean hasRoleForDetail() {
		return UserProfileUtils.hasRole(CmsRoleConstant.PAGE_DETAIL_BANNER_SETTING);
	}

	@Override
	public CmsCommonSearchFillterService<BannerSettingDto, BannerSettingSearchDto, BannerSettingEditDto> getCmsCommonSearchFillterService() {
		return bannerSettingService;

	}

	@Override
	public void validate(BannerSettingEditDto editDto, BindingResult bindingResult) {
		bannerSettingEditValidator.validate(editDto, bindingResult);
	}

	@Override
	public String mavObjectEditName() {
		return "bannerSettingEditDto";
	}
}
