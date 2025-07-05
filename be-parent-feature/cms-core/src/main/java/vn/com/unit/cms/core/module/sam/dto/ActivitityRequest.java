package vn.com.unit.cms.core.module.sam.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivitityRequest {

    private Long id;

	private Long orgLocationId;
    
	private String channel;
	
	private String partner;

	private String zone;
	
	private String zoneCode;
	
	private String zdCode;
	
	private String regional;

	private String area;

	private String bu;
	
	private String buCode;
	
	private Integer buCount;
	
	private Long categoryId;
	
	private String category;
	
	private String subject;

	private String content;

	private String type;

	private String form;
	
	private String actCode;
	
	private Long planId;
	
	private String planDate;

	private Integer personNumberPlan;

	private Double costAmtPlan;

	private Double salesAmtPlan;

	private String actualDate;

	private Integer personNumberActual;

	private Double costAmtActual;

	private Double salesAmtActual;

	private String participants;

	private Long oldStatusId;
	
	private String oldStatus;

	private Long newStatusId;
	
	private String newStatus;

	private String approvedDate;

	private String reportedDate;
	
	private String agentCode;
	
	private String agentType;
	
	private String agentName;
	
	private String cancelReason;
	
	private String result;
}
