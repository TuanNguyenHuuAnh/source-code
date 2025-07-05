/*******************************************************************************
 * Class        ：AppException
 * Created date ：2020/07/28
 * Lasted date  ：2020/07/28
 * Author       ：HungHT
 * Change log   ：2020/07/28：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.common.exception;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.dts.exception.CoreException;


/**
 * AppException
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Getter
@Setter
@SuppressWarnings("serial")
public class AppException extends CoreException {

    /** code */
    private final String code;

    /** args */
    private final Object[] args;

    public AppException(String message, Throwable cause, String code, Object[] args) {
        super(message, cause);
        this.code = code;
        this.args = args;
    }

    public AppException(String message, String code, Object[] args) {
        super(message);
        this.code = code;
        this.args = args;
    }

    public AppException(Throwable cause, String code, Object[] args) {
        super(cause);
        this.code = code;
        this.args = args;
    }
    
    public AppException(String code, Object[] args) {
        super(code, args);
        this.code = code;
        this.args = args;
    }
}
