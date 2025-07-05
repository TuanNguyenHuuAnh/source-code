package vn.com.unit.cms.core.module.result.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDto extends ResultFlDto {

	private String no;
	private String agentCode;
	private String agentName;
	private String agentType;
	private String agentTitle;

	private String evaluatePromotionName;
	private String agentTypeName;

	private String territoryCode;
	private String territoryName;
	private String areaCode;
	private String areaName;
	private String regionCode;
	private String regionName;
	private String officeCode;
	private String officeName;

	private String bdth;
	private String bdah;
	private String bdrh;
	private String bdoh;
	private String agent;
	private String office;

	private String mannger;
	private String leader;
	private String gad;								  //van phong/tong dai ly
	private String title;				     	     // danh hiệu
	private String childGroup;


	private BigDecimal k2Plus;		     			     // Tỉ lệ duy trì HĐ (K2+)
	private String k2PlusStr;		     			     // Tỉ lệ duy trì HĐ (K2+)
	private BigDecimal fyp3month;             			 // FYP 3 tháng tròn
	private BigDecimal fyp6month;             			 // FYP 6 tháng tròn
	private BigDecimal fyp3PastMonth;  		    		 // fyp 3 thang qua
	private BigDecimal fyp6PastMonth;    				 // fyp 6 thang qua
	private BigDecimal fyp6AllMonth;    				 // 6 thang toan nhanh
	private BigDecimal fypYear;				   	 		 // FYP năm
	private BigDecimal fyp6groupMonth;

	private BigDecimal afyp3month;           			 // AFYP 3 tháng tròn
	private BigDecimal afyp6month;           			 // AFYP 6 tháng tròn
	private BigDecimal afyp6AllMonth;    				 // 6 thang toan nhanh
	private BigDecimal afc3month;						 // AFC 3 tháng
	private BigDecimal afc6month;				 		 // AFC 6 EVALUATEPROMOTION
	private BigDecimal afc;					     		 // AFC

	private BigDecimal recruitment3month;    			 // Tuyển dụng 3 tháng tròn
	private BigDecimal recruitment6month;    			 // Tuyển dụng 6 tháng tròn
	private BigDecimal recruitment;				     	     // Tuyển dụng

	private BigDecimal subgroupUmSum; 		  	    	  // Nhóm con phát triển (UM/SUM)
	private BigDecimal subgroupPum;     		  	  	  // Nhóm con phát triển (PUM)
	private BigDecimal groupBm;      			          // Phòng con phát triển (BM)
	private BigDecimal subgroupSumUm; 		  		 	  // Số lượng nhóm con SUM/UM (PUM = 0.5)

	private BigDecimal monthsOfServiceToTerm;   		      // Số tháng phục vụ đến kỳ xem xét
	private BigDecimal monthsSum;				 		      // Số tháng làm SUM
	private BigDecimal monthsBm;				 		      // Số tháng làm BM
	private BigDecimal quantityUm;					          // Số lượng Um
	private BigDecimal	quantityPum;				          // số lượng Pum

	private Date dateWonTitle;   		  	   		  // ngay danh dc danh hieu
	private Date dateReviewPromoUm;			   		  // Ngày xét thăng chức Um
	private Date dateReviewPromoPUm;			   		  // Ngày xét thăng chức Um
	private Date datePromo;			    	   		  // Ngày thăng chức
	private Date appointmentDate;		       		  // Ngày bổ nhiệm
	private Date workingDay; 			       		  // ngay lam viec
	private Date dateReviewPromoOrDemo;        		  // Ngày xét thăng chức/giáng chức
	private Date dateJoinDfa;			       		  // Ngày tham gia kênh DFA

	
	private BigDecimal dfaInGourp;			  			  // DFA trong nhóm của TVTC
	private BigDecimal dfaSelectedByDfa;	              // Số DFA do DFA tuyển của TVTC
	private BigDecimal monthJoinDfa;		              // Số tháng tham gia kênh DFA
	private String motnsOfType;
	
	private BigDecimal quantityContract;				  // Số lượng HĐ phát hành thuần
	private BigDecimal quatityContectService;       	  // SL HD PHUC VU

	private BigDecimal quantitySumUm;
	private BigDecimal quantityWonTitle;     	    	  // so thang dat danh hieu

	private String lastYearMdrt;	      			  // Hiển thị MDRT năm trước của TVTC
	private String monthsOfSegment;		  			  // Hiển thị Months of Segment của TVTC
	private String dateOfType;
	private String monthsOfType;
	private String segment;
	private String goldenUm; 			   			  // Tham gia Golden
	private Date dateOfSegment;		       			  // Hiển thị Date of Segment của TVTC

	private String evaluatePromotion;      			  // Đánh giá lên chức
	private String evaluateDemote;		   			  // Đánh giá hạ bậc
	private String evaluateTerm;		   			  // Đánh giá phân hạng
	private String term;
	private String mdrtLastYear; 
	private String monthOfSegment; 
	
	private String getPromoted;            			  // lên chức
	private String resignedFromoffice;	   			  // Xuống chức
	private String rankUp;				   			  // Lên hạng
	private String rankDown;			   			  // Hạ hạng

	private String resultOfEvaluation; 				  //  Kết quả Đánh giá













}	
