/*******************************************************************************
 * Class        ：ProcessParamInfoReq
 * Created date ：2021/01/12
 * Lasted date  ：2021/01/12
 * Author       ：SonND
 * Change log   ：2020/12/07：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p> ProcessParamInfoReq </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class ProcessPermissionUpdateInfoReq extends ProcessPermissionInfoReq {

    @ApiModelProperty(notes = "Id of permission", example = "1", required = true, position = 0)
    private Long permissionId;

}
