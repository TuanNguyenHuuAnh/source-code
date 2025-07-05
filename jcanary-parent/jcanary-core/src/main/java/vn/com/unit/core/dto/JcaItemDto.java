/*******************************************************************************
 * Class        ：JcaItemDto
 * Created date ：2020/12/07
 * Lasted date  ：2020/12/07
 * Author       ：MinhNV
 * Change log   ：2020/12/07：01-00 MinhNV create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaItemDto
 * 
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
@Setter
@Getter
@NoArgsConstructor
public class JcaItemDto extends AbstractTracking{
   

    private Long itemId;
    private String functionCode;
    private String companyName;
    private String functionName;
    private String description;
    private String functionType;
    private int displayOrder;
    private Long companyId;
}
