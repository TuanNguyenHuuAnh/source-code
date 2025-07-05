/*******************************************************************************
 * Class        ：ConstantInfoReq
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
 * ConstantInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
public class ConstantInfoReq {

    @ApiModelProperty(notes = "Code of constant on system", example = "City", required = true, position = 0)
    private String code;
    
    @ApiModelProperty(notes = "Group code of constant on system", example = "City", required = true, position = 0)
    private String groupCode;

    @ApiModelProperty(notes = "Display order of constant on system", example = "1", required = true, position = 0)
    private Long displayOrder;

    @ApiModelProperty(notes = "Kind of constant on system", example = "", required = false, position = 0)
    private String kind;
    
    @ApiModelProperty(notes = "Actived of constant on system", example = "", required = false, position = 0)
    private boolean actived;
    
    @ApiModelProperty(notes = "Languages of constant on system", example = "[{languageCode: EN, text: City}]", required = true, position = 0)
    private List<ConstantLanguageInfoReq> languages;

}
