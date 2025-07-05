package vn.com.unit.cms.core.module.contract.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractExpiresSearchResultDto {
	private String no;						// STT
	private String policyKey;		// So HDBH
	private String poName;			// Ben mua BH
	private String insuranceOfCustomerMain;			// Nguoi duoc BH chinh
	private Date polIssueEff;				// Ngay hieu luc hop dong
	private Date polMaturedDt;					// Ngay dao han hop dong
	private String polBaseFaceAmt;			// So tien bao hiem
	private String apl;					// No APL
	private String opl;					// No OPL
	private String interest;			// Lai chia
	private String interestEnd;		// Lai chia cuoi hop dong
	private String cash;			// Tien mat dinh ki
	private String premium;		// Phi bao hiem du
	private String refund;				// So tien phai tra
	private String mainProduct; 		//SPBH chinh
	private String tmpAmountOfMoney;	 //So tien tam tinh cua quyen loi dao han
	private String isShare;
}
