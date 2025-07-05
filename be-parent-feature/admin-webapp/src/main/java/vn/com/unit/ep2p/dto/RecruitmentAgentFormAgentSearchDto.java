package vn.com.unit.ep2p.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.core.dto.ConditionSearchCommonDto;

@Getter
@Setter
@NoArgsConstructor
public class RecruitmentAgentFormAgentSearchDto extends ConditionSearchCommonDto {

	private String positionCandidacy;
	
	private Date province;
	
	private Date assignDateAdFrom;
	
	private Integer status;
	
	private String adCode;
	
	private String recruiterCode;
}
