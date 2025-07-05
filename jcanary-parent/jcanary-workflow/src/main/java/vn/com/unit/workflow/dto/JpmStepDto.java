/*******************************************************************************
* Class        JpmStepDto
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
 * JpmStepDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class JpmStepDto {

    private Long stepId;

    private Long stepNo;

    private String stepCode;

    private String stepName;

    private String description;

    private Long commonStatusId;

    private String commonStatusCode;

    private Long statusId;

    private String statusCode;

    private Long processId;

    private String stepKind;

    private String stepType;

    private boolean useClaimButton;

    private List<JpmStepLangDto> stepLangs;

    private List<JpmButtonForStepDto> buttonForStepDtos;

}