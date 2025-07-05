/*******************************************************************************
 * Class        ：DocumentCategoryLanguageRepository
 * Created date ：2017/04/20
 * Lasted date  ：2017/04/20
 * Author       ：thuydtn
 * Change log   ：2017/04/20：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.entity.DocumentCategoryLanguage;
import vn.com.unit.db.repository.DbRepository;

public interface DocumentCategoryLanguageRepository extends DbRepository<DocumentCategoryLanguage, Long> {

    public List<DocumentCategoryLanguage> findByCategoryId(@Param("cateId") Long categoryId);

    @Modifying
    public int deleteData(@Param("id") long id, @Param("deleteBy") String deleteBy);
    
    public DocumentCategoryLanguage findLanguage(@Param("cateId") Long categoryId, @Param("lang") String lang);
}
