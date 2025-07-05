package vn.com.unit.ep2p.core.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PostNotificationletterRes {
    @JsonProperty("Result")
    private String result;

    @JsonProperty("ErrLog")
    private String errLog;

    @JsonProperty("Message")
    private String message;
    
    @JsonProperty("Notifications")
    private List<NotificationsDto> Notifications;
}
