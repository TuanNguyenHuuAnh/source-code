package vn.com.unit.cms.core.module.report.dto;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportGroupDetailExportDto {
	
	private Number no;
	private String territory;
	private String area;
	private String regoin;
	private String BDOH;
	private String office;
	private String gadCode;
	private String gadName;
	private String bmCode;
	private String bmName;
	private String umCode;
	private String umName;
	private String agentCodeTVTC;
	private String agentNameTVTC;
	
	private Number K2FeeToBePaidEPP;								//Phí cần đóng (EPP) 	(K2)
	private Number K2FeePaidAPP;									//Phí đã đóng (APP)		(K2)					
	private Number K2Ratio;											//Tỷ lệ %				(K2)
	private String K2FeeMissing7;									
	private String K2FeeMissing75;
	private String K2FeeMissing8;
	private String K2FeeMissing85;
	private String K2FeeMissing9;
	
	private Number KK2FeeToBePaidEPP;								//Phí cần đóng (EPP) 	(K2+)
	private Number KK2FeePaidAPP;									//Phí đã đóng (APP)		(K2+)
	private Number KK2Ratio;										//Tỷ lệ %				(K2+)
	private String KK2FeeMissing7;
	private String KK2FeeMissing75;
	private String KK2FeeMissing8;
	private String KK2FeeMissing85;
	private String KK2FeeMissing9;
	


}
