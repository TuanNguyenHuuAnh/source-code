/*******************************************************************************
 * Class        ：CaManagementUpdateReq
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：minhnv
 * Change log   ：2020/12/16：01-00 minhnv create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.CaManagementInfoReq;

/**
 * CaManagementUpdateReq
 * 
 * @version 01-00
 * @since 01-00
 * @author minhnv
 */
@Getter
@Setter
public class CaManagementUpdateReq extends CaManagementInfoReq {
    @ApiModelProperty(notes = "Id of ca on system", example = "1", required = true, position = 0)
    private Long caManagementId;
    @ApiModelProperty(notes = "id of company", example = "1", required = false, position = 6)
    private Long companyId;
    @ApiModelProperty(notes = "ca password", example = "Unit@123", required = false, position = 3)
    private String caPassword;
}