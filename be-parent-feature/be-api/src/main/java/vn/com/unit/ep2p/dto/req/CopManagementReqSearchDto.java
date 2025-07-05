package vn.com.unit.ep2p.dto.req;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CopManagementReqSearchDto {

//	@ApiModelProperty(notes = "Start Date from of class format dd/MM/yyyy", example = "2021-04-09T08:07:11.860Z", required = true, position = 0)
//	private String studyDateFrom;
//	
//	@ApiModelProperty(notes = "Start Date to of class format dd/MM/yyyy", example = "2021-04-09T08:07:11.860Z", required = true, position = 0)
//	private String studyDateTo;
	
	@ApiModelProperty(notes = "CandidateName of class", example = "candidateName", required = true, position = 0)
	private String candidateName;
	
	@ApiModelProperty(notes = "Id no of class", example = "123456789", required = true, position = 0)
	private String idNo;
	
	private Date startDateFrom;
    
    private Date startDateTo;
	
}
