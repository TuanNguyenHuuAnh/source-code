package vn.com.unit.cms.core.module.reportGroup;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.module.agent.dto.InfoAgentDto;

@Getter
@Setter
public class ReportGroupResultPremiumDetailDto {

 
	//Lưới doanh số (Premium) của tháng báo cáo
	 private BigDecimal policyCountReceived;				// Tổng hồ sơ nộp vào của từng BDTH, BDAH
	 private BigDecimal fypReceived;						// Tổng FYP của từng BDTH, BDAH
	 private BigDecimal policyCountIssued;					// Tổng hợp đồng phát hành của từng BDTH, BDAH
	 private BigDecimal fypIssued;							// Tổng FYP theo hợp đồng phát hành của từng BDTH, BDAH
	 private BigDecimal policyCount;						// Tổng hợp đồng phát hành thuần của từng BDTH, BDAH
	 private BigDecimal fyp;								// Tổng FYP theo hợp đồng phát hành thuần của từng BDTH, BDAH
	 private BigDecimal policyCountCancel;					// Tổng hợp đồng hủy của từng BDTH, BDAH
	 private BigDecimal fypCancel;							// Tổng FYP theo hợp đồng hủy của từng BDTH, BDAH
	 private BigDecimal rfyp;								// Tổng điều chỉnh/Tái tục FYP của từng BDTH, BDAH
	 private BigDecimal ryp;								// Tổng RYP của từng BDTH, BDAH
	 private String k2;								// Tỉ lệ %K2 của từng BDTH, BDAH
	 private String k2Plus;							// Tỉ lệ %K2+ của từng BDTH, BDAH
	 private String office;								// Hiển thị tất cả Văn phòng /TĐL của BDOH được chọn theo cây cấu trúc của BDRH
	 private String manager;							// Hiển thị tất cả Trưởng phòng của Văn phòng /TĐL được chọn theo cây cấu trúc của BDOH
	 private String lv2Agentname;						// Hiển thị tất cả BM reporting to theo cây cấu trúc của BM 
	 private String lv3Agentname;						// Hiển thị tất cả TVTC của BM reporting to theo cây cấu trúc của BM
	 private String lv2Agentcode;
	 private String lv3Agentcode;
	 private String lv2Agenttype;
	 private String lv3Agenttype;
	 private String lv1Agentname;						// Hiển thị tất cả TVTC của BM reporting to theo cây cấu trúc của BM
	 private String lv1Agentcode;
	 private String lv1Agenttype;
	 private BigDecimal k2Epp;								
	 private BigDecimal k2plusEpp;		
	 private BigDecimal k2App;								
	 private BigDecimal k2plusApp;
	 private String orgNameNew;
		
	private String paren;
	private String child;
	 
	 //cung ky nam trc
	 private BigDecimal lastPolicyCountReceived;			// Tổng hồ sơ nộp vào của từng BDTH, BDAH theo tháng cùng kỳ năm trước	
	 private BigDecimal lastFypReceived;					// Tổng FYP của từng BDTH, BDAH theo tháng cùng kỳ năm trước
	 private BigDecimal lastPolicyCountIssued;				// Tổng hợp đồng phát hành của từng BDTH, BDAH theo tháng cùng kỳ năm trước
	 private BigDecimal lastFypIssued;						// Tổng FYP theo hợp đồng phát hành của từng BDTH, BDAH theo tháng cùng kỳ năm trước
	 private BigDecimal lastPolicyCount;					// Tổng hợp đồng phát hành thuần của từng BDTH, BDAH theo tháng cùng kỳ năm trước
	 private BigDecimal lastFyp;							// Tổng FYP theo hợp đồng phát hành thuần của từng BDTH, BDAH theo tháng cùng kỳ năm trước
	 private BigDecimal lastPolicyCountCancel;				// Tổng hợp đồng hủy của từng BDTH, BDAH theo tháng cùng kỳ năm trước
	 private BigDecimal lastFypCancel;						// Tổng FYP theo hợp đồng hủy của từng BDTH, BDAH theo tháng cùng kỳ năm trước
	 private BigDecimal lastRfyp;							// Tổng điều chỉnh/Tái tục FYP của từng BDTH, BDAH theo tháng cùng kỳ năm trước
	 private BigDecimal lastRyp;							// Tổng RYP của từng BDTH, BDAH theo tháng cùng kỳ năm trước
	 private BigDecimal lastK2Epp;								
	 private BigDecimal lastK2plusEpp;		
	 private BigDecimal lastK2App;								
	 private BigDecimal lastK2plusApp;	
	 

	 private BigDecimal lm3PolicyCount;			// Tổng hợp đồng phát hành thuần tháng 2 (M-3)
	 private BigDecimal lm3Fyp;					// Tổng FYP phát hành thuần tháng 2 (M-3)
	 private BigDecimal lm2PolicyCount;			// Tổng hợp đồng phát hành thuần tháng 3 (M-2)
	 private BigDecimal lm2Fyp;					// Tổng FYP phát hành thuần tháng 3 (M-2)
	 private BigDecimal lm1PolicyCount;			// Tổng hợp đồng phát hành thuần tháng 4 (M-1)
	 private BigDecimal lm1Fyp;					// Tổng FYP phát hành thuần tháng 4 (M-1)

	 	
   private String agentCode;
   private String agentName;
   private String agentType;
   private String agentCodeParen;
   private String agnetNameParen;
   private String agentTypeParen;
   
   
	private String bdthName;
	private String bdthCode;
	private String bdthType;

	private String bdahName;
	private String bdahCode;
	private String bdahType;

	private String bdrhName;
	private String bdrhCode;
	private String bdrhType;

	private String bdohName;
	private String bdohType;
	private String bdohCode;

	private String branchName;
	private String branchCode;
	private String branchType;

	private String unitName;
	private String unitCode;
	private String unitType;

	private String caoCode;
	private String caoName;
	private String caoType;

	private String managerAgentName;
	private String managerAgentCode;
	private String managerAgentType;

	private String gaCode;
	private String gaName;
	private String gaType;

	private String parentCode;
	private String parentAgentCode;
	private Integer agentLevel;
	private Integer treeLevel;
	
	private String reportingToCode;
	private String reportingToName;
	private String reportingToType;
	private String agentGroup;
	private String parentGroup;
	private String childGroup;
	private BigDecimal numOfChild;
	private String parentAll;//type-code-name
	private String agentAll;
	private BigDecimal total;
	private BigDecimal totalGrand;
	
	private Integer no;
	private String id;
	private String orgId;
	private String orgParentId;
	private String parentId;
	private String orgParentName;
	private String orgName;
	private String orgCode;
	
	private BigDecimal fypDate;
	private BigDecimal fypCount;    //Tổng FYP


}
