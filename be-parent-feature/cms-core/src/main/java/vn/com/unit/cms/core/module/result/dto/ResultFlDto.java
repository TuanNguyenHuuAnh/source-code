package vn.com.unit.cms.core.module.result.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ResultFlDto {

	private BigDecimal k2PlusAdd;		 				  // Tỉ lệ duy trì HĐ (K2+) cần bổ sung
	private String k2PlusAddStr;		 				  // Tỉ lệ duy trì HĐ (K2+) cần bổ sung
	private BigDecimal afyp3monthAdd;          		 	  // AFYP 3 tháng tròn cần bổ sung
	private BigDecimal afyp6monthAdd;          		 	  // AFYP 6 tháng tròn cần bổ sung
	private BigDecimal afyp3AllMonthAdd; 			      // afyp 3 thang toan nhanh cần bổ sung
	private BigDecimal afyp6AllMonthAdd;
	private BigDecimal afc3monthAdd;           			  // AFC 3 tháng tròn cần bổ sung
	private BigDecimal afc6monthAdd;           			  // AFC 6 tháng tròn cần bổ sung
	private BigDecimal afcAdd; 					   		  // afc cần bổ sung

	private BigDecimal fyp3monthAdd;            		  // FYP 3 tháng tròn cần bổ sung
	private BigDecimal fyp6monthAdd;             		  // FYP 6 tháng tròn cần bổ sung
	private BigDecimal fyp3PastMonthAdd; 			      // fyp 3 thang qua cần bổ sung
	private BigDecimal fyp6PastMonthAdd;   			  	  // fyp 6 thang qua cần bổ sung
	private BigDecimal fyp3AllMonthAdd; 			      // fyp 3 thang toan nhanh cần bổ sung
	private BigDecimal fyp6AllMonthAdd;   			  	  // fyp 6 thang toan nhanh cần bổ sung

	private BigDecimal recruitment3monthAdd;     		  // Tuyển dụng 3 tháng tròn cần bổ sung
	private BigDecimal recruitment6monthAdd;    		  // Tuyển dụng 6 tháng tròn cần bổ sung
	private BigDecimal recruitmentAdd;    		  // Tuyển dụng cần bổ sung

	private BigDecimal subgroupUmSumAdd; 		  		  // Nhóm con phát triển (UM/SUM) cần bổ sung
	private BigDecimal subgroupPumAdd;    					  // Nhóm con phát triển (PUM) cần bổ sung
	private BigDecimal groupBmAdd;     			   		  // Phòng con phát triển (BM) cần bổ sung

	private BigDecimal quantityContractAdd;				  // SL HD cần bổ sung
	private BigDecimal quantityRecruitAdd;				  // SL tuyen dung cần bổ sung

	private BigDecimal quantityUmAdd;     				  // SL UM cần bổ sung
	private BigDecimal quantityPumAdd;	  				  // SL PUM cần bổ sung
	private BigDecimal quantitySumUmAdd; 		  		  // Số lượng nhóm con SUM/UM (PUM = 0.5)


	private BigDecimal dfaInGourpAdd;			  			  // DFA trong nhóm của TVTC
	private BigDecimal dfaSelectedByDfaAdd;	              // Số DFA do DFA tuyển của TVTC
	private BigDecimal monthJoinDfaAdd;		              // Số tháng tham gia kênh DFA
	private BigDecimal motnsOfTypeAdd;




}	
