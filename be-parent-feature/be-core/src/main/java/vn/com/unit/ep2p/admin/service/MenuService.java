package vn.com.unit.ep2p.admin.service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.entity.JcaMenu;
import vn.com.unit.core.entity.JcaMenuLang;
import vn.com.unit.core.service.JcaMenuService;
import vn.com.unit.ep2p.admin.dto.ItemDto;
import vn.com.unit.ep2p.admin.dto.MenuDto;
import vn.com.unit.ep2p.admin.dto.MenuLanguageDto;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.treebuilder.utils.MenuNode;

public interface MenuService extends JcaMenuService{
    /**
     * findAllMenuByLanguageCode
     *
     * @param languageCode
     * @return List<MenuDto>
     * @author VinhNHT
     */
    public List<MenuDto> findAllMenuByLanguageCode(String languageCode);

    /**
     * search
     *
     * @param page
     * @param menu
     * @param lang
     * @return PageWrapper<MenuDto>
     * @author VinhNHT
     */
    public PageWrapper<MenuDto> search(int page, MenuDto menu, String lang);

    /**
     * getMenuDto
     *
     * @param menuId
     * @param lang
     * @param locale
     * @return MenuDto
     * @author VinhNHT
     */
    public MenuDto getMenuDto(Long menuId, String lang, Locale locale);

    /**
     * findAllMenu
     *
     * @param lang
     * @return List<MenuDto>
     * @author VinhNHT
     */
    public List<MenuDto> findAllMenu(String lang);

    /**
     * saveMenu
     *
     * @param menu
     * @author VinhNHT
     */
    public void saveMenu(JcaMenu menu);

    /**
     * saveMenuLanguage
     *
     * @param menuLanguage
     * @author VinhNHT
     */
    public void saveMenuLanguage(JcaMenuLang menuLanguage);

    /**
     * saveMenu
     *
     * @param menuDto
     * @param locale
     * @author VinhNHT
     */
    public void saveMenu(MenuDto menuDto, Locale locale) throws IOException;

    /**
     * findMenu
     *
     * @param menuId
     * @return Menu
     * @author VinhNHT
     */
    public JcaMenu findMenu(Long menuId);

    /**
     * findMenuLanguage
     *
     * @param menuLanguageId
     * @return MenuLanguage
     * @author VinhNHT
     */
    public JcaMenuLang findMenuLanguage(Long menuLanguageId);

    /**
     * deleteMenu
     *
     * @param menuId
     * @author TranLTH
     */
    public void deleteMenu(Long menuId);

    /**
     * findPathMenuByUrl
     *
     * @param url
     * @return List<MenuDto>
     * @author VinhNHT
     */
    public List<MenuDto> findPathMenuByUrl(String url);

    /**
     * findMenuByCode
     *
     * @param code
     * @return Menu
     * @author VinhNHT
     */
    public JcaMenu findMenuByCodeAndCompanyId(String code, Long companyId);

    /**
     * findMenuTree
     *
     * @param lang
     * @param menuType
     * @return List<MenuNode>
     * @author VinhNHT
     */
    public List<MenuNode> findMenuTree(String lang, String menuType, Long companyId, Long rootId);

    /**
     * findListMenuLanguage
     *
     * @param menuId
     * @return List<MenuNode>
     * @author VinhNHT
     */
    public List<MenuLanguageDto> findListMenuLanguage(Long menuId);

    /**
     * getMaxMenuSort
     *
     * @param parentId
     * @param menuType
     * @return Long
     * @author VinhNHT
     */
    public Long getMaxMenuSort(Long parentId, String menuType);

    /**
     * Build list item for select box
     * 
     * @param locale
     * @return listItemDto : List<Item>
     * @author trieunh <trieunh@unit.com.vn>
     */
    public List<ItemDto> buildListItemDto(Locale locale);

    /**
     * Build structure for menu
     * 
     * @param languageCode
     * @return MenuDto
     * @author trieunh <trieunh@unit.com.vn>
     */
    public MenuDto buildMenuStructure(String languageCode, Long companyId);

    /**
     * Build navigation bar
     * 
     * @param urlMenu
     * @param languageCode
     * @return navigation
     * @author trieunh <trieunh@unit.com.vn>
     */
    public String buildNavigationBar(String urlMenu, String languageCode);


    /**
     * initScreenMenuEdit
     *
     * @param mav
     * @param language
     * @author TranLTH
     */
    public void initScreenMenuEdit(ModelAndView mav, Locale locale, String webKind, MenuDto menuDto);

    /**
     * getMenuTypeList
     *
     * @param type
     *            true: back-end, false: front-end
     * @return
     * @author hand
     */
    public List<String> getMenuTypeList(boolean type);

    /**
     * 
     * @param menuDto
     * @param locale
     */
    public void saveMenuDMS(MenuDto menuDto, Locale locale);

    /**
     * 
     * @param menuId
     */
    public void deleteMenuDMS(Long menuId);

    /**
     * Sort list menu by constructor tree
     * 
     * @param menuList
     *            type List<MenuDto>
     * @return List<MenuDto>
     * @author vinhnht
     */
    public List<MenuDto> sortMenuDtoByConstructorTree(List<MenuDto> menuList, List<MenuDto> resultRoot);

    /**
     * Get list menuDto root by menuList
     * 
     * @param menuList
     *            type List<MenuDto>
     * @return List<MenuDto>
     * @author vinhnht
     */
    public List<MenuDto> getMenuRoot(List<MenuDto> menuList, Long rootId);

    /**
     * getTreeNode
     *
     * @param menuNode
     * @param listAllMenu
     * @return MenuNode
     * @author hand
     */
    public MenuNode getTreeNode(MenuNode menuNode, List<MenuDto> listAllMenu);
    
    /** 
     * checkExistUrl
     *
     * @param url
     * @return
     * @author HUNGHT
     */
    public boolean checkExistUrl(String url);
    
    /**
     * find Menu List For Sorting
     *
     * @param parentId
     * @return List<MenuDto>
     * @author hand
     */
    public List<MenuDto> findMenuListForSorting(Long parentId, String languageCode);
    
    /**
     * updateSortAll
     *
     * @param sortOderList
     * @author hand
     */
    public void updateSortAll(List<SortOrderDto> sortOderList);
    
    /**
     * buildMenuForNewCompany
     * 
     * @param companyId
     * @author HungHT
     */
    void buildMenuForNewCompany(Long companyId) throws Exception;
    
    /**
     * findMenuRoot
     * 
     * @param companyId
     * @return
     * @author HungHT
     */
    public JcaMenu findMenuRoot(Long companyId);
}