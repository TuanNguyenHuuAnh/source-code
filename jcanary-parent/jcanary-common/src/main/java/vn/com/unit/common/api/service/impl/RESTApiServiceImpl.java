/*******************************************************************************
 * Class        ：RESTApiServiceImpl
 * Created date ：2020/11/20
 * Lasted date  ：2020/11/20
 * Author       ：taitt
 * Change log   ：2020/11/20：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.common.api.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

import vn.com.unit.common.api.dto.RESTApiDto;
import vn.com.unit.common.api.service.RESTApiService;
import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.logger.DebugLogger;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.dts.api.dto.AbstractApiExternalDto;
import vn.com.unit.dts.api.dto.AbstractResApiExternalDto;
import vn.com.unit.dts.api.enumdef.APIStatus;
import vn.com.unit.dts.utils.DtsErrorMessageUtil;

/**
 * RESTApiServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Component(value = CommonConstant.BEAN_REST_FULL_API_EXTERNAL)
@Scope(value = "singleton")
public class RESTApiServiceImpl implements RESTApiService{
    /** msg */
    @Autowired
    MessageSource msg;

    @Override
    public <T extends AbstractApiExternalDto, E extends AbstractResApiExternalDto> E callAPI(T object,String url, Object requestObj, Class<E> responseClass) throws Exception {
        
        // PT Tracking - callAPI - Start
        DebugLogger debugLogger = new DebugLogger();
        DebugLogger.debug("[PT Tracking] | [callAPI] | [%d] | [Begin] | [%s] | [%d] | [%s]", Thread.currentThread().getId(), debugLogger.getStart(), 0, url);
        
        
        String lang = "en"; //TODO systemConfig.getConfig(SystemConfig.LANGUAGE_DEFAULT);
        E responseObj = null;
        String requestJson = CommonStringUtil.EMPTY;
        try {
            requestJson = CommonJsonUtil.convertObjectToJSON(requestObj);
        } catch (JsonProcessingException e1) {
//            logger.error("[callAPI] Error: " + e1);
        }
        
        Integer timeoutSeconds = object.getTimeoutSeconds();
        
        // Create RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        if (timeoutSeconds == null) {
            requestFactory.setConnectTimeout(CommonConstant.API_EXTERNAL_TIMEOUT);
            requestFactory.setReadTimeout(CommonConstant.API_EXTERNAL_TIMEOUT);
        } else {
            requestFactory.setConnectTimeout(timeoutSeconds);
            requestFactory.setReadTimeout(timeoutSeconds);
        }
        restTemplate.setRequestFactory(requestFactory);      
        HttpHeaders headers = ((RESTApiDto) object).getHeaders();
        HttpMethod httpMethod = ((RESTApiDto) object).getHttpMethod();
        // Create HttpEntity
        if (headers == null) {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        }
        HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
        
        // Call API
        try {
            ResponseEntity<E> result = restTemplate.exchange(url, httpMethod, entity, responseClass);
            
            responseObj = result.getBody();
            if (responseObj == null) {
                E obj = (E) new AbstractResApiExternalDto();
                responseObj = obj;
            }
            if (CommonStringUtil.isBlank(responseObj.getStatus())) {
                responseObj.setStatus(APIStatus.SUCCESS.toString());
            }
            
        } catch (HttpStatusCodeException e) {
            // Handle HttpStatus exception
            E obj = (E) new AbstractResApiExternalDto();
            responseObj = obj;
            responseObj.setObjErrorsWithErrorMessage(DtsErrorMessageUtil.getErrorMessage(msg, "B100", lang, e.getMessage()));
        } catch (RestClientException e) {
            // Handle HttpStatus exception
            E obj = (E) new AbstractResApiExternalDto();
            responseObj = obj;
            responseObj.setObjErrorsWithErrorMessage(DtsErrorMessageUtil.getErrorMessage(msg, "B101", lang, e.getMessage()));
        } catch (Exception e) {
         // Handle HttpStatus exception
            E obj = (E) new AbstractResApiExternalDto();
            responseObj = obj;
            responseObj.setObjErrorsWithErrorMessage(DtsErrorMessageUtil.getErrorMessage(msg, "B102", lang, e.getMessage()));
        } finally {
            
        }
        
        return responseObj;
    }
    
    @Override
    public <T extends AbstractApiExternalDto, E extends AbstractResApiExternalDto> byte[] callAPIDownloadFile(T object,String url, Object requestObj) throws Exception {
        byte[] bytes = null;
        // PT Tracking - callAPI - Start
        DebugLogger debugLogger = new DebugLogger();
        DebugLogger.debug("[PT Tracking] | [callAPI] | [%d] | [Begin] | [%s] | [%d] | [%s]", Thread.currentThread().getId(), debugLogger.getStart(), 0, url);

        String requestJson = CommonStringUtil.EMPTY;
        try {
            requestJson = CommonJsonUtil.convertObjectToJSON(requestObj);
        } catch (JsonProcessingException e1) {
//            logger.error("[callAPI] Error: " + e1);
        }
        
        Integer timeoutSeconds = object.getTimeoutSeconds();
        
        // Create RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        if (timeoutSeconds == null) {
            requestFactory.setConnectTimeout(CommonConstant.API_EXTERNAL_TIMEOUT);
            requestFactory.setReadTimeout(CommonConstant.API_EXTERNAL_TIMEOUT);
        } else {
            requestFactory.setConnectTimeout(timeoutSeconds);
            requestFactory.setReadTimeout(timeoutSeconds);
        }
        restTemplate.setRequestFactory(requestFactory);      
        HttpHeaders headers = ((RESTApiDto) object).getHeaders();
        HttpMethod httpMethod = ((RESTApiDto) object).getHttpMethod();
        // Create HttpEntity
        if (headers == null) {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        }
        HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
        
        // Call API
        try {
            ResponseEntity<byte[]> result = restTemplate.exchange(url, httpMethod, entity, byte[].class);
            bytes = result.getBody();
            
        } catch (Exception e) {
            throw new Exception();
        }
        return bytes;
    }
    
    @Override
    public <T extends AbstractApiExternalDto, E extends AbstractResApiExternalDto> byte[] callAPIUploadFile(T object,String url, MultiValueMap<String, Object> requestObj) throws Exception {
        byte[] bytes = null;
        // PT Tracking - callAPI - Start
        DebugLogger debugLogger = new DebugLogger();
        DebugLogger.debug("[PT Tracking] | [callAPI] | [%d] | [Begin] | [%s] | [%d] | [%s]", Thread.currentThread().getId(), debugLogger.getStart(), 0, url);
        
        Integer timeoutSeconds = object.getTimeoutSeconds();
        
        // Create RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        if (timeoutSeconds == null) {
            requestFactory.setConnectTimeout(CommonConstant.API_EXTERNAL_TIMEOUT);
            requestFactory.setReadTimeout(CommonConstant.API_EXTERNAL_TIMEOUT);
        } else {
            requestFactory.setConnectTimeout(timeoutSeconds);
            requestFactory.setReadTimeout(timeoutSeconds);
        }
        restTemplate.setRequestFactory(requestFactory);      
        HttpHeaders headers = ((RESTApiDto) object).getHeaders();
        HttpMethod httpMethod = ((RESTApiDto) object).getHttpMethod();
        // Create HttpEntity
        if (headers == null) {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        }
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(requestObj, headers);
        
        // Call API
        try {
            ResponseEntity<byte[]> result = restTemplate.exchange(url, httpMethod, entity, byte[].class);
            bytes = result.getBody();
            
        } catch (Exception e) {
            throw new Exception();
        }
        return bytes;
    }

}
