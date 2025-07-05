package vn.com.unit.ep2p.admin.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.common.utils.CommonObjectUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.entity.JcaMenu;
import vn.com.unit.core.entity.JcaMenuLang;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.AppLanguageService;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.core.service.JcaMenuPathService;
import vn.com.unit.core.service.impl.JcaMenuServiceImpl;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.ItemDto;
import vn.com.unit.ep2p.admin.dto.MenuDto;
import vn.com.unit.ep2p.admin.dto.MenuLanguageDto;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.admin.enumdef.ConstantDisplayType;
import vn.com.unit.ep2p.admin.enumdef.DatabaseTypeEnum;
import vn.com.unit.ep2p.admin.enumdef.MenuSearchEnum;
import vn.com.unit.ep2p.admin.enumdef.MenuTypeEnum;
import vn.com.unit.ep2p.admin.enumdef.MenuTypeEnum.MenuTypeCatEnum;
import vn.com.unit.ep2p.admin.repository.ItemRepository;
import vn.com.unit.ep2p.admin.repository.MenuRepository;
import vn.com.unit.ep2p.admin.service.AbstractCommonService;
import vn.com.unit.ep2p.admin.service.BeAdminFileService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.ManualService;
import vn.com.unit.ep2p.admin.service.MenuLangService;
import vn.com.unit.ep2p.admin.service.MenuService;
import vn.com.unit.ep2p.admin.utils.Utility;
import vn.com.unit.ep2p.configurer.ModuleConfig;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.treebuilder.utils.MenuNode;

@Service
@Transactional
public class MenuServiceImpl extends JcaMenuServiceImpl implements MenuService, AbstractCommonService {

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    MenuLangService menuLangService;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AppLanguageService languageService;

    @Autowired
    private JcaMenuPathService jcaMenuPathService;

    @Autowired
    private JcaConstantService jcaConstantService;
    // @Autowired
    // private JBPMService jBPMService;

    @Autowired
    BeAdminFileService fileService;

    @Autowired
    ManualService manualService;

    @Autowired
    CompanyService companyService;

    @Autowired
    private CommonService comService;
    
//    @Autowired
//    private List<ModuleConfig> moduleConfig;
    
    @Autowired
    private List<ModuleConfig> moduleConfig;

    // Model mapper
    ModelMapper modelMapper = new ModelMapper();

    private static final int MAX_PAGE_SIZE = Integer.MAX_VALUE;

