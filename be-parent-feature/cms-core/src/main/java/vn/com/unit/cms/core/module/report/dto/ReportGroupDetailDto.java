package vn.com.unit.cms.core.module.report.dto;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportGroupDetailDto {
	
	private String lv3Agentcode;
	private String lv3Agentname;
	private String lv3Agenttype;
	private String lv2Agentcode;
	private String lv2Agentname;
	private String lv2Agenttype;
	
	private String paren;
	private String child;
	private String manager;
	
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
	
	private String gadCode;
	private String gadName;
	private String gadType;

	private String parentCode;
	private String parentAgentCode;
	private Integer agentLevel;
	private Integer treeLevel;
	
	private String agentCode;
	private String agentName;
	private String agentType;
	private String reportingToCode;
	private String reportingToName;
	private String reportingToType;
	private String agentGroup;
	private String parentGroup;
	private String childGroup;
	private Integer numOfChild;
	private String parentAll;//type-code-name
	private String agentAll;
	private Integer total;
	private Integer totalGrand;
	
    private Integer no;
	private String id;
	private String orgId;
	private String orgParentId;
	private String parentId;
	private String orgParentName;
	private String orgName;
	
	private BigDecimal k2Epp;      							//Phí cần đóng EPP K2
	private BigDecimal k2App;      							//Phí cần đóng APP K2
	private String k2;									//Phần trăm K2 thực tế
	private BigDecimal k2ChargesIssue;         				//Phí thiếu k2

	private BigDecimal k2plusEpp;     						//Phí cần đóng EPP K2+
	private BigDecimal k2plusApp;     						//Phí cần đóng APP K2+
	private String k2plus;								//Phần trăm K2+ thực tế
	private BigDecimal kk2ChargesIssue;       				 //Phí thiếu k2
	
	private String estimateK2ToGetTheRatioK2;			// Dự tính K2 đạt tỉ lệ
	private Double RYPNeedsToCollectlK2;				// RYP cần phải thu
	
	private String estimateK2ToGetTheRatioK2Plus;		// Dự tính K2+ đạt tỉ lệ
	private Double RYPNeedsToCollectlK2Plus;			// RYP cần phải thu
	private String orgCode;

	private String orgNameNew;
	
	private BigDecimal sumK2epp;
	private BigDecimal sumK2app;
	private String sumK2;
	private BigDecimal sumK2issue;
	private BigDecimal sumK2pepp;
	private BigDecimal sumK2papp;
	private String sumK2p;
	private BigDecimal sumk2pissue;



}
