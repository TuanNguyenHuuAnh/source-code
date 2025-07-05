/*******************************************************************************
 * Class        ：ProcessInstanceInfoDto
 * Created date ：2020/11/10
 * Lasted date  ：2020/11/10
 * Author       ：NhanNV
 * Change log   ：2020/11/10：01-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.workflow.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ProcessInstanceInfoDto
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessInstanceInfoDto {

    private String processInstanceId;
    private String processDefinitionId;
    private String actTaskId;	
    private Map<String, Object> processVariables;	
    private String businessKey;
}
