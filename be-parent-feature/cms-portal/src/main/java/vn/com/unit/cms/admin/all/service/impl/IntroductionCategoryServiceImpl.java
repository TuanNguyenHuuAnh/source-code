package vn.com.unit.cms.admin.all.service.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
//import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.constant.AdminConstant;
import vn.com.unit.cms.admin.all.constant.AdminUrlConst;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.core.IntroductionCategoryNode;
//import vn.com.unit.cms.admin.all.dto.EmailCommonDto;
import vn.com.unit.cms.admin.all.dto.ExportIntroductionCategoryReportDto;
import vn.com.unit.cms.admin.all.dto.IntroductionCategoryDto;
import vn.com.unit.cms.admin.all.dto.IntroductionCategoryLanguageDto;
import vn.com.unit.cms.admin.all.dto.IntroductionCategorySearchDto;
import vn.com.unit.cms.admin.all.dto.IntroductionLanguageDto;
import vn.com.unit.cms.admin.all.entity.IntroductionCategory;
import vn.com.unit.cms.admin.all.entity.IntroductionCategoryLanguage;
import vn.com.unit.cms.admin.all.enumdef.ExportIntroductionCategoryExportEnum;
import vn.com.unit.cms.admin.all.enumdef.StepActionEnum;
import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;
//import vn.com.unit.jcanary.repository.LanguageRepository;
//import vn.com.unit.jcanary.service.AccountService;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.jcanary.utils.APIUtils;
import vn.com.unit.cms.admin.all.repository.IntroductionCategoryLanguageRepository;
import vn.com.unit.cms.admin.all.repository.IntroductionCategoryRepository;
import vn.com.unit.cms.admin.all.repository.IntroductionRepository;
import vn.com.unit.cms.admin.all.service.CmsFileService;
import vn.com.unit.cms.admin.all.service.IntroductionCategoryService;
import vn.com.unit.cms.core.module.banner.enumdef.StepStatusEnum;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.ep2p.dto.JProcessStepDto;
import vn.com.unit.common.dto.SearchKeyDto;
import vn.com.unit.common.exception.SystemException;
//import vn.com.unit.jcanary.utils.Utils;
import vn.com.unit.common.utils.CommonUtil;
//import vn.com.unit.cms.admin.all.util.EmailUtil;
import vn.com.unit.core.entity.Language;
import vn.com.unit.core.repository.LanguageRepository;
//import vn.com.unit.jcanary.authentication.UserProfile;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
//import vn.com.unit.cms.admin.enumdef.StepActionEnum;
import vn.com.unit.ep2p.core.efo.dto.EfoDocDto;
import vn.com.unit.ep2p.core.ers.service.impl.DocumentWorkflowCommonServiceImpl;
//import vn.com.unit.dto.ItemColsExcelDto;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.ep2p.core.res.dto.DocumentAppRes;
import vn.com.unit.ep2p.core.res.dto.DocumentSaveReq;
import vn.com.unit.ep2p.core.utils.Utility;
//import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
import vn.com.unit.ep2p.dto.AccountDetailDto;
//import vn.com.unit.jcanary.utils.ExportExcelUtil;
//import vn.com.unit.jcanary.utils.ImportExcelUtil;
import vn.com.unit.ep2p.utils.SearchUtil;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;
//import vn.com.unit.jcanary.service.HistoryApproveService;
//import vn.com.unit.jcanary.service.JProcessService;
import vn.com.unit.workflow.dto.JpmButtonWrapper;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class IntroductionCategoryServiceImpl
        extends DocumentWorkflowCommonServiceImpl<IntroductionCategoryDto, IntroductionCategoryDto>
        implements IntroductionCategoryService {

    private static final String NAME = "name";
    private static final String CODE = "code";
    private static final String NOTE = "note";
    private static final String DESCRIPTION = "description";
    private static final String LABEL = "label";

    private static final String[] categorySearchFieldDispNames = { "searchfield.disp.code", "searchfield.disp.name",
            "searchfield.disp.note", "searchfield.disp.description", "searchfield.disp.label" };

    private static final String[] categorySearchFieldIds = { CODE, NAME, NOTE, DESCRIPTION, LABEL };

    private static final String PREFIX_CODE_CATEGORY = "INTRO.C.";

    @Autowired
    IntroductionRepository introRepo;

    @Autowired
    IntroductionCategoryRepository introCateRepo;

    @Autowired
    IntroductionCategoryLanguageRepository introCateLangRepo;

    @Autowired
    @Qualifier("languageRepository")
    LanguageRepository langRepo;

    @Autowired
    CmsFileService fileService;

    @Autowired
    private CmsCommonService commonService;

//    @Autowired
//    JProcessService jprocessService;
//    
//    @Autowired
//    HistoryApproveService historyApproveService;

    @Autowired
    MessageSource msg;

//    @Autowired
//    AccountService accountService;
//    
//    @Autowired
//    EmailService emailService;
//    
//    @Autowired
//    EmailUtil emailUtil;

    @Autowired
    ServletContext servletContext;

    @Autowired
    private SystemConfig systemConfig;

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(IntroductionCategoryServiceImpl.class);

    /**
     * Return list of Intro category with field values contain respect parameter
     * value
     *
     * @param code
     * @param name
     * @param note
     * @param description
     * @return IntroductionCategory list
     * @see findAll
     */
    @Override
    public PageWrapper<IntroductionCategoryDto> getActiveCategoryByCondition(int page, int sizeOfPage,
            CommonSearchDto searchDto, Locale locale) {
        IntroductionCategorySearchDto condition = this.genCategorySearchCondition(searchDto);

        String languageCode = locale.toString();

        // set status name
        condition.setStatusName(msg.getMessage(StepStatusEnum.DRAFT.getStatusName(), null, locale));

        PageWrapper<IntroductionCategoryDto> pageWrapper = new PageWrapper<IntroductionCategoryDto>(page, sizeOfPage);

        List<IntroductionCategoryDto> categories = new ArrayList<IntroductionCategoryDto>();
        int count = introCateRepo.countRootActiveByConditions(condition, languageCode);
        if (count > 0) {
            int itemOffset = Utility.calculateOffsetSQL(page, sizeOfPage);
            categories = introCateRepo.findRootActiveByConditions(itemOffset, sizeOfPage, condition, languageCode);
        }

        List<IntroductionCategoryDto> dtoList = loadFullCategoryTreeByRoots(categories, false, null, languageCode);
        int pageFirstIndex = 1;
        for (int prePage = 1; prePage < page; prePage++) {
            int prePageItemOffset = Utility.calculateOffsetSQL(prePage, sizeOfPage);
            List<IntroductionCategoryDto> prePagecategories = introCateRepo
                    .findRootActiveByConditions(prePageItemOffset, sizeOfPage, condition, languageCode);
            List<IntroductionCategoryDto> prePageDtoList = loadFullCategoryTreeByRoots(prePagecategories, true, null,
                    languageCode);
            pageFirstIndex += prePageDtoList.size();
        }
        pageWrapper.setDataAndCount(dtoList, count);
        pageWrapper.setStartIndexCurrent(pageFirstIndex);

        if (count > 0 && dtoList.isEmpty()) {
            constructorTreeData(pageWrapper, dtoList, condition, page, sizeOfPage, languageCode);
        }

        return pageWrapper;
    }

    @Override
    public IntroductionCategoryDto getIntroductionById(Long id, String lang) {
        IntroductionCategoryDto introductionCategoryDto = introCateRepo.findDetailById(id, lang);
        return introductionCategoryDto;
    }

    @Override
    public List<IntroductionCategoryDto> getAllActive(String languageCode, Integer status) {
        List<IntroductionCategoryDto> introductionCategoryDtoList = introCateRepo.findAllActive(languageCode, status);
        return introductionCategoryDtoList;
    }

    @Override
    public List<SearchKeyDto> genCategorySearchKeyList(Locale locale) {
        List<SearchKeyDto> searchKeys = SearchUtil.genSearchKeyList(categorySearchFieldIds,
                categorySearchFieldDispNames, locale, msg);
        return searchKeys;
    }

    @Override
    public List<IntroductionCategoryNode> findSelectionCategoryTree(Long exceptCategoryId, String lang) {
        List<IntroductionCategoryDto> categories = introCateRepo.findAllRootActive(lang);
        List<IntroductionCategoryDto> listCategoryDto = this.loadFullCategoryTreeByRoots(categories, true,
                exceptCategoryId, lang);

        List<IntroductionCategoryNode> categoryTree = new LinkedList<IntroductionCategoryNode>();
        List<IntroductionCategoryDto> listRoot = new LinkedList<IntroductionCategoryDto>();
        if (null != listCategoryDto && !listCategoryDto.isEmpty()) {
            if (null != listCategoryDto) {
                for (IntroductionCategoryDto category : listCategoryDto) {
                    if (null == category.getParentId() || category.getParentId().equals(0L))
                        listRoot.add(category);
                }
                List<IntroductionCategoryNode> parentNodeList = new ArrayList<IntroductionCategoryNode>();
                for (IntroductionCategoryDto categoryDto : listRoot) {
                    IntroductionCategoryNode categoryNode = new IntroductionCategoryNode();
                    categoryNode.setId(categoryDto.getId());
                    categoryNode.setText(categoryDto.getTitle());
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

    /**
     * Return update result
     *
     * @param updateModel
     * @return true/false update result
     * @throws IOException
     * @see
     */
    @Override
    public IntroductionCategoryDto saveUpdateCategory(IntroductionCategoryDto updateDto, Locale locale,
            HttpServletRequest request) throws IOException {
//        UserProfile userProfile = UserProfileUtils.getUserProfile();

        createOrEditIntroductionCategory(updateDto, UserProfileUtils.getUserNameLogin(), locale, request);

        createOrEditIntroductionCategoryLanguage(updateDto, UserProfileUtils.getUserNameLogin(), locale);

        CmsUtils.moveTempSubFolderToUpload(
                Paths.get(AdminConstant.INTRODUCTION_CATEGORY_EDITOR_FOLDER, updateDto.getRequestToken()).toString());

        // if action process
        if (!StringUtils.equals(updateDto.getButtonId().toString(), StepActionEnum.SAVE.getCode())) {
            // update history approve
            updateHistoryApprove(updateDto, locale);

            // send mail
            sendMail(updateDto, request, locale);

            // clear cache api /about
            APIUtils.callApiGet(AdminUrlConst.URL_CACHES_ABOUT);
        }

        return updateDto;
    }

    private void createOrEditIntroductionCategory(IntroductionCategoryDto updateDto, String usernameLogin,
            Locale locale, HttpServletRequest request) throws IOException {
        String lang = locale.toString();

        if (updateDto.getParentId() != null && updateDto.getParentId().equals(-1l)) {
            updateDto.setParentId(null);
        }
        IntroductionCategory entity = new IntroductionCategory();
        if (updateDto.getId() != null) {
            entity = introCateRepo.findOne(updateDto.getId());

            // dữ liệu ko tồn tại hoặc đã bị xóa
            if (entity == null || entity.getDeleteDate() != null) {
                throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
            }

            if (entity.getUpdateDate() != null && !entity.getUpdateDate().equals(updateDto.getUpdateDate())) {
                throw new BusinessException(msg.getMessage(ConstantCore.MSG_DATA_IS_UPDATED_BY_OTHERS, null, locale));
            }

            entity.setUpdateDate(new Date());
            entity.setUpdateBy(usernameLogin);
        } else {
            entity.setCreateDate(new Date());
            entity.setCreateBy(usernameLogin);
            // code generation
            updateDto.setCode(CommonUtil.getNextCode(PREFIX_CODE_CATEGORY,
                    commonService.getMaxCode("M_INTRODUCTION_CATEGORY", PREFIX_CODE_CATEGORY)));
        }

        try {
            entity.copyDtoProperties(updateDto);

            if (!checkValidParentCategory(updateDto, lang)) {
                throw new BusinessException("invalid parent category");
            }

            // set sort and beforeId for dto
            updateSortAndBeforeId(updateDto, lang);
            entity.setBeforeId(updateDto.getBeforeId());
            entity.setSort(updateDto.getSort());

            moveTmpImage(updateDto, entity);

            // handle video
            setPhysicalVideo(updateDto, entity);

//            String businessCode = CommonConstant.HDBANK_BUSINESS_INTRODUCTION;

            // if action process
            if (!StringUtils.equals(updateDto.getButtonId().toString(), StepActionEnum.SAVE.getCode())) {
                if (updateDto.getProcessId() == null) {
                    // First step
//                    JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(businessCode,
//                            locale.toString());
                    // set process id
//                    updateDto.setProcessId(processDto.getProcessId());
                }

                // current step
//                JProcessStepDto currentActionStep = jprocessService.findCurrentProcessStep(updateDto.getProcessId(),
//                        updateDto.getStatus(), updateDto.getButtonId());
//                Integer status = jprocessService.getNextStepNo(currentActionStep, null);

                updateDto.setOldStatus(updateDto.getStatus());

                // set status
//                updateDto.setStatus(status);
//                updateDto.setCurrItem(currentActionStep.getItems());
            }

            entity.setProcessId(updateDto.getProcessId());
            entity.setStatus(updateDto.getStatus());

            // update type of libary
            if (entity.getTypeOfMain() != null && entity.getTypeOfMain() == 1) {
                IntroductionCategory category = getIntroductionByType(11L, entity.getTypeOfMain(), -1);
                if (category != null) {
                    category.setTypeOfMain(null);
                    introCateRepo.save(category);
                }
            }

            if (updateDto.getTypeOfMain() != null && updateDto.getTypeOfMain() == true) {
                entity.setTypeOfMain(1);
            } else {
                entity.setTypeOfMain(null);
            }

            if (entity.getPictureIntroduction() != null && entity.getPictureIntroduction() == 1) {
                IntroductionCategory category = getIntroductionByType(11L, -1, entity.getPictureIntroduction());
                if (category != null) {
                    category.setTypeOfMain(null);
                    introCateRepo.save(category);
                }
            }

            entity = introCateRepo.save(entity);

            updateDto.setId(entity.getId());
            updateDto.setCode(entity.getCode());

            // update before id for introduction category after edited
            updateBeforeIdForIntroductionCategoryAfter(updateDto, lang);
        } catch (Exception e) {
            logger.error("createOrEditIntroductionCategory: " + e.getMessage());
            throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
        }
    }

    private void updateSortAndBeforeId(IntroductionCategoryDto editDto, String lang) {
        List<IntroductionCategoryDto> lstProductType = getListSortRemovedId(null, null, lang);

        if (null != editDto.getBeforeId()) {
            // tim product truoc va gan sort cho productEdit
            int indexBefore = lstProductType.size();
            for (int i = 0, sz = indexBefore; i < sz; i++) {
                IntroductionCategoryDto item = lstProductType.get(i);

                if (item.getId().equals(editDto.getBeforeId())) {
                    indexBefore = i;
                    editDto.setSort(item.getSort() + 1);
                    break;
                }
            }

            // gan sort cho cac product sau produtEdit
            Long currentSort = editDto.getSort() + 1;
            for (int i = indexBefore + 1, sz = lstProductType.size(); i < sz; i++) {
                IntroductionCategoryDto item = lstProductType.get(i);
                IntroductionCategory entity = introCateRepo.findOne(item.getId());
                entity.setSort(currentSort++);
                introCateRepo.save(entity);
            }
        } else {
            if (CollectionUtils.isNotEmpty(lstProductType)) {
                IntroductionCategoryDto item = lstProductType.get(lstProductType.size() - 1);
                editDto.setSort(item.getSort() + 1);
                if (!item.getId().equals(editDto.getId())) {
                    editDto.setBeforeId(item.getId());
                } else if (lstProductType.size() > 2) {
                    editDto.setBeforeId(lstProductType.get(lstProductType.size() - 2).getId());
                } else {
                    editDto.setBeforeId(0l);
                }
            } else {
                editDto.setSort(1l);
                editDto.setBeforeId(0l);
            }
        }
    }

    private void updateBeforeIdForIntroductionCategoryAfter(IntroductionCategoryDto editDto, String lang) {
        List<IntroductionCategoryDto> lstProductType = getListSortRemovedId(null, null, lang);

        if (CollectionUtils.isEmpty(lstProductType)) {
            return;
        }

        IntroductionCategoryDto item = lstProductType.get(0);
        if (item.getId().equals(editDto.getBeforeId()) && !item.getBeforeId().equals(0l)) {
            IntroductionCategory entity = introCateRepo.findOne(item.getId());
            entity.setBeforeId(0l);
            introCateRepo.save(entity);
        }

        for (int i = 0, sz = lstProductType.size(); i < sz - 1; i++) {
            item = lstProductType.get(i);
            if (item.getId().equals(editDto.getId())) {
                IntroductionCategoryDto itemNext = lstProductType.get(i + 1);
                IntroductionCategory entity = introCateRepo.findOne(itemNext.getId());
                entity.setBeforeId(item.getId());
                introCateRepo.save(entity);
                break;
            }
        }
    }

    private void createOrEditIntroductionCategoryLanguage(IntroductionCategoryDto updateDto, String usernameLogin,
            Locale locale) {
        try {
            List<IntroductionCategoryLanguage> introCateLangEntities = updateDto.createCateLanguageEntities();
            for (IntroductionCategoryLanguage introCateLangEntity : introCateLangEntities) {
                introCateLangEntity.setCategoryId(updateDto.getId());
            }

            if (updateDto != null) {
                for (IntroductionCategoryLanguage introCateLangEntity : introCateLangEntities) {
                    introCateLangEntity.setUpdateBy(usernameLogin);
                    introCateLangEntity.setUpdateDate(new Date());
                }
                introCateLangRepo.save(introCateLangEntities);
                List<IntroductionCategoryLanguageDto> infoBylanguage = this
                        .loadCategoryInfoByLanguage(updateDto.getId());
                updateDto.setInfoByLanguages(infoBylanguage);
            }
        } catch (Exception e) {
            logger.error("createOrEditIntroductionCategoryLanguage: " + e.getMessage());
            throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
        }
    }

    /**
     * Return update result
     *
     * @param updateModel
     * @return true/false update result
     * @throws IOException
     * @see
     */
    @Override
    public IntroductionCategoryDto saveNewCategory(IntroductionCategoryDto updateDto) throws IOException {
        if (updateDto.getParentId() != null && updateDto.getParentId().equals(-1l)) {
            updateDto.setParentId(null);
        }
        IntroductionCategory entity = updateDto.createIntroCateEntity();
        List<IntroductionCategoryLanguage> introCateLangEntities = updateDto.createCateLanguageEntities();
//        UserProfile userProfile = UserProfileUtils.getUserProfile();
        entity.setCreateBy(UserProfileUtils.getUserNameLogin());
        entity.setCreateDate(new Date());
        moveTmpImage(updateDto, entity);

        // handle video
        setPhysicalVideo(updateDto, entity);

        entity = introCateRepo.save(entity);
        for (IntroductionCategoryLanguage introCateLangEntity : introCateLangEntities) {
            introCateLangEntity.setCategoryId(entity.getId());
        }
        IntroductionCategoryDto resultDto = null;
        if (entity != null) {
            introCateLangRepo.save(introCateLangEntities);
            List<IntroductionCategoryLanguageDto> infoBylanguage = this.loadCategoryInfoByLanguage(entity.getId());
            resultDto = new IntroductionCategoryDto(entity);
            resultDto.setInfoByLanguages(infoBylanguage);
        }

        CmsUtils.moveTempSubFolderToUpload(
                Paths.get(AdminConstant.INTRODUCTION_CATEGORY_EDITOR_FOLDER, updateDto.getRequestToken()).toString());
        return resultDto;
    }

    /**
     * get category list with name, id for selection
     * 
     * @return ArrayList<IntroductionCategorySelectDto>
     */
    @Override
    public List<IntroductionCategoryDto> getCategoriesForSelection(Long exceptCategoryId, String lang) {
        List<IntroductionCategoryDto> categories = introCateRepo.findRootListForSelection();

        List<IntroductionCategoryDto> categoryDtos = loadFullCategoryTreeByRoots(categories, true, exceptCategoryId,
                lang);
        return categoryDtos;
    }

    /**
     * @param page       int
     * @param sizeOfpage int
     */
    @Override
    public PageWrapper<IntroductionCategoryDto> getAllActiveCategory(int page, int sizeOfPage, String languageCode) {
        PageWrapper<IntroductionCategoryDto> pageWrapper = new PageWrapper<IntroductionCategoryDto>(page, sizeOfPage);

        List<IntroductionCategoryDto> categories = null;
        IntroductionCategorySearchDto condition = new IntroductionCategorySearchDto();
        int count = introCateRepo.countRootActiveByConditions(condition, languageCode);

        if (count > 0) {
            int itemOffset = Utility.calculateOffsetSQL(page, sizeOfPage);
            categories = introCateRepo.findRootActive(itemOffset, sizeOfPage, languageCode);
        }
        List<IntroductionCategoryDto> dtoList = new ArrayList<IntroductionCategoryDto>();
        if (categories != null && categories.size() > 0) {
            dtoList = this.loadFullCategoryTreeByRoots(categories, false, null, languageCode);
        }
        int pageFirstIndex = 1;
        for (int prePage = 1; prePage < page; prePage++) {
            int prePageItemOffset = Utility.calculateOffsetSQL(prePage, sizeOfPage);
            List<IntroductionCategoryDto> prePagecategories = introCateRepo.findRootActive(prePageItemOffset,
                    sizeOfPage, languageCode);
            List<IntroductionCategoryDto> prePageDtoList = this.loadFullCategoryTreeByRoots(prePagecategories, true,
                    null, languageCode);
            pageFirstIndex += prePageDtoList.size();
        }
        pageWrapper.setDataAndCount(dtoList, count);
        pageWrapper.setStartIndexCurrent(pageFirstIndex);

        if (count > 0 && dtoList.isEmpty()) {
            constructorTreeData(pageWrapper, dtoList, condition, page, sizeOfPage, languageCode);
        }

        return pageWrapper;
    }

    /**
     * Return DtoObject
     * 
     * @param id
     * @Return IntroductionCategory (With infos by language -- label)
     * @see
     */
    @Override
    public IntroductionCategoryDto getCategory(Long id, Locale locale, String businessCode) {

        IntroductionCategoryDto resultDto = new IntroductionCategoryDto();

        if (id == null) {
            resultDto.setEnabled(Boolean.TRUE);
            resultDto.setStatus(StepStatusEnum.DRAFT.getStepNo());
            resultDto.setCreateBy(UserProfileUtils.getUserNameLogin());
            resultDto.setCreateDate(new Date());
        } else {
            IntroductionCategory entity = introCateRepo.findOne(id);
            // dữ liệu ko tồn tại hoặc đã bị xóa
            if (entity == null || entity.getDeleteDate() != null) {
                throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
            }
            if (null != entity) {
                List<IntroductionCategoryLanguageDto> infoBylanguage = this.loadCategoryInfoByLanguage(entity.getId());
                resultDto = new IntroductionCategoryDto(entity);
                resultDto.setInfoByLanguages(infoBylanguage);
            }
        }

        // TODO : process

//        Long processId = resultDto.getProcessId();
//        if (processId == null) {
//            // First step
//            JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(businessCode, locale.toString());
//            processId = processDto.getProcessId();
//
//        }
//        // List button of step
//        List<JProcessStepDto> stepButtonList = jprocessService.findStepButtonListHasRole(processId,
//                resultDto.getStatus(), locale.toString());
//        resultDto.setStepBtnList(stepButtonList);
//
//        String statusName = jprocessService.getStatusName(resultDto.getProcessId(), resultDto.getStatus(), locale);
//        resultDto.setStatusName(statusName);

        return resultDto;
    }

    @Override
    public IntroductionCategoryDto getCategoryViewDto(Long id) {
        IntroductionCategoryDto introCateDto = introCateRepo.findCategoryViewDetail(id);
        if (introCateDto != null) {
            List<IntroductionCategoryLanguageDto> infoBylanguage = this
                    .loadCategoryInfoByLanguage(introCateDto.getId());
            introCateDto.setInfoByLanguages(infoBylanguage);
        }

        return introCateDto;
    }

    /**
     * @param CategoryId type Long
     */
    @Override
    public boolean deleteCateById(Long id) {
        IntroductionCategory entity = introCateRepo.findOne(id);
        String userName = UserProfileUtils.getUserNameLogin();
        entity.setDeleteBy(userName);
        entity.setDeleteDate(new Date());
        IntroductionCategory resultEntity = introCateRepo.save(entity);
        if (resultEntity != null) {
            introRepo.deleteByCategoryId(id, userName, new Date());
        }
        return resultEntity != null;
    }

    /**
     * Count Category by code
     */
    @Override
    public int countCategoryByCode(String code) {
        int count = introCateRepo.countByCode(code);
        return count;
    }

    @Override
    public Long getMaxCategorySort(Long parentId) {
        Long maxSort = introCateRepo.findMaxSort(parentId);
        if (maxSort == null) {
            maxSort = 0L;
        }
        return maxSort;
    }

    @Override
    public List<IntroductionCategoryDto> getAllActiveCategoryForSelection() {
        List<IntroductionCategoryDto> selectionCategoryEntities = introCateRepo.findAllActiveForSelection();
        List<IntroductionCategoryDto> categorySelectionList = new ArrayList<IntroductionCategoryDto>();
        for (IntroductionCategoryDto categoryEntity : selectionCategoryEntities) {
            categorySelectionList.add(categoryEntity);
        }
        return categorySelectionList;
    }

    @Override
    public int getCategoryCount(String languageCode, String linkAlias, Long exceptedId, Long parentId) {
        int categoryCount = introCateRepo.countLinkAliasByParentAndIntroductionTypeExceptId(languageCode, linkAlias,
                exceptedId, parentId);
        return categoryCount;
    }

    /**
     * getMaxCodeIntroduction
     *
     * @author nhutnn
     * @return max code
     */
    @Override
    public String getMaxCodeIntroductionCategory() {
        return introCateRepo.getMaxCode();
    }

    @Override
    public List<IntroductionCategoryDto> getListSortRemovedId(Long id, Long parentId, String lang) {
        List<IntroductionCategoryDto> lstCate = introCateRepo.findListSortRemovedId(id, parentId, lang);
        if (lstCate == null) {
            lstCate = new ArrayList<IntroductionCategoryDto>();
            return lstCate;
        }
        return lstCate;
    }

    @Override
    public boolean requestEditorDownload(String fileUrl, HttpServletRequest request, HttpServletResponse response) {
        boolean retVal = false;
        if (fileUrl != null) {
            if (CmsUtils.fileExistedInMain(fileUrl)) {
                fileService.download(fileUrl, request, response);
                retVal = true;
            } else if (CmsUtils.fileExistedInTemp(fileUrl)) {
                fileService.downloadTemp(fileUrl, request, response);
                retVal = true;
            }
        }
        return retVal;
    }

    /**
     * 
     * @param categoryId
     * @return
     */
    private List<IntroductionCategoryLanguageDto> loadCategoryInfoByLanguage(Long categoryId) {
        List<IntroductionCategoryLanguage> infoBylanguageEntities = introCateLangRepo.findByCategoryId(categoryId);
        HashMap<String, IntroductionCategoryLanguage> introLangMap = new HashMap<String, IntroductionCategoryLanguage>();
        for (IntroductionCategoryLanguage infoByLangEntity : infoBylanguageEntities) {
            introLangMap.put(infoByLangEntity.getLanguageCode(), infoByLangEntity);
        }
        List<Language> languages = langRepo.findAllActive();
        List<IntroductionCategoryLanguageDto> cateLangDtos = new ArrayList<IntroductionCategoryLanguageDto>();
        for (Language language : languages) {
            IntroductionCategoryLanguage cateLangEntity = introLangMap.get(language.getCode());
            if (cateLangEntity != null) {
                IntroductionCategoryLanguageDto introLangDto = new IntroductionCategoryLanguageDto(cateLangEntity,
                        language.getName());
                cateLangDtos.add(introLangDto);
            } else {
                IntroductionCategoryLanguageDto cateLangDto = new IntroductionCategoryLanguageDto();
                cateLangDto.setCategoryId(categoryId);
                cateLangDto.setLanguageCode(language.getCode());
                cateLangDto.setLanguageDispName(language.getName());
                cateLangDtos.add(cateLangDto);
            }
        }
        return cateLangDtos;
    }

    private List<IntroductionCategoryNode> loadAllSubNodes(List<IntroductionCategoryNode> parentNodeList,
            List<IntroductionCategoryDto> listAllCategory) {
        List<IntroductionCategoryNode> retVal = new ArrayList<IntroductionCategoryNode>();
        for (IntroductionCategoryNode parentNodeItem : parentNodeList) {
            List<IntroductionCategoryNode> listSub = getListNodeSubCategory(parentNodeItem.getId(), listAllCategory);
            parentNodeItem.setChildren(listSub);
            if (listSub != null && listSub.size() > 0) {
                retVal.addAll(listSub);
            }
        }
        return retVal;
    }

    private List<IntroductionCategoryNode> getListNodeSubCategory(Long menuId,
            List<IntroductionCategoryDto> listAllCategory) {
        List<IntroductionCategoryNode> listSubCategory = new LinkedList<IntroductionCategoryNode>();
        for (IntroductionCategoryDto menu : listAllCategory) {
            if (menu.getParentId() != null && menu.getParentId().equals(menuId)) {
                IntroductionCategoryNode categoryNode = new IntroductionCategoryNode();
                categoryNode.setId(menu.getId());
                categoryNode.setText(menu.getName());
                categoryNode.setState(ConstantCore.OPEN);
                listSubCategory.add(categoryNode);
            }
        }
        return listSubCategory;
    }

    /**
     * @param updateDto
     */
    private boolean checkValidParentCategory(IntroductionCategoryDto updateDto, String lang) {
        boolean retVal = true;
        if (updateDto.getParentId() != null) {
            List<IntroductionCategoryDto> descendandsCategories = this.findDescendandCategories(updateDto, true, lang);
            if (descendandsCategories != null) {
                for (IntroductionCategoryDto category : descendandsCategories) {
                    if (category.getId().equals(updateDto.getParentId())) {
                        retVal = false;
                    }
                }
            }
        }
        return retVal;
    }

    private List<IntroductionCategoryDto> getRoots(List<IntroductionCategoryDto> categoryList) {
        List<IntroductionCategoryDto> results = new ArrayList<>();
        for (IntroductionCategoryDto category : categoryList) {
            if (category.getParentId() == null) {
                results.add(category);
            }
        }
        return results;
    }

    private List<IntroductionCategoryDto> sortMenuDtoByConstructorTree(List<IntroductionCategoryDto> categoryList,
            List<IntroductionCategoryDto> roots) {
        List<IntroductionCategoryDto> result = new ArrayList<>();
        result.addAll(roots);
        int i = 0;
        while (i < result.size()) {
            IntroductionCategoryDto menu = result.get(i);
            Long menuId = menu.getId();
            List<IntroductionCategoryDto> menuChildren = new ArrayList<IntroductionCategoryDto>();
            if (categoryList != null && !categoryList.isEmpty()) {

                int menuListSize = categoryList.size();

                for (int j = menuListSize - 1; j >= 0; j--) {
                    IntroductionCategoryDto menuDto = categoryList.get(j);

                    Long parentId = menuDto.getParentId();

                    if (parentId != null && parentId.equals(menuId)) {
                        result.add(i + 1, menuDto);
                        menuChildren.add(menuDto);
                    }
                }
                categoryList.removeAll(menuChildren);
                i++;
            } else {
                break;
            }

        }
        return result;
    }

    private List<IntroductionCategoryDto> findDescendandCategories(IntroductionCategoryDto categoryDto,
            boolean forSelection, String lang) {
        List<IntroductionCategoryDto> entityList = new ArrayList<IntroductionCategoryDto>();
        entityList.add(categoryDto);
        List<IntroductionCategoryDto> descendands = this.loadDescendandCategories(entityList, forSelection, null, lang);
        return descendands;
    }

    private List<Long> createCategoryIdList(List<IntroductionCategoryDto> categories) {
        List<Long> idList = new ArrayList<Long>();
        for (IntroductionCategoryDto category : categories) {
            idList.add(category.getId());
        }
        return idList;
    }

    /**
     * @param categories
     * @param forSelection
     * @param exceptCategoryId
     * @param selectionParentIds
     * @return
     */
    private List<IntroductionCategoryDto> loadDescendandCategories(List<IntroductionCategoryDto> categories,
            boolean forSelection, Long exceptCategoryId, String lang) {
        List<Long> selectionParentIds = this.createCategoryIdList(categories);
        if (selectionParentIds.size() > 0) {
            List<IntroductionCategoryDto> childrens;
            do {
                if (forSelection) {
                    childrens = introCateRepo.findAllActiveChildrenCategoryForSelection(selectionParentIds, lang);
                } else {
                    childrens = introCateRepo.findAllActiveChildrenCategory(selectionParentIds, lang);
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
     * @param forSelection
     * @return
     */
    private List<IntroductionCategoryDto> loadFullCategoryTreeByRoots(List<IntroductionCategoryDto> categories,
            boolean forSelection, Long exceptCategoryId, String lang) {
        if (exceptCategoryId != null) {
            categories = removeExceptCategory(categories, exceptCategoryId);
        }
        categories = loadDescendandCategories(categories, forSelection, exceptCategoryId, lang);
        List<IntroductionCategoryDto> categoryDtos = new ArrayList<IntroductionCategoryDto>();
        for (IntroductionCategoryDto category : categories) {
            categoryDtos.add(category);
        }
        categoryDtos = sortCategoryDtoByConstructorTree(categoryDtos);
        return categoryDtos;
    }

    /**
     * @param categories
     * @param exceptCategoryId
     * @return categories
     */
    private List<IntroductionCategoryDto> removeExceptCategory(List<IntroductionCategoryDto> categories,
            Long exceptCategoryId) {
        for (int index = 0; index < categories.size(); ++index) {
            IntroductionCategoryDto iCategory = categories.get(index);
            if (iCategory.getId().equals(exceptCategoryId)) {
                categories.remove(index);
                break;
            }
        }
        return categories;
    }

    private void constructorTreeData(PageWrapper<IntroductionCategoryDto> pageWrapper,
            List<IntroductionCategoryDto> dtoList, IntroductionCategorySearchDto condition, int page, int sizeOfPage,
            String languageCode) {
        int categoryIndex = 0;

        int currentPage = pageWrapper.getCurrentPage();

        int startIndex = (currentPage - 1) * sizeOfPage;
        List<IntroductionCategoryDto> categoryList = introCateRepo.findCategoryByCondition(startIndex, sizeOfPage,
                condition, languageCode);
        categoryList = getParentLine(categoryList, languageCode);
        // find root
        List<IntroductionCategoryDto> roots = getRoots(categoryList);

        categoryList = sortMenuDtoByConstructorTree(categoryList, roots);

        int countRoot = introCateRepo.countRootActive();

        boolean startCount = true;
        for (IntroductionCategoryDto category : categoryList) {

            if (page > 1 && startCount) {
                categoryIndex++;
            }

            dtoList.add(category);
        }

        pageWrapper.setDataAndCount(dtoList, countRoot);
        if (page > 1) {
            pageWrapper.setStartIndexCurrent(categoryIndex);
        }
    }

    private List<IntroductionCategoryDto> getParentLine(List<IntroductionCategoryDto> listChild, String lang) {
        List<Long> menuIds = new ArrayList<>();
        List<IntroductionCategoryDto> listParentLine = new ArrayList<>();
        Locale locale = new Locale(lang);
        for (IntroductionCategoryDto child : listChild) {
            List<IntroductionCategoryDto> menuIdLine = getParentLineId(menuIds, child.getId(), lang, locale);
            listParentLine.addAll(menuIdLine);
        }
        return listParentLine;
    }

    private List<IntroductionCategoryDto> getParentLineId(List<Long> menuIds, Long childId, String lang,
            Locale locale) {
        Long menuId = childId;
        IntroductionCategoryDto menu;
        List<IntroductionCategoryDto> str = new ArrayList<>();
        do {
            menu = introCateRepo.findDetailById(menuId, lang);
            if (menu != null && !menuIds.contains(menu.getId())) {
                menuIds.add(menuId);
                str.add(menu);

            }

            if (menu == null) {
                break;
            }

            menuId = menu.getParentId();

        } while (menu.getParentId() != null && !menu.getParentId().equals(0L));
        return str;
    }

    private void setPhysicalVideo(IntroductionCategoryDto updateDto, IntroductionCategory entity) throws IOException {
        // physical file template
        String physicalVideoTmpName = updateDto.getBannerPhysicalVideo();
        // upload video
        if (StringUtils.isNotEmpty(physicalVideoTmpName)) {
            String newPhiscalName = CmsUtils.moveTempToUploadFolder(physicalVideoTmpName,
                    AdminConstant.INTRODUCTION_CATEGORY_FOLDER);
            entity.setBannerPhysicalVideo(newPhiscalName);
            entity.setBannerVideo(updateDto.getBannerVideo());
        } else {
            entity.setBannerPhysicalVideo(null);
            entity.setBannerVideo(null);
        }
    }

    /**
     * @param updateDto
     * @param entity
     * @throws IOException
     */
    private void moveTmpImage(IntroductionCategoryDto updateDto, IntroductionCategory entity) throws IOException {
        String physicalImgTmpName = updateDto.getImagePhysicalName();
        // upload images
        if (StringUtils.isNotEmpty(physicalImgTmpName)) {
            String newPhiscalName = CmsUtils.moveTempToUploadFolder(physicalImgTmpName,
                    AdminConstant.INTRODUCTION_CATEGORY_FOLDER);
            entity.setImageUrl(newPhiscalName);
            entity.setImageName(updateDto.getImageName());
        }
    }

    private IntroductionCategorySearchDto genCategorySearchCondition(CommonSearchDto searchDto) {
        IntroductionCategorySearchDto searchCondition = new IntroductionCategorySearchDto();
        if (searchDto.getCode() != null && !searchDto.getCode().equals("")) {
            searchCondition.setCode(searchDto.getCode());
        }
        if (searchDto.getName() != null && !searchDto.getName().equals("")) {
            searchCondition.setName(searchDto.getName());
        }

        if (searchDto.getStatus() != null && !searchDto.getStatus().equals("")) {
            searchCondition.setStatus(Integer.valueOf(searchDto.getStatus()));
        }

        if (searchDto.getEnabled() != null) {
            searchCondition.setEnabled(searchDto.getEnabled());
        }

        if (searchDto.getTypeOfMain() != null) {
            searchCondition.setTypeOfMain(searchDto.getTypeOfMain());
        }

        if (searchDto.getPictureIntroduction() != null) {
            searchCondition.setPictureIntroduction(searchDto.getPictureIntroduction());
        }

        return searchCondition;
    }

    public List<IntroductionCategoryDto> sortCategoryDtoByConstructorTree(List<IntroductionCategoryDto> categoryList) {
        // Find roots
        List<IntroductionCategoryDto> result = getMenuRoot(categoryList);

        int i = 0;
        while (i < result.size()) {
            IntroductionCategoryDto categoryRootNodeDto = result.get(i);
            Long categoryId = categoryRootNodeDto.getId();

            List<IntroductionCategoryDto> categoryChildren = new ArrayList<IntroductionCategoryDto>();
            for (IntroductionCategoryDto categoryDto : categoryList) {
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

    private List<IntroductionCategoryDto> getMenuRoot(List<IntroductionCategoryDto> categoryList) {
        List<IntroductionCategoryDto> result = new ArrayList<IntroductionCategoryDto>();
        // Find roots
        for (int i = 0; i < categoryList.size(); i++) {
            IntroductionCategoryDto categoryDto = categoryList.get(i);
            if (null == categoryDto.getParentId() || categoryDto.getParentId().equals(0l)) {
                result.add(categoryDto);
            }
        }

        return result;
    }

    private void updateHistoryApprove(IntroductionCategoryDto editDto, Locale locale) {
        // TODO : etc
//        try {
//            // insert comment
//            HistoryApproveDto historyApproveDto = new HistoryApproveDto();
//            historyApproveDto.setApprover(UserProfileUtils.getFullName());
//            historyApproveDto.setComment(editDto.getComment());
//            historyApproveDto.setProcessId(editDto.getProcessId());
//            historyApproveDto.setProcessStep(editDto.getStatus().longValue());
//            historyApproveDto.setReferenceId(editDto.getId());
//            historyApproveDto.setReferenceType(ConstantHistoryApprove.APPROVE_INTRODUCTION_CATEGORY);
//            historyApproveDto.setActionId(editDto.getButtonId());
//            historyApproveDto.setOldStep(editDto.getOldStatus());
//            historyApproveDto.setAccountId(UserProfileUtils.getAccountId());
//            
//            historyApproveService.addHistoryApprove(historyApproveDto);
//        } catch (Exception e) {
//            logger.error("updateHistoryApprove: " + e.getMessage());
//            throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
//        }

    }

    private void sendMail(IntroductionCategoryDto editDto, HttpServletRequest request, Locale locale) {
        // TODO: implement send mail
//        try {
//            EmailCommonDto emailCommon = new EmailCommonDto();
//            emailCommon.setActionName(msg.getMessage("email.template.introduction.category", null, locale));
//            emailCommon.setButtonAction(editDto.getButtonAction());
//            emailCommon.setButtonId(editDto.getButtonId());
//            emailCommon.setComment(editDto.getComment());
//
//            // Nội dung
//            LinkedHashMap<String, String> content = new LinkedHashMap<>();
//            content.put("Mã", editDto.getCode());
//            content.put("Tên hiển thị", editDto.getInfoByLanguages().get(0).getLabel());
//            emailCommon.setContent(content);
//
//            emailCommon.setCurrItem(editDto.getCurrItem());
//
//            emailCommon.setId(editDto.getId());
//            emailCommon.setOldStatus(editDto.getOldStatus());
//            emailCommon.setProcessId(editDto.getProcessId());
//            emailCommon.setReferenceType(editDto.getReferenceType());
//            emailCommon.setStatus(editDto.getStatus());
//
//            // Subject của email
//            emailCommon.setSubject(msg.getMessage("subject.email.template.introduction.category", null, locale));
//
//            emailCommon.setUrl(CmsUtils.getBaseUrl(request) + "/introduction-category/edit?id="
//                    + editDto.getId());
//
//            emailUtil.sendMail(emailCommon, request, locale);
//        } catch (Exception e) {
//            throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
//        }
    }

    @Override
    public IntroductionCategory getIntroductionByType(Long customerId, Integer typeOfMain,
            Integer pictureIntroduction) {
        return introCateRepo.findIntroductionByType(customerId, typeOfMain, pictureIntroduction);
    }

    @Override
    public List<IntroductionLanguageDto> getListForSort(Long customerId, String language) {
        return introCateRepo.findListForSort(customerId, language);
    }

    @Transactional
    @Override
    public void updateSortAll(List<SortOrderDto> sortOderList) {
        for (SortOrderDto dto : sortOderList) {
            introCateRepo.updateSortAll(dto);
        }

        Long itemId = 0L;
        for (SortOrderDto dto : sortOderList) {
            IntroductionCategory item = introCateRepo.findOne(dto.getObjectId());
            item.setBeforeId(itemId);
            itemId = item.getId();
            introCateRepo.save(item);
        }
    }

    @Override
    public void setDataForSearchDto(CommonSearchDto searchDto, String codeSearch, String nameSearch,
            Integer statusSearch, Integer enabledSearch, Integer typeOfMainSearch, Integer pictureIntroductionSearch) {
        if (codeSearch != null) {
            searchDto.setCode(codeSearch);
        }
        if (nameSearch != null) {
            searchDto.setName(nameSearch);
        }
        if (statusSearch != null) {
            searchDto.setStatus(statusSearch.toString());
        }
        if (enabledSearch != null) {
            searchDto.setEnabled(enabledSearch);
        }
        if (typeOfMainSearch != null) {
            searchDto.setTypeOfMain(typeOfMainSearch);
        }
        if (pictureIntroductionSearch != null) {
            searchDto.setPictureIntroduction(pictureIntroductionSearch);
        }
    };

    @Override
    public void exportExcel(CommonSearchDto searchDto, HttpServletResponse res, Locale locale) {
        try {
            // set status name
            searchDto.setStatusName(msg.getMessage(StepStatusEnum.DRAFT.getStatusName(), null, locale));

            /* change template */
            String templateName = vn.com.unit.cms.admin.all.constant.CmsCommonConstant.TEMPLATE_INTRODUCTION_CATEGORY;
            String template = servletContext.getRealPath(CmsCommonConstant.REAL_PATH_TEMPLATE_EXCEL) + "/" + templateName
                    + CmsCommonConstant.TYPE_EXCEL;
            String datePattern = systemConfig.getConfig(SystemConfig.DATE_PATTERN);

            List<ExportIntroductionCategoryReportDto> lstData = introCateRepo.exportExcelWithCondition(searchDto);
            List<ItemColsExcelDto> cols = new ArrayList<>();
            // start fill data to workbook
            ImportExcelUtil.setListColumnExcel(ExportIntroductionCategoryExportEnum.class, cols);
            ExportExcelUtil<ExportIntroductionCategoryReportDto> exportExcel = new ExportExcelUtil<>();
            // do export
            exportExcel.exportExcelWithXSSFNonPass(template, locale, lstData, ExportIntroductionCategoryReportDto.class,
                    cols, datePattern, res, templateName);

        } catch (Exception e) {
            throw new BusinessException("");
        }
    }

    @Override
    public IntroductionCategoryDto getEdit(Long id, String customerAlias, Locale locale) {

        String businessCode = CmsCommonConstant.HDBANK_BUSINESS_INTRODUCTION;

        logger.error("NEED CHANGE BUSINESS CODE");
        // TODO remove "BUSINESS_BANNER"
        businessCode = "BUSINESS_BANNER"; // remove this line, only for test
        logger.error("NEED CHANGE BUSINESS CODE");

        return getCategory(id, locale, businessCode);
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    public DocumentAppRes detail(Long documentId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List getListProcessHistoryDocument(Long documentId) {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public DocumentAppRes initDocument(Long formId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EfoDocDto save(EfoDocDto efoDocDto) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DocumentSaveReq save(DocumentSaveReq saveDto) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DocumentSaveReq saveBusiness(DocumentSaveReq saveDto, EfoDocDto efoDocDto) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EfoDocDto action(EfoDocDto efoDocDto) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

//	@Override
//	public DocumentActionReq action(DocumentActionReq action) throws Exception {
//		return null;
//	}

//	@Override
//	public DocumentActionReq actionBusiness(DocumentActionReq action, EfoDocDto efoDocDto) throws Exception {
//		// documentActionReq.getProcessStatusCode();
//
//				IntroductionCategoryDto editDto = (IntroductionCategoryDto) action;
//
//				// user login
//				String usernameLogin = UserProfileUtils.getUserNameLogin();
//
//				// save or update banner
//				createOrEditIntroductionCategory(editDto, usernameLogin, new Locale("EN"), null);
//
//				// save or update BannerLanguage
////				createOrEditBannerLanguage(editDto, usernameLogin);
//
////				if (StringUtils.isNotBlank(editDto.getRequestToken())) {
////					moveEditorTempFile(editDto.getRequestToken());
////				}
//
//				return editDto;
//	}

    @Override
    public EfoDocDto getEfoDocDtoById(Long documentId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EfoDocDto setValueSaveDocument(DocumentSaveReq documentSaveReq) throws DetailException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EfoDocDto setValueActionDocument(DocumentActionReq documentActionReq) throws DetailException {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List getListSlaDocument(Long documentId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DocumentActionReq loadData(Long documentId) {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public JpmButtonWrapper handleButtonByBusiness(DocumentActionReq data, JpmButtonWrapper jpmButtons) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String setInputJsonForEmail(DocumentActionReq documentActionReq) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EfoDocDto actionNextStep(Long docId, String stepCode, String buttonText) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void createHistTask(Long docId, String buttonText, String note) {
        // TODO Auto-generated method stub

    }

//	@Override
//	public DocumentActionReq actionList(DocumentActionReq documentActionReq) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}

    @Override
    public DocumentActionReq getInfomationByDocId(DocumentActionReq documentActionReq) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public DocumentAppRes detailHistory(Long documentId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DocumentActionReq saveDataBussiness(DocumentActionReq action) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setParamBusiness(DocumentActionReq documentActionReq, EfoDocDto efoDocDto) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public DocumentActionReq actionSaveBusiness(DocumentActionReq documentActionReq, EfoDocDto efoDocDto)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void actionSlaBusiness(DocumentActionReq documentActionReq, EfoDocDto efoDocDto) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void sendMailProcess(DocumentActionReq abstractProcessDto, Integer nextStepNo, String nextStatusCode,
            Integer curStepNo, AccountDetailDto accountAction, HttpServletRequest httpServletRequest, Locale locale)
            throws Exception {
        super.sendMailProcess(abstractProcessDto, nextStepNo, nextStatusCode, curStepNo, accountAction,
                httpServletRequest, locale);
    }
}
