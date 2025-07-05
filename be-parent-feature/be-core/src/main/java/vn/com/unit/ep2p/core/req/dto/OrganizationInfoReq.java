/*******************************************************************************
 * Class        ：OrganizationInfoReq
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：SonND
 * Change log   ：2020/12/15：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * OrganizationInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class OrganizationInfoReq {

    @ApiModelProperty(notes = "Name of organization", example = "Root", required = true, position = 0)
    private String name;
    @ApiModelProperty(notes = "Other name of organization", example = "Source", required = true, position = 0)
    private String nameAbv;
    @ApiModelProperty(notes = "Effectived date of organization", example = "20210106", required = true, position = 0)
    private String effectivedDate;
    @ApiModelProperty(notes = "Expired date of organization", example = "20210106", position = 0)
    private String expiredDate;
    @ApiModelProperty(notes = "Organization order", example = "1",  position = 0)
    private Integer orgOrder;
    @ApiModelProperty(notes = "Id parent of organization", example = "1", required = true, position = 0)
    private Long orgParentId;
    @ApiModelProperty(notes = "Type of organization", example = "T", position = 0)
    private String orgType;
    @ApiModelProperty(notes = "Email", example = "abc@gmail.com", position = 0)
    private String email;
    @ApiModelProperty(notes = "Id of company", example = "1", position = 0)
    private Long companyId;
    @ApiModelProperty(notes = "Phone", example = "02324343412", position = 0)
    private String phone;
    @ApiModelProperty(notes = "Address", example = "TP. Ho Chi Minh", position = 0)
    private String address;
    @ApiModelProperty(notes = "Organization active", example = "1", position = 0)
    private boolean actived;
}
