/*******************************************************************************
 * Class        ：GroupConstantLanguageInfoReq
 * Created date ：2020/12/02
 * Lasted date  ：2020/12/02
 * Author       ：tantm
 * Change log   ：2020/12/02：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * GroupConstantLanguageInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
public class GroupConstantLanguageInfoReq {

    @ApiModelProperty(notes = "Language code of group constant on system", example = "EN", required = true, position = 0)
    private String languageCode;

    @ApiModelProperty(notes = "Text of group constant on system", example = "City", required = true, position = 0)
    private String text;

}
