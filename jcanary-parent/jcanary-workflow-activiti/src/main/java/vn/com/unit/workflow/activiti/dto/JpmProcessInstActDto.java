/*******************************************************************************
* Class        JpmProcessInstActDto
* Created date 2021/03/02
* Lasted date  2021/03/02
* Author       KhuongTH
* Change log   2021/03/02 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.activiti.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * JpmProcessInstActDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class JpmProcessInstActDto {

    private Long id;

    private Long processDeployId;

    private Long businessId;

    private String processInstActId;

    private Long referenceId;

    private Integer referenceType;

    private Long processStatusId;

    private Long commonStatusId;
    
    private String commonStatusCode;
    private String processStatusCode;

}