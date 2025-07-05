/*******************************************************************************
 * Class        ：ExecMessage
 * Created date ：2019/08/17
 * Lasted date  ：2019/08/17
 * Author       ：HungHT
 * Change log   ：2019/08/17：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.utils;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;

import vn.com.unit.core.dto.ErrorMessage;



/**
 * ExecMessage
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class ExecMessage {

    /**
     * getErrorMessage
     * 
     * @param messageSource
     * @param errorCode
     * @param locale
     * @return
     * @author HungHT
     */
    public static ErrorMessage getErrorMessage(MessageSource messageSource, String errorCode, Locale locale) {
        String msg = messageSource.getMessage(errorCode, null, locale);
        return new ErrorMessage(errorCode, msg);
    }

    /**
     * getErrorMessage
     * 
     * @param messageSource
     * @param errorCode
     * @param lang
     * @return
     * @author HungHT
     */
    public static ErrorMessage getErrorMessage(MessageSource messageSource, String errorCode, String lang) {
        String msg = messageSource.getMessage(errorCode, null, new Locale(lang));
        return new ErrorMessage(errorCode, msg);
    }

    /**
     * getErrorMessage
     * 
     * @param messageSource
     * @param errorCode
     * @param message
     * @param locale
     * @return
     * @author HungHT
     */
    public static ErrorMessage getErrorMessage(MessageSource messageSource, String errorCode, String message, Locale locale) {
        String msg = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(message)) {
            msg = message;
        } else {
            msg = messageSource.getMessage(errorCode, null, locale);
        }
        return new ErrorMessage(errorCode, msg);
    }

    /**
     * getErrorMessage
     * 
     * @param messageSource
     * @param errorCode
     * @param message
     * @param lang
     * @return
     * @author HungHT
     */
    public static ErrorMessage getErrorMessage(MessageSource messageSource, String errorCode, String message, String lang) {
        String msg = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(message)) {
            msg = message;
        } else {
            msg = messageSource.getMessage(errorCode, null, new Locale(lang));
        }
        return new ErrorMessage(errorCode, msg);
    }

    /**
     * getErrorMessage
     * 
     * @param messageSource
     * @param errorCode
     * @param args
     * @param locale
     * @return
     * @author HungHT
     */
    public static ErrorMessage getErrorMessage(MessageSource messageSource, String errorCode, Object[] args, Locale locale) {
        String msg = messageSource.getMessage(errorCode, args, locale);
        return new ErrorMessage(errorCode, msg);
    }

    /**
     * getErrorMessage
     * 
     * @param messageSource
     * @param errorCode
     * @param args
     * @param lang
     * @return
     * @author HungHT
     */
    public static ErrorMessage getErrorMessage(MessageSource messageSource, String errorCode, Object[] args, String lang) {
        String msg = messageSource.getMessage(errorCode, args, new Locale(lang));
        return new ErrorMessage(errorCode, msg);
    }

    /**
     * getErrorMessage
     * 
     * @param messageSource
     * @param errorCode
     * @param args
     * @param message
     * @param locale
     * @return
     * @author HungHT
     */
    public static ErrorMessage getErrorMessage(MessageSource messageSource, String errorCode, Object[] args, String message,
            Locale locale) {
        String msg = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(message)) {
            msg = message;
        } else {
            msg = messageSource.getMessage(errorCode, args, locale);
        }
        return new ErrorMessage(errorCode, msg);
    }

    /**
     * getErrorMessage
     * 
     * @param messageSource
     * @param errorCode
     * @param args
     * @param message
     * @param lang
     * @return
     * @author HungHT
     */
    public static ErrorMessage getErrorMessage(MessageSource messageSource, String errorCode, Object[] args, String message, String lang) {
        String msg = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(message)) {
            msg = message;
        } else {
            msg = messageSource.getMessage(errorCode, args, new Locale(lang));
        }
        return new ErrorMessage(errorCode, msg);
    }
    
    /**
     * getErrorMessage
     * 
     * @param msg
     * @param errorCode
     * @return
     * @author HungHT
     */
    public static ErrorMessage getErrorMessage(String msg, String errorCode) {
        return new ErrorMessage(errorCode, msg);
    }
    
    /**
     * getMessage
     * 
     * @param messageSource
     * @param code
     * @param locale
     * @return
     * @author HungHT
     */
    public static String getMessage(MessageSource messageSource, String code, Locale locale) {
        String msg = messageSource.getMessage(code, null, locale);
        return msg;
    }
    
    /**
     * getMessage
     * 
     * @param messageSource
     * @param code
     * @param lang
     * @return
     * @author HungHT
     */
    public static String getMessage(MessageSource messageSource, String code, String lang) {
        String msg = messageSource.getMessage(code, null, new Locale(lang));
        return msg;
    }
    
    /**
     * getMessage
     * 
     * @param messageSource
     * @param code
     * @param args
     * @param locale
     * @return
     * @author HungHT
     */
    public static String getMessage(MessageSource messageSource, String code, Object[] args, Locale locale) {
        String msg = messageSource.getMessage(code, args, locale);
        return msg;
    }

    /**
     * getMessage
     * 
     * @param messageSource
     * @param code
     * @param args
     * @param lang
     * @return
     * @author HungHT
     */
    public static String getMessage(MessageSource messageSource, String code, Object[] args, String lang) {
        String msg = messageSource.getMessage(code, args, new Locale(lang));
        return msg;
    }
}
