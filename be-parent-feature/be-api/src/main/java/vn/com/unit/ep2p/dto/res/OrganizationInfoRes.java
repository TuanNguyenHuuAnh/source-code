/*******************************************************************************
 * Class        ：OrganizationInfoRes
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：SonND
 * Change log   ：2020/12/15：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.res;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.dto.JcaOrganizationDto;

/**
 * OrganizationInfoRes
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class OrganizationInfoRes extends JcaOrganizationDto {
    private String strEffectivedDate;
    private String strExpiredDate;
}
