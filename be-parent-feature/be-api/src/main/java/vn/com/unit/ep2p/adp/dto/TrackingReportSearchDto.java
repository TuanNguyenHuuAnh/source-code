package vn.com.unit.ep2p.adp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackingReportSearchDto {
    private String agentCode;
    private String monthYear;
    private String type;
    private String functionCode;
}
