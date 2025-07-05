package vn.com.unit.cms.core.module.sam.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivitiesDto {

    private Long activityId;

	private String partner;

	private String zone;

	private String bu;
	
	private String buCode;

	private String category;
	
	private String actCode;
	
	private String status;

	private Long statusId;
	
	private String planDate;
	
	private Integer no;
}
