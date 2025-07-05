/*******************************************************************************
 * Class        ：JcaAccountTeamSearchDto
 * Created date ：2021/01/15
 * Lasted date  ：2021/01/15
 * Author       ：taitt
 * Change log   ：2021/01/15：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * JcaAccountTeamSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class JcaAccountTeamSearchDto {
    private String teamCode;
    private Long companyId;
    private String teamName;
    private Boolean nonData;
}
