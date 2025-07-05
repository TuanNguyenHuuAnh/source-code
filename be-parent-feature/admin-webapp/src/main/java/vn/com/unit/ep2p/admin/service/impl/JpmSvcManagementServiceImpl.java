/*******************************************************************************
 * Class        :SvcManagementServiceImpl
 * Created date :2019/04/21
 * Lasted date  :2019/04/21
 * Author       :HungHT
 * Change log   :2019/04/21:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.exception.AppException;
import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.enumdef.FormType;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.AppLanguageService;
import vn.com.unit.dts.api.enumdef.APIStatus;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.ConstantDisplayDto;
import vn.com.unit.ep2p.admin.dto.ResReportList;
import vn.com.unit.ep2p.admin.dto.ResultDto;
import vn.com.unit.ep2p.admin.dto.SvcManagementDto;
import vn.com.unit.ep2p.admin.dto.SvcManagementSearchDto;
import vn.com.unit.ep2p.admin.enumdef.ResultStatus;
import vn.com.unit.ep2p.admin.repository.ComponentRepository;
import vn.com.unit.ep2p.admin.repository.FormLangRepository;
import vn.com.unit.ep2p.admin.repository.ItemRepository;
import vn.com.unit.ep2p.admin.repository.SvcManagementRepository;
import vn.com.unit.ep2p.admin.service.AbstractCommonService;
import vn.com.unit.ep2p.admin.service.CategoryService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.ConstantDisplayService;
import vn.com.unit.ep2p.admin.service.ItemManagementService;
import vn.com.unit.ep2p.admin.service.JpmRegisterSvcService;
import vn.com.unit.ep2p.admin.service.JpmSvcManagementService;
//import vn.com.unit.ep2p.constant.AppDirectoryConstant;
import vn.com.unit.ep2p.core.efo.dto.EfoFormDto;
import vn.com.unit.ep2p.core.efo.dto.EfoFormSearchDto;
import vn.com.unit.ep2p.core.efo.entity.EfoForm;
import vn.com.unit.ep2p.core.efo.entity.EfoFormLang;
import vn.com.unit.ep2p.core.efo.repository.EfoFormRepository;
import vn.com.unit.ep2p.core.efo.service.EfoFormService;
import vn.com.unit.ep2p.core.efo.service.impl.EfoFormServiceImpl;
import vn.com.unit.ep2p.dto.CompanyDto;
import vn.com.unit.ep2p.dto.PPLRegisterSvcDto;
import vn.com.unit.ep2p.dto.PPLRegisterSvcSearchDto;
import vn.com.unit.ep2p.enumdef.SvcSearchEnum;

/**
 * SvcManagementServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Service
@Primary
@Transactional(rollbackFor = Exception.class)
public class JpmSvcManagementServiceImpl extends EfoFormServiceImpl
        implements JpmSvcManagementService, AbstractCommonService {

//    private static final Logger logger = LoggerFactory.getLogger(JpmSvcManagementServiceImpl.class);

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    SvcManagementRepository svcManagementRepository;

    @Autowired
    ComponentRepository componentRepository;

    @Autowired
    MessageSource msg;

    @Autowired
    JpmRegisterSvcService registerSvcService;

    @Autowired
    CompanyService companyService;

    @Autowired
    private JCommonService comService;

    @Autowired
    ItemManagementService itemManagementService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ConstantDisplayService constantDisplayService;

    @Autowired
    AppLanguageService languageService;

    @Autowired
    FormLangRepository formLangRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    private EfoFormRepository efoFormRepository;

    // Model mapper
    ModelMapper modelMapper = new ModelMapper();

    /**
     * getSvcManagementList
     * 
     * @param search
     * @param pageSize
     * @param page
     * @return
     * @author HungHT
     * @throws DetailException
     */
    public PageWrapper<SvcManagementDto> getSvcManagementList(SvcManagementSearchDto search, int pageSize, int page,
            String lang) throws DetailException {
        List<Integer> listPageSize = systemConfig.getListPageSize();
        int sizeOfPage = systemConfig.getSizeOfPage(listPageSize, pageSize);
        PageWrapper<SvcManagementDto> pageWrapper = new PageWrapper<>(page, sizeOfPage);
        pageWrapper.setListPageSize(listPageSize);
        pageWrapper.setSizeOfPage(sizeOfPage);

        Pageable pageableAfterBuild = this.buildPageable(PageRequest.of(page - 1, sizeOfPage), EfoForm.class,
                EfoFormService.TABLE_ALIAS_EFO_FORM);
        setSearchParm(search);

        EfoFormSearchDto reqSearch = this.buildEfoFormSearchDto(search);

        // Set param search

        int count = this.countEfoFormByCondition(reqSearch);
        List<SvcManagementDto> result = new ArrayList<>();
        if (count > 0) {
            // int currentPage = pageWrapper.getCurrentPage();
            // int startIndex = (currentPage - 1) * sizeOfPage;
            List<EfoFormDto> formDtos = this.getEfoFormDtoByCondition(reqSearch, pageableAfterBuild);
            result = formDtos.stream().map(this::convertEfoFormDtoToSvcManagementDto).collect(Collectors.toList());
        }
        pageWrapper.setDataAndCount(result, count);
        return pageWrapper;
    }

    private SvcManagementDto convertEfoFormDtoToSvcManagementDto(EfoFormDto efoFormDto) {
        if (efoFormDto == null)
            return null;
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.addMappings(new PropertyMap<SvcManagementDto, EfoFormDto>() {

            @Override
            protected void configure() {
                skip(destination.getActived());
            }
        });
        SvcManagementDto svcManagementDto = modelMapper.map(efoFormDto, SvcManagementDto.class);
        svcManagementDto.setActived("1".equalsIgnoreCase(efoFormDto.getActived()));

        return svcManagementDto;
    }

    private EfoFormSearchDto buildEfoFormSearchDto(SvcManagementSearchDto search) {
        EfoFormSearchDto reqSearch = new EfoFormSearchDto();

//        String keySearch = search.getFieldSearch();
        Long companyId = search.getCompanyId();
        Long categoryId = search.getCategoryId();
        String formName = search.getName();
        Long formId = null;
        String formType = search.getFormType();
        String description = search.getDescription();
        reqSearch.setCompanyId(companyId);
        reqSearch.setCategoryId(categoryId);
        reqSearch.setFormName(formName);
        reqSearch.setFormId(formId);
        reqSearch.setFormType(formType);
        reqSearch.setDescription(description);
        reqSearch.setFileName(search.getFileName());
//        for (String field : search.getFieldValues()) {
//            if (StringUtils.equals(field, SvcSearchEnum.NAME.name())) {
//                reqSearch.setFormName(search.getFieldSearch().trim());
//                continue;
//            }
//        }

        reqSearch.setCompanyIdList(UserProfileUtils.getCompanyIdList());
        return reqSearch;
    }

    /**
     * setSearchParm
     * 
     * @param search
     * @author HungHT
     */
    private void setSearchParm(SvcManagementSearchDto search) {
        if (null == search.getFieldValues()) {
            search.setFieldValues(new ArrayList<String>());
        }

        if (search.getFieldValues().isEmpty()) {
            search.setName(search.getFieldSearch() != null ? search.getFieldSearch().trim() : search.getFieldSearch());
            search.setDescription(
                    search.getFieldSearch() != null ? search.getFieldSearch().trim() : search.getFieldSearch());
            search.setFileName(
                    search.getFieldSearch() != null ? search.getFieldSearch().trim() : search.getFieldSearch());
        } else {
            for (String field : search.getFieldValues()) {
                if (StringUtils.equals(field, SvcSearchEnum.NAME.name())) {
                    search.setName(search.getFieldSearch().trim());
                    continue;
                }
                if (StringUtils.equals(field, SvcSearchEnum.DESCRIPTION.name())) {
                    search.setDescription(search.getFieldSearch().trim());
                    continue;
                }
                if (StringUtils.equals(field, SvcSearchEnum.FORM_FILE.name())) {
                    search.setFileName(search.getFieldSearch().trim());
                    continue;
                }
            }
        }
    }

    /**
     * findById
     * 
     * @param id
     * @return
     * @author HungHT
     */
    public SvcManagementDto findById(Long id, String lang) {
        SvcManagementDto resDto = svcManagementRepository.findJpmSvcById(id, lang);
        List<EfoFormLang> formLangs = formLangRepository.findByFormId(id);

        if (CollectionUtils.isEmpty(formLangs)) {
            List<LanguageDto> languageList = languageService.getLanguageDtoList();
            formLangs = languageList.stream().map(l -> l.getCode()).sorted()
                    .collect(Collectors.mapping(langCode -> new EfoFormLang(langCode), Collectors.toList()));
        }

        resDto.setFormLangs(formLangs);
        return resDto;
    }

    /**
     * findByProperties1
     * 
     * @param properties1
     * @return
     * @author HungHT
     */
    public SvcManagementDto findByProperties1(String properties1) {
        return svcManagementRepository.findByProperties1(properties1);
    }

    /**
     * initScreenDetail
     * 
     * @param mav
     * @param objectDto
     * @param locale
     * @author HungHT
     */
    public void initScreenDetail(ModelAndView mav, SvcManagementDto objectDto, Locale locale) throws AppException {
        mav.addObject("listForm", getOZFormFileName(objectDto));

        // EfoForm type list
//        List<ConstantDisplayDto> formTypeList = constantDisplayService
//                .findConstantDisplayByTypeAndLang(ConstantDisplayType.FORM_TYPE.toString(), locale);
//        mav.addObject("formTypeList", formTypeList);
        List<ConstantDisplayDto> formTypeList = new ArrayList<>();
        for (FormType formType : FormType.values()) {
            ConstantDisplayDto constantDisplayDto = new ConstantDisplayDto(0,
                    String.valueOf(formType.getValueMapping()), String.valueOf(formType.getValueMapping()));
            constantDisplayDto.setCatOfficialNameVi(formType.getValueMapping());
            formTypeList.add(constantDisplayDto);
        }
        mav.addObject("formTypeList", formTypeList);
        // mav.addObject(PROCESS_TYPE_LIST, formType);
//        List<EfoCategoryDto> categoryList=categoryService.getCategoryList(search, pageSize, page, langCode);
        // lang list
        List<LanguageDto> languageList = languageService.getLanguageDtoList();
        mav.addObject("languageList", languageList);
        // formLangs
        List<EfoFormLang> formLangs = new ArrayList<>();
        List<EfoFormLang> formLangsTmp = objectDto.getFormLangs();
        languageList.forEach(f -> {
            EfoFormLang formLang = formLangsTmp.stream().filter(lt -> f.getCode().equals(lt.getLangCode())).findFirst()
                    .orElse(new EfoFormLang(f.getCode()));
            formLangs.add(formLang);
        });
        objectDto.setFormLangs(formLangs);

        // mutil recruiting
//        if (StringUtils.isNotBlank(objectDto.getFormType()) && ConstantCore.FIXED_FORM_TYPE.equals(objectDto.getFormType())) {
//            List<Select2Dto> mutilRecruitingTypes = MultiRecruitingTypeEnum.toListSelect2Dto();
//            mav.addObject("mutilRecruitingTypes", mutilRecruitingTypes);
//        }
    }

    /**
     * saveSvcManagement
     * 
     * @param objectDto
     * @param locale
     * @return
     * @author HungHT
     */
    public ResultDto saveSvcManagement(SvcManagementDto objectDto, Locale locale) throws Exception {
        ResultDto result = new ResultDto();

        // Check service name
//        EfoFormDto form = FormRepository.findByCompanyIdAndName(objectDto.getCompanyId(), objectDto.getFormName(), objectDto.getFormId());
        EfoFormDto form = efoFormRepository.getEfoFormDtoByCompanyIdAndEfoFormName(objectDto.getCompanyId(),
                objectDto.getFormName(), objectDto.getFormId());
        if (null != form) {
            result.setStatus(ResultStatus.FAIL.toInt());
            result.setMessage(msg.getMessage(ConstantCore.MSG_ERROR_SERVICE_EXISTS,
                    new String[] { objectDto.getFormName() }, locale));
            return result;
        }
//
//        // Create bussiness code
//        String serviceName = CommonStringUtil.removeAccent(objectDto.getFormName()).replace(" ", "");
//        // Save file
        MultipartFile file = objectDto.getFile();
        String filePath = null;
//        Long repositoryId = null;
        if (null != file) {
            CompanyDto company = companyService.findById(objectDto.getCompanyId());
            if (null == company) {
                throw new AppException("B201", null);
            }
//            String subFilePath = AppDirectoryConstant.SERVICE_IMAGES.concat(company.getSystemCode());
//            FileUploadResultDto repoResultDto = repositoryService.uploadFileBySettingKey(file, serviceName, AppSystemConfig.REPO_UPLOADED_MAIN, 2,
//                    null, subFilePath, objectDto.getCompanyId(), locale);
//            if (repoResultDto.isStatus()) {
//                filePath = repoResultDto.getFilePath();
//                repositoryId = repoResultDto.getRepositoryId();
//            } else {
//                throw new AppException(repoResultDto.getMessage(), repoResultDto.getMessageCode(), repoResultDto.getArgs());
//            }
        }
//
//        // Check service function
//        String functionCode = null;
//        if (StringUtils.isBlank(objectDto.getFunctionCode())) {
//            String businessCode = objectDto.getBusinessName();
//            Long businessId = objectDto.getBusinessId();
//            // Get message description
//            String description = ExecMessage.getMessage(msg, "B210", new String[] { objectDto.getDescription() },
//                    systemConfig.getConfig(AppSystemConfig.LANGUAGE_DEFAULT));
//            // Create item
//            String subType = "LINK";
//            JcaItem item = createItem(businessCode, objectDto.getBusinessName(), StringUtils.EMPTY, businessId, description,
//                    objectDto.getCompanyId(), subType);
//            functionCode = item.getFunctionCode();
//
//            // Create item field permission
//            subType = "FIELD_PERM";
//            createItem("FPM#".concat(businessCode), objectDto.getBusinessName(), " (Field permission)", businessId, description,
//                    objectDto.getCompanyId(), subType);
//        } else {
//            functionCode = objectDto.getFunctionCode();
//        }
//
        @SuppressWarnings("unused")
		boolean isChangeFormFileName = false;
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long user = UserProfileUtils.getAccountId();
        EfoForm objectCurrent = efoFormRepository.findOne(objectDto.getFormId());
        objectCurrent.setCategoryId(objectDto.getCategoryId());
        objectCurrent.setName(objectDto.getFormName());
        objectCurrent.setDescription(objectDto.getDescription());
        if (!objectCurrent.getOzFilePath().equalsIgnoreCase(objectDto.getOzFilePath())) {
            objectCurrent.setOzFilePath(objectDto.getOzFilePath());
            isChangeFormFileName = true;
        }
        if (StringUtils.isNotBlank(filePath)) {
            objectCurrent.setIconFilePath(filePath);
//            objectCurrent.setRepositoryId(repositoryId);
        }
        objectCurrent.setBusinessId(objectDto.getBusinessId());
//        objectCurrent.setFunctionCode(functionCode);
        objectCurrent.setDisplayOrder(objectDto.getDisplayOrder());
        objectCurrent.setActived(objectDto.isActived());
        objectCurrent.setDeviceType(objectDto.getDeviceType());
        objectCurrent.setUpdatedId(UserProfileUtils.getAccountId());
        objectCurrent.setUpdatedDate(sysDate);
        objectCurrent.setFormType(objectDto.getFormType());
//        objectCurrent.setMultiRecruiting(objectDto.getMultiRecruiting());
        if (null != objectCurrent.getId()) {
            efoFormRepository.update(objectCurrent);
        } else {
            efoFormRepository.create(objectCurrent);
        }
        // save component
//        if (isChangeFormFileName) {
//            // Delete component old
//            componentRepository.deleteByFormId(objectDto.getId(), user, sysDate);
//
//            // Call API get Component
//            List<Component> componentList = registerSvcService.getComponentListfromOZRepository(objectDto.getId(), objectDto.getName(),
//                    objectDto.getFormFileName(), null, objectDto.getCompanyId());
//
//            // Save component
//            registerSvcService.saveComponent(componentList, objectDto.getName());
//        }

        // Save Language
        List<EfoFormLang> formLangs = objectDto.getFormLangs();
        Long formId = objectDto.getFormId();
        formLangs.stream().filter(Objects::nonNull).forEach(l -> {
            if (null == l.getFormId()) {
                l.setCreatedId(user);
                l.setCreatedDate(sysDate);
                l.setFormId(formId);
                formLangRepository.create(l);
            } else {
                String name = l.getName();
                l = formLangRepository.findOneByPK(l.getFormId(), l.getLangId());
                l.setName(name);
                l.setUpdatedId(user);
                l.setUpdatedDate(sysDate);
                formLangRepository.update(l);

            }
        });

        // Return result
        result.setStatus(ResultStatus.SUCCESS.toInt());
        result.setMessage(msg.getMessage(ConstantCore.MSG_SUCCESS_SAVE, null, locale));
        return result;
    }

    /**
     * deleteSvcManagement
     * 
     * @param id
     * @return
     * @author HungHT
     */
    public boolean deleteSvcManagement(Long id) {
        boolean res = false;
        if (Objects.nonNull(id)) {
            Date sysDate = CommonDateUtil.getSystemDateTime();
            Long user = UserProfileUtils.getAccountId();
            EfoForm object = efoFormRepository.findOne(id);
            if (Objects.nonNull(object) && Long.valueOf(0L).equals(object.getDeletedId())) {
                object.setDeletedId(user);
                object.setDeletedDate(sysDate);
                object.setActived(false);

                efoFormRepository.update(object);
                res = true;
            }
        }
        return res;
    }

    /**
     * getBusinessList
     * 
     * @param keySearch
     * @param companyId
     * @param isPaging
     * @return
     * @author HungHT
     */
    public List<Select2Dto> getBusinessList(String keySearch, Long companyId, boolean isPaging) {
        return svcManagementRepository.getBusinessList(keySearch, companyId, isPaging);
    }

    /**
     * getOZFormFileName
     * 
     * @param currentFormFileName
     * @return
     * @author HungHT
     */
    public List<Select2Dto> getOZFormFileName(SvcManagementDto objectDto) throws AppException {
        String currentFormFileName = objectDto.getOzFilePath();
        List<Select2Dto> listResult = new ArrayList<>();
        Select2Dto obj = new Select2Dto();
        obj.setId(currentFormFileName);
        obj.setText(currentFormFileName);
        obj.setName(currentFormFileName);
        listResult.add(obj);
        String formType = objectDto.getFormType();
//        StringUtils.isNotBlank(formType)
        if (StringUtils.isNotBlank(formType) && !ConstantCore.FREE_FORM_TYPE.equals(formType)) {
            PPLRegisterSvcSearchDto search = new PPLRegisterSvcSearchDto();
            search.setCompanyName(companyService.getSystemCodeByCompanyId(UserProfileUtils.getCompanyId()));
            search.setCompanyId(UserProfileUtils.getCompanyId());
            Long companyId = objectDto.getCompanyId();
            search.setCompanyName(companyService.getSystemCodeByCompanyId(companyId));
            search.setCompanyId(companyId);
            ResReportList reportList = registerSvcService.getRegisterSvcList(search);
            if (reportList != null && APIStatus.SUCCESS.toString().equalsIgnoreCase(reportList.getStatus())
                    && reportList.getResultObj() != null && reportList.getResultObj().size() > 0) {
                for (PPLRegisterSvcDto item : reportList.getResultObj()) {
                    obj = new Select2Dto();
                    obj.setId(item.getReportPath());
                    obj.setText(item.getReportPath());
                    obj.setName(item.getReportPath());
                    listResult.add(obj);
                }
            }
        }
        return listResult;
    }

    // private JcaItem createItem(String itemCode, String itemName, String suffix,
    // Long businessId, String description, Long companyId,
    // String subType) throws AppException {
    // JcaItem item = null;
    // String system = companyService.getSystemCodeByCompanyId(companyId);
    // String code = system.concat("#").concat(itemCode);
    // String name = itemName.concat(suffix);
    // try {
    // int index = 0;
    // do {
    // index += 1;
    // if (index > 10) {
    // throw new Exception();
    // }
    // // Check exists item
    // Boolean isExists = formRepository.checkExistItem(code, companyId);
    // if (isExists == null || !isExists) {
    // item = new JcaItem();
    // item.setFunctionCode(code);
    // item.setFunctionName(name);
    // item.setFunctionType("3");
    // item.setCompanyId(UserProfileUtils.getCompanyId());
    // item.setDescription(description);
    //// item.setBusinessId(businessId);
    //// item.setSubType(subType);
    // item.setDisplayFlag(true);
    // item.setDisplayOrder(itemManagementService.getMaxDisplayOrder());
    // item.setCreatedId(UserProfileUtils.getAccountId());
    // item.setCreatedDate(CommonDateUtil.getSystemDateTime());
    // item.setCompanyId(companyId);
    // item = itemRepository.save(item);
    // break;
    // } else {
    // code = system.concat("#").concat(itemCode).concat(String.valueOf(index));
    // name = itemName.concat(String.valueOf(index)).concat(suffix);
    // }
    // } while (item == null);
    // } catch (Exception e) {
    // throw new AppException(e.getMessage(), e, "B204", new String[] { itemName });
    // }
    // return item;
    // }

    @Override
    public JCommonService getCommonService() {
        return comService;
    }
}