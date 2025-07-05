package vn.com.unit.cms.core.module.ga.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AcceptanceCertificationDetailGa {

    private String gaCode;
    private String period;
    private String agentCode;
    private String otp;
    private String rejectReason;
    private Timestamp confirmTime;
}
