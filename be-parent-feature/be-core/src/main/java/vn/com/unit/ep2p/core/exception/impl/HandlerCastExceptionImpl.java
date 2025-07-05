/*******************************************************************************
 * Class        ：HandlerCastExceptionImpl
 * Created date ：2020/12/08
 * Lasted date  ：2020/12/08
 * Author       ：taitt
 * Change log   ：2020/12/08：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.exception.impl;

import org.springframework.stereotype.Service;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.exception.HandlerCastException;

/**
 * HandlerCastExceptionImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
public class HandlerCastExceptionImpl implements HandlerCastException{


    @Override
    public void castException(Exception ex, String exceptionConstant)throws DetailException {
        if (ex instanceof DetailException) {
            DetailException detailException = (DetailException) ex;
            String exceptionErrorCode = detailException.getExceptionErrorCode();
            throw new DetailException(exceptionErrorCode,detailException.getParamater(),true);
        } else {
            throw new DetailException(exceptionConstant, true);
        }
    }
    
    @Override
    public void castException(Exception ex, String exceptionConstant,String[] param)throws DetailException {
        if (ex instanceof DetailException) {
            DetailException detailException = (DetailException) ex;
            String exceptionErrorCode = detailException.getExceptionErrorCode();
            throw new DetailException(exceptionErrorCode,detailException.getParamater(),true);
        } else {
            throw new DetailException(exceptionConstant, param, true);
        }
    }

}
