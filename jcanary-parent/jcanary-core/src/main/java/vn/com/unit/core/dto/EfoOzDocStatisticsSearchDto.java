/*******************************************************************************
 * Class        ：EfoOzDocStatisticsSearchDto
 * Created date ：2021/02/03
 * Lasted date  ：2021/02/03
 * Author       ：taitt
 * Change log   ：2021/02/03：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * EfoOzDocStatisticsSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class EfoOzDocStatisticsSearchDto extends EfoDocumentFilterSearchDto{
    private List<String> lstUnApprovedDocCat;
    private List<String> lstRejectedDocCat;
    private List<String> lstApprovedDocCat;
}
