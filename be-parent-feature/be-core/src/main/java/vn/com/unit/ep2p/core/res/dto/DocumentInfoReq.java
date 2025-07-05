/*******************************************************************************
 * Class        ：DocumentInfoReq
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：tantm
 * Change log   ：2021/01/19：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.res.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.dto.AbstractProcessDto;

/**
 * DocumentInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
public class DocumentInfoReq extends AbstractProcessDto {

    @ApiModelProperty(notes = "Id of company", example = "1", required = true, position = 0)
    private Long companyId;
    
    @ApiModelProperty(notes = "Title of document", example = "abc", required = true, position = 0)
    private String docTitle;
    
    @ApiModelProperty(notes = "Id of org", example = "1", required = true, position = 0)
    private Long orgId;
    
    //@ApiModelProperty(notes = "Id of user action", example = "1", required = true, position = 0)
    //private Long ownerId;
    
    @ApiModelProperty(notes = "Id of form", example = "1", required = true, position = 0)
    private Long formId;
    
    @ApiModelProperty(notes = "Id of business", example = "1", required = true, position = 0)
    private Long businessId;
    
    @ApiModelProperty(notes = "Id of business", example = "1", required = true, position = 0)
    private String priority;
    
    @ApiModelProperty(notes = "Doc code", example = "PPL-20210121112104-193", required = true, position = 0)
    private String docCode;
    
    @ApiModelProperty(notes = "Id of document", example = "1", required = true, position = 0)
    private Long docId;
    
    //@ApiModelProperty(notes = "Id of mainFile", example = "1", required = true, position = 0)
    //private Long mainFileId;
    
    @ApiModelProperty(notes = "Summary", example = "Document 1...", required = false, position = 0)
    private String docSummary;
    
    @ApiModelProperty(notes = "Process deploy code", example = "PROCESS_AGENCY", required = true, position = 0)
    private String processDeployCode;
    
    private Long processDeployId;
    
    private boolean nextStep = true;
    
    private List<Long> lstDocId;
    private String errorBusiness;
    private List<String> lstErrorBusiness;
    private String buttonText;
    private boolean approveList = false;
    private String order;
}
