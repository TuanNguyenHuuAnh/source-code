/*******************************************************************************
* Class        I18nLocaleDefaultRepository
* Created date 2021/01/06
* Lasted date  2021/01/06
* Author       NhanNV
* Change log   2021/01/06 01-00 NhanNV create a new
******************************************************************************/

package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.com.unit.core.entity.I18nLocaleDefault;
import vn.com.unit.db.repository.DbRepository;

/**
 * I18nLocaleDefaultRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */

@Repository
public interface I18nLocaleDefaultRepository extends DbRepository<I18nLocaleDefault, Long>{
	
	List<I18nLocaleDefault> findByCompanyIdAndLocale(@Param("companyId") long companyId, @Param("locale") String locale);
}