package vn.com.unit.cms.core.module.agent.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDto {

	private byte[] base64;
	private String no;
	private String fileName;
	private String agentCode;
	private String fileType;
	private String baseString;
	private String category;
	private String agentMovementId;
	private String assignDate;
	private String responseCode;
	private String responseMessage;
	private String templateType;
	private String policyNo;
	private String letterCategory;
	private String effectiveDate;
}
