/*******************************************************************************
 * Class        ：JpmSlaConfigSearchDto
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
 * JpmSlaConfigSearchDto
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
public class JpmSlaConfigSearchDto {
    
    private Long jpmSlaInfoId;
    private Long businessId;
    private Long processDeployId;
    private Long stepDeployId;
    private Long langId;
    private String langCode;
    
}
