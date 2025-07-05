/*******************************************************************************
 * Class        ：CaManagementInfoReq
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：minhnv
 * Change log   ：2020/12/16：01-00 minhnv create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * CaManagementInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author minhnv
 */
@Setter
@Getter
public class CaManagementInfoReq {
    @ApiModelProperty(notes = "id of account", example = "1", required = true, position = 1)
    private Long accountId;
    @ApiModelProperty(notes = "ca slot", example = "1", required = true, position = 2)
    private String caSlot;
    @ApiModelProperty(notes = "ca name", example = "theanh", required = false, position = 4)
    private String caName;
    @ApiModelProperty(notes = "ca default", example = "true", required = false, position = 5)
    private boolean caDefault;
    @ApiModelProperty(notes = "ca label", example = "theanh", required = false, position = 8)
    private String caLabel;
    @ApiModelProperty(notes = "ca serial", example = "123", required = false, position = 9)
    private String caSerial;
    @ApiModelProperty(notes = "type of store", example = "1", required = false, position = 10)
    private int storeType;
}
