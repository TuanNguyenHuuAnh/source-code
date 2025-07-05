/*******************************************************************************
 * Class        ：AppWorkflowRes
 * Created date ：2021/01/21
 * Lasted date  ：2021/01/21
 * Author       ：KhuongTH
 * Change log   ：2021/01/21：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.res;

import lombok.Getter;
import lombok.Setter;

/**
 * <p> AppWorkflowRes </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@Setter
public class AppWorkflowRes {
    private String processInstanceId;
    private String coreTaskId;
    private Long taskId;
    
}
