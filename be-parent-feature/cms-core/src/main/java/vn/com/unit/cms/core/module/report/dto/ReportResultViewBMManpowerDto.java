package vn.com.unit.cms.core.module.report.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportResultViewBMManpowerDto {
	private String officeCode;
	private String officeName;
	private String bmCode;
	private String bmName;
	private String reportingDate;

	private String managerCode;//quan ly
	private String managerName;
	private String managerType;
	private String agentCode;//tvtc
	private String agentName;
	private String agentType;

	//sumary BM
    private Integer umQuantity;//so luong um
    private Integer fcQuantity;//so luong fc
    private Integer newRecruitmentQuantity;//Tổng số tuyển dụng mới
    private Integer recoveryCodeQuantity;//Số lượng khôi phục mã số
    private Integer activeQuantity;//Số lượng hoạt động
    private Integer fypTotal;//Tổng số FYP 
    private Integer rypTotal;//Tổng số RYP
    
    private Integer newRecruitmentQuantityByManager;//Tổng tuyển dụng mới trong tháng theo quản lý
    private Integer recoveryCodeQuantityByManager;//Tổng khôi phục mã số trong tháng theo quản lý
    private Integer fypTotalByManager;//FYP ngày theo quản lý
    private Integer activeFcQuantityByManager;//Số lượng FC hoạt động của từng quản lý
    private Integer newActiveFcQuantityByManager;// Số lượng FC mới hoạt động của từng quản lý
    private Integer dynamicFcQuantityByManager;//Số lượng FC năng động của từng quản lý

    
}
