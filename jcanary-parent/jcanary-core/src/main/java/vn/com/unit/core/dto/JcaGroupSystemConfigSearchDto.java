/*******************************************************************************
 * Class        ：JcaGroupSystemConfigSearchDto
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：ngannh
 * Change log   ：2020/12/15：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * JcaGroupSystemConfigSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class JcaGroupSystemConfigSearchDto {
    private Long companyId;
    private String code;
    private String name;
}