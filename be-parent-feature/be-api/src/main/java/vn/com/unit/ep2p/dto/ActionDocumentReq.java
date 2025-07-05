/*******************************************************************************
 * Class        ：ActionDocumentReq
 * Created date ：2020/11/16
 * Lasted date  ：2020/11/16
 * Author       ：taitt
 * Change log   ：2020/11/16：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.core.req.DocumentReq;

/**
 * ActionDocumentReq
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Class Action properties to submit workflow document.")
public class ActionDocumentReq extends DocumentReq {

    @ApiModelProperty(notes = "Button id of document to action submit", example = "1", required = true, position = 0)
    private Long buttonId;
    
    @ApiModelProperty(notes = "Task id runtime of document to action submit", example = "123", required = true, position = 1)
    private String actTaskId;
    
    @ApiModelProperty(notes = "Comment note action", example = "submit", required = true, position = 1)
    private String note;
}
