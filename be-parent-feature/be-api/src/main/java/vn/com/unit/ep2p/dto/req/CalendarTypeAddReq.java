/*******************************************************************************
 * Class        ：CalendarTypeAddReq
 * Created date ：2020/12/14
 * Lasted date  ：2020/12/14
 * Author       ：TrieuVD
 * Change log   ：2020/12/14：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import vn.com.unit.ep2p.core.req.dto.CalendarTypeInfoReq;

/**
 * CalendarTypeAddReq
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public class CalendarTypeAddReq extends CalendarTypeInfoReq {

    /** The id. */
    @ApiModelProperty(notes = "Id of calendar type on system", example = "1", required = true, position = 0)
    private Long id;
}
