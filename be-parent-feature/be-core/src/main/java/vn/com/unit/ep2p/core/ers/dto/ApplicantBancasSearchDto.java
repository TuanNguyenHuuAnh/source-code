package vn.com.unit.ep2p.core.ers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicantBancasSearchDto {

    private String applyForPosition;
    
    private String statusProcess;

    private String statusForm;
    
    private String regionName;

    private String idNo;

    private String candidateName;

    private String recruiterCode;

    private String recruiterName;

    private String avicadStatus;

    private String classCode;
    
    private Long userId;
    
    private String language;
    
    private int hasViewAll;
}
