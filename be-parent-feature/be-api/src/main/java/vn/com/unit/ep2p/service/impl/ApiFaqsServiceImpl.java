package vn.com.unit.ep2p.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.core.module.faqs.dto.FaqsCategoryLangDto;
import vn.com.unit.cms.core.module.faqs.dto.FaqsSearchDto;
import vn.com.unit.cms.core.module.faqs.dto.FaqsSearchResultDto;
import vn.com.unit.cms.core.module.faqs.repository.FaqsRepository;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.ep2p.core.utils.Utility;
import vn.com.unit.ep2p.service.ApiFaqsService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ApiFaqsServiceImpl implements ApiFaqsService {

    @Autowired
    private FaqsRepository faqsRepository;

    @Override
    public int countByCondition(FaqsSearchDto searchDto, Integer modeView) {
        return faqsRepository.countFaqs(searchDto);
    }

    @Override
    public List<FaqsSearchResultDto> searchByCondition(FaqsSearchDto searchDto, Pageable pageable, Integer modeView) {
        List<FaqsSearchResultDto> list = faqsRepository.findFaqsList(searchDto, pageable).getContent();
        return list;
    }


	@Override
	public int countFaqsByIdCategory(String searchKey, Long idCategory, Integer modeView, String language) {
		return faqsRepository.countFaqsByIdCategory(searchKey, idCategory, modeView, language);
	}

	@Override
	public List<FaqsSearchResultDto> searchFaqsByIdCategory(String searchKey, Long idCategory, Integer page,
			Integer size, String language, Integer modeView) {
		int offsetSQL = Utility.calculateOffsetSQL(page, size);
		List<FaqsSearchResultDto> listData = faqsRepository.searchFaqsByIdCategory(offsetSQL, size, searchKey, idCategory,language, modeView);
		/*
		 * for (FaqsSearchResultDto data : listData) {
		 * data.setContentString(CmsUtils.converByteToStringUTF8(data.getContent())); }
		 */
		return listData;
	}

	@Override
	public List<FaqsCategoryLangDto> getListCategoryFaqs(String language, Integer modeView) {
		// TODO Auto-generated method stub
		return faqsRepository.getListCategoryFaqs(language, modeView);
	}
}
