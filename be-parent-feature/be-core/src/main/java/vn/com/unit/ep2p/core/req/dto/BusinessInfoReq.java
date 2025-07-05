/*******************************************************************************
 * Class        ：BusinessInfoReq
 * Created date ：2020/12/07
 * Lasted date  ：2020/12/07
 * Author       ：KhuongTH
 * Change log   ：2020/12/07：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p> BusinessInfoReq </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@Setter
public class BusinessInfoReq {

    @ApiModelProperty(notes = "name of business on system", example = "Business", required = true, position = 0)
    private String businessName;
    
    @ApiModelProperty(notes = "description of business", example = "Business", required = true, position = 0)
    private String description;
    
    @ApiModelProperty(notes = "id of company", example = "1", required = true, position = 0)
    private Long companyId;
    
    @ApiModelProperty(notes = "type of business process", example = "3", required = true, position = 0)
    private Integer processType;
    
    @ApiModelProperty(notes = "authority for process", example = "true", required = true, position = 0)
    private boolean authority;
    
    @ApiModelProperty(notes = "active business", example = "true", required = true, position = 0)
    private boolean actived;
}
