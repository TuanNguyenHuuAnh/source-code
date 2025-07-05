/*******************************************************************************
 * Class        ：AccountTeamUpdateAccountReq
 * Created date ：2021/01/15
 * Lasted date  ：2021/01/15
 * Author       ：taitt
 * Change log   ：2021/01/15：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * AccountTeamUpdateAccountReq
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class AccountTeamUpdateAccountReq {

    private List<Long> accountIds;
    
    private Long teamId;
}
