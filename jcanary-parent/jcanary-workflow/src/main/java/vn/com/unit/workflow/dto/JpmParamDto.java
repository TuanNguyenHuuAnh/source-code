/*******************************************************************************
* Class        JpmParamDto
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
 * JpmParamDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class JpmParamDto {

	private Long paramId;

	private Long processId;

	private String fieldName;

	private String dataType;

	private String formFieldName;

	private List<JpmParamConfigDto> paramConfigDtos;
}