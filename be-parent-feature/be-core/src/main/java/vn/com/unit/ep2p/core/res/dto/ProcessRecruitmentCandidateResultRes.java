/***************************************************************
 * @author vunt					
 * @date Apr 22, 2021	
 * @project mbal-core
 * @version 1.0 
 * @description 
 ***************************************************************/
package vn.com.unit.ep2p.core.res.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessRecruitmentCandidateResultRes extends RecruitmentCandidateResultRes {
    @ApiModelProperty(notes = "Type of process", example = "3", required = true, position = 0)
    private int processType;
    
//    @ApiModelProperty(notes = "List main file", example = "[base64]", required = true, position = 0)
//    private List<String> fileStream;
    
    @ApiModelProperty(notes = "Id of company", example = "1", required = true, position = 0)
    private Long companyId;
    
    @ApiModelProperty(notes = "Title of document", example = "Process for Recruitment Candidate", required = true, position = 0)
    private String docTitle;
    
    @ApiModelProperty(notes = "Id of org", example = "1", required = true, position = 0)
    private Long orgId;
    
    @ApiModelProperty(notes = "Id of creater", example = "1", required = true, position = 0)
    private Long ownerId;
    
//    @ApiModelProperty(notes = "Id of form", example = "1", required = true, position = 0)
//    private Long formId;
    
    @ApiModelProperty(notes = "Id of process deploy", example = "1", required = true, position = 0)
    private Long processDeployId;
    
    @ApiModelProperty(notes = "Id of business", example = "1", required = true, position = 0)
    private Long businessId;
    
    @ApiModelProperty(notes = "Id of business", example = "1", required = true, position = 0)
    private String priority;
    
    @ApiModelProperty(notes = "Doc code", example = "MBAL_RECRUITMENT_CANDIDATE_BANCAS_01", required = true, position = 0)
    private String docCode;
    
    @ApiModelProperty(notes = "Id of document", example = "1", required = true, position = 0)
    private Long docId;
    
//    @ApiModelProperty(notes = "Id of mainFile", example = "1", required = true, position = 0)
//    private Long mainFileId;
    
    @ApiModelProperty(notes = "Summary", example = "Document 1...", required = false, position = 0)
    private String docSummary;
    
    @ApiModelProperty(notes = "Button id of document to action submit", example = "1", required = true, position = 0)
    private Long buttonId;
}
