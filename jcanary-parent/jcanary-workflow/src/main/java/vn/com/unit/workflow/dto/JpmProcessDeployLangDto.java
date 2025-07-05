/*******************************************************************************
* Class        JpmProcessDeployLangDto
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * JpmProcessDeployLangDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class JpmProcessDeployLangDto {

    private Long langId;
    private String langCode;
    private String processName;
    private Long processDeployId;

}