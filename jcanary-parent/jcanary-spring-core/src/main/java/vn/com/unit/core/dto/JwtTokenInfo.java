package vn.com.unit.core.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtTokenInfo {
    
    public JwtTokenInfo(String token, Date sssuedAt, Date expiration, long tokenValidity) {
        super();
        this.token = token;
        this.sssuedAt = sssuedAt;
        this.expiration = expiration;
        this.tokenValidity = tokenValidity;
    }
    
    private String token;
    private Date sssuedAt;
    private Date expiration;
    private long tokenValidity;
}
