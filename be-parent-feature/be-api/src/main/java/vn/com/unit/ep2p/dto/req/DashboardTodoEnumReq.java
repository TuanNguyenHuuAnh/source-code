/*******************************************************************************
 * Class        ：DashboardTodoEnumReq
 * Created date ：2021/01/27
 * Lasted date  ：2021/01/27
 * Author       ：taitt
 * Change log   ：2021/01/27：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.annotation.MapWithEnum;
import vn.com.unit.ep2p.core.annotation.NotNull;
import vn.com.unit.ep2p.core.enumdef.DashBoardTodoTypeEnum;

/**
 * DashboardTodoEnumReq
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class DashboardTodoEnumReq {
    @MapWithEnum(enumClass = DashBoardTodoTypeEnum.class)
    @NotNull
    private String filter;
}
