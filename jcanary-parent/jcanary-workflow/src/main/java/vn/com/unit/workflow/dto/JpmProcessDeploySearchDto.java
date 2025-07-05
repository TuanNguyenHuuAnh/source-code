/*******************************************************************************
 * Class        ：JpmProcessDeploySearchDto
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：KhuongTH
 * Change log   ：2020/12/15：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.workflow.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * JpmProcessDeploySearchDto
 * </p>
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@Setter
public class JpmProcessDeploySearchDto {
    private Long companyId;
    private String processName;
    private String processCode;
    private String keySearch;
    private Long businessId;
    private Long processId;
    private Date fromDate;
    private Date toDate;
}
