package vn.com.unit.dts.exception;

import vn.com.unit.dts.web.rest.common.DtsApiResponse;

public interface ErrorHandler {

	@Deprecated // 2021-06-18 - LocLT - Write Logstash
	public DtsApiResponse handlerException(Exception ex, long start);
	
	// 2021-06-18 - LocLT - Write Logstash
	public DtsApiResponse handlerException(Exception ex, long start, String transactionId, String others);
	
	// 2021-06-28 - TriTV - Write Logstash
	public DtsApiResponse handlerException(Exception ex, long start, Object transactionId);

	@Deprecated // 2021-06-18 - LocLT - Write Logstash
	public DtsApiResponse handlerException(int codeStatus, String message);

	// 2021-06-18 - LocLT - Write Logstash
	public DtsApiResponse handlerException(int codeStatus, String message, String transactionId, long start,
			String others);
}
