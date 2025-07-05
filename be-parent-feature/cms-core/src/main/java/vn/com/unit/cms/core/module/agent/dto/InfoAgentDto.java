package vn.com.unit.cms.core.module.agent.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoAgentDto {
	
	private Integer no;                               //Quản lý 
	private String agentAll;
	private String agentType; 
	private String agentName;
	private String gender;                            //GIOI TINH
	private String start;                             //TRANG THAI
	private String mobilePhone;                            //PHONE
	private Date appointedDate;                           //NGAY BAT DAU HOA DONG
	private Date terminatedDate;                             //NGAY CHAM DƯT HOP DONG
	private Date birthDay;
	
	private String gadCode;
	private String gadName;
	private String gadType;
	private String gad;
	private String agentGroup;                        
	private BigDecimal total;                             //TOTAL CAC COT 
	private BigDecimal totalheadofdepartment;             
	private BigDecimal totalmanager;                      
	private BigDecimal totaltvtc;                         
	private BigDecimal totaltvtcsa;                       
	private String grandTotal;                        // TONG CAC NOTE 
	private String childGroup;                        
	private String agentCode;                         
	private String orgCode;                          
	
	
	private String mobile;
	private String email;
	private String emailDLVN;
	private String addressStaying;						//Địa chỉ tạm trú
	private String address;
	
	private String agentTyle;
	private String work;
	private String presenterCode;						//Người giới thiệu
	private String presenterName;
	private String mangerAgentCode;							//Người quản lý trực tiếp 
	private String managerAgentName;
	private String managerAgentType;
	private String leaderCode;							//Trưởng phòng
	private String leaderName;
	private String title;
	private String startDateDVLN;						//Ngày gia nhập DLVN
	private String taxNumber;							//Ma so thue TNCN
	private String parentAgentCode;
	private String parentAgentType;
	private String parentAgentName;
	
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
	private String orgParentId;
	private String agentStatus;
	private Integer agentLevel;
	private Integer treeLevel;
	private String groupType;
	private String orgNameNew;
}
