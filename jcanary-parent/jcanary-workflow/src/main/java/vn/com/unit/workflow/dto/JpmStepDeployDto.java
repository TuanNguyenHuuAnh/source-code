/*******************************************************************************
* Class        JpmStepDeployDto
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * JpmStepDeployDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class JpmStepDeployDto {

    private Long stepDeployId;

    private Long stepNo;

    private String stepCode;

    private String stepName;

    private String description;

    private Long commonStatusId;

    private String commonStatusCode;

    private Long statusDeployId;

    private String statusCode;

    private Long processDeployId;

    private String stepKind;

    private String stepType;

    private boolean useClaimButton;

    private Long stepId;

    private List<JpmStepLangDeployDto> stepLangs;

    private List<JpmButtonForStepDeployDto> buttonForStepDtos;

    /** statusId use for deploy */
    private Long statusId;
    private List<String> permissionCodes;
    
    private String processStatusCode;
    private Long processStatusId;
}