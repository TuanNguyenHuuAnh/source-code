package vn.com.unit.cms.core.module.contract.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaimAdditionalInformationDto {
	private String claimno;
	private String documentname;
	private String assessorcomment;
	private String claimantcomment;
	private Date submitteddate;
	private Date expireddate;

}


     
