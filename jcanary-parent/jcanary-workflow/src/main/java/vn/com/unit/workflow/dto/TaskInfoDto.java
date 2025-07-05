/*******************************************************************************
 * Class        ：TaskInfoDto
 * Created date ：2020/07/21
 * Lasted date  ：2020/07/21
 * Author       ：NhanNV
 * Change log   ：2020/07/21：01-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.workflow.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * TaskInfoDto
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */
@Getter
@Setter
public class TaskInfoDto {

    private String coreTaskId;
    private String processInstanceId;
    private String taskDefinitionKey;
    private String executionId;
    private List<String> groupIds;
}