    private static final String MENU_ROOT = "ROOT";

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    /**
     * Build structure for menu
     * 
     * @param languageCode
     * @return listMenuDto : List<MenuDto>
     * @author trieunh <trieunh@unit.com.vn>
     */
    public MenuDto buildMenuStructure(String languageCode, Long companyId) {
        List<MenuDto> listMenuDto = new ArrayList<>();

        // Check to init menu for new company
        JcaMenu menuroot = menuRepository.findMenuRoot(companyId);
        if (null == menuroot) {
            try {
                this.buildMenuForNewCompany(companyId);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        try {
        	List<String> includeModuleCode = new ArrayList<>();
        	List<String> excludeModuleCode = new ArrayList<>();
        	Set<String> menuTypes = new HashSet<>();
        	
        	Set<String> menuModule = new HashSet<>();
        	
            for (ModuleConfig m : moduleConfig) {
                Map<String, List<String>> mapIn = m.getIncludeModuleCode();
                if (mapIn != null) {
                    for (String type : mapIn.keySet()) {
                        menuTypes.add(type);
                        if (!mapIn.get(type).isEmpty()) {
                            includeModuleCode.addAll(mapIn.get(type));
                        }
                        
                    }
                }

                Map<String, List<String>> mapEx = m.getExcludeModuleCode();
                if (mapEx != null) {
                    for (String type : mapEx.keySet()) {
                        if (!mapEx.get(type).isEmpty()) {
                            excludeModuleCode.addAll(mapEx.get(type));
                        }
                    }
                }
            }
        	
        	excludeModuleCode.removeAll(includeModuleCode);

			if (CommonCollectionUtil.isNotEmpty(includeModuleCode)) {
//                listMenuDto = menuRepository.findMenuByModuleAndLanguage(languageCode.toUpperCase(), companyId,
//                        includeModuleCode, excludeModuleCode, new ArrayList<>(menuTypes));
				if (!includeModuleCode.isEmpty()) {
					for (String module : includeModuleCode) {
						menuModule.add(module);
					}
				}
				
				listMenuDto = menuRepository.findMenuByMenuModule(languageCode.toUpperCase(), companyId,
						new ArrayList<String>(menuModule));
			} else {
				listMenuDto = menuRepository.findAllByLanguage(languageCode.toUpperCase(), companyId);
			}
		} catch (Exception e) {
			listMenuDto = menuRepository.findAllByLanguage(languageCode.toUpperCase(), companyId);
		}

        Hashtable<Long, MenuDto> hashTableMenu = new Hashtable<>();

        for (int i = 0; i < listMenuDto.size(); i++) {
            MenuDto menuDto = listMenuDto.get(i);

            List<MenuDto> listSubMenuDto = new ArrayList<>();
            for (int j = 0; j < listMenuDto.size(); j++) {
                MenuDto subMenuDto = listMenuDto.get(j);
                if (menuDto.getMenuId().equals(subMenuDto.getParentId())) {
                    MenuDto subNode = hashTableMenu.get(subMenuDto.getMenuId());
                    if (subNode == null) {
                        subNode = new MenuDto();
                        subNode.setMenuId(subMenuDto.getMenuId());
                        subNode.setMenuName(subMenuDto.getMenuName());
                        subNode.setUrl(subMenuDto.getUrl());
                        subNode.setCheckOpen(subMenuDto.getCheckOpen());
                        subNode.setIcon(subMenuDto.getIcon());
                        subNode.setMenuType(subMenuDto.getMenuType());
                        subNode.setMenuCode(subMenuDto.getMenuCode());
                        hashTableMenu.put(subMenuDto.getMenuId(), subNode);
                    }
                    // truonghop lien ket quen o menu sub
                    if (subMenuDto.getItemId() != null && subMenuDto.getItemId() != 0) {
                        // String functionCode =
                        // subMenuDto.getFunctionCode().concat(ConstantCore.COLON_DISP);
                        String functionCode = subMenuDto.getFunctionCode();
                        if (checkRole(functionCode)) {
                            listSubMenuDto.add(subNode);
                        }
                    } else {// truong hop khong lien ket quyen
                        listSubMenuDto.add(subNode);
                    }

                }
            }

            MenuDto node = hashTableMenu.get(menuDto.getMenuId());
            if (node == null) {
                node = new MenuDto();
                node.setMenuId(menuDto.getMenuId());
                node.setMenuName(menuDto.getMenuName());
                node.setUrl(menuDto.getUrl());
                node.setCheckOpen(menuDto.getCheckOpen());
                node.setIcon(menuDto.getIcon());
                node.setMenuType(menuDto.getMenuType());
                node.setMenuCode(menuDto.getMenuCode());
            }
            // truonghop lien ket quen o menu
            if (menuDto.getItemId() != null && menuDto.getItemId() != 0) {
                // String functionCode =
                // menuDto.getFunctionCode().concat(ConstantCore.COLON_DISP);
                String functionCode = menuDto.getFunctionCode();
                if (checkRole(functionCode)) {
                    node.setListSubMenu(listSubMenuDto);
                    hashTableMenu.remove(menuDto.getMenuId());
                    hashTableMenu.put(menuDto.getMenuId(), node);
                }
            } else {// truong hop khong lien ket quyen
                node.setListSubMenu(listSubMenuDto);
                hashTableMenu.remove(menuDto.getMenuId());
                hashTableMenu.put(menuDto.getMenuId(), node);
            }

        }
        MenuDto root = null;
        for (MenuDto orgTmp : listMenuDto) {
            if (orgTmp.getParentId().equals(0L)) {
                root = hashTableMenu.get(orgTmp.getMenuId());
                break;
            }
        }

        return root;
    }

    public Boolean checkRole(String functionCode) {
        if (StringUtils.isNotBlank(functionCode) && UserProfileUtils.hasRole(functionCode)
                || UserProfileUtils.hasRole(functionCode + ConstantCore.COLON_EDIT)
                || UserProfileUtils.hasRole(functionCode + ConstantCore.COLON_DISP)
                || UserProfileUtils.hasRole(functionCode + ConstantCore.COLON_DELETE)) {
            return true;
        }
        return false;
    }

    /**
     * Build navigation bar
     * 
     * @param urlMenu
     * @param languageCode
     * @return navigation
     * @author trieunh <trieunh@unit.com.vn>
     */
    public String buildNavigationBar(String urlMenu, String languageCode) {
        String navigation = "";
        Long parrentId = -2L;
        String url = UrlConst.ROOT;
        if (!UrlConst.ROOT.equals(urlMenu)) {
            String[] arrUrl = urlMenu.split(UrlConst.ROOT);
            if (arrUrl.length > 1) {
                url = url.concat(arrUrl[1]).concat(UrlConst.ROOT);
            }
        }

        List<MenuDto> listMenuDto = new ArrayList<>();
        listMenuDto = menuRepository.findAllByLanguageAndMainUrl(languageCode, UserProfileUtils.getCompanyId(), url);

        Hashtable<Long, MenuDto> hashtableMenuName = new Hashtable<>();
        for (MenuDto menuDto : listMenuDto) {
            hashtableMenuName.put(menuDto.getMenuId(), menuDto);
            // if (menuDto.getUrl().equals(urlMenu)) {
            if (!UrlConst.ROOT.equals(menuDto.getUrl())
                    && (urlMenu.contains(menuDto.getUrl()) || menuDto.getUrl().contains(url))) {
                // Find first name with URL
                navigation = menuDto.getMenuName();
                parrentId = menuDto.getParentId();
            }
        }

        while (!parrentId.equals(-1L)) {
            MenuDto menuDtoTmp = hashtableMenuName.get(parrentId);
            if (menuDtoTmp != null) {
                navigation = menuDtoTmp.getMenuName() + " / " + navigation;
                parrentId = menuDtoTmp.getParentId();
            } else {
                parrentId = -1L;
            }
        }
        return navigation;
    }

    /**
     * Find all menu by language code
     * 
     * @param languageCode type string
     * @return List<MenuDto>
     * @author vinhnht
     */
    @Override
    public List<MenuDto> findAllMenuByLanguageCode(String languageCode) {
        // Long accountId = UserProfileUtils.getUserProfile().getAccountId();
        List<MenuDto> listMenu = new ArrayList<>();
        listMenu = menuRepository.findAllMenuLanguage(languageCode, UserProfileUtils.findOnlyFunctionCode(), "1");

        List<MenuDto> listRoot = new LinkedList<MenuDto>();
        if (null != listMenu) {
            for (MenuDto menu : listMenu) {
                if (null == menu.getParentId() || menu.getParentId().equals(0L))
                    listRoot.add(menu);
            }

            for (MenuDto menu : listRoot) {
                menu = getTreeMenu(menu, listMenu);
            }
        }
        return listRoot;
    }

    /**
     * Get tree menu by root
     * 
     * @param menuDto
     * @param listAllMenu
     * @return MenuDto
     * @author vinhnht
     */
    public MenuDto getTreeMenu(MenuDto menuDto, List<MenuDto> listAllMenu) {
        if (null != menuDto.getParentId()) {
            List<MenuDto> listSub = getListSubMenu(menuDto.getMenuId(), listAllMenu);
            menuDto.setListSubMenu(listSub);
            if (null != listSub) {
                for (MenuDto menuSub : listSub) {
                    menuSub = getTreeMenu(menuSub, listAllMenu);
                }
            }
        }
        return menuDto;
    }

    /**
     * Get list sub menu of root
     * 
     * @param menuId
     * @param listAllMenu
     * @return List<MenuDto>
     * @author vinhnht
     */
    public List<MenuDto> getListSubMenu(Long menuId, List<MenuDto> listAllMenu) {
        List<MenuDto> listSubmenu = new LinkedList<MenuDto>();
        for (MenuDto menu : listAllMenu) {
            if (menu.getParentId().equals(menuId))
                listSubmenu.add(menu);
        }
        return listSubmenu;
    }

    /**
     * Search all menu
     * 
     * @param page
     * @param menu
     * @return PageWrapper<Menu>
     * @author vinhnht
     */
    public PageWrapper<MenuDto> search(int page, MenuDto menuDtoSearch, String lang) {
        // int sizeOfPage = systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
        int sizeOfPage = MAX_PAGE_SIZE;
        PageWrapper<MenuDto> pageWrapper = new PageWrapper<MenuDto>(page, sizeOfPage);
        if (null == menuDtoSearch)
            menuDtoSearch = new MenuDto();
        // set SearchParm
        setSearchParm(menuDtoSearch);
        int count = manualService.countMenuByCondition(menuDtoSearch, lang);

        int countMenuRoot = 0;
        int menuRootAdded = 0;
        List<MenuDto> result = new LinkedList<MenuDto>();
        int menuIndex = 0;
        List<MenuDto> listMenuPage = new LinkedList<MenuDto>();
        int currentPage = pageWrapper.getCurrentPage();
        if (count > 0) {

            int startIndex = (currentPage - 1) * sizeOfPage;

            result = manualService.findMenuListByCondition(startIndex, sizeOfPage, menuDtoSearch, lang);

            JcaMenu root = menuRepository.findMenuRoot(menuDtoSearch.getCompanyId());
            Long rootId = 1L;
            if (null != root) {
                rootId = root.getId();
            }

            if (StringUtils.isNotEmpty(menuDtoSearch.getFieldSearch()))
                result = getParentLine(result, lang, menuDtoSearch.getCompanyId(), rootId);

            // resultRoot
            List<MenuDto> resultRoot = getMenuRoot(result, rootId);

            result = sortMenuDtoByConstructorTree(result, resultRoot);

            countMenuRoot = resultRoot.size();

            int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);

            boolean startCount = true;
            for (MenuDto menuDto : result) {

                if (page > 1 && startCount) {
                    menuIndex++;
                }

                if (rootId != null && rootId.equals(menuDto.getParentId())) {
                    menuRootAdded++;
                }

                if (offsetSQL < menuRootAdded && menuRootAdded <= offsetSQL + sizeOfPage) {
                    startCount = false;
                    boolean deleteFlag = menuRepository.countChildMenuByMenuId(menuDto.getMenuId()) > 0 ? false : true;
                    menuDto.setDeleteFlag(deleteFlag);
                    listMenuPage.add(menuDto);
                }
            }
        }

        pageWrapper.setDataAndCount(listMenuPage, countMenuRoot);
        if (page > 1) {
            pageWrapper.setStartIndexCurrent(menuIndex);
        }

        return pageWrapper;
    }

    /**
     * Sort list menu by constructor tree
     * 
     * @param menuList type List<MenuDto>
     * @return List<MenuDto>
     * @author vinhnht
     */
    @Override
    public List<MenuDto> sortMenuDtoByConstructorTree(List<MenuDto> menuList, List<MenuDto> resultRoot) {
        // Find roots
        List<MenuDto> result = new ArrayList<>();

        result.addAll(resultRoot);

        int i = 0;
        while (i < result.size()) {
            MenuDto menu = result.get(i);
            Long menuId = menu.getMenuId();

            List<MenuDto> menuChildren = new ArrayList<MenuDto>();
            if (menuList != null && !menuList.isEmpty()) {

                int menuListSize = menuList.size();

                for (int j = menuListSize - 1; j >= 0; j--) {
                    MenuDto menuDto = menuList.get(j);

                    Long parentId = menuDto.getParentId();

                    if (parentId != null && parentId.equals(menuId)) {
                        result.add(i + 1, menuDto);
                        menuChildren.add(menuDto);
                    }
                }

                menuList.removeAll(menuChildren);
                i++;
            } else {
                break;
            }

        }
        // Collections.sort(result, new SortChildrentMenu());
        return result;
    }

    /**
     * Get list menuDto root by menuList
     * 
     * @param menuList type List<MenuDto>
     * @return List<MenuDto>
     * @author vinhnht
     */
    @Override
    public List<MenuDto> getMenuRoot(List<MenuDto> menuList, Long rootId) {
        List<MenuDto> result = new ArrayList<MenuDto>();

        // Find roots
        for (int i = 0; i < menuList.size(); i++) {
            MenuDto menuDto = menuList.get(i);
            if (null == menuDto.getParentId() || menuDto.getParentId().equals(rootId)) {
                menuDto.setRoot(true);
                result.add(menuDto);
            }
        }

        return result;
    }

    /**
     * Get menu Dto
     * 
     * @param menuId
     * @param lang
     * @return MenuDto
     * @author vinhnht
     */
    public MenuDto getMenuDto(Long menuId, String lang, Locale locale) {
        MenuDto menuDto = new MenuDto();
        List<MenuLanguageDto> listMenuLanguageTmp = new ArrayList<MenuLanguageDto>();
        if (StringUtils.equals(DatabaseTypeEnum.ORACLE.toString(), systemConfig.getConfig(SystemConfig.DBTYPE))) {
            menuDto = menuRepository.getMenuDto(menuId, lang);
        } else {
            menuDto = menuRepository.getMenuDto(menuId, lang);
        }
        if (null == menuDto) {
            return null;
        }
        listMenuLanguageTmp = findListMenuLanguage(menuId);
        // Set list item
        // menuDto.setListItemDto(buildListItemDto(locale));

        // set item
        String itemName = menuDto.getItemName();
        if (StringUtils.isBlank(itemName)) {
            menuDto.setItemId(0L);
            itemName = messageSource.getMessage(ConstantCore.MESSAGE_NONE, null, locale);
            menuDto.setItemName(itemName);
        }

        List<MenuLanguageDto> listMenuLanguage = new ArrayList<MenuLanguageDto>();
        List<LanguageDto> languageList = languageService.getListByCompanyId(UserProfileUtils.getCompanyId());
        // Build language for menu when edit. listMenuLanguageTmp not empty.
        for (LanguageDto languageDto : languageList) {
            boolean checkExist = false;
            for (MenuLanguageDto menuLanguageDto : listMenuLanguageTmp) {
                if (languageDto.getId().equals(menuLanguageDto.getLanguageId())) {
                    MenuLanguageDto newMenuLanguage = new MenuLanguageDto();
                    newMenuLanguage.setLanguageCode(menuLanguageDto.getLanguageCode());
                    newMenuLanguage.setLanguageId(menuLanguageDto.getLanguageId());
                    newMenuLanguage.setLanguageName(menuLanguageDto.getLanguageName());
                    newMenuLanguage.setAlias(menuLanguageDto.getAlias());
                    newMenuLanguage.setMenuLanguageId(menuLanguageDto.getMenuLanguageId());
                    listMenuLanguage.add(newMenuLanguage);
                    checkExist = true;
                    break;
                }
            }
            if (!checkExist) {
                MenuLanguageDto newMenuLanguage = new MenuLanguageDto();
                newMenuLanguage.setLanguageCode(languageDto.getCode());
                newMenuLanguage.setLanguageId(languageDto.getId());
                listMenuLanguage.add(newMenuLanguage);
            }
        }
        menuDto.setListMenuLanguage(listMenuLanguage);
        JcaMenu root = menuRepository.findMenuRoot(menuDto.getCompanyId());
        Long rootId = 0L;
        if (null != root) {
            rootId = root.getId();
        }
        if (null != menuDto && null != menuDto.getParentId() && menuDto.getParentId().equals(rootId)) {
            menuDto.setParentId(rootId);
        }
        return menuDto;
    }

    /**
     * Build list item for select box
     * 
     * @param locale
     * @return listItemDto : List<Item>
     * @author trieunh <trieunh@unit.com.vn>
     */
    @Override
    public List<ItemDto> buildListItemDto(Locale locale) {

        List<ItemDto> listItem = itemRepository.findItemBySubType("LINK");

        List<ItemDto> listItemDto = new ArrayList<>();
        ItemDto defaultItem = new ItemDto(0, messageSource.getMessage(ConstantCore.MESSAGE_NONE, null, locale));
        listItemDto.add(defaultItem);
        for (ItemDto item : listItem) {
            listItemDto.add(new ItemDto(item.getId(), item.getFunctionName()));
        }
        return listItemDto;

    }

    /**
     * Find all menu
     * 
     * @param lang
     * @return List<MenuDto>
     */
    public List<MenuDto> findAllMenu(String lang) {
        // Long accountId = UserProfileUtils.getUserProfile().getAccountId();

        List<String> listRole = UserProfileUtils.findOnlyFunctionCode();

        List<MenuDto> lstMenuDto = menuRepository.findAllMenuLanguage(lang, listRole, "1");
        return lstMenuDto;
    }

    /**
     * Save menu
     * 
     * @param menu
     * @author vinhnht
     */
    public void saveMenu(JcaMenu menu) {
        menuRepository.save(menu);
    }

    /**
     * Save menu Language
     * 
     * @param menuLanguage
     * @author vinhnht
     */
    public void saveMenuLanguage(JcaMenuLang menuLanguage) {
        menuLangService.save(menuLanguage);
    }

    /**
     * Find Menu
     * 
     * @param menuId
     * @return Menu
     * @author vinhnht
     */
    @Override
    public JcaMenu findMenu(Long menuId) {
        return menuRepository.findOne(menuId);
    }

    /**
     * Find menu Language
     * 
     * @param menuLanguageId
     * @return MenuLanguage
     * @author vinhnht
     */
    @Override
    public JcaMenuLang findMenuLanguage(Long menuLanguageId) {
        return menuLangService.findOne(menuLanguageId);
    }

    /**
     * save MenuDto
     * 
     * @param menu
     * @param listMenuLanguage
     * @author vinhnht
     * @throws IOException
     */
    @Override
    public void saveMenu(MenuDto menuDto, Locale locale) throws IOException {
        // Add process role
//		Long processId = processService.getProcessIdByBusinessCode(MasterType.SM1.toString());

//        UserPrincipal userProfile = UserProfileUtils.getUserPrincipal();

        // Check create menu
        if (null != menuDto) {
            JcaMenu menu = new JcaMenu();
            if (null != menuDto.getMenuId())
                menu = findMenu(menuDto.getMenuId());

            // create menu
            if (null == menuDto.getParentId())
                menu.setParentId(0l);
            else
                menu.setParentId(menuDto.getParentId());
            menu.setUrl(menuDto.getUrl().trim());
            if (null == menu.getCreatedDate()) {
                menu.setCreatedDate(comService.getSystemDateTime());
                menu.setCreatedId(UserProfileUtils.getAccountId());
            } else {
                menu.setUpdatedDate(comService.getSystemDateTime());
                menu.setUpdatedId(UserProfileUtils.getAccountId());
            }
            menu.setCode(menuDto.getMenuCode().trim());
            if (null != menuDto.getSort())
                menu.setDisplayOrder(menuDto.getSort());
            if (null != menuDto.getIcon() && !menuDto.getIcon().isEmpty())
                menu.setClassName(menuDto.getIcon());
            if (null != menuDto.getMenuType())
                menu.setMenuType(Integer.parseInt(menuDto.getMenuType()));
//			menu.setCheckOpen(menuDto.getCheckOpen());
            menu.setActived(menuDto.getActiveMenu());
            // Set item id for menu
            menu.setItemId(menuDto.getItemId());

            // physical icon
//            String physicalIconTmpName = menuDto.getPhysicalIcon();
            // upload images
//			if (StringUtils.isNotEmpty(physicalIconTmpName)) {
//				String newPhiscalName = fileService.moveFileFromTempToFolderUploadMain(physicalIconTmpName, MENU_FOLDER);
//				menu.setPhysicalIcon(newPhiscalName);
//				menu.setIconImg(menuDto.getIconImg());
//			} else {
//				menu.setPhysicalIcon(null);
//				menu.setIconImg(null);
//			}

            List<JcaMenuLang> listMenuLanguage = new ArrayList<JcaMenuLang>();
            for (MenuLanguageDto menuLanguage : menuDto.getListMenuLanguage()) {
                JcaMenuLang menuLanguageDb = new JcaMenuLang();
                if (null != menuLanguage.getMenuLanguageId()) {
                    menuLanguageDb = findMenuLanguage(menuLanguage.getMenuLanguageId());
                }

                if (null == menuLanguageDb.getCreatedDate()) {
                    menuLanguageDb.setCreatedId(UserProfileUtils.getAccountId());
                    menuLanguageDb.setCreatedDate(comService.getSystemDateTime());
                } else {
                    menuLanguageDb.setUpdatedDate(comService.getSystemDateTime());
                    menuLanguageDb.setUpdatedId(UserProfileUtils.getAccountId());
                }
                menuLanguageDb.setName(menuLanguage.getLanguageName().trim());
//				menuLanguageDb.setmLanguageId(menuLanguage.getLanguageId());
                menuLanguageDb.setNameAbv(menuLanguage.getAlias().trim());
                listMenuLanguage.add(menuLanguageDb);
            }

            if (null != menu.getId())
                menuDto.setMenuId(menu.getId());
            List<MenuLanguageDto> listMenuLanguageTmp = findListMenuLanguage(menuDto.getMenuId());
            List<MenuLanguageDto> listMenuLanguageDto = new ArrayList<MenuLanguageDto>();

            List<LanguageDto> languageList = languageService.getLanguageDtoList();
            for (LanguageDto languageDto : languageList) {
                for (MenuLanguageDto menuLanguageDto : listMenuLanguageTmp) {
                    if (languageDto.getId().equals(menuLanguageDto.getLanguageId())) {
                        MenuLanguageDto newMenuLanguage = new MenuLanguageDto();
                        newMenuLanguage.setLanguageCode(languageDto.getCode());
                        newMenuLanguage.setLanguageId(languageDto.getId());
                        newMenuLanguage.setLanguageName(menuLanguageDto.getLanguageName());
                        newMenuLanguage.setAlias(menuLanguageDto.getAlias());
                        newMenuLanguage.setMenuLanguageId(menuLanguageDto.getMenuLanguageId());
                        listMenuLanguageDto.add(newMenuLanguage);
                        break;
                    }
                }
            }
            if (null != listMenuLanguage) {
                menuDto.setListMenuLanguage(listMenuLanguageDto);
            }
//			menu.setOwnerBranchId(userProfile.getBranchId());
//			menu.setOwnerId(userProfile.getAccountId());
//			menu.setOwnerSectionId(userProfile.getDepartmentId());
//			if (menuDto.isAction()) {
//				menu.setStatusCode(MenuProcessEnum.SUBMIT.toString());
//			} else {
//				menu.setStatusCode(MenuProcessEnum.CREATE.toString());
//			}
//			menu.setProcessId(processId);
            // End add process

            /* --- Start jBPM process -------------------------------- */
            // vn.com.unit.jcanary.entity.Process process = processRepository
            // .findOne(processId);
            /*
             * if (process != null) { if (UserProfileUtils.hasRole(
             * RoleConstant.ROLE_USER.concat(ConstantCore.COLON_EDIT)) ||
             * UserProfileUtils.hasRole(RoleConstant.ROLE_USER
             * .concat(ConstantCore.COLON_DISP))) { // Check id and status to update jBPM if
             * (!menuDto.isAction() && null == menu.getId()) { // start jBPM Process and
             * action 'save' Hashtable<String, Object> params = new Hashtable<String,
             * Object>(); params.put(CommonConstant.PARAM_TYPE,
             * CommonConstant.PROCESS_MENU); params.put(CommonConstant.PARAM_ACTION,
             * CommonConstant.ACTION_VALUE_SAVE);
             * menu.setProcessInstanceId(jBPMService.startJBPMProcess(
             * process.getDeploymentId(), process.getProcessDefinitionId(),
             * RoleConstant.ROLE_USER, RoleConstant.ROLE_USER, params)); } if
             * (menuDto.isAction()) { if (null == menu.getId()) { // When user click direct
             * to 'submit' button, it // will start jBPM process first. // After it will
             * continue with updateJBPM status. Hashtable<String, Object> params = new
             * Hashtable<String, Object>(); params.put(CommonConstant.PARAM_TYPE,
             * CommonConstant.PROCESS_MENU); params.put(CommonConstant.PARAM_ACTION,
             * CommonConstant.ACTION_VALUE_SAVE); menu.setProcessInstanceId(jBPMService
             * .startJBPMProcess(process.getDeploymentId(),
             * process.getProcessDefinitionId(), RoleConstant.ROLE_USER,
             * RoleConstant.ROLE_USER, params)); } // action 'submit' Hashtable<String,
             * Object> params = new Hashtable<String, Object>();
             * params.put(CommonConstant.PARAM_TYPE, CommonConstant.PROCESS_MENU);
             * params.put(CommonConstant.PARAM_ACTION, CommonConstant.ACTION_VALUE_SUBMIT);
             * 
             * List<String> listCheck = new ArrayList<String>();
             * listCheck.add(CommonConstant.STATUS_SAVED);
             * listCheck.add(CommonConstant.STATUS_REJECTED);
             * 
             * jBPMService.updateJBPMStatus(process.getDeploymentId(),
             * menu.getProcessInstanceId(), RoleConstant.ROLE_USER, RoleConstant.ROLE_USER,
             * params, CommonConstant.PARAM_STATUS, listCheck); } } }
             */
            /* --- End jBPM process ---------------------------------- */

            menuRepository.save(menu);
            menuDto.setMenuId(menu.getId());

            for (JcaMenuLang menuLanguage : listMenuLanguage) {
                if (null != menu.getId()) {
                    menuLanguage.setMenuId(menu.getId());
                }
                menuLangService.save(menuLanguage);
            }
        }
    }

    @Override
    @Transactional
    public void deleteMenu(Long menuId) {
        Long userNameLogin = UserProfileUtils.getAccountId();
        JcaMenu menu = new JcaMenu();
        if (null != menuId) {
            menu = menuRepository.findOne(menuId);
        }
        // delete menu
        menu.setDeletedDate(comService.getSystemDateTime());
        menu.setDeletedId(userNameLogin);

        try {
            // delete menu
            menuRepository.save(menu);

            // delete job language
            menuLangService.deleteJcaMenuLangByMenuId(menu.getId());

            // delete path
            jcaMenuPathService.deleteMenuPathByDescendantId(menu.getId());
        } catch (Exception ex) {
            throw new SystemException(ex);
        }

    }

    /**
     * Find list path menu parent by url
     * 
     * @param url type String
     * @return List<MenuDto>
     * @author vinhnht
     */
    @Override
    public List<MenuDto> findPathMenuByUrl(String url) {
        return null;
    }

    /**
     * Find menu by code
     * 
     * @param code
     * @return Menu
     * @author vinhnht
     */
    @Override
    public JcaMenu findMenuByCodeAndCompanyId(String code, Long companyId) {
        return menuRepository.findMenuByCodeAndCompanyId(code, companyId);
    }

    /**
     * Find menu tree
     * 
     * @param lang
     * @return List<MenuNode>
     * @author vinhnht
     */
    @Override
    public List<MenuNode> findMenuTree(String lang, String menuType, Long companyId, Long rootId) {
        List<MenuDto> listMenuDto = new ArrayList<MenuDto>();
        if (StringUtils.equals(DatabaseTypeEnum.ORACLE.toString(), systemConfig.getConfig(SystemConfig.DBTYPE))) {
            listMenuDto = menuRepository.findAllMenByType(lang.toUpperCase(), menuType, companyId);
        } else {
            listMenuDto = menuRepository.findAllMenByType(lang, menuType, companyId);
        }

        List<MenuNode> menuTree = new LinkedList<MenuNode>();
        List<MenuDto> listRoot = new LinkedList<MenuDto>();
        if (null != listMenuDto && !listMenuDto.isEmpty()) {
            for (MenuDto menu : listMenuDto) {
                if (null == menu.getParentId() || menu.getParentId().equals(rootId))
                    listRoot.add(menu);
            }

            for (MenuDto menu : listRoot) {
                MenuNode menuNode = new MenuNode();
                menuNode.setId(menu.getMenuId());
                menuNode.setText(menu.getMenuName());
                menuNode.setState(ConstantCore.OPEN);
                menuNode = getTreeNode(menuNode, listMenuDto);
                menuTree.add(menuNode);
            }
        }

        // set root
        MenuNode nodeRoot = new MenuNode();
        nodeRoot.setId(rootId);
        nodeRoot.setText(MENU_ROOT);
        nodeRoot.setState(ConstantCore.OPEN);
        nodeRoot.setChildren(menuTree);

        List<MenuNode> treeResult = new LinkedList<MenuNode>();
        treeResult.add(nodeRoot);

        return treeResult;
    }

    /**
     * getTreeNode
     *
     * @param menuNode
     * @param listAllMenu
     * @return MenuNode
     * @author hand
     */
    @Override
    public MenuNode getTreeNode(MenuNode menuNode, List<MenuDto> listAllMenu) {

        List<MenuNode> listSub = getListNodeSubMenu(menuNode.getId(), listAllMenu);
        menuNode.setChildren(listSub);
        if (null != listSub) {
            for (MenuNode menuSub : listSub) {
                menuSub = getTreeNode(menuSub, listAllMenu);
            }
        }
        return menuNode;
    }

    public List<MenuNode> getListNodeSubMenu(Long menuId, List<MenuDto> listAllMenu) {
        List<MenuNode> listSubmenu = new LinkedList<MenuNode>();
        for (MenuDto menu : listAllMenu) {
            if (menu.getParentId().equals(menuId)) {
                MenuNode menuNode = new MenuNode();
                menuNode.setId(menu.getMenuId());
                menuNode.setText(menu.getMenuName());
                menuNode.setState(ConstantCore.OPEN);
                listSubmenu.add(menuNode);
            }
        }
        return listSubmenu;
    }

    /**
     * Find list menu language
     * 
     * @param menuId
     * @return List<MenuLanguageDto>
     * @author vinhnht
     */
    @Override
    public List<MenuLanguageDto> findListMenuLanguage(Long menuId) {
        return menuRepository.findListMenuLanguage(menuId);
    }

    /**
     * getMaxMenuSort
     *
     * @param parentId
     * @param menuType
     * @return Long
     * @author VinhNHT
     */
    @Override
    public Long getMaxMenuSort(Long parentId, String menuType) {
        return menuRepository.getMaxMenuSort(parentId, menuType);
    }

    /**
     * setSearchParm
     *
     * @param menuDtoSearch
     * @author TranLTH
     */
    private void setSearchParm(MenuDto menuDtoSearch) {
        if (null == menuDtoSearch.getFieldValues()) {
            menuDtoSearch.setFieldValues(new ArrayList<String>());
        }

        if (menuDtoSearch.getFieldValues().isEmpty()) {
            menuDtoSearch.setMenuCode(menuDtoSearch.getFieldSearch() != null ? menuDtoSearch.getFieldSearch().trim()
                    : menuDtoSearch.getFieldSearch());
            menuDtoSearch.setMenuName(menuDtoSearch.getFieldSearch() != null ? menuDtoSearch.getFieldSearch().trim()
                    : menuDtoSearch.getFieldSearch());
            menuDtoSearch.setUrl(menuDtoSearch.getFieldSearch() != null ? menuDtoSearch.getFieldSearch().trim()
                    : menuDtoSearch.getFieldSearch());

        } else {
            for (String field : menuDtoSearch.getFieldValues()) {
                if (StringUtils.equals(field, MenuSearchEnum.CODE.name())) {
                    menuDtoSearch.setMenuCode(menuDtoSearch.getFieldSearch().trim());
                    continue;
                }
                if (StringUtils.equals(field, MenuSearchEnum.NAME.name())) {
                    menuDtoSearch.setMenuName(menuDtoSearch.getFieldSearch().trim());
                    continue;
                }
                if (StringUtils.equals(field, MenuSearchEnum.URL.name())) {
                    menuDtoSearch.setUrl(menuDtoSearch.getFieldSearch().trim());
                    continue;
                }
                if (StringUtils.equals(field, MenuSearchEnum.TYPE.name())) {
                    menuDtoSearch.setMenuType(menuDtoSearch.getFieldSearch().trim());
                    continue;
                }
            }
        }

    }

    @Override
    public void initScreenMenuEdit(ModelAndView mav, Locale locale, String webKind, MenuDto menuDto) {
        // Init language
        try {
            List<LanguageDto> languageList = languageService.getListByCompanyId(UserProfileUtils.getCompanyId());
            mav.addObject("languageList", languageList);

            // Init menu tree
            JcaConstantDto menuTypeList = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind(webKind,
                    ConstantDisplayType.JCA_ADMIN_MENU.toString(), "MENU_TYPE", UserProfileUtils.getLanguage());
//			List<ConstantDisplay> menuTypeList = constDispService.findByTypeAndKind(ConstantDisplayType.M07, webKind);
            mav.addObject("menuTypeList", menuTypeList);

            // Init root id
            Long rootId = 0L;
            JcaMenu root = menuRepository.findMenuRoot(menuDto.getCompanyId());
            if (null != root) {
                rootId = root.getId();
            }
            if (null == menuDto.getMenuId()) {
                menuDto.setParentId(rootId);
            }
            mav.addObject("rootId", rootId);

            // menuTree
            List<MenuNode> menuTree = findMenuTree(locale.getLanguage(), menuTypeList.getCode(), menuDto.getCompanyId(),
                    rootId);
            String menuTreeJson = CommonJsonUtil.convertObjectToJsonString(menuTree);
            mav.addObject("menuList", menuTreeJson);

            // Init menu icon
            List<JcaConstantDto> menuIconList = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind(
                    ConstantDisplayType.JCA_ADMIN_MENU.toString(), "MENU_ICON", UserProfileUtils.getLanguage());
//			List<ConstantDisplay> menuIconList = constDispService.findByTypeAndKind(ConstantDisplayType.M08, webKind);
            mav.addObject("menuIconList", menuIconList);

            // Add company list
            List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(),
                    UserProfileUtils.isCompanyAdmin());
            mav.addObject("companyList", companyList);

            // Menu type select2
            List<Select2Dto> menuTypeSelect2Dto = MenuTypeEnum.getSelect2Dtos();
            mav.addObject("menuTypeSelect2Dto", menuTypeSelect2Dto);
            
            List<JcaConstantDto> menuModule = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind(
					ConstantDisplayType.JCA_ADMIN_MENU.toString(), "MENU_MODULE", UserProfileUtils.getLanguage());
			mav.addObject("menuModule", menuModule);
			
        } catch (Exception ex) {
            throw new SystemException(ex);
        }
    }

