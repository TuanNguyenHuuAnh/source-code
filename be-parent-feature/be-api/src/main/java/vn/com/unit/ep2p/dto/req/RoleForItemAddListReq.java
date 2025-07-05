/*******************************************************************************
 * Class        ：RoleForItemAddListReq
 * Created date ：2020/12/31
 * Lasted date  ：2020/12/31
 * Author       ：ngannh
 * Change log   ：2020/12/31：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * RoleForItemAddListReq
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class RoleForItemAddListReq {
    /** data */
    @ApiModelProperty(notes = "List of authority", required = true, position = 0)
    private List<RoleForItemAddReq> data;
}
