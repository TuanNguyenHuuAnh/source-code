/*******************************************************************************
 * Class        ：JpmSlaConfigDto
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.workflow.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.sla.dto.AbstractSlaConfigDto;

/**
 * <p>
 * JpmSlaConfigDto
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
public class JpmSlaConfigDto extends AbstractSlaConfigDto {
    
    private Long jpmSlaConfigId;
    private Long jpmSlaInfoId;
    private Long businessId;
    private String businessName;
    private Long processDeployId;
    private String processDeployName;
    private Long stepDeployId;
    private String stepDeployCode;
    private String stepDeployName;
    private String slaCalendarType;
}
