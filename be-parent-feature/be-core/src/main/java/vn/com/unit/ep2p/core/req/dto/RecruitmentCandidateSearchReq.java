package vn.com.unit.ep2p.core.req.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecruitmentCandidateSearchReq {
	
	private String positionCandidacy;
	
	private Date province;
	
	private Date assignDateAdFrom;
	
	private Integer status;
	
	private String adCode;
	
	private String recruiterCode;
	
	private Integer page;
	
	private Integer pageSize;
}
