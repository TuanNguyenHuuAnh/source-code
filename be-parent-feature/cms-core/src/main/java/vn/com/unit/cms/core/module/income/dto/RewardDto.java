package vn.com.unit.cms.core.module.income.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RewardDto {//Tổng thưởng
	private Integer quarterlyProfessionalBonus;			//Thưởng chuyên nghiệp quí
	private Integer totalBonusTeamLeaderLevel;			//Tổng Thưởng theo cấp Trưởng Nhóm
	private Integer	salesBonusDirectGroup;				//Thưởng doanh số trực tiếp Nhóm
	private Integer salesBonusRenewalsGroup;			//Thưởng doanh số phí tái tục Nhóm
	private Integer totalBonusHeadOfDepartment;			//Tổng Thưởng theo cấp Trưởng Phòng
	private Integer	salesBonusDirectDepartment;			//Thưởng doanh số trực tiếp Phòng
	private Integer	salesBonusIndirectDepartment;		//Thưởng doanh số gián tiếp Phòng



}
