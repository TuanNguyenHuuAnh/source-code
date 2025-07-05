/*******************************************************************************
 * Class        ：JcaGroupConstantLanguageService
 * Created date ：2020/12/24
 * Lasted date  ：2020/12/24
 * Author       ：tantm
 * Change log   ：2020/12/24：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import vn.com.unit.core.dto.JcaGroupConstantLangDto;

/**
 * JcaGroupConstantLanguageService.
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface JcaGroupConstantLangService {

    /**
     * Save list jca group constant language.
     *
     * @param id
     *            the id type Long
     * @param languages
     *            the languages type List of JcaGroupConstantLanguageDto
     * @author tantm
     * @return 
     */
    List<JcaGroupConstantLangDto> saveListJcaGroupConstantLanguage(Long id, List<JcaGroupConstantLangDto> languages);

}
