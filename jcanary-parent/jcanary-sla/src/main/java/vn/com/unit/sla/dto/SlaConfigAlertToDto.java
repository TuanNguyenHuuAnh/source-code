/*******************************************************************************
 * Class        ：SlaConfigAlertToDto
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：TrieuVD
 * Change log   ：2021/01/13：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * SlaConfigAlertToDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class SlaConfigAlertToDto {

    private Long id;

    private Long slaConfigDetailId;
    
    private Long involedType;
    
    private Long receiverId;
    
    private Integer receiverType;
}
