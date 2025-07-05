/*******************************************************************************
 * Class        ：ProcessParamUpdateInfoReq
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
 * <p> ProcessParamUpdateInfoReq </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class ProcessParamUpdateInfoReq extends ProcessParamInfoReq {

    @ApiModelProperty(notes = "Id of process parameter", example = "1", required = true, position = 0)
    private Long paramId;

}
