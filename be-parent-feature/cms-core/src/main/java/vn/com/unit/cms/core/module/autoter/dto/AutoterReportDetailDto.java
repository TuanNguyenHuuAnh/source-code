package vn.com.unit.cms.core.module.autoter.dto;                                      

import lombok.Getter;                                       
import lombok.Setter;                                       

@Getter
@Setter
public class AutoterReportDetailDto {
	
	private String tdCode;                                          // Territory
	private String tdName; 
	private String bdthCode;                                        // 
	private String bdthName;                                        // 
	private String bdthAgenttype;                                  	//
	private String bdthFullName;
	
	private String nCode;                                         	// Area
	private String nName;                                         	// Area
	private String bdahCode;                                        // 
	private String bdahName;                                        // 
	private String bdahAgenttype;                        			//
	private String bdahFullName;
	
	private String rCode;                                        	// Region
	private String rName;                                        	// Region
	private String bdrhCode;                                        // 
	private String bdrhName;                                        // 
	private String bdrhAgenttype;                        			//
	private String bdrhFullName;
	
	private String officeCode;                                      // Office
	private String officeName;                                      // Office
	private String bdohCode;                                        // 
	private String bdohName;                                        // 
	private String bdohAgenttype;                        			//
	private String bdohFullName;
	
	private String lv1Agentcode;                                    //
	private String lv1Agentname;                                    //
	private String lv1Agenttype;                                    //
	private String lv1Status;                                       //
	private String lv1AgentGroup;                                   //
	private String lv2Agentcode;                                    //
	private String lv2Agentname;                                    //
	private String lv2Agenttype;                                    //
	private String lv2Status;                                       //
	private String lv2AgentGroup;                                   //
	private String lv3Agentcode;                                    //
	private String lv3Agentname;                                    //
	private String lv3Agenttype;                                    //
	private String lv3Status;                                       //
	private String lv3AgentGroup;                                   //
	private String orgId;                                       	//
	private String orgCode;                                        	//
	private String orgName;                                        	//
	private Integer policyCountIssue;                               // Số lượng hợp đồng thuần phát hành thuần
	private Integer policyCountService;                             // Số lượng hợp đồng phục vụ
	private String manager; 										// Trưởng phòng
	private String manage; 											// Quản lý
	private String tvtcName ; 										// TVTC
}
