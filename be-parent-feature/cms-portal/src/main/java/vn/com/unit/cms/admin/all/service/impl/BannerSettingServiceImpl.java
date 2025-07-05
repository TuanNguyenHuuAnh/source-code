package vn.com.unit.cms.admin.all.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.constant.ConstDispType;
import vn.com.unit.cms.admin.all.controller.NotifyController;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.service.BannerSettingService;
import vn.com.unit.cms.admin.all.util.CmsLanguageUtils;
import vn.com.unit.cms.admin.all.util.CommonSearchFilterUtils;
import vn.com.unit.cms.core.module.banner.dto.BannerLanguageDto;
import vn.com.unit.cms.core.module.banner.dto.setting.BannerSettingDto;
import vn.com.unit.cms.core.module.banner.dto.setting.BannerSettingEditDto;
import vn.com.unit.cms.core.module.banner.dto.setting.BannerSettingSearchDto;
import vn.com.unit.cms.core.module.banner.entity.HomePageSetting;
import vn.com.unit.cms.core.module.banner.repository.BannerLanguageRepository;
import vn.com.unit.cms.core.module.banner.repository.BannerSettingRepository;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.core.service.JcaDatatableConfigService;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.exception.BusinessException;
import vn.com.unit.ep2p.core.dto.CommonSearchFilterDto;
import vn.com.unit.ep2p.core.ers.service.Select2DataService;

/**
 * BannerSettingServiceImpl
 *
 * @version 01-00
 * @since 01-00
 * @author longdch
 */
