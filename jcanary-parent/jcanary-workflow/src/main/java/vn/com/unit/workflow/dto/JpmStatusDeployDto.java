/*******************************************************************************
* Class        JpmStatusDeployDto
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
 * JpmStatusDeployDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class JpmStatusDeployDto {

	private Long statusDeployId;

	private Long processDeployId;

	private String statusCode;

	private String statusName;

	private Long statusId;
	
	List<JpmStatusLangDeployDto> statusLangs;

}