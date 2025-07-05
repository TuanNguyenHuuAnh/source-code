package vn.com.unit.cms.core.module.report.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportActiveContractMonthDto {
	private Integer no;														//STT
	private String policyKey;												//Số HĐBH
	private String poName;													//Bên mua bảo hiểm
	private String liName;													//Người được bảo hiểm
	private Date receivedDate;												//Ngày nộp
	private Date polIssueEff;												//Ngày phát hành
	private Integer polAgtShrPct;											//Tỷ lệ share
	private Integer fypIssue;												//FYP phát hành
	private Integer fypCancel;												//FYP hủy
	private Integer sumFyp;											 		//Tổng FYP = FYP phát hành – FYP Hũy
	private Integer fypPending;												//FYP đang xét
	private Integer fypReject;												//FYP từ chối
	private Integer sumFypall;												//Tổng  FYP Phát hành, FYP Hũy, FYP Tổng, FYP Đang xét, FYP Từ chối
		
	
}
