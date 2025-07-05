package vn.com.unit.ep2p.core.ers.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErsClassInfoDto extends ErsAbstract {
    // private static final String DATE_PATTERN = "dd/MM/yyyy";
    // protected static final String TIME_ZONE = "Asia/Saigon";
    
    private String classType;

    private String classCode;

    private String onlineOffline;

    private String province;

    @JsonFormat(pattern = DATE_PATTERN, timezone = TIME_ZONE)
    private Date startDate;

    @JsonFormat(pattern = DATE_PATTERN, timezone = TIME_ZONE)
    private Date endDate;

    @JsonFormat(pattern = DATE_PATTERN, timezone = TIME_ZONE)
    private Date examDate;

    private String channel;
}
