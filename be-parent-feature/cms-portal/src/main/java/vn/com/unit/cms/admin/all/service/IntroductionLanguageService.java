package vn.com.unit.cms.admin.all.service;

import java.util.List;

import vn.com.unit.cms.admin.all.entity.IntroductionLanguage;

/**
 * ProductCategoryLanguageService
 * 
 * @version 01-00
 * @since 01-00
 * @author diennv
 */
public interface IntroductionLanguageService {

	public List<IntroductionLanguage> findByIntroductionId(Long productCategoryId);
}
