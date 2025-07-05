package vn.com.unit.ep2p.admin.sla.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.workflow.dto.JpmSlaConfigDto;

/**
 * @author TuyenTD
 *
 */
@Getter
@Setter
public class SlaStepDto extends JpmSlaConfigDto {

	private Long id;
	
	private Long slaInfoId;
	
	private boolean stepFinished;

	private SlaSettingDto notification;
	
	private List<SlaSettingDto> notificationList;

	private List<SlaSettingDto> reminderList;

	private List<SlaSettingDto> escalateList;
	
	private List<SlaSettingDto> transferList;

	private Boolean autoAction;

	private Long actionButtonId;

	private String buttonCode;

	private MessageList messageList;
	
	private String noNotificationMessage;
	
	private String noReminderMessage;
	
	private String noEscalateMessage;
	
	private Long companyId;
}