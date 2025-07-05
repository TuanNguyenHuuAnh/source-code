/*******************************************************************************
 * Class        ：RoleForPositionInfoRes
 * Created date ：2020/12/08
 * Lasted date  ：2020/12/08
 * Author       ：SonND
 * Change log   ：2020/12/08：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.res;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.dto.JcaRoleDto;

/**
 * RoleForPositionInfoRes
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class RoleForPositionInfoRes {
    List<JcaRoleDto> roleDtos;
    Long positionId;
    Long companyId;
}
