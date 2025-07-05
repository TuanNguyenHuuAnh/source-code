package vn.com.unit.ep2p.core.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QaCodeRes {
    @JsonProperty("Result")
    private String result;

    @JsonProperty("ErrLog")
    private String errLog;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("NewAPIToken")
    private String newApiToken;

}
