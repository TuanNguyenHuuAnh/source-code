package vn.com.unit.ep2p.adp.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactHistoryDto {
	private Long id;
	private Integer no;
	private String policyNo;
	private Date dueDate;
	private String contactStage;
	private Integer contactStageDays;
	private String contactGroup;
	private String agentCode;
	private String agentName;
	private String contactMethod;
	private String contactMethodText;
	private String contactResult;
	private String contactResultText;
	private Date contactDate;
	private String notes;
	private Date updatedTime;
}
