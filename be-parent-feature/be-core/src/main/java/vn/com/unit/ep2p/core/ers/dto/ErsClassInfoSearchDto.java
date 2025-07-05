package vn.com.unit.ep2p.core.ers.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErsClassInfoSearchDto {
    
    private String channel;
    
    private String classType;

    private String classCode;

    private String province;

    private Date startDateFrom;

    private Date startDateTo;

}
