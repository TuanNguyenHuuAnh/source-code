/*******************************************************************************
 * Class        ：ApiExternalService
 * Created date ：2020/11/20
 * Lasted date  ：2020/11/20
 * Author       ：taitt
 * Change log   ：2020/11/20：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.common.api.service;

import org.springframework.util.MultiValueMap;

import vn.com.unit.dts.api.dto.AbstractApiExternalDto;
import vn.com.unit.dts.api.dto.AbstractResApiExternalDto;

/**
 * ApiExternalService
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface ApiExternalService {

    <T extends AbstractApiExternalDto, E extends AbstractResApiExternalDto> E callAPI(T apiDto,String url,
            Object requestObj, Class<E> responseClass,String protocol) throws Exception;
    
    <T extends AbstractApiExternalDto, E extends AbstractResApiExternalDto> byte[] callAPIDownload(T apiDto,String url,
            Object requestObj,String protocol) throws Exception;
    
    <T extends AbstractApiExternalDto, E extends AbstractResApiExternalDto> byte[]callAPIUpload(T apiDto,String url,
            MultiValueMap<String, Object> requestObj,String protocol) throws Exception;
}
