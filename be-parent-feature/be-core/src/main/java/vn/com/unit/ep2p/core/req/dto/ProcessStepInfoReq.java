/*******************************************************************************
 * Class        ：ProcessStepInfoReq
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
 * <p> ProcessStepInfoReq </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class ProcessStepInfoReq {

    @ApiModelProperty(notes = "Number order of step", example = "1", required = true, position = 0)
    private Long stepNo;
    
    @ApiModelProperty(notes = "Name of step", example = "Submit document", required = true, position = 0)
    private String stepName;
    
    @ApiModelProperty(notes = "Id of status", example = "1", required = true, position = 0)
    private Long statusId;
    
    @ApiModelProperty(notes = "Id of process", example = "1", required = true, position = 0)
    private Long processId;
    
    @ApiModelProperty(notes = "Type of step", example = "USER_TASK", required = true, position = 0)
    private String stepType;
    
    @ApiModelProperty(notes = "Kind of step", example = "NORMAL", required = true, position = 0)
    private String stepKind;
    
    @ApiModelProperty(notes = "Code of status", example = "991", required = true, position = 0)
    private String statusCodeCommon;
    
    @ApiModelProperty(notes = "Use claim button", example = "false", required = true, position = 0)
    private boolean useClaimButton;
    
    public boolean getUseClaimButton() {
        return useClaimButton;
    }
    
    @ApiModelProperty(notes = "List language of step", required = true, position = 0)
    private List<ProcessStepLangInfoReq> stepLangs;
    
    @ApiModelProperty(notes = "List button of step", required = true, position = 0)
    private List<ProcessButtonForStepInfoReq> buttonsForStep;

}
