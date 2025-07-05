/*******************************************************************************
 * Class        ：I18nLocaleReq
 * Created date ：2020/11/16
 * Lasted date  ：2020/11/16
 * Author       ：NhanNV
 * Change log   ：2020/11/16：01-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * I18nLocaleReq
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Class I18n properties to clone translation.")
public class I18nLocaleReq{

    @ApiModelProperty(notes = "Company id", example = "1001", required = true, position = 1)
    private long companyId;
    
    @ApiModelProperty(notes = "The new language code", example = "en", required = true, position = 1)
    private String newLanguageCode;
}
