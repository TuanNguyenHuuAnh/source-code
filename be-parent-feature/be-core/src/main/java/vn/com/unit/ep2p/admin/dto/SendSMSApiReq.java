package vn.com.unit.ep2p.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendSMSApiReq {

	private String phoneNumber;
	private String content;
	private String sender;
	private String smsType;
	private String policyNumber;
}
