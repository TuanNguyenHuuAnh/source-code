package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.entity.InvestorLanguage;
import vn.com.unit.db.repository.DbRepository;

public interface InvestorLanguageRepository extends DbRepository<InvestorLanguage, Long> {

	List<InvestorLanguage> findByInvestorId(@Param("investorId") Long investorId);

}
