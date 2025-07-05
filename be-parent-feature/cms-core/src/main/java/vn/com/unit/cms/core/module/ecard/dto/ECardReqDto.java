/*******************************************************************************
 * Class        ：ECardEditDto
 * Created date ：2017/04/20
 * Lasted date  ：2017/04/20
 * Author       ：TaiTM
 * Change log   ：2017/04/20：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.ecard.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonEditDto;

/**
 *
 * @version 01-00
 * @since 01-00
 * @author TriNT
 */
@Getter
@Setter
public class ECardReqDto extends CmsCommonEditDto {
    private String agentName;
    private String agentType;
    private String office;
    private String phone;
    private String zalo;
    private String facebook;
    private String email;
    private String avatar;
    private String taskingWinner;

}
