package vn.com.unit.cms.core.module.libraryMaterial.repository;

import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.core.module.document.entity.DocumentCategory;
import vn.com.unit.cms.core.module.libraryMaterial.dto.LibraryMaterialSuperDto;
import vn.com.unit.db.repository.DbRepository;

import java.util.List;

public interface LibraryMaterialRepository extends DbRepository<DocumentCategory, Long> {
    public List<LibraryMaterialSuperDto> getListSuperCategory(@Param("parentId") Long parentId, @Param("language") String language
            , @Param("isCandidate") boolean isCandidate, @Param("categoryType") String categoryType, @Param("channel") String channel, @Param("partners") List<String> partners);

    int countSubCategoryById(@Param("id") Long id, @Param("locale") String locale,  @Param("keySearch")String keySearch, @Param("channel")String channel);

    List<LibraryMaterialSuperDto> findSubCategoryById(@Param("id") Long id, @Param("locale") String locale
            , @Param("offset") int offsetSQL, @Param("pageSize") Integer pageSize, @Param("keySearch") String keySearch, @Param("channel")String channel);

    public LibraryMaterialSuperDto getCategoryByConstant(@Param("constantCode") String constantCode, @Param("lang") String lang);

	public List<LibraryMaterialSuperDto> findSubCategoryGa(String string, int offsetSQL, Integer pageSize,
			String keySearch);
}
