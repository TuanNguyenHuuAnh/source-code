/*******************************************************************************
 * Class        ：EfoComponentDto
 * Created date ：2020/12/31
 * Lasted date  ：2020/12/31
 * Author       ：taitt
 * Change log   ：2020/12/31：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * EfoComponentDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class EfoComponentDto {

    private Long componentId;
    private Long formId;
    private String compId;
    private String compName;
    private Long displayOrder;
    private String activeFlag;
    private String createdBy;
}
