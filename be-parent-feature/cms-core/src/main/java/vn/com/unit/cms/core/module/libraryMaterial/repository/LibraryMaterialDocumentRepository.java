package vn.com.unit.cms.core.module.libraryMaterial.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.core.module.document.entity.Document;
import vn.com.unit.cms.core.module.libraryMaterial.dto.LibraryMaterialSearchResultDto;
import vn.com.unit.db.repository.DbRepository;

public interface LibraryMaterialDocumentRepository extends DbRepository<Document, Long>{
	public int countLibraryMaterialByIdCategory(@Param("searchKey")String searchKey,@Param("categoryId")Long categoryId, @Param("modeView")Integer modeView, @Param("language")String language);

	public List<LibraryMaterialSearchResultDto> searchLibraryMaterialByIdCategory(
			@Param("offset") int offset, @Param("size") int size,
			@Param("searchKey")String searchKey,@Param("categoryId")Long categoryId, @Param("language")String language, @Param("modeView")Integer modeView);
}
