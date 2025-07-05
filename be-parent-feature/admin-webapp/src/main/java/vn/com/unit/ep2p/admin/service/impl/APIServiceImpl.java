package vn.com.unit.ep2p.admin.service.impl;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import vn.com.unit.common.exception.AppException;
import vn.com.unit.common.logger.DebugLogger;
import vn.com.unit.core.res.ResAPI;
import vn.com.unit.dts.api.enumdef.APIStatus;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.service.APIService;
import vn.com.unit.ep2p.core.annotation.NoLogging;
import vn.com.unit.ep2p.utils.ExecMessage;
import vn.com.unit.ep2p.utils.JsonUtils;


/**
 * APIServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@NoLogging
@Service
public class APIServiceImpl implements APIService {

    /** msg */
    @Autowired
    MessageSource msg;

    /** systemConfig */
    @Autowired
    SystemConfig systemConfig;

    /** TIMEOUT */
    private static final int TIMEOUT = 720000;

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(APIServiceImpl.class);

    /**
     * callAPI
     * 
     * @param <T>
     * @param url
     * @param httpMethod
     * @param headers
     * @param timeoutSeconds
     * @param requestObj
     * @param responseClass
     * @return
     * @author HungHT
     */
    @NoLogging
    @SuppressWarnings("unchecked")
    public <T extends ResAPI> T callAPI(String url, HttpMethod httpMethod, HttpHeaders headers, Integer timeoutSeconds, Object requestObj,
            Class<T> responseClass) {
        // PT Tracking - callAPI - Start
        DebugLogger debugLogger = new DebugLogger();
        DebugLogger.debug("[PT Tracking] | [callAPI] | [%d] | [Begin] | [%s] | [%d] | [%s]", Thread.currentThread().getId(), debugLogger.getStart(), 0, url);
        
        String lang = systemConfig.getConfig(SystemConfig.LANGUAGE_DEFAULT);
        T responseObj = null;
        String requestJson = StringUtils.EMPTY;
        try {
            requestJson = JsonUtils.convertObjectToJSON(requestObj);
        } catch (Exception e1) {
            logger.error("[callAPI] Error: ", e1);
        }

        // Create RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        if (timeoutSeconds == null) {
            requestFactory.setConnectTimeout(TIMEOUT);
            requestFactory.setReadTimeout(TIMEOUT);
        } else {
            requestFactory.setConnectTimeout(timeoutSeconds);
            requestFactory.setReadTimeout(timeoutSeconds);
        }
        restTemplate.setRequestFactory(requestFactory);

        // Create HttpEntity
        if (headers == null) {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        // Call API
        try {
            ResponseEntity<T> result = restTemplate.exchange(url, httpMethod, entity, responseClass);
            responseObj = result.getBody();
            if (responseObj == null) {
                T obj = (T) new ResAPI();
                responseObj = obj;
            }
            if (StringUtils.isBlank(responseObj.getStatus())) {
                responseObj.setStatus(APIStatus.SUCCESS.toString());
            }
        } catch (HttpStatusCodeException e) {
            // Handle HttpStatus exception
            T obj = (T) new ResAPI();
            responseObj = obj;
            responseObj.setObjErrorsWithErrorMessage(ExecMessage.getErrorMessage(msg, "B100", lang, e.getMessage()));
        } catch (RestClientException e) {
            // Handle RestClient exception
            T obj = (T) new ResAPI();
            responseObj = obj;
            responseObj.setObjErrorsWithErrorMessage(ExecMessage.getErrorMessage(msg, "B101", lang, e.getMessage()));
        } catch (Exception e) {
            // Handle RestClient exception
            T obj = (T) new ResAPI();
            responseObj = obj;
            responseObj.setObjErrorsWithErrorMessage(ExecMessage.getErrorMessage(msg, "B102", lang, e.getMessage()));
        } finally {
            // PT Tracking - callAPI - End
            debugLogger.setEndTime();
            DebugLogger.debug("[PT Tracking] | [callAPI] | [%d] | [End] | [%s] | [%d] | [%s]", Thread.currentThread().getId(), debugLogger.getEnd(), debugLogger.getElapsedTime(), url);
        }
        return responseObj;
    }

    /**
     * callAPIDownloadFile
     * 
     * @param url
     * @param httpMethod
     * @param headers
     * @param timeoutSeconds
     * @param requestObj
     * @return
     * @throws AppException
     * @author HungHT
     */
    @NoLogging
    public byte[] callAPIDownloadFile(String url, HttpMethod httpMethod, HttpHeaders headers, Integer timeoutSeconds, Object requestObj)
            throws AppException {
        // PT Tracking - callAPIDownloadFile - Start
        DebugLogger debugLogger = new DebugLogger();
        DebugLogger.debug("[PT Tracking] | [callAPIDownloadFile] | [%d] | [Begin] | [%s] | [%d] | [%s]", Thread.currentThread().getId(), debugLogger.getStart(), 0, url);
        
        String requestJson = StringUtils.EMPTY;
        try {
            requestJson = JsonUtils.convertObjectToJSON(requestObj);
        } catch (Exception e1) {
            logger.error("[callAPIDownloadFile] Error: ",e1);
        }

        // Create RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        if (timeoutSeconds == null) {
            requestFactory.setConnectTimeout(TIMEOUT);
            requestFactory.setReadTimeout(TIMEOUT);
        } else {
            requestFactory.setConnectTimeout(timeoutSeconds);
            requestFactory.setReadTimeout(timeoutSeconds);
        }
        restTemplate.setRequestFactory(requestFactory);

        // Create HttpEntity
        if (headers == null) {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        }
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        // Call API
        try {
            ResponseEntity<byte[]> result = restTemplate.exchange(url, httpMethod, entity, byte[].class);
            return result.getBody();
        } catch (Exception e) {
            throw new AppException(e.getCause(), "", null);
        } finally {
            // PT Tracking - callAPIDownloadFile - End
            debugLogger.setEndTime();
            DebugLogger.debug("[PT Tracking] | [callAPIDownloadFile] | [%d] | [End] | [%s] | [%d] | [%s]", Thread.currentThread().getId(), debugLogger.getEnd(), debugLogger.getElapsedTime(), url);
        }
    }

    /**
     * callAPIUploadFile
     * 
     * @param url
     * @param httpMethod
     * @param headers
     * @param timeoutSeconds
     * @param requestObj
     * @return
     * @throws AppException
     * @author KhuongTH
     */
    @NoLogging
    @Override
    public byte[] callAPIUploadFile(String url, HttpMethod httpMethod, HttpHeaders headers, Integer timeoutSeconds, MultiValueMap<String, Object> requestObj)
            throws AppException {
        // PT Tracking - callAPIUploadFile - Start
        DebugLogger debugLogger = new DebugLogger();
        DebugLogger.debug("[PT Tracking] | [callAPIUploadFile] | [%d] | [Begin] | [%s] | [%d] | [%s]", Thread.currentThread().getId(), debugLogger.getStart(), 0, url);
        
        // Create RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        if (timeoutSeconds == null) {
            requestFactory.setConnectTimeout(TIMEOUT);
            requestFactory.setReadTimeout(TIMEOUT);
        } else {
            requestFactory.setConnectTimeout(timeoutSeconds);
            requestFactory.setReadTimeout(timeoutSeconds);
        }
        restTemplate.setRequestFactory(requestFactory);

        // Create HttpEntity
        if (headers == null) {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        }
        
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(requestObj, headers);

        // Call API
        try {
            ResponseEntity<byte[]> result = restTemplate.exchange(url, httpMethod, entity, byte[].class);
            return result.getBody();
        } catch (Exception e) {
            throw new AppException(e.getCause(), "", null);
        } finally {
            // PT Tracking - callAPIUploadFile - End
            debugLogger.setEndTime();
            DebugLogger.debug("[PT Tracking] | [callAPIUploadFile] | [%d] | [End] | [%s] | [%d] | [%s]", Thread.currentThread().getId(), debugLogger.getEnd(), debugLogger.getElapsedTime(), url);
        }
    }

    @SuppressWarnings("unchecked")
	@NoLogging
    @Override
    public <T extends ResAPI> T callAPIMultipartForm(String url, HttpMethod httpMethod, HttpHeaders headers, Integer timeoutSeconds,
            MultiValueMap<String, Object> requestObj, Class<T> responseClass) {
        // PT Tracking - callAPIMultipartForm - Start
        DebugLogger debugLogger = new DebugLogger();
        DebugLogger.debug("[PT Tracking] | [callAPIMultipartForm] | [%d] | [Begin] | [%s] | [%d] | [%s]", Thread.currentThread().getId(), debugLogger.getStart(), 0, url);
        
        T responseObj = null;
        // Create RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        if (timeoutSeconds == null) {
            requestFactory.setConnectTimeout(TIMEOUT);
            requestFactory.setReadTimeout(TIMEOUT);
        } else {
            requestFactory.setConnectTimeout(timeoutSeconds);
            requestFactory.setReadTimeout(timeoutSeconds);
        }
        restTemplate.setRequestFactory(requestFactory);

        // Create HttpEntity
        if (headers == null) {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        }
        
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(requestObj, headers);

        // Call API
        try {
            ResponseEntity<T> result = restTemplate.exchange(url, httpMethod, entity, responseClass);
            responseObj = result.getBody();
            if (responseObj == null) {
                T obj = (T) new ResAPI();
                responseObj = obj;
            }
            if (StringUtils.isBlank(responseObj.getStatus())) {
                responseObj.setStatus(APIStatus.SUCCESS.toString());
            }
        } catch (Exception e) {
        	responseObj = (T)new ResAPI();
            responseObj.setStatus(APIStatus.FAIL.toString());
        } finally {
            // PT Tracking - callAPIMultipartForm - End
            debugLogger.setEndTime();
            DebugLogger.debug("[PT Tracking] | [callAPIMultipartForm] | [%d] | [End] | [%s] | [%d] | [%s]", Thread.currentThread().getId(), debugLogger.getEnd(), debugLogger.getElapsedTime(), url);
        }
        return responseObj;
    }
}
