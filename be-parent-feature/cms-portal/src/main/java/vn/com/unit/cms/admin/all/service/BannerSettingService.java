package vn.com.unit.cms.admin.all.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.repository.query.Param;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.core.module.banner.dto.setting.BannerSettingDto;
import vn.com.unit.cms.core.module.banner.dto.setting.BannerSettingEditDto;
import vn.com.unit.cms.core.module.banner.dto.setting.BannerSettingPageDto;
import vn.com.unit.cms.core.module.banner.dto.setting.BannerSettingSearchDto;
import vn.com.unit.cms.core.module.banner.entity.Banner;
import vn.com.unit.cms.core.module.banner.entity.BannerLanguage;
import vn.com.unit.cms.core.module.banner.entity.HomePageSetting;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.ep2p.core.dto.CommonSearchFilterDto;

/**
 * BannerSettingService
 *
 * @version 01-00
 * @since 01-00
 * @author longdch
 */
public interface BannerSettingService
		extends CmsCommonSearchFillterService<BannerSettingDto, BannerSettingSearchDto, BannerSettingEditDto> {

	/**
	 * find Banner by id
	 *
	 * @param id
	 * @return
	 * @author longdch
	 */
	public HomePageSetting findBannerById(Long id);

//    public List<BannerLanguage> findBannerLanguageByBannerId(Long bid);

	/**
	 * initLanguageList
	 *
	 * @param mav
	 * @author longdch
	 */
	public void initLanguageList(ModelAndView mav);

//	List<CommonSearchFilterDto> initListSearchFilter(BannerSettingDto searchDto, Locale locale);

	/**
	 * get BannerEdit
	 *
	 * @param bannerId
	 * @return BannerSettingEditDto
	 * @author longdch
	 */
	public BannerSettingEditDto getBannerEdit(Long bannerId, Locale locale);

	/**
	 * addOrEdit
	 *
	 * @param bannerEdit
	 * @author longdch
	 * @throws IOException
	 * @throws Exception
	 */
	public boolean addOrEdit(BannerSettingEditDto bannerEdit, String requestToken, HttpServletRequest request,
			Locale locale) throws Exception;

	/**
	 * find BannerPage
	 *
	 * @param bannerPage
	 * @return
	 * @author longdch
	 */
	public List<Select2Dto> findByBannerPage(String bannerPage, String lang);

	/**
	 * delete BannerSetting by entity
	 *
	 * @param banner
	 * @author longdch
	 */
	public void deleteBanner(HomePageSetting banner);

	// public Integer findDateInto(@Param("checkStartDate") Date startDate,
	// @Param("checkEndDate") Date endDate, @Param("bannerPage") String
	// bannerPage,@Param("bannerType") String bannerType);

	public HomePageSetting findDateInto(String bannerPage, String bannerType,Long id, String channel);



}
