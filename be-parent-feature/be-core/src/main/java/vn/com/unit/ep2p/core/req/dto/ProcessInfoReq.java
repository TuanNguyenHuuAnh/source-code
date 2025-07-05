/*******************************************************************************
 * Class        ：ProcessInfoReq
 * Created date ：2020/12/07
 * Lasted date  ：2020/12/07
 * Author       ：KhuongTH
 * Change log   ：2020/12/07：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p> ProcessInfoReq </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@Setter
public class ProcessInfoReq {

    @ApiModelProperty(notes = "code of process on system", example = "BUSINESS", required = true, position = 0)
    private String processCode;
    
    @ApiModelProperty(notes = "name of process on system", example = "Business", required = true, position = 0)
    private String processName;
    
    @ApiModelProperty(notes = "description of process", example = "Business", required = true, position = 0)
    private String description;
    
    @ApiModelProperty(notes = "id of company", example = "1", required = true, position = 0)
    private Long companyId;
    
    @ApiModelProperty(notes = "date effective process", example = "20201110123456", required = true, position = 0)
    private String effectiveDate;

    @ApiModelProperty(notes = "id of business", example = "1", required = true, position = 0)
    private Long businessId;
    
    @ApiModelProperty(notes = "active process", example = "true", required = true, position = 0)
    private boolean actived;
    
    @ApiModelProperty(notes = "show workflow diagram on document detail", example = "true", required = true, position = 0)
    private boolean showWorkflow;
    
    @ApiModelProperty(notes = "name of file BPMN", example = "SimpleProcess.bpmn", required = true, position = 0)
    private String bpmnFileName;
    
    @ApiModelProperty(notes = "content of file BPMN encode by BASE64", example = "LCBidXMuQVVUSE9SSVRZIC...", required = true, position = 0)
    private String bpmnContent;
    
    @ApiModelProperty(notes = "List language of process name", example = "", required = true, position = 0)
    private List<ProcessLangReq> processLangs;
}
