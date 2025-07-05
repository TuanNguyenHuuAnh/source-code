package vn.com.unit.ep2p.ers.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@Setter
public class CustomUsernamePasswordAuthentication extends UsernamePasswordAuthenticationToken {

    private String deviceToken;
    private String deviceId;
    private boolean isWebapp;
    private String valueToken;

    public CustomUsernamePasswordAuthentication(Object principal, Object credentials, String deviceId, String deviceToken, String valueToken, boolean isWebapp) {
        super(principal, credentials);
        this.deviceToken = deviceToken;
        this.deviceId = deviceId;
        this.isWebapp = isWebapp;
        this.valueToken = valueToken;
    }
}
