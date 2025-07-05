package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.entity.CustomerVipLanguage;
import vn.com.unit.db.repository.DbRepository;

public interface CustomerVipLanguageRepository extends DbRepository<CustomerVipLanguage, Long> {

	List<CustomerVipLanguage> findByCustomerId(@Param("id") Long id);
}
