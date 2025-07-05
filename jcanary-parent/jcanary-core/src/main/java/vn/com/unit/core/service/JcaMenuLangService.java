/*******************************************************************************
 * Class        ：JcaMenuLanguageService
 * Created date ：2021/02/24
 * Lasted date  ：2021/02/24
 * Author       ：taitt
 * Change log   ：2021/02/24：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import vn.com.unit.core.dto.JcaMenuLangDto;
import vn.com.unit.core.entity.JcaMenuLang;
import vn.com.unit.db.service.DbRepositoryService;

/**
 * JcaMenuLanguageService.
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface JcaMenuLangService extends DbRepositoryService<JcaMenuLang, Long>{

    /**
     * <p>
     * Save list jca menu language.
     * </p>
     *
     * @param menuId
     *            type {@link Long}
     * @param languages
     *            type {@link List<JcaMenuLanguageDto>}
     * @return {@link List<JcaMenuLanguageDto>}
     * @author taitt
     */
    List<JcaMenuLangDto> saveListJcaMenuLanguage(Long menuId, List<JcaMenuLangDto> languages);
    
    /**
     * <p>
     * Delete jca menu lang by menu id.
     * </p>
     *
     * @author tantm
     * @param id
     *            type {@link Long}
     * @return {@link int}
     */
    int deleteJcaMenuLangByMenuId(Long id);
    
    /**
     * <p>
     * Get jca menu lang dto list default.
     * </p>
     *
     * @author TrieuVD
     * @return {@link List<JcaMenuLangDto>}
     */
    public List<JcaMenuLangDto> getJcaMenuLangDtoListDefault();
    
    /**
     * <p>
     * Save jca menu lang dto.
     * </p>
     *
     * @author TrieuVD
     * @param jcaMenuLangDto
     *            type {@link JcaMenuLangDto}
     * @return {@link JcaMenuLang}
     */
    public JcaMenuLang saveJcaMenuLangDto(JcaMenuLangDto jcaMenuLangDto);
}
