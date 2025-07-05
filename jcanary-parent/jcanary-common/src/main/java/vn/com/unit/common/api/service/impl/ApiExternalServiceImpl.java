/*******************************************************************************
 * Class        ：ApiExternalServiceImpl
 * Created date ：2020/11/20
 * Lasted date  ：2020/11/20
 * Author       ：taitt
 * Change log   ：2020/11/20：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.common.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.api.service.ApiExternalService;
import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.dts.api.dto.AbstractApiExternalDto;
import vn.com.unit.dts.api.dto.AbstractResApiExternalDto;
import vn.com.unit.dts.api.enumdef.ApiExternalProtocol;
import vn.com.unit.dts.api.service.AbstractApiExternalService;

/**
 * ApiExternalServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
public class ApiExternalServiceImpl implements ApiExternalService{

    @Autowired
    ApplicationContext applicationContext;
    
    
    @Override
    public <T extends AbstractApiExternalDto, E extends AbstractResApiExternalDto> E callAPI(T apiDto, String url
            , Object requestObj, Class<E> responseClass, String protocol) throws Exception {
        
        ApiExternalProtocol apiExternalProtocol = ApiExternalProtocol.resolveByValue(protocol);
        AbstractApiExternalService abstractApiExternalService = null;
        if (null != apiExternalProtocol) {
            switch (apiExternalProtocol) {
            case REST_FULL:
                abstractApiExternalService = (AbstractApiExternalService) applicationContext.getBean(CommonConstant.BEAN_REST_FULL_API_EXTERNAL);
                break;
            case SOAP:
                abstractApiExternalService = (AbstractApiExternalService) applicationContext.getBean(CommonConstant.BEAN_SOAP_API_EXTERNAL);
                break;
            default:
                abstractApiExternalService = (AbstractApiExternalService) applicationContext.getBean(CommonConstant.BEAN_REST_FULL_API_EXTERNAL);
                break;
            }
        }
        apiDto.setAbstractApiExternalService(abstractApiExternalService);
        
        return abstractApiExternalService.callAPI(apiDto, url, requestObj, responseClass);
    }
    
    
    @Override
    public <T extends AbstractApiExternalDto, E extends AbstractResApiExternalDto> byte[] callAPIDownload(T apiDto, String url
            ,Object requestObj  , String protocol) throws Exception {
        
        ApiExternalProtocol apiExternalProtocol = ApiExternalProtocol.resolveByValue(protocol);
        AbstractApiExternalService abstractApiExternalService = null;
        if (null != apiExternalProtocol) {
            switch (apiExternalProtocol) {
            case REST_FULL:
                abstractApiExternalService = (AbstractApiExternalService) applicationContext.getBean(CommonConstant.BEAN_REST_FULL_API_EXTERNAL);
                break;
            case SOAP:
                abstractApiExternalService = (AbstractApiExternalService) applicationContext.getBean(CommonConstant.BEAN_SOAP_API_EXTERNAL);
                break;
            default:
                abstractApiExternalService = (AbstractApiExternalService) applicationContext.getBean(CommonConstant.BEAN_REST_FULL_API_EXTERNAL);
                break;
            }
        }
        apiDto.setAbstractApiExternalService(abstractApiExternalService);
        
        return abstractApiExternalService.callAPIDownloadFile(apiDto, url, requestObj);
    }
    
    
    @Override
    public <T extends AbstractApiExternalDto, E extends AbstractResApiExternalDto> byte[] callAPIUpload(T apiDto, String url
            , MultiValueMap<String, Object> requestObj, String protocol) throws Exception {
        
        ApiExternalProtocol apiExternalProtocol = ApiExternalProtocol.resolveByValue(protocol);
        AbstractApiExternalService abstractApiExternalService = null;
        if (null != apiExternalProtocol) {
            switch (apiExternalProtocol) {
            case REST_FULL:
                abstractApiExternalService = (AbstractApiExternalService) applicationContext.getBean(CommonConstant.BEAN_REST_FULL_API_EXTERNAL);
                break;
            case SOAP:
                abstractApiExternalService = (AbstractApiExternalService) applicationContext.getBean(CommonConstant.BEAN_SOAP_API_EXTERNAL);
                break;
            default:
                abstractApiExternalService = (AbstractApiExternalService) applicationContext.getBean(CommonConstant.BEAN_REST_FULL_API_EXTERNAL);
                break;
            }
        }
        apiDto.setAbstractApiExternalService(abstractApiExternalService);
        
        return abstractApiExternalService.callAPIUploadFile(apiDto, url, requestObj);
    }

}
