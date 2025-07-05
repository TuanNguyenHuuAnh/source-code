/*******************************************************************************
 * Class        ：DashBoardDocumentTaskUpdateReq
 * Created date ：2021/01/26
 * Lasted date  ：2021/01/26
 * Author       ：taitt
 * Change log   ：2021/01/26：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.annotation.NotNull;
import vn.com.unit.ep2p.core.annotation.Required;

/**
 * DashBoardDocumentTaskUpdateReq
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class DashBoardDocumentTaskUpdateReq extends DashboardDocumentTaskEnumReq{
    
    @NotNull
    private Long documentId;
    
    @Required
    private String stepCode;
}
