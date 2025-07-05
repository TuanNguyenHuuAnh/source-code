/*******************************************************************************
 * Class        ：SlaAlertDto
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
 * SlaAlertDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class SlaNotiAlertDto {

    private Long id;

    private Integer alertType;

    private Date alertDate;
    
    private Long notiTemplateId;

    private String notiJsonData;
    
    private String notiTitle;

    private String notiContent;

    private Integer status;
    
    private List<SlaReceiverDto> slaReceiverDtoList;
    
}
