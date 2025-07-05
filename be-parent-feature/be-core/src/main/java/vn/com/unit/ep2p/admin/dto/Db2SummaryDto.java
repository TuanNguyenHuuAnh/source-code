package vn.com.unit.ep2p.admin.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Db2SummaryDto {

	private String ndCode;  // toan quoc
	private String tdCode;  // mien
	private String nCode;   // khu vuc
	private String rCode;   // vung
	private String oCode;   // office

	private Date createdDate;
	private String createdBy;

	private String agentCode; // AgentCode 
	private String agentName;
	private String agentType;

	private String lv2Agentcode; // TVTC
	private String lv2Agentname;
	private String lv2Agenttype;
	
    private String bdthCode;
    private String bdthName;
    private String bdthAgentType;
    
    private String bdahCode;
    private String bdahName;
    private String bdahAgentType;
    
    private String bdrhCode;
    private String bdrhName;
    private String bdrhAgentType;
    
	private String bdohCode; 
    private String bdohName;
    private String bdohAgentType;
	
	private String reportingToCode;
	private String reportingToName;
	private String reportingToType;
	
	private String lv1Agentcode; // bmCode 
	private String lv1Agentname;
	private String lv1Agenttype;
	
	private String gabName;

	private String oName;
	
}
