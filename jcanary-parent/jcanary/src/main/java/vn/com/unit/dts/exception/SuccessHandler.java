package vn.com.unit.dts.exception;

import vn.com.unit.dts.web.rest.common.DtsApiResponse;

public interface SuccessHandler {

	@Deprecated
    public DtsApiResponse handlerSuccess(Object data, long start);
    
    public DtsApiResponse handlerSuccess(Object data, long start, Object transactionId);
    
    public DtsApiResponse handlerSuccess(Object data, long start, String transactionId, String others);

    public DtsApiResponse handlerSuccessAdmin(Object data);
}
