/*******************************************************************************
 * Class        ：JcaAccountTeamDto
 * Created date ：2021/01/15
 * Lasted date  ：2021/01/15
 * Author       ：taitt
 * Change log   ：2021/01/15：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * JcaAccountTeamDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */

@Getter
@Setter
public class JcaAccountTeamDto {
 
    private Long jcaAccountTeamId;
    private Long accountId;
    private Long teamId;
    private Date effectedDate;
    private Date expiredDate;
    private String teamName;
}
