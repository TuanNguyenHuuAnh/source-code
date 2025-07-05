/*******************************************************************************
* Class        JpmStatusDto
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
 * JpmStatusDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class JpmStatusDto {

	private Long statusId;

	private Long processId;

	private String statusCode;

	private String statusName;
	
	private List<JpmStatusLangDto> statusLangs;

}