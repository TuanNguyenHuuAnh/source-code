package vn.com.unit.ep2p.core.ers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErsSlaConfigDto extends ErsAbstract{

    private String channel;
    
    private String processStatusCode;
    
    private String processStatus;
    
    private String statusSla;
    
    private String mailType;
    
    private Integer dueDate;
    
    private Integer reminderBefore;
}