    /**
     * getMenuTypeList
     *
     * @param type true: back-end, false: front-end
     * @return
     * @author hand
     */
    @Override
    public List<String> getMenuTypeList(boolean type) {

        List<String> resultList = new ArrayList<String>();

        if (type) {
            resultList.add(MenuTypeCatEnum.BACK_END.toString());
            resultList.add(MenuTypeCatEnum.FRONT_END.toString());
            resultList.add(MenuTypeCatEnum.EXTERNAL.toString());
        } else {
            for (MenuTypeCatEnum menu : MenuTypeCatEnum.values()) {
                String menuType = menu.toString();

                if (!StringUtils.equals(menuType, MenuTypeEnum.BACK_END.toString())) {
                    resultList.add(menuType);
                }
            }
        }

        return resultList;
    }

    @Override
    public void saveMenuDMS(MenuDto menuDto, Locale locale) {

//        UserPrincipal userProfile = UserProfileUtils.getUserPrincipal();

        // Check create menu
        if (null != menuDto) {
            JcaMenu menu = new JcaMenu();
            if (null != menuDto.getMenuId())
                menu = findMenu(menuDto.getMenuId());

            // create menu
            if (null == menuDto.getParentId())
                menu.setParentId(0l);
            else
                menu.setParentId(menuDto.getParentId());
            menu.setUrl(menuDto.getUrl().trim());
            if (null == menu.getCreatedDate()) {
                menu.setCreatedDate(comService.getSystemDateTime());
                menu.setCreatedId(UserProfileUtils.getAccountId());
            } else {
                menu.setUpdatedDate(comService.getSystemDateTime());
                menu.setUpdatedId(UserProfileUtils.getAccountId());
            }
            menu.setCode(menuDto.getMenuCode().trim());
            if (null != menuDto.getSort())
                menu.setDisplayOrder(menuDto.getSort());
            if (null != menuDto.getIcon() && !menuDto.getIcon().isEmpty())
                menu.setClassName(menuDto.getIcon());
            if (null != menuDto.getMenuType())
                menu.setMenuType(Integer.parseInt(menuDto.getMenuType()));
//			menu.setCheckOpen(menuDto.getCheckOpen());
            menu.setActived(menuDto.getActiveMenu());
            // Set item id for menu
            menu.setItemId(menuDto.getItemId());

            if (null == menuDto.getMenuId()) {
                menu.setActived(true);
            }

            List<JcaMenuLang> listMenuLanguage = new ArrayList<JcaMenuLang>();
            for (MenuLanguageDto menuLanguage : menuDto.getListMenuLanguage()) {
                JcaMenuLang menuLanguageDb = new JcaMenuLang();
                if (null != menuLanguage.getMenuLanguageId()) {
                    menuLanguageDb = findMenuLanguage(menuLanguage.getMenuLanguageId());
                }

                if (null == menuLanguageDb.getCreatedDate()) {
                    menuLanguageDb.setCreatedId(UserProfileUtils.getAccountId());
                    menuLanguageDb.setCreatedDate(comService.getSystemDateTime());
                } else {
                    menuLanguageDb.setUpdatedDate(comService.getSystemDateTime());
                    menuLanguageDb.setUpdatedId(UserProfileUtils.getAccountId());
                }
                menuLanguageDb.setLangCode(menuLanguage.getLanguageCode());
                menuLanguageDb.setName(menuLanguage.getLanguageName().trim());
//				menuLanguageDb.setmLanguageId(menuLanguage.getLanguageId());
                menuLanguageDb.setNameAbv(menuLanguage.getAlias().trim());
                listMenuLanguage.add(menuLanguageDb);
            }

            if (null != menu.getId())
                menuDto.setMenuId(menu.getId());
            List<MenuLanguageDto> listMenuLanguageTmp = findListMenuLanguage(menuDto.getMenuId());
            List<MenuLanguageDto> listMenuLanguageDto = new ArrayList<MenuLanguageDto>();

            List<LanguageDto> languageList = languageService.getLanguageDtoList();
            for (LanguageDto languageDto : languageList) {
                for (MenuLanguageDto menuLanguageDto : listMenuLanguageTmp) {
                    if (languageDto.getId().equals(menuLanguageDto.getLanguageId())) {
                        MenuLanguageDto newMenuLanguage = new MenuLanguageDto();
                        newMenuLanguage.setLanguageCode(languageDto.getCode());
                        newMenuLanguage.setLanguageId(languageDto.getId());
                        newMenuLanguage.setLanguageName(menuLanguageDto.getLanguageName());
                        newMenuLanguage.setAlias(menuLanguageDto.getAlias());
                        newMenuLanguage.setMenuLanguageId(menuLanguageDto.getMenuLanguageId());
                        listMenuLanguageDto.add(newMenuLanguage);
                        break;
                    }
                }
            }
            if (null != listMenuLanguage) {
                menuDto.setListMenuLanguage(listMenuLanguageDto);
            }
//			menu.setOwnerBranchId(userProfile.getBranchId());
//			menu.setOwnerId(userProfile.getAccountId());
//			menu.setOwnerSectionId(userProfile.getDepartmentId());
//			menu.setStatusCode(MenuProcessEnum.APPROVAL.toString());

            // Add company_id
            menu.setCompanyId(menuDto.getCompanyId());

            menuRepository.save(menu);
            menuDto.setMenuId(menu.getId());

            for (JcaMenuLang menuLanguage : listMenuLanguage) {
                if (null != menu.getId()) {
                    menuLanguage.setMenuId(menu.getId());
                }
                menuLangService.save(menuLanguage);
            }
        }
    }

