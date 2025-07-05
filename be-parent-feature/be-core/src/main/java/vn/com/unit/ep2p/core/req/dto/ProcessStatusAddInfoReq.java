/*******************************************************************************
 * Class        ：ProcessStatusAddInfoReq
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：SonND
 * Change log   ：2020/13/07：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p> ProcessStatusAddInfoReq </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class ProcessStatusAddInfoReq extends ProcessStatusInfoReq {
    
    @ApiModelProperty(notes = "Code of status", example = "APPROVED", required = true, position = 0)
    private String statusCode;

}
