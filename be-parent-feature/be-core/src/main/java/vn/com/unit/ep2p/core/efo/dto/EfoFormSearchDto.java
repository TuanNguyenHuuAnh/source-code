/*******************************************************************************
 * Class        ：EfoFormSearchDto
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：taitt
 * Change log   ：2020/12/23：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * EfoFormSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class EfoFormSearchDto {

    private Long companyId;
    private Long categoryId;
    private String description;
    private String formName;
    private String fileName;
    private Long formId;
    private String formType;
    private List<Long> companyIdList;
}
