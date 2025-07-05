package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.entity.JobTypeLanguage;
import vn.com.unit.db.repository.DbRepository;

public interface JobTypeLanguageRepository extends DbRepository<JobTypeLanguage, Long> {

	List<JobTypeLanguage> findByTypeId(@Param("typeId") Long typeId);

	@Modifying
	public void deleteByTypeId(@Param("deleteId") Long id, @Param("deleteBy") String userName,
			@Param("deleteDate") Date date);

}
