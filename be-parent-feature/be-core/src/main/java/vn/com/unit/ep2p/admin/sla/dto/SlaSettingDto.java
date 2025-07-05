package vn.com.unit.ep2p.admin.sla.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.sla.dto.SlaConfigDetailDto;

/**
 * @author TuyenTD
 *
 */

@Getter
@Setter
public class SlaSettingDto extends SlaConfigDetailDto {

	private List<SlaSettingAlertToDto> lstEmailToDto;

	private List<SlaSettingAlertToDto> lstEmailCcDto;
	
	private List<Long> lstEmailToId;

    private List<Long> lstEmailCcId;

	private Long calendarTypeId;
	
	private Long companyId;
	
	private Boolean autoAction;

	private Long actionButtonId;
	
	private Boolean isTransfer; 
	
}