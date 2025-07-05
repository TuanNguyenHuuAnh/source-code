package vn.com.unit.cms.core.module.contract.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContractBusinessSearchResultDto {
	private Integer no;							// STT

	private String policyNo;//so hd
	private String totalContract;//tong so hd
	private String insuranceBuyer;//ben mua bao hiem
	private String requestAdjustment;// yeu cau dieu chinh, loai yeu cau
	private String status;// trang thai
	private String waitingAddInformation;//thong tin cho bo sung
	private Date requestDate;// ngay yeu cau
	private Date startDate;// ngay thuc hien
	private Date addExpirationDate;// ngay het han bo sung
	private String agentCode;
	private String agentName;  //lv3Agenttype
	private String agentType;
	private String lv3Agentname;
	private String lv3Agentcode;
	private String lv3Agenttype;
	private String officeName;
	private String requestDetail;
	private boolean check;
	
	private String leName; //ten thu
	private String leLink; //link thu
	private String typeIn; // loai yeu cau
	private String typeLink; // link form
	private String processtypecode; //loai yeu cau code
	
	private String customerNo;
	
	private String scanloc; //van phong tiep nhan 
	private Integer isShare;
	private List<String> listWaitingAddInformation;
	private String keyGroup;

	public ContractBusinessSearchResultDto(Integer no, String policyNo, String totalContract, String insuranceBuyer, String requestAdjustment, String status, String waitingAddInformation, Date requestDate, Date startDate, Date addExpirationDate, boolean check) {
		this.no = no;
		this.policyNo = policyNo;
		this.totalContract = totalContract;
		this.insuranceBuyer = insuranceBuyer;
		this.requestAdjustment = requestAdjustment;
		this.status = status;
		this.waitingAddInformation = waitingAddInformation;
		this.requestDate = requestDate;
		this.startDate = startDate;
		this.addExpirationDate = addExpirationDate;
		this.check = check;
	}
}
