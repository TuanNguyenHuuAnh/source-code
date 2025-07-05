package vn.com.unit.cms.core.module.report.dto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ReportActiveDto {
	
	private String dataType; // phan biet (IN_MONTH, IN_YEAR1.....)
	
	//MTD
	private BigDecimal mtdSumbitAmount; 			//Số lượng HĐ nộp vào
	private BigDecimal mtdSumbitFyp; 				//FYP nộp vào
	private BigDecimal mtdReleaseAmount;			//Số lượng HĐ phát hành
	private BigDecimal mtdReleaseFyp;				//FYP phát hành
	private BigDecimal mtdActionAmount;			//Số lượng HĐ phát hành thuần
	private BigDecimal mtdActionFyp;				//FYP phát hành thuần
	private BigDecimal mtdCancelAmount;			//Số lượng HĐ hủy
	private BigDecimal mtdCancelFyp;				//FYP hủy
	private BigDecimal mtdRenewalsFyp;				//ĐC/ tái tục FYP
	private BigDecimal mtdPee2Yearyp;				//Phí >= năm 2 RYP
	private BigDecimal k2;							//K2%
	private BigDecimal k2plus;						//K2+%

	
	//YTD
	private BigDecimal ytdSumbitAmount; 			//Số lượng HĐ nộp vào
	private BigDecimal ytdSumbitFyp; 				//FYP nộp vào
	private BigDecimal ytdReleaseAmount;			//Số lượng HĐ phát hành
	private BigDecimal ytdReleaseFyp;				//FYP phát hành
	private BigDecimal ytdActionAmount;			//Số lượng HĐ phát hành thuần
	private BigDecimal ytdActionFyp;				//FYP phát hành thuần
	private BigDecimal ytdCancelAmount;			//Số lượng HĐ hủy
	private BigDecimal ytdCancelFyp;				//FYP hủy
	private BigDecimal ytdRenewalsFyp;				//ĐC/ tái tục FYP
	private BigDecimal ytdPee2Yearyp;				//Phí >= năm 2 RYP
	private BigDecimal	ytdK2;						//K2%
	private BigDecimal ytdK2Plus;					//K2+%

	
	//cung ky nam truoc YTD
	private BigDecimal ytdAgoSumbitAmount; 		//Số lượng HĐ nộp vào
	private BigDecimal ytdAgoSumbitFyp; 			//FYP nộp vào
	private BigDecimal ytdAgoReleaseAmount;		//Số lượng HĐ phát hành
	private BigDecimal ytdAgoReleaseFyp;			//FYP phát hành
	private BigDecimal ytdAgoActionAmount;			//Số lượng HĐ phát hành thuần
	private BigDecimal ytdAgoActionFyp;			//FYP phát hành thuần
	private BigDecimal ytdAgoCancelAmount;			//Số lượng HĐ hủy
	private BigDecimal ytdAgoCancelFyp;			//FYP hủy
	private BigDecimal ytdAgoRenewalsFyp;			//ĐC/ tái tục FYP
	private BigDecimal ytdAgoPee2Yearyp;			//Phí >= năm 2 RYP
	private BigDecimal	ytdAgoK2;					//K2%
	private BigDecimal ytdAgoK2Plus;				//K2+%
	
	
	// phat hanh thuan 1 thang truoc
	private Integer month1; 					// thang
	private BigDecimal policyAmount1;				//Số luong HĐ
	private BigDecimal fyp1;
	private BigDecimal k2Next1;
	private BigDecimal k2PlusNext1;
	
	// phat hanh thuan 2 thang truoc
	private Integer month2; 					// thang
	private BigDecimal policyAmount2;				//Số luong HĐ
	private BigDecimal fyp2;
	private BigDecimal k2Next2;
	private BigDecimal k2PlusNext2;
	
	
	// phat hanh thuan 3 thang truoc
	private Integer month3; 					// thang
	private BigDecimal policyAmount3;				//Số luong HĐ
	private BigDecimal fyp3;
	private BigDecimal k2Next3;
	private BigDecimal k2PlusNext3;
	
	
	
	
	
	
}
