package vn.com.unit.core.dto;

import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtTokenValidate {
    
    private Claims claims;
    private boolean valid;
    private boolean expired = false;
}
