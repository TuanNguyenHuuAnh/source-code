/*******************************************************************************
 * Class        ：NewsSearchDto
 * Created date ：2017/02/23
 * Lasted date  ：2017/02/23
 * Author       ：hand
 * Change log   ：2017/02/23：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.news.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeNewsSearchDto {

    private String[] ignoreNews;
    
    private Long mNewsCategoryId;
    private String key;
    private Integer homepage;
    private Long idHotNews;
    private String codeNewsType;
}
