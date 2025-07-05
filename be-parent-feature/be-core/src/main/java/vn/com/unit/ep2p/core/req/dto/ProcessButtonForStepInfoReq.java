/*******************************************************************************
 * Class        ：ProcessStepLangInfoReq
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
 * <p> ProcessStepLangInfoReq </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class ProcessButtonForStepInfoReq {
    @ApiModelProperty(notes = "Id of button", example = "1", required = true, position = 0)
    private Long buttonId;
    
    @ApiModelProperty(notes = "Id of permission", example = "1", required = true, position = 0)
    private Long permissionId;
    
    @ApiModelProperty(notes = "Has save form", example = "true", required = true, position = 0)
    private boolean optionSaveForm;

    @ApiModelProperty(notes = "Has save e-form", example = "true", required = true, position = 0)
    private boolean optionSaveEform;
    
    @ApiModelProperty(notes = "Has authenticate", example = "true", required = true, position = 0)
    private boolean optionAuthenticate;

    @ApiModelProperty(notes = "Has signed", example = "true", required = true, position = 0)
    private boolean optionSigned;

    @ApiModelProperty(notes = "Has export pdf", example = "true", required = true, position = 0)
    private boolean optionExportPdf;
    
    @ApiModelProperty(notes = "Has show history", example = "true", required = true, position = 0)
    private boolean optionShowHistory;

    @ApiModelProperty(notes = "Field to form", example = "true", required = true, position = 0)
    private boolean optionFillToEform;
}
