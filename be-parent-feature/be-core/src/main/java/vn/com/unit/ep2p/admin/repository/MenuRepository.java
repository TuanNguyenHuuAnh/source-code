package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.entity.JcaMenu;
import vn.com.unit.core.repository.JcaMenuRepository;
import vn.com.unit.ep2p.admin.dto.MenuDto;
import vn.com.unit.ep2p.admin.dto.MenuLanguageDto;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;

public interface MenuRepository extends JcaMenuRepository {

	/**
	 * Find all menu by language
	 * 
	 * @param languageCode
	 * @return listMenuDto : List<MenuDto>
	 * @author trieunh <trieunh@unit.com.vn>
	 */
	public List<MenuDto> findAllByLanguage(@Param("languageCode") String languageCode,
			@Param("companyId") Long companyId);

	/**
	 * find all menu language
	 * 
	 * @param languageCode
	 * @return List<MenuDto>
	 * @author vinhnht
	 */
	public List<MenuDto> findAllMenuLanguage(@Param("languageCode") String languageCode,
			@Param("listFunctionCode") Iterable<String> listFunctionCode, @Param("menuType") String menuType);

	/**
	 * find sub menu
	 * 
	 * @param parentId
	 * @param languageCode
	 * @return List<MenuDto>
	 * @author vinhnht
	 */
	public List<MenuDto> findSubMenu(@Param("parentId") Long parentId, @Param("languageCode") String languageCode);

	/**
	 * count menu by condition
	 * 
	 * @param menu
	 * @return int
	 * @author vinhnht
	 */
	public int countMenuByCondition(@Param("menuDto") MenuDto menu, @Param("languageCode") String languageCode);

	/**
	 * find menu list by condition
	 * 
	 * @param startIndex
	 * @param sizeOfPage
	 * @param condition
	 * @param languageCode
	 * @return List<MenuDto>
	 * @author vinhnht
	 */
	public List<MenuDto> findMenuListByCondition(@Param("startIndex") int startIndex,
			@Param("sizeOfPage") int sizeOfPage, @Param("menuDto") MenuDto condition,
			@Param("languageCode") String languageCode);

	/**
	 * get menu dto
	 * 
	 * @param menuId
	 * @param languageCode
	 * @return MenuDto
	 * @author vinhnht
	 */
	public MenuDto getMenuDto(@Param("menuId") Long menuId, @Param("languageCode") String languageCode);

	/**
	 * Find list path menu parent by url
	 * 
	 * @param url type String
	 * @return List<MenuDto>
	 * @author vinhnht
	 */
	public List<MenuDto> findPathMenuByUrl(@Param("url") String url);

	/**
	 * findMenuByCodeAndCompanyId
	 * 
	 * @param code
	 * @param companyId
	 * @return
	 * @author HungHT
	 */
	public JcaMenu findMenuByCodeAndCompanyId(@Param("code") String code, @Param("companyId") Long companyId);

	/**
	 * Find list menu language
	 * 
	 * @param menuId
	 * @return List<MenuLanguageDto>
	 * @author vinhnht
	 */
	public List<MenuLanguageDto> findListMenuLanguage(@Param("menuId") Long menuId);

	/**
	 * Get max menu sort
	 * 
	 * @param parentId
	 * @param menuType
	 * @return Long
	 * @author vinhnht
	 */
	public Long getMaxMenuSort(@Param("parentId") Long parentId, @Param("menuType") String menuType);

	/**
	 * findAllMenByType
	 *
	 * @param languageCode
	 * @param menuType
	 * @return List<MenuDto>
	 * @author hand
	 */
	public List<MenuDto> findAllMenByType(@Param("languageCode") String languageCode,
			@Param("menuType") String menuType, @Param("companyId") Long companyId);

	/*-----------------------BEGIN_ORACLE----------------------*/

	// MenuRepository_findAllByLanguageOracle
	public List<MenuDto> findAllByLanguageOracle(@Param("languageCode") String languageCode);

