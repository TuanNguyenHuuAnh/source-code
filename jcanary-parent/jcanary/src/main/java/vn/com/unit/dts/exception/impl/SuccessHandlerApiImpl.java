package vn.com.unit.dts.exception.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;

import vn.com.unit.dts.constant.DtsConstant;
import vn.com.unit.dts.exception.SuccessHandler;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ies.logstash.LogstashDto;
import vn.com.unit.ies.logstash.LogstashService;

public class SuccessHandlerApiImpl implements SuccessHandler {

	@Autowired
	private LogstashService logstashService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Deprecated
    public DtsApiResponse handlerSuccess(Object data, long start) {
        return handlerSuccess(data, start, null, null);
    }

	// 2021-06-28 - TriTV - Write Logstash
	@Override
    public DtsApiResponse handlerSuccess(Object data, long start, Object transactionId) {
		String transactionIdStr = "";
		if (transactionId != null) {
			transactionIdStr = String.valueOf(transactionId);
		}
		return handlerSuccess(data, start, transactionIdStr, "");
	}
	
    // 2021-06-18 - LocLT - Write Logstash
	@Override
    public DtsApiResponse handlerSuccess(Object data, long start, String transactionId, String others) {
        long took = System.currentTimeMillis() - start;
        
        int codeStatus = DtsConstant.SUCCESS_CODE;
        String message = DtsConstant.SUCCESS;
        
        DtsApiResponse response = new DtsApiResponse(codeStatus, message, Strings.EMPTY, took, data);

		// 2021-06-18 - LocLT - Write Logstash
        String dataId = "";
        java.lang.reflect.Method method = null;

        try {
            method = data.getClass().getMethod("getId");
            dataId = String.valueOf(method.invoke(data));
		} catch (Exception e) {
		}

        transactionId = transactionId != null && transactionId != "" ? transactionId : dataId;

		LogstashDto logstash = new LogstashDto(null, request, "INFO", response, codeStatus, message, transactionId,
				took, others);

		logstashService.writeLogstash(logstash);
		// 2021-06-18 - LocLT - Write Logstash

        return response;
    }

    public DtsApiResponse handlerSuccessAdmin(Object data) {
        return new DtsApiResponse(DtsConstant.SUCCESS_CODE, DtsConstant.SUCCESS, data,null);
    }
}
