package vn.com.unit.cms.core.module.sam.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivitityDetailDto {

    private Long activityId;

	private String partner;

	private Long orgLocationId;
	
	private String zoneCode;
	
	private String zone;

	private String area;
	
	private String regional;
	
	private String bu;
	
	@Getter(value = AccessLevel.NONE)
	private List<String> buLst;
	
	private String buCode;
	
	@Getter(value = AccessLevel.NONE)
	private List<String> buCodeLst;

	private Long categoryId;
	
	private String category;
	
	@Getter(value = AccessLevel.NONE)
	private String categoryDisplay;
	
	private String subject;

	private String content;

	private String type;

	private String form;
	
	private String actCode;
	
	private String buCount;

	private Long planId;
	
	private String planDate;

	private String personNumberPlan;

	private String costAmtPlan;

	private String salesAmtPlan;

	private String actualDate;

	private String personNumberActual;

	private String costAmtActual;

	private String salesAmtActual;

	private String participants;
	
	@Getter(value = AccessLevel.NONE)
	private List<String> participantLst;

	private List<AttachmentDto> activityAttachment;
    
    private String reportAttachment;
	
    private String reportAttachmentPhysical;
    
    private String reportAttachmentImg;
    
    private String cancelReason;
    
    private String actualResult;
    
    private String createdBy;
    
	public List<String> getBuLst() {
		if(bu.contains(",")) {
			buLst = Arrays.asList(bu.split(","));
		} else if (StringUtils.isNotBlank(bu)) {
			buLst = new ArrayList<>();
			buLst.add(bu);
		}
		
		return buLst;
	}

	public List<String> getBuCodeLst() {
		if(buCode.contains(",")) {
			buCodeLst = Arrays.asList(buCode.split(","));
		} else if (StringUtils.isNotBlank(buCode)) {
			buCodeLst = new ArrayList<>();
			buCodeLst.add(buCode);
		}
		return buCodeLst;
	}

	private boolean bacnkerChecked;
	private boolean buHeadChecked;
	private boolean gdBrachChecked;
	private boolean ioIsChecked;
	private boolean ilSmChecked;
	private boolean zdChecked;
	private boolean customerChecked;
	
	public List<String> getParticipantLst() {
		if(participants.contains(",")) {
			participantLst = Arrays.asList(participants.split(","));
		} else if (StringUtils.isNotBlank(participants)) {
			participantLst = new ArrayList<>();
			participantLst.add(participants);
		}
		
		for (String string : participantLst) {
			if ("1".equals(string)) {
		      setBacnkerChecked(true);
		    } else if ("2".equals(string)) {
		      setBuHeadChecked(true);
		    } else if ("3".equals(string)) {
		      setGdBrachChecked(true);
		    } else if ("4".equals(string)) {
		      setIoIsChecked(true);
		    } else if ("5".equals(string)) {
		      setIlSmChecked(true);
		    } else if ("6".equals(string)) {
		      setZdChecked(true);
		    } else if ("7".equals(string)) {
		      setCustomerChecked(true);
		    }
		}
		
		return participantLst;
	}

	private Long statusId;
	
	private String status;
	
	@Getter(value = AccessLevel.NONE)
	private String approvedStatus;

	private String approvedDate;

	@Getter(value = AccessLevel.NONE)
	private String reportedStatus;
	
	private String reportedDate;

	public String getApprovedStatus() {
		if(statusId == 3 || statusId == 4) {
			return "Đã duyệt";
		} else {
			return status;
		}
	}

	public String getReportedStatus() {
		if(statusId == 4) {
			return status;
		} else {
			return StringUtils.EMPTY;
		}
	}

	public String getCategoryDisplay() {
		categoryDisplay = category;
		if(StringUtils.isNotBlank(category) && category.length() > 25) {
			categoryDisplay = category.substring(0, 25) + ".....";
		}
		return categoryDisplay;
	}
}
