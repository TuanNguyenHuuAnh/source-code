/*******************************************************************************
* Class        JpmButtonDto
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
 * JpmButtonDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class JpmButtonDto {

    private Long buttonId;

    private Long processId;

    private String buttonCode;

    private String buttonText;

    private String buttonValue;

    private String buttonClass;

    private String buttonType;

    private Long displayOrder;

    private List<JpmButtonLangDto> buttonLangs;
}