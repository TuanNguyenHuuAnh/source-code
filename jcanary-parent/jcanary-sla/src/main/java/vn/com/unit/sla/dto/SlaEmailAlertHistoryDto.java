/*******************************************************************************
 * Class        ：SlaAlertHistoryDto
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：TrieuVD
 * Change log   ：2020/11/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * SlaAlertHistoryDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class SlaEmailAlertHistoryDto {

    private Long id;

    private String alertType;

    private Date alertDate;
    
    private String emailSubject;
    
    private String emailContent;

    private Integer status;
    
    private String responseJson;
    
    private List<SlaReceiverDto> slaReceiverDtoList;
}
