/*******************************************************************************
 * Class        ：JpmSvcBoardService
 * Created date ：2021/01/27
 * Lasted date  ：2021/01/27
 * Author       ：taitt
 * Change log   ：2021/01/27：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service;

import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.ep2p.dto.res.JpmSvcBoardRes;

/**
 * JpmSvcBoardService
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface JpmSvcBoardService {

    /**
     * svcBoardList
     * @param requestParams
     * @param pageable
     * @return
     * @author taitt
     */
    ObjectDataRes<JpmSvcBoardRes> svcBoardList(MultiValueMap<String, String> requestParams, Pageable pageable) throws Exception;

}
