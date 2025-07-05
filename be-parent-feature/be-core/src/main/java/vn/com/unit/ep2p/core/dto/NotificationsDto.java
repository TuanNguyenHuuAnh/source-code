package vn.com.unit.ep2p.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationsDto {
    @JsonProperty("LetterType")
    private String LetterType;
    @JsonProperty("PolicyNo")
    private String PolicyNo;
    @JsonProperty("MELetter")
    private String MELetter;
    @JsonProperty("ClientName")
    private String ClientName;
    @JsonProperty("PrimaryAgent")
    private String PrimaryAgent;
    @JsonProperty("ClientId")
    private String ClientId;
    @JsonProperty("CreateDate")
    private String CreateDate;
    @JsonProperty("ExpiredDate")
    private String ExpiredDate;
    @JsonProperty("DateDiff")
    private String DateDiff;
    
}
