/*******************************************************************************
 * Class        ：ProcessButtonInfoReq
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：SonND
 * Change log   ：2020/13/07：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p> ProcessButtonInfoReq </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class ProcessButtonInfoReq {

    @ApiModelProperty(notes = "Id of process", example = "1", required = true, position = 0)
    private Long processId;
    
    @ApiModelProperty(notes = "Text of button", example = "Submit", required = true, position = 0)
    private String buttonText;
    
    @ApiModelProperty(notes = "Value of button", example = "1", required = true, position = 0)
    private String buttonValue;
    
    @ApiModelProperty(notes = "Name class button to CSS", example = "u-btn-primary", required = true, position = 0)
    private String buttonClass;
    
    @ApiModelProperty(notes = "Type of button", example = "SUBMIT", required = true, position = 0)
    private String buttonType;
    
    @ApiModelProperty(notes = "Order button", example = "1", required = true, position = 0)
    private Long displayOrder;
    
    @ApiModelProperty(notes = "Language for process button", required = true, position = 0)
    private List<ProcessButtonLangInfoReq> buttonLangs;

}
