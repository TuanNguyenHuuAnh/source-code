/*******************************************************************************
 * Class        ：JcaAccountOrgSearchDto
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：SonND
 * Change log   ：2020/12/16：01-00 SonND create a new
 ******************************************************************************/

package vn.com.unit.core.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JcaAccountOrgSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Setter
@Getter
@NoArgsConstructor
public class JcaAccountOrgSearchDto {
    private Long accountId;
    private String orgName;
    private String positionName;
    private Boolean actived;
}
