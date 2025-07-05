package vn.com.unit.ep2p.core.ers.dto;

import lombok.Getter;
import lombok.Setter;

//import java.util.Date;

@Getter
@Setter
public class ErsMicDto extends ErsAbstract{
    
    private String unitCode;

    private String unitName;

    private String areaName;

    private String regionName;

}
