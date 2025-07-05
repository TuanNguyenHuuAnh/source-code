package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RequestAdditionalDto {
	private String insuredPerson;
	private String requestType;
	private String requestDetail;
	private Date ackSendDate;
	private Date expiredDate;
}
