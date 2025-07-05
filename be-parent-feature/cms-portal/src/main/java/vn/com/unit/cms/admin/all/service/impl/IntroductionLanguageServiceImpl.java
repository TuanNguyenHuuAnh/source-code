package vn.com.unit.cms.admin.all.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.unit.cms.admin.all.entity.IntroductionLanguage;
import vn.com.unit.cms.admin.all.repository.IntroductionLanguageRepository;
import vn.com.unit.cms.admin.all.service.IntroductionLanguageService;

@Service
public class IntroductionLanguageServiceImpl implements IntroductionLanguageService{

	@Autowired
	IntroductionLanguageRepository introductionLanguageRepository;
	
	@Override
	public List<IntroductionLanguage> findByIntroductionId(Long productCategoryId) {
		return introductionLanguageRepository.findByIntroductionId(productCategoryId);
	}

}
