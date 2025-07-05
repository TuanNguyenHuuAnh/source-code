/*******************************************************************************
 * Class        ：RestFullApiServiceImpl
 * Created date ：2021/02/02
 * Lasted date  ：2021/02/02
 * Author       ：taitt
 * Change log   ：2021/02/02：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.api.dto.RESTApiDto;
import vn.com.unit.common.api.service.ApiExternalService;
import vn.com.unit.common.api.service.RESTApiService;
import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.dts.api.dto.AbstractResApiExternalDto;
import vn.com.unit.ep2p.core.service.RestFullApiService;

/**
 * RestFullApiServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class RestFullApiServiceImpl implements RestFullApiService {

    @Autowired
    private RESTApiService restApiService;
    
    @Autowired
    private ApiExternalService apiExternalService;
    
    @Override
    public byte[] uploadRestFull(String url,MultiValueMap<String, Object> requestObj) throws Exception{
        byte[] response = null;
        try {
            RESTApiDto restApiDto = new RESTApiDto();
            restApiDto.setTimeoutSeconds(CommonConstant.API_EXTERNAL_TIMEOUT);
            restApiDto.setAbstractApiExternalService(restApiService);
            restApiDto.setHttpMethod(HttpMethod.POST);
            
            // Call API to repository server
            response = apiExternalService.callAPIUpload(restApiDto, url , requestObj,CommonConstant.API_PROTOCOL_REST_FULL);
        }catch (Exception e) {
           throw new Exception(e);
        }
        return response;        
    }
    
    @Override
   public <E extends AbstractResApiExternalDto> E restFull(String url,
            Object requestObj, Class<E> responseClass) throws Exception{
        E responseObj = null;
        try {
            RESTApiDto restApiDto = new RESTApiDto();
            restApiDto.setTimeoutSeconds(CommonConstant.API_EXTERNAL_TIMEOUT);
            restApiDto.setAbstractApiExternalService(restApiService);
            restApiDto.setHttpMethod(HttpMethod.POST);
            
            responseObj = apiExternalService.callAPI(restApiDto,url , requestObj, responseClass,CommonConstant.API_PROTOCOL_REST_FULL);
        }catch (Exception e) {
           throw new Exception(e);
        }
        return responseObj;        
    }
}
