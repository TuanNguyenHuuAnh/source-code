package vn.com.unit.core.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JcaEmailSearchDto {
    
	private String receiveAddress;
    private String senderAddress;
    private String subject;
    private String contentEmail;
    private String sendStatus;
    private String fromDate;
    private String toDate;
    private Long companyId;
}
