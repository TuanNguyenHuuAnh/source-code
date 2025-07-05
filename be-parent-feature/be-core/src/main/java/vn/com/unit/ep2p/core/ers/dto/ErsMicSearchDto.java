package vn.com.unit.ep2p.core.ers.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ErsMicSearchDto {
    
    private String region;
    
    private String area;

    private Date createDateFrom;

    private Date createDateTo;
}
