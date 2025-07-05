package vn.com.unit.cms.core.module.report.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportResultViewGADTabManPowerDto {
	private Integer countBdthCode;
	private Integer countBdahCode;
	private Integer countBdrhCode;
	private Integer countBdohCode;
	private Integer countGadCode;
	private Integer countOfficeCode;
	private Integer countOrgCode;
	
	private Integer countBmAgentcode;
	private Integer countUmAgentcode;
	private Integer countFcAgentcode;
	
	private Integer countBmTypeAgentcode;//sl
	private Integer countUmTypeAgentcode;
	private Integer countPumTypeAgentcode;
	private Integer countFcTypeAgentcode;
	private Integer countSaTypeAgentcode;
	
	private Integer countNewRecruitment;       //Số lượng Tuyển dụng
	private Integer countReinstate;            //Số lượng khôi phục mã số
	private Integer countActive;               //Số lượng FC hoạt động
	private Integer countNewRecruitmentActive; //Số lượng FC mới hoạt động
	private Integer countSchemeFc;             //Số lượng FC hoạt động theo scheme
	private Integer countPfcFc;                //Số lượng FC năng động (PFC)
	private Integer sumFyp;
	private Integer sumRyp;
	private Integer lastDate;
	private Integer lastCountNewRecruitment;   //Số lượng Tuyển dụng
	private Integer lastCountReinstate;        //Số lượng khôi phục mã số
	private Integer lastCountActive;           //Số lượng hoạt động
	private Integer lastCountNewRecruitmentActive;     //Số lượng TV mới hoạt động
	private Integer lastCountSchemeFc;
	private Integer lastCountPfcFc;
	private Integer lastSumFyp;
	private Integer lastSumRyp;
}
