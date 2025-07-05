/*******************************************************************************
 * Class        ：ResObjectService
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：taitt
 * Change log   ：2020/12/01：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.Collection;

import vn.com.unit.core.res.ObjectDataRes;

/**
 * ResObjectService
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface ResObjectService {

    /**
     * responseObjectMapAll
     * @param <T>
     * @param <S>
     * @param datas
     * @param totalData
     * @return
     * @author taitt
     * @throws Exception 
     */
    <T extends ObjectDataRes, S> T responseObjectMapAll(Class<T> classT, Collection<S> datas, int totalData) throws Exception;

}
