package vn.com.unit.dts.web.rest.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class DtsAppLog {

    private Request request;

    private Response response;

    public void writeWithLogger(String marker) {
    	/*String request = this.request.toString();
    	if (request != null && request.contains("password")) {
    		request = request.substring(0, request.indexOf("password"));
    	}
        log.info("{} -- Request: {} -- Response: {}", marker, request, this.response.toString());
        */
    }
    
    public void writeWithLoggerRequest(String marker) {
    	/*String request = this.request.toString();
    	if (request != null && request.contains("password")) {
    		request = request.substring(0, request.indexOf("password"));
    	}
        log.info("{} -- Request: {}", marker, request);
        */
    }

    public void writeWithLoggerResponse(String marker) {
        //log.info("{} -- Response: {}", marker, this.response.toString());
    }

    @Data
    @AllArgsConstructor
    public static class Request {

        private int totalConnections;

        private int activeConnections;

        private int idleConnections;

        private int idleThreadsAwaitingConnection;

        private String requestMethod;

        private String requestUrl;

        private String header;

        private String requestBody;
        
        
      
    }

    @Data
    @AllArgsConstructor
    public static class Response {

        private long took;

        private String status;

        private int totalConnections;
        
        private int activeConnections;
        
        private int idleConnections;
        
        private int idleThreadsAwaitingConnection;

        private Object responseBody;
    }
}
