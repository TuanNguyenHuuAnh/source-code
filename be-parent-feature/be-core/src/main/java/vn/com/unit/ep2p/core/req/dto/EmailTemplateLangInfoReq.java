/*******************************************************************************
 * Class        ：EmailTemplateLangInfoReq
 * Created date ：2021/01/15
 * Lasted date  ：2021/01/15
 * Author       ：SonND
 * Change log   ：2021/01/15：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * EmailTemplateLangInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class EmailTemplateLangInfoReq {
    @ApiModelProperty(notes = "Code of language", example = "en",  position = 0)
    private String langCode;
    
	@ApiModelProperty(notes = "Title of email template language", example = "Notification My RM",  position = 0)
    private String title;
	
	@ApiModelProperty(notes = "Notication of email", example = "Tài liệu ${docTitle} ${actionPassive_VI} bởi ông/bà ${submitBy}. Nhấn vào để xem chi tiết.",  position = 0)
    private String notification;
	
}
