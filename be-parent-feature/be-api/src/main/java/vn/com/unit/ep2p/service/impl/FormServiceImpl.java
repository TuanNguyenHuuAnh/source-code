/*******************************************************************************
 * Class        ：FormServiceImpl
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：taitt
 * Change log   ：2020/12/23：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.utils.CommonBase64Util;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.enumdef.FormType;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.api.enumdef.APIStatus;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.constant.AppCoreConstant;
import vn.com.unit.ep2p.core.constant.AppCoreExceptionCodeConstant;
import vn.com.unit.ep2p.core.dto.EfoFormRegisterRes;
import vn.com.unit.ep2p.core.dto.EfoFormRegisterSvcUploadFileDto;
import vn.com.unit.ep2p.core.efo.dto.EfoFormDto;
import vn.com.unit.ep2p.core.efo.dto.EfoFormRegisterCreateDto;
import vn.com.unit.ep2p.core.efo.dto.EfoFormRegisterDto;
import vn.com.unit.ep2p.core.efo.dto.EfoFormRegisterSearchDto;
import vn.com.unit.ep2p.core.efo.dto.EfoFormSearchDto;
import vn.com.unit.ep2p.core.efo.entity.EfoComponent;
import vn.com.unit.ep2p.core.efo.entity.EfoForm;
import vn.com.unit.ep2p.core.efo.service.EfoCategoryService;
import vn.com.unit.ep2p.core.efo.service.EfoComponentService;
import vn.com.unit.ep2p.core.efo.service.EfoFormRegisterSvcService;
import vn.com.unit.ep2p.core.efo.service.EfoFormService;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.req.FormRegisterReq;
import vn.com.unit.ep2p.dto.req.FormUpdateReq;
import vn.com.unit.ep2p.dto.res.CompanyInfoRes;
import vn.com.unit.ep2p.dto.res.FormInfoRes;
import vn.com.unit.ep2p.dto.res.FormRegisterResultRes;
import vn.com.unit.ep2p.service.FormService;
import vn.com.unit.workflow.dto.JpmBusinessDto;
import vn.com.unit.workflow.service.JpmBusinessService;

/**
 * FormServiceImpl.
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class FormServiceImpl extends AbstractCommonService implements FormService {

    /** The efo form service. */
    @Autowired
    private EfoFormService efoFormService;

    /** The jpm business service. */
    @Autowired
    private JpmBusinessService jpmBusinessService;
    
    /** The efo category service. */
    @Autowired
    private EfoCategoryService  efoCategoryService;
    
    @Autowired
    private EfoFormRegisterSvcService registerSvc;
    
    /** The message source. */
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private EfoComponentService efoComponentService;

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.BaseRestService#search(org.springframework.util.MultiValueMap, org.springframework.data.domain.Pageable)
     */
    @Override
    public ObjectDataRes<EfoFormDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException {
        ObjectDataRes<EfoFormDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, EfoForm.class, EfoFormService.TABLE_ALIAS_EFO_FORM);
            /** init param search repository */
            EfoFormSearchDto reqSearch = this.buildEfoFormSearchDto(commonSearch);

            int totalData = this.countEfoFormByCondition(reqSearch);
            List<EfoFormDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = this.getEfoFormDtoByCondition(reqSearch, pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022901_APPAPI_FORM_LIST_ERROR);
        }
        return resObj;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.BaseRestService#save(java.lang.Object)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public EfoFormDto save(EfoFormDto objectDto) throws DetailException {
        try {
            EfoForm efoForm = efoFormService.saveEfoFormDto(objectDto);
            objectDto.setFormId(efoForm.getId());
        }catch (Exception e) {
            throw new DetailException(AppApiExceptionCodeConstant.E4022903_APPAPI_FORM_UPDATE_INFO_ERROR);
        }
        return objectDto;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.BaseRestService#delete(java.lang.Long)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws DetailException {
        EfoForm efoForm = efoFormService.getFormById(id);
        if (null != efoForm) {
            try {
                // hardcode
                efoForm.setDeletedId(1L);
                efoForm.setDeletedDate(commonService.getSystemDate());
                efoFormService.saveEfoForm(efoForm);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022905_APPAPI_FORM_DELETE_ERROR);
            }
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E4022904_APPAPI_FORM_NOT_FOUND);
        }
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.BaseRestService#detail(java.lang.Long)
     */
    @Override
    public EfoFormDto detail(Long id) throws DetailException {
        EfoFormDto efoFormDto = efoFormService.getEfoFormDtoById(id);
        if (null == efoFormDto) {
            throw new DetailException(AppApiExceptionCodeConstant.E4022904_APPAPI_FORM_NOT_FOUND);
        }
        return efoFormDto;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.FormService#getEfoFormDtoByCondition(vn.com.unit.core.dto.EfoFormSearchDto, org.springframework.data.domain.Pageable)
     */
    @Override
    public List<EfoFormDto> getEfoFormDtoByCondition(EfoFormSearchDto efoFormSearchDto, Pageable pagable) {
        return efoFormService.getEfoFormDtoByCondition(efoFormSearchDto, pagable);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.FormService#countEfoFormByCondition(vn.com.unit.core.dto.EfoFormSearchDto)
     */
    @Override
    public int countEfoFormByCondition(EfoFormSearchDto efoFormSearchDto) {
        return efoFormService.countEfoFormByCondition(efoFormSearchDto);
    }

    /**
     * <p>
     * Builds the efo form search dto.
     * </p>
     *
     * @param commonSearch
     *            type {@link MultiValueMap<String,String>}
     * @return {@link EfoFormSearchDto}
     * @author taitt
     */
    private EfoFormSearchDto buildEfoFormSearchDto(MultiValueMap<String, String> commonSearch) {
        EfoFormSearchDto reqSearch = new EfoFormSearchDto();

        String keySearch = null != commonSearch.getFirst("keySearch") ? commonSearch.getFirst("keySearch") : AppApiConstant.EMPTY;
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        Long categoryId = null != commonSearch.getFirst("categoryId") ? Long.valueOf(commonSearch.getFirst("categoryId")) : null;
        Long formId = null != commonSearch.getFirst("formId") ? Long.valueOf(commonSearch.getFirst("formId")) : null;

        reqSearch.setCompanyId(companyId);
        reqSearch.setCategoryId(categoryId);
        reqSearch.setFormName(keySearch);
        reqSearch.setFormId(formId);
        return reqSearch;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.FormService#getFormInfoResById(java.lang.Long)
     */
    @Override
    public FormInfoRes getFormInfoResById(Long id) throws Exception {
        FormInfoRes formInfoRes = new FormInfoRes();

        try {
            EfoFormDto efoFormDto = this.detail(id);

            /** convert value */
//            formInfoRes.setFormTypeName(FormType.resolveByValueMapping(efoFormDto.getFormType()).toString());
            formInfoRes.setCreatedDateStr(null != efoFormDto.getCreatedDate()
                    ? CommonDateUtil.formatDateToString(efoFormDto.getCreatedDate(), AppCoreConstant.YYYYMMDD_TIME)
                    : null);
            formInfoRes.setCategoryName(efoFormDto.getCategoryName());
            formInfoRes.setBusinessName(efoFormDto.getBusinessName());
//            formInfoRes.setOwner(efoFormDto.getCreatedName());

            /** set value */
            formInfoRes.setActived(efoFormDto.getActived());
//            formInfoRes.setBusinessCode(efoFormDto.getBusinessCode());
            formInfoRes.setDescription(efoFormDto.getDescription());
            formInfoRes.setDeviceType(efoFormDto.getDeviceType());
            formInfoRes.setDisplayOrder(efoFormDto.getDisplayOrder());
            formInfoRes.setIconFilePath(efoFormDto.getIconFilePath());
            formInfoRes.setIconRepoId(efoFormDto.getIconRepoId());
            formInfoRes.setOzAppendFilePath(efoFormDto.getOzAppendFilePath());
            formInfoRes.setBusinessId(efoFormDto.getBusinessId());
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022906_APPAPI_FORM_INFO_ERROR);
        }
        return formInfoRes;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.FormService#update(vn.com.unit.mbal.api.req.dto.FormUpdateReq)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FormUpdateReq formUpdateReq) throws Exception {
        Long formId = formUpdateReq.getFormId();
        try {
            EfoFormDto efoForm = efoFormService.getEfoFormDtoById(formId);
            if (null != efoForm) {
                this.validUpdateBusinessforForm(formUpdateReq.getBusinessId());
                this.validUpdateCategoryforForm(formUpdateReq.getCategoryId());
                
                efoForm.setFormName(formUpdateReq.getFormName());
                efoForm.setDeviceType(formUpdateReq.getDeviceType());
                efoForm.setCategoryId(formUpdateReq.getCategoryId());
                efoForm.setBusinessId(formUpdateReq.getBusinessId());
                efoForm.setDisplayOrder(formUpdateReq.getDisplayOrder());
                efoForm.setDescription(formUpdateReq.getDescription());
                
                this.save(efoForm);
            } else {
                throw new DetailException(AppApiExceptionCodeConstant.E4022904_APPAPI_FORM_NOT_FOUND);
            }
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022901_APPAPI_FORM_LIST_ERROR);
        }

    }

    /**
     * <p>
     * Valid update categoryfor form.
     * </p>
     *
     * @param categoryId
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     * @author taitt
     */
    private void validUpdateCategoryforForm(Long categoryId) throws DetailException {
        if (null == efoCategoryService.getCategoryById(categoryId)) {
            throw new DetailException(AppApiExceptionCodeConstant.E4022706_APPAPI_CATEGORY_NOT_FOUND);
        }
    }

    /**
     * <p>
     * Valid update businessfor form.
     * </p>
     *
     * @param businessId
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     * @author taitt
     */
    private void validUpdateBusinessforForm(Long businessId) throws DetailException {
        if (null == jpmBusinessService.getJpmBusinessDtoById(businessId)) {
            throw new DetailException(AppApiExceptionCodeConstant.E4022904_APPAPI_FORM_NOT_FOUND);
        }
    }
    
    @Override
    public EfoFormRegisterRes getFormRegisterDtoByCondition(MultiValueMap<String, String> commonSearch) throws Exception {
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        Long categoryId = null != commonSearch.getFirst("categoryId") ? Long.valueOf(commonSearch.getFirst("categoryId")) : null;

        if (null == companyId) {
        	throw new DetailException(AppApiExceptionCodeConstant.E4022904_APPAPI_FORM_NOT_FOUND);
        }
        
        CompanyInfoRes company = new CompanyInfoRes();
        
        if (null == company) {
        	throw new DetailException(AppApiExceptionCodeConstant.E4021203_APPAPI_COMPANY_NOT_FOUND);
        }
        
        EfoFormRegisterSearchDto searchDto = new EfoFormRegisterSearchDto();
        searchDto.setCategoryId(categoryId);
        searchDto.setCompanyId(companyId);
        searchDto.setCompanyName(company.getSystemCode());
        
        // STEP 1: Call Api OZREPO get LIST OZR
        EfoFormRegisterRes reportList = registerSvc.getEfoFormRegisterDtoListOZRepository(searchDto);
        
        // STEP 2: IF SUCCESS and not register then add item list
        if (reportList != null && APIStatus.SUCCESS.toString().equalsIgnoreCase(reportList.getStatus())) {
            List<EfoFormRegisterDto> newRegisterList = new ArrayList<>();
            List<EfoFormRegisterDto> registerList = reportList.getResultObj();
            List<String> fileNameList = registerList.stream().map(x -> x.getReportPath().toUpperCase()).collect(Collectors.toList());

            // Get list form from database
            List<EfoFormDto> formList = efoFormService.getEfoFormDtoByCompanyIdAndFileName(companyId, fileNameList);
            EfoFormDto resultDto;
            if (null == formList || formList.isEmpty()) {
                newRegisterList = registerList;
            } else {
                for (EfoFormRegisterDto registerSvcDto : registerList) {
                    // Search in formList to check exists
                    resultDto = formList.stream()
                            .filter(predicate -> predicate.getOzFilePath().equalsIgnoreCase(registerSvcDto.getReportPath())).findAny()
                            .orElse(null);
                    if (null == resultDto) {
                        newRegisterList.add(registerSvcDto);
                    }
                }
            }
            // Set new services which do not register
            reportList.setResultObj(newRegisterList);
        }
        return reportList;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public FormRegisterResultRes registerForm(FormRegisterReq formRegisterReq) throws Exception {
        FormRegisterResultRes registerResultRes = new FormRegisterResultRes();
        
        Long companyId = formRegisterReq.getCompanyId();
        String efoFormName = formRegisterReq.getFormName();
        String description = formRegisterReq.getDescription();
        Long businessId = formRegisterReq.getBusinessId();
        String imageString = CommonBase64Util.removeBase64Header(formRegisterReq.getImageOzr());
        byte[] base64Image = CommonBase64Util.decodeToByte(imageString);
        
        EfoFormDto efoFormDto = efoFormService.getEfoFormDtoByCompanyIdAndEfoFormName(companyId, efoFormName, null);
        if (null != efoFormDto) {
            registerResultRes.setStatus(CommonConstant.RESULT_STATUS_FAIL);
            registerResultRes.setMessage(this.messageSource.getMessage(AppCoreExceptionCodeConstant.E401801_CORE_REGISTER_FORM_EXISTS, new String[] { efoFormName},UserProfileUtils.getLocaleRequest()));
            return registerResultRes;
        }
        
        JpmBusinessDto jpmBusinessDto = jpmBusinessService.getJpmBusinessDtoById(businessId);
        if (null == jpmBusinessDto) {
            registerResultRes.setStatus(CommonConstant.RESULT_STATUS_FAIL);
            registerResultRes.setMessage(this.messageSource.getMessage(AppCoreExceptionCodeConstant.E401810_CORE_REGISTER_FORM_BUSINESS_NOT_FOUNT, new String[] { efoFormName},UserProfileUtils.getLocaleRequest()));
            return registerResultRes;
        }
        
        // Create bussiness code
        String businessCode = CommonStringUtil.removeAccent(efoFormName).replace(" ", "");
        
        //TODO UPLOAD OZR
        EfoFormRegisterSvcUploadFileDto resultUploadFileDto = registerSvc.uploadFileImageFormRegister(base64Image, companyId, businessCode, efoFormName);
        String filePath = resultUploadFileDto.getFilePath();
        Long repositoryId = resultUploadFileDto.getRepositoryId();
        
        // Check service function
        // Get message description
        String descriptionAutoGen = this.messageSource.getMessage(AppCoreExceptionCodeConstant.E401803_CORE_REGISTER_FORM_AUTO_GEN_DESCRIPTION, new String[] { formRegisterReq.getFormName() },
                UserProfileUtils.getLocaleRequest());  
//                systemConfig.getConfig(SystemConfig.LANGUAGE_DEFAULT));
        if (CommonStringUtil.isBlank(description)) {
            description = descriptionAutoGen;
        }
        
//        if (CommonStringUtil.isBlank(functionCode)) {
//            // Create item
//            String subType = "LINK";
//            JcaItem item = efoFormService.createItem(businessCode, efoFormName, CommonStringUtil.EMPTY, businessId, description,
//                    companyId, subType);
//            functionCode = item.getFunctionCode();
//
//            // Create item field permission
//            subType = "FIELD_PERM";
//            efoFormService.createItem("FPM#".concat(businessCode), efoFormName, " (Field permission)", businessId, description,
//                    companyId, subType);
//        }
        
        // Save to form table
        EfoFormRegisterCreateDto register = this.getEfoFormRegisterCreateDtoByFormRegisterReq(formRegisterReq);
        EfoForm service = this.saveEfoFormRegister(register, filePath, repositoryId, businessCode, businessId);

        if(FormType.FIXED_FORM_TYPE.toString().equals(service.getFormType())) {
            // Call API get Component
            List<EfoComponent> componentList = registerSvc.getComponentListfromOZRepository(service.getId(), service.getName(), service.getOzFilePath(), null, register.getCompanyId());
            // Save component
            efoComponentService.saveEfoComponentList(componentList, service.getName());
        }
        // Return result
        registerResultRes.setStatus(CommonConstant.RESULT_STATUS_SUCCESS);
        registerResultRes.setMessage(this.messageSource.getMessage(AppCoreExceptionCodeConstant.E401808_CORE_REGISTER_FORM_SUCCESS, new String[] { formRegisterReq.getFormName()},UserProfileUtils.getLocaleRequest()));
        return registerResultRes;
    }
    
    private EfoFormRegisterCreateDto getEfoFormRegisterCreateDtoByFormRegisterReq(FormRegisterReq formRegisterReq) {        
        return objectMapper.convertValue(formRegisterReq, EfoFormRegisterCreateDto.class);
    }
    
    
    @Transactional(rollbackFor = Exception.class)
    EfoForm saveEfoFormRegister(EfoFormRegisterCreateDto register, String filePath, Long repositoryId, String businessCode, Long businessId) throws DetailException {
        try {
            Long displayOrder = efoFormService.findMaxDisplayOrderByCompanyId(register.getCompanyId()) + 1;
            EfoForm service = new EfoForm();
            //formType
            String formType = register.getFormType();
            service.setFormType(formType);
            
            service.setCompanyId(register.getCompanyId());
            service.setCategoryId(register.getCategoryId());
            service.setBusinessId(businessId);
            service.setName(register.getFormName());
            service.setDescription(register.getReportDesc());
            service.setOzFilePath(register.getReportPath());
            service.setIconFilePath(filePath);
            service.setIconRepoId(repositoryId);
            service.setDisplayOrder(displayOrder);
            service.setDeviceType(register.getDeviceType());
            service.setActived(true);
            efoFormService.saveEfoForm(service);         
            return service;
        } catch (Exception e) {
            throw new DetailException(AppApiExceptionCodeConstant.E4022902_APPAPI_FORM_ADD_ERROR, new String[] { register.getFormName() },true);
        }
    }
    
}
