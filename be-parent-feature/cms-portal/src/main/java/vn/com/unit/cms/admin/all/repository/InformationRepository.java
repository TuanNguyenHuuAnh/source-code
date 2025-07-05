package vn.com.unit.cms.admin.all.repository;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.entity.Information;
import vn.com.unit.db.repository.DbRepository;

public interface InformationRepository extends DbRepository<Information, Long> {

	public Information findInfo(@Param("infoId") Long infoId);

}
