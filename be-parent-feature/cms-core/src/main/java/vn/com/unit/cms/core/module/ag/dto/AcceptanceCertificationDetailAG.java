package vn.com.unit.cms.core.module.ag.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptanceCertificationDetailAG {
    
    private String period;
    private String agentCode;
    private String otp;
    private String rejectReason;
    private Timestamp confirmTime;
}
