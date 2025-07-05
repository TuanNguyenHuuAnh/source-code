/*******************************************************************************
 * Class        ：ConstantLanguageInfoReq
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
 * ConstantLanguageInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
public class ConstantLanguageInfoReq {

    @ApiModelProperty(notes = "Language code of constant on system", example = "EN", required = true, position = 0)
    private String langCode;

    @ApiModelProperty(notes = "Text of constant on system", example = "City", required = true, position = 0)
    private String name;

}
