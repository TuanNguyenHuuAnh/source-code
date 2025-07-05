/*******************************************************************************
* Class        JpmProcessDeployDto
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
 * JpmProcessDeployDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class JpmProcessDeployDto {

	private Long id;
    private Long processDeployId;

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

    private Long processId;

    private String fileBpmn; // Base64 encode

    private List<JpmProcessDeployLangDto> processLangs;

    private List<JpmStepDeployDto> steps;

    private List<JpmParamDeployDto> params;

    private List<JpmStatusDeployDto> statuses;

    private List<JpmButtonDeployDto> buttons;

    private List<JpmPermissionDeployDto> permissions;

    // use for handle
    private String businessCode;
    private Integer processType;
    private Date deployedDate;
    private String companyName;
}