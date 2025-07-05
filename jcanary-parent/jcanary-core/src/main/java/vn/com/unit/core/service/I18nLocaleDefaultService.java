/*******************************************************************************
* Class        I18nLocaleDefaultService
* Created date 2021/01/06
* Lasted date  2021/01/06
* Author       NhanNV
* Change log   2021/01/06 01-00 NhanNV create a new
******************************************************************************/

package vn.com.unit.core.service;

import java.util.List;

import vn.com.unit.core.dto.I18nLocaleDefaultDto;
import vn.com.unit.core.entity.I18nLocaleDefault;

/**
 * I18nLocaleDefaultService
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */

public interface I18nLocaleDefaultService {

    /**
     * get I18nLocaleDefaultDto by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link I18nLocaleDefaultDto}
     * @author NhanNV
     */
    I18nLocaleDefaultDto getI18nLocaleDefaultDtoById(Long id);

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
     * save I18nLocaleDefault with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE)
     * 
     * @param I18nLocaleDefault
     *            type {@link I18nLocaleDefault}
     * @return {@link I18nLocaleDefault}
     * @author NhanNV
     */
    I18nLocaleDefault saveI18nLocaleDefault(I18nLocaleDefault i18nLocaleDefault);

    /**
     * save I18nLocaleDefaultDto
     * 
     * @param i18nLocaleDefaultDto
     *            type {@link I18nLocaleDefaultDto}
     * @return {@link I18nLocaleDefault}
     * @author NhanNV
     */
    I18nLocaleDefault saveI18nLocaleDefaultDto(I18nLocaleDefaultDto i18nLocaleDefaultDto);
    
    List<I18nLocaleDefault> findByCompanyIdAndLocale(long companyId, String locale);


}