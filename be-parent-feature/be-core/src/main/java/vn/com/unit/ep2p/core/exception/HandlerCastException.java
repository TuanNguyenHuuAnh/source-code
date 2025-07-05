/*******************************************************************************
 * Class        ：HandlerCastException
 * Created date ：2020/12/08
 * Lasted date  ：2020/12/08
 * Author       ：taitt
 * Change log   ：2020/12/08：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.exception;

import vn.com.unit.dts.exception.DetailException;

/**
 * HandlerCastException
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface HandlerCastException {

    public void castException(Exception ex, String exceptionConstant)throws DetailException;

    /**
     * castException
     * @param ex
     * @param exceptionConstant
     * @param param
     * @throws DetailException
     * @author taitt
     */
    void castException(Exception ex, String exceptionConstant, String[] param) throws DetailException;
}
