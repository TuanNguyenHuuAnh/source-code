/*******************************************************************************
* Class        JpmParamConfigDeployDto
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * JpmParamConfigDeployDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class JpmParamConfigDeployDto {

    private Long processDeployId;

    private Long paramDeployId;

    private Long stepDeployId;

    private boolean required;

    /** stepDeployId use for deploy*/
    private Long stepId;
    private String stepName;

}