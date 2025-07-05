/*******************************************************************************
 * Class        ：SystemConfigInfoReq
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：ngannh
 * Change log   ：2020/12/16：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * SystemConfigInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class SystemConfigInfoReq {
    @ApiModelProperty(notes = "setting key of system config", example = "email", required = true, position = 0)
    private String settingKey;
    
    @ApiModelProperty(notes = "Setting value of system config", example = "email", required = true, position = 0)
    private String settingValue;
    
    @ApiModelProperty(notes = "group code on system", example = "1", required = true, position = 0)
    private String groupCode;
    

    
    
}
