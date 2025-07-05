/*******************************************************************************
 * Class        ：DocumentTypeLanguageRepository
 * Created date ：2017/04/19
 * Lasted date  ：2017/04/19
 * Author       ：thuydtn
 * Change log   ：2017/04/19：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.entity.DocumentTypeLanguage;
import vn.com.unit.db.repository.DbRepository;



    /**
     * DocumentTypeLanguageRepository
     * 
     * @version 01-00
     * @since 01-00
     * @author thuydtn
     */
    public interface DocumentTypeLanguageRepository extends DbRepository<DocumentTypeLanguage, Long> {

    /**
     * find DocumentTypeLanguageb by typeId
     *
     * @param documentTypeId
     * @return
     * @author thuydtn
     */
    List<DocumentTypeLanguage> findByTypeId(@Param("typeId") Long typeId);

    /**
     * delete DocumentTypeLanguage by document type id
     *
     * @param deleteDate
     * @param deleteBy
     * @param documentId
     * @author thuydtn
     */
    @Modifying
    public void deleteByTypeId(@Param("deleteDate") Date deleteDate, @Param("deleteBy") String deleteBy,
            @Param("typeId") Long typeId);

}
