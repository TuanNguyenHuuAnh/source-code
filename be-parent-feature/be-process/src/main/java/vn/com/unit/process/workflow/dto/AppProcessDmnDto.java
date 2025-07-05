/*******************************************************************************
* Class        AppProcessDmnDto
* Created date 2021/03/15
* Lasted date  2021/03/15
* Author       KhuongTH
* Change log   2021/03/15 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.process.workflow.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

/**
 * AppProcessDmnDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Getter
@Setter
public class AppProcessDmnDto {

    private Long id;
    private Long processId;
    private String dmnFileName;
    private String dmnFilePath;
    private Long dmnRepoId;

    private MultipartFile fileDmn;
}