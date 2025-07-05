/*******************************************************************************
* Class        I18nLocaleService
* Created date 2021/01/06
* Lasted date  2021/01/06
* Author       NhanNV
* Change log   2021/01/06 01-00 NhanNV create a new
******************************************************************************/

package vn.com.unit.core.service;

import java.util.List;

import vn.com.unit.core.dto.I18nLocaleDto;
import vn.com.unit.core.entity.I18nLocale;

/**
 * I18nLocaleService
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */

public interface I18nLocaleService {

    /**
     * get I18nLocaleDto by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link I18nLocaleDto}
     * @author NhanNV
     */
    I18nLocaleDto getI18nLocaleDtoById(Long id);

    /**
     * check flag DELETED_ID by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link boolean}
     * @author NhanNV
     */
    boolean deleteById(Long id);

    /**
     * save I18nLocale with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE)
     * 
     * @param I18nLocale
     *            type {@link I18nLocale}
     * @return {@link I18nLocale}
     * @author NhanNV
     */
    I18nLocale saveI18nLocale(I18nLocale i18nLocale);

    /**
     * save I18nLocaleDto
     * 
     * @param i18nLocaleDto
     *            type {@link I18nLocaleDto}
     * @return {@link I18nLocale}
     * @author NhanNV
     */
    I18nLocale saveI18nLocaleDto(I18nLocaleDto i18nLocaleDto);
    
    List<I18nLocale> findByCompanyIdAndLocale(long companyId, String locale);
    
    List<I18nLocaleDto> findI18nLocaleDtoByCompanyIdAndLocale(long companyId, String locale);
    
    boolean cloneTranslation(long companyId, String newLocale);
}