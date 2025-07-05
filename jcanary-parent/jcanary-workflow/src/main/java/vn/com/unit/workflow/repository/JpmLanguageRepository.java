/*******************************************************************************
 * Class        ：JpmLanguageRepository
 * Created date ：2021/03/04
 * Lasted date  ：2021/03/04
 * Author       ：KhuongTH
 * Change log   ：2021/03/04：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.workflow.repository;

import java.util.List;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmLanguageDto;

/**
 * <p>JpmLanguageRepository</p>.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface JpmLanguageRepository extends DbRepository<Object, Long> {
    
    /**
     * <p>Gets the default languages.</p>
     *
     * @return the default languages
     * @author KhuongTH
     */
    List<JpmLanguageDto> getDefaultLanguages();
}
