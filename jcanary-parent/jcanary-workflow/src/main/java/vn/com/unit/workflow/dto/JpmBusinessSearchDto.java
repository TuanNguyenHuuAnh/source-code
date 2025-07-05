/*******************************************************************************
 * Class        ：JpmBusinessSearchDto
 * Created date ：2020/12/07
 * Lasted date  ：2020/12/07
 * Author       ：KhuongTH
 * Change log   ：2020/12/07：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.workflow.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * <p> JpmBusinessSearchDto </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@Setter
public class JpmBusinessSearchDto {
    private Long companyId;
    private String businessName;
    private String businessCode;
    private String keySearch;
    private String processType;
}
