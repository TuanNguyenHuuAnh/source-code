package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.entity.JobTypeDetailLanguage;
import vn.com.unit.cms.admin.all.entity.JobTypeLanguage;
import vn.com.unit.cms.admin.all.entity.JobTypeSubLanguage;
import vn.com.unit.db.repository.DbRepository;

public interface JobTypeDetailLanguageRepository extends DbRepository<JobTypeDetailLanguage, Long> {

	/**
	 * retrieve all rows in m_job_type
	 * 
	 * @return
	 */
	public List<JobTypeLanguage> getJtLanguageDtoByLangId(@Param("langCode") String langCode);

	/**
	 * retrieve all row in m_job_type_sub
	 * 
	 * @return
	 */
	public List<JobTypeSubLanguage> getJtsLanguageDtoByLangId(@Param("langCode") String langCode);

}
