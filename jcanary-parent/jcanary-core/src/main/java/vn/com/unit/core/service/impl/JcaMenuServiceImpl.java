/*******************************************************************************
 * Class        :JcaMenuServiceImpl
 * Created date :2020/12/09
 * Lasted date  :2020/12/09
 * Author       :SonND
 * Change log   :2020/12/09 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.tree.TreeBuilder;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaMenuDto;
import vn.com.unit.core.dto.JcaMenuLangDto;
import vn.com.unit.core.dto.JcaMenuPathDto;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.entity.JcaMenu;
import vn.com.unit.core.repository.JcaMenuRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaMenuLangService;
import vn.com.unit.core.service.JcaMenuPathService;
import vn.com.unit.core.service.JcaMenuService;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.db.repository.DbRepository;

/**
 * <p>
 * JcaMenuServiceImpl
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaMenuServiceImpl implements JcaMenuService {

    /** The jca menu repository. */
    @Autowired
    JcaMenuRepository jcaMenuRepository;

    /** The object mapper. */
    @Autowired
    ObjectMapper objectMapper;

    /** The jca menu language service. */
    @Autowired
    JcaMenuLangService jcaMenuLangService;

    /** The jca menu path service. */
    @Autowired
    JcaMenuPathService jcaMenuPathService;

    /** The language service. */
    @Autowired
    LanguageService languageService;
    
    @Autowired
    private JCommonService commonService;

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.JcaMenuService#getJcaMenuDto()
     */
    @Override
    public List<JcaMenuDto> getListJcaMenuDto(Long companyId) {
        List<JcaMenuDto> listJcaMenuDto = jcaMenuRepository.getListJcaMenuDto(companyId);
        if (CommonCollectionUtil.isNotEmpty(listJcaMenuDto)) {
            return buildJcaMenuDto(listJcaMenuDto);
        }
        return null;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.JcaMenuService#saveJcaMenu(vn.com.unit.core.entity.JcaMenu)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaMenu saveJcaMenu(JcaMenu jcaMenu) {
        Date systemDate = commonService.getSystemDate();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Long id = jcaMenu.getId();
        if (null != id) {
            JcaMenu oldJcaMenu = jcaMenuRepository.findOne(id);
            if (null != oldJcaMenu) {
                jcaMenu.setCreatedDate(oldJcaMenu.getCreatedDate());
                jcaMenu.setCreatedId(oldJcaMenu.getCreatedId());
                jcaMenu.setDeletedId(oldJcaMenu.getDeletedId());
                jcaMenu.setUpdatedDate(systemDate);
                jcaMenu.setUpdatedId(userId);
                jcaMenuRepository.update(jcaMenu);
            }
        } else {
            jcaMenu.setCreatedDate(systemDate);
            jcaMenu.setCreatedId(userId);
            jcaMenu.setUpdatedDate(systemDate);
            jcaMenu.setUpdatedId(userId);
            jcaMenuRepository.create(jcaMenu);
        }
        return jcaMenu;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.JcaMenuService#saveJcaMenuDto(vn.com.unit.core.dto.JcaMenuDto)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaMenu saveJcaMenuDto(JcaMenuDto jcaMenuDto) {
        Long menuId = jcaMenuDto.getId();

        /** BEGIN save menu info */
        JcaMenu jcaMenu = objectMapper.convertValue(jcaMenuDto, JcaMenu.class);
        this.saveJcaMenu(jcaMenu);
        jcaMenuDto.setId(jcaMenu.getId());
        /** END */

        /** BEGIN save menu language */
        List<JcaMenuLangDto> menuLanguages = jcaMenuDto.getLanguages();
        menuLanguages = this.buildDtoSaveMenuLangCode(menuLanguages, menuId);
        // save JcaConstantLanguage
        jcaMenuLangService.saveListJcaMenuLanguage(jcaMenu.getId(), menuLanguages);
        jcaMenuDto.setLanguages(menuLanguages);
        /** END */

        /** BEGIN save menu path */
        // delete menu path
        jcaMenuPathService.deleteMenuPathByDescendantId(jcaMenu.getId());
        this.saveMenuPath(jcaMenuDto);
        /** END */

        return jcaMenu;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.JcaMenuService#getJcaMenuDtoById(java.lang.Long)
     */
    @Override
    public JcaMenuDto getJcaMenuDtoById(Long id) {
        List<JcaMenuDto> listJcaMenuDto = jcaMenuRepository.getJcaMenuDtoById(id);
        if (CommonCollectionUtil.isNotEmpty(listJcaMenuDto)) {
            return buildJcaMenuDto(listJcaMenuDto).get(0);
        }
        return null;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.JcaMenuService#deleteJcaMenuById(java.lang.Long)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJcaMenuById(Long id) {
        Date systemDate = commonService.getSystemDate();
        Long userId = UserProfileUtils.getAccountId();
        if (null != id) {
            JcaMenu jcaMenu = jcaMenuRepository.findOne(id);
            if (null != jcaMenu) {
                jcaMenu.setDeletedDate(systemDate);
                jcaMenu.setDeletedId(userId);
                jcaMenuRepository.update(jcaMenu);
            }
        }
    }

    /**
     * <p>
     * Builds the jca menu dto.
     * </p>
     *
     * @param listJcaMenuDto
     *            type {@link List<JcaMenuDto>}
     * @return {@link List<JcaMenuDto>}
     * @author taitt
     */
    private List<JcaMenuDto> buildJcaMenuDto(List<JcaMenuDto> listJcaMenuDto) {
    	
        String langCodeRequest = CommonStringUtil.upperCase(UserProfileUtils.getLanguage());
//        String langCodeRequest = "en";

        List<JcaMenuDto> temp = listJcaMenuDto.stream().map(p -> {
            JcaMenuDto gc = new JcaMenuDto();
            gc.setId(p.getId());
            gc.setActived(p.getActived());
            gc.setCode(p.getCode());
            gc.setCompanyId(p.getCompanyId());
            gc.setClassName(p.getClassName());
            gc.setItemId(p.getItemId());
//            gc.setMenuLevel(p.getMenuLevel());
            gc.setDisplayOrder(p.getDisplayOrder());
            gc.setParentId(p.getParentId());
            gc.setParentMenuCode(p.getParentMenuCode());
//            gc.setParentMenuName(p.getParentMenuName());
            gc.setStatus(p.getStatus());
            gc.setUrl(p.getUrl());
            gc.setLanguages(new ArrayList<>());
            gc.getLanguages().add(new JcaMenuLangDto(p.getId(), p.getLangId(), p.getLangCode(), 
                    p.getName(), p.getNameAbv()));
            return gc;
        }).collect(Collectors.collectingAndThen(Collectors.toMap(JcaMenuDto::getId, Function.identity(), (gc1, gc2) -> {
            gc1.getLanguages().addAll(gc2.getLanguages());
            gc1.setName(langCodeRequest.equals(CommonStringUtil.upperCase(gc2.getLanguages().get(0).getLangCode())) ? gc2.getLanguages().get(0).getName()
                    : gc1.getName());
            gc1.setNameAbv(langCodeRequest.equals(CommonStringUtil.upperCase(gc2.getLanguages().get(0).getLangCode())) ? gc2.getLanguages().get(0).getNameAbv()
                    : gc1.getNameAbv());

            return gc1;
        }), m -> new ArrayList<JcaMenuDto>(m.values())));

        return temp;
    }

    /**
     * <p>
     * Builds the dto save menu lang code.
     * </p>
     *
     * @param menuLanguagesNew
     *            type {@link List<JcaMenuLanguageDto>}
     * @param menuId
     *            type {@link Long}
     * @return {@link List<JcaMenuLanguageDto>}
     * @author taitt
     */
    private List<JcaMenuLangDto> buildDtoSaveMenuLangCode(List<JcaMenuLangDto> menuLanguagesNew, Long menuId) {
        List<JcaMenuLangDto> menuLanguages = new ArrayList<>();
        /** Update or create menu language */
        List<LanguageDto> languageList = languageService.getListByCompanyId(UserProfileUtils.getCompanyId());
        if (null != menuId) {
            JcaMenuDto jcaMenuDtoOld = this.getJcaMenuDtoById(menuId);
            List<JcaMenuLangDto> menuLanguagesOld = jcaMenuDtoOld.getLanguages();
            for (JcaMenuLangDto menuLanguage : menuLanguagesNew) {
                String langCode = menuLanguage.getLangCode();
                String alias = menuLanguage.getNameAbv();
                String name = menuLanguage.getName();
                menuLanguages = menuLanguagesOld.stream().map(p -> {
                    if (CommonStringUtil.upperCase(langCode).equals(CommonStringUtil.upperCase(p.getLangCode()))) {
                        p.setNameAbv(alias);
                        p.setName(name);
                    }else if(menuLanguagesOld.stream().noneMatch(s -> CommonStringUtil.upperCase(s.getLangCode()).equals(langCode))){
                        Long langId = languageList.stream().filter(i ->
                        CommonStringUtil.upperCase(langCode).equals(CommonStringUtil.upperCase(i.getCode()))).findFirst().get().getId();
                        p.setNameAbv(alias);
                        p.setName(name);
                        p.setLangId(langId);
                        p.setMenuId(menuId);
                        p.setLangCode(langCode);
                    }
                    return p;
                }).collect(Collectors.toList());
            }

        } else {
            for (LanguageDto language : languageList) {
                String langCode = language.getCode();
                Long langId = language.getId();
                menuLanguages = menuLanguagesNew.stream().map(p -> {
                    if (CommonStringUtil.upperCase(langCode).equals(CommonStringUtil.upperCase(p.getLangCode()))) {
                        p.setLangId(langId);
                    }
                    return p;
                }).collect(Collectors.toList());
            }
        }
        /** END */

        return menuLanguages;
    }

    /**
     * <p>
     * Save menu path.
     * </p>
     *
     * @param jcaMenuDto
     *            type {@link JcaMenuDto}
     * @author taitt
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void saveMenuPath(JcaMenuDto jcaMenuDto) {
        List<JcaMenuDto> datas = this.getListJcaMenuDto(jcaMenuDto.getCompanyId());
        TreeBuilder<JcaMenuDto> builder = new TreeBuilder(datas);
        List<JcaMenuDto> listTree = builder.getParentBySub(jcaMenuDto);
        long depth = CommonConstant.NUMBER_ZERO;

        // save position path leaf
        JcaMenuPathDto jcaMenuPathLeafDto = new JcaMenuPathDto();
        jcaMenuPathLeafDto.setAncestorId(jcaMenuDto.getId());
        jcaMenuPathLeafDto.setDescendantId(jcaMenuDto.getId());
        jcaMenuPathLeafDto.setDepth(depth);
        jcaMenuPathService.saveJcaMenuPathDto(jcaMenuPathLeafDto);

        // save position path parent
        if (CommonCollectionUtil.isNotEmpty(listTree)) {
            // add leaf
            listTree.add(0, jcaMenuDto);
            // save path parent
            for (JcaMenuDto tree : listTree) {
                depth++;
                JcaMenuPathDto jcaMenuPathDto = new JcaMenuPathDto();
                jcaMenuPathDto.setAncestorId(tree.getParentId());
                jcaMenuPathDto.setDescendantId(jcaMenuDto.getId());
                jcaMenuPathDto.setDepth(depth);
                jcaMenuPathService.saveJcaMenuPathDto(jcaMenuPathDto);
            }
        } else {
            // save path root
            JcaMenuPathDto jcaMenuPathRootDto = new JcaMenuPathDto();
            jcaMenuPathRootDto.setAncestorId(jcaMenuDto.getParentId());
            jcaMenuPathRootDto.setDescendantId(jcaMenuDto.getId());
            jcaMenuPathRootDto.setDepth(CommonConstant.NUMBER_ONE_L);
            jcaMenuPathService.saveJcaMenuPathDto(jcaMenuPathRootDto);
        }

    }

    @Override
    public DbRepository<JcaMenu, Long> initRepo() {
        return jcaMenuRepository;
    }

    @Override
    @Transactional
    public void buildMenuByCompany(Long companyId) {
        Map<Long, Long> mapId = this.cloneMenuByDefault(companyId);
        List<JcaMenuLangDto> jcaMenuLangDtoList = jcaMenuLangService.getJcaMenuLangDtoListDefault();
        for (JcaMenuLangDto jcaMenuLangDto : jcaMenuLangDtoList) {
            jcaMenuLangDto.setMenuId(mapId.get(jcaMenuLangDto.getMenuId()));
            jcaMenuLangService.saveJcaMenuLangDto(jcaMenuLangDto);
        }
        List<JcaMenuPathDto> jcaMenuPathDtoList = jcaMenuPathService.getJcaMenuPathDtoListDefault();
        for (JcaMenuPathDto jcaMenuPathDto : jcaMenuPathDtoList) {
            jcaMenuPathDto.setAncestorId(mapId.getOrDefault(jcaMenuPathDto.getAncestorId(), 0L));
            jcaMenuPathDto.setDescendantId(mapId.getOrDefault(jcaMenuPathDto.getDescendantId(), 0L));
            jcaMenuPathService.saveJcaMenuPathDto(jcaMenuPathDto);
        }
    }
    
    private Map<Long, Long> cloneMenuByDefault(Long companyId) {
        List<JcaMenuDto> jcaMenuDtoListDefault = jcaMenuRepository.getJcaMenuDtoListDefault();
        Map<Long, Long> mapId = new HashMap<>();
        for (JcaMenuDto jcaMenuDto : jcaMenuDtoListDefault) {
            JcaMenu jcaMenu = objectMapper.convertValue(jcaMenuDto, JcaMenu.class);
            Long oldId = jcaMenu.getId();
            jcaMenu.setId(null);
            jcaMenu.setCompanyId(companyId);
            jcaMenuRepository.create(jcaMenu);
            Long newId = jcaMenu.getId();
            mapId.put(oldId, newId);
        }
        return mapId;
    }

	@Override
	public List<JcaMenuDto> getTreeMenuByUser(Long companyId, String langCode, Long userId) {
		List<JcaMenuDto> listJcaMenuDto = jcaMenuRepository.getListMenuByUserId(companyId, langCode, userId);
        if (CommonCollectionUtil.isNotEmpty(listJcaMenuDto)) {
            return buildJcaMenuDto(listJcaMenuDto);
        }
        return Collections.emptyList();
	}

	@Override
	public List<JcaMenuDto> getListMenuByUser(Long companyId, String langCode, Long userId) {
		return jcaMenuRepository.getListMenuByUserId(companyId, langCode, userId);
	}

}
