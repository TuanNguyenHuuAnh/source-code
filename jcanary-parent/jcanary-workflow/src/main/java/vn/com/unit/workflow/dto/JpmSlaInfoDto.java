/*******************************************************************************
 * Class        ：JpmSlaInfoDto
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.workflow.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * JpmSlaInfoDto
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
public class JpmSlaInfoDto {
    
    private Long id;
    private String name;
    private Long businessId;
    private String businessName;
    private Long processDeployId;
    private String processDeployName;
    private Long slaCalendarTypeId;
    private String slaCalendarType;
    private Long companyId;
    private String companyName;
}
