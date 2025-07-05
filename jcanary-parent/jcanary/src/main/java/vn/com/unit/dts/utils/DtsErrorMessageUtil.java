/*******************************************************************************
 * Class        ：DtsErrorMessageUtil
 * Created date ：2020/11/23
 * Lasted date  ：2020/11/23
 * Author       ：taitt
 * Change log   ：2020/11/23：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.dts.utils;

import java.util.Locale;

import org.springframework.context.MessageSource;

import vn.com.unit.dts.api.dto.ApiExternalErrorMessage;

/**
 * DtsErrorMessageUtil
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class DtsErrorMessageUtil {

    
    public static ApiExternalErrorMessage getErrorMessage(MessageSource messageSource, String errorCode, Locale locale) {
        String msg = messageSource.getMessage(errorCode, null, locale);
        return new ApiExternalErrorMessage(errorCode, msg);
    }
    
    public static ApiExternalErrorMessage getErrorMessage(MessageSource messageSource, String errorCode, String lang) {
        String msg = messageSource.getMessage(errorCode, null, new Locale(lang));
        return new ApiExternalErrorMessage(errorCode, msg);
    }
    
    public static ApiExternalErrorMessage getErrorMessage(MessageSource messageSource, String errorCode, String message, Locale locale) {
        String msg = DtsStringUtil.EMPTY;
        if (DtsStringUtil.isNotBlank(message)) {
            msg = message;
        } else {
            msg = messageSource.getMessage(errorCode, null, locale);
        }
        return new ApiExternalErrorMessage(errorCode, msg);
    }
    
    public static ApiExternalErrorMessage getErrorMessage(MessageSource messageSource, String errorCode, String message, String lang) {
        String msg = DtsStringUtil.EMPTY;
        if (DtsStringUtil.isNotBlank(message)) {
            msg = message;
        } else {
            msg = messageSource.getMessage(errorCode, null, new Locale(lang));
        }
        return new ApiExternalErrorMessage(errorCode, msg);
    }
    
    public static ApiExternalErrorMessage getErrorMessage(MessageSource messageSource, String errorCode, Object[] args, Locale locale) {
        String msg = messageSource.getMessage(errorCode, args, locale);
        return new ApiExternalErrorMessage(errorCode, msg);
    }
    
    public static ApiExternalErrorMessage getErrorMessage(MessageSource messageSource, String errorCode, Object[] args, String lang) {
        String msg = messageSource.getMessage(errorCode, args, new Locale(lang));
        return new ApiExternalErrorMessage(errorCode, msg);
    }
    
    public static ApiExternalErrorMessage getErrorMessage(MessageSource messageSource, String errorCode, Object[] args, String message,
            Locale locale) {
        String msg = DtsStringUtil.EMPTY;
        if (DtsStringUtil.isNotBlank(message)) {
            msg = message;
        } else {
            msg = messageSource.getMessage(errorCode, args, locale);
        }
        return new ApiExternalErrorMessage(errorCode, msg);
    }

    public static ApiExternalErrorMessage getErrorMessage(MessageSource messageSource, String errorCode, Object[] args, String message, String lang) {
        String msg = DtsStringUtil.EMPTY;
        if (DtsStringUtil.isNotBlank(message)) {
            msg = message;
        } else {
            msg = messageSource.getMessage(errorCode, args, new Locale(lang));
        }
        return new ApiExternalErrorMessage(errorCode, msg);
    }
    
    public static ApiExternalErrorMessage getErrorMessage(String msg, String errorCode) {
        return new ApiExternalErrorMessage(errorCode, msg);
    }
    
    public static String getMessage(MessageSource messageSource, String code, Locale locale) {
        String msg = messageSource.getMessage(code, null, locale);
        return msg;
    }
    
    public static String getMessage(MessageSource messageSource, String code, String lang) {
        String msg = messageSource.getMessage(code, null, new Locale(lang));
        return msg;
    }
    
    public static String getMessage(MessageSource messageSource, String code, Object[] args, Locale locale) {
        String msg = messageSource.getMessage(code, args, locale);
        return msg;
    }
    
    public static String getMessage(MessageSource messageSource, String code, Object[] args, String lang) {
        String msg = messageSource.getMessage(code, args, new Locale(lang));
        return msg;
    }
}
