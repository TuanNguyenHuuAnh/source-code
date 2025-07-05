package vn.com.unit.ep2p.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SamAttachmentInfoDto {
	private Long activityId;
	private String agentCode;
	private String activityCode;
	private String activityType;
	private String attachmentPhysical;
}