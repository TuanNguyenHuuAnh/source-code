/*******************************************************************************
 * Class        ：SlaAccountDto
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：TrieuVD
 * Change log   ：2021/01/13：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SlaAccountDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
@NoArgsConstructor
public class SlaReceiverDto {
    private Long alertId;
    private Long receiverId;
    private Integer receiverType;

    private String businessEmail;

    public SlaReceiverDto(Long receiverId) {
        super();
        this.receiverId = receiverId;
    }
    
    public SlaReceiverDto(Long receiverId, String businessEmail) {
        super();
        this.receiverId = receiverId;
        this.businessEmail = businessEmail;
    }
}
