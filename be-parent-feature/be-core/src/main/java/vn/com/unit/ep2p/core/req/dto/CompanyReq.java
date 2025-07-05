/*******************************************************************************
 * Class        ：CompanyReq
 * Created date ：2020/12/07
 * Lasted date  ：2020/12/07
 * Author       ：ngannh
 * Change log   ：2020/12/07：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * CompanyReq
 * 
 * @version 01-00
 * @since 01-00
 * @author NganNH
 */
@Getter
@Setter
public class CompanyReq {
    
    @ApiModelProperty(notes = "Name of company on system", example = "Unit Corp", required = true, position = 0)
    private String name;
    
    @ApiModelProperty(notes = "System name of company on system", example = "Unit_Corp", required = true, position = 0)
    private String systemName;
    

}
