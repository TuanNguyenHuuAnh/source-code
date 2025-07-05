/*******************************************************************************
 * Class        ：JpmSlaInfoSearchDto
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
 * JpmSlaInfoSearchDto
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
public class JpmSlaInfoSearchDto {
    
    private Long businessId;
    private Long processDeployId;
    private Long companyId;
    private String name;
}
