/*******************************************************************************
* Class        JpmAuthorityDto
* Created date 2021/03/02
* Lasted date  2021/03/02
* Author       KhuongTH
* Change log   2021/03/02 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * JpmAuthorityDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class JpmAuthorityDto {

	private Long processDeployId;
	private Long permissionDeployId;
	private Long roleId;
	
	// only view
	private String permissionName;
	private boolean accessFlag;
}