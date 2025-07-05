/*******************************************************************************
 * Class        ：ItemSearchDto
 * Created date ：2020/12/08
 * Lasted date  ：2020/12/08
 * Author       ：MinhNV
 * Change log   ：2020/12/08：01-00 MinhNV create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * TeamSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
@Setter
@Getter
@NoArgsConstructor
public class JcaTeamSearchDto{

    private String code;
    private String name;
    private String nameAbv;
    private String description;

    private int pageSize;
    private int pageIndex;
    private int isPaging;

    private String keySearch;
    private Long companyId;
    private Boolean actived;
}
