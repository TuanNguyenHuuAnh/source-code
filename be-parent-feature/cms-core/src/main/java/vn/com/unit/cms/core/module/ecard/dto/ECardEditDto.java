/*******************************************************************************
 * Class        ：ECardEditDto
 * Created date ：2017/04/20
 * Lasted date  ：2017/04/20
 * Author       ：TaiTM
 * Change log   ：2017/04/20：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.ecard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonEditDto;

/**
 * ECardEditDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
@NoArgsConstructor
public class ECardEditDto extends CmsCommonEditDto {

    private String type;

    private String typeName;

    private Integer agentName;

    private Integer agentType;

    private Integer office;

    private Integer phone;

    private Integer zalo;

    private Integer facebook;

    private Integer email;

    private String eCardImg;

    private String eCardPhysicalImg;

    private String background;
    private Integer label;
    public ECardEditDto(Integer agentName, Integer agentType, Integer office, Integer phone,
            Integer zalo, Integer facebook, Integer email,  Integer label) {
        super();
        this.agentName = agentName;
        this.agentType = agentType;
        this.office = office;
        this.phone = phone;
        this.zalo = zalo;
        this.facebook = facebook;
        this.email = email;
        this.label = label;
    }
}
