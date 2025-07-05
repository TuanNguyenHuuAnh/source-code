/*******************************************************************************
 * Class        ：GroupSystemConfigSearchReq
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：ngannh
 * Change log   ：2020/12/15：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * GroupSystemConfigSearchReq
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class GroupSystemConfigSearchReq {
    private String keySearch;
    private Long companyId;
}
