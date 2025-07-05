
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

@Setter
@Getter
@NoArgsConstructor
public class JcaGroupEmailDto extends AbstractTracking {
    
    private Long accountId;
    private String userName;
    private String roleId;
    private String email;     
}