    @Override
    @Transactional
    public void deleteMenuDMS(Long menuId) {
        Long userNameLogin = UserProfileUtils.getAccountId();
        JcaMenu menu = new JcaMenu();
        List<JcaMenuLang> menuLanguages = new ArrayList<JcaMenuLang>();
        if (null != menuId) {
            menu = menuRepository.findOne(menuId);
            menuLanguages = menuLangService.findListMenuLanguageId(menuId);
        }
        // delete menu
        menu.setDeletedDate(comService.getSystemDateTime());
        menu.setDeletedId(userNameLogin);

        try {
            menuRepository.save(menu);
        } catch (Exception ex) {
            throw new SystemException(ex);
        }
        if (null != menuLanguages) {
            for (JcaMenuLang menuLanguage : menuLanguages) {
                // TODO tantm delete
//				menuLanguage.setDeletedDate(comService.getSystemDateTime());
//				menuLanguage.setDeletedId(userNameLogin);
                try {
                    menuLangService.save(menuLanguage);
                } catch (Exception ex) {
                    throw new SystemException(ex);
                }
            }
        }
    }

    @Override
    public boolean checkExistUrl(String url) {
        boolean isExist = false;
        List<MenuDto> menuList = menuRepository.checkExistUrl(url);
        if (!menuList.isEmpty()) {
            isExist = true;
        }
        return isExist;
    }