@Service(value = "bannerSettingServiceImpl")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class BannerSettingServiceImpl implements BannerSettingService {
	private static final Logger logger = LoggerFactory.getLogger(BannerSettingServiceImpl.class);

	@Autowired
	private BannerSettingRepository bannerSettingRepository;

	@Autowired
	private BannerLanguageRepository bannerLanguageRepository;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	ServletContext servletContext;

	@Autowired
	private CmsCommonService cmsCommonService;

	@Autowired
	private JcaDatatableConfigService jcaDatatableConfigService;

	@Autowired
	private CommonSearchFilterUtils commonSearchFilterUtils;

	@Autowired
	private MessageSource msg;

	@Autowired
	private JcaConstantService jcaConstantService;

	@Autowired
	private Select2DataService select2DataService;
	
	private Date checkMaxDate(Date date) {
        if(!ObjectUtils.isNotEmpty(date)) {
                try {
                    Date maxDate = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/9999");
                    return maxDate;
                } catch (ParseException e) {
                    logger.error("Exception ", e);
                }
            }
        return date;
    }
	
	private List<BannerLanguageDto> generateBannerLanguage(String idList, String lang) {
		List<BannerLanguageDto> result = new ArrayList<BannerLanguageDto>();
		if (StringUtils.isBlank(idList)) {
			return result;
		}
		String[] lst = idList.split(",");
		for (String id : lst) {
			if (StringUtils.isNotEmpty(id)) {
				BannerLanguageDto bannerLang = bannerLanguageRepository.findByBannerIdAndLangueCode(Long.valueOf(id));
				if (bannerLang != null) {
					result.add(bannerLang);
				}
			}
		}
		return result;
	}

	@Override
	public List<BannerSettingSearchDto> getListByCondition(BannerSettingDto searchDto, Pageable pageable) {
		List<BannerSettingSearchDto> datas = bannerSettingRepository.findByBannerSearchDto(searchDto, pageable)
				.getContent();

		for (BannerSettingSearchDto homepageSetting : datas) {
			if (homepageSetting.getBannerTopList() == null) {
				homepageSetting.setBannerTopList(new ArrayList<>());
				homepageSetting.setBannerTopList(
						generateBannerLanguage(homepageSetting.getMBannerTopId(), searchDto.getLanguageCode()));
			}
			if (homepageSetting.getBannerTopMobileList() == null) {
				homepageSetting.setBannerTopMobileList(new ArrayList<>());
				homepageSetting.setBannerTopMobileList(
						generateBannerLanguage(homepageSetting.getMBannerTopMobileId(), searchDto.getLanguageCode()));
			}
		}

		return datas;
	}

	@Override
	public int countListByCondition(BannerSettingDto searchDto) {
		return bannerSettingRepository.countByBannerSearchDto(searchDto);

	}

	@Override
	public ServletContext getServletContext() {
		return servletContext;

	}

	@Override
	public SystemConfig getSystemConfig() {
		return systemConfig;

	}

	@Override
	public CmsCommonService getCmsCommonService() {
		return cmsCommonService;

	}

	@Override
	public JcaDatatableConfigService getJcaDatatableConfigService() {
		return jcaDatatableConfigService;

	}

	@Override
	public BannerSettingEditDto getEditDtoById(Long id, Locale locale) {
		return getBannerEdit(id, locale);

	}

	@Override
	public void saveOrUpdate(BannerSettingEditDto editDto, Locale locale) throws Exception {
		addOrEdit(editDto, editDto.getRequestToken(), null, locale);

	}

	@Override
	public void deleteDataById(Long id) throws Exception {
		HomePageSetting banner = findBannerById(id);
		deleteBanner(banner);
	}

	@Override
	public void deleteBanner(HomePageSetting banner) {
		// user name login
		String userName = UserProfileUtils.getUserNameLogin();

//        bannerSettingRepository.deleteHomePageByBannerId(banner.getId(), new Date(), userName);

		banner.setDeleteDate(new Date());
		banner.setDeleteBy(userName);
		bannerSettingRepository.save(banner);
	}

	@Override
	public HomePageSetting findBannerById(Long id) {
		return bannerSettingRepository.findOne(id);
	}

//    @Override
//    public List<BannerLanguage> findBannerLanguageByBannerId(Long bid) {
//        return null;
//    }

	@Override
	public List<BannerSettingSearchDto> getListForSort(BannerSettingDto searchDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSortAll(BannerSettingDto searchDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public Class<?> getEnumColumnForExport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTemplateNameForExport(Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initLanguageList(ModelAndView mav) {
		CmsLanguageUtils.initLanguageList(mav);

	}

//
//    @Override
//    public List<CommonSearchFilterDto> initListSearchFilter(BannerSettingDto searchDto, Locale locale) {
//        List<CommonSearchFilterDto> list = new ArrayList<>();
//
//        CommonSearchFilterDto bannerPage = commonSearchFilterUtils.createInputCommonSearchFilterDto("bannerPageName",
//                msg.getMessage("banner.page", null, locale), searchDto.getBannerPageName());
//        new CommonSearchFilterDto();
//        list.add(bannerPage);
//
//        CommonSearchFilterDto startDate = commonSearchFilterUtils.createDateCommonSearchFilterDto("startDate",
//                msg.getMessage("homepage.setting.fromDate", null, locale), searchDto.getStartDate());
//        new CommonSearchFilterDto();
//        list.add(startDate);
//
//        CommonSearchFilterDto endDate = commonSearchFilterUtils.createDateCommonSearchFilterDto("endDate",
//                msg.getMessage("homepage.setting.endDate", null, locale), searchDto.getEndDate());
//        new CommonSearchFilterDto();
//        list.add(endDate);
//
//        return list;
//    }
	@Override
	public List<CommonSearchFilterDto> initListSearchFilter(BannerSettingDto searchDto, Locale locale) {
		List<CommonSearchFilterDto> list = BannerSettingService.super.initListSearchFilter(searchDto, locale);
		List<CommonSearchFilterDto> rs = new ArrayList<>();

		List<Select2Dto> listDataPage = bannerSettingRepository.findByBannerPage("PAGE_TYPE", locale.toString());
		List<Select2Dto> listDataType = select2DataService.getConstantData("M_BANNER", "TYPE", locale.toString());

		if (CollectionUtils.isNotEmpty(list)) {
			for (CommonSearchFilterDto filter : list) {
				if ("bannerPageName".equals(filter.getField())) {
					filter = commonSearchFilterUtils.createSelectCommonSearchFilterDto(filter.getField(),
							filter.getFieldName(), searchDto.getBannerPage(), listDataPage);
					rs.add(filter);
				} else if ("bannerTypeName".equals(filter.getField())) {
					filter = commonSearchFilterUtils.createSelectCommonSearchFilterDto(filter.getField(),
							filter.getFieldName(), searchDto.getBannerPage(), listDataType);
					rs.add(filter);
				} else {
					if (!"mBannerTopId".equals(filter.getField()) && !"mBannerTopMobileId".equals(filter.getField())
							&& filter.getField() != null) {
						rs.add(filter);
					}
				}
			}
		}
		return rs;
	}

	@Override
	public BannerSettingEditDto getBannerEdit(Long id, Locale locale) {

		BannerSettingEditDto resultDto = new BannerSettingEditDto();
		if (id == null) {
			return resultDto;
		}

		// set banner
		HomePageSetting banner = findBannerById(id);
		// dữ liệu ko tồn tại hoặc đã bị xóa
		if (banner == null || banner.getDeleteDate() != null) {
			throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
		}
		if (banner != null) {
			resultDto.setId(banner.getId());
			resultDto.setBannerPageName(banner.getBannerPage());
			resultDto.setBannerType(banner.getBannerType());
			resultDto.setBannerTypeName(banner.getBannerType());
			resultDto.setAutoPlay(banner.isAutuPlay());
			if (banner.isAutoMute() == true) {
				banner.setAutoMute(false);

			} else {
				banner.setAutoMute(true);
			}
			resultDto.setAutoMute(banner.isAutoMute());
			resultDto.setAutoReplay(banner.isAutoReplay());
			resultDto.setSlideTime(banner.getSlideTime());
			resultDto.setBannerEff(banner.getEffect());
			resultDto.setStartDate(banner.getStartDate());
			resultDto.setEndDate(checkMaxDate(banner.getEndDate()));
            resultDto.setChannel(banner.getChannel());
			if (banner.getChannel() != null) {
				String[] lstChannel = banner.getChannel().split(",");
				List<String> channelsList = Arrays.asList(lstChannel);
				resultDto.setChannelList(channelsList);
			}
			resultDto.setMBannerTopId(banner.getMBannerTopId());
			resultDto.setMBannerTopMobileId(banner.getMBannerMobileId());

			if (!StringUtils.isEmpty(banner.getMBannerTopId())) {

				String[] lstBannerTop = banner.getMBannerTopId().split(",");
				resultDto.setListMBannerTopId(lstBannerTop);
			}
			if (!StringUtils.isEmpty(banner.getMBannerMobileId())) {

				String[] lstBannerTopMibile = banner.getMBannerMobileId().split(",");
				resultDto.setListMBannerMobileTopId(lstBannerTopMibile);
			}

			resultDto.setBannerYoutubeVideo(banner.getYoutubeVideo());
			if (!StringUtils.isEmpty(banner.getYoutubeVideo())) {

				resultDto.setIsYoutube(1);
			}

			if (!StringUtils.isEmpty(banner.getBannerType())) {
				List<JcaConstantDto> listBannerType = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind(
						ConstDispType.M10.toString(), banner.getBannerType(), "EN");
				resultDto.setBannerTypeName(listBannerType.get(0).getCode());
			}
		}

//        List<BannerLanguageDto> bannerLanguageDtos = getBannerLanguageDto(id);
//        resultDto.setBannerLanguageList(bannerLanguageDtos);

		return resultDto;
	}

	@Override
	public boolean addOrEdit(BannerSettingEditDto bannerEdit, String requestToken, HttpServletRequest request,
			Locale locale) throws Exception {

//		// user login
		String usernameLogin = UserProfileUtils.getUserNameLogin();

//		// save or update banner
		createOrEditBanner(bannerEdit, usernameLogin, locale);

		return true;
	}

	private void createOrEditBanner(BannerSettingEditDto editDto, String userName, Locale locale) {
		HomePageSetting entity = new HomePageSetting();

		if (null != editDto.getId()) {
			entity = bannerSettingRepository.findOne(editDto.getId());

			if (null == entity) {
				throw new BusinessException("Not found data with id=" + editDto.getId());
			}

			entity.setUpdateDate(new Date());
			entity.setUpdateBy(userName);
		} else {
			entity.setCreateDate(new Date());
			entity.setCreateBy(userName);
		}

		entity.setBannerPage(editDto.getBannerPageName());
		entity.setSlideTime(editDto.getSlideTime());
		entity.setBannerType(editDto.getBannerType());
		entity.setYoutubeVideo(editDto.getBannerYoutubeVideo());
		entity.setLinkYoutubeVideo(editDto.getBannerLanguageList().get(0).getBannerPhysicalVideo());
		entity.setMBannerTopId(editDto.getMBannerTopId());
		entity.setMBannerMobileId(editDto.getMBannerTopMobileId());
		entity.setEffect(editDto.getBannerEff());
		if (editDto.getChannelList() != null) {
			entity.setChannel(String.join(",", editDto.getChannelList()));
		}
		entity.setStartDate(editDto.getStartDate());
		entity.setEndDate(checkMaxDate(editDto.getEndDate()));
		entity.setAutuPlay(editDto.isAutoPlay());
		
		if (editDto.isAutoMute() == true) {
			editDto.setAutoMute(false);

		} else {
			editDto.setAutoMute(true);
		}
		entity.setAutoMute(editDto.isAutoMute());

		entity.setAutoReplay(editDto.isAutoReplay());
		entity.setNote(editDto.getNote());
		bannerSettingRepository.save(entity);

		editDto.setId(entity.getId());
	}

	@Override
	public List<Select2Dto> findByBannerPage(String bannerPage, String lang) {
		return bannerSettingRepository.findByBannerPage(bannerPage, lang);
	}

	@Override
	public CommonSearchFilterUtils getCommonSearchFilterUtils() {
		return commonSearchFilterUtils;
	}

	@Override
	public HomePageSetting findDateInto(String bannerPage, String bannerType, Long id, String channel) {
		return bannerSettingRepository.findDateInto(bannerPage, bannerType, id, channel);
	}

}
