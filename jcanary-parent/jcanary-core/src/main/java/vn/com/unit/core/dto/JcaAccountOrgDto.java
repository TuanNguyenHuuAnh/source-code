/*******************************************************************************
 * Class        ：JcaAccountOrgDto
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：SonND
 * Change log   ：2020/12/16：01-00 SonND create a new
 ******************************************************************************/

package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaAccountOrgDto
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Setter
@Getter
@NoArgsConstructor
public class JcaAccountOrgDto extends AbstractTracking {
    //private Long accountOrgId;
    private Long userId;
    private String userName;
    private Long orgId;
    private String orgName;
    private String orgCode;
    private Long positionId;
    private String positionName;
    private Boolean actived;
    private Boolean mainFlag;
}
