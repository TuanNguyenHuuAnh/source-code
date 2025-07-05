package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import vn.com.unit.cms.core.module.news.dto.FeNewsSearchDto;
import vn.com.unit.cms.core.module.news.dto.NewsByTypeSearchDto;
import vn.com.unit.cms.core.module.news.dto.NewsSearchResultDto;
import vn.com.unit.cms.core.module.news.dto.NewsTypeSearchAllPage;
import vn.com.unit.cms.core.module.news.entity.News;
import vn.com.unit.cms.core.module.news.repository.NewsRepository;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaSystemConfigDto;
import vn.com.unit.ep2p.core.utils.Utility;
import vn.com.unit.ep2p.service.ApiNewsService;
import vn.com.unit.ep2p.service.SystemConfigService;
import vn.com.unit.ep2p.utils.RestUtil;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ApiNewsServiceImpl implements ApiNewsService {

	private static final Logger logger = LoggerFactory.getLogger(ApiNewsServiceImpl.class);

	@Autowired
	private NewsRepository newsRepository;
	
    @Autowired
    private HttpServletRequest httpRequest;
    
	@Autowired
	private SystemConfig systemConfig;
	
	@Autowired
	private ServletContext servletContext;


	@Override
	public int countByCondition(FeNewsSearchDto searchDto, Integer modeView, String language, String channel) {
		logger.info("Count Data News");
		return newsRepository.countNewsDto(searchDto, modeView, language, channel);
	}

	@Override
	public List<NewsSearchResultDto> searchByCondition(FeNewsSearchDto searchDto, Integer page, Integer sizeOfPage,
			String language, Integer modeView, String channel) {
		int offsetSQL = 0;
		if(page != null && sizeOfPage != null) {
			offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);
			
		}
		List<NewsSearchResultDto> listData = newsRepository.findNewsDto(offsetSQL, sizeOfPage, searchDto, language, modeView, channel);
		if(CollectionUtils.isNotEmpty(listData)) {
			for (NewsSearchResultDto data : listData) {
				try {
					data.setShortContent(convertImageContentString(data.getShortContent()));
					data.setContentString(convertImageContentString(CmsUtils.converByteToStringUTF8(data.getContent())));
				} catch (Exception e) {
					logger.error("Exception ", e);
				}
//				data.setContentString(CmsUtils.converByteToStringUTF8(data.getContent()));
				data.setPhysicalImgUrl(RestUtil.replaceImageUrl(data.getPhysicalImgUrl(), null));
			}
		}
		
		return listData;
	}

	@Override
	public NewsSearchResultDto getDetailListNew(Long id, String language) {
		try {
			NewsSearchResultDto detail = newsRepository.getDetailListNews(id, language);
			try {
				detail.setShortContent(convertImageContentString(detail.getShortContent()));
				detail.setContentString(convertImageContentString(CmsUtils.converByteToStringUTF8(detail.getContent())));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Exception ", e);
			}
//			detail.setContentString(CmsUtils.converByteToStringUTF8(detail.getContent()));
			return detail;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public List<NewsSearchResultDto> getListLabelNewsType(Long[] typeIds) {
		try {
			List<NewsSearchResultDto> lstLabel = newsRepository.getListLableNewsType(typeIds);
			return lstLabel;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public List<News> getListNewsByTypeIds(Long[] typeIds) {
		return newsRepository.findNewsByTypeIds(typeIds);
	}
	
	@Autowired
    private SystemConfigService systemConfigService;
	
	@Override
	public List<NewsSearchResultDto> getListCategoryByPageType(String pageType, Integer modeView, String channel) {
		try {
		    JcaSystemConfigDto userGuide = systemConfigService.getConfigByKey("USERGUIDE", 2L);
		    JcaSystemConfigDto security = systemConfigService.getConfigByKey("SECURITY", 2L);
		    List<NewsSearchResultDto> lstLabel = new ArrayList<>();
		    if(pageType.equalsIgnoreCase(userGuide.getSettingKey()) ) {
		        lstLabel = newsRepository.getListCategoryOfUserGuide(userGuide.getSettingKey(), Long.valueOf(userGuide.getSettingValue()), modeView);
		    }
		    else if(pageType.equalsIgnoreCase(security.getSettingKey()) ) {
		        lstLabel = newsRepository.getListCategoryOfUserGuide(security.getSettingKey(), Long.valueOf(security.getSettingValue()), modeView);
	         }
		    else {
		        lstLabel = newsRepository.getListCategoryByPageType(pageType, modeView, channel);
		    }
			return lstLabel;
		} catch (Exception e) {
		    logger.error("getListCategoryByPageType", e);
		}
		return null;
	}
	
	@Override
	public NewsSearchResultDto detailNewsByLink(String link, String type, String categoryId, String language) {
		try {
			NewsSearchResultDto detail = newsRepository.getDetailNewsByLink(link, type, categoryId, language);
			try {
				detail.setShortContent(convertImageContentString(detail.getShortContent()));
				detail.setContentString(convertImageContentString(CmsUtils.converByteToStringUTF8(detail.getContent())));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Exception ", e);
			}
//			detail.setContentString(CmsUtils.converByteToStringUTF8(detail.getContent()));
			return detail;
		} catch (Exception e) {
			logger.error("detailNewsByLink", e);
		}
		return null;
	}

	@Override
	public NewsSearchResultDto getTypeByPageType(String pageType, Integer modeView, String language) {
		try {
			NewsSearchResultDto lstLabel = newsRepository.getTypeByPageType(pageType, modeView, language);
			return lstLabel;
		} catch (Exception e) {
			logger.error("getListTypeByPageType", e);
		}
		return null;
	}

	@Override
	public List<NewsTypeSearchAllPage> getTypeByNewsKey(String key, Integer modeView, String language) {
		try {
			List<NewsTypeSearchAllPage> lstLabel = newsRepository.getTypeByNewsKey(key, modeView, language);
			return lstLabel;
		} catch (Exception e) {
			logger.error("getListTypeByPageType", e);
		}
		return null;
	}
	@Override
	public int countNewsByType(NewsByTypeSearchDto searchDto, Integer modeView, String language) {
		return newsRepository.countNewsByType(searchDto, modeView, language);
	}

	@Override
	public List<NewsSearchResultDto> searchNewsByType(NewsByTypeSearchDto searchDto, Integer page, Integer sizeOfPage,
			String language, Integer modeView) {
		int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);
		List<NewsSearchResultDto> listData = newsRepository.searchNewsByType(offsetSQL, sizeOfPage, searchDto, language, modeView);
		for (NewsSearchResultDto data : listData) {
			if(data.getNewsCategoryId() != 999999L) {
				try {
					data.setShortContent(convertImageContentString(data.getShortContent()));
					data.setContentString(convertImageContentString(CmsUtils.converByteToStringUTF8(data.getContent())));
				} catch (Exception e) {
					logger.error("Exception ", e);
				}
//				data.setContentString(CmsUtils.converByteToStringUTF8(data.getContent()));
			}
		}
		return listData;
	}

	@Override
	public List<NewsSearchResultDto> getListPageType(String language, Integer modeView, String channel) {
		try {
			List<NewsSearchResultDto> lstLabel = newsRepository.getListPageType(language, modeView, channel);
			return lstLabel;
		} catch (Exception e) {
			logger.error("getListPageType", e);
		}
		return null;
	}

	@Override
	public List<NewsSearchResultDto> listNewsCareer(String typeCode, String language) {
		try {
			List<NewsSearchResultDto> lstLabel = new ArrayList<>();
			lstLabel = newsRepository.listNewsCareer(typeCode, language);
			for (NewsSearchResultDto data : lstLabel) {
				try {
					data.setShortContent(convertImageContentString(data.getShortContent()));
					data.setContentString(convertImageContentString(CmsUtils.converByteToStringUTF8(data.getContent())));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("Exception ", e);
				}
//				data.setContentString(CmsUtils.converByteToStringUTF8(data.getContent()));
				data.setPhysicalImgUrl(RestUtil.replaceImageUrl(data.getPhysicalImgUrl(), null));
			}
			return lstLabel;
		} catch (Exception e) {
			logger.error("getListPageType", e);
		}
		return null;
	}

	@Override
	public List<NewsSearchResultDto> listNewsEvent(String typeCode, String language, Long newsId) {
		List<NewsSearchResultDto> listData = newsRepository.listNewsEvent(typeCode, language, newsId);
		if(CollectionUtils.isNotEmpty(listData)) {
			for (NewsSearchResultDto data : listData) {
				try {
					data.setShortContent(convertImageContentString(data.getShortContent()));
					data.setContentString(convertImageContentString(CmsUtils.converByteToStringUTF8(data.getContent())));
				} catch (Exception e) {
					logger.error("Exception ", e);
				}
//				data.setContentString(CmsUtils.converByteToStringUTF8(data.getContent()));
				data.setPhysicalImgUrl(RestUtil.replaceImageUrl(data.getPhysicalImgUrl(), null));
			}
		}
		return listData;
	}

	@Override
	public NewsSearchResultDto newsHot(String typeCode, String language) {
		NewsSearchResultDto entity = newsRepository.newsHot(typeCode, language);
		if(!ObjectUtils.isEmpty(entity)) {
			entity.setShortContent(convertImageContentString(entity.getShortContent()));
			entity.setContentString(convertImageContentString(CmsUtils.converByteToStringUTF8(entity.getContent())));
			entity.setPhysicalImgUrl(RestUtil.replaceImageUrl(entity.getPhysicalImgUrl(), null));
		}
		return entity;
	}

	@Override
	public List<NewsSearchResultDto> getNewsHot(String language, Long mNewsCategoryId) {
		List<NewsSearchResultDto> listData = newsRepository.getNewsHot(language, mNewsCategoryId);
		if(CommonCollectionUtil.isNotEmpty(listData)) {
			for (NewsSearchResultDto data : listData) {
				try {
					data.setShortContent(convertImageContentString(data.getShortContent()));
					data.setContentString(convertImageContentString(CmsUtils.converByteToStringUTF8(data.getContent())));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("Exception ", e);
				}
				data.setPhysicalImgUrl(RestUtil.replaceImageUrl(data.getPhysicalImgUrl(), null));
			}
		}
		return listData;
	}

	@Override
	public List<NewsSearchResultDto> getAllEnableByTypeCode(String typeCode, String language) {
		List<NewsSearchResultDto> listData = newsRepository.getAllByTypeCode(typeCode, language);
		if(CollectionUtils.isNotEmpty(listData)) {
			for (NewsSearchResultDto data : listData) {
				try {
					data.setShortContent(convertImageContentString(data.getShortContent()));
					data.setContentString(convertImageContentString(CmsUtils.converByteToStringUTF8(data.getContent())));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("Exception ", e);
				}
//				data.setContentString(CmsUtils.converByteToStringUTF8(data.getContent()));
				data.setPhysicalImgUrl(RestUtil.replaceImageUrl(data.getPhysicalImgUrl(), null));
			}
		}
		return listData;
	}
	private String convertImageContentString(String content) {
		try {

			String pathAdmin = systemConfig.getConfig(SystemConfig.PATH_DOMAIN_ADMIN);
			String baseAdmin = systemConfig.getConfig(SystemConfig.BASE_DOMAIN_ADMIN);

			String pathApi = systemConfig.getConfig(SystemConfig.PATH_DOMAIN_API);
			String baseApi = systemConfig.getConfig(SystemConfig.BASE_DOMAIN_API);
			String contents = RestUtil.replaceUrlImg("personal", "news", httpRequest, content, pathAdmin, pathApi, servletContext);
			String templatePath = baseAdmin+"/"+pathApi;
	        String pathAdmins = baseApi+"/"+pathAdmin+"/static/CMS";
	        return contents.replace(pathAdmins, templatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception ", e);
		}
		return null;
	}
	
}
