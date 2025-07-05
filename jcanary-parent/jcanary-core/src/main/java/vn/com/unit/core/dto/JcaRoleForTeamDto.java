/*******************************************************************************
 * Class        ：JcaRoleForGroup
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：SonND
 * Change log   ：2020/12/22：01-00 SonND create a new
 ******************************************************************************/

package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaRoleForGroup
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Setter
@Getter
@NoArgsConstructor
public class JcaRoleForTeamDto extends AbstractTracking {
    private Long id;
    private Long teamId;
    private String teamCode;
    private String teamName;
    private Long roleId;
    private String roleCode;
    private String roleName;
    private Long companyId;
    private String companyName;
    
    private boolean flgChecked;
	private boolean active;
	private int defFlag;
}
