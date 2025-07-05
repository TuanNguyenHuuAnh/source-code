/*******************************************************************************
 * Class        ：GroupConstantInfoReq
 * Created date ：2020/12/02
 * Lasted date  ：2020/12/02
 * Author       ：tantm
 * Change log   ：2020/12/02：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * GroupConstantInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
public class GroupConstantInfoReq {

    @ApiModelProperty(notes = "Code of group constant on system", example = "City", required = true, position = 0)
    private String code;

    @ApiModelProperty(notes = "Display order of group constant on system", example = "1", required = true, position = 0)
    private Long displayOrder;

    @ApiModelProperty(notes = "Company id of group constant on system", example = "1", required = true, position = 0)
    private Long companyId;

    @ApiModelProperty(notes = "Languages of group constant on system", example = "[{languageCode: EN, text: City}]", required = true, position = 0)
    private List<GroupConstantLanguageInfoReq> languages;
    
    @ApiModelProperty(notes = "Constants of group constant on system", example = "[{code:,displayOrder:,note:,languages:[{languageCode:,text:}]}]", required = true, position = 0)
    private List<ConstantInfoReq> constants;

}
