/*******************************************************************************
* Class        JpmProcessDto
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * JpmProcessDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class JpmProcessDto {

    private Long processId;

    private String processCode;

    private String processName;

    private String description;

    private Long majorVersion;

    private Long minorVersion;

    private Date effectiveDate;

    private Long bpmnRepoId;

    private String bpmnFilePath;

    private String bpmnFileName;

    private boolean deployed;

    private boolean actived;

    private Long businessId;

    private String processDefinitionId;

    private boolean showWorkflow;

    private Long companyId;

    private String fileBpmn; // Base64 encode

    private List<JpmProcessLangDto> processLangs;

    private List<JpmStepDto> steps;

    private List<JpmParamDto> params;

    private List<JpmStatusDto> statuses;

    private List<JpmButtonDto> buttons;

    private List<JpmPermissionDto> permissions;

    // to handle
    private String businessCode;
    private String companyName;
    private String processType;

    // public Object getEffectiveDate() {
    // return this.effectiveDate;
    // }
    //
    // public void setEffectiveDate(Date effectiveDate) {
    // this.effectiveDate = effectiveDate;
    // }
}