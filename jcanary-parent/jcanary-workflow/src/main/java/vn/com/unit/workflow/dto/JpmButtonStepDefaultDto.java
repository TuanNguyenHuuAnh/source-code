/*******************************************************************************
* Class        JpmButtonStepDefaultDto
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * JpmButtonStepDefaultDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class JpmButtonStepDefaultDto {

    private Long id;

    private Long stepId;

    private Long buttonId;

    private Long permissionId;

    private boolean optionSaveForm;

    private boolean optionSaveEform;

    private boolean optionAuthenticate;

    private boolean optionSigned;

    private boolean optionExportPdf;

    private boolean optionShowHistory;

    private boolean optionFillToEform;

    private String processType;

    private String processKind;

}