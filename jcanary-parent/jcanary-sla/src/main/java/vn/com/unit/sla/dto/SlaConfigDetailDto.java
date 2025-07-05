/*******************************************************************************
 * Class        ：SlaConfigDetailDto
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：TrieuVD
 * Change log   ：2020/11/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.dto;

import java.util.List;

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
public class SlaConfigDetailDto {

    private Long id;
    private Long slaConfigId;
    private Integer alertType;
    private Long alertTime;
    private Integer alertUnitTime;
    private Long emailTemplateId;
    private String emailTemplateName;
    private boolean emailSendFlag;
    private Long notiTemplateId;
    private String notiTemplateName;
    private boolean notiSendFlag;
    private boolean actived;
    private List<SlaConfigAlertToDto> alertToList;
    
    //SlaConfig
    private Long calendarTypeId;
    private Long slaDueTime;
    private Integer slaTimeType;
}
