/*******************************************************************************
* Class        JpmPermissionDeployDto
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * JpmPermissionDeployDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class JpmPermissionDeployDto {

	private Long permissionDeployId;

	private Long processDeployId;

	private String permissionCode;

	private String permissionName;

	private Integer permissionType;

	private Long permissionId;

}