	// MenuRepository_findAllMenuLanguageOracle
	public List<MenuDto> findAllMenuLanguageOracle(@Param("languageCode") String languageCode,
			@Param("listFunctionCode") Iterable<String> listFunctionCode, @Param("menuType") String menuType);

	// MenuRepository_findSubMenuOracle
	public List<MenuDto> findSubMenuOracle(@Param("parentId") Long parentId,
			@Param("languageCode") String languageCode);

	// MenuRepository_countMenuByConditionOracle
	public int countMenuByConditionOracle(@Param("menuDto") MenuDto menu, @Param("languageCode") String languageCode);

	// MenuRepository_findMenuListByConditionOracle
	public List<MenuDto> findMenuListByConditionOracle(@Param("startIndex") int startIndex,
			@Param("sizeOfPage") int sizeOfPage, @Param("menuDto") MenuDto condition,
			@Param("languageCode") String languageCode);

	// MenuRepository_getMenuDtoOracle
	public MenuDto getMenuDtoOracle(@Param("menuId") Long menuId, @Param("languageCode") String languageCode);

	// MenuRepository_findPathMenuByUrlOracle
	public List<MenuDto> findPathMenuByUrlOracle(@Param("url") String url);

	// MenuRepository_findMenuByOracle
	public JcaMenu findMenuByCodeOracle(@Param("code") String code);

	// MenuRepository_findListMenuLanguageOracle
	public List<MenuLanguageDto> findListMenuLanguageOracle(@Param("menuId") Long menuId);

	// MenuRepository_getMaxMenuSortOracle
	public Long getMaxMenuSortOracle(@Param("parentId") Long parentId, @Param("menuType") String menuType);

	// MenuRepository_findAllMenByTypeOracle
	public List<MenuDto> findAllMenByTypeOracle(@Param("languageCode") String languageCode,
			@Param("menuType") String menuType);

	/*-----------------------END_ORACLE----------------------*/

	/**
	 * checkExistUrl
	 *
	 * @param url
	 * @return
	 * @author HUNGHT
	 */
	List<MenuDto> checkExistUrl(@Param("url") String url);

	/**
	 * updateSortAll
	 *
	 * @param sortOderList
	 * @author hand
	 */
	@Modifying
	public void updateSortAll(@Param("cond") SortOrderDto sortOderList);

	/**
	 * findMenuRoot
	 * 
	 * @return
	 * @author HungHT
	 */
	public JcaMenu findMenuRoot(@Param("companyId") Long companyId);

	/**
	 * findAllMenuExcludeRoot
	 * 
	 * @return
	 * @author HungHT
	 */
	public List<JcaMenu> findAllMenuExcludeRoot();

	/**
	 * findAllByLanguageAndMainUrl
	 * 
	 * @param languageCode
	 * @param companyId
	 * @param mainUrl
	 * @return
	 * @author HungHT
	 */
	public List<MenuDto> findAllByLanguageAndMainUrl(@Param("languageCode") String languageCode,
			@Param("companyId") Long companyId, @Param("mainUrl") String mainUrl);

	/**
	 * countChildMenuByMenuId
	 * 
	 * @param menuId
	 * @return
	 * @author trieuvd
	 */
	public int countChildMenuByMenuId(@Param("menuId") Long menuId);

	// 2021 07 09 - LocLT - find menu by module code
    public List<MenuDto> findMenuByModuleAndLanguage(@Param("languageCode") String languageCode,
            @Param("companyId") Long companyId, @Param("includeModuleCode") List<String> includeModuleCode,
            @Param("excludeModuleCode") List<String> excludeModuleCode, @Param("menuTypes") List<String> menuTypes);

	// TaiTM: Tìm menu theo module
	public List<MenuDto> findMenuByMenuModule(@Param("languageCode") String languageCode,
			@Param("companyId") Long companyId, @Param("menuModule") List<String> menuModule);
}