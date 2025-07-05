package vn.com.unit.cms.core.module.reportGroup;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportGroupResultManPowerDetailDto {
	
	private String agentCode;
    private String agentName;
    private String agentType;
    private String agentGroup;
    private String agentcodeParent;
    private String agentNameParent;
    private String agentTypeParent;
    
    private BigDecimal countBmTypeAgentcode;    					//	Số lượng BM
    private BigDecimal countUmTypeAgentcode;   					//	Số lượng UM
    private BigDecimal countPumTypeAgentcode;   				//	Số lượng PUM
    private BigDecimal countFcTypeAgentcode;    				//	Số lượng FC
    private BigDecimal countSaTypeAgentcode;    				//	Số lượng SA
    private BigDecimal countNewRecruitment;    				//	Số lượng Tuyển dụng                                     //
    private BigDecimal countReinstate;    						//	Số lượng khôi phục mã số
    private BigDecimal countActive;    						//	Số lượng FC hoạt động                                   //
    private BigDecimal countNewRecruitmentActive;    			//	Số lượng FC mới hoạt động
    private BigDecimal countSchemeFc;    						//	Số lượng FC hoạt động theo scheme
    private BigDecimal countPfcFc;    							//	Số lượng FC năng động (PFC)

    private String orgNameNew;
    
    private String manager;									//	Trưởng phòng
    private Integer recruitmentNewMonth;					//	Tuyển dụng mới (MTD)
    private Integer recoveryCodeMonth;						//	Khôi phục mã số (MTD)
    private BigDecimal fypDate;									//	FYP ngày
    private Integer lastYearQuantilyAction;					//	Số lượt hoạt động
    private Integer quantilyActiveLastYear;					//	Số lượt hoạt động năm trước
	private String paren;
	private String child;
	
	 private String lv2Agentname;						
	 private String lv3Agentname;						
	 private String lv2Agentcode;
	 private String lv3Agentcode;
	 private String lv2Agenttype;
	 private String lv3Agenttype;
	 private String lv1Agentname;					
	 private String lv1Agentcode;
	 private String lv1Agenttype;
    
    //CUNG KY NAM NGOAI
    private BigDecimal lastCountNewRecruitment;				//	Số lượng Tuyển dụng
    private BigDecimal lastCountReinstate;						//	Số lượng khôi phục mã số
    private BigDecimal lastCountNewRecruitmentActive;			//	Số lượng TV mới hoạt động
    private BigDecimal lastCountActive; 						//	



    private String childGroup;
    private String caoCode;
    private String bdthCode;
    private String bdahCode;
    private String bdrhCode;
    private String bdohCode;
    private String gaCode;
    private String branchCode;
    private String unitCode;

    private String caoName;
    private String bdthName;
    private String bdahName;
    private String bdrhName;
    private String bdohName;
    private String gaName;
    private String branchName;
    private String unitName;

    private String caoType;
    private String bdthType;
    private String bdahType;
    private String bdrhType;
    private String bdohType;
    private String gaType;
    private String branchType;
    private String unitType;

    private String orgId;
    private String orgName;
    private String parentGroup;
    private String agentStatus;
	private String parentAll;//type-code-name
	private String agentAll;
	private Integer agentLevel;
	private Integer treeLevel;
	private String orgParentId;
	private String parentId;
	private String orgParentName;
	private String parentCode;
	private String parentAgentCode;
	private String lastDate;
	private String orgCode;
}
