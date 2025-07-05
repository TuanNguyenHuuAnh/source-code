/*******************************************************************************
 * Class        ：EmailTemplateUpdateInfoReq
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
 * EmailTemplateUpdateInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class EmailTemplateUpdateInfoReq extends EmailTemplateInfoReq {
	
	@ApiModelProperty(notes = "Id of email template", example = "1",  position = 0)
    private Long emailTemplateId;
	
}
