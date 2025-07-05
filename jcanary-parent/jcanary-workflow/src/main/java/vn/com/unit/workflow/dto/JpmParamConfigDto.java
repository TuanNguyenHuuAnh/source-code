/*******************************************************************************
* Class        JpmParamConfigDto
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * JpmParamConfigDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class JpmParamConfigDto {

	private Long processId;

	private Long paramId;

	private Long stepId;

	private boolean required;
	
	/**to handle*/
    private String stepName;

}