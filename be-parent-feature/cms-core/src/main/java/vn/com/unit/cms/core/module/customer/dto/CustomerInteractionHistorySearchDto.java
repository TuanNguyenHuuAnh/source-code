package vn.com.unit.cms.core.module.customer.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class CustomerInteractionHistorySearchDto  extends CommonSearchWithPagingDto {
	private String agentCode; 				// username
    private String phoneNumber;             // So dien thoai
	private String customerType;			// Nhom KH
	private String proposalNo;				// So HSYCBH
	private String policyNo;				// So HDBH
	private String insuredBuyer;			// Ben mua bao hiem
	private String insuredName;				// Nguoi duoc bao hiem
	private String status;					// Tinh trang
}
