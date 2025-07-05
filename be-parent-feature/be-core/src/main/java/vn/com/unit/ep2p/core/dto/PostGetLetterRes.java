package vn.com.unit.ep2p.core.dto;

import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
@Setter
public class PostGetLetterRes {
    @JsonProperty("Result")
    private String result;
    
    @JsonProperty("Data")
    private String Data;
    
    @JsonProperty("FileName")
    private String FileName;

    @JsonProperty("ErrLog")
    private String errLog;

    @JsonProperty("Message")
    private String message;
}
