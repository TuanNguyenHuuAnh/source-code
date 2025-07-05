package vn.com.unit.cms.core.module.income.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeductDto {//Khấu trừ
	private Integer examFeeFfs;			//Lệ phí thi FFS
	private Integer calendarFee;		//Chi phí tiền lịch
	private Integer travelExpenses;		//Chi phí đi lại
	private Integer advanceMonth;		//Tạm ứng trong tháng

}
