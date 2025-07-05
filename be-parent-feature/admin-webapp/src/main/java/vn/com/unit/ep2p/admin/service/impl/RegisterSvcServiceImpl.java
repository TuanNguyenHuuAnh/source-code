/*******************************************************************************
 * Class        :RegisterSvcServiceImpl
 * Created date :2019/04/16
 * Lasted date  :2019/04/16
 * Author       :HungHT
 * Change log   :2019/04/16:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import vn.com.unit.common.exception.AppException;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.api.enumdef.APIStatus;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.FileResultDto;
import vn.com.unit.ep2p.admin.dto.ResReportList;
import vn.com.unit.ep2p.admin.dto.ResultDto;
import vn.com.unit.ep2p.admin.enumdef.ResultStatus;
import vn.com.unit.ep2p.admin.repository.ComponentRepository;
import vn.com.unit.ep2p.admin.repository.FormRepository;
import vn.com.unit.ep2p.admin.repository.ItemRepository;
import vn.com.unit.ep2p.admin.service.APIService;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.ItemManagementService;
import vn.com.unit.core.service.JRepositoryService;
import vn.com.unit.ep2p.admin.service.RegisterSvcService;
import vn.com.unit.ep2p.constant.AppConstantCore;
import vn.com.unit.ep2p.constant.AppDirectoryConstant;
import vn.com.unit.ep2p.constant.AppSystemSettingKey;
import vn.com.unit.ep2p.constant.AppUrlConst;
import vn.com.unit.ep2p.core.efo.dto.EfoFormDto;
import vn.com.unit.ep2p.core.efo.entity.EfoComponent;
import vn.com.unit.ep2p.dto.CompanyDto;
import vn.com.unit.ep2p.dto.PPLRegisterSvcDto;
import vn.com.unit.ep2p.dto.PPLRegisterSvcSearchDto;
import vn.com.unit.ep2p.dto.ResComponentList;
import vn.com.unit.ep2p.utils.AppStringUtils;

/**
 * RegisterSvcServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
/**
 * RegisterSvcServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RegisterSvcServiceImpl implements RegisterSvcService {

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    FormRepository formRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ComponentRepository componentRepository;

    @Autowired
    ItemManagementService itemManagementService;

    @Autowired
    APIService apiService;

    @Autowired
    CompanyService companyService;

    @Autowired
    MessageSource msg;

    @Autowired
    private CommonService comService;

    @Autowired
    private JRepositoryService repositoryService;

    // Model mapper
    ModelMapper modelMapper = new ModelMapper();

    private static final String KEY_COMPANY_NAME = "companyName";

    private static final String FORM_FILE_NAME = "formFileName";

    private static final String FORM_OZ_CATEGORY = "formOzCategory";

    private static final String FORM_ID = "formId";

    private static final String CREATED_BY = "createdBy";

    private static final String CREATED_DATE = "createdDate";

    /**
     * getRegisterSvcList
     * 
     * @param search
     * @return
     * @author HungHT
     * @throws AppException
     */
    @Override
    public ResReportList getRegisterSvcList(PPLRegisterSvcSearchDto search) throws AppException {
        ResReportList reportList = null;
        try {
            String ozserver = systemConfig.getConfig(AppSystemSettingKey.OZ_REPOSITORY_LOCAL_URL);
            String url = ozserver.concat(AppUrlConst.API_V1_FORM_REPORTS);

            // Init param request
            Map<String, Object> requestObj = new HashMap<>();
            requestObj.put(KEY_COMPANY_NAME, search.getCompanyName());

            // Call API to repository server
            reportList = apiService.callAPI(url, HttpMethod.POST, null, null, requestObj, ResReportList.class);
            if (reportList != null && APIStatus.SUCCESS.toString().equalsIgnoreCase(reportList.getStatus())) {
                List<PPLRegisterSvcDto> newRegisterList = new ArrayList<>();
//                List<PPLRegisterSvcDto> registerList = reportList.getResultObj();
//                List<String> fileNameList = registerList.stream().map(x -> x.getReportPath().toUpperCase()).collect(Collectors.toList());
//
//                // Get list form from database
//                List<EfoFormDto> formList = formRepository.findByCompanyIdAndFileName(search.getCompanyId(), fileNameList);
//                EfoFormDto resultDto;
//                if (null == formList || formList.size() == 0) {
//                    newRegisterList = registerList;
//                } else {
//                    for (PPLRegisterSvcDto PPLRegisterSvcDto : registerList) {
//                        // Search in formList to check exists
//                        resultDto = formList.stream()
//                                .filter(predicate -> predicate.getFileName().equalsIgnoreCase(PPLRegisterSvcDto.getReportPath())).findAny()
//                                .orElse(null);
//                        if (null == resultDto) {
//                            newRegisterList.add(PPLRegisterSvcDto);
//                        }
//                    }
//                }
                // Set new services which do not register
                reportList.setResultObj(newRegisterList);
            }
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e, "B200", null);
        }
        return reportList;
    }

    /**
     * registerSvc
     * 
     * @param register
     * @param locale
     * @return
     * @author HungHT
     * @throws IOException
     */
    public ResultDto registerSvc(PPLRegisterSvcSearchDto register, Locale locale) throws Exception {

        ResultDto result = new ResultDto();

        // Check service name
        EfoFormDto form = formRepository.findByCompanyIdAndName(register.getCompanyId(), register.getServiceName(), null);
        if (null != form) {
            result.setStatus(ResultStatus.FAIL.toInt());
            result.setMessage(msg.getMessage(AppConstantCore.MSG_ERROR_SERVICE_EXISTS, new String[] { register.getServiceName() }, null));
            return result;
        }

        // Create bussiness code
        String businessCode = AppStringUtils.removeAccent(register.getServiceName()).replace(" ", "");
        // Save file
        MultipartFile file = register.getFile();
        @SuppressWarnings("unused")
		String filePath = null;
        @SuppressWarnings("unused")
		Long repositoryId = null;
        if (null != file) {
            CompanyDto company = companyService.findById(register.getCompanyId());
            if (null == company) {
                throw new AppException("B201", null);
            }
            String subFilePath = AppDirectoryConstant.SERVICE_IMAGES.concat(company.getSystemCode());
            FileResultDto repoResultDto = repositoryService.uploadFileBySettingKey(file, businessCode, SystemConfig.REPO_UPLOADED_MAIN,
                    2, null, subFilePath, register.getCompanyId(), locale);
            if (repoResultDto.isStatus()) {
                filePath = repoResultDto.getFilePath();
                repositoryId = repoResultDto.getRepositoryId();
            } else {
                throw new AppException(repoResultDto.getMessage(), repoResultDto.getMessageCode(), repoResultDto.getArgs());
            }
        }
        // Get message description
//        String description = ExecMessage.getMessage(msg, "B210", new String[] { register.getServiceName() },
//                systemConfig.getConfig(AppSystemConfig.LANGUAGE_DEFAULT));

        // Check service business
        @SuppressWarnings("unused")
		String resultBusinessCode = null;
        if (StringUtils.isNotBlank(register.getBusinessCode())) {
            resultBusinessCode = register.getBusinessCode();
        } else {
            // Create bussiness

            // Create process
            // createProcess("P".concat(businessCode), register.getServiceName(), resultBusinessCode, register.getCompanyId());
        }

//		/*
//		 * // Check service function String functionCode = null; if
//		 * (StringUtils.isBlank(register.getFunctionCode())) { // Create item String
//		 * subType = "PERM"; JcaItem item = createItem(businessCode,
//		 * register.getServiceName(), StringUtils.EMPTY, resultBusinessCode,
//		 * description, register.getCompanyId(), subType); functionCode =
//		 * item.getFunctionCode();
//		 * 
//		 * // Create item field permission subType = "FIELD_PERM";
//		 * createItem("FPM#".concat(businessCode), register.getServiceName(),
//		 * " (Field permission)", resultBusinessCode, description,
//		 * register.getCompanyId(), subType); } else { functionCode =
//		 * register.getFunctionCode(); }
//		 */
        // Save to form table
//        EfoForm service = saveService(register, filePath, repositoryId, resultBusinessCode, null);

        // Call API get Component
//        List<Component> componentList = getComponentListfromOZRepository(service.getId(), service.getName(), service.getFileName(), null);

        // Save component
//        saveComponent(componentList, service.getName());

        // Return result
        result.setStatus(ResultStatus.SUCCESS.toInt());
        result.setMessage(msg.getMessage(AppConstantCore.MSG_SUCCESS_REGISTER_SERVICE, new String[] { register.getServiceName() }, null));
        return result;
    }

