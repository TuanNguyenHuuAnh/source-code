/*******************************************************************************
 * Class        ：JcaSystemConfigSearchDto
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：ngannh
 * Change log   ：2020/12/16：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * JcaSystemConfigSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class JcaSystemConfigSearchDto {
    private Long companyId;
    private String groupCode;
    
    private String key;
    private String value;
}