    private List<MenuDto> getParentLine(List<MenuDto> listChild, String lang, Long companyId, Long rootId) {
        List<Long> menuIds = new ArrayList<>();
        List<MenuDto> listParentLine = new ArrayList<>();
        Locale locale = new Locale(lang);
        for (MenuDto child : listChild) {
            List<MenuDto> menuIdLine = getParentLineId(menuIds, child.getMenuId(), lang, locale, companyId, rootId);
            listParentLine.addAll(menuIdLine);
        }
        return listParentLine;
    }

    private List<MenuDto> getParentLineId(List<Long> menuIds, Long childId, String lang, Locale locale, Long companyId,
            Long rootId) {
        Long menuId = childId;
        MenuDto menu;
        List<MenuDto> str = new ArrayList<>();
        do {
            menu = getMenuDtoByMenuId(menuId, lang, companyId);
            if (!menuIds.contains(menu.getMenuId())) {
                menuIds.add(menuId);
                str.add(menu);
            }

            menuId = menu.getParentId();
        } while (menu.getParentId() != null && !menu.getParentId().equals(rootId));
        return str;
    }

    public MenuDto getMenuDtoByMenuId(Long menuId, String lang, Long companyId) {
        MenuDto condition = new MenuDto();
        condition.setMenuId(menuId);
        condition.setMenuTypeList(getMenuTypeList(true));
        condition.setCompanyId(companyId);
        List<MenuDto> listMenu = manualService.findMenuListByCondition(0, 1, condition, lang);
        return listMenu.isEmpty() ? new MenuDto() : listMenu.get(0);
    }

