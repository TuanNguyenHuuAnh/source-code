package vn.com.unit.ep2p.admin.sla.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.workflow.dto.JpmSlaInfoDto;

/**
 * SlaInfoDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TuyenTD
 *
 */

@Getter
@Setter
public class SlaInfoDto extends JpmSlaInfoDto{

//	private Long id;
//	private Long businessId;
//	private Long companyId;
//	private Long processId;
//	private String slaName;
//	private String createdBy;
//	private Date createdDate;
//	private String updatedBy;
//	private Date updatedDate;
//	private String deletedBy;
//	private Date deletedDate;
	private List<SlaStepDto> stepList;
	private String businessCode;
	private String processDeployCode;
	private MessageList messageList;
	private String companyName;

}