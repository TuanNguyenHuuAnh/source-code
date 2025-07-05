/*******************************************************************************
 * Class        ：JcaNotiTemplateSearchDto
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * JcaNotiTemplateSearchDto
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
public class JcaNotiTemplateSearchDto {

    /** The name. */
    private String name;

    /** The code. */
    private String code;

    /** The company id. */
    private Long companyId;
}
