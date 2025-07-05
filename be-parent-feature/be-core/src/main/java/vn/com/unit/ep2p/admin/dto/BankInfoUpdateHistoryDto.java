package vn.com.unit.ep2p.admin.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankInfoUpdateHistoryDto {
	private Integer no;
	private Integer id;
	private String agentCode;
	private String agentName;
	private String idNumber;
	private String bankAccountName;
	private String bankBranchName;
	private String bankName;
	private String bankAccountNumber;
	private String img1;
	private String img1Physical;
	private String img2;
	private String img2Physical;
	private String email;
	private Date submitTime;
}
