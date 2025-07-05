/*******************************************************************************
 * Class        ：ECardSearchResultDto
 * Created date ：2017/02/22
 * Lasted date  ：2017/02/22
 * Author       ：TaiTM
 * Change log   ：2017/02/22：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.ecard.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchResultFilterDto;

/**
 * ECardSearchResultDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class ECardSearchResultDto extends CmsCommonSearchResultFilterDto {
    private Long mCustomTypeId;
    private String type;
    private String agentName;
    private String agentType;
    private String office;
    private String phone;
    private String zalo;
    private String facebook;
    private String email;
    private Long sort;
    private Long docId;

}
