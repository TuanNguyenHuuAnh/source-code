/*******************************************************************************
 * Class        ：FormLangRepository
 * Created date ：2020/01/14
 * Lasted date  ：2020/01/14
 * Author       ：KhuongTH
 * Change log   ：2020/01/14：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.core.efo.entity.EfoFormLang;
import vn.com.unit.ep2p.dto.LanguageMapDto;


/**
 * FormLangRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface FormLangRepository extends DbRepository<EfoFormLang, Long> {
    List<EfoFormLang> findByFormId(@Param("formId")Long formId);
    
    /**
     * @param formId
     * @return
     */
    List<LanguageMapDto> findFormNameListById(@Param("formId") Long formId);

    EfoFormLang findOneByPK(@Param("formId") Long formId, @Param("langId") Long langId);
}
