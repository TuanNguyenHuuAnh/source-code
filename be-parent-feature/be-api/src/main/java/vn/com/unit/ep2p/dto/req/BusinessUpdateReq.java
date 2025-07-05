/*******************************************************************************
 * Class        ：BusinessUpdateReq
 * Created date ：2020/12/07
 * Lasted date  ：2020/12/07
 * Author       ：KhuongTH
 * Change log   ：2020/12/07：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.BusinessInfoReq;


/**
 * <p> BusinessUpdateReq </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@Setter
public class BusinessUpdateReq extends BusinessInfoReq {
    
    @ApiModelProperty(notes = "Id of bussiness on system", example = "1", required = true, position = 0)
    private Long businessId;
}
