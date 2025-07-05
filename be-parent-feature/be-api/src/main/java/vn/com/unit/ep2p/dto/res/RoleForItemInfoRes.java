/*******************************************************************************
 * Class        ：RoleForItemInfoRes
 * Created date ：2020/12/24
 * Lasted date  ：2020/12/24
 * Author       ：ngannh
 * Change log   ：2020/12/24：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.res;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.dto.JcaAuthorityDto;

/**
 * RoleForItemInfoRes
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class RoleForItemInfoRes{
    private Long roleId;
    private List<JcaAuthorityDto> listJcaAuthorityDto;
}
