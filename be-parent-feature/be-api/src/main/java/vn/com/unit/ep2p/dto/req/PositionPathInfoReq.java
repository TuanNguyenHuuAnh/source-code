/*******************************************************************************
 * Class        ：PositionPathInfoReq
 * Created date ：2020/12/25
 * Lasted date  ：2020/12/25
 * Author       ：SonND
 * Change log   ：2020/12/01：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * PositionPathInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class PositionPathInfoReq {
    private Integer depth;
    private Long ancestorId;
    private Long descendantId;
    
}
