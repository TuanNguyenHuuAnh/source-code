/*******************************************************************************
 * Class        ：FaqsCategoryServiceImpl
 * Created date ：2017/02/28
 * Lasted date  ：2017/02/28
 * Author       ：TaiTM
 * Change log   ：2017/02/28：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.dto.FaqsCategoryDto;
import vn.com.unit.cms.admin.all.dto.FaqsCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.FaqsCategoryLanguageDto;
import vn.com.unit.cms.admin.all.dto.FaqsCategorySearchDto;
import vn.com.unit.cms.admin.all.dto.FaqsCategorySearchResultDto;
import vn.com.unit.cms.admin.all.dto.ParentPathDto;
import vn.com.unit.cms.admin.all.entity.FaqsCategory;
import vn.com.unit.cms.admin.all.entity.FaqsCategoryLanguage;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.repository.FaqsCategoryRepository;
import vn.com.unit.cms.admin.all.service.FaqsCategoryLanguageService;
import vn.com.unit.cms.admin.all.service.FaqsCategoryService;
import vn.com.unit.cms.admin.all.service.FaqsService;
import vn.com.unit.cms.admin.all.service.ParentPathService;
import vn.com.unit.cms.admin.all.util.CommonSearchFilterUtils;
import vn.com.unit.cms.core.module.faqs.dto.FaqsSearchDto;
import vn.com.unit.cms.core.module.faqs.entity.FaqsLanguage;
import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.tree.TreeBuilder;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.entity.Language;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaDatatableConfigService;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.core.dto.CommonSearchFilterDto;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.ep2p.treebuilder.utils.MenuNode;

/**
 * FaqsCategoryServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Service("faqsCategoryServiceImpl")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class FaqsCategoryServiceImpl implements FaqsCategoryService {

    @Autowired
    public FaqsCategoryRepository faqsCategoryRepository;

    @Autowired
    private FaqsService faqsService;

    @Autowired
    private FaqsCategoryLanguageService categoryLanguageService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private CommonSearchFilterUtils commonSearchFilterUtils;

    @Autowired
    private MessageSource msg;

    @Autowired
    private CmsCommonService comService;

    @Autowired
    private JcaDatatableConfigService jcaDatatableConfigService;

    @Autowired
    private ParentPathService parentPathService;

    @Autowired
    private ServletContext servletContext;

    private static final String MENU_ROOT = "ROOT";

    private static final String PATH_PARENT_TYPE = "M_FAQS_CATEGORY";

    @Override
    public List<FaqsCategorySearchResultDto> getListByCondition(FaqsCategorySearchDto searchDto, Pageable pageable) {
        return faqsCategoryRepository.findListSearch(searchDto, pageable).getContent();
    }

    @Override
    public int countListByCondition(FaqsCategorySearchDto searchDto) {
        return faqsCategoryRepository.countList(searchDto);
    }

    @Override
    public SystemConfig getSystemConfig() {
        return systemConfig;
    }

    @Override
    public CmsCommonService getCmsCommonService() {
        return comService;
    }

    @Override
    public JcaDatatableConfigService getJcaDatatableConfigService() {
        return jcaDatatableConfigService;
    }
    
    @Override
    @Transactional
    public void deleteById(Long cateId) {
        // check exists FaqsType
        FaqsCategory category = faqsCategoryRepository.findOne(cateId);
        if (null == category) {
            throw new BusinessException("Not found FaqsCategory with id=" + cateId);
        }

        deleteFaqsCategory(category, UserProfileUtils.getUserNameLogin());
    }

    /**
     * findById
     *
     * @param id
     * @return FaqsCategory
     * @author TaiTM
     */
    @Override
    public FaqsCategory findById(Long id) {
        return faqsCategoryRepository.findOne(id);
    }

    /**
     * delete FaqsCategory by typeId
     *
     * @param typeId
     * @author TaiTM
     */
    @Override
    @Transactional
    public void deleteByTypeId(Long typeId) {
        // userName
        String userName = UserProfileUtils.getUserNameLogin();

        List<FaqsCategory> categorieList = faqsCategoryRepository.findByTypeId(typeId);

        for (FaqsCategory category : categorieList) {
            // delete FaqsCategory
            deleteFaqsCategory(category, userName);
        }
    }

    /**
     * deleteFaqsCategory
     *
     * @param category
     * @param userName
     * @author TaiTM
     */
    private void deleteFaqsCategory(FaqsCategory category, String userName) {
        // delete Faqs
        faqsService.deleteFaqsByCategoryId(category.getId());

        // delete FaqsCategoryLanguage
        categoryLanguageService.deleteByCategoryId(category.getId(), userName);

        // delete FaqsCategory
        category.setDeleteDate(new Date());
        category.setDeleteBy(userName);
        faqsCategoryRepository.save(category);
    }

    /**
     * getFaqsCategory
     *
     * @param id
     * @param action true is edit, false is detail
     * @return FaqsCategoryEditDto
     * @author TaiTM
     */
    @Override
    public FaqsCategoryEditDto getFaqsCategory(Long id, boolean action) {
        FaqsCategoryEditDto resultDto = new FaqsCategoryEditDto();

        if (id == null) {
            resultDto.setEnabled(Boolean.TRUE);
            return resultDto;
        }

        // set FaqsCategory
        FaqsCategory category = faqsCategoryRepository.findOne(id);
        if (null != category) {
            resultDto.setId(category.getId());
            resultDto.setCode(category.getCode());
            resultDto.setNote(category.getNote());
            resultDto.setSort(category.getSort());
            resultDto.setEnabled(category.isEnabled());
            resultDto.setCustomerTypeId(category.getCustomerTypeId());
            resultDto.setItemFunctionCode(category.getItemFunctionCode());
            resultDto.setFaqsCategoryParentId(category.getFaqsCategoryParentId());

            resultDto.setCreateBy(category.getCreateBy());
        }

        List<FaqsCategoryLanguageDto> categoryLanguageList = getFaqsCategoryLanguageList(id);
        resultDto.setCategoryLanguageList(categoryLanguageList);

        return resultDto;
    }

    /**
     * get FaqsCategoryLanguageDto List
     *
     * @param categoryId
     * @return FaqsCategoryLanguageDto list
     * @author TaiTM
     */
    private List<FaqsCategoryLanguageDto> getFaqsCategoryLanguageList(Long categoryId) {
        List<FaqsCategoryLanguageDto> resultList = new ArrayList<FaqsCategoryLanguageDto>();

        List<FaqsCategoryLanguage> categoryLanguages = categoryLanguageService.findByCategoryId(categoryId);

        // languageList
        List<Language> languageList = languageService.findAllActive();

        // loop language
        for (Language language : languageList) {
            // loop categoryLanguages
            for (FaqsCategoryLanguage entity : categoryLanguages) {
                // faqsCategoryLanguageId is languageId
                if (StringUtils.equals(entity.getLanguageCode(), language.getCode())) {
                    FaqsCategoryLanguageDto categoryLanguageDto = new FaqsCategoryLanguageDto();
                    categoryLanguageDto.setId(entity.getId());
                    categoryLanguageDto.setTitle(entity.getTitle());
                    categoryLanguageDto.setLanguageCode(entity.getLanguageCode());
                    categoryLanguageDto.setKeywordsSeo(entity.getKeywordsSeo()); // thaonv: add code start
                    categoryLanguageDto.setKeywords(entity.getKeywords());
                    categoryLanguageDto.setKeywordsDesc(entity.getKeywordsDesc()); // thaonv: add code end
                    resultList.add(categoryLanguageDto);
                    break;
                }
            }
        }
        return resultList;
    }

    /**
     * find FaqsCategory by code
     *
     * @param code
     * @return
     * @author TaiTM
     */
    @Override
    public FaqsCategory findByCode(String code) {
        return faqsCategoryRepository.findByCode(code);
    }

    /**
     * find FaqsCategory by code
     *
     * @param code
     * @return
     * @author TaiTM
     */
    @Override
    @Transactional
    public void addOrEdit(FaqsCategoryEditDto categoryEditDto) {
        // user name login
        String userName = UserProfileUtils.getUserNameLogin();

        createOrEditFaqsCategory(categoryEditDto, userName);

        createOrEditCategoryLanguage(categoryEditDto, userName);

    }

    /**
     * create or update FaqsCategory
     *
     * @param editDto
     * @author TaiTM
     */
    private void createOrEditFaqsCategory(FaqsCategoryEditDto editDto, String userName) {

        FaqsCategory entity = new FaqsCategory();

        if (null != editDto.getId()) {
            entity = faqsCategoryRepository.findOne(editDto.getId());

            if (null == entity) {
                throw new BusinessException("Not found Faqs Category with id=" + editDto.getId());
            }

            entity.setUpdateDate(new Date());
            entity.setUpdateBy(userName);

            Long parentId = entity.getFaqsCategoryParentId();

            FaqsCategory parent = faqsCategoryRepository.findOne(editDto.getFaqsCategoryParentId());
            if (parent != null && parent.getFaqsCategoryParentId().equals(editDto.getId())) {
                parent.setFaqsCategoryParentId(parentId);
                faqsCategoryRepository.update(parent);
            }
        } else {
            entity.setCreateDate(new Date());
            entity.setCreateBy(userName);
        }

        entity.setCode(editDto.getCode());
        entity.setNote(editDto.getNote());
        entity.setSort(editDto.getSort());
        entity.setEnabled(editDto.isEnabled());
        entity.setCustomerTypeId(editDto.getCustomerTypeId());
        entity.setItemFunctionCode(editDto.getItemFunctionCode());
        entity.setFaqsCategoryParentId(editDto.getFaqsCategoryParentId());

        faqsCategoryRepository.save(entity);

        editDto.setId(entity.getId());
    }

    /**
     * createOrEditCategoryLanguage
     *
     * @param editDto
     * @author TaiTM
     */
    private void createOrEditCategoryLanguage(FaqsCategoryEditDto editDto, String userName) {
        for (FaqsCategoryLanguageDto cLanguageDto : editDto.getCategoryLanguageList()) {

            FaqsCategoryLanguage entity = new FaqsCategoryLanguage();
            FaqsCategoryLanguage entityEn = new FaqsCategoryLanguage();

            if (null != cLanguageDto.getId()) {
                entity = categoryLanguageService.findByCategoryIdAndLanguage(editDto.getId(),"VI");
                entityEn = categoryLanguageService.findByCategoryIdAndLanguage(editDto.getId(),"EN");
                if (null == entity || null == entityEn ) {
                    throw new BusinessException("Not found FaqsCategoryLanguag with id=" + cLanguageDto.getId());
                }
                entity.setUpdateDate(new Date());
                entity.setUpdateBy(userName);
                entityEn.setUpdateDate(new Date());
                entityEn.setUpdateBy(userName);
            } else {
                entity.setCreateDate(new Date());
                entity.setCreateBy(userName);
                entityEn.setCreateDate(new Date());
                entityEn.setCreateBy(userName);
            }
            entity.setCategoryId(editDto.getId());
            entity.setTitle(cLanguageDto.getTitle());
            entity.setLanguageCode("VI");
            entity.setKeywordsSeo(cLanguageDto.getKeywordsSeo()); // thaonv: add code start
            entity.setKeywords(cLanguageDto.getKeywords());
            entity.setKeywordsDesc(cLanguageDto.getKeywordsDesc()); // thaonv: add code end
            categoryLanguageService.saveFaqsCategoryLanguage(entity);
            
            entityEn.setCategoryId(editDto.getId());
            entityEn.setTitle(cLanguageDto.getTitle());
            entityEn.setLanguageCode("EN");
            entityEn.setKeywordsSeo(cLanguageDto.getKeywordsSeo()); 
            entityEn.setKeywords(cLanguageDto.getKeywords());
            entityEn.setKeywordsDesc(cLanguageDto.getKeywordsDesc()); 
            categoryLanguageService.saveFaqsCategoryLanguage(entityEn);
        }
    }

    @Override
    @Transactional
    public void updateSortAll(FaqsCategorySearchDto searchDto) {
        List<SortOrderDto> sortOderList = searchDto.getSortOderList();
        for (SortOrderDto dto : sortOderList) {
            faqsCategoryRepository.updateSortAll(dto);
        }
    }

    @Override
    public List<FaqsCategoryDto> findAllFaqsCategoryByCustomerId(String lang, Long customerId) {
        return faqsCategoryRepository.findAllFaqsCategoryByCustomerId(lang, customerId);
    }

    @Override
    public FaqsCategoryLanguage findByAliasCustomerId(String linkAlias, String languageCode, Long customerId) {
        return faqsCategoryRepository.findByAliasCustomerId(linkAlias, languageCode, customerId);
    }

 /*   @Override
    public List<CommonSearchFilterDto> initListSearchFilter(FaqsCategorySearchDto searchDto, Locale locale) {
        List<CommonSearchFilterDto> list = new ArrayList<>();
        CommonSearchFilterDto code = commonSearchFilterUtils.createInputCommonSearchFilterDto("code",
                msg.getMessage("searchfield.disp.categorycode", null, locale), searchDto.getCode());
        new CommonSearchFilterDto();
        list.add(code);

        CommonSearchFilterDto title = commonSearchFilterUtils.createInputCommonSearchFilterDto("title",
                msg.getMessage("searchfield.disp.categoryname", null, locale), searchDto.getTitle());
        list.add(title);

        boolean check = false;
        if ("1".equals(searchDto.getEnabled())) {
            check = true;
        }
        CommonSearchFilterDto enabled = commonSearchFilterUtils.createCheckboxCommonSearchFilterDto("enabled",
                msg.getMessage("searchfield.disp.enabled", null, locale), check);
        list.add(enabled);

        return list;
    }*/

    @Override
    public List<CommonSearchFilterDto> initListSearchFilter(FaqsCategorySearchDto searchDto, Locale locale) {
        List<CommonSearchFilterDto> list = FaqsCategoryService.super.initListSearchFilter(searchDto, locale);
        

        
        return list;

    }
    @Override
    public FaqsCategoryEditDto getEditDtoById(Long id, Locale locale) {
        return getFaqsCategory(id, true);
    }

    @Override
    public List<MenuNode> getListTree(String lang, Long rootId) {
        List<FaqsCategoryEditDto> listMenuDto = faqsCategoryRepository.findListForTree(lang);

        List<MenuNode> menuTree = new LinkedList<MenuNode>();
        List<FaqsCategoryEditDto> listRoot = new LinkedList<FaqsCategoryEditDto>();
        if (null != listMenuDto && !listMenuDto.isEmpty()) {
            for (FaqsCategoryEditDto menu : listMenuDto) {
                if (null == menu.getFaqsCategoryParentId() || menu.getFaqsCategoryParentId().equals(rootId)) {
                    listRoot.add(menu);
                }
            }

            if (listRoot.isEmpty() && !listMenuDto.isEmpty()) {
                for (FaqsCategoryEditDto menu : listMenuDto) {
                    if (!MENU_ROOT.equals(menu.getCode())) {
                        if (rootId.equals(menu.getFaqsCategoryParentId())) {
                            listRoot.add(menu);
                        }
                    }
                }
            }

            for (FaqsCategoryEditDto menu : listRoot) {
                MenuNode menuNode = new MenuNode();
                menuNode.setId(menu.getId());
                menuNode.setText(menu.getTitle());
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

    private MenuNode getTreeNode(MenuNode menuNode, List<FaqsCategoryEditDto> listAllMenu) {
        List<MenuNode> listSub = getListNodeSubMenu(menuNode.getId(), listAllMenu);
        menuNode.setChildren(listSub);
        if (null != listSub) {
            for (MenuNode menuSub : listSub) {
                menuSub = getTreeNode(menuSub, listAllMenu);
            }
        }
        return menuNode;
    }

    private List<MenuNode> getListNodeSubMenu(Long menuId, List<FaqsCategoryEditDto> listAllMenu) {
        List<MenuNode> listSubmenu = new LinkedList<MenuNode>();
        for (FaqsCategoryEditDto menu : listAllMenu) {
            if (menuId.equals(menu.getFaqsCategoryParentId())) {
                MenuNode menuNode = new MenuNode();
                menuNode.setId(menu.getId());
                menuNode.setText(menu.getTitle());
                menuNode.setState(ConstantCore.OPEN);
                listSubmenu.add(menuNode);
            }
        }
        return listSubmenu;
    }

    @Override
    public void saveOrUpdate(FaqsCategoryEditDto editDto, Locale locale) throws Exception {
        addOrEdit(editDto);

        parentPathService.deleteMenuPathByDescendantId(editDto.getId(), "M_FAQS_CATEGORY");
        saveMenuPath(editDto, locale.toString());
    }

    private void saveMenuPath(FaqsCategoryEditDto jcaMenuDto, String lang) {

        List<FaqsCategoryEditDto> datas = getListJcaMenuDto(lang);
        TreeBuilder<FaqsCategoryEditDto> builder = new TreeBuilder<FaqsCategoryEditDto>(datas);

        List<FaqsCategoryEditDto> listTree = builder.getParentBySub(jcaMenuDto);
        long depth = CommonConstant.NUMBER_ZERO;

        // save position path leaf
        ParentPathDto parentPathLeafDto = new ParentPathDto();
        parentPathLeafDto.setAncestorId(jcaMenuDto.getId());
        parentPathLeafDto.setDescendantId(jcaMenuDto.getId());
        parentPathLeafDto.setDepth(depth);
        parentPathLeafDto.setCreatedDate(new Date());
        parentPathLeafDto.setType(PATH_PARENT_TYPE);
        parentPathService.saveParentPathDto(parentPathLeafDto);

        // save position path parent
        if (CommonCollectionUtil.isNotEmpty(listTree)) {
            // add leaf
            listTree.add(0, jcaMenuDto);
            // save path parent
            for (FaqsCategoryEditDto tree : listTree) {
                depth++;
                ParentPathDto ParentPathDto = new ParentPathDto();
                ParentPathDto.setAncestorId(tree.getFaqsCategoryParentId());
                ParentPathDto.setDescendantId(jcaMenuDto.getId());
                ParentPathDto.setDepth(depth);
                parentPathLeafDto.setCreatedDate(new Date());
                ParentPathDto.setType(PATH_PARENT_TYPE);
                parentPathService.saveParentPathDto(ParentPathDto);
            }
        } else {
            // save path root
            ParentPathDto parentPathRootDto = new ParentPathDto();
            parentPathRootDto.setAncestorId(jcaMenuDto.getFaqsCategoryParentId());
            parentPathRootDto.setDescendantId(jcaMenuDto.getId());
            parentPathRootDto.setDepth(CommonConstant.NUMBER_ONE_L);
            parentPathRootDto.setType(PATH_PARENT_TYPE);
            parentPathLeafDto.setCreatedDate(new Date());
            parentPathService.saveParentPathDto(parentPathRootDto);
        }
    }

    public List<FaqsCategoryEditDto> getListJcaMenuDto(String lang) {
        List<FaqsCategoryEditDto> listJcaMenuDto = faqsCategoryRepository.findListForTree(lang);
        if (CommonCollectionUtil.isNotEmpty(listJcaMenuDto)) {
            return buildJcaMenuDto(listJcaMenuDto);
        }
        return null;
    }

    private List<FaqsCategoryEditDto> buildJcaMenuDto(List<FaqsCategoryEditDto> listJcaMenuDto) {
        List<FaqsCategoryEditDto> temp = listJcaMenuDto.stream().map(p -> {
            FaqsCategoryEditDto gc = new FaqsCategoryEditDto();
            gc.setId(p.getId());
            gc.setCode(p.getCode());
            gc.setFaqsCategoryParentId(p.getFaqsCategoryParentId());
            gc.setUrl(p.getUrl());
            return gc;
        }).collect(Collectors
                .collectingAndThen(Collectors.toMap(FaqsCategoryEditDto::getId, Function.identity(), (gc1, gc2) -> {
                    return gc1;
                }), m -> new ArrayList<FaqsCategoryEditDto>(m.values())));

        return temp;
    }

    @Override
    public void deleteDataById(Long id) throws Exception {
        deleteById(id);
    }

    @Override
    public List<FaqsCategorySearchResultDto> getListForSort(FaqsCategorySearchDto searchDto) {
        return faqsCategoryRepository.findListForSort(searchDto);
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class getEnumColumnForExport() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getTemplateNameForExport(Locale locale) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CommonSearchFilterUtils getCommonSearchFilterUtils() {
        return commonSearchFilterUtils;

    }
}