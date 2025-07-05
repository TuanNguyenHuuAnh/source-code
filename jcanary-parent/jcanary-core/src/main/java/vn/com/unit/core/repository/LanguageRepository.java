/*******************************************************************************
 * Class        LanguageRepository
 * Created date 2017/02/16
 * Lasted date  2017/02/16
 * Author       NhanNV
 * Change log   2017/02/1601-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.entity.Language;
import vn.com.unit.db.repository.DbRepository;

public interface LanguageRepository extends DbRepository<Language, Long>{

	public List<Language> findAllActive();

	public List<Language> findAllActiveOracle();
	
    /**
     * findByCode
     * 
     * @param lang
     * @return
     * @author NhanNV
     */
    public LanguageDto findByCode(@Param("lang") String lang);
    
    public LanguageDto findByCompanyIdAndCode(@Param("companyId") Long companyId, @Param("code") String code);

    /**
     * findByCompanyId
     * @param companyId
     * @return
     * @author ngannh
     */
    public List<Language> getByCompanyId(@Param("companyId") Long companyId);
	
}
