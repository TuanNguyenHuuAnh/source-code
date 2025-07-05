package vn.com.unit.ep2p.core.ers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErsDynamicInputConfDto extends ErsAbstract {
    
    private String channel;
    private Integer idInputBox;
    private String titleInputBox;
    private Integer widthChar;
    private String typeInputBox;
    private String statusInputBox;

}
