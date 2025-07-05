/*******************************************************************************
 * Class        ：EmailTemplateInfoReq
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：SonND
 * Change log   ：2020/12/23：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * EmailTemplateInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class EmailTemplateInfoReq {
	@ApiModelProperty(notes = "Name of email template", example = "Send RM", required = true, position = 0)
    private String templateName;
	@ApiModelProperty(notes = "Content of email template", example = "<p>Thanks and best regards <p/>", position = 0)
    private String templateContent;
	@ApiModelProperty(notes = "Email template actived", example = "true", position = 0)
    private Boolean actived;
	@ApiModelProperty(notes = "Subject of email template", example = "Notification My RM",  position = 0)
    private String subject;
	@ApiModelProperty(notes = "Id of company", example = "1",  position = 0)
    private Long companyId;
	@ApiModelProperty(notes = "List information language of email template",  required = true,  position = 0)
	private List<EmailTemplateLangInfoReq> emailTemplateLangInfoReqs;
}
