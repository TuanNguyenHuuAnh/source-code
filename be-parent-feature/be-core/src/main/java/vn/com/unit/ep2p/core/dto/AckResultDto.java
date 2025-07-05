package vn.com.unit.ep2p.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AckResultDto {
    @JsonProperty("Result")
    private String result;
    
    @JsonProperty("Message")
    private String Message;

    @JsonProperty("ErrLog")
    private String errLog;

    @JsonProperty("NewAPIToken")
    private String newAPIToken;
}
