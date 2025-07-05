package vn.com.unit.cms.core.module.agent.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntroduceLetterDto {
	private Integer no;
	private Date effectiveDate;//ngay hieu luc
	private String agentCode;
	private String letterCategory;
	private String letterCategoryName;
	private Date createdDate;
	private Date endDate;
	private String oldAgentType;
	private String newAgentType;
	private String soHdbh; // so hop dong
	private String insuranceBuyName; // chu hop dong
	private String formOfDistribution; // hinh thuc phan cong
	private String nameFileLetter;
	private String movementType;
	private boolean shared;
	private boolean download;
	private boolean view;
	private String productCode;
	private String templateType;
	private String type;
	private Date assignDate;
	private String policyNo;
	// AGENT_CODE, LETTER_CATEGORY, 
    // LETTER_CATEGORY_NAME, CREATED_DATE, EFFECTIVE_DATE,
	// END_DATE, OLD_AGENT_TYPE, NEW_AGENT_TYPE, SO_HDBH, INSURANCE_BUY_NAME, FORM_OF_DISTRIBUTION,
	// NAME_FILE_LETTER, MOVEMENT_TYPE, SHARED, VIEW, PRODUCT_CODE, TEMPLATE_TYPE
}
