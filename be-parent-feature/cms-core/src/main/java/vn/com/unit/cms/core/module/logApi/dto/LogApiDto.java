package vn.com.unit.cms.core.module.logApi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class LogApiDto {
	private Long id;
	private String username;
    private String endpoint;
    private String method;
    private String clientIp;
    private int status;
    private String message;
    private String requestJson;
    private String responseJson;
    private int tatms;
    private String device;
    private String exception;
    private Date createdDay;
    private String storeName;
    private String param;
    private int tatDb;
}
