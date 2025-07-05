/*******************************************************************************
* Class        JpmProcessDmnDeployDto
* Created date 2021/03/15
* Lasted date  2021/03/15
* Author       KhuongTH
* Change log   2021/03/15 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * JpmProcessDmnDeployDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class JpmProcessDmnDeployDto {

    private Long id;

    private Long processDeployId;

    private String deploymentId;

    private String dmnFileName;

    private String dmnFilePath;

    private Long dmnRepoId;

}