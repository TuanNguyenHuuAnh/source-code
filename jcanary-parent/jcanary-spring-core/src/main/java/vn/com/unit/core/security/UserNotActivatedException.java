/*******************************************************************************
 * Class        ：UserNotActivatedException
 * Created date ：2020/12/03
 * Lasted date  ：2020/12/03
 * Author       ：KhoaNA
 * Change log   ：2020/12/03：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.core.security;

import org.springframework.security.core.AuthenticationException;

/**
 * UserNotActivatedException
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class UserNotActivatedException extends AuthenticationException {

    /** serialVersionUID */
    private static final long serialVersionUID = 388736016302681041L;

    public UserNotActivatedException(String message) {
        super(message);
    }

    public UserNotActivatedException(String message, Throwable t) {
        super(message, t);
    }
}
