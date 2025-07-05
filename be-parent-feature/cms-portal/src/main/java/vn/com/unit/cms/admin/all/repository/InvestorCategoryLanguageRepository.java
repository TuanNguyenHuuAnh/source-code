package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.entity.InvestorCategoryLanguage;
import vn.com.unit.db.repository.DbRepository;

public interface InvestorCategoryLanguageRepository extends DbRepository<InvestorCategoryLanguage,Long>	{

	/**
	 * deleteByInvestorCategoryId_IN_m_investor_category_language
	 */
	@Modifying
	public void deleteByInvestorCategoryId(@Param("deleteDate") Date deleteDate, @Param("deleteBy") String deleteBy,
			@Param("categoryId") Long categoryId);
	
	public List<InvestorCategoryLanguage> findByCategoryId(@Param("cateId") Long categoryId);
}
