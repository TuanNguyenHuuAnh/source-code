/*******************************************************************************
 * Class        ：PositionSearchDto
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：MinhNV
 * Change log   ：2020/12/01：01-00 MinhNV create a new
 ******************************************************************************/

package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * PositionSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
@Setter
@Getter
@NoArgsConstructor
public class JcaPositionSearchDto {

    /** code */
    private String code;
    /** name */
    private String name;
    /** nameAbv */
    private String nameAbv;
    /** description */
    private String description;

    private int pageSize;
    private int pageIndex;
    private int isPaging;

    private String keySearch;
    private Long companyId;
    private Boolean actived;
}
