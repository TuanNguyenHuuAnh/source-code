/*******************************************************************************
 * Class        ：SystemConfigSearchReq
 * Created date ：2020/12/11
 * Lasted date  ：2020/12/11
 * Author       ：ngannh
 * Change log   ：2020/12/11：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.PagingReq;

/**
 * ConfigParamsSearchReq
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class SystemConfigSearchReq extends PagingReq {
    private String keySearch;
    private Long companyId;
    private Long groupId;
}
