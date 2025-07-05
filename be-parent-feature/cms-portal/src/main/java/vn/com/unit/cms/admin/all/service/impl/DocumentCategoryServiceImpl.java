package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.dto.DocumentCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategoryLanguageDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategorySearchDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategorySearchResultDto;
import vn.com.unit.cms.admin.all.dto.ParentPathDto;
import vn.com.unit.cms.admin.all.entity.DocumentCategory;
import vn.com.unit.cms.admin.all.entity.DocumentCategoryLanguage;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.repository.DocumentCategoryLanguageRepository;
import vn.com.unit.cms.admin.all.repository.DocumentCategoryRepository;
import vn.com.unit.cms.admin.all.service.DocumentCategoryService;
import vn.com.unit.cms.admin.all.service.ParentPathService;
import vn.com.unit.cms.admin.all.util.CommonSearchFilterUtils;
import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.tree.TreeBuilder;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaDatatableConfigService;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.core.dto.CommonSearchFilterDto;
import vn.com.unit.ep2p.core.ers.service.Select2DataService;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.ep2p.treebuilder.utils.MenuNode;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class DocumentCategoryServiceImpl implements DocumentCategoryService {

	private static final String MENU_ROOT = "ROOT";

	private static final String PATH_PARENT_TYPE = "M_DOCUMENT_CATEGORY";

	@Autowired
	private DocumentCategoryRepository documentCategoryRepository;

	@Autowired
	private DocumentCategoryLanguageRepository documentCategoryLanguageRepository;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private CmsCommonService comService;

	@Autowired
	private Select2DataService select2DataService;

	@Autowired
	private JcaDatatableConfigService jcaDatatableConfigService;

	@Autowired
	private CommonSearchFilterUtils commonSearchFilterUtils;

	@Autowired
	private MessageSource msg;

	@Autowired
	private ParentPathService parentPathService;

	@Autowired
	private CmsCommonService cmsCommonService;

	@Override
	public List<DocumentCategorySearchResultDto> getListByCondition(DocumentCategorySearchDto searchDto,
			Pageable pageable) {
		searchDto.setCompanyId(UserProfileUtils.getCompanyId());
		if ("AD".equals(UserProfileUtils.getChannel())) {
			searchDto.setChannel("AD");
		} else {
			searchDto.setChannel("AG");
		}
		return documentCategoryRepository.findListSearch(searchDto, pageable).getContent();
	}

	@Override
	public int countListByCondition(DocumentCategorySearchDto searchDto) {
		searchDto.setCompanyId(UserProfileUtils.getCompanyId());
		if ("AD".equals(UserProfileUtils.getChannel())) {
			searchDto.setChannel("AD");
		} else {
			searchDto.setChannel("AG");
		}
		return documentCategoryRepository.countList(searchDto);
	}

	@Override
	public ServletContext getServletContext() {
		return servletContext;
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
	public List<CommonSearchFilterDto> initListSearchFilter(DocumentCategorySearchDto searchDto, Locale locale) {
		List<CommonSearchFilterDto> list = DocumentCategoryService.super.initListSearchFilter(searchDto, locale);
		List<CommonSearchFilterDto> rs = new ArrayList<>();
		List<Select2Dto> listDataType = select2DataService.getConstantData("DOCUMENT", "CATEGORY_DOCUMENT", locale.toString());

		if (CollectionUtils.isNotEmpty(list)) {
			for (CommonSearchFilterDto filter : list) {
				if ("categoryType".equals(filter.getField())) {
					filter = commonSearchFilterUtils.createSelectCommonSearchFilterDto(filter.getField(),
							filter.getFieldName(), searchDto.getCategoryType(), listDataType);
					rs.add(filter);
				}else {
					rs.add(filter);
				}
			}
		}
		return rs;
	}

	@Override
	public DocumentCategoryEditDto getEditDtoById(Long id, Locale locale) {
		DocumentCategoryEditDto resultDto = new DocumentCategoryEditDto();

		if (id == null) {
			resultDto.setCreateBy(UserProfileUtils.getUserNameLogin());
			resultDto.setEnabled(true);
			Long customerId = 9L;
			resultDto.setCustomerTypeId(customerId);
			resultDto.setIndexLangActive(0);
			return resultDto;
		}

		// set FaqsCategory
		DocumentCategory entity = documentCategoryRepository.findOne(id);

		// dữ liệu ko tồn tại hoặc đã bị xóa
		if (entity == null || entity.getDeleteDate() != null) {
			throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
		}

		if (null != entity) {
			resultDto.setId(entity.getId());
			resultDto.setCode(entity.getCode());
			resultDto.setNote(entity.getNote());
			resultDto.setSort(entity.getSort());
			resultDto.setEnabled(entity.isEnabled());
			resultDto.setCategoryType(entity.getCategoryType());

			resultDto.setCustomerTypeId(entity.getCustomerTypeId());
			resultDto.setItemFunctionCode(entity.getItemFunctionCode());
			resultDto.setParentId(entity.getParentId());
			resultDto.setForCandidate(entity.getForCandidate());
			resultDto.setCreateBy(entity.getCreateBy());
		}

		List<DocumentCategoryLanguageDto> languageList = getLanguageList(id);
		resultDto.setLanguageList(languageList);

		return resultDto;
	}

	private List<DocumentCategoryLanguageDto> getLanguageList(Long categoryId) {
		List<DocumentCategoryLanguageDto> resultList = new ArrayList<DocumentCategoryLanguageDto>();

		List<DocumentCategoryLanguage> langList = documentCategoryLanguageRepository.findByCategoryId(categoryId);

		// loop categoryLanguages
		for (DocumentCategoryLanguage entity : langList) {
			// faqsCategoryLanguageId is languageId
			DocumentCategoryLanguageDto categoryLanguageDto = new DocumentCategoryLanguageDto();
			categoryLanguageDto.setId(entity.getId());
			categoryLanguageDto.setTitle(entity.getTitle());
			categoryLanguageDto.setLanguageCode(entity.getLanguageCode());
			categoryLanguageDto.setKeywordsSeo(entity.getKeywordsSeo());
			categoryLanguageDto.setKeywords(entity.getKeywords());
			categoryLanguageDto.setKeywordsDesc(entity.getKeywordsDesc());
			resultList.add(categoryLanguageDto);
			break;
		}
		return resultList;
	}

	@Override
	public void saveOrUpdate(DocumentCategoryEditDto editDto, Locale locale) throws Exception {
		String username = UserProfileUtils.getUserNameLogin();

		createOrEditData(editDto, username);
		createOrEditDataLanguage(editDto, username);

		parentPathService.deleteMenuPathByDescendantId(editDto.getId(), PATH_PARENT_TYPE);
		saveMenuPath(editDto, MENU_ROOT);
	}

	private void createOrEditData(DocumentCategoryEditDto editDto, String userName) {
		DocumentCategory entity = new DocumentCategory();

		if (null != editDto.getId()) {
			entity = documentCategoryRepository.findOne(editDto.getId());

			if (null == entity) {
				throw new BusinessException("Not found Faqs Category with id=" + editDto.getId());
			}

			entity.setUpdateDate(new Date());
			entity.setUpdateBy(userName);
			Long parentId = entity.getParentId();
			DocumentCategory parent = documentCategoryRepository.findOne(parentId);

			if (parent != null && parent.getParentId().equals(editDto.getId())) {
				parent.setParentId(parentId);
				documentCategoryRepository.update(parent);
			}

		} else {
			entity.setCreateDate(new Date());
			entity.setCreateBy(userName);

			entity.setCode(CommonUtil.getNextBannerCode(editDto.getCode().toUpperCase(),
					cmsCommonService.getMaxCodeYYMM("M_DOCUMENT_CATEGORY", editDto.getCode().toUpperCase())));
		}

//		entity.setCode(editDto.getCode());
		entity.setCategoryType(editDto.getCategoryType());
		entity.setPartner(editDto.getPartner());
		entity.setNote(editDto.getNote());
		entity.setSort(editDto.getSort());
		entity.setEnabled(editDto.isEnabled());
		entity.setCustomerTypeId(editDto.getCustomerTypeId());
		entity.setItemFunctionCode(editDto.getItemFunctionCode());
		entity.setParentId(editDto.getParentId());
		entity.setForCandidate(editDto.getForCandidate());
		if (editDto.getForCandidate() == null) {
			entity.setForCandidate(false);
		}
		if ("AD".equals(UserProfileUtils.getChannel())) {
			entity.setChannel("AD");
		} else {
			entity.setChannel("AG");
		}
		documentCategoryRepository.save(entity);

		editDto.setId(entity.getId());
	}

	/**
	 * createOrEditDataLanguage
	 *
	 * @param editDto
	 * @author TaiTM
	 */
	private void createOrEditDataLanguage(DocumentCategoryEditDto editDto, String userName) {
		for (DocumentCategoryLanguageDto cLanguageDto : editDto.getLanguageList()) {
			DocumentCategoryLanguage entity = new DocumentCategoryLanguage();
			DocumentCategoryLanguage entityEn = new DocumentCategoryLanguage();
			if (null != cLanguageDto.getId()) {
				entity = documentCategoryLanguageRepository.findLanguage(editDto.getId(), "en");
				entityEn = documentCategoryLanguageRepository.findLanguage(editDto.getId(), "vi");
				if (null == entity || null == entityEn) {
					throw new BusinessException("Not found data language with id=" + cLanguageDto.getId());
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
			entity.setLanguageCode("EN");
			entity.setKeywordsSeo(cLanguageDto.getKeywordsSeo());
			entity.setKeywords(cLanguageDto.getKeywords());
			entity.setKeywordsDesc(cLanguageDto.getKeywordsDesc());
			documentCategoryLanguageRepository.save(entity);

			entityEn.setCategoryId(editDto.getId());
			entityEn.setTitle(cLanguageDto.getTitle());
			entityEn.setLanguageCode("VI");
			entityEn.setKeywordsSeo(cLanguageDto.getKeywordsSeo());
			entityEn.setKeywords(cLanguageDto.getKeywords());
			entityEn.setKeywordsDesc(cLanguageDto.getKeywordsDesc());
			documentCategoryLanguageRepository.save(entityEn);

		}
	}

	@Override
	public void deleteDataById(Long id) throws Exception {
		// userName
		String userName = UserProfileUtils.getUserNameLogin();

		Date deleteDate = new Date();

		DocumentCategory entity = documentCategoryRepository.findOne(id);
		entity.setDeleteBy(userName);
		entity.setDeleteDate(deleteDate);
		documentCategoryRepository.update(entity);
		documentCategoryLanguageRepository.deleteData(id, userName);
	}

	@Override
	public List<DocumentCategorySearchResultDto> getListForSort(DocumentCategorySearchDto searchDto) {
		searchDto.setChannel(UserProfileUtils.getChannel());
		return documentCategoryRepository.findListForSort(searchDto);
	}

	@Override
	public void updateSortAll(DocumentCategorySearchDto searchDto) {
		if (CollectionUtils.isNotEmpty(searchDto.getSortOderList())) {
			for (SortOrderDto sort : searchDto.getSortOderList()) {
				documentCategoryRepository.updateSortAll(sort);
			}
		}
	}

	@Override
	public Class<?> getEnumColumnForExport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTemplateNameForExport(Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MenuNode> getListTree(String lang, Long rootId, Long idIgnore) {
		List<DocumentCategoryEditDto> listMenuDto = documentCategoryRepository.findListForTree(lang, idIgnore, UserProfileUtils.getChannel());

		List<MenuNode> menuTree = new LinkedList<MenuNode>();
		List<DocumentCategoryEditDto> listRoot = new LinkedList<DocumentCategoryEditDto>();
		if (null != listMenuDto && !listMenuDto.isEmpty()) {
			for (DocumentCategoryEditDto menu : listMenuDto) {
				if (null == menu.getParentId() || menu.getParentId().equals(rootId)) {
					listRoot.add(menu);
				}
			}

			if (listRoot.isEmpty() && !listMenuDto.isEmpty()) {
				for (DocumentCategoryEditDto menu : listMenuDto) {
					if (!MENU_ROOT.equals(menu.getCode())) {
						if (rootId.equals(menu.getParentId())) {
							listRoot.add(menu);
						}
					}
				}
			}

			for (DocumentCategoryEditDto menu : listRoot) {
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

	private MenuNode getTreeNode(MenuNode menuNode, List<DocumentCategoryEditDto> listAllMenu) {
		List<MenuNode> listSub = getListNodeSubMenu(menuNode.getId(), listAllMenu);
		menuNode.setChildren(listSub);
		if (null != listSub) {
			for (MenuNode menuSub : listSub) {
				menuSub = getTreeNode(menuSub, listAllMenu);
			}
		}
		return menuNode;
	}

	private List<MenuNode> getListNodeSubMenu(Long menuId, List<DocumentCategoryEditDto> listAllMenu) {
		List<MenuNode> listSubmenu = new LinkedList<MenuNode>();
		for (DocumentCategoryEditDto menu : listAllMenu) {
			if (menuId.equals(menu.getParentId())) {
				MenuNode menuNode = new MenuNode();
				menuNode.setId(menu.getId());
				menuNode.setText(menu.getTitle());
				menuNode.setState(ConstantCore.OPEN);
				listSubmenu.add(menuNode);
			}
		}
		return listSubmenu;
	}

	private void saveMenuPath(DocumentCategoryEditDto jcaMenuDto, String lang) {

		List<DocumentCategoryEditDto> datas = getListJcaMenuDto(lang, jcaMenuDto.getId());
		TreeBuilder<DocumentCategoryEditDto> builder = new TreeBuilder<DocumentCategoryEditDto>(datas);

		List<DocumentCategoryEditDto> listTree = builder.getParentBySub(jcaMenuDto);
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
			for (DocumentCategoryEditDto tree : listTree) {
				depth++;
				ParentPathDto ParentPathDto = new ParentPathDto();
				ParentPathDto.setAncestorId(tree.getParentId());
				ParentPathDto.setDescendantId(jcaMenuDto.getId());
				ParentPathDto.setDepth(depth);
				parentPathLeafDto.setCreatedDate(new Date());
				ParentPathDto.setType(PATH_PARENT_TYPE);
				parentPathService.saveParentPathDto(ParentPathDto);
			}
		} else {
			// save path root
			ParentPathDto parentPathRootDto = new ParentPathDto();
			parentPathRootDto.setAncestorId(jcaMenuDto.getParentId());
			parentPathRootDto.setDescendantId(jcaMenuDto.getId());
			parentPathRootDto.setDepth(CommonConstant.NUMBER_ONE_L);
			parentPathLeafDto.setCreatedDate(new Date());
			parentPathRootDto.setType(PATH_PARENT_TYPE);
			parentPathService.saveParentPathDto(parentPathRootDto);
		}
	}

	public List<DocumentCategoryEditDto> getListJcaMenuDto(String lang, Long idIgnore) {
		List<DocumentCategoryEditDto> listJcaMenuDto = documentCategoryRepository.findListForTree(lang, idIgnore, UserProfileUtils.getChannel());
		if (CommonCollectionUtil.isNotEmpty(listJcaMenuDto)) {
			return buildJcaMenuDto(listJcaMenuDto);
		}
		return null;
	}

	private List<DocumentCategoryEditDto> buildJcaMenuDto(List<DocumentCategoryEditDto> listJcaMenuDto) {
		List<DocumentCategoryEditDto> temp = listJcaMenuDto.stream().map(p -> {
			DocumentCategoryEditDto gc = new DocumentCategoryEditDto();
			gc.setId(p.getId());
			gc.setCode(p.getCode());
			gc.setParentId(p.getParentId());
			gc.setUrl(p.getUrl());
			return gc;
		}).collect(Collectors
				.collectingAndThen(Collectors.toMap(DocumentCategoryEditDto::getId, Function.identity(), (gc1, gc2) -> {
					return gc1;
				}), m -> new ArrayList<DocumentCategoryEditDto>(m.values())));

		return temp;
	}

	@Override
	public CommonSearchFilterUtils getCommonSearchFilterUtils() {
		return commonSearchFilterUtils;
	}
}
