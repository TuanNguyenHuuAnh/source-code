/**
 * 
 */
package vn.com.unit.cms.admin.all.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.cms.admin.all.constant.AdminConstant;
import vn.com.unit.cms.admin.all.dto.CustomerTypeDto;
import vn.com.unit.cms.admin.all.dto.ServiceDetailDto;
import vn.com.unit.cms.admin.all.dto.ServiceDetailLanguageDto;
import vn.com.unit.cms.admin.all.dto.ServiceDto;
import vn.com.unit.cms.admin.all.dto.ServiceLanguageDto;
import vn.com.unit.cms.admin.all.dto.ServiceLanguageSearchDto;
import vn.com.unit.cms.admin.all.entity.CustomerTypeLanguage;
import vn.com.unit.cms.admin.all.entity.ServiceDetail;
import vn.com.unit.cms.admin.all.entity.ServiceLanguage;
import vn.com.unit.cms.admin.all.enumdef.ServiceSearchEnum;
import vn.com.unit.cms.admin.all.repository.CustomerTypeLanguageRepository;
import vn.com.unit.cms.admin.all.repository.CustomerTypeRepository;
import vn.com.unit.cms.admin.all.repository.ServiceDetailRepository;
import vn.com.unit.cms.admin.all.repository.ServiceLanguageRepository;
import vn.com.unit.cms.admin.all.repository.ServiceRepository;
import vn.com.unit.cms.admin.all.service.CmsFileService;
import vn.com.unit.cms.admin.all.service.ServiceService;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.entity.Language;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.core.service.LanguageService;
//import vn.com.unit.jcanary.utils.Utils;
import vn.com.unit.ep2p.core.utils.Utility;

