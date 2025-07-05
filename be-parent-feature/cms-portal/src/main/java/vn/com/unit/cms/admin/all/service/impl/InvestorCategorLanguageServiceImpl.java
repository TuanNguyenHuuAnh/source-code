package vn.com.unit.cms.admin.all.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.repository.InvestorCategoryLanguageRepository;
import vn.com.unit.cms.admin.all.service.InvestorCategorLanguageService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class InvestorCategorLanguageServiceImpl implements InvestorCategorLanguageService{

	
	@Autowired
	InvestorCategoryLanguageRepository categoryLanguageRepository;
	
	/**
	 * deleteByInvestorCategoryId_IN_m_investor_category_language
	 */
	@Override
	@Transactional
	public void deleteByInvestorCategoryId(Date deleteDate, String deleteBy, Long categoryId) {
		categoryLanguageRepository.deleteByInvestorCategoryId(deleteDate, deleteBy, categoryId);
	}

}
