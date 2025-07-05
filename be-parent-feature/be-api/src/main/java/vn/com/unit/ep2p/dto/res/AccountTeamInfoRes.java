/*******************************************************************************
 * Class        ：AccountTeamInfoRes
 * Created date ：2021/01/18
 * Lasted date  ：2021/01/18
 * Author       ：taitt
 * Change log   ：2021/01/18：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.res;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.dto.JcaAccountDto;

/**
 * AccountTeamInfoRes
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class AccountTeamInfoRes{

    private Long teamId;
    private String teamCode;
    private String teamName;
    private Long companyId;
    private String companyName;
    private String description;
    private List<JcaAccountDto> accounts;
}