/**
 * @author tungns <tungns@unit.com.vn>
 *
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    ServiceLanguageRepository serviceLanguageRepository;

    @Autowired
    ServiceDetailRepository serviceDetailRepository;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private CustomerTypeRepository customerTypeRepository;

    @Autowired
    private CustomerTypeLanguageRepository customerTypeLanguageRepository;

    @Autowired
    CmsFileService fileService;

    private void setSearchParam(ServiceLanguageSearchDto searchDto) {
        if (searchDto.getFieldValues() != null
                && searchDto.getFieldValues().size() > 0) {
            for (String item : searchDto.getFieldValues()) {
                if (item.equals(ServiceSearchEnum.CODE.name())) {
                    searchDto.setCode(searchDto.getFieldSearch().trim());
                }
                if (item.equals(ServiceSearchEnum.TITLE.name())) {
                    searchDto.setTitle(searchDto.getFieldSearch().trim());
                }
                if (item.equals(ServiceSearchEnum.DESC.name())) {
                    searchDto.setDescriptionAbv(
                            searchDto.getFieldSearch().trim());
                }
                if (item.equals(ServiceSearchEnum.CUST.name())) {
                    searchDto.setCustomerTypesSearch(
                            searchDto.getFieldSearch().trim());
                }
            }
        } else {
            searchDto.setCode(searchDto.getFieldSearch().trim());
            searchDto.setTitle(searchDto.getFieldSearch().trim());
            searchDto.setDescriptionAbv(searchDto.getFieldSearch().trim());
            searchDto.setCustomerTypesSearch(searchDto.getFieldSearch().trim());
        }
    }

    @Override
    public PageWrapper<ServiceLanguageSearchDto> findAll(int page,
            ServiceLanguageSearchDto serviceSearch, String langCode) {
        // General pageWrapper
        int sizeOfPage = systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
        PageWrapper<ServiceLanguageSearchDto> pageWrapper = new PageWrapper<ServiceLanguageSearchDto>(
                page, sizeOfPage);
        //
        // Add list to pageWrapper
        // set param
        setSearchParam(serviceSearch);
        //
        int count = serviceRepository.countAll(serviceSearch);
        List<ServiceLanguageSearchDto> list = new LinkedList<ServiceLanguageSearchDto>();
        if (count > 0) {
            int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);
            // if(offsetSQL >= count){
            // offsetSQL = Utility.calculateOffsetSQL(page -1 , sizeOfPage);
            // }
            list = serviceLanguageRepository.findAllActive(offsetSQL,
                    sizeOfPage, serviceSearch);
        }
        pageWrapper.setDataAndCount(list, count);

        return pageWrapper;
    }

    @Override
    public ServiceLanguageSearchDto getServiceLanguageDto(Long id,
            String langCode) {
        ServiceLanguageSearchDto dto = serviceLanguageRepository
                .findServiceLanguageDto(id, langCode);
        /*
         * Set new customer_type_name
         */
        String[] customerTypeNames = null;
        String s = "";
        if (dto.getmCustomerTypeName() != null) {
            customerTypeNames = dto.getmCustomerTypeName().split("</br>");
            if (customerTypeNames.length > 0) {
                for (String customerTypeName : customerTypeNames) {
                    s += customerTypeName + "\n";
                }
            }
        }
        if (s.equals("")) {
            s = null;
        }
        dto.setmCustomerTypeName(s);

        return dto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.cms.admin.all.service.ServiceService#getServiceDetailList(java.
     * lang.Long, java.lang.String)
     */
    @Override
    public List<ServiceDetailDto> getServiceDetailList(Long id,
            String langCode) {
        /*
         * get serviceDetailList
         */
        List<ServiceDetailLanguageDto> ServiceDetailLanguageList = getServiceDetailList(
                id);
        List<ServiceDetailDto> serviceDetailList = null;
        for (ServiceDetailLanguageDto detailLanguageDto : ServiceDetailLanguageList) {
            if (detailLanguageDto.getmLanguageCode().equals(langCode)) {
                serviceDetailList = detailLanguageDto.getServiceDetailDtoList();
                break;
            }
        }
        if (serviceDetailList == null) {
            serviceDetailList = new LinkedList<>();
        }

        return serviceDetailList;
    }

    @Override
    public ServiceDto getServiceDto(Long id) {
        ServiceDto serviceDto = new ServiceDto();
        if (id == null) { // if new
            // Set some field for new object
            int sort = serviceRepository.findMaxOrder();
            serviceDto.setSortOrder(sort + 1);
        } else {
            serviceDto = serviceRepository.findServiceDto(id);
        }
        // Get language details list
        serviceDto.setServiceLanguageList(getServiceLanguageDtoList(id));
        // get service details list
        serviceDto.setServiceDetailLanguageDtoList(getServiceDetailList(id));
        // Set list customerTypeIDs;
        List<Long> list = new LinkedList<Long>();
        if (serviceDto.getmCustomerTypeId() != null) {
            String[] customerTypeIDs = serviceDto.getmCustomerTypeId()
                    .split(",");
            if (customerTypeIDs.length > 0) {
                for (String s : customerTypeIDs) {
                    list.add(Long.parseLong(s));
                }
            }
        }
        serviceDto.setCustomerTypeIDs(list);

        return serviceDto;
    }

    public List<ServiceDetailLanguageDto> getServiceDetailList(Long id) {
        List<ServiceDetailLanguageDto> detailLangList = new LinkedList<>();
        List<Language> languageList = languageService.findAllActive();// Get
                                                                      // language
                                                                      // in
                                                                      // system
                                                                      // config
        if (id != null) {
            for (Language language : languageList) {
                ServiceDetailLanguageDto detailLang = new ServiceDetailLanguageDto();
                detailLang.setmLanguageCode(language.getCode());
                List<ServiceDetailDto> serviceDetailDtoList = serviceDetailRepository
                        .findDetailList(id, detailLang.getmLanguageCode());
                detailLang.setServiceDetailDtoList(serviceDetailDtoList);
                detailLangList.add(detailLang);
            }
        }
        return detailLangList;
    }

    public List<ServiceLanguageDto> getServiceLanguageDtoList(Long id) {
        // languageList
        List<Language> languageList = languageService.findAllActive();// Get
                                                                      // language
                                                                      // in
                                                                      // system
                                                                      // config
        List<ServiceLanguageDto> resultList = new LinkedList<>();
        // Get list term language dto all
        if (id != null) {
            List<ServiceLanguage> serviceLanguageList = serviceLanguageRepository
                    .findLanguageByID(id);
            for (Language language : languageList) {
                for (ServiceLanguage serviceLanguage : serviceLanguageList) {
                    if (StringUtils.equals(language.getCode(),
                            serviceLanguage.getmLanguageCode())) {
                        ServiceLanguageDto newT = new ServiceLanguageDto();
                        newT.setId(serviceLanguage.getId());
                        newT.setmServiceId(serviceLanguage.getServiceId());
                        newT.setmLanguageCode(
                                serviceLanguage.getmLanguageCode());
                        newT.setTitle(serviceLanguage.getTitle());
                        newT.setDescriptionAbv(
                                serviceLanguage.getDescriptionAbv());
                        newT.setDescriptionSlogan(
                                serviceLanguage.getDescriptionSlogan());

                        resultList.add(newT);
                    }
                }
            }
        } else {
            for (Language l : languageList) {
                ServiceLanguageDto newEntity = new ServiceLanguageDto();
                newEntity.setmLanguageCode(l.getCode().toUpperCase());
                resultList.add(newEntity);
            }
        }

        return resultList;
    }

    @Override
    public ServiceDto getServiceByCode(String code) {
        return serviceRepository.findServiceDtoByCode(code);
    }

    @Override
    @Transactional
    public void submitObject(ServiceDto serviceDto, String langCode,
            String requestToken) throws IOException {
        // user name login
        String userName = UserProfileUtils.getUserNameLogin();
        vn.com.unit.cms.admin.all.entity.Service entity = new vn.com.unit.cms.admin.all.entity.Service();

        if (serviceDto.getId() != null) {
            entity = serviceRepository.findOne(serviceDto.getId());
            if (entity == null) {
                throw new BusinessException(
                        "Not found Term with id=" + serviceDto.getId());
            }
            entity.setUpdateDate(new Date());
            entity.setUpdateBy(userName);
        } else {
            entity.setCreateBy(userName);
            entity.setCreateDate(new Date());
        }
        entity.setCode(serviceDto.getCode().toUpperCase());
        entity.setName(serviceDto.getName());
        entity.setDescription(serviceDto.getDescriptionAbv());
        entity.setNote(serviceDto.getNote());
        entity.setSortOrder(serviceDto.getSortOrder());
        entity.setCustomerTypeIdList(serviceDto.getmCustomerTypeId());
        moveTmpImage(serviceDto, entity);
        /*
         * Update customer_type_name
         */
        entity.setmCustomerTypeName(updateCustomerTypeName(serviceDto, "VI"));
        /*
         * End set customer_type_name
         */
        serviceRepository.save(entity);
        serviceDto.setId(entity.getId());
        // Insert or update Service Language
        for (ServiceLanguageDto serviceLanguageDto : serviceDto
                .getServiceLanguageList()) {
            ServiceLanguage entityLanguage = new ServiceLanguage();
            if (serviceLanguageDto.getId() != null) {// update
                entityLanguage = serviceLanguageRepository
                        .findOne(serviceLanguageDto.getId());
                if (entityLanguage == null) {
                    throw new BusinessException(
                            "Not found ServiceLanguage with id="
                                    + serviceLanguageDto.getId());
                }
                // set update
                entityLanguage.setUpdateBy(userName);
                entityLanguage.setUpdateDate(new Date());
            } else {// new
                // set create new
                entityLanguage.setCreateDate(new Date());
                entityLanguage.setCreateBy(userName);
                entityLanguage.setServiceId(serviceDto.getId());
            }
            entityLanguage.setmLanguageCode(
                    serviceLanguageDto.getmLanguageCode().toUpperCase());
            entityLanguage.setTitle(serviceLanguageDto.getTitle());
            entityLanguage
                    .setDescriptionAbv(serviceLanguageDto.getDescriptionAbv());
            entityLanguage.setDescriptionSlogan(
                    serviceLanguageDto.getDescriptionSlogan());
            /*
             * Update customer_type_name
             */
            entityLanguage.setmCustomerTypeName(updateCustomerTypeName(
                    serviceDto, entityLanguage.getmLanguageCode()));
            /*
             * End set customer_type_namelocale
             */
            serviceLanguageRepository.save(entityLanguage);
        }
        /* End Action for Service languge */
        /* Insert or Update Service Detail */
        if (serviceDto.getServiceDetailLanguageDtoList() != null) {
            for (ServiceDetailLanguageDto serviceDetailLanguageDto : serviceDto.getServiceDetailLanguageDtoList()) {
                if (serviceDetailLanguageDto.getServiceDetailDtoList() != null) {
                    for (ServiceDetailDto detail : serviceDetailLanguageDto
                            .getServiceDetailDtoList()) {
                        ServiceDetail entityDetail = new ServiceDetail();
                        Long id = detail.getId();
                        if (id == null) {
                            entityDetail.setServiceId(serviceDto.getId());
                            // throw new BusinessException("Not found
                            // ServiceDetail
                            // with id= null");
                        } else {
                            entityDetail = serviceDetailRepository.findOne(detail.getId());
                            if (entityDetail == null) {
                                throw new BusinessException(
                                        "Not found ServiceDetail with id= "
                                                + detail.getId());
                            }
                        }
                        entityDetail.setmLanguageCode(detail.getmLanguageCode().toUpperCase());
                        entityDetail.setContent(detail.getContent());
                        entityDetail.setKeyContent(detail.getKeyContent());
                        entityDetail.setBackgroundUrl(detail.getBackgroundUrl());
                        entityDetail.setBackgroundPhysical(detail.getBackgroundPhysical());
                        entityDetail.setGroupContent(detail.getGroupContent());
                        moveEditorTempFile(requestToken);
                        serviceDetailRepository.save(entityDetail);
                    }
                }
            }
        }
    }

    private String updateCustomerTypeName(ServiceDto serviceDto,
            String langCode) {
        String[] customerTypeIDs = null;
        String customerType = "";
        if (serviceDto.getmCustomerTypeId() != null) {
            customerTypeIDs = serviceDto.getmCustomerTypeId().split(",");
            if (customerTypeIDs.length > 0) {
                for (String custTypeID : customerTypeIDs) {
                    Long typeId = Long.parseLong(custTypeID);
                    CustomerTypeLanguage custTypeDto = new CustomerTypeLanguage();
                    custTypeDto = customerTypeLanguageRepository
                            .findByTypeIdAndLanguageCode(typeId, langCode);
                    if (custTypeDto != null) {
                        customerType += " - " + custTypeDto.getTitle();
                        customerType += "</br>";
                    }
                }
            }
        }
        if (customerType.equals("")) {
            customerType = null;
        }
        return customerType;
    }

    @Override
    @Transactional
    public void deleteObject(Long id) {
        vn.com.unit.cms.admin.all.entity.Service entity = serviceRepository
                .findOne(id);
        if (entity != null) {
            entity.setDeleteDate(new Date());
            entity.setDeleteBy(UserProfileUtils.getUserNameLogin());
        } else {
            throw new BusinessException("Not found id=" + id);
        }

        serviceRepository.save(entity);
        serviceLanguageRepository.deleteLanguageByID(id,
                UserProfileUtils.getUserNameLogin(), new Date());
    }

    @Override
    public List<CustomerTypeDto> getCustomerTypeList(Locale locale) {
        List<CustomerTypeDto> list = customerTypeRepository
                .findByLanguageCode(locale.getLanguage());
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.cms.admin.all.service.ServiceService#addNewDetail(vn.com.unit.
     * hdb.admin.dto.ServiceDto)
     */
    @Override
    public ServiceDto addNewDetail(ServiceDto serviceEdit, List<LanguageDto> languageList) {

        List<ServiceDetailLanguageDto> serviceDetailLanguageDtoList = serviceEdit.getServiceDetailLanguageDtoList();
        DateFormat df = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
        String d = df.format(new Date());
        if (serviceDetailLanguageDtoList != null) {
            for (LanguageDto langDto : languageList) {
                for (ServiceDetailLanguageDto detailLanguageDto : serviceDetailLanguageDtoList) {
                    
                    if (detailLanguageDto.getmLanguageCode().equals(langDto.getCode())) {
                        detailLanguageDto.setmLanguageCode(langDto.getCode());
                        List<ServiceDetailDto> detailDtoList = detailLanguageDto.getServiceDetailDtoList();

                        if (detailDtoList == null) {
                            detailDtoList = new LinkedList<>();
                            detailLanguageDto.setServiceDetailDtoList(detailDtoList);
                            detailLanguageDto.setmLanguageCode(langDto.getCode());
                        }
                        ServiceDetailDto detailDto = new ServiceDetailDto();
                        detailDto.setmLanguageCode(detailLanguageDto.getmLanguageCode());
                        detailDto.setmServiceId(serviceEdit.getId());
                        detailDto.setGroupContent(d);
                        detailDtoList.add(detailDto);
                    }else if(detailLanguageDto.getmLanguageCode().equals("")){
                        detailLanguageDto.setmLanguageCode(langDto.getCode());
                        List<ServiceDetailDto> detailDtoList = detailLanguageDto.getServiceDetailDtoList();

                        if (detailDtoList == null) {
                            detailDtoList = new LinkedList<>();
                            detailLanguageDto.setServiceDetailDtoList(detailDtoList);
                            detailLanguageDto.setmLanguageCode(langDto.getCode());
                        }
                        ServiceDetailDto detailDto = new ServiceDetailDto();
                        detailDto.setmLanguageCode(detailLanguageDto.getmLanguageCode());
                        detailDto.setmServiceId(serviceEdit.getId());
                        detailDto.setGroupContent(d);
                        detailDtoList.add(detailDto);
                    }
                }
            }
        }

        return serviceEdit;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.cms.admin.all.service.ServiceService#deleteServiceDetail(java.
     * lang.Long)
     */
    @Override
    @Transactional
    public void deleteServiceDetail(Long detailID) {
        if (detailID != null) {
            ServiceDetail serviceDetail = serviceDetailRepository.findOne(detailID);            
            serviceDetailRepository.deleteDetailList(serviceDetail.getServiceId(), serviceDetail.getGroupContent());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.cms.admin.all.service.ServiceService#requestDownloadImage(java.
     * lang.String, javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    @Override
    public boolean requestDownloadImage(String imageUrl,
            HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        fileService.download(imageUrl, request, response);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.cms.admin.all.service.ServiceService#requestEditorDownload(java.
     * lang.String, javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    @Override
    public boolean requestEditorDownload(String fileUrl,
            HttpServletRequest request, HttpServletResponse response) {
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
     * @param updateDto
     * @param entity
     * @throws IOException 
     */
    private void moveTmpImage(ServiceDto updateDto,
            vn.com.unit.cms.admin.all.entity.Service entity) throws IOException {
        String physicalImgTmpName = updateDto.getImagePhysicalName();
        // upload images
        if (StringUtils.isNotEmpty(physicalImgTmpName)) {
            String newPhiscalName = CmsUtils.moveTempToUploadFolder(
                    physicalImgTmpName, AdminConstant.SERVICE_FOLDER);
            entity.setImageUrl(newPhiscalName);
            entity.setImageName(updateDto.getImageName());
        } else {
            entity.setImageUrl(null);
            entity.setImageName(null);
        }
    }

    private void moveEditorTempFile(String requestToken) {
    	CmsUtils.moveTempSubFolderToUpload(
                Paths.get(AdminConstant.SERVICE_EDITOR_FOLDER, requestToken)
                        .toString());
    }

}
