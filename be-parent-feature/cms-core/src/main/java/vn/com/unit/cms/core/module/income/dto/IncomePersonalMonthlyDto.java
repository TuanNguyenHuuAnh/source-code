package vn.com.unit.cms.core.module.income.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
public class IncomePersonalMonthlyDto {
    private Integer no;
	private String agentCode;
	private String agentType;
	private String agentName;
	
	private String taxCode;												//mã số thuế
	private String bankAccountNumber;                                   //số tk
	private String bankName;                                            //tên ngân hàng
	private String paymentPeriod;										//MM_YYYY
	private Date cutoffDate;											//ngày cắt
	
	private Integer balancePreviousMonth;								//Số dư kỳ trước/tháng trước mang sang
	private Integer totalCommission;                                    //hoa hồng
	//private Integer totalReward;										//tổng thưởng
	private Integer totalAllowance;								//phụ cấp
	private Integer totalTaxReturn;							//Tổng kê khai thuế (Quà Tặng/Nhận tiền mặt)
	private Integer gross;												//thu nhập trước thuế
	private Integer tax;												//thuế thu nhập
	private Integer temporarilyHoldTax;									//thuế tạm giữ
	private Integer waitingEndYearTax;									//thuế nộp chờ quyết toán cúi năm
	private Integer totalBalanceDeclarationTax;		//Tổng cân đối kê khai thuế (quà tặng)/Cân đối thu nhập từ quà tặng/Tiền mặt
	private Integer payCash;											//Nộp tiền mặt/trả về từ Ngân hàng
	private Integer totalDeduction;									//Tổng Khấu trừ
	private Integer totalLiabilities;									//Tổng nợ
	private Integer totalOutstandingEndOfTerm;							//Tổng Dư nợ cuối kỳ
	private Integer totalIncomeTemporarilyHold;							//Tổng thu nhập tạm giữ
	private Integer realIncomeEndOfTerm;								//Thực lãnh cuối kỳ
	private Integer totalIncomeYear;									//Tổng thu nhập trong năm
	private Integer totalTaxPaidYear;									//Tổng Thuế đã nộp trong năm
	private Integer totalSponsors;										//tài trợ
	private Integer totalEndingBalance;                                 //tổng số dư cuối kì
	
	//private CommissionDto totalCommission;                               //tổng hoa hồng
	//private Integer totalCommission;                                    //hoa hồng
	//private AllowanceDto totalAllowance;                               //phụ cấp
	//private DeclarationTaxDto totalTaxReturn;                          //Tổng kê khai thuế (Quà Tặng/Nhận tiền mặt)
	//private DeclarationTaxDto totalTaxReturn;                            //Tổng kê khai thuế (Quà Tặng/Nhận tiền mặt)
    //private BalanceDeclarationTaxDto totalBalanceDeclarationTax;      //Tổng cân đối kê khai thuế (quà tặng)/Cân đối thu nhập từ quà tặng/Tiền mặt
	//private DeductDto totalDeduction;                                    //Tổng Khấu trừ
	
	//detail
	private String mainCode;
    private String mainName;
    private String subCode;
    private String subName;
    private String itemCode;
    private String itemName;
    private BigDecimal amount;
    
    private String mainDirectory;
    private String subDirectory;
    private String detailDirectory;
    private BigDecimal amountDirectory;
    
    private String levelId;
    
    public IncomePersonalMonthlyDto(String mainCode, String mainName, String mainDirectory, String subDirectory, BigDecimal amountDirectory) {
        super();
        this.mainCode = mainCode;
        this.mainName = mainName;
        this.mainDirectory = mainDirectory;
        this.subDirectory = subDirectory;
        this.amountDirectory = amountDirectory;
    }
}
