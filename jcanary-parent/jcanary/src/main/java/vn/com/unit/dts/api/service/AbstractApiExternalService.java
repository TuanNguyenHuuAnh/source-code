/*******************************************************************************
 * Class        ：AbstractApiExternalService
 * Created date ：2020/11/20
 * Lasted date  ：2020/11/20
 * Author       ：taitt
 * Change log   ：2020/11/20：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.dts.api.service;

import org.springframework.util.MultiValueMap;

import vn.com.unit.dts.api.dto.AbstractApiExternalDto;
import vn.com.unit.dts.api.dto.AbstractResApiExternalDto;

/**
 * AbstractApiExternalService
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public abstract interface AbstractApiExternalService{

    public <T extends AbstractApiExternalDto, E extends AbstractResApiExternalDto> E callAPI(T object, String url,
            Object requestObj, Class<E> responseClass) throws Exception;

    /**
     * callAPIDownloadFile
     * @param <T>
     * @param <E>
     * @param object
     * @param url
     * @param requestObj
     * @param responseClass
     * @return
     * @throws Exception
     * @author taitt
     */
    <T extends AbstractApiExternalDto, E extends AbstractResApiExternalDto> byte[] callAPIDownloadFile(T object, String url,
            Object requestObj) throws Exception;

    /**
     * callAPIUploadFile
     * @param <T>
     * @param <E>
     * @param object
     * @param url
     * @param requestObj
     * @param responseClass
     * @return
     * @throws Exception
     * @author taitt
     */
    <T extends AbstractApiExternalDto, E extends AbstractResApiExternalDto> byte[] callAPIUploadFile(T object, String url,
            MultiValueMap<String, Object> requestObj) throws Exception;
}
