/*******************************************************************************
 * Class        ：AccountAppUpdateReq
 * Created date ：2021/02/26
 * Lasted date  ：2021/02/26
 * Author       ：taitt
 * Change log   ：2021/02/26：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * AccountAppUpdateReq
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class AccountAppUpdateReq {

    @ApiModelProperty(notes = "Received email", example = "false",  position = 1)
    private Boolean receivedEmail;
    @ApiModelProperty(notes = "Received notification", example = "true",  position = 2)
    private Boolean receivedNotification;
    
    @ApiModelProperty(notes = "Encode file to Base64", position = 0)
    private String imgBase64;
    @ApiModelProperty(notes = "Name file upload", example = "avatar",  position = 0)
    private String fileName;
}
