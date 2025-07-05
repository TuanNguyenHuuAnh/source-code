package vn.com.unit.ep2p.core.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SigninADPortalRes {
    @JsonProperty("code")
    private String code;

    @JsonProperty("description")
    private String description;

}