    /**
     * find Menu List For Sorting
     *
     * @param parentId
     * @return List<MenuDto>
     * @author hand
     */
    @Override
    public List<MenuDto> findMenuListForSorting(Long parentId, String languageCode) {
        return menuRepository.findSubMenu(parentId, languageCode);
    }

    /**
     * updateSortAll
     *
     * @param sortOderList
     * @author hand
     */
    @Override
    @Transactional
    public void updateSortAll(List<SortOrderDto> sortOderList) {
        for (SortOrderDto dto : sortOderList) {
            menuRepository.updateSortAll(dto);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.jcanary.service.MenuService#buildMenuForNewCompany(java.lang.
     * Long)
     */
    @Override
    public void buildMenuForNewCompany(Long companyId) throws Exception {
        Long user = UserProfileUtils.getAccountId();
        Date date = comService.getSystemDateTime();
        JcaMenu root = menuRepository.findMenuRoot(null);
        if (root != null) {
            JcaMenu newRoot = new JcaMenu();
            CommonObjectUtil.copyPropertiesNonNull(root, newRoot);
            generateMenuEntity(newRoot, companyId, user, date, -1L);
            menuRepository.save(newRoot);

            // Save new menu for language
            List<JcaMenuLang> menuLangList = menuLangService.findListMenuLanguageId(root.getId());
            for (JcaMenuLang menuLanguage : menuLangList) {
                JcaMenuLang newMenuLang = new JcaMenuLang();
                CommonObjectUtil.copyPropertiesNonNull(menuLanguage, newMenuLang);
                generateMenuLangEntity(newMenuLang, newRoot.getId(), newRoot.getCreatedId(), newRoot.getCreatedDate());
                menuLangService.save(newMenuLang);
            }

            // Get all menu
            List<JcaMenu> allMenu = menuRepository.findAllMenuExcludeRoot();
            if (null != allMenu && !allMenu.isEmpty()) {
                getTreeMenuForNewCompany(root, allMenu, newRoot);
            }
        }
    }

    /**
     * getTreeMenuForNewCompany
     * 
     * @param menu
     * @param listAllMenu
     * @param newMenu
     * @throws Exception
     * @author HungHT
     */
    private void getTreeMenuForNewCompany(JcaMenu menu, List<JcaMenu> listAllMenu, JcaMenu newMenu) throws Exception {
        if (null != menu.getParentId()) {
            List<JcaMenu> listSub = getListSubMenuForNewCompany(menu.getId(), listAllMenu);
            if (null != listSub) {
                for (JcaMenu menuSub : listSub) {
                    // Save new menu
                    JcaMenu newSubMenu = new JcaMenu();
                    CommonObjectUtil.copyPropertiesNonNull(menuSub, newSubMenu);
                    generateMenuEntity(newSubMenu, newMenu.getCompanyId(), newMenu.getCreatedId(),
                            newMenu.getCreatedDate(), newMenu.getId());
                    menuRepository.save(newSubMenu);

                    // Save new menu for language
                    List<JcaMenuLang> menuLangList = menuLangService.findListMenuLanguageId(menuSub.getId());
                    for (JcaMenuLang menuLanguage : menuLangList) {
                        JcaMenuLang newMenuLang = new JcaMenuLang();
                        CommonObjectUtil.copyPropertiesNonNull(menuLanguage, newMenuLang);
                        generateMenuLangEntity(newMenuLang, newSubMenu.getId(), newSubMenu.getCreatedId(),
                                newSubMenu.getCreatedDate());
                        menuLangService.save(newMenuLang);
                    }
                    getTreeMenuForNewCompany(menuSub, listAllMenu, newSubMenu);
                }
            }
        }
    }

    /**
     * getListSubMenuForNewCompany
     * 
     * @param menuId
     * @param listAllMenu
     * @return
     * @author HungHT
     */
    private List<JcaMenu> getListSubMenuForNewCompany(Long menuId, List<JcaMenu> listAllMenu) {
        List<JcaMenu> listSubmenu = new LinkedList<JcaMenu>();
        for (JcaMenu menu : listAllMenu) {
            if (menu.getParentId().equals(menuId)) {
                listSubmenu.add(menu);
            }
        }
        return listSubmenu;
    }

    /**
     * generateMenuEntity
     * 
     * @param menu
     * @param companyId
     * @param user
     * @param date
     * @param parentId
     * @author HungHT
     */
    private void generateMenuEntity(JcaMenu menu, Long companyId, Long user, Date date, Long parentId) {
        if (null != menu) {
            menu.setId(null);
            menu.setCompanyId(companyId);
            menu.setParentId(parentId);
            menu.setCreatedId(user);
            menu.setCreatedDate(date);
            menu.setUpdatedId(null);
            menu.setUpdatedDate(null);
        }
    }

    /**
     * generateMenuLangEntity
     * 
     * @param menuLanguage
     * @param menuId
     * @param user
     * @param date
     * @author HungHT
     */
    private void generateMenuLangEntity(JcaMenuLang menuLanguage, Long menuId, Long user, Date date) {
        if (null != menuLanguage) {
            menuLanguage.setMenuId(menuId);
            menuLanguage.setCreatedId(user);
            menuLanguage.setCreatedDate(date);
            menuLanguage.setUpdatedId(null);
            menuLanguage.setUpdatedDate(null);
        }
    }

    /**
     * findMenuRoot
     * 
     * @param companyId
     * @return
     * @author HungHT
     */
    public JcaMenu findMenuRoot(Long companyId) {
        return menuRepository.findMenuRoot(companyId);
    }

    @Override
    public vn.com.unit.common.service.JCommonService getCommonService() {
        return comService;
    }
}
