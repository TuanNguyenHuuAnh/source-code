package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.core.entity.CategoryLanguage;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.core.efo.dto.EfoCategoryLangDto;

/**
 * CategoryLanguageRepository
 * @author trieuvd
 *
 */
public interface CategoryLanguageRepository extends DbRepository<CategoryLanguage, Long>{

    /**
     * getByCategoryID
     * @param id
     * @return List CategoryLanguage
     * @author trieuvd
     */
    List<CategoryLanguage> getByCategoryID(@Param("categoryID") Long categoryId);

    /**
     * getListToCheckName
     * @param categoryName
     * @param langCode
     * @param categoryId
     * @param companyId
     * @return
     * @author trieuvd
     */
    List<EfoCategoryLangDto> getListToCheckName(@Param("categoryName") String categoryName, @Param("languageCode") String langCode, @Param("categoryID") Long categoryId, @Param("companyId") Long companyId);

    public CategoryLanguage findByCategoryIdAndLangId(@Param("categoryId")Long categoryId, @Param("langId")Long langId);
}
