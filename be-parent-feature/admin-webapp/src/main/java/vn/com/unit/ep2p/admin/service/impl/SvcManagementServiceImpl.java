/*******************************************************************************
 * Class        :SvcManagementServiceImpl
 * Created date :2019/04/21
 * Lasted date  :2019/04/21
 * Author       :HungHT
 * Change log   :2019/04/21:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.exception.AppException;
import vn.com.unit.common.service.JCommonService;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.api.enumdef.APIStatus;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.core.dto.FileResultDto;
import vn.com.unit.ep2p.admin.dto.ResReportList;
import vn.com.unit.ep2p.admin.dto.ResultDto;
import vn.com.unit.ep2p.admin.dto.SvcManagementDto;
import vn.com.unit.ep2p.admin.dto.SvcManagementSearchDto;
import vn.com.unit.ep2p.admin.enumdef.ResultStatus;
import vn.com.unit.ep2p.admin.repository.ComponentRepository;
import vn.com.unit.ep2p.admin.repository.FormRepository;
import vn.com.unit.ep2p.admin.repository.SvcManagementRepository;
import vn.com.unit.ep2p.admin.service.AbstractCommonService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.core.service.JRepositoryService;
import vn.com.unit.ep2p.admin.service.RegisterSvcService;
import vn.com.unit.ep2p.admin.service.SvcManagementService;
import vn.com.unit.ep2p.constant.AppDirectoryConstant;
import vn.com.unit.ep2p.core.efo.dto.EfoFormDto;
import vn.com.unit.ep2p.dto.CompanyDto;
import vn.com.unit.ep2p.dto.PPLRegisterSvcDto;
import vn.com.unit.ep2p.dto.PPLRegisterSvcSearchDto;
import vn.com.unit.ep2p.enumdef.SvcSearchEnum;
import vn.com.unit.ep2p.utils.AppStringUtils;

/**
 * SvcManagementServiceImpl
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SvcManagementServiceImpl implements SvcManagementService, AbstractCommonService{  
    
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(SvcManagementServiceImpl.class);

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    SvcManagementRepository svcManagementRepository;
    
    @Autowired
    FormRepository formRepository;
    
    @Autowired
    ComponentRepository componentRepository;
    
    @Autowired
    MessageSource msg;
    
    @Autowired
    RegisterSvcService registerSvcService;
    
    @Autowired
    CompanyService companyService;
    
//    @Autowired
//	private JCommonService comService;
    
    @Autowired
    private JRepositoryService repositoryService;

	// Model mapper
	ModelMapper modelMapper = new ModelMapper();

    /**
     * getSvcManagementList
     * @param search
     * @param pageSize
     * @param page
     * @return
     * @author HungHT
     */
    public PageWrapper<SvcManagementDto> getSvcManagementList(SvcManagementSearchDto search, int pageSize, int page, String lang) {
    	List<Integer> listPageSize = systemConfig.getListPageSize();
        int sizeOfPage = systemConfig.getSizeOfPage(listPageSize, pageSize);
        PageWrapper<SvcManagementDto> pageWrapper = new PageWrapper<>(page, sizeOfPage);
        pageWrapper.setListPageSize(listPageSize);
        pageWrapper.setSizeOfPage(sizeOfPage);

        // Set param search
        setSearchParm(search);

        int count = svcManagementRepository.countSvcManagementList(search);
        List<SvcManagementDto> result = new ArrayList<>();
        if (count > 0) {
            int currentPage = pageWrapper.getCurrentPage();
            int startIndex = (currentPage - 1) * sizeOfPage;
            result = svcManagementRepository.getSvcManagementList(search, startIndex, sizeOfPage, lang);
        }
        pageWrapper.setDataAndCount(result, count);
        return pageWrapper;
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
            search.setDescription(search.getFieldSearch() != null ? search.getFieldSearch().trim() : search.getFieldSearch());
            search.setFileName(search.getFieldSearch() != null ? search.getFieldSearch().trim() : search.getFieldSearch());
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
     * @param id
     * @return
     * @author HungHT
     */
    public SvcManagementDto findById(Long id, String lang) {
        return svcManagementRepository.findById(id, lang);
    }

	/**
     * findByProperties1
     * @param properties1
     * @return
     * @author HungHT
     */
    public SvcManagementDto findByProperties1(String properties1) {
        return svcManagementRepository.findByProperties1(properties1);
    }

	/**
     * initScreenDetail
     * @param mav
     * @param objectDto
     * @param locale
     * @author HungHT
     */
    public void initScreenDetail(ModelAndView mav, SvcManagementDto objectDto, Locale locale) {
        mav.addObject("listForm", getOZFormFileName(objectDto.getOzFilePath()));
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
        EfoFormDto form = formRepository.findByCompanyIdAndName(objectDto.getCompanyId(), objectDto.getFormName(), objectDto.getFormId());
        if (null != form) {
            result.setStatus(ResultStatus.FAIL.toInt());
            result.setMessage(msg.getMessage(ConstantCore.MSG_ERROR_SERVICE_EXISTS, new String[] { objectDto.getFormName() }, null));
            return result;
        }

        // Create bussiness code
        String serviceName = AppStringUtils.removeAccent(objectDto.getFormName()).replace(" ", "");
        // Save file
        MultipartFile file = objectDto.getFile();
        @SuppressWarnings("unused")
		String filePath = null;
        @SuppressWarnings("unused")
		Long repositoryId = null;
        if (null != file) {
            CompanyDto company = companyService.findById(objectDto.getCompanyId());
            if (null == company) {
                throw new AppException("B201", null);
            }
            String subFilePath = AppDirectoryConstant.SERVICE_IMAGES.concat(company.getSystemCode());
            FileResultDto repoResultDto = repositoryService.uploadFileBySettingKey(file, serviceName, SystemConfig.REPO_UPLOADED_MAIN, 2,
                    null, subFilePath, objectDto.getCompanyId(), locale);
            if (repoResultDto.isStatus()) {
                filePath = repoResultDto.getFilePath();
                repositoryId = repoResultDto.getRepositoryId();
            } else {
                throw new AppException(repoResultDto.getMessage(), repoResultDto.getMessageCode(), repoResultDto.getArgs());
            }
        }

//        boolean isChangeFormFileName = false;
//        Date sysDate = CommonDateUtil.getSystemDateTime();
//        String user = UserProfileUtils.getUserNameLogin();
//        EfoForm objectCurrent = formRepository.findOne(objectDto.getId());
//        objectCurrent.setCategoryId(objectDto.getCategoryId());
//        objectCurrent.setName(objectDto.getName());
//        objectCurrent.setDescription(objectDto.getDescription());
//        if (!objectCurrent.getFileName().equalsIgnoreCase(objectDto.getFormFileName())) {
//            objectCurrent.setFileName(objectDto.getFormFileName());
//            isChangeFormFileName = true;
//        }
//        if (StringUtils.isNotBlank(filePath)) {
//            objectCurrent.setImage(filePath);
//            objectCurrent.setRepositoryId(repositoryId);
//        }
//        objectCurrent.setBusinessCode(objectDto.getBusinessCode());
//        objectCurrent.setFunctionCode(objectDto.getFunctionCode());
//        objectCurrent.setDisplayOrder(objectDto.getDisplayOrder());
//        objectCurrent.setActiveFlag(objectDto.isActiveFlag() ? "1" : "0");
//        objectCurrent.setDeviceType(objectDto.getDeviceType());
//        objectCurrent.setUpdatedId(UserProfileUtils.getAccountId());
//        objectCurrent.setUpdatedDate(sysDate);
//        if (objectCurrent.getId() != null) {
//        	formRepository.update(objectCurrent);
//        } else {
//        	formRepository.create(objectCurrent);
//        }
        
        // save component
//        if (isChangeFormFileName) {
//            // Delete component old
//            componentRepository.deleteByFormId(objectDto.getId(), user, sysDate);
//
//            // Call API get Component
//            List<Component> componentList = registerSvcService.getComponentListfromOZRepository(objectDto.getId(), objectDto.getName(),
//                    objectDto.getFormFileName(), null);
//
//            // Save component
//            registerSvcService.saveComponent(componentList, objectDto.getName());
//        }
        // Return result
        result.setStatus(ResultStatus.SUCCESS.toInt());
        result.setMessage(msg.getMessage(ConstantCore.MSG_SUCCESS_SAVE, null, null));
        return result;
    }

	/**
     * deleteSvcManagement
     * @param id
     * @return
     * @author HungHT
     */
    public boolean deleteSvcManagement(Long id) {
//		Date sysDate = CommonDateUtil.getSystemDateTime();
//        String user = UserProfileUtils.getUserNameLogin();
//        EfoForm object = formRepository.findOne(id);
//        object.setDeletedId(UserProfileUtils.getAccountId());
//        object.setDeletedDate(sysDate);
//        formRepository.save(object);
        return true;
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
    public List<Select2Dto> getOZFormFileName(String currentFormFileName) {
        List<Select2Dto> listResult = new ArrayList<>();
        Select2Dto obj = new Select2Dto();
        obj.setId(currentFormFileName);
        obj.setText(currentFormFileName);
        obj.setName(currentFormFileName);
        listResult.add(obj);
        PPLRegisterSvcSearchDto search = new PPLRegisterSvcSearchDto();
        search.setCompanyName(companyService.getSystemCodeByCompanyId(UserProfileUtils.getCompanyId()));
        search.setCompanyId(UserProfileUtils.getCompanyId());
        try {
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
        } catch (AppException e) {
            logger.error(e.getMessage());
        }
        return listResult;
    }

	@Override
	public JCommonService getCommonService() {
		// TODO Auto-generated method stub
		return null;
	}
}