/*******************************************************************************
 * Class        ：NewsCategoryLanguageRepository
 * Created date ：2017/02/27
 * Lasted date  ：2017/02/27
 * Author       ：hand
 * Change log   ：2017/02/27：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.entity.NewsCategoryLanguage;
import vn.com.unit.db.repository.DbRepository;

/**
 * NewsCategoryLanguageRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public interface NewsCategoryLanguageRepository extends DbRepository<NewsCategoryLanguage, Long> {

    /**
     * find NewsCategoryLanguageb by CategoryId
     *
     * @param mNewsCategoryId
     * @return
     * @author hand
     */
    List<NewsCategoryLanguage> findByCategoryId(@Param("mNewsCategoryId") Long mNewsCategoryId);

    /**
     * delete NewsCategoryLanguage by category id
     *
     * @param deleteDate
     * @param deleteBy
     * @param categoryId
     * @author hand
     */
    @Modifying
    public void deleteByCategoryId(@Param("deleteDate") Date deleteDate, @Param("deleteBy") String deleteBy,
            @Param("categoryId") Long categoryId);
    
    
    /**
     * find NewsCategoryLanguageb by CategoryId and languageCode
     *
     * @param categoryId
     * @param languageCode
     * @return
     * @author hand
     */
    NewsCategoryLanguage findByCategoryIdAndLanguageCode(@Param("categoryId")Long categoryId, @Param("languageCode")String languageCode);
}
