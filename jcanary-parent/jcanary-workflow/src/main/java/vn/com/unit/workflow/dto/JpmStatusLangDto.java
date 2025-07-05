/*******************************************************************************
* Class        JpmStatusLangDto
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * JpmStatusLangDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class JpmStatusLangDto {

	private Long langId;

	private String langCode;

	private String statusName;

	private Long statusId;
}