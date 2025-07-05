/*******************************************************************************
 * Class        ：InvalidAccessException
 * Created date ：2019/07/24
 * Lasted date  ：2019/07/24
 * Author       ：KhoaNA
 * Change log   ：2019/07/24：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.common.exception;

/**
 * Throw error invalidAccess.
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class InvalidAccessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidAccessException(String message) {
        super(message);
    }

    public InvalidAccessException(String message, Throwable t) {
        super(message, t);
    }

}
