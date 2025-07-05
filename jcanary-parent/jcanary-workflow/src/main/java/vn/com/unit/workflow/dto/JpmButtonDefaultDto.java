/*******************************************************************************
* Class        JpmButtonDefaultDto
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * JpmButtonDefaultDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class JpmButtonDefaultDto {

    private Long id;

    private String buttonCode;

    private String buttonText;

    private String buttonValue;

    private String buttonClass;

    private String buttonType;

    private Long displayOrder;

    private String processType;

    private String processKind;

}