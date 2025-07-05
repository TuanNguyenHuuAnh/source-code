/*******************************************************************************
 * Class        ：JpmProcessImportExportDto
 * Created date ：2021/01/12
 * Lasted date  ：2021/01/12
 * Author       ：KhuongTH
 * Change log   ：2021/01/12：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.workflow.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * JpmProcessImportExportDto
 * </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@Setter
public class JpmProcessImportExportDto {

    private JpmBusinessDto businessDto;
    private String processCode;
    private String processName;
    private String description;
    private Date effectiveDate;
    private String fileBpmn;
    private boolean showWorkflow;
    private Long companyId;
    private Long bpmnRepoId;
    private String bpmnFileName;
    private String bpmnFilePath;

    private List<JpmProcessLangDto> processLangs;
    private List<JpmStepDto> steps;
    private List<JpmParamDto> params;
    private List<JpmStatusDto> statuses;
    private List<JpmButtonDto> buttons;
    private List<JpmPermissionDto> permissions;
}
