/*******************************************************************************
 * Class        ：MenuPathInfoReq
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：SonND
 * Change log   ：2020/12/01：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * MenuPathInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class MenuPathInfoReq {
    private Integer depth;
    private Long ancestorId;
    private Long descendantId;
    
}
