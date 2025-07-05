/*******************************************************************************
* Class        I18nLocaleRepository
* Created date 2021/01/06
* Lasted date  2021/01/06
* Author       NhanNV
* Change log   2021/01/06 01-00 NhanNV create a new
******************************************************************************/

package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.com.unit.core.entity.I18nLocale;
import vn.com.unit.db.repository.DbRepository;

/**
 * I18nLocaleRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */

@Repository
public interface I18nLocaleRepository extends DbRepository<I18nLocale, Long>{

	List<I18nLocale> findByCompanyIdAndLocale(@Param("companyId") long companyId, @Param("locale") String locale);
}