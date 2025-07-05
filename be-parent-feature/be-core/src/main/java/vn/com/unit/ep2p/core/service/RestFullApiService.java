/*******************************************************************************
 * Class        ：RestFullApiService
 * Created date ：2021/02/02
 * Lasted date  ：2021/02/02
 * Author       ：taitt
 * Change log   ：2021/02/02：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.service;

import org.springframework.util.MultiValueMap;

import vn.com.unit.dts.api.dto.AbstractResApiExternalDto;

/**
 * RestFullApiService
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface RestFullApiService {

    /**
     * uploadRestFull
     * @param url
     * @param requestObj
     * @return
     * @throws Exception
     * @author taitt
     */
    byte[] uploadRestFull(String url, MultiValueMap<String, Object> requestObj) throws Exception;

    /**
     * restFull
     * @param <E>
     * @param url
     * @param requestObj
     * @param responseClass
     * @return
     * @throws Exception
     * @author taitt
     */
    <E extends AbstractResApiExternalDto> E restFull(String url, Object requestObj, Class<E> responseClass) throws Exception;

}
