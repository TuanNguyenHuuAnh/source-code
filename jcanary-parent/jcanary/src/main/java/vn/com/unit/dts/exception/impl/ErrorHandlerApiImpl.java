package vn.com.unit.dts.exception.impl;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import vn.com.unit.dts.constant.DtsConstant;
import vn.com.unit.dts.constant.DtsExceptionCodeConstant;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.dts.exception.ErrorHandler;
import vn.com.unit.dts.exception.ExceptionCode;
import vn.com.unit.dts.exception.GlobalException;
import vn.com.unit.dts.exception.MessageError;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ies.logstash.LogstashDto;
import vn.com.unit.ies.logstash.LogstashService;

/**
 * @Last updated: 22/03/2024	nt.tinh SR16136 - Fix lỗi phát hiện trong quá trình Pentest - 2023
 */
public class ErrorHandlerApiImpl implements ErrorHandler {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private LogstashService logstashService;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Deprecated // 2021-06-18 - LocLT - Write Logstash
	public DtsApiResponse handlerException(Exception ex, long start) {
		return this.handlerException(ex, start, null, null);
	}
	
	// handlerException
	@Override
	public DtsApiResponse handlerException(Exception ex, long start, Object transactionId) {
		String transactionIdStr = "";
		if (transactionId != null) {
			transactionIdStr = String.valueOf(transactionId);
		}
		return handlerException(ex, start, transactionIdStr,"");
	}
	
	
	// 2021-06-18 - LocLT - Write Logstash
	@Override
	public DtsApiResponse handlerException(Exception ex, long start, String transactionId, String others) {
		// Default message
		long took = System.currentTimeMillis() - start;
		ExceptionCode expCode = new ExceptionCode(DtsExceptionCodeConstant.E500_ERROR_INTERNAL);
		boolean isTranslate = false;
		String message = ex.getMessage();
		String hiddenDesc = Strings.EMPTY;
		Object[] paramater = null;

		if (ex instanceof NullPointerException) {
			hiddenDesc = "NPE";
		} else if (ex instanceof GlobalException) {
			GlobalException globalException = (GlobalException) ex;
			expCode = globalException.getExceptionCode();
			if (ex instanceof DetailException) {
				DetailException detailException = (DetailException) globalException;
				isTranslate = detailException.isTranslate();
				message = detailException.getSpecificMsg();
				hiddenDesc = detailException.getSpecificMsg();
				paramater = detailException.getParamater();
			}
        } else if (ex instanceof UsernameNotFoundException) { // xử lý phần login cho API
            message = this.messageSource.getMessage(ex.getMessage(), paramater,
                    new Locale(Optional.ofNullable(this.request.getHeader("Accept-Language")).orElse("en")));
        } else if (ex instanceof SQLException || ex.getClass().getSimpleName().equalsIgnoreCase("SQLRuntimeException") || ex.getMessage().equalsIgnoreCase("java.sql.SQLException")
        		 || ex.getMessage().indexOf("java.sql.SQLException") > -1) {
			message = "Hệ thống đang gặp sự cố kết nối, Bạn vui lòng đăng nhập lại sau!";
			logger.error("SQL_DATA_EXCEPTION", ex);
		}
        else if (ex.getClass().getSimpleName().equalsIgnoreCase("DataIntegrityViolationException")
        		|| ex.getClass().getSimpleName().equalsIgnoreCase("NoSuchFileException")
        		|| ex.getClass().getSimpleName().equalsIgnoreCase("BadSqlGrammarException")) {
			message = "Có lỗi xảy ra, bạn vui lòng thực hiện lại sau!";
		}

		String code = expCode.getText();

		if (isTranslate) {
			message = this.messageSource.getMessage(code, paramater,
					new Locale(Optional.ofNullable(this.request.getHeader("Accept-Language")).orElse("en")));
		}

		if (Strings.isBlank(message)) {
			message = code;
		}

		int codeStatus = expCode.getValue();
		DtsApiResponse response = new DtsApiResponse(expCode.getValue(), DtsConstant.ERROR, message, hiddenDesc, took);

		// 2021-06-18 - LocLT - Write Logstash
		LogstashDto logstash = new LogstashDto(ex, request, "ERROR", response, codeStatus, message, transactionId, took,
				others);

		logstashService.writeLogstash(logstash);
		// 2021-06-18 - LocLT - Write Logstash

		return response;
	}

	@Deprecated // 2021-06-18 - LocLT - Write Logstash
	public DtsApiResponse handlerException(int codeStatus, String message) {
		return this.handlerException(codeStatus, message, null, 0, null);
	}

	// 2021-06-18 - LocLT - Write Logstash
	@Override
	public DtsApiResponse handlerException(int codeStatus, String message, String transactionId, long start,
			String others) {
		long took = System.currentTimeMillis() - start;

		boolean isTranslate = false;

		if (isTranslate) {
			message = this.messageSource.getMessage(MessageError.ERROR_COMMON, null,
					new Locale(Optional.ofNullable(this.request.getHeader("Accept-Language")).orElse("en")));
		}

		DtsApiResponse response = new DtsApiResponse(codeStatus, message, null, null);

		// 2021-06-18 - LocLT - Write Logstash
		LogstashDto logstash = new LogstashDto(null, request, "ERROR", response, codeStatus, message, transactionId,
				took, others);

		logstashService.writeLogstash(logstash);
		// 2021-06-18 - LocLT - Write Logstash

		return response;
	}
}
