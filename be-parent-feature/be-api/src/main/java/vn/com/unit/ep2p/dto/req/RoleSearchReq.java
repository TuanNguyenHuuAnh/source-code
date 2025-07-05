/*******************************************************************************
 * Class        ：RoleSearchReq
 * Created date ：2020/12/02
 * Lasted date  ：2020/12/02
 * Author       ：ngannh
 * Change log   ：2020/12/02：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * RoleSearchReq
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class RoleSearchReq {
    @ApiModelProperty(notes = "Id of company", example = "2", required = true, position = 0)
    private Long companyId;
}
