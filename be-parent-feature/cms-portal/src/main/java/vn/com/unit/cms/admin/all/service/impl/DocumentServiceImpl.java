/*******************************************************************************
 * Class        ：DocumentServiceImpl
 * Created date ：2017/04/18
 * Lasted date  ：2017/04/18
 * Author       ：TaiTM
 * Change log   ：2017/04/18：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.db2.jcc.am.ed;

import liquibase.integration.commandline.Main;
import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.constant.ConstantHistoryApprove;
import vn.com.unit.cms.admin.all.core.DocumentCategoryNode;
import vn.com.unit.cms.admin.all.dto.DocumentCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategoryParentCodeDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategorySearchDto;
import vn.com.unit.cms.admin.all.dto.DocumentViewAuthoritySelectDto;
import vn.com.unit.cms.admin.all.entity.DocumentCategory;
import vn.com.unit.cms.admin.all.entity.FaqsCategory;
import vn.com.unit.cms.admin.all.enumdef.exports.DocumentExportEnum;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.repository.DocumentCategoryRepository;
import vn.com.unit.cms.admin.all.service.CmsFileService;
import vn.com.unit.cms.admin.all.service.DocumentService;
import vn.com.unit.cms.admin.all.util.CommonSearchFilterUtils;
import vn.com.unit.cms.core.constant.CmsPrefixCodeConstant;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.cms.core.module.document.dto.DocumentEditDto;
import vn.com.unit.cms.core.module.document.dto.DocumentLanguageDto;
import vn.com.unit.cms.core.module.document.dto.DocumentSearchDto;
import vn.com.unit.cms.core.module.document.dto.DocumentSearchResultDto;
import vn.com.unit.cms.core.module.document.dto.DocumentVersionDto;
import vn.com.unit.cms.core.module.document.entity.Document;
import vn.com.unit.cms.core.module.document.entity.DocumentDetail;
import vn.com.unit.cms.core.module.document.entity.DocumentLanguage;
import vn.com.unit.cms.core.module.document.repository.DocumentLanguageRepository;
import vn.com.unit.cms.core.module.document.repository.DocumentRepository;
import vn.com.unit.cms.core.module.document.repository.DocumentVersionRepository;
import vn.com.unit.cms.core.module.faqs.entity.Faqs;
import vn.com.unit.cms.core.utils.CmsDateUtils;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.dto.JcaGroupEmailDto;
import vn.com.unit.core.entity.Language;
import vn.com.unit.core.repository.LanguageRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaDatatableConfigService;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.DirectoryConstant;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.core.dto.CommonSearchFilterDto;
import vn.com.unit.ep2p.core.efo.dto.EfoDocDto;
import vn.com.unit.ep2p.core.ers.service.Select2DataService;
import vn.com.unit.ep2p.core.ers.service.impl.DocumentWorkflowCommonServiceImpl;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.ep2p.core.res.dto.DocumentSaveReq;
import vn.com.unit.ep2p.dto.AccountDetailDto;
import vn.com.unit.workflow.dto.JpmStatusDeployDto;
import vn.com.unit.workflow.service.JpmStatusDeployService;

/**
 * DocumentServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class DocumentServiceImpl extends DocumentWorkflowCommonServiceImpl<DocumentEditDto, DocumentEditDto>
		implements DocumentService {

	private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

	private static final String ITEM_SUBTYPE_DOCUMENT = "DOCUMENT";
	private static final String CATEGORY_CODE = "categoryCode";

	@Autowired
	@Qualifier("languageRepository")
	private LanguageRepository langRepository;

	@Autowired
	private DocumentCategoryRepository documentCategoryRepository;

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private DocumentLanguageRepository documentLanguageRepository;

	@Autowired
	private DocumentVersionRepository documentVersionRepository;

	@Autowired
	MessageSource msg;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private CmsCommonService cmsCommonService;

	@Autowired
	private CommonSearchFilterUtils commonSearchFilterUtils;

	@Autowired
	private JcaDatatableConfigService jcaDatatableConfigService;

	@Autowired
	private Select2DataService select2DataService;

	@Autowired
	private JpmStatusDeployService jpmStatusDeployService;

	@Autowired
	ServletContext servletContext;

	@Autowired
	private CmsFileService cmsFileService;

	private Date checkMaxDate(Date date) {
        if(!ObjectUtils.isNotEmpty(date)) {
                try {
                    Date maxDate = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/9999");
                    return maxDate;
                } catch (ParseException e) {
                    logger.error("Exception ", e);
                }
            }
        return date;
    }
	
	@Override
	public List<DocumentCategoryNode> findSelectionCategoryTree(Long exceptCategoryId, String customerTypeIdText) { 
		List<DocumentCategory> categories = documentCategoryRepository.findAllRootActive(customerTypeIdText); 
		List<DocumentCategoryEditDto> listCategoryDto = this.loadFullCategoryTreeByRoots(categories, true, 
				exceptCategoryId, customerTypeIdText);

		List<DocumentCategoryNode> categoryTree = new LinkedList<DocumentCategoryNode>();
		List<DocumentCategoryEditDto> listRoot = new LinkedList<DocumentCategoryEditDto>();
		if (null != listCategoryDto && !listCategoryDto.isEmpty()) {
			if (null != listCategoryDto) {
				for (DocumentCategoryEditDto category : listCategoryDto) {
					if (null == category.getParentId() || category.getParentId().equals(0L))
						listRoot.add(category);
				}
				List<DocumentCategoryNode> parentNodeList = new ArrayList<DocumentCategoryNode>();
				for (DocumentCategoryEditDto categoryDto : listRoot) {
					DocumentCategoryNode categoryNode = new DocumentCategoryNode();
					categoryNode.setId(categoryDto.getId());
					categoryNode.setState(ConstantCore.OPEN);
					categoryTree.add(categoryNode);
					parentNodeList.add(categoryNode);
				}
				do {
					parentNodeList = loadAllSubNodes(parentNodeList, listCategoryDto);
				} while (parentNodeList != null && parentNodeList.size() > 0);
			}
		}
		return categoryTree;
	}

	public List<DocumentCategoryNode> loadAllSubNodes(List<DocumentCategoryNode> parentNodes,
			List<DocumentCategoryEditDto> listAllCategory) {
		List<DocumentCategoryNode> retVal = new ArrayList<DocumentCategoryNode>();
		for (DocumentCategoryNode currentNode : parentNodes) {
			List<DocumentCategoryNode> listSub = getListSubNodes(currentNode.getId(), listAllCategory);
			currentNode.setChildren(listSub);
			retVal.addAll(listSub);
		}
		return retVal;
	}

	public List<DocumentCategoryNode> getListSubNodes(Long menuId, List<DocumentCategoryEditDto> listAllCategory) {
		List<DocumentCategoryNode> listSubCategory = new LinkedList<DocumentCategoryNode>();
		for (DocumentCategoryEditDto menu : listAllCategory) {
			if (menu.getParentId() != null && menu.getParentId().equals(menuId)) {
				DocumentCategoryNode categoryNode = new DocumentCategoryNode();
				categoryNode.setId(menu.getId());
				categoryNode.setState(ConstantCore.OPEN);
				listSubCategory.add(categoryNode);
			}
		}
		return listSubCategory;
	}

	public List<DocumentCategoryEditDto> sortCategoryDtoByConstructorTree(List<DocumentCategoryEditDto> categoryList) {
		// Find roots
		List<DocumentCategoryEditDto> result = getCategoryRoot(categoryList);

		int i = 0;
		while (i < result.size()) {
			DocumentCategoryEditDto categoryRootNodeDto = result.get(i);
			Long categoryId = categoryRootNodeDto.getId();

			List<DocumentCategoryEditDto> categoryChildren = new ArrayList<DocumentCategoryEditDto>();
			for (DocumentCategoryEditDto categoryDto : categoryList) {
				Long parentId = categoryDto.getParentId();
				if (parentId != null && categoryId.equals(parentId)) {
					result.add(i + 1, categoryDto);
					categoryChildren.add(categoryDto);
				}
			}
			categoryList.removeAll(categoryChildren);
			i++;
		}

		return result;
	}

	private List<DocumentCategoryEditDto> getCategoryRoot(List<DocumentCategoryEditDto> categoryList) {
		List<DocumentCategoryEditDto> result = new ArrayList<DocumentCategoryEditDto>();
		// Find roots
		for (int i = 0; i < categoryList.size(); i++) {
			DocumentCategoryEditDto categoryDto = categoryList.get(i);
			if (null == categoryDto.getParentId() || categoryDto.getParentId().equals(0l)) {
				result.add(categoryDto);
			}
		}

		return result;
	}

	/**
	 * @param updateDto
	 * @param entity
	 * @throws IOException
	 */
	private void moveTmpImage(DocumentEditDto updateDto, Document entity) throws IOException {
		String tempImageUrl = updateDto.getImageTempUrl();

		if (StringUtils.isNotEmpty(tempImageUrl)) {
			String newPhiscalName = CmsUtils.moveTempToUploadFolder(tempImageUrl, DirectoryConstant.DOCUMENT_FOLDER);
			updateDto.setPhysicalFileName(newPhiscalName);
		}
	}

	/**
	 * @param versionEntity
	 * @param tempFileUrl
	 * @param categoryId
	 * @throws IOException
	 */
	private void moveTmpFile(DocumentDetail versionEntity, String tempFileUrl, Long categoryId) throws IOException {
		List<String> ancestorCategoryCode = this.getAncestorCategoryCodes(categoryId);
		if (StringUtils.isEmpty(tempFileUrl)) {
			throw new BusinessException("illegal data");
		}
		String physicalTmpName = tempFileUrl;
		// upload images
		String directoryPath = DirectoryConstant.DOCUMENT_FOLDER;
		if (ancestorCategoryCode != null && ancestorCategoryCode.size() > 0) {
			for (String categoryCode : ancestorCategoryCode) {
				directoryPath = directoryPath.concat(categoryCode).concat("/");
			}
		}
		String newPhiscalName = CmsUtils.moveTempToUploadFolder(physicalTmpName, directoryPath);
		versionEntity.setPhysicalFileName(newPhiscalName);
		versionEntity.setFileName(versionEntity.getFileName());
	}

	/**
	 * @param Document entity
	 * @return
	 */
	private List<DocumentLanguageDto> loadDocumentInfoByLanguage(Document documentEntity) {
		Long documentId = documentEntity.getId();
		return loadDocumentInfoByLanguage(documentId);
	}

	/**
	 * @param documentId
	 * @return
	 */
	private List<DocumentLanguageDto> loadDocumentInfoByLanguage(Long documentId) {
		List<DocumentLanguage> infoBylanguageEntities = documentLanguageRepository.findByDocumentId(documentId);
		HashMap<String, DocumentLanguage> documentLangMap = new HashMap<String, DocumentLanguage>();
		for (DocumentLanguage infoByLangEntity : infoBylanguageEntities) {
			documentLangMap.put(infoByLangEntity.getLanguageCode(), infoByLangEntity);
		}
		List<Language> languages = langRepository.findAllActive();
		List<DocumentLanguageDto> documentLangDtos = new ArrayList<DocumentLanguageDto>();
		for (Language language : languages) {
			DocumentLanguage documentLangEntity = documentLangMap.get(language.getCode());
			if (documentLangEntity != null) {
				DocumentLanguageDto documentLangDto = new DocumentLanguageDto();
				documentLangDtos.add(documentLangDto);
			} else {
				DocumentLanguageDto documentLangDto = new DocumentLanguageDto();
				documentLangDto.setDocumentId(documentId);
				documentLangDto.setLanguageCode(language.getCode());
				documentLangDto.setLanguageDispName(language.getName());
				documentLangDtos.add(documentLangDto);
			}
		}
		return documentLangDtos;
	}

	/**
	 * get category list with name, id for selection
	 * 
	 * @return ArrayList<DocumentCategorySelectDto>
	 */
	@Override
	public List<DocumentCategoryEditDto> getCategoriesForSelection(Long exceptCategoryId, String customerTypeIdText) {
		List<DocumentCategory> categories = documentCategoryRepository.findRootListForSelection(customerTypeIdText);

		List<DocumentCategoryEditDto> categoryDtos = loadFullCategoryTreeByRoots(categories, true, exceptCategoryId,
				customerTypeIdText);
		return categoryDtos;
	}

	/**
	 * @param categories
	 * @param forSelection
	 * @return
	 */
	private List<DocumentCategoryEditDto> loadFullCategoryTreeByRoots(List<DocumentCategory> categories,
			boolean forSelection, Long exceptCategoryId, String customerTypeIdText) {
		if (exceptCategoryId != null) {
			categories = removeExceptCategory(categories, exceptCategoryId);
		}
		categories = loadDescendandCategories(categories, forSelection, exceptCategoryId, customerTypeIdText);
		List<DocumentCategoryEditDto> categoryDtos = new ArrayList<DocumentCategoryEditDto>();
//        for (DocumentCategory category : categories) {
//            DocumentCategoryEditDto categoryDto = new DocumentCategoryEditDto(category);
//            DocumentCategoryEditDto categoryDto = new DocumentCategoryEditDto();
//            categoryDtos.add(categoryDto);
//        }
		categoryDtos = this.sortCategoryDtoByConstructorTree(categoryDtos);
		return categoryDtos;
	}

	/**
	 * @param categories
	 * @param forSelection
	 * @param exceptCategoryId
	 * @param selectionParentIds
	 * @return
	 */
	private List<DocumentCategory> loadDescendandCategories(List<DocumentCategory> categories, boolean forSelection,
			Long exceptCategoryId, String customerTypeIdText) {
		List<Long> selectionParentIds = this.createCategoryIdList(categories);
		if (selectionParentIds.size() > 0) {
			List<DocumentCategory> childrens;
			do {
				if (forSelection) {
					childrens = documentCategoryRepository.findAllActiveChildrenCategoryForSelection(selectionParentIds,
							customerTypeIdText);
				} else {
					childrens = documentCategoryRepository.findAllActiveChildrenCategory(selectionParentIds,
							customerTypeIdText);
				}
				if (exceptCategoryId != null) {
					childrens = removeExceptCategory(childrens, exceptCategoryId);
				}
				selectionParentIds = this.createCategoryIdList(childrens);
				categories.addAll(childrens);
			} while (childrens != null && childrens.size() > 0);
		}
		return categories;
	}

	/**
	 * @param categories
	 * @param exceptCategoryId
	 * @return categories
	 */
	private List<DocumentCategory> removeExceptCategory(List<DocumentCategory> categories, Long exceptCategoryId) {
		for (int index = 0; index < categories.size(); ++index) {
			DocumentCategory iCategory = categories.get(index);
			if (iCategory.getId().equals(exceptCategoryId)) {
				categories.remove(index);
				break;
			}
		}
		return categories;
	}

	private List<Long> createCategoryIdList(List<DocumentCategory> categories) {
		List<Long> idList = new ArrayList<Long>();
		for (DocumentCategory category : categories) {
			idList.add(category.getId());
		}
		return idList;
	}

	@Override
	public int countDocumentByCode(String code) {
		return documentRepository.countByCode(code);
	}

	/**
	 * @param id
	 */
	@Override
	public DocumentEditDto getDocumentObject(Long id) {
		Document entity = documentRepository.findOne(id);
		if (entity == null) {
			return null;
		} else {
			return new DocumentEditDto();
		}
	}

	@Override
	public boolean requestDownloadDocument(Long documentId, Long version, String token, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException {

		DocumentDetail docDetail = documentVersionRepository.findDocumentDetailByDocumentId(documentId, version);

		if (version != null) {
			String fileName = docDetail.getPhysicalFileName();
			cmsFileService.download(fileName, request, response);
		} else {
			throw new BusinessException("illegal data");
		}

		return true;
	}

	@Override
	@Transactional
	public String genDocumentShareToken(Long documentId, Long versionId)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String randomToken = CommonUtil.randomString(30);
		Document doc = documentRepository.findOne(documentId);
		DocumentDetail version = documentVersionRepository.findOne(versionId);
		if (version.isCurrentVersion() && version.getDocumentId().equals(doc.getId())
				&& UserProfileUtils.getUserNameLogin().equals(doc.getCreateBy())) {

			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

			byte[] hashTokenBytes = CommonUtil.genMd5Hash(randomToken, timeStamp);
			documentVersionRepository.saveShareToken(hashTokenBytes, timeStamp, documentId, versionId);
		} else {
			throw new BusinessException("illegal data");
		}
		return randomToken;
	}

	@Override
	public List<DocumentViewAuthoritySelectDto> getSelectionViewAuthorityItems(Locale locale) {
		List<String> subType = new ArrayList<>();
		subType.add(ITEM_SUBTYPE_DOCUMENT);
//		List<Item> itemEntityList = itemRepository.findItemBySubType(subType);
		List<DocumentViewAuthoritySelectDto> viewDtoList = new ArrayList<DocumentViewAuthoritySelectDto>();
//		for (Item item : itemEntityList) {
//			DocumentViewAuthoritySelectDto viewAuthorityDto = new DocumentViewAuthoritySelectDto();
//			viewAuthorityDto.setFunctionCode(item.getFunctionCode());
//			String dispName = item.getFunctionName();
//			if (item.getFunctionCode().equals(RoleConstant.DOCUMENT_VIEW_GUEST)) {
//				dispName = msg.getMessage("document.viewauthority.guest", null, locale);
//			} else if (item.getFunctionCode().equals(RoleConstant.DOCUMENT_VIEW_MEMBER)) {
//				dispName = msg.getMessage("document.viewauthority.member", null, locale);
//			} else if (item.getFunctionCode().equals(RoleConstant.DOCUMENT_VIEW_AUTHOR)) {
//				dispName = msg.getMessage("document.viewauthority.author", null, locale);
//			}
//			viewAuthorityDto.setDispName(dispName);
//			viewDtoList.add(viewAuthorityDto);
//		}
		return viewDtoList;
	}

	private List<String> getAncestorCategoryCodes(Long categoryId) {
		List<DocumentCategoryParentCodeDto> parentCodeList = documentCategoryRepository.findAllActiveCodes();
		HashMap<String, String> codeMap = new HashMap<String, String>();
		List<String> ancestorCodes = new ArrayList<String>();
		String currentCode = null;
		if (parentCodeList != null && parentCodeList.size() > 0) {
			for (DocumentCategoryParentCodeDto codeDto : parentCodeList) {
				codeMap.put(codeDto.getCode(), codeDto.getParentCode());
				if (codeDto.getId().equals(categoryId)) {
					currentCode = codeDto.getCode();
				}
			}
		}
		while (currentCode != null) {
			ancestorCodes.add(0, currentCode);
			currentCode = codeMap.get(currentCode);

		}
		return ancestorCodes;
	}

	@Override
	public void requestDownloadImage(String imageUrl, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		// fileService.download(imageUrl, request, response);
	}

	/**
	 * trace parent nodes by walking up hierarchy
	 * 
	 * @param condition
	 * @return
	 */
	public List<DocumentCategorySearchDto> traceParent(List<DocumentCategoryEditDto> searchResults) {

		List<DocumentCategorySearchDto> rootList = new ArrayList<>();
		List<DocumentCategoryEditDto> rootDtoList = new ArrayList<>();
		List<String> keyIds = new ArrayList<>();
		keyIds.add(CATEGORY_CODE);

		// Start walking up hierarchy by branches
		for (DocumentCategoryEditDto searchResult : searchResults) {
			Long currentNodeId = searchResult.getId();

			// Look up parent while node is not root
			while (documentCategoryRepository.findParentIdByCategoryId(currentNodeId) != null)
				currentNodeId = documentCategoryRepository.findParentIdByCategoryId(currentNodeId);

			DocumentCategoryEditDto rootNode = documentCategoryRepository.findDocumentCategoryDtoById(currentNodeId);

			if (!rootDtoList.contains(rootNode) && rootNode != null)
				rootDtoList.add(rootNode);
		}

		// Sort list by defined sortOrder
//        Collections.sort(rootDtoList, this.sortOrder);
		// Find DocumentCategorySearchDto based on Strings
		for (DocumentCategoryEditDto documentCategoryDto : rootDtoList) {
			DocumentCategorySearchDto rootNode = new DocumentCategorySearchDto();
			rootNode.setCode(documentCategoryDto.getCode());
//            rootNode.setName(documentCategoryDto.getName());
			rootNode.setParentId(documentCategoryDto.getParentId());
//            rootNode.setTypeId(documentCategoryDto.getDocumentTypeId());
//            rootNode.setCustomerTypeIdText(documentCategoryDto.getStrCustomerTypeId());

			rootList.add(rootNode);
		}
		return rootList;
	}

	@Override
	public List<Map<String, String>> listDependencies(Long documentTypeId, List<Long> lstStatus) {
		return documentRepository.listDependencies(documentTypeId, lstStatus);
	}

	@Override
	public DocumentEditDto getEdit(Long id, String customerAlias, Locale locale) {
		return getEditDtoById(id, locale);
	}

	@Override
	public DocumentActionReq actionBusiness(DocumentActionReq action, EfoDocDto efoDocDto, Locale locale)
			throws Exception {
		DocumentEditDto editDto = (DocumentEditDto) action;
		// saveOrUpdate(editDto, locale);
		String usernameLogin = UserProfileUtils.getUserNameLogin();
		try {
			if (StringUtils.isNotBlank(editDto.getPostedDateString())) {
				editDto.setPostedDate(
						CmsDateUtils.convertStringToDate(editDto.getPostedDateString(), "dd/MM/yyyy HH:mm:ss"));
			}

			if (StringUtils.isNotBlank(editDto.getExpirationDateString())) {
				editDto.setExpirationDate(
						CmsDateUtils.convertStringToDate(editDto.getExpirationDateString(), "dd/MM/yyyy HH:mm:ss"));
			}

			if (editDto.getHasUpdateData() != null && editDto.getHasUpdateData() == true) {
				Document entity = documentRepository.findOne(editDto.getId());
				entity.setEnabled(editDto.isEnabled());
				entity.setPostedDate(editDto.getPostedDate());
				entity.setExpirationDate(checkMaxDate(editDto.getExpirationDate()));
				entity.setNote(editDto.getNote());
				entity.setUpdateBy(usernameLogin);
				entity.setUpdateDate(new Date());

				documentRepository.save(entity);
				editDto.setId(entity.getId());
				editDto.setCode(entity.getCode());
			} else {
				createOrEditData(editDto, usernameLogin, locale);

				createOrEditLanguage(editDto, usernameLogin, locale);
		
			}

		} catch (Exception e) {
			logger.error("Need fix createOrEditLanguage", e);
			throw new Exception(e.getMessage());
		}

		editDto.setDataId(editDto.getId());
		return editDto;
	}

	@Override
	public void sendMailProcess(DocumentActionReq abstractProcessDto, Integer nextStepNo, String nextStatusCode,
			Integer curStepNo, AccountDetailDto accountAction, HttpServletRequest httpServletRequest, Locale locale)
			throws Exception {
		super.sendMailProcess(abstractProcessDto, nextStepNo, nextStatusCode, curStepNo, accountAction,
				httpServletRequest, locale);

	}

	@Override
	public List<DocumentSearchResultDto> getListByCondition(DocumentSearchDto searchDto, Pageable pageable) {
		if (!UserProfileUtils.hasRole(CmsRoleConstant.CMS_ROLE_ADMIN.concat(CoreConstant.COLON_EDIT))) {
			searchDto.setUsername(UserProfileUtils.getUserNameLogin());
		}
		if ("AD".equals(UserProfileUtils.getChannel())) {
			searchDto.setChannel("AD");
		} else {
			searchDto.setChannel("AG");
		}
		return documentRepository.findListSearch(searchDto, pageable).getContent();
	}

	@Override
	public int countListByCondition(DocumentSearchDto searchDto) {
		if (!UserProfileUtils.hasRole(CmsRoleConstant.CMS_ROLE_ADMIN.concat(CoreConstant.COLON_EDIT))) {
			searchDto.setUsername(UserProfileUtils.getUserNameLogin());
		}
		if ("AD".equals(UserProfileUtils.getChannel())) {
			searchDto.setChannel("AD");
		} else {
			searchDto.setChannel("AG");
		}
		return documentRepository.countList(searchDto);
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
		return cmsCommonService;
	}

	@Override
	public JcaDatatableConfigService getJcaDatatableConfigService() {
		return jcaDatatableConfigService;
	}

	@Override
	public List<CommonSearchFilterDto> initListSearchFilter(DocumentSearchDto searchDto, Locale locale) {

        List<CommonSearchFilterDto> list = DocumentService.super.initListSearchFilter(searchDto, locale);
        List<CommonSearchFilterDto> rs = new ArrayList<>();

        List<Select2Dto> listCategory = select2DataService.getListDocumentCategory(locale.toString(), UserProfileUtils.getChannel());

		CommonSearchFilterDto status = commonSearchFilterUtils.createSelectStatusProcess("statusName",
				searchDto.getStatusCode(), "BUSINESS_CMS", locale);
		List<Select2Dto> lsStatus = new ArrayList<Select2Dto>();
		lsStatus.addAll(status.getListSelect());

        if (CollectionUtils.isNotEmpty(list)) {
            for (CommonSearchFilterDto filter : list) {
                if ("categoryName".equals(filter.getField())) {
                    filter = commonSearchFilterUtils.createSelectCommonSearchFilterDto(filter.getField(),
                            filter.getFieldName(), searchDto.getCategoryName(), listCategory);
                    rs.add(filter);
                } else if ("statusName".equals(filter.getField())) {
                    filter = commonSearchFilterUtils.createSelectCommonSearchFilterDto(filter.getField(),
                            filter.getFieldName(), searchDto.getStatusCode(), lsStatus);
                    rs.add(filter);
                } else {
                    rs.add(filter);
                }
            }
        }
        return rs;
	}

	@Override
	public DocumentEditDto getEditDtoById(Long id, Locale locale) {
		if (id == null) {
			DocumentEditDto documentDto = new DocumentEditDto();
			documentDto.setEnabled(Boolean.TRUE);
			documentDto.setCustomerTypeId(9L);
			documentDto.setCreateBy(UserProfileUtils.getUserNameLogin());
			return documentDto;
		}

		Document entity = documentRepository.findOne(id);

		// dữ liệu ko tồn tại hoặc đã bị xóa
		if (entity == null || entity.getDeleteDate() != null) {
			throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
		}

		DocumentEditDto editDto = new DocumentEditDto();
		if (entity != null) {
			editDto.setId(entity.getId());
			editDto.setCode(entity.getCode());
			editDto.setCategoryId(entity.getCategoryId());
			editDto.setDocId(entity.getDocId());
			editDto.setPostedDate(entity.getPostedDate());
			editDto.setExpirationDate(entity.getExpirationDate());
			editDto.setEnabled(entity.isEnabled());

			List<DocumentVersionDto> documentVersions = documentVersionRepository.findAllVersionObjectByDocumentId(id);
			editDto.setVersions(documentVersions);

			DocumentDetail currentVersion = documentVersionRepository.findDocumentDetailByDocumentId(id, null);
			if (currentVersion != null) {
				editDto.setFileName(currentVersion.getFileName());
				editDto.setFileSize(currentVersion.getFileSize());
				editDto.setFileType(currentVersion.getFileType());
				editDto.setPhysicalFileName(currentVersion.getPhysicalFileName());
				editDto.setFileId(currentVersion.getId());
			}

			JpmStatusDeployDto status = jpmStatusDeployService.getStatusDeploy(editDto.getDocId(), locale.toString());
			if (status != null) {
				editDto.setStatusCode(status.getStatusCode());
				editDto.setStatusName(status.getStatusName());
			}

			List<DocumentLanguageDto> documentLangDtos = loadDocumentInfoByLanguage(entity);
			editDto.setListLanguage(documentLangDtos);
			editDto.setReferenceId(entity.getId());
			editDto.setReferenceType(ConstantHistoryApprove.APPROVE_DOCUMENT);
			editDto.setCreateBy(UserProfileUtils.getUserNameLogin());
			editDto.setUpdateDate(entity.getUpdateDate());
		}

		List<DocumentLanguageDto> listLanguage = getListLanguage(id);
		editDto.setListLanguage(listLanguage);

		return editDto;
	}

	/**
	 * @author TaiTM
	 */
	private List<DocumentLanguageDto> getListLanguage(Long id) {
		List<DocumentLanguageDto> resultList = new ArrayList<DocumentLanguageDto>();

		List<DocumentLanguage> listLanguage = documentLanguageRepository.findByDocumentId(id);

		for (DocumentLanguage entity : listLanguage) {
			DocumentLanguageDto dto = new DocumentLanguageDto();
			dto.setId(entity.getId());
			dto.setTitle(entity.getTitle());
			dto.setLanguageCode(entity.getLanguageCode());
			dto.setDocumentId(entity.getDocumentId());
			dto.setKeyword(entity.getKeyword());
			dto.setKeywordDescription(entity.getKeywordDescription());
			dto.setLinkAlias(entity.getLinkAlias());
			resultList.add(dto);
			break;
		}
		return resultList;
	}

	@Override
	public void saveOrUpdate(DocumentEditDto editDto, Locale locale) throws Exception {
		String username = UserProfileUtils.getUserNameLogin();

		createOrEditData(editDto, username, locale);
		createOrEditLanguage(editDto, username, locale);
	}

	private void createOrEditData(DocumentEditDto editDto, String userName, Locale locale) {
		Document entity = new Document();

		if (null != editDto.getId()) {
			entity = documentRepository.findOne(editDto.getId());
			if (null == entity || entity.getDeleteDate() != null) {
				throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
			}

			entity.setUpdateDate(new Date());
			entity.setUpdateBy(userName);
		} else {
			entity.setCreateDate(new Date());
			entity.setCreateBy(userName);
		}

		try {
			if ( editDto.getCategoryId() != null) {
				if(entity.getCategoryId() != editDto.getCategoryId()) {
				    entity.setCode(CommonUtil.getNextBannerCode(
	                        documentRepository.findGenCode(editDto.getCategoryId()).substring(0, 3),
	                        cmsCommonService.getMaxCodeYYMM("M_DOCUMENT",
	                                documentRepository.findGenCode(editDto.getCategoryId()).substring(0, 3))));
				}
			}
			entity.setDocId(editDto.getDocId());

			entity.setCategoryId(editDto.getCategoryId());
			entity.setEnabled(editDto.isEnabled());
			entity.setCustomerTypeId(editDto.getCustomerTypeId());
			entity.setSort(editDto.getSort());
			entity.setPostedDate(editDto.getPostedDate());
			entity.setExpirationDate(checkMaxDate(editDto.getExpirationDate()));
			if ("AD".equals(UserProfileUtils.getChannel())) {
				entity.setChannel("AD");
			} else {
				entity.setChannel("AG");
			}

			moveTmpImage(editDto, entity);

			entity = documentRepository.save(entity);

			if (entity != null) {
				if (!StringUtils.isEmpty(editDto.getFileName())) {
					if (editDto.getFileId() == null) {
						Integer currentVersion = documentVersionRepository
								.findCurrentVersionByDocumentId(entity.getId());
						documentVersionRepository.updateAllToOldVersionByDocumentId(entity.getId());

						DocumentDetail docVersionEntity = new DocumentDetail();
						docVersionEntity.setDocumentId(entity.getId());
						docVersionEntity.setFileName(editDto.getFileName());
						docVersionEntity.setFileSize(editDto.getFileSize());
						docVersionEntity.setFileType(editDto.getFileType());
						docVersionEntity.setPhysicalFileName(editDto.getPhysicalFileName());
						docVersionEntity.setVersion(currentVersion != null ? currentVersion + 1 : 1);
						docVersionEntity.setCurrentVersion(true);

						docVersionEntity.setCreateBy(userName);
						docVersionEntity.setCreateDate(new Date());

						moveTmpFile(docVersionEntity, editDto.getTempFileUrl(), editDto.getCategoryId());

						docVersionEntity = documentVersionRepository.save(docVersionEntity);
						editDto.setCurrentVersion(docVersionEntity.getVersion());
						editDto.setPhysicalFileName(docVersionEntity.getPhysicalFileName());
					}
				}
			}

			if (editDto.getId() == null) {
				editDto.setId(entity.getId());
				editDto.setCode(entity.getCode());

			}
			editDto.setDataId(entity.getId());
		} catch (Exception e) {
			logger.error("saveUpdateDocument", e.getMessage());
		}
	}

	private void createOrEditLanguage(DocumentEditDto editDto, String userName, Locale locale) {
		try {
			for (DocumentLanguageDto dto : editDto.getListLanguage()) {
				DocumentLanguage entity = new DocumentLanguage();
                DocumentLanguage entityEn = new DocumentLanguage();
				if (null != dto.getId()) {
					entity = documentLanguageRepository.findLanguage(editDto.getId(),"vi");
					entityEn = documentLanguageRepository.findLanguage(editDto.getId(),"en");
					if (null == entity || null == entityEn) {
						throw new BusinessException(
								msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_WITH_ID, null, locale)
										+ editDto.getId());
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
				entity.setDocumentId(editDto.getId());
				entity.setLanguageCode("VI");
				entity.setTitle(dto.getTitle());
				entity.setLinkAlias(dto.getLinkAlias());
				entity.setKeyword(dto.getKeyword());
				entity.setKeywordDescription(dto.getKeywordDescription());
				documentLanguageRepository.save(entity);
				
				entityEn.setDocumentId(editDto.getId());
				entityEn.setLanguageCode("EN");
				entityEn.setTitle(dto.getTitle());
				entityEn.setLinkAlias(dto.getLinkAlias());
				entityEn.setKeyword(dto.getKeyword());
				entityEn.setKeywordDescription(dto.getKeywordDescription());
                documentLanguageRepository.save(entityEn);
			}
		} catch (Exception e) {
			logger.error("createOrEditLanguage: " + e);
			throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
		}
	}

	@Override
	public void deleteDataById(Long id) throws Exception {
		Document entity = documentRepository.findOne(id);
		String userName = UserProfileUtils.getUserNameLogin();
		entity.setDeleteBy(userName);
		entity.setDeleteDate(new Date());
		documentRepository.save(entity);
	}

	@Override
	public List<DocumentSearchResultDto> getListForSort(DocumentSearchDto searchDto) {
		List<DocumentSearchResultDto> resultDtoss = documentRepository.findListForSort(searchDto);
		return resultDtoss;
	}

	@Override
	public void updateSortAll(DocumentSearchDto searchDto) {
		if (CollectionUtils.isNotEmpty(searchDto.getSortOderList())) {
			for (SortOrderDto dto : searchDto.getSortOderList()) {
				documentRepository.updateSortAll(dto);
			}
		}
	}

	@Override
	public Class<DocumentExportEnum> getEnumColumnForExport() {
		return DocumentExportEnum.class;
	}

	@Override
	public String getTemplateNameForExport(Locale locale) {
		return CmsCommonConstant.TEMPLATE_DOCUMENT;
	}

	@Override
	public CommonSearchFilterUtils getCommonSearchFilterUtils() {
		return commonSearchFilterUtils;
	}

	@Override
	public List<String> getListRoleNameByUserName(String userNameLogin) {
		return documentRepository.getListRoleNameByUserName(userNameLogin);
	}

	@Override
	public boolean isUserHasRoleMaker(String username) {
		return Optional.ofNullable(documentRepository.isUserHasRoleMaker(username)).orElse(0) > 0;
	}

	@Override
	public String getLsDocument(Long id, String lang) {
		return documentRepository.getCategoryName(id,lang);
	}
	@Override 
	public List<JcaGroupEmailDto> lstEmailCcByCondition(String roleCode, String groupCode) {
		return documentRepository.lstEmailCcByCondition(roleCode, groupCode, UserProfileUtils.getChannel());
	}


	@Override
	public Document getlsDocument(Long id) {
		return documentRepository.findOne(id);
	}

	@Override
	public String getbutton(Long id, Long idp) {
		return documentRepository.getbutton(id,idp);
	}

	@Override
	public List<JcaGroupEmailDto> findGourpMailByTaskId(Long jpmTaskId) {
		return documentRepository.findGourpMailByTaskId(jpmTaskId);
	}

	@Override
	public List<JcaGroupEmailDto> getListRoleNameByUserNameAndProcessDeployId(String userNameLogin,
			Long processDeployId, String permissonCode) {
		return documentRepository.getListRoleNameByUserNameAndProcessDeployId(userNameLogin, processDeployId, permissonCode, UserProfileUtils.getChannel());
	}
}
