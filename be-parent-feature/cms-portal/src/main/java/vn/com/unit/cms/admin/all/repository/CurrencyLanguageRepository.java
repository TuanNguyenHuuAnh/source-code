package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.entity.CurrencyLanguage;
import vn.com.unit.db.repository.DbRepository;

public interface CurrencyLanguageRepository extends DbRepository<CurrencyLanguage, Long> {

	public List<CurrencyLanguage> findAllByCurrencyId(@Param("currencyId") Long currencyId);

	@Modifying
	public void deleteByCurrencyId(@Param("currencyId") Long currencyId, @Param("deleteDate") Date deleteDate,
			@Param("deleteBy") String deleteBy);

}
