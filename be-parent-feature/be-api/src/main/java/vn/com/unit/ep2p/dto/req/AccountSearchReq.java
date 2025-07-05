/*******************************************************************************
 * Class        ：AccountSearchReq
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：SonND
 * Change log   ：2020/12/01：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * AccountSearchReq
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class AccountSearchReq{

    private String keySearch;
    private Long companyId;
    private boolean actived;
    
    
    public boolean getActived() {
        return actived;
    }
    
}
