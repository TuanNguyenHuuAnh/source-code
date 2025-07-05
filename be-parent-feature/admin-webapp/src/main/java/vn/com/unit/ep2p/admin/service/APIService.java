/*******************************************************************************
 * Class        ：APIService
 * Created date ：2019/04/18
 * Lasted date  ：2019/04/18
 * Author       ：HungHT
 * Change log   ：2019/04/18：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.exception.AppException;
import vn.com.unit.core.res.ResAPI;



/**
 * APIService
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public interface APIService {
    
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
    public <T extends ResAPI> T callAPI(String url, HttpMethod httpMethod, HttpHeaders headers, Integer timeoutSeconds,
            Object requestObj, Class<T> responseClass);
    
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
    public byte[] callAPIDownloadFile(String url, HttpMethod httpMethod, HttpHeaders headers, Integer timeoutSeconds, Object requestObj)
            throws AppException;  
    
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
    public byte[] callAPIUploadFile(String url, HttpMethod httpMethod, HttpHeaders headers, Integer timeoutSeconds, MultiValueMap<String, Object> requestObj)
            throws AppException;  
    
    /**
     * callAPIMultipartForm
     * @param url
     * @param httpMethod
     * @param headers
     * @param timeoutSeconds
     * @param requestObj
     * @param responseClass
     * @return
     * @author KhuongTH
     */
    <T extends ResAPI> T callAPIMultipartForm(String url, HttpMethod httpMethod, HttpHeaders headers, Integer timeoutSeconds,
            MultiValueMap<String, Object> requestObj, Class<T> responseClass);
}
