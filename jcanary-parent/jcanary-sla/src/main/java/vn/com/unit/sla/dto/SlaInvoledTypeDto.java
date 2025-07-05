/*******************************************************************************
 * Class        ：SlaConfigDetailDto
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：TrieuVD
 * Change log   ：2020/11/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * SlaConfigDetailDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class SlaInvoledTypeDto {

    private Long id;
    private String code;
    private String langId;
    private String langCode;
    private String name;
}
