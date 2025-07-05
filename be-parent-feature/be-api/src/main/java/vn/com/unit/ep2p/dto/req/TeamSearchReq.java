/*******************************************************************************
 * Class        ：TeamSearchReq
 * Created date ：2020/12/09
 * Lasted date  ：2020/12/09
 * Author       ：minhnv
 * Change log   ：2020/12/09：01-00 minhnv create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * TeamSearchReq
 * 
 * @version 01-00
 * @since 01-00
 * @author minhnv
 */
@Setter
@Getter
public class TeamSearchReq{
    private String keySearch;
    private Long companyId;
    private Boolean actived;
    
    public Boolean getActived() {
        return actived;
    }
}
