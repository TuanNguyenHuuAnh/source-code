/*******************************************************************************
 * Class        ：CoreException
 * Created date ：2020/11/10
 * Lasted date  ：2020/11/10
 * Author       ：taitt
 * Change log   ：2020/11/10：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.dts.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * CoreException
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class CoreException extends Exception{

    /** serialVersionUID */
	private static final long serialVersionUID = 6588040559805256124L;

	public CoreException(String code, Object[] args) {
        super();
    }

    public CoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoreException(String message) {
        super(message);
    }

    public CoreException(Throwable cause) {
        super(cause);
    }
}
