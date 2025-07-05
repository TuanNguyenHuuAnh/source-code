/*******************************************************************************
 * Class        ：EmailTemplateAddInfoReq
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：SonND
 * Change log   ：2020/12/23：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * EmailTemplateAddInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class EmailTemplateAddInfoReq extends EmailTemplateInfoReq {
	@ApiModelProperty(notes = "Code of email template", example = "SEND_RM", required = true, position = 0)
    private String code;
	
}
