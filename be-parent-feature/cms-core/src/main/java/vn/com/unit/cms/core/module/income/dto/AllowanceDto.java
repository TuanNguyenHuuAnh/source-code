package vn.com.unit.cms.core.module.income.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllowanceDto {//Phụ cấp
	private Integer rewardEmulationProgram;			//Thưởng chương trình thi đua
	private Integer businessSupportAndSeminars;		//Hỗ trợ kinh doanh và hội thảo
	private Integer total;
}
