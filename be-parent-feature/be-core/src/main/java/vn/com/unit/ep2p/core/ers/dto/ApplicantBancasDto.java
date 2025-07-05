package vn.com.unit.ep2p.core.ers.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicantBancasDto {
    protected static final String DATE_PATTERN = "dd/MM/yyyy";
    protected static final String TIME_ZONE = "Asia/Saigon";

    private Long no;
    
    private Long id;
    
    private String applyForPosition;
    
    private String regionName;
    
    private String agentCode;
    
    private String bpCode;
    
    private String candidateName;
    
    @JsonFormat(pattern = DATE_PATTERN, timezone = TIME_ZONE)
    private Date dob;
    
    private String idNo;
    
    @JsonFormat(pattern = DATE_PATTERN, timezone = TIME_ZONE)
    private Date idDateOfIssue;
    
    private String idPlaceOfIssueName;
    
    private Long recruiterCode;
    
    private String recruiterName;
    
    private String classCode;
    
    private String examResult;
    
    private String statusProcess;

    private String statusForm;
    
    private Integer checkAvicadStatus;
    
    private String avicadStatus;
    
    private String createdBy;
    
    private String classNote;
    
    @JsonFormat(pattern = DATE_PATTERN, timezone = TIME_ZONE)
    private Date createdDate;
    
    private int hasEdit;

    private Long formId;
    
    private int hasDelete;
}
