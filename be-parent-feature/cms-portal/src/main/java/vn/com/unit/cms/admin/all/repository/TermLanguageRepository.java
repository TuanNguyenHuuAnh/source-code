package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.entity.TermLanguage;
import vn.com.unit.db.repository.DbRepository;

public interface TermLanguageRepository extends DbRepository<TermLanguage, Long> {

	public List<TermLanguage> findAllByTermId(@Param("termId") Long termId);

	public List<TermLanguage> findAllByLanguageCode(@Param("languageCode") String languageCode);

	@Modifying
	public void deleteByTermId(@Param("termId") Long termId, @Param("deleteDate") Date deleteDate,
			@Param("deleteBy") String deleteBy);

	/**
	 * findByTermCode
	 *
	 * @param termCode
	 * @param languageCode
	 * @return
	 * @author hand
	 */
	public TermLanguage findByTermCode(@Param("termCode") String termCode, @Param("languageCode") String languageCode);

}
