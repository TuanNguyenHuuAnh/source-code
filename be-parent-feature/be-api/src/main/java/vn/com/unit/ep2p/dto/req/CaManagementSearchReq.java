/*******************************************************************************
 * Class        ：CaManagementSearchReq
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：minhnv
 * Change log   ：2020/12/16：01-00 minhnv create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * CaManagementSearchReq
 * 
 * @version 01-00
 * @since 01-00
 * @author minhnv
 */
@Getter
@Setter
public class CaManagementSearchReq {

    private String keySearch;
    private Long companyId;
}
