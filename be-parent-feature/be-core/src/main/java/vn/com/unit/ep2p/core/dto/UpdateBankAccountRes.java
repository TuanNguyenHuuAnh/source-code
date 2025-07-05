package vn.com.unit.ep2p.core.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBankAccountRes {
    @JsonProperty("result")
    private String result;

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;
}
