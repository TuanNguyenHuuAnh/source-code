package vn.com.unit.core.exception;

/**
 * Throw error business.
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class BusinessException extends RuntimeException {

    /** Serial version.*/
    private static final long serialVersionUID = -7664370531422975930L;

    /**
     * Exception with message and throwable.
     * 
     * @param message
     *          type String
     * @param cause
     *          type Throwable
     * @author KhoaNA
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception with only message.
     * 
     * @param message
     *          type String
     * @author KhoaNA
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * Exception with only throwable.
     * 
     * @param cause
     *          type Throwable
     * @author KhoaNA
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }
}