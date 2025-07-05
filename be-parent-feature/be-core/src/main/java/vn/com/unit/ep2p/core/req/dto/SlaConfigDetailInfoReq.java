/*******************************************************************************
 * Class        ：SlaConfigDetailInfoReq
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：TrieuVD
 * Change log   ：2021/01/25：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * SlaConfigDetailInfoReq
 * </p>
 * 
 *
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class SlaConfigDetailInfoReq {

    @ApiModelProperty(notes = "configId of sla config detail on system", example = "0", required = true, position = 0)
    private Long slaConfigId;

    @ApiModelProperty(notes = "alert type of sla config detail on system", example = "NOTIFICATION", required = true, position = 1)
    private String alertType;

    @ApiModelProperty(notes = "alert time of sla config detail on system", example = "0", required = true, position = 2)
    private Long alertTime;

    @ApiModelProperty(notes = "alertUnitTime of sla config detail on system", example = "0", required = true, position = 3)
    private String alertUnitTime;

    @ApiModelProperty(notes = "templateId of sla config detail on system", example = "0", required = true, position = 4)
    private Long templateId;

    @ApiModelProperty(notes = "sendMailFlag of sla config detail on system", example = "true", required = true, position = 5)
    private boolean sendMailFlag;

    @ApiModelProperty(notes = "sendNotiFlag of sla config detail on system", example = "true", required = true, position = 6)
    private boolean sendNotiFlag;

    @ApiModelProperty(notes = "activeFlag of sla config detail on system", example = "true", required = true, position = 7)
    private boolean activeFlag;

    @ApiModelProperty(notes = "alertToList of sla config detail on system", position = 8)
    private List<SlaConfigAlertToReq> alertToList;

}