//    /**
//     * createItem
//     * 
//     * @param itemCode
//     * @param itemName
//     * @param suffix
//     * @param businessCode
//     * @param description
//     * @param companyId
//     * @param subType
//     * @return
//     * @throws AppException
//     * @author HungHT
//     */
//    private JcaItem createItem(String itemCode, String itemName, String suffix, String businessCode, String description, Long companyId,
//            String subType) throws AppException {
//        JcaItem item = null;
//        String system = companyService.getSystemCodeByCompanyId(companyId);
//        String code = system.concat("#").concat(itemCode);
//        String name = itemName.concat(suffix);
//        try {
//            int index = 0;
//            do {
//                index += 1;
//                if (index > 10) {
//                    throw new Exception();
//                }
//                // Check exists item
//                Boolean isExists = formRepository.checkExistItem(code, companyId);
//                if (isExists == null || !isExists) {
//                    item = new JcaItem();
//                    item.setFunctionCode(code);
//                    item.setFunctionName(name);
//                    item.setFunctionType("3");
//                    item.setCompanyId(UserProfileUtils.getCompanyId());
//                    item.setDescription(description);
//                    // item.setBusinessCode(businessCode);
//                    // item.setSubType(subType);
//                    item.setDisplayOrder(itemManagementService.getMaxDisplayOrder());
//                    item.setCreatedId(UserProfileUtils.getAccountId());
//                    item.setCreatedDate(comService.getSystemDateTime());
//                    item.setCompanyId(companyId);
//                    item = itemRepository.save(item);
//                    break;
//                } else {
//                    code = system.concat("#").concat(itemCode).concat(String.valueOf(index));
//                    name = itemName.concat(String.valueOf(index)).concat(suffix);
//                }
//            } while (item == null);
//        } catch (Exception e) {
//            throw new AppException(e.getMessage(), e, "B204", new String[] { itemName });
//        }
//        return item;
//    }
//
//    /**
//     * saveService
//     * 
//     * @param register
//     * @param filePath
//     * @param repositoryId
//     * @param businessCode
//     * @param functionCode
//     * @return
//     * @author HungHT
//     * @throws AppException
//     */
//    private EfoForm saveService(PPLRegisterSvcSearchDto register, String filePath, Long repositoryId, String businessCode,
//            String functionCode) throws AppException {
//        try {
////            Long displayOrder = formRepository.findMaxDisplayOrderByCompanyId(register.getCompanyId()) + 1;
////            EfoForm service = new EfoForm();
////            service.setCompanyId(register.getCompanyId());
////            service.setCategoryId(register.getCategoryId());
////            service.setBusinessCode(businessCode);
////            service.setFunctionCode(functionCode);
////            service.setName(register.getServiceName());
////            service.setDescription(register.getReportDesc());
////            service.setFileName(register.getReportPath());
////            service.setImage(filePath);
////            service.setRepositoryId(repositoryId);
////            service.setDisplayOrder(displayOrder);
////            service.setDeviceType(register.getDeviceType());
////            service.setActiveFlag("1");
////            service.setCreatedId(UserProfileUtils.getAccountId());
////            service.setCreatedDate(comService.getSystemDateTime());
////            return formRepository.save(service);
//            return null;
//        } catch (Exception e) {
//            throw new AppException(e.getMessage(), e, "B205", new String[] { register.getServiceName() });
//        }
//    }

    /**
     * getComponentListfromOZRepository
     * 
     * @param formId
     * @param formName
     * @param formFileName
     * @param formOzCategory
     * @return
     * @throws AppException
     * @author HungHT
     */
    public List<EfoComponent> getComponentListfromOZRepository(Long formId, String formName, String formFileName, String formOzCategory)
            throws AppException {
        List<EfoComponent> componentList = null;
        try {
            String ozserver = systemConfig.getConfig(AppSystemSettingKey.OZ_REPOSITORY_LOCAL_URL);
            String url = ozserver.concat(AppUrlConst.API_V1_FORM_REPORTS_COMPONENT);

            // Init param request
            Map<String, Object> requestObj = new HashMap<>();
            requestObj.put(FORM_FILE_NAME, formFileName);
            requestObj.put(FORM_OZ_CATEGORY, formOzCategory);
            requestObj.put(FORM_ID, formId);
            requestObj.put(CREATED_BY, UserProfileUtils.getUserNameLogin());
            requestObj.put(CREATED_DATE, comService.getSystemDateTime());

            // Call API to repository server
            ResComponentList resComponentList = apiService.callAPI(url, HttpMethod.POST, null, null, requestObj, ResComponentList.class);
            if (resComponentList != null && APIStatus.SUCCESS.toString().equalsIgnoreCase(resComponentList.getStatus())) {
                componentList = resComponentList.getResultObj();
            }
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e, "B206", new String[] { formName });
        }
        return componentList;
    }

    /**
     * saveComponent
     * 
     * @param componentList
     * @author HungHT
     * @throws AppException
     */
    public void saveComponent(List<EfoComponent> componentList, String formName) throws AppException {
        try {
            if (null != componentList && componentList.size() > 0) {
                // Save component
                componentRepository.save(componentList);
            }
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e, "B207", new String[] { formName });
        }
    }
}