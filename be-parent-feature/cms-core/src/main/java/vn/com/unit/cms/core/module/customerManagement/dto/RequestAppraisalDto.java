package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class RequestAppraisalDto {
	private String insuredPerson;
	private String requestType;
	private Integer numberOfRequest;
	private Date sendDate;
	private Date expiredDate;
}
