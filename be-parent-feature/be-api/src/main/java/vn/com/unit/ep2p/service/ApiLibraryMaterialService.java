package vn.com.unit.ep2p.service;

import java.util.List;
import java.util.Locale;

import vn.com.unit.cms.core.module.libraryMaterial.dto.LibraryMaterialSearchResultDto;
import vn.com.unit.cms.core.module.libraryMaterial.dto.LibraryMaterialSuperDto;
import vn.com.unit.core.res.ObjectDataRes;

public interface ApiLibraryMaterialService {
	public int countLibraryMaterialByIdCategory(String searchKey, Long categoryId, Integer modeView, String language);

	public List<LibraryMaterialSearchResultDto> searchLibraryMaterialByIdCategory(String searchKey, Long categoryId, Integer page, Integer size,
			String language, Integer modeView);
	public List<LibraryMaterialSuperDto> getListSuperCategory(Long parentId,Locale language,String categoryType);
	
	ObjectDataRes<LibraryMaterialSuperDto> getListSubDoc(Long id, Locale locale, Integer page, Integer pageSize, String keySearch);

	public LibraryMaterialSuperDto getCategoryByConstant(String constantCode, Locale locale);

	public ObjectDataRes<LibraryMaterialSuperDto> getListSubDocGa(Locale locale, Integer page, Integer pageSize,
			String keySearch);
}
