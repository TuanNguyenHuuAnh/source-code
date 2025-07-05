package vn.com.unit.ep2p.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.cms.core.module.faqs.dto.FaqsCategoryLangDto;
import vn.com.unit.cms.core.module.faqs.dto.FaqsSearchDto;
import vn.com.unit.cms.core.module.faqs.dto.FaqsSearchResultDto;

public interface ApiFaqsService {
    public int countByCondition(FaqsSearchDto searchDto, Integer modeView);

    public List<FaqsSearchResultDto> searchByCondition(FaqsSearchDto searchDto, Pageable pageable, Integer modeView);
    
	public int countFaqsByIdCategory(String searchKey, Long idCategory, Integer modeView, String language);

	public List<FaqsSearchResultDto> searchFaqsByIdCategory(String searchKey, Long idCategory, Integer page, Integer size,
			String language, Integer modeView);

	public List<FaqsCategoryLangDto> getListCategoryFaqs(String language, Integer modeView);
}
