package vn.com.unit.ep2p.service;

import java.util.List;

import vn.com.unit.cms.core.module.news.dto.FeNewsSearchDto;
import vn.com.unit.cms.core.module.news.dto.NewsByTypeSearchDto;
import vn.com.unit.cms.core.module.news.dto.NewsSearchResultDto;
import vn.com.unit.cms.core.module.news.dto.NewsTypeSearchAllPage;
import vn.com.unit.cms.core.module.news.entity.News;

public interface ApiNewsService{
	
	public int countByCondition(FeNewsSearchDto searchDto, Integer modeView, String language, String channel);

    public List<NewsSearchResultDto> searchByCondition(FeNewsSearchDto searchDto, Integer page, Integer size, String language, Integer modeView, String channel);
    
    public NewsSearchResultDto getDetailListNew(Long id, String language);
    
    public List<NewsSearchResultDto> getListLabelNewsType(Long[] typeIds);

    public List<News> getListNewsByTypeIds(Long[] typeIds);

	public List<NewsSearchResultDto> getListCategoryByPageType(String pageType, Integer modeView, String channel);

	public NewsSearchResultDto detailNewsByLink(String link, String type, String categoryId, String language);

	public NewsSearchResultDto getTypeByPageType(String pageType, Integer modeView, String language);

	public List<NewsTypeSearchAllPage> getTypeByNewsKey(String key, Integer modeView, String language);

	public int countNewsByType(NewsByTypeSearchDto searchDto, Integer modeView, String language);

	public List<NewsSearchResultDto> searchNewsByType(NewsByTypeSearchDto searchDto, Integer page, Integer size,
			String language, Integer modeView);

	public List<NewsSearchResultDto> getListPageType(String language, Integer modeView, String channel);

	public List<NewsSearchResultDto> listNewsCareer(String typeCode, String language);
	
	public List<NewsSearchResultDto> listNewsEvent(String typeCode, String language, Long newsId);
	
	public NewsSearchResultDto newsHot(String typeCode, String language);
	
	public List<NewsSearchResultDto> getNewsHot(String language, Long mNewsCategoryId);
	
	public List<NewsSearchResultDto> getAllEnableByTypeCode(String typeCode, String language);
	
}
