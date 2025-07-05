/*******************************************************************************
* Class        JpmPermissionDto
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * JpmPermissionDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class JpmPermissionDto {

	private Long permissionId;

	private Long processId;

	private String permissionCode;

	private String permissionName;

	private Integer permissionType;

}