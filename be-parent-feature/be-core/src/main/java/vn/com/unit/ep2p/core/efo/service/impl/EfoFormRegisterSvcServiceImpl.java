/*******************************************************************************
 * Class        ：EfoFormRegisterSvcServiceImpl
 * Created date ：2020/12/31
 * Lasted date  ：2020/12/31
 * Author       ：taitt
 * Change log   ：2020/12/31：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.api.dto.ResRESTApi;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.entity.JcaCompany;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaCompanyService;
import vn.com.unit.core.storage.dto.AbstractResultUpload;
import vn.com.unit.dts.api.enumdef.APIStatus;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppCoreConstant;
import vn.com.unit.ep2p.core.constant.AppCoreExceptionCodeConstant;
import vn.com.unit.ep2p.core.constant.AppSystemSettingKey;
import vn.com.unit.ep2p.core.dto.EfoFormRegisterRes;
import vn.com.unit.ep2p.core.dto.EfoFormRegisterSvcUploadFileDto;
import vn.com.unit.ep2p.core.efo.dto.EfoFormRegisterSearchDto;
import vn.com.unit.ep2p.core.efo.entity.EfoComponent;
import vn.com.unit.ep2p.core.efo.service.EfoFormRegisterSvcService;
import vn.com.unit.ep2p.core.exception.HandlerCastException;
import vn.com.unit.ep2p.core.res.dto.ComponentFormRes;
import vn.com.unit.ep2p.core.service.RestFullApiService;
import vn.com.unit.storage.dto.FileUploadParamDto;
import vn.com.unit.storage.dto.FileUploadResultDto;
import vn.com.unit.storage.service.FileStorageService;

/**
 * EfoFormRegisterSvcServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EfoFormRegisterSvcServiceImpl implements EfoFormRegisterSvcService{

    @Autowired
    private JcaCompanyService jcaCompanyService;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private HandlerCastException castException;
    
    @Autowired
    private RestFullApiService restFullApiService;
    
    @Autowired
    private SystemConfig systemConfig;
    
    private static final String IMGAE_FORM_FOLDER = "service_images/";
    
    /** The Constant KEY_COMPANY_NAME. */
    // PARAM SERVICE
    private static final String KEY_COMPANY_NAME = "companyName";
    
    /** The Constant FORM_FILE_NAME. */
    private static final String FORM_FILE_NAME = "formFileName";
    
    /** The Constant FORM_OZ_CATEGORY. */
    private static final String FORM_OZ_CATEGORY = "formOzCategory";
    
    /** The Constant FORM_ID. */
    private static final String FORM_ID = "formId";
    
    /** The Constant CREATED_BY. */
    private static final String CREATED_BY = "createdBy";
    
    /** The Constant CREATED_DATE. */
    private static final String CREATED_DATE = "createdDate";

    @SuppressWarnings("unchecked")
	@Override
    public <T extends AbstractResultUpload> T uploadFileImageFormRegister(byte[] base64Image, Long companyId, String businessCode, String formName) throws DetailException {
        EfoFormRegisterSvcUploadFileDto result = new EfoFormRegisterSvcUploadFileDto();
        try {           
            String filePath = null;
            Long repositoryId = null;
            if (null != base64Image) {
                JcaCompany company = jcaCompanyService.getCompanyById(companyId);
                if (null == company) {
                    throw new DetailException(AppCoreExceptionCodeConstant.E401809_CORE_REGISTER_FORM_COMPANY_NOT_FOUNT, new String[] { formName },true);
                }
                repositoryId = Long.valueOf(systemConfig.getConfig(SystemConfig.REPO_DOCUMENT, companyId));
                
                String subFilePath = IMGAE_FORM_FOLDER.concat(company.getSystemCode());
                
                //fileupload
                FileUploadParamDto param = new FileUploadParamDto();
                param.setFileByteArray(base64Image);
                param.setFileName(businessCode);
                param.setRename(null);
                
                param.setTypeRule(2);
                param.setDateRule(null);
                param.setSubFilePath(subFilePath);
                param.setCompanyId(companyId);
                param.setRepositoryId(repositoryId);
                FileUploadResultDto uploadResultDto = fileStorageService.upload(param);
                if (uploadResultDto.isSuccess()) {
                    filePath = uploadResultDto.getFilePath();
                    repositoryId = uploadResultDto.getRepositoryId();
                } else {
                    throw new DetailException(AppCoreExceptionCodeConstant.E401802_CORE_REGISTER_FORM_FAIL, new String[] { formName },true);
                }
            }
            result.setFilePath(filePath);
            result.setRepositoryId(repositoryId);
        }catch (Exception e) {
            castException.castException(e, AppCoreExceptionCodeConstant.E401802_CORE_REGISTER_FORM_FAIL);
        }
        return (T) result;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.RegisterSvc#getComponentListfromOZRepository(java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
     */
    @Override
    public List<EfoComponent> getComponentListfromOZRepository(Long formId, String formName, String formFileName, String formOzCategory,
            Long companyId) throws DetailException{
        List<EfoComponent> componentList = null;
        try {
            String urlRepo = systemConfig.getConfig(AppSystemSettingKey.OZ_REPOSITORY_LOCAL_URL,companyId);
            String url = urlRepo.concat(AppCoreConstant.URL_CONSTANT_REPO_FORM.substring(1).concat(AppCoreConstant.URL_CONSTANT_REPO_REPORT_TEMPLATE).concat(AppCoreConstant.URL_CONSTANT_REPO_REPORT_COMPONENT_TEMPLATE));
            
            // Init param request
            Map<String, Object> requestObj = new HashMap<>();
            requestObj.put(FORM_FILE_NAME, formFileName);
            requestObj.put(FORM_OZ_CATEGORY, formOzCategory);
            requestObj.put(FORM_ID, formId);
            requestObj.put(CREATED_BY, "admin");
            requestObj.put(CREATED_DATE, CommonDateUtil.getSystemDateTime());
            
            // Call API to repository server            
            ComponentFormRes resComponentList = restFullApiService.restFull(url, requestObj, ComponentFormRes.class);
            
            if (resComponentList != null && APIStatus.SUCCESS.toString().equalsIgnoreCase(resComponentList.getStatus())) {
                componentList = resComponentList.getResultObj();
            }
        } catch (Exception e) {
            throw new DetailException(AppCoreExceptionCodeConstant.E401805_CORE_REGISTER_FORM_CANNOT_RETRIEVE_OZ, new String[] { formName },true);
        }
        return componentList;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.RegisterSvc#getEfoFormRegisterDtoListOZRepository(vn.com.unit.core.dto.EfoFormRegisterSearchDto)
     */
    @SuppressWarnings("unchecked")
	@Override
    public <T extends ResRESTApi> T getEfoFormRegisterDtoListOZRepository(EfoFormRegisterSearchDto search) throws DetailException {
        EfoFormRegisterRes reportList = null;
        Long companyId = UserProfileUtils.getUserPrincipal().getCompanyId();
        try {
            String urlRepo = systemConfig.getConfig(AppSystemSettingKey.OZ_REPOSITORY_LOCAL_URL,companyId);
            String url = urlRepo.concat(AppCoreConstant.URL_CONSTANT_REPO_FORM.substring(1).concat(AppCoreConstant.URL_CONSTANT_REPO_REPORT_TEMPLATE));
            
            // Init param request
            Map<String, Object> requestObj = new HashMap<>();
            requestObj.put(KEY_COMPANY_NAME, search.getCompanyName());
            
            // Call API to repository server
            reportList = restFullApiService.restFull(url, requestObj, EfoFormRegisterRes.class);
        } catch (Exception e) {
            throw new DetailException(AppCoreExceptionCodeConstant.E401801_CORE_REGISTER_FORM_EXISTS);
        }
        return (T) reportList;
    }

}
