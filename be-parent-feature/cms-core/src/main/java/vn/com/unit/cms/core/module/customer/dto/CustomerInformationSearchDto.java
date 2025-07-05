package vn.com.unit.cms.core.module.customer.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class CustomerInformationSearchDto  extends CommonSearchWithPagingDto {
	private String agentCode; // username
	private Object customerNo; // ma khach hang
	private Object customerName; // ten khach hang
	private Object customerType; // loại khach hang
	private Object nickName; // nick name
	private Object dateOfBirth;// ngay sinh
	private Object address; // dia chi lien lac
    private String phoneNumber;    	// Điện thoại di động
    private Object createdDate;    	// Ngày tạo thông tin
    private Object reachDate;    	// Ngày hết hạn tiếp cận
    private String clientStatus;    // Tình trạng
}
