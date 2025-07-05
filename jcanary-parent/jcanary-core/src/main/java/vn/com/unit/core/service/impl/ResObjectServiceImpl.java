/*******************************************************************************
 * Class        ：ResObjectServiceImpl
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：taitt
 * Change log   ：2020/12/01：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.common.utils.CommonObjectMapperUtil;
import vn.com.unit.core.constant.CoreExceptionCodeConstant;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.ResObjectService;
import vn.com.unit.dts.exception.DetailException;

/**
 * ResObjectServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ResObjectServiceImpl implements ResObjectService{

    @Override
    public <T extends ObjectDataRes,S> T responseObjectMapAll(Class<T> classT,final Collection<S> datas, int totalData) throws Exception {
        T objReponse = classT.getDeclaredConstructor().newInstance();
        try {
            List<Object> datasObj = CommonObjectMapperUtil.mapAll(datas, Object.class);
            objReponse.setDatas(datasObj);
            objReponse.setTotalData(totalData);
        }catch (Exception e) {
           throw new DetailException(CoreExceptionCodeConstant.E301701_CORE_MODEL_MAPPER_ERROR,new String[] {CommonJsonUtil.convertObjectToJSON(datas)},true);
        }
        
        return objReponse;
    }
    
}
