package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.entity.JobTypeSubLanguage;
import vn.com.unit.db.repository.DbRepository;

public interface JobTypeSubLanguageRepository extends DbRepository<JobTypeSubLanguage, Long> {
	List<JobTypeSubLanguage> findByTypeId(@Param("typeId") Long typeId);
	
	@Modifying
	public void deleteByTypeId(@Param("typeId") Long typeId, @Param("deleteBy") String deleteBy,
			@Param("deleteDate") Date deleteDate);

}
